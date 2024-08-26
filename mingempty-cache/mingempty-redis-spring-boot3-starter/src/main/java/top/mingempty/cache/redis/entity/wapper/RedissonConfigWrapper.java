package top.mingempty.cache.redis.entity.wapper;

import org.redisson.config.Config;
import top.mingempty.domain.other.AbstractRouter;

import java.util.Map;

/**
 * Config路由封装
 *
 * @author zzhao
 * @date 2023/3/12 11:12
 */
public class RedissonConfigWrapper extends AbstractRouter<Config> {


    public RedissonConfigWrapper(String defaultTargetName, Map<String, Config> targetRouter) {
        super(defaultTargetName, targetRouter);
    }

    public RedissonConfigWrapper(String defaultTargetName, Map<String, Config> targetRouter, boolean lenientFallback) {
        super(defaultTargetName, targetRouter, lenientFallback);
    }

    /**
     * 检索查找路由方式
     */
    @Override
    protected String determineCurrentLookupKey() {
        return "";
    }

}
