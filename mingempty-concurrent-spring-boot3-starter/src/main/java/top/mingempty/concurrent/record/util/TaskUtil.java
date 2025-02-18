package top.mingempty.concurrent.record.util;

import lombok.extern.slf4j.Slf4j;
import top.mingempty.concurrent.exception.ConcurrentException;
import top.mingempty.concurrent.record.service.TaskService;
import top.mingempty.concurrent.thread.AbstractDelegatingCallable;
import top.mingempty.concurrent.thread.AbstractDelegatingContext;
import top.mingempty.concurrent.thread.AbstractDelegatingRunnable;
import top.mingempty.util.SpringContextUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

@Slf4j
public class TaskUtil {


    public static void record(Runnable runnable, String poolName) {
        AbstractDelegatingRunnable abstractDelegatingRunnable
                = AbstractDelegatingRunnable.gainDelegatingRunnable(runnable);
        if (abstractDelegatingRunnable != null
                && abstractDelegatingRunnable.isEnableRecord()) {
            recordT(abstractDelegatingRunnable, poolName);
        }
    }


    public static <T> void record(Callable<T> callable, String poolName) {
        AbstractDelegatingCallable<T> tAbstractDelegatingCallable
                = AbstractDelegatingCallable.gainDelegatingCallable(callable);
        if (tAbstractDelegatingCallable != null
                && tAbstractDelegatingCallable.isEnableRecord()) {
            recordT(tAbstractDelegatingCallable, poolName);
        }
    }


    public static <T extends AbstractDelegatingContext<V>, V> void recordT(T contex, String poolName) {
        try {
            TaskService taskService = SpringContextUtil.gainBean(TaskService.class);
            taskService.record(contex, poolName);
        } catch (Exception e) {
            log.error("记录线程池提交任务异常", e);
        }
    }

    public static void complete(String taskId) {
        try {
            TaskService taskService = SpringContextUtil.gainBean(TaskService.class);
            taskService.complete(taskId);
        } catch (Exception e) {
            log.error("线程池任务任务完成修改记录异常", e);
        }
    }

    public static void submit(Object object, String poolName) {
        ExecutorService executorService
                = SpringContextUtil.gainBean("poolRouterExecutorService", ExecutorService.class);
        if (object instanceof AbstractDelegatingRunnable abstractDelegatingRunnable) {
            abstractDelegatingRunnable.setInstanceName(poolName);
            executorService.submit(abstractDelegatingRunnable);
        } else if (object instanceof AbstractDelegatingCallable<?> abstractDelegatingCallable) {
            abstractDelegatingCallable.setInstanceName(poolName);
            executorService.submit(abstractDelegatingCallable);
        } else {
            throw new ConcurrentException("concurrent-0000000003");
        }
    }
}
