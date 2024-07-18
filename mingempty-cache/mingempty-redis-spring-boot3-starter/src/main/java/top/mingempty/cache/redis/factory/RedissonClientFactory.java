package top.mingempty.cache.redis.factory;

import lombok.AllArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.wapper.RedissonClientWapper;
import top.mingempty.cache.redis.entity.wapper.RedissonConfigWapper;
import top.mingempty.domain.function.IBuilder;
import top.mingempty.domain.other.GlobalConstant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RedissonClient 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedissonClientFactory implements IBuilder<RedissonClientWapper> {

    private final RedisCacheProperties redisCacheProperties;
    private final RedissonConfigWapper redissonConfigWapper;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedissonClientWapper build() {
        Map<String, RedissonClient> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, redisson(redissonConfigWapper.getResolvedDefaultRouter()));
        redisCacheProperties.getMore()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(),
                        redisson(redissonConfigWapper.getResolvedRouter(entry.getKey()))));
        return new RedissonClientWapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }


    public static RedissonClient redisson(Config config) {
        return Redisson.create(config);
    }

}
