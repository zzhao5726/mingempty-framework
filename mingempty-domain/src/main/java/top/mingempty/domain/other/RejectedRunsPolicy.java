package top.mingempty.domain.other;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池拒绝策略，不管什么原因拒绝，直接以当前线程执行任务
 *
 * @author zzhao
 */
@Slf4j
public class RejectedRunsPolicy implements RejectedExecutionHandler {
    public final static RejectedRunsPolicy INSTANCE = new RejectedRunsPolicy();

    /**
     * 重写拒绝策略
     *
     * @param r 当前提交任务的线程
     * @param e 线程池对象
     */
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (e.isShutdown()) {
            log.warn("Thread Pool is ShutDown!");
        } else if (!e.isShutdown()) {
            String content = String.format("Thread Pool is EXHAUSTED!" +
                            "Pool Size: %d,(active: %d,core :%d,max :%d,largest :%d),Task:%d(completed :%d)," +
                            "Executor status:(isShutdown: %s, isTerminated: %s, isTerminating: %s)"
                    , e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(), e.getLargestPoolSize()
                    , e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(), e.isTerminating());
            log.warn(content);
        }

        r.run();
    }
}
