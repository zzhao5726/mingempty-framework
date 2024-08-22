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
import top.mingempty.cache.redis.entity.wapper.RedisCacheConfigurationWapper;
import top.mingempty.cache.redis.entity.wapper.RedisCacheManagerWrapper;
import top.mingempty.cache.redis.entity.wapper.RedisConfigurationWapper;
import top.mingempty.cache.redis.entity.wapper.RedisConnectionFactoryWapper;
import top.mingempty.cache.redis.entity.wapper.RedisTemplateWapper;
import top.mingempty.cache.redis.entity.wapper.RedissonClientWapper;
import top.mingempty.cache.redis.entity.wapper.RedissonConfigWapper;
import top.mingempty.cache.redis.entity.wapper.RedissonRxClientWapper;
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
    @ConditionalOnMissingClass(value = {"org.springframework.security.jackson2.CoreJackson2Module.class"})
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
    public RedisConfigurationWapper redisConfigurationWapper(RedisConfigurationFactory redisConfigurationFactory) {
        return redisConfigurationFactory.build();
    }

    @Bean
    @ConditionalOnClass(name = {"redis.clients.jedis.Jedis"})
    public MeRedisConnectionFactory redisJedisConnectionFactory() {
        return new RedisJedisConnectionFactory(redisCacheProperties);
    }


    @Bean
    @ConditionalOnMissingBean(value = {MeRedisConnectionFactory.class})
    public MeRedisConnectionFactory redisLettuceConnectionFactory(RedisConfigurationWapper redisConfigurationWapper) {
        return new RedisLettuceConnectionFactory(redisCacheProperties, redisConfigurationWapper);
    }

    @Bean
    @ConditionalOnBean(value = {MeRedisConnectionFactory.class})
    public RedisConnectionFactoryWapper redisConnectionFactoryWapper(MeRedisConnectionFactory meRedisConnectionFactory) {
        return meRedisConnectionFactory.build();
    }

    @Bean
    @ConditionalOnBean(value = {RedisConnectionFactoryWapper.class, Jackson2JsonRedisSerializer.class})
    public RedisTemplateFactory redisTemplateFactory(RedisConnectionFactoryWapper redisConnectionFactoryWapper,
                                                     Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
        return new RedisTemplateFactory(redisCacheProperties,
                redisConnectionFactoryWapper, jackson2JsonRedisSerializer);
    }

    @Bean
    @ConditionalOnBean(value = {RedisTemplateFactory.class})
    public RedisTemplateWapper redisTemplateWapper(RedisTemplateFactory redisTemplateFactory) {
        return redisTemplateFactory.build();
    }

    @Bean
    @ConditionalOnBean(value = {RedisConnectionFactoryWapper.class, RedisTemplateFactory.class})
    public RedisTemplate<String, Object> redisTemplate(RedisTemplateFactory redisTemplateFactory,
                                                       RedisConnectionFactoryWapper redisConnectionFactoryWapper) {
        return redisTemplateFactory.redisTemplate(redisCacheProperties.getRedis(), redisConnectionFactoryWapper);
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
    public RedisCacheConfigurationWapper redisCacheConfigurationWapper(RedisCacheConfigurationFactory redisCacheConfigurationFactory) {
        return redisCacheConfigurationFactory.build();
    }


    @Bean
    @ConditionalOnBean(value = {RedisCacheConfigurationWapper.class, MeRedisConnectionFactory.class})
    public RedisCacheManagerFactory redisCacheManagerFactory(RedisConnectionFactoryWapper redisConnectionFactoryWapper,
                                                             RedisCacheConfigurationWapper redisCacheConfigurationWapper) {
        return new RedisCacheManagerFactory(redisCacheProperties, redisConnectionFactoryWapper, redisCacheConfigurationWapper);
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
    public RedissonConfigWapper redissonConfigWapper(RedissonConfigFactory redissonConfigFactory) {
        return redissonConfigFactory.build();
    }

    @Bean
    @ConditionalOnBean(value = {RedissonConfigWapper.class})
    public RedissonClientFactory redissonClientFactory(RedissonConfigWapper redissonConfigWapper) {
        return new RedissonClientFactory(redisCacheProperties, redissonConfigWapper);
    }

    @Bean
    @ConditionalOnBean(value = {RedissonClientFactory.class})
    public RedissonClientWapper redissonConfigWapper(RedissonClientFactory redissonClientFactory) {
        return redissonClientFactory.build();
    }

    @Bean
    @ConditionalOnBean(value = {RedissonClientWapper.class})
    @ConditionalOnProperty(prefix = "me.cache", name = "enabled-redisson-rx", havingValue = "true")
    public RedissonRxClientFactory redissonRxClientFactory(RedissonClientWapper redissonConfigWapper) {
        return new RedissonRxClientFactory(redisCacheProperties, redissonConfigWapper);
    }

    @Bean
    @ConditionalOnBean(value = {RedissonRxClientFactory.class})
    public RedissonRxClientWapper redissonConfigWapper(RedissonRxClientFactory redissonRxClientFactory) {
        return redissonRxClientFactory.build();
    }

    /*=================redisson👆 redisCacheApi👇================================*/

    @Bean
    public RedisCacheApi redisCacheApi(ObjectMapper redisObjectMapper,
                                       @Autowired(required = false) RedisCacheManagerWrapper redisCacheManagerWrapper,
                                       @Autowired(required = false) RedissonClientWapper redissonClientWapper,
                                       @Autowired(required = false) RedissonRxClientWapper redissonRxClientWapper,
                                       RedisTemplateWapper redisTemplateWapper,
                                       RedisTemplate<String, Object> redisTemplate) {
        return new RedisCacheApiImpl(redisObjectMapper, redisCacheManagerWrapper,
                redissonClientWapper, redissonRxClientWapper, redisTemplateWapper, redisTemplate);
    }


}
