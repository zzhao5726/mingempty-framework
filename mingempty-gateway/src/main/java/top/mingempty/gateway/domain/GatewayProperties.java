package top.mingempty.gateway.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 网关配置文件
 *
 * @author zzhao
 */
@Data
@ConfigurationProperties(prefix = "me.gateway")
@Schema(title = "网关配置文件", description = "网关配置文件")
public class GatewayProperties {


    /**
     * 配置需要清洗的请求头
     * <p>
     * 内置会清理的header有：</br>
     * <p>
     * {@code request-from-inner}
     */
    @Schema(title = "配置需要清洗的请求头")
    private List<String> clearHeaders = List.of();

    /**
     * 默认菜单数据
     */
    @Schema(title = "默认菜单数据")
    private String defaultMenu;
}
