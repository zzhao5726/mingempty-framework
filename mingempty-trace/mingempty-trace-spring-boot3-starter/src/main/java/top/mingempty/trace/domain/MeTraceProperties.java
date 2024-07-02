package top.mingempty.trace.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 链路日志配置文件
 *
 * @author zzhao
 */
@Data
@Schema(title = "链路日志配置文件")
@ConfigurationProperties(prefix = "me.trace")
public class MeTraceProperties {

    /**
     * 是否开启链路日志
     */
    @Schema(title = "是否开启链路日志")
    private boolean enabled;

}
