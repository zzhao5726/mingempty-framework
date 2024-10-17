package top.mingempty.datasource.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import top.mingempty.commons.data.StopWatch;
import top.mingempty.datasource.aspect.annotation.Ds;
import top.mingempty.datasource.enums.ClusterMode;
import top.mingempty.domain.other.ThreadLocalLink;
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
public class DsAspect implements Ordered {

    /**
     * 切面线程变量
     */
    private static final ThreadLocalLink<DsInfo> THREAD_LOCAL = new ThreadLocalLink<>();

    /**
     * 获取当前线程datasource切面名称
     *
     * @return
     */
    public static DsInfo acquireDs() {
        return THREAD_LOCAL.acquireData();
    }

    /**
     * 设置当前线程datasource切面名称
     *
     * @return
     */
    public static void putDs(DsInfo dsInfo) {
        THREAD_LOCAL.putData(dsInfo);
    }

    /**
     * 移除当前线程datasource切面名称
     *
     * @return
     */
    public static void removeDs() {
        THREAD_LOCAL.removeData();
    }


    @Pointcut("@within(top.mingempty.datasource.aspect.annotation.Ds)" +
            "|| @annotation(top.mingempty.datasource.aspect.annotation.Ds)")
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
        if (log.isDebugEnabled()) {
            log.debug("DS切面AOP环绕通知=========={}#{}方法执行开始", aClass.getName(), method.getName());
        }
        Ds ds = null;
        if (method.isAnnotationPresent(Ds.class)) {
            ds = method.getAnnotation(Ds.class);
        } else if (aClass.isAnnotationPresent(Ds.class)) {
            ds = (Ds) aClass.getAnnotation(Ds.class);
        }
        try {
            if (ds != null) {
                putDs(new DsInfo(EnvironmentUtil.resolvePlaceholders(ds.value()), ds.type()));
            }
            return proceed(joinPoint);
        } finally {
            removeDs();
            stopWatch.stop();
            if (log.isDebugEnabled()) {
                log.debug("DS切面AOP环绕通知==========执行结束,共耗时[{}]ms", stopWatch.getTotalTimeMillis());
            }
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

    public record DsInfo(String dsName, ClusterMode type) {
    }


}
