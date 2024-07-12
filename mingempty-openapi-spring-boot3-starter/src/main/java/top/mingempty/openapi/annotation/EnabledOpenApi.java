package top.mingempty.openapi.annotation;


import org.springframework.context.annotation.Import;
import top.mingempty.openapi.config.DisableOpenApiAutoConfiguration;
import top.mingempty.openapi.config.OpenApiAutoConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对openapi的自动装配
 *
 * @author zzhao
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({OpenApiAutoConfiguration.class, DisableOpenApiAutoConfiguration.class})
public @interface EnabledOpenApi {

}
