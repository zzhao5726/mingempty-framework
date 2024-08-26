package top.mingempty.cache.redis.factory;

import lombok.AllArgsConstructor;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.wapper.RedisConnectionFactoryWrapper;

/**
 * RedisConnectionFactory JEDIS 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedisJedisConnectionFactory implements MeRedisConnectionFactory {

    private final RedisCacheProperties redisCacheProperties;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedisConnectionFactoryWrapper build() {
        return null;
    }
}
