package top.mingempty.distributed.lock.factory;


import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.util.Assert;
import top.mingempty.cache.redis.api.RedisCacheApi;
import top.mingempty.distributed.lock.api.DistributedReadWriteLock;
import top.mingempty.distributed.lock.api.impl.RedisLock;
import top.mingempty.distributed.lock.api.impl.RedisReadWriteLock;
import top.mingempty.util.SpringContextUtil;

import java.util.List;

/**
 * redis锁构建
 *
 * @author zzhao
 */
public class RedisLockBuilder implements Lock<RedisLock> {

    private final RedissonClient redissonClient;

    public RedisLockBuilder(String instanceName) {
        RedisCacheApi redisCacheApi = SpringContextUtil.gainBean(RedisCacheApi.class);
        Assert.notNull(redisCacheApi, "redisCacheApi is null");
        this.redissonClient = redisCacheApi.redissonClientForInstance(instanceName);
    }


    /**
     * 锁的key
     *
     * @param lockKey 锁的key
     */
    @Override
    public LockBase<RedisLock> key(String lockKey) {
        return () -> new RedisLock(redissonClient.getLock(lockKey));
    }

    /**
     * 获取联锁
     *
     * @param lockKeys 联锁的key
     */
    @Override
    public MultiLock<RedisLock> multiLock(List<String> lockKeys) {
        RLock[] rLocks = (RLock[]) lockKeys.parallelStream().map(redissonClient::getLock).toArray();
        return () -> new RedisLock(redissonClient.getMultiLock(rLocks));
    }

    /**
     * 获取读写锁
     *
     * @param lockKey 锁的key
     */
    @Override
    public ReadWriteLock<RedisLock> readWriteLock(String lockKey) {
        return new ReadWriteLock<>() {
            private final DistributedReadWriteLock<RedisLock> readWriteLock
                    = new RedisReadWriteLock(redissonClient.getReadWriteLock(lockKey));

            @Override
            public LockBase<RedisLock> readLock() {
                return readWriteLock::readLock;
            }

            @Override
            public LockBase<RedisLock> writeLock() {
                return readWriteLock::writeLock;
            }
        };
    }
}
