package top.mingempty.zookeeper.annotation;

import org.springframework.stereotype.Indexed;
import top.mingempty.domain.other.GlobalConstant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Zookeeper切面注解
 *
 * @author zzhao
 */
@Indexed
@Inherited
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZookeeperCut {


    /**
     * 缓存名称
     */
    String value() default GlobalConstant.DEFAULT_INSTANCE_NAME;


}
