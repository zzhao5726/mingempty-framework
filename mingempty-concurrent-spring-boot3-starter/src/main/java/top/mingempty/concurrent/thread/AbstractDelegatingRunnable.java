package top.mingempty.concurrent.thread;


import com.alibaba.ttl.TtlRunnable;
import top.mingempty.commons.trace.TraceContext;
import top.mingempty.concurrent.model.enums.PriorityEnum;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 线程Runnable抽象的委托实现类
 *
 * @author zzhao
 * @date 2023/8/2 11:26
 */
public abstract class AbstractDelegatingRunnable
        extends AbstractDelegatingContext<Void>
        implements Runnable {


    public AbstractDelegatingRunnable() {
    }

    public AbstractDelegatingRunnable(CountDownLatch countDownLatch) {
        super(countDownLatch);
    }

    public AbstractDelegatingRunnable(CountDownLatch countDownLatch, PriorityEnum priorityEnum) {
        super(countDownLatch, priorityEnum);
    }

    public AbstractDelegatingRunnable(CountDownLatch countDownLatch, PriorityEnum priorityEnum, TraceContext traceContext) {
        super(countDownLatch, priorityEnum, traceContext);
    }

    public AbstractDelegatingRunnable(CountDownLatch countDownLatch, TraceContext traceContext) {
        super(countDownLatch, traceContext);
    }

    public AbstractDelegatingRunnable(Map<String, Object> params) {
        super(params);
    }

    public AbstractDelegatingRunnable(Map<String, Object> params, CountDownLatch countDownLatch) {
        super(params, countDownLatch);
    }

    public AbstractDelegatingRunnable(Map<String, Object> params, CountDownLatch countDownLatch, TraceContext traceContext) {
        super(params, countDownLatch, traceContext);
    }

    public AbstractDelegatingRunnable(Map<String, Object> params, PriorityEnum priorityEnum) {
        super(params, priorityEnum);
    }

    public AbstractDelegatingRunnable(Map<String, Object> params, PriorityEnum priorityEnum, CountDownLatch countDownLatch) {
        super(params, priorityEnum, countDownLatch);
    }

    public AbstractDelegatingRunnable(Map<String, Object> params, PriorityEnum priorityEnum, CountDownLatch countDownLatch, TraceContext traceContext) {
        super(params, priorityEnum, countDownLatch, traceContext);
    }

    public AbstractDelegatingRunnable(Map<String, Object> params, PriorityEnum priorityEnum, TraceContext traceContext) {
        super(params, priorityEnum, traceContext);
    }

    public AbstractDelegatingRunnable(Map<String, Object> params, TraceContext traceContext) {
        super(params, traceContext);
    }

    public AbstractDelegatingRunnable(PriorityEnum priorityEnum) {
        super(priorityEnum);
    }

    public AbstractDelegatingRunnable(PriorityEnum priorityEnum, CountDownLatch countDownLatch) {
        super(priorityEnum, countDownLatch);
    }

    public AbstractDelegatingRunnable(PriorityEnum priorityEnum, CountDownLatch countDownLatch, TraceContext traceContext) {
        super(priorityEnum, countDownLatch, traceContext);
    }

    public AbstractDelegatingRunnable(PriorityEnum priorityEnum, TraceContext traceContext) {
        super(priorityEnum, traceContext);
    }

    public AbstractDelegatingRunnable(TraceContext traceContext) {
        super(traceContext);
    }

    @Override
    public final void run() {
        this.abstractRun();
    }

    /**
     * 线程真正执行的业务方法
     */
    @Override
    public Void realRun() throws Exception {
        runReal();
        return null;
    }

    /**
     * 线程真正执行的业务方法，无返回值
     */
    public abstract void runReal();


    /**
     * 线程池Runnable转换为封装的线程池执行类
     *
     * @param runnable 要执行的Runnable
     * @return 返回委托的Runnable
     */
    public static Runnable delegatingRunnable(Runnable runnable) {
        return delegatingRunnable(runnable, PriorityEnum.D);
    }


    /**
     * 线程池Runnable转换为封装的线程池执行类
     *
     * @param runnable     要执行的Runnable
     * @param priorityEnum 线程优先级
     * @return 返回委托的Runnable
     */
    public static Runnable delegatingRunnable(Runnable runnable, PriorityEnum priorityEnum) {
        if (runnable instanceof TtlRunnable) {
            Runnable runnable1 = ((TtlRunnable) runnable).getRunnable();
            if (runnable1 instanceof AbstractDelegatingRunnable) {
                return runnable;
            }
        } else if (runnable instanceof AbstractDelegatingRunnable) {
            return runnable;
        }
        //转换为MeDelegatingRunnable
        return new AbstractDelegatingRunnable(priorityEnum) {
            /**
             * 线程真正执行的业务方法，无返回值
             */
            @Override
            public void runReal() {
                runnable.run();
            }
        };
    }
}
