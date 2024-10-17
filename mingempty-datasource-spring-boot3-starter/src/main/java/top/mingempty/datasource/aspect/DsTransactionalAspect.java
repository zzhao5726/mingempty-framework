package top.mingempty.datasource.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import top.mingempty.commons.data.StopWatch;
import top.mingempty.datasource.aspect.annotation.DSTransactional;
import top.mingempty.datasource.enums.DsPropagation;
import top.mingempty.datasource.transaction.TransactionalExecutor;
import top.mingempty.datasource.transaction.TransactionalTemplate;
import top.mingempty.domain.other.ThreadLocalLink;
import top.mingempty.util.AspectUtil;

import java.lang.reflect.Method;

/**
 * 多数据源本地事务切面类
 *
 * @author zzhao
 */
@Slf4j
@Aspect
public class DsTransactionalAspect implements Ordered {

    /**
     * 切面线程变量
     */
    private static final ThreadLocalLink<TransactionalInfo> THREAD_LOCAL = new ThreadLocalLink<>();

    /**
     * 事务模板
     */
    private final TransactionalTemplate transactionalTemplate = new TransactionalTemplate();

    /**
     * 获取当前线程datasource切面名称
     *
     * @return
     */
    public static TransactionalInfo acquireTs() {
        return THREAD_LOCAL.acquireData();
    }

    /**
     * 设置当前线程datasource切面名称
     *
     * @return
     */
    public static void putTs(TransactionalInfo transactionalInfo) {
        THREAD_LOCAL.putData(transactionalInfo);
    }

    /**
     * 移除当前线程datasource切面名称
     *
     * @return
     */
    public static void removeTs() {
        THREAD_LOCAL.removeData();
    }


    @Pointcut("@within(top.mingempty.datasource.aspect.annotation.DSTransactional)" +
            "|| @annotation(top.mingempty.datasource.aspect.annotation.DSTransactional)")
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
            log.debug("Ts切面AOP环绕通知=========={}#{}方法执行开始", aClass.getName(), method.getName());
        }
        DSTransactional dsTransactional = null;
        if (method.isAnnotationPresent(DSTransactional.class)) {
            dsTransactional = method.getAnnotation(DSTransactional.class);
        } else if (aClass.isAnnotationPresent(DSTransactional.class)) {
            dsTransactional = (DSTransactional) aClass.getAnnotation(DSTransactional.class);
        }
        try {
            if (dsTransactional != null) {
                putTs(new TransactionalInfo(dsTransactional.rollbackFor(), dsTransactional.noRollbackFor(), dsTransactional.propagation()));
            }

            TransactionalExecutor transactionalExecutor = new TransactionalExecutor() {
                @Override
                public Object execute() throws Throwable {
                    return proceed(joinPoint);
                }

                @Override
                public TransactionalInfo getTransactionInfo() {
                    return acquireTs();
                }
            };

            return transactionalTemplate.execute(transactionalExecutor);
        } finally {
            removeTs();
            stopWatch.stop();
            if (log.isDebugEnabled()) {
                log.debug("Ts切面AOP环绕通知==========执行结束,共耗时[{}]ms", stopWatch.getTotalTimeMillis());
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
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    /**
     * 事务基础信息
     *
     * @param rollbackFor   回滚异常
     * @param noRollbackFor 不回滚异常
     * @param propagation   事务传播行为
     */
    public record TransactionalInfo(Class<? extends Throwable>[] rollbackFor,
                                    Class<? extends Throwable>[] noRollbackFor,
                                    DsPropagation propagation) {
    }

}
