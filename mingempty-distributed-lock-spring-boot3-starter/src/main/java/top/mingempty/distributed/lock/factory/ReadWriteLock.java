package top.mingempty.distributed.lock.factory;


/**
 * 读写锁
 *
 * @param <T>
 * @author zzhao
 */
public interface ReadWriteLock<T> {

    /**
     * 获取写锁
     */
    LockBase<T> readLock();

    /**
     * 获取读锁
     */
    LockBase<T> writeLock();


}
