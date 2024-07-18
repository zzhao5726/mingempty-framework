package top.mingempty.cache.local.annotation;


import org.springframework.context.annotation.Import;
import top.mingempty.cache.local.config.LocalCacheConfig;
import top.mingempty.cache.local.config.LocalCacheDiasbleDefaultCacheConfig;

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
@Import({LocalCacheDiasbleDefaultCacheConfig.class, LocalCacheConfig.class})
public @interface EnabledLocalCache {

}
