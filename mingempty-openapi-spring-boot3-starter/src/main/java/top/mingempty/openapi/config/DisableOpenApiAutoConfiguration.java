package top.mingempty.openapi.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * springdoc默认配置
 *
 * @author zzhao
 */
@Configuration
@EnableAutoConfiguration(excludeName = {"org.springdoc.webflux.ui.SwaggerConfig",
        "org.springdoc.webflux.ui.SwaggerConfig", "org.springdoc.core.configuration.SpringDocConfiguration"})
@ConditionalOnProperty(prefix = "me.openapi", name = "enabled", havingValue = "false", matchIfMissing = true)
public class DisableOpenApiAutoConfiguration {

}
