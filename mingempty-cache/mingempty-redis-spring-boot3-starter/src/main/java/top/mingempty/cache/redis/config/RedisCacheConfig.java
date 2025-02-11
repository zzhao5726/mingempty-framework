package top.mingempty.cache.redis.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import top.mingempty.cache.redis.api.RedisCacheApi;
import top.mingempty.cache.redis.api.impl.RedisCacheApiImpl;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.serializer.MeJackson2JsonRedisSerializer;
import top.mingempty.cache.redis.entity.wapper.RedisCacheConfigurationWrapper;
import top.mingempty.cache.redis.entity.wapper.RedisCacheManagerWrapper;
import top.mingempty.cache.redis.entity.wapper.RedisConfigurationWrapper;
import top.mingempty.cache.redis.entity.wapper.RedisConnectionFactoryWrapper;
import top.mingempty.cache.redis.entity.wapper.RedissonClientWrapper;
import top.mingempty.cache.redis.entity.wapper.RedissonConfigWrapper;
import top.mingempty.cache.redis.entity.wapper.RedissonRxClientWrapper;
import top.mingempty.cache.redis.factory.MeRedisConnectionFactory;
import top.mingempty.cache.redis.factory.RedisCacheConfigurationFactory;
import top.mingempty.cache.redis.factory.RedisCacheManagerFactory;
import top.mingempty.cache.redis.factory.RedisConfigurationFactory;
import top.mingempty.cache.redis.factory.RedisJedisConnectionFactory;
import top.mingempty.cache.redis.factory.RedisLettuceConnectionFactory;
import top.mingempty.cache.redis.factory.RedisTemplateFactory;
import top.mingempty.cache.redis.factory.RedissonClientFactory;
import top.mingempty.cache.redis.factory.RedissonConfigFactory;
import top.mingempty.cache.redis.factory.RedissonRxClientFactory;
import top.mingempty.commons.util.JacksonUtil;


/**
 * redis缓存配置类
 *
 * @author zzhao
 */
@Slf4j
@EnableCaching
@AllArgsConstructor
@EnableConfigurationProperties(value = {RedisCacheProperties.class})
@ConditionalOnProperty(prefix = "me.cache", name = "enabled-redis", havingValue = "true", matchIfMissing = true)
public class RedisCacheConfig {

    /***
     * 缓存配置
     */
    private final RedisCacheProperties redisCacheProperties;

    /**
     * ObjectMapper
     *
     * @return
     */
    @Bean(value = "redisObjectMapper")
    @ConditionalOnMissingBean(name = {"redisObjectMapper"})
    @ConditionalOnMissingClass(value = {"org.springframework.security.jackson2.CoreJackson2Module"})
    public ObjectMapper redisObjectMapper() {
        return JacksonUtil.build(false, JsonInclude.Include.NON_NULL, false);
    }

    /**
     * 序列化
     *
     * @return
     */
    @Bean
    @ConditionalOnBean(value = {ObjectMapper.class})
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(ObjectMapper redisObjectMapper) {
        return new MeJackson2JsonRedisSerializer<>(redisObjectMapper, Object.class);
    }

    @Bean
    public RedisConfigurationFactory redisConfigurationFactory() {
        return new RedisConfigurationFactory(redisCacheProperties);
    }

    @Bean
    @ConditionalOnBean(value = {RedisConfigurationFactory.class})
    public RedisConfigurationWrapper redisConfigurationWrapper(RedisConfigurationFactory redisConfigurationFactory) {
        return redisConfigurationFactory.build();
    }

    @Bean
    @ConditionalOnClass(name = {"redis.clients.jedis.Jedis"})
    public MeRedisConnectionFactory redisJedisConnectionFactory(RedisConfigurationWrapper redisConfigurationWrapper) {
        return new RedisJedisConnectionFactory(redisCacheProperties, redisConfigurationWrapper);
    }


    @Bean
    @ConditionalOnMissingBean(value = {MeRedisConnectionFactory.class})
    public MeRedisConnectionFactory redisLettuceConnectionFactory(RedisConfigurationWrapper redisConfigurationWrapper) {
        return new RedisLettuceConnectionFactory(redisCacheProperties, redisConfigurationWrapper);
    }

    @Bean
    @ConditionalOnBean(value = {MeRedisConnectionFactory.class})
    public RedisConnectionFactoryWrapper redisConnectionFactoryWrapper(MeRedisConnectionFactory meRedisConnectionFactory) {
        return meRedisConnectionFactory.build();
    }

