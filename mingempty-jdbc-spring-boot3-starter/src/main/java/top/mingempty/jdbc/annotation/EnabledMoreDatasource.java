package top.mingempty.jdbc.annotation;


import org.springframework.context.annotation.Import;
import top.mingempty.jdbc.config.C3p0DatasourceAutoConfigure;
import top.mingempty.jdbc.config.DatasourceAutoConfigure;
import top.mingempty.jdbc.config.DruidDatasourceAutoConfigure;
import top.mingempty.jdbc.config.HikariDatasourceAutoConfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对缓存的装配
 *
 * @author zzhao
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DatasourceAutoConfigure.class, HikariDatasourceAutoConfigure.class,
        DruidDatasourceAutoConfigure.class, C3p0DatasourceAutoConfigure.class})
public @interface EnabledMoreDatasource {

}
