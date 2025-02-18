package top.mingempty.concurrent.config;


import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import top.mingempty.concurrent.factory.ExecutorServiceFactory;
import top.mingempty.concurrent.model.ConcurrentConstant;
import top.mingempty.concurrent.model.ConcurrentProperties;
import top.mingempty.concurrent.model.ThreadPoolConfig;
import top.mingempty.concurrent.pool.PoolRouterExecutorService;
import top.mingempty.concurrent.pool.VirtualExecutorService;
import top.mingempty.config.MeSpringConfiguration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

/**
 * 并发配置类
 *
 * @author zzhao
 */
@Slf4j
@EnableAsync
@Configuration
@AutoConfigureAfter(value = {MeSpringConfiguration.class})
@EnableConfigurationProperties(ConcurrentProperties.class)
@ConditionalOnProperty(prefix = "me.concurrent", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ConcurrentConfiguration {

    /**
     * 构建封装后的虚拟线程执行器
     *
     * @param concurrentProperties 线程池配置
     * @return 虚拟线程执行器
     */
    @Bean
    public ExecutorService virtualExecutorService(ConcurrentProperties concurrentProperties) {
        return new VirtualExecutorService(concurrentProperties.getVirtualName(),
                concurrentProperties.getAwaitTermination(),
                concurrentProperties.getAwaitTerminationTimeUnit());
    }


    /**
     * 构建封装后的线程池执行器
     *
     * @param concurrentProperties 线程池配置
     * @return 虚拟线程执行器
     */
    @Primary
    @Bean(value = {"poolRouterExecutorService", "executorService"})
    public ExecutorService poolRouterExecutorService(ConcurrentProperties concurrentProperties) {
        if (!concurrentProperties.isOtherEnabled()) {
            ThreadPoolConfig threadPoolConfig = concurrentProperties.getDef();
            ExecutorService executorService = ExecutorServiceFactory.ttlExecutorService(threadPoolConfig);
            return doAfterPropertiesSet(concurrentProperties, new PoolRouterExecutorService(concurrentProperties, executorService));
        }
        List<ThreadPoolConfig> concurrentPropertiesOther = concurrentProperties.getOther();
        Map<String, ExecutorService> executorServiceMap = concurrentPropertiesOther
                .parallelStream()
                .collect(Collectors.toMap(ThreadPoolConfig::getName, ExecutorServiceFactory::ttlExecutorService));

        Preconditions.checkArgument(executorServiceMap.containsKey(concurrentProperties.getPrimary()),
                "executorService name must have primary");

        PoolRouterExecutorService poolRouterExecutorService
                = new PoolRouterExecutorService(concurrentProperties, executorServiceMap.get(concurrentProperties.getPrimary()));
        poolRouterExecutorService.putAll(executorServiceMap);
        executorServiceMap.clear();
        return doAfterPropertiesSet(concurrentProperties, poolRouterExecutorService);
    }

    /**
     * 做后置处理
     *
     * @param concurrentProperties
     * @param poolRouterExecutorService
     * @return
     */
    private PoolRouterExecutorService doAfterPropertiesSet(ConcurrentProperties concurrentProperties,
                                                           PoolRouterExecutorService poolRouterExecutorService) {
        poolRouterExecutorService.put(ConcurrentConstant.VIRTUAL_THREAD_EXECUTOR_NAME, virtualExecutorService(concurrentProperties));
        return poolRouterExecutorService;
    }
}
