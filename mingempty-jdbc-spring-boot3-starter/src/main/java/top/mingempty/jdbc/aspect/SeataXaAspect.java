package top.mingempty.jdbc.aspect;


import io.seata.rm.datasource.xa.DataSourceProxyXA;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import top.mingempty.commons.data.StopWatch;
import top.mingempty.util.AspectUtil;

import javax.sql.DataSource;

/**
 * 对seata xa模式的代理实现
 *
 * @author zzhao
 */
@Slf4j
@Aspect
public class SeataXaAspect implements Ordered {


    @Pointcut("execution(* top.mingempty.jdbc.factory.DatasourceFactory.createDataSource(..))")
    public void cut() {

    }

    @Around("cut()")
    public Object Around(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.debug("Seata AT模式代理切面AOP环绕通知=========={}#{}方法执行开始",
                AspectUtil.getClass(joinPoint).getName(), AspectUtil.getMethod(joinPoint).getName());
        try {
            Object proceed = joinPoint.proceed(joinPoint.getArgs());
            if (proceed instanceof DataSource dataSource) {
                return new DataSourceProxyXA(dataSource);
            }
            return proceed;
        } finally {
            stopWatch.stop();
            log.debug("Seata AT模式代理切面AOP环绕通知==========执行结束,共耗时[{}]ms", stopWatch.getTotalTimeMillis());
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
