package top.mingempty.cache.redis.factory;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import top.mingempty.builder.WrapperBuilder;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.RedisProperties;
import top.mingempty.cache.redis.entity.wapper.RedisConfigurationWrapper;
import top.mingempty.cache.redis.entity.wapper.RedisConnectionFactoryWrapper;
import top.mingempty.domain.other.GlobalConstant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
public abstract class MeRedisConnectionFactory
        implements WrapperBuilder<RedisConnectionFactoryWrapper, RedisConnectionFactory, RedisProperties> {

    protected final RedisCacheProperties redisCacheProperties;
    protected final RedisConfigurationWrapper redisConfigurationWrapper;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedisConnectionFactoryWrapper build() {
        Map<String, RedisConnectionFactory> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, build(GlobalConstant.DEFAULT_INSTANCE_NAME, redisCacheProperties.getRedis()));
        redisCacheProperties.getMore()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(), build(entry.getKey(), entry.getValue())));
        return new RedisConnectionFactoryWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }

}
