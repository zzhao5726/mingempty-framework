package top.mingempty.cache.redis.factory;

import lombok.AllArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.config.Config;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.wapper.RedissonClientWapper;
import top.mingempty.cache.redis.entity.wapper.RedissonRxClientWapper;
import top.mingempty.domain.function.IBuilder;
import top.mingempty.domain.other.GlobalConstant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RedissonRxClient 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedissonRxClientFactory implements IBuilder<RedissonRxClientWapper> {

    private final RedisCacheProperties redisCacheProperties;
    private final RedissonClientWapper redissonClientWapper;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedissonRxClientWapper build() {
        Map<String, RedissonRxClient> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, redissonRx(redissonClientWapper.getResolvedDefaultRouter()));
        redisCacheProperties.getMore()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(),
                        redissonRx(redissonClientWapper.getResolvedRouter(entry.getKey()))));
        return new RedissonRxClientWapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }


    public RedissonRxClient redissonRx(Config config) {
        return Redisson.create(config).rxJava();
    }

    public RedissonRxClient redissonRx(RedissonClient redissonClient) {
        return redissonClient.rxJava();
    }

}
