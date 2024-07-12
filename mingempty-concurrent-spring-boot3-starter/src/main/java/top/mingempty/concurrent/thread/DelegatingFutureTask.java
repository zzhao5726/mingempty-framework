package top.mingempty.concurrent.thread;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import lombok.Getter;
import top.mingempty.concurrent.model.enums.PriorityEnum;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * FutureTask的委托实现类
 *
 * @author zzhao
 */
@Getter
public class DelegatingFutureTask<T>
        extends FutureTask<T>
        implements Comparable<DelegatingFutureTask<T>> {

    /**
     * 线程比较值
     */
    private final Integer priority;

    public DelegatingFutureTask(Callable<T> callable) {
        super(AbstractDelegatingCallable.delegatingCallable(callable));
        Integer priority = PriorityEnum.D.getPriority();
        if (callable instanceof TtlCallable) {
            Callable<T> callable1 = ((TtlCallable<T>) callable).getCallable();
            if (callable1 instanceof AbstractDelegatingCallable) {
                priority = ((AbstractDelegatingCallable<T>) callable1).getPriority();
            }
        } else if (callable instanceof AbstractDelegatingCallable) {
            priority = ((AbstractDelegatingCallable<T>) callable).getPriority();
        }
        this.priority = priority;
    }

    public DelegatingFutureTask(Runnable runnable, T result) {
        super(AbstractDelegatingRunnable.delegatingRunnable(runnable), result);
        Integer priority = PriorityEnum.D.getPriority();
        if (runnable instanceof TtlRunnable) {
            Runnable runnable1 = ((TtlRunnable) runnable).getRunnable();
            if (runnable1 instanceof AbstractDelegatingRunnable) {
                priority = ((AbstractDelegatingRunnable) runnable1).getPriority();
            }
        } else if (runnable instanceof AbstractDelegatingRunnable) {
            priority = ((AbstractDelegatingRunnable) runnable).getPriority();
        }
        this.priority = priority;
    }

    public DelegatingFutureTask(Callable<T> callable, PriorityEnum priorityEnum) {
        super(AbstractDelegatingCallable.delegatingCallable(callable, priorityEnum));
        this.priority = priorityEnum == null ? PriorityEnum.D.getPriority() : priorityEnum.getPriority();
    }

    public DelegatingFutureTask(Runnable runnable, T result, PriorityEnum priorityEnum) {
        super(AbstractDelegatingRunnable.delegatingRunnable(runnable, priorityEnum), result);
        this.priority = priorityEnum == null ? PriorityEnum.D.getPriority() : priorityEnum.getPriority();
    }

    @Override
    public int compareTo(DelegatingFutureTask<T> delegatingFutureTask) {
        return this.getPriority().compareTo(delegatingFutureTask.getPriority());
    }
}
