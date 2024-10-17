package top.mingempty.datasource.aspect.annotation;

import top.mingempty.datasource.enums.ClusterMode;
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
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Ds {

    /**
     * 数据源名称
     *
     * @return 数据源名称
     */
    String value() default GlobalConstant.DEFAULT_INSTANCE_NAME;


    /**
     * 数据源类型
     *
     * @return 数据源类型
     */
    ClusterMode type() default ClusterMode.MASTER;


}
