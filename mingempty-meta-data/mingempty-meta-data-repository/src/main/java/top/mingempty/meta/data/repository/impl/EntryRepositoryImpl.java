package top.mingempty.meta.data.repository.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.mingempty.distributed.lock.api.DistributedLock;
import top.mingempty.meta.data.commons.util.EntryVersionUtil;
import top.mingempty.meta.data.domain.biz.dict.repository.CacheRepository;
import top.mingempty.meta.data.domain.biz.dict.repository.EntryRepository;
import top.mingempty.meta.data.domain.enums.RedisCacheKeyEnum;
import top.mingempty.meta.data.domain.middleware.lock.LockService;
import top.mingempty.meta.data.repository.service.OperationHistoryService;

import java.util.concurrent.TimeUnit;


/**
 * * 字典条目仓储接口实现
 * *
 * * @author zzhao
 */
@Slf4j
@Component
public class EntryRepositoryImpl implements EntryRepository {

    @Resource
    private LockService lockService;

    @Resource
    private CacheRepository cacheRepository;

    @Resource
    private OperationHistoryService operationHistoryService;


    @Override
    public Long gainVersion(String entryCode) {
        Long version = EntryVersionUtil.gainVersion(entryCode);
        if (version != null) {
            return version;
        }

        DistributedLock distributedLock = lockService.lock(RedisCacheKeyEnum.CHANGE_LOCK.gainKey(entryCode));
        try {
            if (distributedLock.tryLock(1, 1, TimeUnit.MINUTES)) {
                version = EntryVersionUtil.gainVersion(entryCode);
                if (version != null) {
                    return version;
                }
                version = cacheRepository.gainVersion(entryCode);
                if (version > 1) {
                    EntryVersionUtil.settingVersion(entryCode, version);
                    return version;
                }
                //说明是第一次，或者是缓存不存在了
                //数据库查询最大版本
                Long maxVersion = operationHistoryService.gainMaxVersion(entryCode);
                //设置缓存
                version = cacheRepository.settingVersion(entryCode, maxVersion);
                EntryVersionUtil.settingVersion(entryCode, version);
                return version;
            }
            return 0L;
        } finally {
            distributedLock.unlock();
        }
    }
}
