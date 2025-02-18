package top.mingempty.event.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import top.mingempty.concurrent.factory.ExecutorServiceFactory;
import top.mingempty.event.MeApplicationEventMulticaster;
import top.mingempty.event.model.MeEventThreadPoolConfig;
import top.mingempty.event.publisher.MeEventPublisher;

import java.util.concurrent.ExecutorService;

/**
 * 事件自动注入判断
 *
 * @author zzhao
 */
@EnableAsync
@EnableConfigurationProperties(MeEventThreadPoolConfig.class)
@ConditionalOnProperty(prefix = "me.event", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MeEventAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "me.event.pool", name = "enabled", havingValue = "true", matchIfMissing = true)
    public ExecutorService eventExecutorService(MeEventThreadPoolConfig meEventThreadPoolConfig) {
        return ExecutorServiceFactory.ttlExecutorService(meEventThreadPoolConfig.getPool());
    }

    @Bean
    public MeEventPublisher meEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new MeEventPublisher(applicationEventPublisher);
    }


    @Bean(value = {"applicationEventMulticaster", "meApplicationEventMulticaster"})
    public MeApplicationEventMulticaster meApplicationEventMulticaster(
            @Autowired(required = false) @Qualifier("eventExecutorService") ExecutorService eventExecutorService) {
        return new MeApplicationEventMulticaster(eventExecutorService);
    }


}
