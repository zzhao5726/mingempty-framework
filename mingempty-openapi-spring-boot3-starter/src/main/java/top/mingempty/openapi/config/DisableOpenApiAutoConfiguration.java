package top.mingempty.openapi.config;

import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.webmvc.ui.SwaggerConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * springdoc默认配置
 *
 * @author zzhao
 */
@Configuration
@EnableAutoConfiguration(exclude = {SwaggerConfig.class, SpringDocConfiguration.class})
@ConditionalOnProperty(prefix = "me.openapi", name = "enabled", havingValue = "false", matchIfMissing = true)
public class DisableOpenApiAutoConfiguration {

}
