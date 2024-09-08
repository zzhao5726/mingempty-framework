package top.mingempty.distributed.lock.factory;

import top.mingempty.domain.other.GlobalConstant;

/**
 * 锁的工厂
 *
 * @author zzhao
 */
public class LockFactory {


    /**
     * 获取redis锁工厂
     */
    public static RedisLockBuilder redisLock() {
        return redisLock(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }


    /**
     * 获取redis锁工厂
     *
     * @param instanceName 实例名称
     */
    public static RedisLockBuilder redisLock(String instanceName) {
        return new RedisLockBuilder(instanceName);
    }


    /**
     * 获取zookeeper锁工厂
     */
    public static ZookeeperLockBuilder zookeeperLock() {
        return zookeeperLock(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }


    /**
     * 获取zookeeper锁工厂
     *
     * @param instanceName 实例名称
     */
    public static ZookeeperLockBuilder zookeeperLock(String instanceName) {
        return new ZookeeperLockBuilder(instanceName);
    }


}
