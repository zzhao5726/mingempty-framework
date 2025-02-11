package top.mingempty.cache.redis.factory;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import top.mingempty.cache.redis.entity.wapper.MeRedisTemplate;

/**
 * RedisTemplate 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedisTemplateFactory {


    private static final StringRedisSerializer STRING_REDIS_SERIALIZER = new StringRedisSerializer();

    public static StringRedisSerializer gainStringRedisSerializer() {
        return STRING_REDIS_SERIALIZER;
    }

    public static RedisTemplate<String, Object> redisTemplate(boolean enableTransactionSupport
            , RedisConnectionFactory connectionFactory
            , RedisSerializer<?> valueSerializer) {
        return redisTemplate(enableTransactionSupport, connectionFactory, valueSerializer,
                STRING_REDIS_SERIALIZER, valueSerializer);
    }

    public static RedisTemplate<String, Object> redisTemplate(boolean enableTransactionSupport
            , RedisConnectionFactory connectionFactory
            , RedisSerializer<?> keySerializer
            , RedisSerializer<?> valueSerializer) {
        return redisTemplate(enableTransactionSupport, connectionFactory, valueSerializer,
                keySerializer, valueSerializer);
    }

    public static RedisTemplate<String, Object> redisTemplate(boolean enableTransactionSupport
            , RedisConnectionFactory connectionFactory
            , RedisSerializer<?> defaultSerializer
            , RedisSerializer<?> keySerializer
            , RedisSerializer<?> valueSerializer) {
        RedisTemplate<String, Object> redisTemplate = new MeRedisTemplate<>();
        // 开启redis数据库事务的支持
        redisTemplate.setEnableTransactionSupport(enableTransactionSupport);
        redisTemplate.setConnectionFactory(connectionFactory);
        //设置默认序列化方式
        redisTemplate.setDefaultSerializer(defaultSerializer);
        // 如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(keySerializer);
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(keySerializer);
        //RedisSerializer<Object> redisSerializer = genericJackson2JsonRedisSerializer();

        // value序列化方式采用jackson
        redisTemplate.setValueSerializer(valueSerializer);
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(valueSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
