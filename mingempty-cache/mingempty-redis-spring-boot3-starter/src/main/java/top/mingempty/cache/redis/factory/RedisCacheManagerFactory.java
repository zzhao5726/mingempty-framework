package top.mingempty.cache.redis.factory;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import top.mingempty.builder.WrapperBuilder;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.RedisProperties;
import top.mingempty.cache.redis.entity.wapper.RedisCacheConfigurationWrapper;
import top.mingempty.cache.redis.entity.wapper.RedisCacheManagerWrapper;
import top.mingempty.cache.redis.entity.wapper.RedisConnectionFactoryWrapper;
import top.mingempty.domain.other.GlobalConstant;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RedisCacheManager 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedisCacheManagerFactory
        implements WrapperBuilder<RedisCacheManagerWrapper, RedisCacheManager, RedisProperties> {

    private final RedisCacheProperties redisCacheProperties;
    private final RedisConnectionFactoryWrapper redisConnectionFactoryWrapper;
    private final RedisCacheConfigurationWrapper redisCacheConfigurationWrapper;


    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedisCacheManagerWrapper build() {
        Map<String, RedisCacheManager> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, buildToSub(
                GlobalConstant.DEFAULT_INSTANCE_NAME, redisCacheProperties.getRedis()));
        redisCacheProperties.getMore()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(),
                        buildToSub(entry.getKey(), entry.getValue())));
        return new RedisCacheManagerWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }

    /**
     * 构建
     *
     * @param instanceName
     * @param properties
     * @return 被构建的对象
     */
    @Override
    public RedisCacheManager buildToSub(String instanceName, RedisProperties properties) {
        RedisConnectionFactory redisConnectionFactory = redisConnectionFactoryWrapper.getResolvedRouter(instanceName);
        RedisCacheConfiguration redisCacheConfiguration = redisCacheConfigurationWrapper.getResolvedRouter(instanceName);
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                redisCacheConfiguration, redisCacheConfigurationMap);
    }
}
