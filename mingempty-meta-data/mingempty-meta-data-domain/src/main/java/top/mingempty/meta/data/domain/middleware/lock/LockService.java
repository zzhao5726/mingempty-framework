package top.mingempty.meta.data.domain.middleware.lock;

import top.mingempty.distributed.lock.api.DistributedLock;

import java.util.Collection;
import java.util.Set;

/**
 * 锁服务接口
 *
 * @author zzhao
 */
public interface LockService {

    /**
     * 获取锁
     *
     * @param lockKey
     * @return
     */
    DistributedLock lock(String lockKey);

    /**
     * 获取写锁
     *
     * @param lockKey
     * @return
     */
    DistributedLock writeLock(String lockKey);

    /**
     * 获取读锁
     *
     * @param lockKey
     * @return
     */
    DistributedLock readLock(String lockKey);

    /**
     * 获取多锁
     *
     * @param lockKeys
     * @return
     */
    default DistributedLock multiLock(String... lockKeys) {
        if (lockKeys == null) {
            return null;
        }
        return multiLock(Set.of(lockKeys));
    }

    /**
     * 获取多锁
     *
     * @param lockKeys
     * @return
     */
    DistributedLock multiLock(Collection<String> lockKeys);
}
