package top.mingempty.distributed.lock.api;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁顶层抽象
 *
 * @author zzhao
 */
public interface DistributedLock {


    /**
     * 锁
     */
    void lock();

    /**
     * 锁，并设置锁的过期时间
     *
     * @param leaseTime 锁的过期时间
     * @param unit      锁的过期时间单位
     */
    void lock(long leaseTime, TimeUnit unit);

    /**
     * 尝试获取锁
     *
     * @return 获取锁的结果
     */
    boolean tryLock();

    /**
     * 在指定时间内尝试获取锁
     *
     * @param waitTime 获取锁的等待时间
     * @param unit     过期时间单位
     * @return 锁的获取结果
     */
    boolean tryLock(long waitTime, TimeUnit unit);

    /**
     * 在指定时间内尝试获取锁，并设置锁的过期时间
     *
     * @param waitTime  获取锁的等待时间
     * @param leaseTime 锁的过期时间
     * @param unit      锁的过期时间单位
     * @return 锁的获取结果
     */
    boolean tryLock(long waitTime, long leaseTime, TimeUnit unit);

    /**
     * 解锁
     */
    void unlock();

    /**
     * 是否锁定
     *
     * @return 锁的获取结果
     */
    boolean isLocked();
}
