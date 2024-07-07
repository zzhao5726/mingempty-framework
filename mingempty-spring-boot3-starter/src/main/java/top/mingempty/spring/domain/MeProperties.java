package top.mingempty.spring.domain;

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
@ConfigurationProperties(prefix = "me")
public class MeProperties {

    /**
     * 是否开启基础工具配置类
     */
    @Schema(title = "是否开启基础工具配置类")
    private boolean enabled = true;

    /**
     * 系统名称</br>
     * <p>
     * 如果{@code  me.name}不存在时则取{@code spring.application.name}</br>
     * 最终{@code me.name}和{@code spring.application.name}值相同
     */
    @Schema(title = "系统名称")
    private String name = "mingempty";

    /**
     * 系统分组</br>
     * <p>
     * 如果{@code me.group}不存在时则取{@code spring.application.group}</br>
     * 最终{@code me.group}和{@code spring.application.group}值相同
     */
    @Schema(title = "系统分组")
    private String group = "g";

    /**
     * 系统版本</br>
     * <p>
     * 如果{@code me.version}不存在时则取{@code spring.application.version}</br>
     * 最终{@code me.version}和{@code spring.application.version}值相同
     */
    @Schema(title = "系统版本")
    private String version = "v";


}
