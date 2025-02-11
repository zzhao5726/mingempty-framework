package top.mingempty.cache.redis.entity;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.redisson.config.Protocol;
import org.redisson.config.TransportMode;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import top.mingempty.builder.BuilderWrapperParent;
import top.mingempty.cache.redis.entity.enums.RedisTypeEnum;
import top.mingempty.cache.redis.excetion.RedisUrlSyntaxException;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * redis连接配置
 *
 * @author zzhao
 */
@Data
public class RedisProperties implements BuilderWrapperParent {
    /**
     * 连接模式（默认单机）
     */
    private RedisTypeEnum type = RedisTypeEnum.Single;

    /**
     * 连接工厂使用的数据库索引。
     */
    private int database = 0;

    /**
     * 连接网址。覆盖主机、端口和密码。用户被忽略。例：
     * redis://user:password@example.com:6379
     */
    private String url;

    /**
     * 解析url
     */
    @NestedConfigurationProperty
    private ConnectionInfo connectionInfo;

    /**
     * Redis 服务器的登录用户名。
     */
    private String username;

    /**
     * Redis 服务器的登录密码。
     */
    private String password;

    /**
     * Redis 哨兵服务器的登录密码。
     */
    private String sentinelPassword;

    /**
     * 是否启用 SSL 支持。
     */
    private boolean ssl;

    /**
     * 读取超时时间
     */
    private Duration timeout;

    /**
     * 连接超时时间
     */
    private Duration connectTimeout;

    /**
     * 要在具有客户端 SETNAME 的连接上设置的客户端名称
     */
    private String clientName;

    /**
     * Redis 服务器的名称。
     */
    private String master;

    /**
     * Redis Cache 设置ttl（单位：S）
     */
    private Integer seconds = 3600;

    /**
     * Redis Cache 在cache name 前面加上给定的值
     */
    private String prefix;

    /**
     * “主机：端口”的列表。
     */
    private List<String> nodes = new ArrayList<>() {{
        this.add("localhost:6379");
    }};

    /**
     * 设置自适应拓扑更新的超时。默认30S
     */
    private Integer adaptiveRefreshTriggersTimeout = 30;

    /**
     * 启用定期刷新并设置刷新周期。默认60S
     */
    private Integer enabledPeriodicRefresh = 60;

    /**
     * 要遵循的最大重定向数。
     */
    private Integer maxRedirects = 0;

    /**
     * 是否启用 事务 支持。
     */
    private boolean enableTransactionSupport = false;

    /**
     *
     */
    @NestedConfigurationProperty
    private final Refresh refresh = new Refresh();

    /**
     * 线程池设置
     */
    @NestedConfigurationProperty
    private Pool pool;


    /**
     * redisson配置类
     */
    @NestedConfigurationProperty
    private RedissonConfig redisson;


    /**
     * 是否注册到spring
     */
    private boolean registerToSpring;

    public String getHost() {
        List<String> nodes = getNodes();
        if (CollUtil.isEmpty(nodes)) {
            return "localhost";
        }
        String[] split = nodes.getFirst().split(":");
        if (split.length == 2) {
            return split[0];
        }
        throw new RedisUrlSyntaxException(nodes.getFirst());
    }

    public int getPort() {
        List<String> nodes = getNodes();
        if (CollUtil.isEmpty(nodes)) {
            return 6379;
        }
        String[] split = nodes.getFirst().split(":");
        if (split.length == 2) {
            return Integer.parseInt(split[1]);
        }
        throw new RedisUrlSyntaxException(nodes.getFirst());
    }

    public String getAddress() {
        return this.getHost().concat(":").concat(String.valueOf(this.getPort()));
    }

    public String getAddress(String prefix) {
        return prefix.concat(getAddress());
    }

    public List<String> getNodes(String prefix) {
        if (CollectionUtil.isEmpty(this.getNodes())) {
            return new ArrayList<>();
        }
        return this.getNodes().parallelStream()
                .map(prefix::concat)
                .collect(Collectors.toList());
    }

    public ConnectionInfo getConnectionInfo() {
        if (StrUtil.isEmpty(this.getUrl())) {
            return this.connectionInfo;
        }
        synchronized (this) {
            if (StrUtil.isEmpty(this.getUrl())) {
                return this.connectionInfo;
            }
            this.connectionInfo = parseUrl(this.getUrl());
            this.setNodes(List.of(connectionInfo.getHostName() + ":" + connectionInfo.getPort()));
            this.setUsername(connectionInfo.getUsername());
            this.setPassword(connectionInfo.getPassword());
            this.setSsl(connectionInfo.isUseSsl());
            this.setUrl(null);
        }
        return this.connectionInfo;
    }

    /**
     * Shutdown timeout.
     */
    private Duration shutdownTimeout = Duration.ofMillis(100);

