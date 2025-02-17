package top.mingempty.logging.model;

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
     * 配置文件名称
     */
    private String configName = "logback.xml";


    /**
     * nacos配置分组
     */
    private String nacosGroup = "LOGBACK_XML_GROUP";


}
