package top.mingempty.cache.redis.factory;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.util.Assert;
import top.mingempty.builder.WrapperBuilder;
import top.mingempty.cache.redis.entity.RedisCacheProperties;
import top.mingempty.cache.redis.entity.RedisProperties;
import top.mingempty.cache.redis.entity.wapper.RedisConfigurationWrapper;
import top.mingempty.cache.redis.excetion.RedisCacheException;
import top.mingempty.domain.other.GlobalConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RedisCacheConfiguration 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedisConfigurationFactory
        implements WrapperBuilder<RedisConfigurationWrapper, RedisConfiguration, RedisProperties> {

    private final RedisCacheProperties redisCacheProperties;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public RedisConfigurationWrapper build() {
        Map<String, RedisConfiguration> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, buildToSub(GlobalConstant.DEFAULT_INSTANCE_NAME, redisCacheProperties.getRedis()));
        redisCacheProperties.getMore()
                .entrySet()
                .parallelStream()
                .forEach(entry -> map.put(entry.getKey(), buildToSub(entry.getKey(), entry.getValue())));
        return new RedisConfigurationWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }

    /**
     * 构建
     *
     * @param instanceName
     * @param properties
     * @return 被构建的对象
     */
    @Override
    public RedisConfiguration buildToSub(String instanceName, RedisProperties properties) {
        switch (properties.getType()) {
            case Single -> {
                return standaloneConfig(properties);
            }
            case Cluster -> {
                return clusterConfiguration(properties);
            }
            case MasterSlave -> {
                return masterReplicaConfig(properties);
            }
            case Sentinel -> {
                return sentinelConfig(properties);
            }
            default -> throw new RedisCacheException("0000000003");
        }
    }


    public RedisStaticMasterReplicaConfiguration masterReplicaConfig(RedisProperties properties) {
        RedisStaticMasterReplicaConfiguration config = new RedisStaticMasterReplicaConfiguration(properties.getHost(), properties.getPort());
        config.setUsername(properties.getUsername());
        config.setPassword(RedisPassword.of(properties.getPassword()));
        config.setDatabase(properties.getDatabase());
        return config;
    }


    public RedisStandaloneConfiguration standaloneConfig(RedisProperties properties) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(properties.getHost());
        config.setPort(properties.getPort());
        config.setUsername(properties.getUsername());
        config.setPassword(RedisPassword.of(properties.getPassword()));
        config.setDatabase(properties.getDatabase());
        return config;
    }

    public RedisSentinelConfiguration sentinelConfig(RedisProperties properties) {
        RedisSentinelConfiguration config = new RedisSentinelConfiguration();
        config.master(properties.getMaster());
        config.setSentinels(redisNodeList(properties.getNodes()));
        config.setUsername(properties.getUsername());
        if (properties.getPassword() != null) {
            config.setPassword(RedisPassword.of(properties.getPassword()));
        }
        if (properties.getSentinelPassword() != null) {
            config.setSentinelPassword(RedisPassword.of(properties.getSentinelPassword()));
        }
        config.setDatabase(properties.getDatabase());
        return config;
    }


    public RedisClusterConfiguration clusterConfiguration(RedisProperties properties) {
        RedisClusterConfiguration config = new RedisClusterConfiguration(properties.getNodes());
        if (properties.getMaxRedirects() != null) {
            config.setMaxRedirects(properties.getMaxRedirects());
        }
        config.setUsername(properties.getUsername());
        if (properties.getPassword() != null) {
            config.setPassword(RedisPassword.of(properties.getPassword()));
        }
        return config;
    }

    /**
     * 生成RedisNode
     *
     * @param nodeList
     * @return
     */
    public List<RedisNode> redisNodeList(List<String> nodeList) {
        List<RedisNode> nodes = new ArrayList<>(nodeList.size());
        for (String node : nodeList) {
            List<String> parts = StrUtil.split(node, ":");
            Assert.notNull(parts, "redis node must not null");
            Assert.state(parts.size() == 2, "redis node must be defined as 'host:port'");
            nodes.add(new RedisNode(parts.get(0), Integer.parseInt(parts.get(1))));
        }
        return nodes;
    }
}