    @Bean(value = {"redisTemplate", "meRedisTemplate"})
    @ConditionalOnBean(value = {RedisConnectionFactoryWrapper.class})
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactoryWrapper redisConnectionFactoryWrapper,
                                                       Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
        return RedisTemplateFactory.redisTemplate(redisCacheProperties.getRedis().isEnableTransactionSupport(),
                redisConnectionFactoryWrapper, jackson2JsonRedisSerializer);
    }

    /*=================redis👆  CacheManager👇================================*/

    @Bean
    @ConditionalOnBean(value = {Jackson2JsonRedisSerializer.class})
    @ConditionalOnProperty(prefix = "me.cache", name = "enabled-redis-spring-cache", havingValue = "true", matchIfMissing = true)
    public RedisCacheConfigurationFactory redisCacheConfigurationFactory(Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
        return new RedisCacheConfigurationFactory(redisCacheProperties, jackson2JsonRedisSerializer);
    }

    @Bean
    @ConditionalOnBean(value = {RedisCacheConfigurationFactory.class})
    public RedisCacheConfigurationWrapper redisCacheConfigurationWrapper(RedisCacheConfigurationFactory redisCacheConfigurationFactory) {
        return redisCacheConfigurationFactory.build();
    }


    @Bean
    @ConditionalOnBean(value = {RedisCacheConfigurationWrapper.class, MeRedisConnectionFactory.class})
    public RedisCacheManagerFactory redisCacheManagerFactory(RedisConnectionFactoryWrapper redisConnectionFactoryWrapper,
                                                             RedisCacheConfigurationWrapper redisCacheConfigurationWrapper) {
        return new RedisCacheManagerFactory(redisCacheProperties, redisConnectionFactoryWrapper, redisCacheConfigurationWrapper);
    }

    @Bean
    @ConditionalOnBean(value = {RedisCacheManagerFactory.class})
    public RedisCacheManagerWrapper redisCacheManagerWrapper(RedisCacheManagerFactory redisCacheManagerFactory) {
        return redisCacheManagerFactory.build();
    }

    /*=================CacheManager👆  redisson👇================================*/

    @Bean
    @ConditionalOnProperty(prefix = "me.cache", name = "enabled-redisson", havingValue = "true", matchIfMissing = true)
    public RedissonConfigFactory redissonConfigFactory() {
        return new RedissonConfigFactory(redisCacheProperties);
    }

    @Bean
    @ConditionalOnBean(value = {RedissonConfigFactory.class})
    public RedissonConfigWrapper redissonConfigWrapper(RedissonConfigFactory redissonConfigFactory) {
        return redissonConfigFactory.build();
    }

    @Bean
    @ConditionalOnBean(value = {RedissonConfigWrapper.class})
    public RedissonClientFactory redissonClientFactory(RedissonConfigWrapper redissonConfigWrapper) {
        return new RedissonClientFactory(redisCacheProperties, redissonConfigWrapper);
    }

    @Bean
    @ConditionalOnBean(value = {RedissonClientFactory.class})
    public RedissonClientWrapper redissonClientWrapper(RedissonClientFactory redissonClientFactory) {
        return redissonClientFactory.build();
    }

    @Bean
    @ConditionalOnBean(value = {RedissonClientWrapper.class})
    @ConditionalOnProperty(prefix = "me.cache", name = "enabled-redisson-rx", havingValue = "true")
    public RedissonRxClientFactory redissonRxClientFactory(RedissonClientWrapper redissonConfigWrapper) {
        return new RedissonRxClientFactory(redisCacheProperties, redissonConfigWrapper);
    }

    @Bean
    @ConditionalOnBean(value = {RedissonRxClientFactory.class})
    public RedissonRxClientWrapper redissonRxClientWrapper(RedissonRxClientFactory redissonRxClientFactory) {
        return redissonRxClientFactory.build();
    }

    /*=================redisson👆 redisCacheApi👇================================*/

    @Bean
    public RedisCacheApi redisCacheApi(ObjectMapper redisObjectMapper,
                                       @Autowired(required = false) RedisCacheManagerWrapper redisCacheManagerWrapper,
                                       @Autowired(required = false) RedissonClientWrapper redissonClientWrapper,
                                       @Autowired(required = false) RedissonRxClientWrapper redissonRxClientWrapper,
                                       RedisTemplate<String, Object> redisTemplate) {
        return new RedisCacheApiImpl(redisObjectMapper, redisCacheManagerWrapper,
                redissonClientWrapper, redissonRxClientWrapper, redisTemplate);
    }


}
