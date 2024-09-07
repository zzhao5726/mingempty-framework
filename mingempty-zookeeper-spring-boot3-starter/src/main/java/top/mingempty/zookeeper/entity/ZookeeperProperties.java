package top.mingempty.zookeeper.entity;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import top.mingempty.domain.other.GlobalConstant;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Zookeeper的全局配置
 *
 * @author zzhao
 */
@Data
@ConfigurationProperties(prefix = "me.zookeeper")
public class ZookeeperProperties  {
    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 默认配置
     */
    @NestedConfigurationProperty
    private Zookeeper one;

    /**
     * 更多配置
     */
    private Map<String, Zookeeper> more;

    /**
     * 获取所有的配置
     *
     * @return
     */
    public Map<String, Zookeeper> gainAll() {
        Map<String, Zookeeper> allProperties = new HashMap<>();
        Optional.ofNullable(more)
                .ifPresent(allProperties::putAll);
        allProperties.put(GlobalConstant.DEFAULT_INSTANCE_NAME, one);
        return Collections.unmodifiableMap(allProperties);
    }

    /**
     * 通过实例名称获取配置
     *
     * @param instanceName 实例名称
     * @return
     */
    public Zookeeper gain(String instanceName) {
        if (GlobalConstant.DEFAULT_INSTANCE_NAME.equals(instanceName)) {
            return one;
        }
        return Optional.ofNullable(more)
                .flatMap(map -> Optional.ofNullable(map.get(instanceName)))
                .orElse(null);
    }
}
