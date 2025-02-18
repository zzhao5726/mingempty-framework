package top.mingempty.concurrent.thread;


import com.alibaba.ttl.TtlCallable;
import top.mingempty.commons.trace.TraceContext;
import top.mingempty.concurrent.exception.ConcurrentException;
import top.mingempty.concurrent.model.enums.PriorityEnum;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * 线程Callable抽象的委托实现类
 *
 * @author zzhao
 * @date 2023/8/2 11:26
 */
public abstract class AbstractDelegatingCallable<V>
        extends AbstractDelegatingContext<V>
        implements Callable<V> {


    public AbstractDelegatingCallable() {
        super();
    }

    public AbstractDelegatingCallable(CountDownLatch countDownLatch) {
        super(countDownLatch);
    }

    public AbstractDelegatingCallable(CountDownLatch countDownLatch, PriorityEnum priorityEnum) {
        super(countDownLatch, priorityEnum);
    }

    public AbstractDelegatingCallable(CountDownLatch countDownLatch, PriorityEnum priorityEnum, TraceContext traceContext) {
        super(countDownLatch, priorityEnum, traceContext);
    }

    public AbstractDelegatingCallable(CountDownLatch countDownLatch, TraceContext traceContext) {
        super(countDownLatch, traceContext);
    }

    public AbstractDelegatingCallable(Map<String, Object> params) {
        super(params);
    }

    public AbstractDelegatingCallable(Map<String, Object> params, CountDownLatch countDownLatch) {
        super(params, countDownLatch);
    }

    public AbstractDelegatingCallable(Map<String, Object> params, CountDownLatch countDownLatch, TraceContext traceContext) {
        super(params, countDownLatch, traceContext);
    }

    public AbstractDelegatingCallable(Map<String, Object> params, PriorityEnum priorityEnum) {
        super(params, priorityEnum);
    }

    public AbstractDelegatingCallable(Map<String, Object> params, PriorityEnum priorityEnum, CountDownLatch countDownLatch) {
        super(params, priorityEnum, countDownLatch);
    }

    public AbstractDelegatingCallable(Map<String, Object> params, PriorityEnum priorityEnum, CountDownLatch countDownLatch, TraceContext traceContext) {
        super(params, priorityEnum, countDownLatch, traceContext);
    }

    public AbstractDelegatingCallable(Map<String, Object> params, PriorityEnum priorityEnum, TraceContext traceContext) {
        super(params, priorityEnum, traceContext);
    }

    public AbstractDelegatingCallable(Map<String, Object> params, TraceContext traceContext) {
        super(params, traceContext);
    }

    public AbstractDelegatingCallable(PriorityEnum priorityEnum) {
        super(priorityEnum);
    }

    public AbstractDelegatingCallable(PriorityEnum priorityEnum, CountDownLatch countDownLatch) {
        super(priorityEnum, countDownLatch);
    }

    public AbstractDelegatingCallable(PriorityEnum priorityEnum, CountDownLatch countDownLatch, TraceContext traceContext) {
        super(priorityEnum, countDownLatch, traceContext);
    }

    public AbstractDelegatingCallable(PriorityEnum priorityEnum, TraceContext traceContext) {
        super(priorityEnum, traceContext);
    }

    public AbstractDelegatingCallable(TraceContext traceContext) {
        super(traceContext);
    }

    @Override
    public final V call() throws Exception {
        return abstractRun();
    }

    /**
     * 线程池Callable转换为封装的线程池执行类
     *
     * @param callable 要执行的Callable
     * @return 返回委托的Callable
     */
    public static <V> Callable<V> delegatingCallable(Callable<V> callable) {
        return delegatingCallable(callable, PriorityEnum.D);
    }

    /**
     * 线程池Callable转换为封装的线程池执行类
     *
     * @param callable     要执行的Callable
     * @param priorityEnum 线程优先级
     * @return 返回委托的Callable
     */
    public static <V> Callable<V> delegatingCallable(Callable<V> callable, PriorityEnum priorityEnum) {
        AbstractDelegatingCallable<V> abstractDelegatingCallable = gainDelegatingCallable(callable);
        if (abstractDelegatingCallable != null) {
            return callable;
        }

        return new SimpleDelegatingCallable<>(() -> {
            try {
                return callable.call();
            } catch (Exception e) {
                throw new ConcurrentException("concurrent-0000000004", e);
            }
        }, priorityEnum);
    }

    /**
     * 获取自定义代理的Callable
     *
     * @param callable 原始Callable
     * @param <V>
     * @return
     */
    public static <V> AbstractDelegatingCallable<V> gainDelegatingCallable(Callable<V> callable) {
        if (callable instanceof AbstractDelegatingCallable<V> abstractDelegatingCallable) {
            return abstractDelegatingCallable;
        }

        if (callable instanceof TtlCallable<V> ttlCallable
                && ttlCallable.getCallable() instanceof AbstractDelegatingCallable<V> abstractDelegatingCallable) {
            return abstractDelegatingCallable;
        }
        return null;
    }
}
