package top.mingempty.openapi.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.SpecVersion;
import lombok.Data;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * ApiDocProperties
 *
 * @author zzhao
 */
@Data
@Schema(title = "链路日志配置文件")
@ConfigurationProperties(prefix = "me.openapi")
public class OpenApiProperties  {

    /**
     * 是否开启swagger
     */
    private Boolean enabled = true;

    /**
     * 标题
     **/
    private String title = "明空科技API文档";

    /**
     * 版本
     */
    private String version = "1.0.0";

    /**
     * spec 版本
     */
    private SpecVersion specVersion = SpecVersion.V30;

    /**
     * 描述
     */
    private String description = "明空科技API文档";

    /**
     * 联系名称
     */
    private String contactName = "zzhao";

    /**
     * 联系邮箱
     */
    private String contactEmail = "945514650@qq.com";

    /**
     * 联系地址
     */
    private String contactUrl = "https://gitee.com/mingempty/mingempty-framework";

    /**
     * 许可地址
     */
    private String licenseUrl = "https://gitee.com/mingempty/mingempty-framework/blob/master/LICENSE";

    /**
     * 许可名称
     */
    private String licenseName = "Apache License 2.0";

    /**
     * springdoc 配置
     * <br>
     * 配置后，会覆盖默认配置
     */
    @NestedConfigurationProperty
    private SpringDocConfigProperties springdoc = new SpringDocConfigProperties();

    /**
     * swagger ui 配置
     * <br>
     * 配置后，会覆盖默认配置
     */
    @NestedConfigurationProperty
    private SwaggerUiConfigProperties swaggerUi = new SwaggerUiConfigProperties();

}
