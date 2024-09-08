package top.mingempty.distributed.lock.factory;

import java.util.List;

/**
 * 锁基础抽象接口
 *
 * @param <T>
 * @author zzhao
 */
public interface Lock<T> {


    /**
     * 锁的key
     *
     * @param lockKey 锁的key
     */
    LockBase<T> key(String lockKey);

    /**
     * 获取联锁
     *
     * @param lockKeys 联锁的key
     */
    MultiLock<T> multiLock(List<String> lockKeys);

    /**
     * 获取读写锁
     *
     * @param lockKey 锁的key
     */
    ReadWriteLock<T> readWriteLock(String lockKey);


}
