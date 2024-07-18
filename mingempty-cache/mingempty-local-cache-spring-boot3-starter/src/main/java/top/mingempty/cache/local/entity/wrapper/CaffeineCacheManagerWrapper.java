package top.mingempty.cache.local.entity.wrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import top.mingempty.cache.commons.entity.wrapper.CacheManagerWrapper;
import top.mingempty.cache.local.aspect.LocalCacheAspect;

import java.util.Map;

/**
 * CaffeineCacheManager路由类
 *
 * @author zzhao
 */
@Slf4j
public class CaffeineCacheManagerWrapper extends CacheManagerWrapper<CaffeineCacheManager> {

    public CaffeineCacheManagerWrapper(String defaultTargetName, Map<String, CaffeineCacheManager> targetRouter) {
        super(defaultTargetName, targetRouter);
    }

    public CaffeineCacheManagerWrapper(String defaultTargetName, Map<String, CaffeineCacheManager> targetRouter, boolean lenientFallback) {
        super(defaultTargetName, targetRouter, lenientFallback);
    }

    /**
     * 检索查找路由方式
     */
    @Override
    protected String determineCurrentLookupKey() {
        return LocalCacheAspect.acquireCacheName();
    }
}
