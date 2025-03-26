package top.mingempty.meta.data.domain.biz.dict.service;

import cn.hutool.core.util.ObjUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.mingempty.distributed.lock.api.DistributedLock;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.commons.util.EntryVersionUtil;
import top.mingempty.meta.data.domain.biz.dict.model.OperationHistoryModel;
import top.mingempty.meta.data.domain.biz.dict.query.OperationHistoryQuery;
import top.mingempty.meta.data.domain.biz.dict.repository.OperationHistoryRepository;
import top.mingempty.meta.data.domain.biz.dict.service.converter.OperationHistoryDomainConverter;
import top.mingempty.meta.data.domain.biz.dict.service.info.OperationHistoryInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.OperationRecordInfo;
import top.mingempty.meta.data.domain.biz.dict.value.OperationHistoryVo;
import top.mingempty.meta.data.domain.enums.RedisCacheKeyEnum;
import top.mingempty.meta.data.domain.middleware.cache.repository.CacheRepository;
import top.mingempty.meta.data.domain.middleware.lock.LockService;
import top.mingempty.meta.data.domain.rpc.auth.AuthClient;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 字典操作历史领域服务
 */
@Slf4j
@Service
@AllArgsConstructor
public class OperationHistoryDomainService implements BaseDomainService<OperationHistoryInfo, OperationHistoryQuery> {

    private final OperationHistoryRepository operationHistoryRepository;

    private final LockService lockService;

    private final CacheRepository cacheRepository;

    private final AuthClient authClient;

    /**
     * 获取条目最新版本
     *
     * @param entryCode 条目编码
     * @return
     */
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
                Long maxVersion = operationHistoryRepository.gainMaxVersion(entryCode);
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

    /**
     * 记录操作历史
     *
     * @param operationRecordInfo 操作记录信息
     */
    public void record(OperationRecordInfo operationRecordInfo) {
        if (ObjUtil.isEmpty(operationRecordInfo)
                || ObjUtil.isEmpty(operationRecordInfo.getEntryCode())) {
            return;
        }
        OperationHistoryModel operationHistoryModel
                = new OperationHistoryModel(operationRecordInfo.getEntryCode(), List.of());
        OperationHistoryVo operationHistoryVo = OperationHistoryDomainConverter.recordInfoToVo(operationRecordInfo, authClient.gainUserCode());
        operationHistoryModel.recordToModel(operationHistoryVo);
        operationHistoryRepository.record(operationHistoryModel);
    }

    @Override
    public List<OperationHistoryInfo> query(OperationHistoryQuery operationHistoryQuery, final MePage page) {
        List<OperationHistoryModel> operationHistoryModels = operationHistoryRepository.query(operationHistoryQuery, page);
        return OperationHistoryDomainConverter.modelToInfos(operationHistoryModels);
    }

    @Override
    public OperationHistoryQuery gainQuery(String entryCode) {
        return OperationHistoryQuery.builder().entryCode(entryCode).build();
    }

    @Override
    public OperationHistoryQuery gainQuery(Collection<String> entryCodes) {
        return OperationHistoryQuery.builder().entryCodes(entryCodes).build();
    }
}
