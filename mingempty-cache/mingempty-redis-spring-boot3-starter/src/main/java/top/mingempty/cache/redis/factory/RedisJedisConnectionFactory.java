package top.mingempty.cache.redis.factory;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.RedisProperties;
import top.mingempty.cache.redis.entity.wapper.RedisConfigurationWrapper;

/**
 * RedisConnectionFactory JEDIS 工厂
 *
 * @author zzhao
 */
public class RedisJedisConnectionFactory extends MeRedisConnectionFactory {


    public RedisJedisConnectionFactory(RedisCacheProperties redisCacheProperties, RedisConfigurationWrapper redisConfigurationWrapper) {
        super(redisCacheProperties, redisConfigurationWrapper);
    }

    /**
     * 构建
     *
     * @param instanceName
     * @param properties
     * @return 被构建的对象
     */
    @Override
    public RedisConnectionFactory buildToSub(String instanceName, RedisProperties properties) {
        return null;
    }
}
