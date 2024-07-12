package top.mingempty.concurrent.thread;


import top.mingempty.commons.trace.TraceContext;
import top.mingempty.concurrent.model.enums.PriorityEnum;

import java.util.concurrent.CountDownLatch;
import java.util.function.Supplier;

/**
 * 线程Callable简单实现类
 *
 * @author zzhao
 * @date 2023/8/2 11:26
 */
public class SimpleDelegatingCallable<V>
        extends AbstractDelegatingCallable<V> {

    /**
     * 默认线程实现方法
     */
    private final Supplier<V> supplier;

    public SimpleDelegatingCallable(Supplier<V> supplier) {
        this(supplier, null, null, null);
    }

    public SimpleDelegatingCallable(Supplier<V> supplier, PriorityEnum priorityEnum) {
        this(supplier, priorityEnum, null, null);
    }

    public SimpleDelegatingCallable(Supplier<V> supplier, TraceContext traceContext) {
        this(supplier, null, traceContext, null);
    }

    public SimpleDelegatingCallable(Supplier<V> supplier, CountDownLatch countDownLatch) {
        this(supplier, null, null, countDownLatch);
    }

    public SimpleDelegatingCallable(Supplier<V> supplier, PriorityEnum priorityEnum, TraceContext traceContext) {
        this(supplier, priorityEnum, traceContext, null);
    }

    public SimpleDelegatingCallable(Supplier<V> supplier, PriorityEnum priorityEnum, CountDownLatch countDownLatch) {
        this(supplier, priorityEnum, null, countDownLatch);
    }

    public SimpleDelegatingCallable(Supplier<V> supplier, TraceContext traceContext, CountDownLatch countDownLatch) {
        this(supplier, null, traceContext, countDownLatch);
    }

    public SimpleDelegatingCallable(Supplier<V> supplier, PriorityEnum priorityEnum, TraceContext traceContext,
                                    CountDownLatch countDownLatch) {
        super(priorityEnum, countDownLatch, traceContext);
        assert supplier != null;
        this.supplier = supplier;
    }

    /**
     * 线程真正执行的业务方法
     *
     * @return 线程执行结果
     */
    @Override
    public V realRun() throws Exception {
        return supplier.get();
    }
}
