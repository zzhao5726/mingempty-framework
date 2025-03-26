package top.mingempty.meta.data.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.mingempty.cache.redis.api.RedisCacheApi;
import top.mingempty.meta.data.domain.biz.dict.repository.CacheRepository;
import top.mingempty.meta.data.domain.enums.RedisCacheKeyEnum;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * 基于redis的缓存仓储接口实现
 *
 * @author zzhao
 */
@Slf4j
@Component
public class CacheRepositoryImpl implements CacheRepository {

    @Autowired
    private RedisCacheApi redisCacheApi;

    @Override
    public Long gainVersion(String entryCode) {
        RAtomicLong rAtomicLong = gainRAtomicLong(entryCode);
        try {
            return rAtomicLong.incrementAndGet();
        } finally {
            expireWithMaxVersion(rAtomicLong);
        }
    }

    @Override
    public Long settingVersion(String entryCode, Long entrytVersion) {
        RAtomicLong rAtomicLong = gainRAtomicLong(entryCode);
        try {
            rAtomicLong.set(entrytVersion);
            return rAtomicLong.incrementAndGet();
        } finally {
            expireWithMaxVersion(rAtomicLong);
        }
    }


    /**
     * 获取条目版本的基于redis的原子控制器
     *
     * @param entryCode
     * @return
     */
    private RAtomicLong gainRAtomicLong(String entryCode) {
        return redisCacheApi.redissonClient().getAtomicLong(RedisCacheKeyEnum.CHANGE_VERSION.gainKey(entryCode));
    }


    /**
     * 设置获取条目版本的key的过期时间
     *
     * @param atomicLong
     */
    private void expireWithMaxVersion(RAtomicLong atomicLong) {
        //设置过期时间
        atomicLong.expireAsync(Duration.of(30, ChronoUnit.MINUTES));
    }
}
