package top.mingempty.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

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
     * <p>
     * 如果最终都不存在时则默认为{@code  mingempty}</br>
     * <p>
     * 最终{@code me.name}和{@code spring.application.name}值相同
     */
    @Schema(title = "系统名称")
    private String name = "mingempty";

    /**
     * 系统分组</br>
     * <p>
     * 如果{@code me.group}不存在时则取{@code spring.application.group}</br>
     * <p>
     * 如果最终都不存在时则默认为{@code g}</br>
     * <p>
     * 最终{@code me.group}和{@code spring.application.group}值相同
     */
    @Schema(title = "系统分组")
    private String group = "g";

    /**
     * 系统版本</br>
     * <p>
     * 如果{@code me.version}不存在时则取{@code spring.application.version}</br>
     * <p>
     * 如果最终都不存在时则默认为{@code v}</br>
     * <p>
     * 最终{@code me.version}和{@code spring.application.version}值相同
     */
    @Schema(title = "系统版本")
    private String version = "v";

    /**
     * 请求路径前缀</br>
     * <p>
     * 如果{@code  me.base-path}不存在时则取{@code server.servlet.context-path}</br>
     * <p>
     * 如果{@code  server.servlet.context-path}不存在时则取{@code spring.webflux.base-path}</br>
     * <p>
     * 如果最终都不存在时则默认为{@code /mingempty}</br>
     * <p>
     * 最终{@code me.base-path}、{@code server.servlet.context-path}和{@code spring.webflux.base-path}值相同
     */
    @Schema(title = "请求路径前缀")
    private String basePath = "/mingempty";

    /**
     * 项目编译时间</br>
     * <p>
     * 建议在底层配置文件内增加配置项</br>
     * {@code me.timestamp=${current.time}}, 然后通过maven插件配置时间</br>
     */
    @Schema(title = "项目编译时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime timestamp;


}
