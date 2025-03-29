package top.mingempty.dubbo.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * dubbo配置
 */
@Data
@ConfigurationProperties(prefix = "me.dubbo")
public class DubboProperties {

    /**
     * 是否启用
     */
    private boolean enabled = true;

}
