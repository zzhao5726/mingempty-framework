package top.mingempty.zookeeper.entity;


import lombok.Data;
import org.apache.curator.RetryPolicy;
import org.apache.curator.SessionFailedRetryPolicy;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryForever;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.retry.RetryUntilElapsed;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import top.mingempty.builder.BuilderWrapperParent;
import top.mingempty.util.SpringContextUtil;
import top.mingempty.zookeeper.entity.enums.RetryPolicyEnum;
import top.mingempty.zookeeper.entity.enums.ZookeeperFactoryEnum;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * zookeeper的配置项
 *
 * @author zzhao
 */
@Data
public class Zookeeper implements BuilderWrapperParent {
    private static final int DEFAULT_SESSION_TIMEOUT_MS = Integer.getInteger("curator-default-session-timeout", 60 * 1000);
    private static final int DEFAULT_CONNECTION_TIMEOUT_MS = Integer.getInteger("curator-default-connection-timeout", 15 * 1000);
    private static final int DEFAULT_CLOSE_WAIT_MS = (int) TimeUnit.SECONDS.toMillis(1);


    /**
     * 设置要连接的服务器列表
     * <p>
     * 集群环境是用英文分号分隔
     */
    private String connectString = "127.0.0.1:2181";

    /**
     * 会话超时时间，单位为毫秒
     */
    private int sessionTimeoutMs = DEFAULT_SESSION_TIMEOUT_MS;

    /**
     * 连接超时时间，单位为毫秒
     */
    private int connectionTimeoutMs = DEFAULT_CONNECTION_TIMEOUT_MS;

    /**
     * 最大关闭等待时间，单位为毫秒
     */
    private int maxCloseWaitMs = DEFAULT_CLOSE_WAIT_MS;

    /**
     * 线程工厂，用于创建线程
     */
    private String threadFactoryName;

    /**
     * 命名空间，用于标识所属的命名空间
     * <p>
     * 由于ZooKeeper是一个共享空间，因此给定集群的用户应该留在预定义的命名空间内。如果在此处设置了命名空间，则所有路径都将被该命名空间预先设置。
     */
    private String namespace;

    /**
     * 设置默认的数据
     */
    private String defaultData;

    /**
     * 认证信息列表，用于存储认证信息
     */
    @NestedConfigurationProperty
    private List<MeAuthInfo> authInfos = List.of();

    /**
     * Zookeeper工厂，用于创建Zookeeper客户端
     */
    private ZookeeperFactoryEnum zookeeperFactory = ZookeeperFactoryEnum.ADMIN;

    /**
     * 表示是否可以只读。
     * <p>
     * 如果为 true，则允许 zookeeper 客户端在网络分区的情况下进入只读模式
     */
    private boolean canBeReadOnly = false;

    /**
     * 表示是否使用容器父节点，如果可用的话，提高查找效率
     */
    private boolean useContainerParentsIfAvailable = true;

    /**
     * 等待关闭的超时时间，单位为毫秒
     */
    private int waitForShutdownTimeoutMs = 0;

    /**
     * 模拟的会话过期百分比，用于测试和验证会话过期的逻辑
     */
    private int simulatedSessionExpirationPercent = 100;

    /**
     * 重试策略
     */
    private RetryPolicyEnum retryPolicyEnum;

    /**
     * 最大尝试次
     */
    private int maxRetries;

    /**
     * 重试之间的睡眠时间，用于控制重试的频率，单位为毫秒
     * 如果使用{@link ExponentialBackoffRetry}及其子类时，表示为基础睡眠时间,后续重试时会以此为基础进行指数增长
     */
    private int sleepMsBetweenRetry;

    /**
     * 表示重试的最大持续时间，单位是毫秒
     * 重试将在这个时间段内持续进行，直到成功或达到时间上限为止
     */
    private int maxElapsedTimeMs;

    /**
     * 拒绝策略的bean名称
     */
    private String delegatePolicyBeanName;

    /**
     * 是否注册到spring
     */
    private boolean registerToSpring = false;

    /**
     * 获取当前类的拒绝策略实现类
     *
     * @return
     */
    public RetryPolicy generateRetryPolicy() {
        if (this.retryPolicyEnum == null) {
            return null;
        }
        switch (this.retryPolicyEnum) {
            case RETRY_N_TIMES -> {
                return new RetryNTimes(this.maxRetries, this.sleepMsBetweenRetry);
            }
            case RETRY_ONE_TIME -> {
                return new RetryOneTime(this.sleepMsBetweenRetry);
            }
            case RETRY_UNTIL_ELAPSED -> {
                return new RetryUntilElapsed(this.maxElapsedTimeMs, this.sleepMsBetweenRetry);
            }
            case EXPONENTIAL_BACKOFF_RETRY -> {
                return new ExponentialBackoffRetry(this.sleepMsBetweenRetry, this.maxRetries, this.maxElapsedTimeMs);
            }
            case BOUNDED_EXPONENTIAL_BACKOFF_RETRY -> {
                return new BoundedExponentialBackoffRetry(this.sleepMsBetweenRetry, this.maxElapsedTimeMs, this.maxRetries);
            }
            case RETRY_FOREVER -> {
                return new RetryForever(this.sleepMsBetweenRetry);
            }
            default -> {
                return Optional.ofNullable(this.delegatePolicyBeanName)
                        .flatMap(delegatePolicyBeanName -> Optional.of(SpringContextUtil.gainBean(delegatePolicyBeanName, RetryPolicy.class)))
                        .flatMap(retryPolicy -> Optional.of(new SessionFailedRetryPolicy(retryPolicy)))
                        .orElse(null);
            }
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

}
