package top.mingempty.zookeeper.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import top.mingempty.commons.data.StopWatch;
import top.mingempty.domain.other.ThreadLocalLink;
import top.mingempty.util.AspectUtil;
import top.mingempty.util.EnvironmentUtil;
import top.mingempty.zookeeper.annotation.ZookeeperCut;

import java.lang.reflect.Method;

/**
 * 数据源名称切面类
 *
 * @author zzhao
 */
@Slf4j
@Aspect
public class ZookeeperAspect implements Ordered {


    private static final ThreadLocalLink<String> THREAD_LOCAL_LINK = new ThreadLocalLink<>();

    /**
     * 获取当前线程Zookeeper切面名称
     *
     * @return
     */
    public static String acquireName() {
        return THREAD_LOCAL_LINK.acquireData();
    }

    /**
     * 设置当前线程Zookeeper切面名称
     *
     * @return
     */
    public static void putName(String acquireName) {
        THREAD_LOCAL_LINK.putData(acquireName);
    }

    /**
     * 移除当前线程Zookeeper切面名称
     *
     * @return
     */
    public static void removeName() {
        THREAD_LOCAL_LINK.removeData();
    }


    @Pointcut("@within(top.mingempty.zookeeper.annotation.ZookeeperCut)" +
            "|| @annotation(top.mingempty.zookeeper.annotation.ZookeeperCut)")
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
        log.debug("Zookeeper切面AOP环绕通知=========={}#{}方法执行开始", aClass.getName(), method.getName());
        ZookeeperCut zookeeperCut = null;
        if (aClass.isAnnotationPresent(ZookeeperCut.class)) {
            zookeeperCut = (ZookeeperCut) aClass.getAnnotation(ZookeeperCut.class);
        } else if (method.isAnnotationPresent(ZookeeperCut.class)) {
            zookeeperCut = method.getAnnotation(ZookeeperCut.class);
        }
        try {
            if (zookeeperCut != null) {
                putName(EnvironmentUtil.resolvePlaceholders(zookeeperCut.value()));
            }
            return proceed(joinPoint);
        } finally {
            removeName();
            stopWatch.stop();
            log.debug("Zookeeper切面AOP环绕通知==========执行结束,共耗时[{}]ms", stopWatch.getTotalTimeMillis());
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
