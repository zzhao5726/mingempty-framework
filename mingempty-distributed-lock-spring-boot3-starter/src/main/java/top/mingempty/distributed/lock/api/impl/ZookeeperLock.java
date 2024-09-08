package top.mingempty.distributed.lock.api.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import top.mingempty.distributed.lock.api.DistributedLock;
import top.mingempty.distributed.lock.exception.ResubmitLockException;
import top.mingempty.distributed.lock.exception.ResubmitUnLockException;

import java.util.concurrent.TimeUnit;

/**
 * 基于zookeeper实现分布式锁
 *
 * @author zzhao
 */
@Slf4j
@AllArgsConstructor
public class ZookeeperLock implements DistributedLock {

    /**
     * 锁的实例
     */
    private final InterProcessLock interProcessLock;

    /**
     * 锁
     */
    @Override
    public void lock() {
        try {
            interProcessLock.acquire();
        } catch (Exception e) {
            throw new ResubmitLockException("zk00000001", e);
        }
    }

    /**
     * 锁，并设置锁的过期时间
     *
     * @param leaseTime 锁的过期时间
     * @param unit      锁的过期时间单位
     */
    @Override
    public void lock(long leaseTime, TimeUnit unit) {
        log.warn("The leaseTime parameter invalidates zookeeper's lock method.");
        lock();
    }

    /**
     * 尝试获取锁
     *
     * @return 获取锁的结果
     */
    @Override
    public boolean tryLock() {
        return tryLock(0, TimeUnit.MILLISECONDS);
    }

    /**
     * 在指定时间内尝试获取锁
     *
     * @param waitTime 获取锁的等待时间
     * @param unit     过期时间单位
     * @return 锁的获取结果
     */
    @Override
    public boolean tryLock(long waitTime, TimeUnit unit) {
        try {
            return interProcessLock.acquire(waitTime, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new ResubmitLockException("zk00000002", e);
        }
    }

    /**
     * 在指定时间内尝试获取锁，并设置锁的过期时间
     *
     * @param waitTime  获取锁的等待时间
     * @param leaseTime 锁的过期时间
     * @param unit      锁的过期时间单位
     * @return 锁的获取结果
     */
    @Override
    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) {
        log.warn("The leaseTime parameter invalidates zookeeper's tryLock method.");
        return tryLock(waitTime, unit);
    }

    /**
     * 解锁
     */
    @Override
    public void unlock() {
        try {
            interProcessLock.release();
        } catch (Exception e) {
            throw new ResubmitUnLockException("zk00000003", e);
        }
    }

    /**
     * 是否锁定
     *
     * @return 锁的获取结果
     */
    @Override
    public boolean isLocked() {
        return interProcessLock.isAcquiredInThisProcess();
    }

}
