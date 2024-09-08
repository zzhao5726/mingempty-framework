package top.mingempty.distributed.lock.api.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import top.mingempty.distributed.lock.api.DistributedLock;
import top.mingempty.distributed.lock.exception.ResubmitLockException;
import top.mingempty.distributed.lock.exception.ResubmitUnLockException;

import java.util.concurrent.TimeUnit;

/**
 * 基于redis的分布式锁实现
 *
 * @author zzhao
 */
@Slf4j
@AllArgsConstructor
public class RedisLock implements DistributedLock {

    private final RLock lock;

    /**
     * 锁
     */
    @Override
    public void lock() {
        try {
            lock.lock();
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
        try {
            lock.lock(leaseTime, unit);
        } catch (Exception e) {
            throw new ResubmitLockException("zk00000001", e);
        }
    }

    /**
     * 尝试获取锁
     *
     * @return 获取锁的结果
     */
    @Override
    public boolean tryLock() {
        try {
            return lock.tryLock();
        } catch (Exception e) {
            throw new ResubmitLockException("zk00000001", e);
        }
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
            if (waitTime <= 0) {
                return tryLock();
            }

            return lock.tryLock(waitTime, unit);
        } catch (InterruptedException e) {
            if (log.isDebugEnabled()) {
                log.warn("redis try lock interrupted exception", e);
            }
            return false;
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
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            if (log.isDebugEnabled()) {
                log.warn("redis try lock interrupted exception", e);
            }
            return false;
        } catch (Exception e) {
            throw new ResubmitLockException("zk00000002", e);
        }
    }

    /**
     * 解锁
     */
    @Override
    public void unlock() {
        try {
            lock.unlock();
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
        return lock.isLocked();
    }
}
