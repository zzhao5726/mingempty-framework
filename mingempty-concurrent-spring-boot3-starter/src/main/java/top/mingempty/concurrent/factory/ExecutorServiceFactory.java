package top.mingempty.concurrent.factory;

import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.threadpool.TtlExecutors;
import org.springframework.util.Assert;
import top.mingempty.commons.thread.ThreadFactoryBuilder;
import top.mingempty.concurrent.model.ConcurrentConstant;
import top.mingempty.concurrent.model.ThreadPoolConfig;
import top.mingempty.concurrent.model.enums.RejectedExecutionHandlerEnum;
import top.mingempty.concurrent.model.enums.WorkQueueEnum;
import top.mingempty.concurrent.pool.DelegatingThreadPoolExecutor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池创建工厂
 *
 * @author zzhao
 */
public class ExecutorServiceFactory {


    public static ExecutorService ttlExecutorService(ThreadPoolConfig threadPoolConfig) {
        return TtlExecutors.getTtlExecutorService(threadPoolExecutor(threadPoolConfig));
    }


    public static ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfig threadPoolConfig) {
        return threadPoolExecutor(threadPoolConfig.getName(), threadPoolConfig);
    }

    public static ThreadPoolExecutor threadPoolExecutor(String threadPoolName, ThreadPoolConfig threadPoolConfig) {
        Assert.hasText(threadPoolName, "线程池名称不可为空");
        Assert.notNull(threadPoolConfig.getCorePoolSize(), "创建线程池时，核心线程数不能为空");
        Assert.notNull(threadPoolConfig.getMaximumPoolSize(), "创建线程池时，非核心线程数不能为空");
        Assert.notNull(threadPoolConfig.getKeepAliveTime(), "创建线程池时，非核心线程最大存活时间不能为空");
        Assert.notNull(threadPoolConfig.getKeepAliveTimeUnit(), "创建线程池时，非核心线程最大存活时间单位不能为空");
        Assert.notNull(threadPoolConfig.getMaxPoolSize(), "创建线程池时，线程池队列容量不能为空");
        if (StrUtil.isEmpty(threadPoolConfig.getThreadName())) {
            threadPoolConfig.setThreadName(threadPoolName.concat("-").concat(ConcurrentConstant.DEFAULT_THREAD_POOL_NAME));
        }
        BlockingQueue<Runnable> workQueue = WorkQueueEnum
                .blockingQueue(threadPoolConfig.getWorkQueueEnum(),
                        threadPoolConfig.getMaxPoolSize());

        ThreadFactory threadFactory = ThreadFactoryBuilder.create()
                .setNamePrefix(threadPoolConfig.getThreadName())
                .setDaemon(threadPoolConfig.getDaemon())
                .setPriority(threadPoolConfig.getPriority())
                .build();

        RejectedExecutionHandler rejectedExecutionHandler
                = RejectedExecutionHandlerEnum
                .rejectedExecutionHandler(threadPoolConfig.getRejectedExecutionHandlerEnum());

        Integer corePoolSize = threadPoolConfig.getCorePoolSize();
        Integer maximumPoolSize = threadPoolConfig.getMaximumPoolSize();
        Long keepAliveTime = threadPoolConfig.getKeepAliveTime();
        TimeUnit unit = threadPoolConfig.getKeepAliveTimeUnit();

        return new DelegatingThreadPoolExecutor(threadPoolName, corePoolSize, maximumPoolSize,
                keepAliveTime, unit, workQueue, threadFactory, rejectedExecutionHandler);
    }
}
