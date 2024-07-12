package top.mingempty.concurrent.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.ObjectUtils;
import top.mingempty.concurrent.model.ConcurrentResult;
import top.mingempty.concurrent.model.ExecutorServiceThreadLocal;
import top.mingempty.concurrent.model.ThreadPoolProperties;
import top.mingempty.domain.other.AbstractRouter;
import top.mingempty.domain.other.GlobalConstant;

import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * 线程池代理类
 *
 * @author :zzhao
 * @date 2023/8/2 11:42
 */
@Slf4j
public class PoolRouterExecutorService extends AbstractRouter<ExecutorService> implements DisposableBean,
        ExecutorService {

    /**
     * 非线程池关闭时，等待任务执行时间（默认为一分钟）
     */
    private final Long awaitTermination;

    /**
     * 非线程池关闭时，等待任务执行时间单位（默认为一分钟）
     */
    private final TimeUnit awaitTerminationTimeUnit;

    /**
     * 配置文件
     */
    private final ThreadPoolProperties threadPoolProperties;

    public PoolRouterExecutorService(ThreadPoolProperties threadPoolProperties, String defaultTargetName,
                                     Map<String, ExecutorService> targetRouter) {
        super(defaultTargetName, targetRouter);
        this.threadPoolProperties = threadPoolProperties;
        this.awaitTermination = ObjectUtils.isEmpty(threadPoolProperties.getAwaitTermination())
                ? 1L : threadPoolProperties.getAwaitTermination();
        this.awaitTerminationTimeUnit = ObjectUtils.isEmpty(threadPoolProperties.getAwaitTerminationTimeUnit())
                ? TimeUnit.MINUTES : threadPoolProperties.getAwaitTerminationTimeUnit();
    }

    /**
     * Initiates an orderly shutdown in which previously submitted
     * tasks are executed, but no new tasks will be accepted.
     * Invocation has no additional effect if already shut down.
     *
     * <p>This method does not wait for previously submitted tasks to
     * complete execution.  Use {@link #awaitTermination awaitTermination}
     * to do that.
     *
     * @throws SecurityException if a security manager exists and
     *                           shutting down this ExecutorService may manipulate
     *                           threads that the caller is not permitted to modify
     *                           because it does not hold {@link
     *                           RuntimePermission}{@code ("modifyThread")},
     *                           or the security manager's {@code checkAccess} method
     *                           denies access.
     */
    @Override
    public void shutdown() {
        this.getResolvedRouters()
                .parallelStream()
                .forEach(ExecutorService::shutdown);
    }

    /**
     * Attempts to stop all actively executing tasks, halts the
     * processing of waiting tasks, and returns a list of the tasks
     * that were awaiting execution.
     *
     * <p>This method does not wait for actively executing tasks to
     * terminate.  Use {@link #awaitTermination awaitTermination} to
     * do that.
     *
     * <p>There are no guarantees beyond best-effort attempts to stop
     * processing actively executing tasks.  For example, typical
     * implementations will cancel via {@link Thread#interrupt}, so any
     * task that fails to respond to interrupts may never terminate.
     *
     * @return list of tasks that never commenced execution
     * @throws SecurityException if a security manager exists and
     *                           shutting down this ExecutorService may manipulate
     *                           threads that the caller is not permitted to modify
     *                           because it does not hold {@link
     *                           RuntimePermission}{@code ("modifyThread")},
     *                           or the security manager's {@code checkAccess} method
     *                           denies access.
     */
    @Override
    public List<Runnable> shutdownNow() {
        return this.getResolvedRouters()
                .parallelStream()
                .map(ExecutorService::shutdownNow)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * Returns {@code true} if this executor has been shut down.
     *
     * @return {@code true} if this executor has been shut down
     */
    @Override
    public boolean isShutdown() {
        return this.getResolvedRouters()
                .parallelStream()
                .allMatch(ExecutorService::isShutdown);
    }

    /**
     * Returns {@code true} if all tasks have completed following shut down.
     * Note that {@code isTerminated} is never {@code true} unless
     * either {@code shutdown} or {@code shutdownNow} was called first.
     *
     * @return {@code true} if all tasks have completed following shut down
     */
    @Override
    public boolean isTerminated() {
        return this.getResolvedRouters()
                .parallelStream()
                .allMatch(ExecutorService::isTerminated);
    }

    /**
     * Blocks until all tasks have completed execution after a shutdown
     * request, or the timeout occurs, or the current thread is
     * interrupted, whichever happens first.
     *
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @return {@code true} if this executor terminated and
     * {@code false} if the timeout elapsed before termination
     * @throws InterruptedException if interrupted while waiting
     */
    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return this.getResolvedRouter()
                .entrySet()
                .parallelStream()
                .allMatch(entry -> {
                    try {
                        if (GlobalConstant.DEFAULT_INSTANCE_NAME.equals(entry.getKey())) {
                            return getResolvedDefaultRouter().awaitTermination(timeout, unit);
                        } else {
                            ThreadPoolProperties threadPoolPropertiesByOther =
                                    this.threadPoolProperties.getOtherThreadPoolProperties().get(entry.getKey());
                            long awaitTerminationByOther =
                                    ObjectUtils.isEmpty(threadPoolPropertiesByOther.getAwaitTermination())
                                            ? 1L : threadPoolPropertiesByOther.getAwaitTermination();
                            TimeUnit awaitTerminationTimeUnitByOther =
                                    ObjectUtils.isEmpty(threadPoolPropertiesByOther.getAwaitTerminationTimeUnit())
                                            ? TimeUnit.MINUTES : threadPoolPropertiesByOther.getAwaitTerminationTimeUnit();
                            return entry.getValue().awaitTermination(awaitTerminationByOther, awaitTerminationTimeUnitByOther);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    /**
     * Submits a value-returning task for execution and returns a
     * Future representing the pending results of the task. The
     * Future's {@code get} method will return the task's result upon
     * successful completion.
     *
     * <p>
     * If you would like to immediately block waiting
     * for a task, you can use constructions of the form
     * {@code result = exec.submit(aCallable).get();}
     *
     * <p>Note: The {@link Executors} class includes a set of methods
     * that can convert some other common closure-like objects,
     * for example, {@link PrivilegedAction} to
     * {@link Callable} form so they can be submitted.
     *
     * @param task the task to submit
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be
     *                                    scheduled for execution
     * @throws NullPointerException       if the task is null
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return this.determineTargetRouter().submit(task);
    }

    /**
     * Submits a Runnable task for execution and returns a Future
     * representing that task. The Future's {@code get} method will
     * return the given result upon successful completion.
     *
     * @param task   the task to submit
     * @param result the result to return
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be
     *                                    scheduled for execution
     * @throws NullPointerException       if the task is null
     */
    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return this.determineTargetRouter().submit(task, result);
    }

    /**
     * Submits a Runnable task for execution and returns a Future
     * representing that task. The Future's {@code get} method will
     * return {@code null} upon <em>successful</em> completion.
     *
     * @param task the task to submit
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be
     *                                    scheduled for execution
     * @throws NullPointerException       if the task is null
     */
    @Override
    public Future<Void> submit(Runnable task) {
        return (Future<Void>) this.determineTargetRouter().submit(task);
    }

    /**
     * Executes the given tasks, returning a list of Futures holding
     * their status and results when all complete.
     * {@link Future#isDone} is {@code true} for each
     * element of the returned list.
     * Note that a <em>completed</em> task could have
     * terminated either normally or by throwing an exception.
     * The results of this method are undefined if the given
     * collection is modified while this operation is in progress.
     *
     * @param tasks the collection of tasks
     * @return a list of Futures representing the tasks, in the same
     * sequential order as produced by the iterator for the
     * given task list, each of which has completed
     * @throws InterruptedException       if interrupted while waiting, in
     *                                    which case unfinished tasks are cancelled
     * @throws NullPointerException       if tasks or any of its elements are {@code null}
     * @throws RejectedExecutionException if any task cannot be
     *                                    scheduled for execution
     */
    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return this.determineTargetRouter().invokeAll(tasks);
    }

    /**
     * Executes the given tasks, returning a list of Futures holding
     * their status and results
     * when all complete or the timeout expires, whichever happens first.
     * {@link Future#isDone} is {@code true} for each
     * element of the returned list.
     * Upon return, tasks that have not completed are cancelled.
     * Note that a <em>completed</em> task could have
     * terminated either normally or by throwing an exception.
     * The results of this method are undefined if the given
     * collection is modified while this operation is in progress.
     *
     * @param tasks   the collection of tasks
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @return a list of Futures representing the tasks, in the same
     * sequential order as produced by the iterator for the
     * given task list. If the operation did not time out,
     * each task will have completed. If it did time out, some
     * of these tasks will not have completed.
     * @throws InterruptedException       if interrupted while waiting, in
     *                                    which case unfinished tasks are cancelled
     * @throws NullPointerException       if tasks, any of its elements, or
     *                                    unit are {@code null}
     * @throws RejectedExecutionException if any task cannot be scheduled
     *                                    for execution
     */
    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return this.determineTargetRouter().invokeAll(tasks, timeout, unit);
    }

    /**
     * Executes the given tasks, returning the result
     * of one that has completed successfully (i.e., without throwing
     * an exception), if any do. Upon normal or exceptional return,
     * tasks that have not completed are cancelled.
     * The results of this method are undefined if the given
     * collection is modified while this operation is in progress.
     *
     * @param tasks the collection of tasks
     * @return the result returned by one of the tasks
     * @throws InterruptedException       if interrupted while waiting
     * @throws NullPointerException       if tasks or any element task
     *                                    subject to execution is {@code null}
     * @throws IllegalArgumentException   if tasks is empty
     * @throws ExecutionException         if no task successfully completes
     * @throws RejectedExecutionException if tasks cannot be scheduled
     *                                    for execution
     */
    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return this.determineTargetRouter().invokeAny(tasks);
    }

    /**
     * Executes the given tasks, returning the result
     * of one that has completed successfully (i.e., without throwing
     * an exception), if any do before the given timeout elapses.
     * Upon normal or exceptional return, tasks that have not
     * completed are cancelled.
     * The results of this method are undefined if the given
     * collection is modified while this operation is in progress.
     *
     * @param tasks   the collection of tasks
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @return the result returned by one of the tasks
     * @throws InterruptedException       if interrupted while waiting
     * @throws NullPointerException       if tasks, or unit, or any element
     *                                    task subject to execution is {@code null}
     * @throws TimeoutException           if the given timeout elapses before
     *                                    any task successfully completes
     * @throws ExecutionException         if no task successfully completes
     * @throws RejectedExecutionException if tasks cannot be scheduled
     *                                    for execution
     */
    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.determineTargetRouter().invokeAny(tasks, timeout, unit);
    }

    /**
     * Executes the given command at some time in the future.  The command
     * may execute in a new thread, in a pooled thread, or in the calling
     * thread, at the discretion of the {@code Executor} implementation.
     *
     * @param command the runnable task
     * @throws RejectedExecutionException if this task cannot be
     *                                    accepted for execution
     * @throws NullPointerException       if command is null
     */
    @Override
    public void execute(Runnable command) {
        this.determineTargetRouter().execute(command);
    }

    @Override
    public void destroy() throws Exception {
        try {
            log.info("bontal thread pool shutdown now。。。。。。");
            //关闭线程池，已提交的任务继续执行，不再接收新的任务
            this.shutdown();
            //等待所有的任务都结束（实时判断是否全完成），若所有任务都已完成，则返回true，若超时未完成，则返回false
            if (!this.awaitTermination(awaitTermination, awaitTerminationTimeUnit)) {
                //超时的时候向线程池中所有的线程发出中断(interrupted)。
                this.shutdownNow();
            }
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            log.error("awaitTermination interrupted:", e);
            this.shutdownNow();
        }
    }


    /**
     * 将线程池批量执行结果封装起来
     *
     * @param futureList 线程池任务列表
     * @param <T>        线程池执行返回结果泛型
     * @return 线程池批量行结果记录
     */
    public static <T> Map<Future<T>, ConcurrentResult<T>> concurrentResult(Collection<Future<T>> futureList) {
        if (futureList == null
                || futureList.isEmpty()) {
            return Collections.emptyMap();
        }
        Iterator<Future<T>> iterator = futureList.iterator();

        Map<Future<T>, ConcurrentResult<T>> concurrentResultMap = new HashMap<>(futureList.size());

        while (iterator.hasNext()) {
            Future<T> tFuture = iterator.next();
            while (true) {
                //CPU高速轮询：每个future都并发轮循，判断完成状态然后获取结果，这一行，是本实现方案的精髓所在。即有10个future在高速轮询，完成一个future的获取结果，就关闭一个轮询
                //获取future成功完成状态，如果想要限制每个任务的超时时间，取消本行的状态判断+future.get(1000*1, TimeUnit.MILLISECONDS)+catch超时异常使用即可。
                if (tFuture.isDone() && !tFuture.isCancelled()) {
                    ConcurrentResult<T> tPoolResult = new ConcurrentResult<>();
                    try {
                        //获取结果
                        T t = tFuture.get();
                        tPoolResult.setData(t);
                    } catch (Exception e) {
                        tPoolResult.setException(e);
                    }
                    concurrentResultMap.put(tFuture, tPoolResult);
                    //当前future获取结果完毕，跳出while
                    break;
                } else {
                    //每次轮询休息1毫秒（CPU纳秒级），避免CPU高速轮循耗空CPU---》新手别忘记这个
                    try {
                        TimeUnit.MILLISECONDS.sleep(1L);
                    } catch (Exception e) {
                        log.warn("防止线程空转休息时异常", e);
                    }
                }
            }
        }
        return concurrentResultMap;
    }

    /**
     * 检索查找路由方式
     */
    @Override
    protected String determineCurrentLookupKey() {
        try {
            return ExecutorServiceThreadLocal.gain();
        } finally {
            ExecutorServiceThreadLocal.remove();
        }
    }
}
