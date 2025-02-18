package top.mingempty.concurrent.thread;


import top.mingempty.commons.trace.TraceContext;
import top.mingempty.concurrent.model.enums.PriorityEnum;
import top.mingempty.domain.function.ApplyFunction;

import java.util.concurrent.CountDownLatch;

/**
 * 线程Runnable简单实现类
 *
 * @author zzhao
 * @date 2023/8/2 11:26
 */
public class SimpleDelegatingRunnable
        extends AbstractDelegatingRunnable {

    /**
     * 默认线程实现方法
     */
    private final ApplyFunction applyFunction;

    public SimpleDelegatingRunnable(ApplyFunction applyFunction) {
        this(applyFunction, (PriorityEnum) null);
    }

    public SimpleDelegatingRunnable(ApplyFunction applyFunction, PriorityEnum priorityEnum) {
        this(applyFunction, priorityEnum, (TraceContext) null);
    }

    public SimpleDelegatingRunnable(ApplyFunction applyFunction, TraceContext traceContext) {
        this(applyFunction, null, traceContext);
    }

    public SimpleDelegatingRunnable(ApplyFunction applyFunction, CountDownLatch countDownLatch) {
        this(applyFunction, null, null, countDownLatch);
    }

    public SimpleDelegatingRunnable(ApplyFunction applyFunction, PriorityEnum priorityEnum, TraceContext traceContext) {
        this(applyFunction, priorityEnum, traceContext, null);
    }

    public SimpleDelegatingRunnable(ApplyFunction applyFunction, PriorityEnum priorityEnum, CountDownLatch countDownLatch) {
        this(applyFunction, priorityEnum, null, countDownLatch);
    }

    public SimpleDelegatingRunnable(ApplyFunction applyFunction, TraceContext traceContext, CountDownLatch countDownLatch) {
        this(applyFunction, null, traceContext, countDownLatch);
    }

    public SimpleDelegatingRunnable(ApplyFunction applyFunction, PriorityEnum priorityEnum, TraceContext traceContext,
                                    CountDownLatch countDownLatch) {
        super(priorityEnum, countDownLatch, traceContext);
        assert applyFunction != null;
        this.applyFunction = applyFunction;
    }


    /**
     * 线程真正执行的业务方法，无返回值
     */
    @Override
    public void realRun() {
        applyFunction.apply();
    }
}
