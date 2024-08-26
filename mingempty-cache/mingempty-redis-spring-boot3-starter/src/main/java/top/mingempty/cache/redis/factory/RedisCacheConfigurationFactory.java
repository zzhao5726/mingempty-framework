package top.mingempty.cache.redis.factory;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ObjectUtils;
import top.mingempty.builder.WrapperBuilder;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.RedisProperties;
import top.mingempty.cache.redis.entity.wapper.RedisCacheConfigurationWrapper;
import top.mingempty.domain.other.GlobalConstant;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RedisCacheConfiguration 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedisCacheConfigurationFactory
        implements WrapperBuilder<RedisCacheConfigurationWrapper, RedisCacheConfiguration, RedisProperties> {

    private final RedisCacheProperties redisCacheProperties;
    private final Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedisCacheConfigurationWrapper build() {
        Map<String, RedisCacheConfiguration> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, buildToSub(GlobalConstant.DEFAULT_INSTANCE_NAME, redisCacheProperties.getRedis()));
        redisCacheProperties.getMore()
                .entrySet()
                .parallelStream()
                .forEach(entry -> map.put(entry.getKey(), buildToSub(entry.getKey(), entry.getValue())));
        return new RedisCacheConfigurationWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }

    /**
     * 构建
     *
     * @param instanceName
     * @param properties
     * @return 被构建的对象
     */
    @Override
    public RedisCacheConfiguration buildToSub(String instanceName, RedisProperties properties) {
        if (ObjectUtils.isEmpty(properties.getSeconds())) {
            properties.setSeconds(0);
        }
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer))
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new StringRedisSerializer()))
                .entryTtl(Duration.ofSeconds(properties.getSeconds()))
                //设置为只要一个冒号的
                .computePrefixWith(name
                        -> Optional.ofNullable(properties.getPrefix())
                        .map(prefix -> {
                            if (StrUtil.isNotBlank(prefix)
                                    && !prefix.endsWith(":")) {
                                return prefix.concat(":");
                            }
                            return prefix;
                        }).orElse("")
                        .concat(name).concat(":"));
        return redisCacheConfiguration;
    }
}
