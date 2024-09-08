package top.mingempty.distributed.lock.factory;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.springframework.util.Assert;
import top.mingempty.distributed.lock.api.DistributedReadWriteLock;
import top.mingempty.distributed.lock.api.impl.ZookeeperLock;
import top.mingempty.distributed.lock.api.impl.ZookeeperReadWriteLock;
import top.mingempty.util.SpringContextUtil;
import top.mingempty.zookeeper.api.ZookeeperApi;

import java.util.List;

/**
 * zookeeper锁构建
 *
 * @author zzhao
 */
public class ZookeeperLockBuilder implements Lock<ZookeeperLock> {

    /**
     * zk客户端
     */
    private final CuratorFramework curatorFramework;

    public ZookeeperLockBuilder(String instanceName) {
        ZookeeperApi zookeeperApi = SpringContextUtil.gainBean(ZookeeperApi.class);
        Assert.notNull(zookeeperApi, "zookeeperApi is null");
        this.curatorFramework = zookeeperApi.gainCuratorFrameworkForInstance(instanceName);
    }

    /**
     * 锁的key
     *
     * @param lockKey 锁的key
     */
    @Override
    public LockBase<ZookeeperLock> key(String lockKey) {
        return () -> new ZookeeperLock(new InterProcessMutex(curatorFramework, lockKey));
    }

    /**
     * 获取联锁
     *
     * @param lockKeys 联锁的key
     */
    @Override
    public MultiLock<ZookeeperLock> multiLock(List<String> lockKeys) {
        return () -> new ZookeeperLock(new InterProcessMultiLock(curatorFramework, lockKeys));
    }

    /**
     * 获取读写锁
     *
     * @param lockKey 锁的key
     */
    @Override
    public ReadWriteLock<ZookeeperLock> readWriteLock(String lockKey) {
        return new ReadWriteLock<>() {
            private final DistributedReadWriteLock<ZookeeperLock> readWriteLock
                    = new ZookeeperReadWriteLock(new InterProcessReadWriteLock(curatorFramework, lockKey));

            @Override
            public LockBase<ZookeeperLock> readLock() {
                return readWriteLock::readLock;
            }

            @Override
            public LockBase<ZookeeperLock> writeLock() {
                return readWriteLock::writeLock;
            }
        };
    }
}
