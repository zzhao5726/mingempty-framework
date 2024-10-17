package top.mingempty.datasource.aspect.annotation;

import org.springframework.core.annotation.AliasFor;
import top.mingempty.datasource.enums.ClusterMode;
import top.mingempty.domain.other.GlobalConstant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 从库
 *
 * @author zzhao
 */
@Documented
@Ds(type = ClusterMode.SLAVE)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface SlaveDs {

    /**
     * 数据源名称
     *
     * @return 数据源名称
     */
    @AliasFor(
            annotation = Ds.class
    )
    String value() default GlobalConstant.DEFAULT_INSTANCE_NAME;
}