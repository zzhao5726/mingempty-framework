package top.mingempty.concurrent.config;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.Assert;
import top.mingempty.concurrent.model.ConcurrentConstant;
import top.mingempty.concurrent.model.ThreadPoolProperties;
import top.mingempty.concurrent.model.enums.RejectedExecutionHandlerEnum;
import top.mingempty.concurrent.model.enums.WorkQueueEnum;
import top.mingempty.concurrent.pool.DelegatingThreadPoolExecutor;
import top.mingempty.concurrent.pool.PoolRouterExecutorService;
import top.mingempty.concurrent.pool.VirtualExecutorService;
import top.mingempty.concurrent.thread.ThreadFactoryBuilder;
import top.mingempty.config.MeConfiguration;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.util.SpringContextUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 并发配置类
 *
 * @author zzhao
 */
@Slf4j
@Configuration
@AutoConfigureAfter(value = {MeConfiguration.class})
@EnableConfigurationProperties(ThreadPoolProperties.class)
@ConditionalOnProperty(prefix = "me.concurrent", name = "enabled", havingValue = "true")
public class ConcurrentConfig {

    /**
     * 构建封装后的虚拟线程执行器
     *
     * @param threadPoolProperties 线程池配置
     * @return 虚拟线程执行器
     */
    @Bean
    @Primary
    public VirtualExecutorService virtualExecutorService(ThreadPoolProperties threadPoolProperties) {
        return new VirtualExecutorService(threadPoolProperties.getAwaitTermination(),
                threadPoolProperties.getAwaitTerminationTimeUnit());
    }


    /**
     * 构建封装后的线程池执行器
     *
     * @param threadPoolProperties 线程池配置
     * @return 虚拟线程执行器
     */
    @Bean
    @Primary
    public PoolRouterExecutorService poolRouterExecutorService(ThreadPoolProperties threadPoolProperties) {
        Map<String, ExecutorService> executorServiceMap = new HashMap<>(2);
        ExecutorService ttlExecutorService
                = TtlExecutors.getTtlExecutorService(threadPoolExecutor(threadPoolProperties));
        executorServiceMap.put(GlobalConstant.DEFAULT_INSTANCE_NAME, ttlExecutorService);
        if (MapUtil.isNotEmpty(threadPoolProperties.getOtherThreadPoolProperties())) {
            threadPoolProperties.getOtherThreadPoolProperties()
                    .forEach((key, value) -> executorServiceMap.put(key,
                            TtlExecutors.getTtlExecutorService(threadPoolExecutor(key, value))));
        }
        PoolRouterExecutorService poolRouterExecutorService = new PoolRouterExecutorService(threadPoolProperties, GlobalConstant.DEFAULT_INSTANCE_NAME, executorServiceMap);
        //将每一个线程吃执行服务注册为Bean
        poolRouterExecutorService.getResolvedRouter()
                .forEach((key, value)
                        -> SpringContextUtil
                        .registerBean(key.concat("ExecutorService"), ExecutorService.class, () -> value));
        return poolRouterExecutorService;
    }


    private ThreadPoolExecutor threadPoolExecutor(ThreadPoolProperties threadPoolProperties) {
        return threadPoolExecutor(GlobalConstant.DEFAULT_INSTANCE_NAME, threadPoolProperties);
    }

    private ThreadPoolExecutor threadPoolExecutor(String bussType, ThreadPoolProperties threadPoolProperties) {
        Assert.hasText(bussType, "业务类型不能为空");
        Assert.notNull(threadPoolProperties.getCorePoolSize(), "创建线程池时，核心线程数不能为空");
        Assert.notNull(threadPoolProperties.getMaximumPoolSize(), "创建线程池时，非核心线程数不能为空");
        Assert.notNull(threadPoolProperties.getKeepAliveTime(), "创建线程池时，非核心线程最大存活时间不能为空");
        Assert.notNull(threadPoolProperties.getKeepAliveTimeUnit(), "创建线程池时，非核心线程最大存活时间单位不能为空");
        Assert.notNull(threadPoolProperties.getMaxPoolSize(), "创建线程池时，线程池队列容量不能为空");
        if (StrUtil.isEmpty(threadPoolProperties.getThreadName())) {
            threadPoolProperties.setThreadName(bussType.concat("-").concat(ConcurrentConstant.DEFAULT_THREAD_POOL_NAME));
        }
        BlockingQueue<Runnable> workQueue = WorkQueueEnum
                .blockingQueue(threadPoolProperties.getWorkQueueEnum(),
                        threadPoolProperties.getMaxPoolSize());

        ThreadFactory threadFactory = ThreadFactoryBuilder.create()
                .setNamePrefix(threadPoolProperties.getThreadName())
                .setDaemon(threadPoolProperties.getDaemon())
                .setPriority(threadPoolProperties.getPriority())
                .build();

        RejectedExecutionHandler rejectedExecutionHandler
                = RejectedExecutionHandlerEnum
                .rejectedExecutionHandler(threadPoolProperties.getRejectedExecutionHandlerEnum());

        Integer corePoolSize = threadPoolProperties.getCorePoolSize();
        Integer maximumPoolSize = threadPoolProperties.getMaximumPoolSize();
        Long keepAliveTime = threadPoolProperties.getKeepAliveTime();
        TimeUnit unit = threadPoolProperties.getKeepAliveTimeUnit();

        return new DelegatingThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, unit, workQueue, threadFactory, rejectedExecutionHandler);
    }

}
