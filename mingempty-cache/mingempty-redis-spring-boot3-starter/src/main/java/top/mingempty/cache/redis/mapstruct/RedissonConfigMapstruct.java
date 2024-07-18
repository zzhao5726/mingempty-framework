package top.mingempty.cache.redis.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.redisson.config.Config;
import top.mingempty.cache.redis.entity.RedisProperties;

/**
 * Redisson Config配置转换
 *
 * @author zzhao
 */
@Mapper
public interface RedissonConfigMapstruct {
    RedissonConfigMapstruct INSTANCE = Mappers.getMapper(RedissonConfigMapstruct.class);

    /**
     * Redisson Config配置转换
     *
     * @param redissonConfig 配置文件
     * @return Redisson Config
     */
    @Mappings(value = {})
    Config toConfig(RedisProperties.RedissonConfig redissonConfig);

}
