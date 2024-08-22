package top.mingempty.cache.redis.factory;

import lombok.AllArgsConstructor;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import top.mingempty.cache.redis.entity.RedisCacheConstant;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.RedisProperties;
import top.mingempty.cache.redis.entity.enums.RedisTypeEnum;
import top.mingempty.cache.redis.entity.wapper.RedissonConfigWapper;
import top.mingempty.cache.redis.excetion.RedisCacheException;
import top.mingempty.cache.redis.mapstruct.RedissonConfigMapstruct;
import top.mingempty.domain.function.IBuilder;
import top.mingempty.domain.other.GlobalConstant;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;

/**
 * Redisson Config 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedissonConfigFactory implements IBuilder<RedissonConfigWapper> {

    private final static RedisProperties.RedissonConfig REDISSON_CONFIG = new RedisProperties.RedissonConfig();

    private final RedisCacheProperties redisCacheProperties;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedissonConfigWapper build() {
        Map<String, Config> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, config(redisCacheProperties.getRedis()));
        redisCacheProperties.getMore()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(), config(entry.getValue())));
        return new RedissonConfigWapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }


    public static Config config(RedisProperties properties) {
        final Config config = RedissonConfigMapstruct.INSTANCE.toConfig(properties.getRedisson() == null
                ? REDISSON_CONFIG : properties.getRedisson());
        config.setCodec(new StringCodec());

        //设置使用虚拟线程
        config.setExecutor(Executors.newThreadPerTaskExecutor(Thread.ofVirtual()
                .name("redisson-virtual-thread", 0)
                .factory()));
        config.setNettyExecutor(Executors.newThreadPerTaskExecutor(Thread.ofVirtual()
                .name("redisson-netty-virtual-thread", 0)
                .factory()));

        switch (properties.getRedisTypeEnum()) {
            case RedisTypeEnum.Single -> configSingel(config, properties);
            case RedisTypeEnum.Cluster -> configCluster(config, properties);
            case RedisTypeEnum.MasterSlave -> configMasterSlave(config, properties);
            case RedisTypeEnum.Sentinel -> configSentinel(config, properties);
            case RedisTypeEnum.Replicated -> configReplicated(config, properties);
            default -> throw new RedisCacheException("0000000003");
        }
        return config;
    }

    public static void configReplicated(final Config config, RedisProperties properties) {
        config.useReplicatedServers()
                .setDatabase(properties.getDatabase())
                .setUsername(properties.getUsername())
                .setPassword(properties.getPassword())
                .setNodeAddresses(properties.getNodes(RedisCacheConstant.REDIS_PROTOCOL_PREFIX));

    }

    public static void configSentinel(final Config config, RedisProperties properties) {
        config.useSentinelServers()
                .setDatabase(properties.getDatabase())
                .setUsername(properties.getUsername())
                .setPassword(properties.getPassword())
                .setMasterName(properties.getMaster())
                .setSentinelPassword(properties.getSentinelPassword())
                .setSentinelAddresses(properties.getNodes(RedisCacheConstant.REDIS_PROTOCOL_PREFIX));
    }

    public static void configMasterSlave(final Config config, RedisProperties properties) {
        config.useMasterSlaveServers()
                .setDatabase(properties.getDatabase())
                .setUsername(properties.getUsername())
                .setPassword(properties.getPassword())
                .setMasterAddress(properties.getAddress(RedisCacheConstant.REDIS_PROTOCOL_PREFIX))
                .setClientName(properties.getClientName())
                .setSlaveAddresses(new HashSet<>(properties.getNodes(RedisCacheConstant.REDIS_PROTOCOL_PREFIX)));
    }

    public static void configCluster(final Config config, RedisProperties properties) {
        config.useClusterServers()
                .setPassword(properties.getPassword())
                .setUsername(properties.getUsername())
                .setNodeAddresses(properties.getNodes(RedisCacheConstant.REDIS_PROTOCOL_PREFIX));
    }


    public static void configSingel(final Config config, RedisProperties properties) {
        config.useSingleServer()
                .setDatabase(properties.getDatabase())
                .setAddress(properties.getAddress(RedisCacheConstant.REDIS_PROTOCOL_PREFIX))
                .setUsername(properties.getUsername())
                .setPassword(properties.getPassword());
    }
}
