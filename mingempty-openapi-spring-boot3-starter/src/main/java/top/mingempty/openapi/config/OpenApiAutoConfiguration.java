package top.mingempty.openapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import top.mingempty.openapi.domain.OpenApiProperties;


/**
 * SpringDoc配置
 *
 * @author zzhao
 */
@Order(9)
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(OpenApiProperties.class)
@ConditionalOnProperty(prefix = "me.openapi", name = "enabled", havingValue = "true")
public class OpenApiAutoConfiguration {

    private final OpenApiProperties openApiProperties;

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(info())
                .specVersion(openApiProperties.getSpecVersion());
        // oauth2.0 password
        openAPI.schemaRequirement(HttpHeaders.AUTHORIZATION, this.securityScheme());return openAPI;
    }

    private Info info() {
        return new Info()
                .title(openApiProperties.getTitle())
                .version(openApiProperties.getVersion())
                .description(openApiProperties.getDescription())
                .contact(new Contact()
                        .email(openApiProperties.getContactEmail())
                        .url(openApiProperties.getContactUrl())
                        .name(openApiProperties.getContactName())
                )
                .license(new License()
                        .name(openApiProperties.getLicenseName())
                        .url(openApiProperties.getLicenseUrl())
                )
                ;
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme();
    }
}
