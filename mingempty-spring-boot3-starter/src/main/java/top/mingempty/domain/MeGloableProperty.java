package top.mingempty.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 基础配置文件
 *
 * @author zzhao
 */
@Data
@Schema(title = "基础配置文件")
@ConfigurationProperties(prefix = "me")
public class MeGloableProperty {

    /**
     * 是否写进程ID到当前目录
     */
    @Schema(title = "是否写进程ID到当前目录")
    private boolean writePid = false;

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
     * 是否使用设置的请求路径前缀
     */
    @Schema(title = "是否使用设置的请求路径前缀")
    private boolean usingBasePath = true;

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
     * 建议在配置文件内增加配置项<CODE>me.timestamp=${current.time}</CODE>，然后项目编译时maven插件会自动配置时间该时间
     */
    @Schema(title = "项目编译时间")
    private String timestamp;


}
