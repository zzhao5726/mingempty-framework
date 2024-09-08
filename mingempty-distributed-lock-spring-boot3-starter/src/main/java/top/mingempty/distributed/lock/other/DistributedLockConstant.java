package top.mingempty.distributed.lock.other;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 静态常量类
 *
 * @author zzhao
 */
@Schema(title = "常量类", description = "常量类")
public interface DistributedLockConstant {

    /**
     * Redis分隔符
     */
    String REDIS_SEPARATOR = ":";

    /**
     * zookeeper分隔符
     */
    String ZOOKEEPER_SEPARATOR = "/";

    /**
     * Redis统一锁前缀
     */
    String REDIS_RESUBMIT_LOCK_KEY_PREFIX = "RESUBMIT:LOCK:";

    /**
     * zookeeper统一锁前缀
     */
    String ZOOKEEPER_RESUBMIT_LOCK_KEY_PREFIX = "/RESUBMIT/LOCK/";
}
