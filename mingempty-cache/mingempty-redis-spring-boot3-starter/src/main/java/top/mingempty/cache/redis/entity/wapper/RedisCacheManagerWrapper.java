package top.mingempty.cache.redis.entity.wapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.RedisCacheManager;
import top.mingempty.cache.commons.entity.wrapper.CacheManagerWrapper;
import top.mingempty.cache.redis.aspect.RedisCacheAspect;

import java.util.Map;

/**
 * RedisCacheManager路由类
 *
 * @author zzhao
 */
@Slf4j
public class RedisCacheManagerWrapper extends CacheManagerWrapper<RedisCacheManager> {


    public RedisCacheManagerWrapper(String defaultTargetName, Map<String, RedisCacheManager> targetRouter) {
        super(defaultTargetName, targetRouter);
    }

    public RedisCacheManagerWrapper(String defaultTargetName, Map<String, RedisCacheManager> targetRouter, boolean lenientFallback) {
        super(defaultTargetName, targetRouter, lenientFallback);
    }

    /**
     * 检索查找路由方式
     */
    @Override
    protected String determineCurrentLookupKey() {
        return RedisCacheAspect.acquireCacheName();
    }

}
