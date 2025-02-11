package top.mingempty.cache.redis.entity.wapper;

import cn.hutool.core.util.StrUtil;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

/**
 * 重写获取线程工厂的方法，让其支持路由功能
 *
 * @param <K>
 * @param <V>
 * @author zzhao
 */
public class MeRedisTemplate<K, V> extends RedisTemplate<K, V> {

    /**
     * 线程内部存储路由实例ID
     */
    private final ThreadLocal<String> ROUTER_INSTANCE_ID = new ThreadLocal<>();

    /**
     * 设置当前线程的实例ID
     *
     * @param instanceId
     * @return
     */
    public RedisTemplate<K, V> gainResolvedRouter(String instanceId) {
        ROUTER_INSTANCE_ID.set(instanceId);
        return this;
    }


    /**
     * 重写该方法，获取到当前线程的实例ID，然后获取到对应的路由实例
     *
     * @return
     */
    @Override
    public RedisConnectionFactory getRequiredConnectionFactory() {
        if (StrUtil.isNotEmpty(ROUTER_INSTANCE_ID.get())) {
            try {
                if (getConnectionFactory() instanceof RedisConnectionFactoryWrapper redisConnectionFactoryWrapper) {
                    RedisConnectionFactory connectionFactory
                            = redisConnectionFactoryWrapper.getResolvedRouter(ROUTER_INSTANCE_ID.get());
                    Assert.state(connectionFactory != null, "RedisConnectionFactory is required");
                    return connectionFactory;
                }
            } finally {
                ROUTER_INSTANCE_ID.remove();
            }
        }
        return super.getRequiredConnectionFactory();
    }
}
