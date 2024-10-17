package top.mingempty.cache.local.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import top.mingempty.cache.local.annotation.LocalCacheCut;
import top.mingempty.commons.data.StopWatch;
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
public class LocalCacheAspect implements Ordered {


    private static final ThreadLocalLink<String> THREAD_LOCAL_LINK = new ThreadLocalLink<>();

    /**
     * 获取当前线程cache切面名称
     *
     * @return
     */
    public static String acquireCacheName() {
        return THREAD_LOCAL_LINK.acquireData();
    }

    /**
     * 设置当前线程cache切面名称
     *
     * @return
     */
    public static void putCacheName(String cacheName) {
        THREAD_LOCAL_LINK.putData(cacheName);
    }

    /**
     * 移除当前线程cache切面名称
     *
     * @return
     */
    public static void removeCacheName() {
        THREAD_LOCAL_LINK.removeData();
    }


    @Pointcut("@within(top.mingempty.cache.local.annotation.LocalCacheCut)" +
            "|| @annotation(top.mingempty.cache.local.annotation.LocalCacheCut)")
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
        log.debug("LocalCache切面AOP环绕通知=========={}#{}方法执行开始", aClass.getName(), method.getName());
        LocalCacheCut localCacheCut = null;
        if (aClass.isAnnotationPresent(LocalCacheCut.class)) {
            localCacheCut = (LocalCacheCut) aClass.getAnnotation(LocalCacheCut.class);
        } else if (method.isAnnotationPresent(LocalCacheCut.class)) {
            localCacheCut = method.getAnnotation(LocalCacheCut.class);
        }
        try {
            if (localCacheCut != null) {
                putCacheName(EnvironmentUtil.resolvePlaceholders(localCacheCut.value()));
            }
            return proceed(joinPoint);
        } finally {
            removeCacheName();
            stopWatch.stop();
            log.debug("LocalCache切面AOP环绕通知==========执行结束,共耗时[{}]ms", stopWatch.getTotalTimeMillis());
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
