package top.mingempty.datasource.aspect.annotation;


import org.springframework.context.annotation.Import;
import top.mingempty.datasource.config.DatasourceAutoConfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对数据源的装配
 *
 * @author zzhao
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DatasourceAutoConfigure.class})
public @interface EnabledMoreDatasource {

}
