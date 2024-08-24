package top.mingempty.cache.local.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import top.mingempty.cache.local.api.LocalCacheApi;
import top.mingempty.cache.local.api.impl.LocalCacheApiImpl;
import top.mingempty.cache.local.entity.LocalCacheProperties;
import top.mingempty.cache.local.entity.wrapper.CaffeineCacheManagerWrapper;
import top.mingempty.cache.local.factory.CaffeineCacheManagerFactory;

import java.util.Map;

/**
 * 本地缓存配置类
 *
 * @author zzhao
 */
@Slf4j
@EnableCaching
@AllArgsConstructor
@EnableConfigurationProperties(value = {LocalCacheProperties.class})
@ConditionalOnProperty(prefix = "me.cache", name = "enabled-local-cache", havingValue = "true", matchIfMissing = true)
public class LocalCacheConfig {

    private final LocalCacheProperties localCacheProperties;


    @Bean
    @ConditionalOnClass(name = {"com.github.benmanes.caffeine.cache.Caffeine"})
    @ConditionalOnProperty(prefix = "me.cache", name = "enabled-caffeine", havingValue = "true", matchIfMissing = true)
    public CaffeineCacheManagerFactory caffeineCacheManagerFactory(Map<String, CacheLoader<Object, Object>> cacheLoaderMap) {
        return new CaffeineCacheManagerFactory(localCacheProperties, cacheLoaderMap);
    }

    @Bean
    @Primary
    @ConditionalOnBean(value = {CaffeineCacheManagerFactory.class})
    public CaffeineCacheManagerWrapper caffeineCacheManagerWrapper(CaffeineCacheManagerFactory caffeineCacheManagerFactory) {
        return caffeineCacheManagerFactory.build();
    }
    /*=================CacheManager👆  localCacheApi👇================================*/

    @Bean
    @ConditionalOnBean(value = {CaffeineCacheManagerWrapper.class}, name = "caffeineCacheManagerWrapper")
    public LocalCacheApi localCacheApi(CaffeineCacheManagerWrapper caffeineCacheManagerWrapper) {
        return new LocalCacheApiImpl(caffeineCacheManagerWrapper);
    }

}
