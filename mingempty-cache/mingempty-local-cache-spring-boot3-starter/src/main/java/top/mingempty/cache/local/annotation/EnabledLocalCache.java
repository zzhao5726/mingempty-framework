package top.mingempty.cache.local.annotation;


import org.springframework.context.annotation.Import;
import top.mingempty.cache.local.config.DefaultCacheManagerConfig;
import top.mingempty.cache.local.config.DiasbleDefaultCacheConfig;
import top.mingempty.cache.local.config.LocalCacheConfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对本地缓存的装配
 *
 * @author zzhao
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DiasbleDefaultCacheConfig.class, LocalCacheConfig.class, DefaultCacheManagerConfig.class})
public @interface EnabledLocalCache {

}
