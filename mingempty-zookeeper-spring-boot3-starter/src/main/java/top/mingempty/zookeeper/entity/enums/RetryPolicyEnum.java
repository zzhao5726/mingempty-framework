package top.mingempty.zookeeper.entity.enums;

import org.apache.curator.RetryPolicy;
import org.apache.curator.SessionFailedRetryPolicy;
import org.apache.curator.retry.BoundedExponentialBackoffRetry;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryForever;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.retry.RetryUntilElapsed;

/**
 * 重试策略
 *
 * @author zzhao
 */
public enum RetryPolicyEnum {

    RETRY_N_TIMES(RetryNTimes.class, "重试最大次数的重试策略", "n", "sleepMsBetweenRetries"),
    RETRY_ONE_TIME(RetryOneTime.class, "重试一次", "sleepMsBetweenRetry"),
    RETRY_UNTIL_ELAPSED(RetryUntilElapsed.class, "在给定的时间量之后再试", "maxElapsedTimeMs", "sleepMsBetweenRetries"),
    EXPONENTIAL_BACKOFF_RETRY(ExponentialBackoffRetry.class, "重试设定的次数，同时增加两次重试之间的睡眠时间", "baseSleepTimeMs", "maxRetries", "maxSleepMs"),
    BOUNDED_EXPONENTIAL_BACKOFF_RETRY(BoundedExponentialBackoffRetry.class, "重试设定的次数，重试之间的睡眠时间增加（最多达到最大限制）", "baseSleepTimeMs", "maxSleepTimeMs", "maxRetries"),
    RETRY_FOREVER(RetryForever.class, "始终允许重试", "sleepMsBetweenRetry"),
    SESSION_FAILED_RETRY_POLICY(SessionFailedRetryPolicy.class, "会话失败时使用特定的重试策略", "delegatePolicy"),
    ;


    /**
     * 描述
     */
    private final String desc;


    /**
     * 构建的类
     */
    private final Class<? extends RetryPolicy> retryPolicyClass;

    /**
     * 参数列表
     */
    private final String[] args;

    RetryPolicyEnum(Class<? extends RetryPolicy> retryPolicyClass, String desc, String... args) {
        this.retryPolicyClass = retryPolicyClass;
        this.desc = desc;
        this.args = args;
    }
}
