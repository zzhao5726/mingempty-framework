package top.mingempty.cache.redis.config;


import lombok.extern.slf4j.Slf4j;
import org.redisson.spring.starter.RedissonAutoConfigurationV2;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;

/**
 * 自动装配禁用默认缓存配置
 *
 * @author zzhao
 */
@Slf4j
@EnableAutoConfiguration(exclude = {
        RedisAutoConfiguration.class,
        RedissonAutoConfigurationV2.class,
        CacheAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class})
public class RedisDiasbleDefaultCacheConfig {
}
