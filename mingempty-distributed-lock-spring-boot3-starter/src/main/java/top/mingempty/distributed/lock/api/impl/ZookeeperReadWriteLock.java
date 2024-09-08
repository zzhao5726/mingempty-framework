package top.mingempty.distributed.lock.api.impl;

import lombok.AllArgsConstructor;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import top.mingempty.distributed.lock.api.DistributedReadWriteLock;

/**
 * 基于zookeeper实现分布式读写锁
 *
 * @author zzhao
 */
@AllArgsConstructor
public class ZookeeperReadWriteLock implements DistributedReadWriteLock<ZookeeperLock> {

    /**
     * 锁的实例
     */
    private final InterProcessReadWriteLock interProcessReadWriteLock;

    /**
     * 获取读锁
     *
     * @return
     */
    @Override
    public ZookeeperLock readLock() {
        return new ZookeeperLock(interProcessReadWriteLock.readLock());
    }

    /**
     * 获取写锁
     *
     * @return
     */
    @Override
    public ZookeeperLock writeLock() {
        return new ZookeeperLock(interProcessReadWriteLock.writeLock());
    }
}
