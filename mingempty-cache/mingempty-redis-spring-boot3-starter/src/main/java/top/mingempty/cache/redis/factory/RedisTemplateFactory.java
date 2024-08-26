package top.mingempty.cache.redis.factory;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import top.mingempty.builder.WrapperBuilder;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.RedisProperties;
import top.mingempty.cache.redis.entity.wapper.RedisConnectionFactoryWrapper;
import top.mingempty.cache.redis.entity.wapper.RedisTemplateWrapper;
import top.mingempty.domain.other.GlobalConstant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RedisTemplate 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedisTemplateFactory
    implements WrapperBuilder<RedisTemplateWrapper, RedisOperations<String, Object>, RedisProperties> {

    private final RedisCacheProperties redisCacheProperties;
    private final RedisConnectionFactoryWrapper redisConnectionFactoryWrapper;
    private final Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer;
    private final StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedisTemplateWrapper build() {
        Map<String, RedisOperations<String, Object>> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, buildToSub(GlobalConstant.DEFAULT_INSTANCE_NAME,redisCacheProperties.getRedis()));
        redisCacheProperties.getMore()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(),
                        buildToSub(entry.getKey(), entry.getValue())));
        return new RedisTemplateWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }

    /**
     * 构建
     *
     * @param instanceName
     * @param properties
     * @return 被构建的对象
     */
    @Override
    public RedisOperations<String, Object> buildToSub(String instanceName, RedisProperties properties) {
        RedisConnectionFactory connectionFactory = redisConnectionFactoryWrapper.getResolvedRouter(instanceName);
        return redisTemplate(properties.isEnableTransactionSupport(),connectionFactory);
    }

    public RedisTemplate<String, Object> redisTemplate(boolean enableTransactionSupport,
                                                       RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 开启redis数据库事务的支持
        redisTemplate.setEnableTransactionSupport(enableTransactionSupport);
        redisTemplate.setConnectionFactory(connectionFactory);

        //设置默认序列化方式
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);

        // 如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //RedisSerializer<Object> redisSerializer = genericJackson2JsonRedisSerializer();

        // value序列化方式采用jackson
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
