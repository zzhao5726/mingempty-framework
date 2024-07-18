package top.mingempty.cache.redis.factory;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.wapper.RedisCacheConfigurationWapper;
import top.mingempty.cache.redis.entity.wapper.RedisCacheManagerWrapper;
import top.mingempty.cache.redis.entity.wapper.RedisConnectionFactoryWapper;
import top.mingempty.domain.function.IBuilder;
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
public class RedisCacheManagerFactory implements IBuilder<RedisCacheManagerWrapper> {

    private final RedisCacheProperties redisCacheProperties;
    private final RedisConnectionFactoryWapper redisConnectionFactoryWapper;
    private final RedisCacheConfigurationWapper redisCacheConfigurationWapper;


    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedisCacheManagerWrapper build() {
        Map<String, RedisCacheManager> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, redisCacheManager(
                redisConnectionFactoryWapper.getResolvedDefaultRouter(),
                redisCacheConfigurationWapper.getResolvedDefaultRouter()));
        redisCacheProperties.getMore()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(),
                        redisCacheManager(redisConnectionFactoryWapper.getResolvedRouter(entry.getKey()),
                                redisCacheConfigurationWapper.getResolvedRouter(entry.getKey()))));
        return new RedisCacheManagerWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }


    public static RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory,
                                                      RedisCacheConfiguration redisCacheConfiguration) {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        RedisCacheManager redisCacheManager
                = new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                redisCacheConfiguration, redisCacheConfigurationMap);
        return redisCacheManager;
    }
}
