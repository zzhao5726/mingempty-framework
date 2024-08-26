package top.mingempty.cache.redis.entity.wapper;

import org.springframework.data.redis.cache.RedisCacheConfiguration;
import top.mingempty.cache.redis.aspect.RedisCacheAspect;
import top.mingempty.domain.other.AbstractRouter;

import java.util.Map;

/**
 * RedisCacheConfiguration路由封装
 *
 * @author zzhao
 * @date 2023/3/12 11:12
 */
public class RedisCacheConfigurationWrapper extends AbstractRouter<RedisCacheConfiguration> {


    public RedisCacheConfigurationWrapper(String defaultTargetName, Map<String, RedisCacheConfiguration> targetRouter) {
        super(defaultTargetName, targetRouter);
    }

    public RedisCacheConfigurationWrapper(String defaultTargetName, Map<String, RedisCacheConfiguration> targetRouter, boolean lenientFallback) {
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
