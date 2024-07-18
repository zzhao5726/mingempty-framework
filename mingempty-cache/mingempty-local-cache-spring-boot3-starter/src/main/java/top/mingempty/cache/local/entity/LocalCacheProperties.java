package top.mingempty.cache.local.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Collections;
import java.util.Map;

/**
 * 本地缓存配置
 *
 * @author zzhao
 */
@Data
@ConfigurationProperties(prefix = "me.cache")
public class LocalCacheProperties {

    /**
     * 是否开启本地缓存
     */
    private boolean enabledLocalCache = true;

    /**
     * 是否开启caffeine缓存
     */
    private boolean enabledCaffeine = true;

    /**
     * 默认caffeine缓存配置
     */
    @NestedConfigurationProperty
    private CaffeineProperties caffeine = new CaffeineProperties();

    /**
     * 其余的caffeine缓存配置
     */
    private Map<String, CaffeineProperties> moreCaffeine = Collections.emptyMap();

}
