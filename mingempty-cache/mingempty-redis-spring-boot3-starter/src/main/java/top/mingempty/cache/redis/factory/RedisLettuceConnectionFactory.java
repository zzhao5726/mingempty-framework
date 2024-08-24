package top.mingempty.cache.redis.factory;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.resource.ClientResources;
import lombok.AllArgsConstructor;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.RedisProperties;
import top.mingempty.cache.redis.entity.enums.RedisTypeEnum;
import top.mingempty.cache.redis.entity.wapper.RedisConfigurationWapper;
import top.mingempty.cache.redis.entity.wapper.RedisConnectionFactoryWapper;
import top.mingempty.domain.other.GlobalConstant;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * RedisConnectionFactory LETTUCE 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedisLettuceConnectionFactory implements MeRedisConnectionFactory {

    private final RedisCacheProperties redisCacheProperties;
    private final RedisConfigurationWapper redisConfigurationWapper;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedisConnectionFactoryWapper build() {
        Map<String, RedisConnectionFactory> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, redisConnectionFactory(redisCacheProperties.getRedis(),
                redisConfigurationWapper.getResolvedDefaultRouter()));
        redisCacheProperties.getMore()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(),
                        redisConnectionFactory(entry.getValue(),
                                redisConfigurationWapper.getResolvedRouter(entry.getKey()))));
        return new RedisConnectionFactoryWapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }


    public RedisConnectionFactory redisConnectionFactory(RedisProperties properties, RedisConfiguration redisConfiguration) {
        LettuceClientConfiguration clientConfig
                = lettuceClientConfiguration(properties,
                clientResources());
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration, clientConfig);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    public LettuceClientConfiguration lettuceClientConfiguration(RedisProperties properties,
                                                                 ClientResources clientResources) {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = PoolBuilderFactory.createBuilder(properties.getPool());
        applyProperties(properties, builder);
        if (properties.isSsl()) {
            builder.useSsl();
        }
        builder.clientOptions(createClientOptions(properties));
        builder.clientResources(clientResources);

        // ClusterTopologyRefreshOptions配置用于开启自适应刷新和定时刷新。如自适应刷新不开启，Redis集群变更时将会导致连接异常！
        ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                //开启自适应刷新
                .enableAdaptiveRefreshTrigger(ClusterTopologyRefreshOptions.RefreshTrigger.MOVED_REDIRECT,
                        ClusterTopologyRefreshOptions.RefreshTrigger.PERSISTENT_RECONNECTS)
                .enableAllAdaptiveRefreshTriggers()
                //设置自适应拓扑更新的超时。
                .adaptiveRefreshTriggersTimeout(Duration.ofSeconds(properties.getAdaptiveRefreshTriggersTimeout()))
                //开启定时刷新,时间间隔根据实际情况修改
                .enablePeriodicRefresh(Duration.ofSeconds(properties.getEnabledPeriodicRefresh()))
                .build();
        builder.clientOptions(
                ClusterClientOptions.builder().topologyRefreshOptions(topologyRefreshOptions).build());

        return builder.build();
    }

    public void applyProperties(RedisProperties properties,
                                LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        if (properties.isSsl()) {
            builder.useSsl();
        }
        if (properties.getTimeout() != null) {
            builder.commandTimeout(properties.getTimeout());
        }
        if (properties.getShutdownTimeout() != null && !properties.getShutdownTimeout().isZero()) {
            builder.shutdownTimeout(properties.getShutdownTimeout());
        }

        if (StrUtil.isNotBlank(properties.getClientName())) {
            builder.clientName(properties.getClientName());
        }
    }

    public ClientOptions createClientOptions(RedisProperties properties) {
        ClientOptions.Builder builder = initializeClientOptionsBuilder(properties);
        Duration connectTimeout = properties.getConnectTimeout();
        if (connectTimeout != null) {
            builder.socketOptions(SocketOptions.builder().connectTimeout(connectTimeout).build());
        }
        return builder.timeoutOptions(TimeoutOptions.enabled()).build();
    }


    public ClientOptions.Builder initializeClientOptionsBuilder(RedisProperties properties) {
        if (RedisTypeEnum.Cluster.equals(properties.getType())) {
            ClusterClientOptions.Builder builder = ClusterClientOptions.builder();
            RedisProperties.Refresh refreshProperties = properties.getRefresh();
            ClusterTopologyRefreshOptions.Builder refreshBuilder = ClusterTopologyRefreshOptions.builder()
                    .dynamicRefreshSources(refreshProperties.isDynamicRefreshSources());
            if (refreshProperties.getPeriod() != null) {
                refreshBuilder.enablePeriodicRefresh(refreshProperties.getPeriod());
            }
            if (refreshProperties.isAdaptive()) {
                refreshBuilder.enableAllAdaptiveRefreshTriggers();
            }
            return builder.topologyRefreshOptions(refreshBuilder.build());
        }

        return ClientOptions.builder();
    }

    public static ClientResources clientResources() {
        return ClientResources.builder().build();
    }

    /**
     * Inner class to allow optional commons-pool2 dependency.
     */
    static class PoolBuilderFactory {
        static LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(RedisProperties.Pool pool) {
            if (pool == null) {
                return  LettucePoolingClientConfiguration.builder();
            }
            return LettucePoolingClientConfiguration.builder().poolConfig(getPoolConfig(pool));
        }

        static GenericObjectPoolConfig<?> getPoolConfig(RedisProperties.Pool pool) {
            GenericObjectPoolConfig<?> config = new GenericObjectPoolConfig<>();
            config.setMaxTotal(pool.getMaxActive());
            config.setMaxIdle(pool.getMaxIdle());
            config.setMinIdle(pool.getMinIdle());
            if (ObjUtil.isNotEmpty(pool.getTimeBetweenEvictionRuns())) {
                config.setTimeBetweenEvictionRuns(pool.getTimeBetweenEvictionRuns());
            }
            if (ObjUtil.isNotEmpty(pool.getMaxWait())) {
                config.setMaxWait(pool.getMaxWait());
            }
            return config;
        }

    }
}