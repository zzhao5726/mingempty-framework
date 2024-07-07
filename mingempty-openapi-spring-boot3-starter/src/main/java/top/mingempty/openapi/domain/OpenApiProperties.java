package top.mingempty.openapi.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ApiDocProperties
 *
 * @author zzhao
 */
@Data
@Schema(title = "链路日志配置文件")
@ConfigurationProperties(prefix = "me.openapi")
public class OpenApiProperties {

    /**
     * 是否开启swagger
     */
    private Boolean enabled = true;

    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "";

    /**
     * swagger会解析的url规则
     **/
    private List<String> basePath = new ArrayList<>();

    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePath = new ArrayList<>();

    /**
     * 需要排除的服务
     */
    private List<String> ignoreProviders = new ArrayList<>();

    /**
     * 标题
     **/
    private String title = "明空科技API文档";

    /**
     * 版本
     */
    private String version = "1.0.0";

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
     * 网关
     */
    private String gateway;

    /**
     * 获取token
     */
    private String tokenUrl;

    /**
     * 作用域
     */
    private String scope;

    /**
     * 服务转发配置
     */
    private Map<String, String> services;

}
