package top.mingempty.spring.condition;


import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 当配置文件问存在配置时，则注入
 * @author zzhao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(HavingPropertiesCondition.class)
public @interface ConditionalOnHavingProperties {

    /**
     * 前缀
     *
     */
    String prefix() default "";


    /**
     * 值
     * 只有都存在是才会注入
     *
     */
    String[] value() default {};


    /**
     * 若不存在时是否依旧注入
     *
     */
    boolean matchIfMissing() default false;

}
