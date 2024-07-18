package top.mingempty.cache.redis.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import top.mingempty.cache.redis.annotation.RedisCacheCut;
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
public class RedisCacheAspect implements Ordered {


    private static final ThreadLocalLink THREAD_LOCAL_LINK = new ThreadLocalLink();

    /**
     * 获取当前线程cache切面名称
     *
     * @return
     */
    public static String acquireCacheName() {
        return THREAD_LOCAL_LINK.acquireName();
    }

    /**
     * 设置当前线程cache切面名称
     *
     * @return
     */
    public static void putCacheName(String cacheName) {
        THREAD_LOCAL_LINK.putName(cacheName);
    }

    /**
     * 移除当前线程cache切面名称
     *
     * @return
     */
    public static void removeCacheName() {
        THREAD_LOCAL_LINK.removeName();
    }


    @Pointcut("@within(top.mingempty.cache.redis.annotation.RedisCacheCut)" +
            "|| @annotation(top.mingempty.cache.redis.annotation.RedisCacheCut)")
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
        log.debug("RedisCache切面AOP环绕通知=========={}#{}方法执行开始", aClass.getName(), method.getName());
        RedisCacheCut redisCacheCut = null;
        if (aClass.isAnnotationPresent(RedisCacheCut.class)) {
            redisCacheCut = (RedisCacheCut) aClass.getAnnotation(RedisCacheCut.class);
        } else if (method.isAnnotationPresent(RedisCacheCut.class)) {
            redisCacheCut = method.getAnnotation(RedisCacheCut.class);
        }
        try {
            if (redisCacheCut != null) {
                putCacheName(EnvironmentUtil.resolvePlaceholders(redisCacheCut.value()));
            }
            return proceed(joinPoint);
        } finally {
            removeCacheName();
            stopWatch.stop();
            log.debug("RedisCache切面AOP环绕通知==========执行结束,共耗时[{}]ms", stopWatch.getTotalTimeMillis());
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
