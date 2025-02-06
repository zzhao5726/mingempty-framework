package top.mingempty.logging.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * logging配置文件
 *
 * @author zzhao
 */
@Data
@ConfigurationProperties(prefix = "me.logging")
public class LoggingProperties {

    /**
     * 日志配置文件名称
     */
    private String configName = LogginConstant.DEFAULT_CONFIG_NAME;


    /**
     * nacos配置分组
     */
    private String nacosGroup = LogginConstant.DEFAULT_NACOS_GROUP;


}
