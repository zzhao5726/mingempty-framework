package top.mingempty.cache.redis.entity.wapper;

import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import top.mingempty.cache.redis.aspect.RedisCacheAspect;
import top.mingempty.domain.other.AbstractRouter;

import java.util.Map;
import java.util.function.Supplier;

/**
 * RedisConfiguration路由封装
 *
 * @author zzhao
 * @date 2023/3/12 11:05
 */
public class RedisConfigurationWapper extends AbstractRouter<RedisConfiguration> implements RedisConfiguration {

    public RedisConfigurationWapper(String defaultTargetName, Map<String, RedisConfiguration> targetRouter) {
        super(defaultTargetName, targetRouter);
    }

    public RedisConfigurationWapper(String defaultTargetName, Map<String, RedisConfiguration> targetRouter, boolean lenientFallback) {
        super(defaultTargetName, targetRouter, lenientFallback);
    }

    /**
     * 检索查找路由方式
     */
    @Override
    protected String determineCurrentLookupKey() {
        return RedisCacheAspect.acquireCacheName();
    }

    /************************RedisConfiguration方法封装----------start*********************************************/
    /**
     * Get the configured database index if the current {@link RedisConfiguration} is
     * {@link #isDatabaseIndexAware(RedisConfiguration) database aware} or evaluate and return the value of the given
     * {@link Supplier}.
     *
     * @param other a {@code Supplier} whose result is returned if given {@link RedisConfiguration} is not
     *              {@link #isDatabaseIndexAware(RedisConfiguration) database aware}.
     * @return never {@literal null}.
     * @throws IllegalArgumentException if {@code other} is {@literal null}.
     */
    @Override
    public Integer getDatabaseOrElse(Supplier<Integer> other) {
        return determineTargetRouter().getDatabaseOrElse(other);
    }

    /**
     * Get the configured {@link RedisPassword} if the current {@link RedisConfiguration} is
     * {@link #isAuthenticationAware(RedisConfiguration) password aware} or evaluate and return the value of the given
     * {@link Supplier}.
     *
     * @param other a {@code Supplier} whose result is returned if given {@link RedisConfiguration} is not
     *              {@link #isAuthenticationAware(RedisConfiguration) password aware}.
     * @return never {@literal null}.
     * @throws IllegalArgumentException if {@code other} is {@literal null}.
     */
    @Override
    public RedisPassword getPasswordOrElse(Supplier<RedisPassword> other) {
        return determineTargetRouter().getPasswordOrElse(other);
    }

    /************************RedisConfiguration方法封装----------end*********************************************/


}
