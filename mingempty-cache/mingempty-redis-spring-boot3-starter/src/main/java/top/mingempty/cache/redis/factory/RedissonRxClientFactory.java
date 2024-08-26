package top.mingempty.cache.redis.factory;

import lombok.AllArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.config.Config;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.wapper.RedissonClientWrapper;
import top.mingempty.cache.redis.entity.wapper.RedissonRxClientWrapper;
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
public class RedissonRxClientFactory implements IBuilder<RedissonRxClientWrapper> {

    private final RedisCacheProperties redisCacheProperties;
    private final RedissonClientWrapper redissonClientWrapper;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedissonRxClientWrapper build() {
        Map<String, RedissonRxClient> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, redissonRx(redissonClientWrapper.getResolvedDefaultRouter()));
        redisCacheProperties.getMore()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(),
                        redissonRx(redissonClientWrapper.getResolvedRouter(entry.getKey()))));
        return new RedissonRxClientWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }


    public RedissonRxClient redissonRx(Config config) {
        return Redisson.create(config).rxJava();
    }

    public RedissonRxClient redissonRx(RedissonClient redissonClient) {
        return redissonClient.rxJava();
    }

}
