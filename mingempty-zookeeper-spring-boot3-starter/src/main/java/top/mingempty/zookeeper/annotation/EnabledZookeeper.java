package top.mingempty.zookeeper.annotation;


import org.springframework.context.annotation.Import;
import top.mingempty.zookeeper.config.ZookeeperConfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对zookeeper的装配
 *
 * @author zzhao
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({ZookeeperConfig.class})
public @interface EnabledZookeeper {

}