    public static ConnectionInfo parseUrl(String url) {
        try {
            URI uri = new URI(url);
            String scheme = uri.getScheme();
            if (!"redis".equals(scheme) && !"rediss".equals(scheme)) {
                throw new RedisUrlSyntaxException(url);
            }
            boolean useSsl = ("rediss".equals(scheme));
            String username = null;
            String password = null;
            if (uri.getUserInfo() != null) {
                String candidate = uri.getUserInfo();
                int index = candidate.indexOf(':');
                if (index >= 0) {
                    username = candidate.substring(0, index);
                    password = candidate.substring(index + 1);
                } else {
                    password = candidate;
                }
            }
            return new ConnectionInfo(uri, useSsl, username, password);
        } catch (URISyntaxException ex) {
            throw new RedisUrlSyntaxException(url, ex);
        }
    }

    /**
     * 是否注册到spring
     *
     * @return
     */
    @Override
    public boolean registerToSpring() {
        return this.registerToSpring;
    }

    /**
     * 连接信息
     */
    @Data
    public static class ConnectionInfo {

        private URI uri;

        private boolean useSsl;

        private String username;

        private String password;


        public ConnectionInfo(URI uri, boolean useSsl, String username, String password) {
            this.uri = uri;
            this.useSsl = useSsl;
            this.username = username;
            this.password = password;
        }

        public String getHostName() {
            return uri.getHost();
        }

        public int getPort() {
            return uri.getPort();
        }
    }


    /**
     *
     */
    @Data
    public static class Refresh {

        /**
         * 是否发现并查询所有群集节点以获取群集拓扑。设置为 false 时，仅将初始种子节点用作拓扑发现的源。
         */
        private boolean dynamicRefreshSources = true;

        /**
         * 群集拓扑刷新周期(毫秒)。
         */
        private Duration period;

        /**
         * 是否应使用使用所有可用刷新触发器的自适应拓扑刷新。
         */
        private boolean adaptive;

    }


    /**
     * 池化配置
     */
    @Data
    public static class Pool {


        /**
         * 是否启用池化技术
         */
        private Boolean enabled;

        /**
         * 池中“空闲”连接的最大数量。使用负值表示无限数量的空闲连接。
         */
        private int maxIdle = 8;

        /**
         * 以池中要维护的最小空闲连接数为目标。仅当它和逐出运行之间的时间均为正时，此设置才有效。
         */
        private int minIdle = 0;

        /**
         * 池在给定时间可以分配的最大连接数。使用负值表示无限制。
         */
        private int maxActive = 8;

        /**
         * 当池耗尽时，在引发异常之前，连接分配应阻塞的最大时间。使用负值可无限期阻止。
         */
        private Duration maxWait = Duration.ofMillis(-1);

        /**
         * 空闲对象清除线程的运行间隔时间。如果为正值，则空闲对象逐出线程将启动，否则不会执行空闲对象逐出。
         */
        private Duration timeBetweenEvictionRuns;


    }


    /**
     * 要使用的 Redis 客户端的类型。
     */
    public enum ClientType {

        /**
         * 使用LETTUCE redis 客户端。
         */
        LETTUCE,

        /**
         * 使用 Jedis redis 客户端。
         */
        JEDIS

    }

    /**
     * redisson配置文件
     */
    @Data
    public static class RedissonConfig {
        /**
         * 线程数
         */
        private int threads = 16;

        /**
         * etty线程数
         */

        private int nettyThreads = 32;

        /**
         * 是否启用引用功能
         */
        private boolean referenceEnabled = true;

        /**
         * 传输模式
         */
        private TransportMode transportMode = TransportMode.NIO;

        /**
         * 锁看门狗超时时间，单位为毫秒
         */
        private long lockWatchdogTimeout = 30 * 1000;

        /**
         * 是否检查锁的同步从节点
         */
        private boolean checkLockSyncedSlaves = true;

        /**
         * 从节点同步超时时间，单位为毫秒
         */
        private long slavesSyncTimeout = 1000;

        /**
         * 可靠主题看门狗超时时间，单位为毫秒
         */
        private long reliableTopicWatchdogTimeout = TimeUnit.MINUTES.toMillis(10);

        /**
         * 是否保持PubSub顺序
         */
        private boolean keepPubSubOrder = true;

        /**
         * 是否使用脚本缓存
         */
        private boolean useScriptCache = false;

        /**
         * 最小清理延迟，单位为秒
         */
        private int minCleanUpDelay = 5;

        /**
         * 最大清理延迟，单位为秒
         */
        private int maxCleanUpDelay = 30 * 60;

        /**
         * 清理键的数量
         */
        private int cleanUpKeysAmount = 100;

        /**
         * 是否使用线程类加载器
         */
        private boolean useThreadClassLoader = true;

        /**
         * 是否延迟初始化
         */
        private boolean lazyInitialization = true;

        /**
         * 协议类型
         */
        private Protocol protocol = Protocol.RESP2;
    }

}