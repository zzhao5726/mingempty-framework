package top.mingempty.datasource.aspect.annotation;



import top.mingempty.datasource.enums.DsPropagation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * multi data source transaction
 *
 * @author funkye
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DSTransactional {

    /**
     * 回滚异常
     *
     * @return Class[]
     */
    Class<? extends Throwable>[] rollbackFor() default {Exception.class};

    /**
     * 不回滚异常
     *
     * @return Class[]
     */
    Class<? extends Throwable>[] noRollbackFor() default {};

    /**
     * 事务传播行为
     *
     * @return DsPropagation
     */
    DsPropagation propagation() default DsPropagation.REQUIRED;
}