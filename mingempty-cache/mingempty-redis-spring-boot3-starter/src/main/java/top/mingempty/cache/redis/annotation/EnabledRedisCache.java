package top.mingempty.cache.redis.annotation;


import org.springframework.context.annotation.Import;
import top.mingempty.cache.local.annotation.EnabledLocalCache;
import top.mingempty.cache.redis.config.RedisDiasbleDefaultCacheConfig;
import top.mingempty.cache.redis.config.RedisCacheConfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对Redis的装配
 *
 * @author zzhao
 */
@Inherited
@Documented
@EnabledLocalCache
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RedisDiasbleDefaultCacheConfig.class, RedisCacheConfig.class})
public @interface EnabledRedisCache {

}
