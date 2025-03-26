package top.mingempty.meta.data.infrastructure.middleware.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.mingempty.distributed.lock.api.DistributedLock;
import top.mingempty.distributed.lock.factory.LockFactory;
import top.mingempty.meta.data.domain.middleware.lock.LockService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 锁服务实现类
 *
 * @author zzhao
 */
@Slf4j
@Component
public class LockServiceImpl implements LockService {

    @Override
    public DistributedLock lock(String lockKey) {
        return LockFactory.redisLock().key(lockKey).lock();
    }

    @Override
    public DistributedLock writeLock(String lockKey) {
        return LockFactory.redisLock().readWriteLock(lockKey).writeLock().lock();
    }

    @Override
    public DistributedLock readLock(String lockKey) {
        return LockFactory.redisLock().readWriteLock(lockKey).readLock().lock();
    }

    @Override
    public DistributedLock multiLock(Collection<String> lockKeys) {
        return LockFactory.redisLock().multiLock(new ArrayList<>(lockKeys)).lock();
    }
}
