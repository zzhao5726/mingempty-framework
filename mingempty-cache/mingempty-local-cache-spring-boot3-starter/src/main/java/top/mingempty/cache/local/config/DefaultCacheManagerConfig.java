package top.mingempty.cache.local.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 当不存在CacheManager时自动注入一个默认的CacheManager
 *
 * @author zzhao
 */
@ConditionalOnMissingBean(value = CacheManager.class)
public class DefaultCacheManagerConfig {

    @Bean
    @Primary
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }
}
