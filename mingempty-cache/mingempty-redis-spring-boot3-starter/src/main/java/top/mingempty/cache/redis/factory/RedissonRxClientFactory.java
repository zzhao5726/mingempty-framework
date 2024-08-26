package top.mingempty.cache.redis.factory;

import lombok.AllArgsConstructor;
import org.redisson.api.RedissonRxClient;
import top.mingempty.builder.WrapperBuilder;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.RedisProperties;
import top.mingempty.cache.redis.entity.wapper.RedissonClientWrapper;
import top.mingempty.cache.redis.entity.wapper.RedissonRxClientWrapper;
import top.mingempty.domain.other.GlobalConstant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RedissonRxClient 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedissonRxClientFactory
        implements WrapperBuilder<RedissonRxClientWrapper, RedissonRxClient, RedisProperties> {

    private final RedisCacheProperties redisCacheProperties;
    private final RedissonClientWrapper redissonClientWrapper;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedissonRxClientWrapper build() {
        Map<String, RedissonRxClient> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, buildToSub(GlobalConstant.DEFAULT_INSTANCE_NAME, redisCacheProperties.getRedis()));
        redisCacheProperties.getMore()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(),
                        buildToSub(entry.getKey(), entry.getValue())));
        return new RedissonRxClientWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }

    /**
     * 构建
     *
     * @param instanceName
     * @param properties
     * @return 被构建的对象
     */
    @Override
    public RedissonRxClient buildToSub(String instanceName, RedisProperties properties) {
        return redissonClientWrapper.getResolvedRouter(instanceName).rxJava();
    }
}
