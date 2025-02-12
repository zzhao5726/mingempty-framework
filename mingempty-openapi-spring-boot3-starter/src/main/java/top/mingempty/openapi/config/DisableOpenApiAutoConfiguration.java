package top.mingempty.openapi.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;

/**
 * springdoc默认配置
 *
 * @author zzhao
 */
@Order(10)
@ConditionalOnMissingBean(value = {OpenApiAutoConfiguration.class})
@EnableAutoConfiguration(excludeName = {"org.springdoc.webmvc.ui.SwaggerConfig",
        "org.springdoc.webflux.ui.SwaggerConfig", "org.springdoc.core.configuration.SpringDocConfiguration",
        "org.springdoc.webmvc.core.configuration.MultipleOpenApiSupportConfiguration"})
@ConditionalOnProperty(prefix = "me.openapi", name = "enabled", havingValue = "false", matchIfMissing = true)
public class DisableOpenApiAutoConfiguration {

    public DisableOpenApiAutoConfiguration() {
        System.out.println("");
    }
}
