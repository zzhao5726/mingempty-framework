package top.mingempty.cache.redis.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Collections;
import java.util.Map;

/**
 * Redis缓存配置
 *
 * @author zzhao
 */
@Data
@ConfigurationProperties(prefix = "me.cache")
public class RedisCacheProperties {

    /**
     * 是否启用redis Cache
     */
    private boolean enabledRedis = true;

    /**
     * 默认的redis缓存配置
     */
    @NestedConfigurationProperty
    private RedisProperties redis = new RedisProperties();

    /**
     * 其余的缓存配置
     */
    private Map<String, RedisProperties> more = Collections.emptyMap();

    /**
     * 是否启用基于spring的redis Cache
     */
    private Boolean enabledRedisSpringCache = Boolean.TRUE;

    /**
     * 是否启用Redisson
     */
    private Boolean enabledRedisson = Boolean.TRUE;

    /**
     * 是否启用RedissonRx
     */
    private Boolean enabledRedissonRx = Boolean.FALSE;


}
