package top.mingempty.jdbc.annotation;

import top.mingempty.domain.other.GlobalConstant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源动态切换注解
 *
 * @author zzhao
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DS {

    /**
     * 数据源名称
     *
     * @return 数据源名称
     */
    String value() default GlobalConstant.DEFAULT_INSTANCE_NAME;


}
