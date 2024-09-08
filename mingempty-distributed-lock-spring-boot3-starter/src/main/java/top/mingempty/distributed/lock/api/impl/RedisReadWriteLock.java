package top.mingempty.distributed.lock.api.impl;

import lombok.AllArgsConstructor;
import org.redisson.api.RReadWriteLock;
import top.mingempty.distributed.lock.api.DistributedReadWriteLock;

/**
 * 基于redis实现分布式读写锁
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedisReadWriteLock implements DistributedReadWriteLock<RedisLock> {

    /**
     * 锁的实例
     */
    private final RReadWriteLock readWriteLock;

    /**
     * 获取读锁
     *
     * @return
     */
    @Override
    public RedisLock readLock() {
        return new RedisLock(readWriteLock.readLock());
    }

    /**
     * 获取写锁
     *
     * @return
     */
    @Override
    public RedisLock writeLock() {
        return new RedisLock(readWriteLock.writeLock());
    }
}
