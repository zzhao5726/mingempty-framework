package top.mingempty.cache.redis.factory;

import lombok.AllArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import top.mingempty.builder.WrapperBuilder;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.RedisProperties;
import top.mingempty.cache.redis.entity.wapper.RedissonClientWrapper;
import top.mingempty.cache.redis.entity.wapper.RedissonConfigWrapper;
import top.mingempty.domain.other.GlobalConstant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RedissonClient 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedissonClientFactory
        implements WrapperBuilder<RedissonClientWrapper, RedissonClient, RedisProperties> {

    private final RedisCacheProperties redisCacheProperties;
    private final RedissonConfigWrapper redissonConfigWrapper;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedissonClientWrapper build() {
        Map<String, RedissonClient> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, buildToSub(GlobalConstant.DEFAULT_INSTANCE_NAME, redisCacheProperties.getRedis()));
        redisCacheProperties.getMore()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(),
                        buildToSub(entry.getKey(), entry.getValue())));
        return new RedissonClientWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }

    /**
     * 构建
     *
     * @param instanceName
     * @param properties
     * @return 被构建的对象
     */
    @Override
    public RedissonClient buildToSub(String instanceName, RedisProperties properties) {
        return Redisson.create(redissonConfigWrapper.getResolvedRouter(instanceName));
    }
}
