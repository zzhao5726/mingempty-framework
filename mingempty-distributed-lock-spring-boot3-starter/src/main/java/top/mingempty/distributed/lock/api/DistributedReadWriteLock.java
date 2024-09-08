package top.mingempty.distributed.lock.api;

/**
 * 读写锁
 *
 * @author zzhao
 */
public interface DistributedReadWriteLock<T extends DistributedLock> {

    /**
     * 获取读锁
     *
     * @return
     */
    T readLock();

    /**
     * 获取写锁
     *
     * @return
     */
    T writeLock();
}
