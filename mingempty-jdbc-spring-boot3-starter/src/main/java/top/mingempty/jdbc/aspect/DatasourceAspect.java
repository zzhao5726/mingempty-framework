package top.mingempty.jdbc.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import top.mingempty.commons.data.StopWatch;
import top.mingempty.domain.other.ThreadLocalLink;
import top.mingempty.jdbc.annotation.DS;
import top.mingempty.util.AspectUtil;
import top.mingempty.util.EnvironmentUtil;

import java.lang.reflect.Method;

/**
 * 数据源名称切面类
 *
 * @author zzhao
 */
@Slf4j
@Aspect
public class DatasourceAspect implements Ordered {

    /**
     * 切面线程变量
     */
    private static final ThreadLocalLink THREAD_LOCAL = new ThreadLocalLink();

    /**
     * 获取当前线程datasource切面名称
     *
     * @return
     */
    public static String acquireName() {
        return THREAD_LOCAL.acquireName();
    }

    /**
     * 设置当前线程datasource切面名称
     *
     * @return
     */
    public static void putName(String name) {
        THREAD_LOCAL.putName(name);
    }

    /**
     * 移除当前线程datasource切面名称
     *
     * @return
     */
    public static void removeName() {
        THREAD_LOCAL.removeName();
    }


    @Pointcut("@within(top.mingempty.jdbc.annotation.DS)" +
            "|| @annotation(top.mingempty.jdbc.annotation.DS)")
    public void cut() {

    }


    @Around("cut()")
    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取Method
        Method method = AspectUtil.getMethod(joinPoint);
        //获取代理的对象
        Class aClass = AspectUtil.getClass(joinPoint);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.debug("DS切面AOP环绕通知=========={}#{}方法执行开始", aClass.getName(), method.getName());
        DS ds = null;
        if (aClass.isAnnotationPresent(DS.class)) {
            ds = (DS) aClass.getAnnotation(DS.class);
        } else if (method.isAnnotationPresent(DS.class)) {
            ds = method.getAnnotation(DS.class);
        }
        try {
            if (ds != null) {
                putName(EnvironmentUtil.resolvePlaceholders(ds.value()));
            }
            return proceed(joinPoint);
        } finally {
            removeName();
            stopWatch.stop();
            log.debug("DS切面AOP环绕通知==========执行结束,共耗时[{}]ms", stopWatch.getTotalTimeMillis());
        }
    }


    public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取参数
        Object[] args = joinPoint.getArgs();
        Object proceed = null;
        if (args == null || args.length == 0) {
            proceed = joinPoint.proceed();
        } else {
            proceed = joinPoint.proceed(args);
        }
        return proceed;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
