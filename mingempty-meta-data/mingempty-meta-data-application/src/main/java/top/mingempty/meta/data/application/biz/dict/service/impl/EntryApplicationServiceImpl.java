package top.mingempty.meta.data.application.biz.dict.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.application.biz.dict.service.EntryApplicationService;
import top.mingempty.meta.data.commons.exception.MetaDataException;
import top.mingempty.meta.data.domain.biz.dict.query.EntryQuery;
import top.mingempty.meta.data.domain.biz.dict.service.AuthorizationDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.EntryDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.ExtraFieldDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.ItemDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.LabelDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.OperationHistoryDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.info.EntryBaseInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.EntryInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.EntryBaseModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.OperationRecordInfo;
import top.mingempty.meta.data.domain.enums.DictOperationEnum;

import java.util.List;

/**
 * 字典条目应用层服务接口实现
 *
 * @author zzhao
 */
@Slf4j
@Service
@AllArgsConstructor
public class EntryApplicationServiceImpl implements EntryApplicationService {
    private final EntryDomainService entryDomainService;
    private final AuthorizationDomainService authorizationDomainService;
    private final ExtraFieldDomainService extraFieldDomainService;
    private final OperationHistoryDomainService operationHistoryDomainService;
    private final ItemDomainService itemDomainService;
    private final LabelDomainService labelDomainService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void modifies(EntryBaseModifiesInfo entryBaseModifiesInfo) {
        if (ObjUtil.isEmpty(entryBaseModifiesInfo)
                || StrUtil.isEmpty(entryBaseModifiesInfo.getEntryCode())) {
            throw new MetaDataException("meta-data-application-0000000001");
        }
        //权限校验
        authorizationDomainService.checkAuthorization(entryBaseModifiesInfo.getEntryCode());
        Long entryChanegVersion = 0L;
        if (ObjUtil.isNotEmpty(entryBaseModifiesInfo.getEntry())) {
            entryChanegVersion = operationHistoryDomainService.gainVersion(entryBaseModifiesInfo.getEntryCode());
            entryDomainService.modifies(entryBaseModifiesInfo.getEntryCode(), entryChanegVersion, entryBaseModifiesInfo.getEntry());

        }
        if (CollUtil.isNotEmpty(entryBaseModifiesInfo.getAuthorizations())) {
            entryChanegVersion = entryChanegVersion == 0
                    ? operationHistoryDomainService.gainVersion(entryBaseModifiesInfo.getEntryCode()) : entryChanegVersion;
            authorizationDomainService.modifies(entryBaseModifiesInfo.getEntryCode(), entryChanegVersion, entryBaseModifiesInfo.getAuthorizations());
        }
        if (CollUtil.isNotEmpty(entryBaseModifiesInfo.getExtraFields())) {
            entryChanegVersion = entryChanegVersion == 0
                    ? operationHistoryDomainService.gainVersion(entryBaseModifiesInfo.getEntryCode()) : entryChanegVersion;
            extraFieldDomainService.modifies(entryBaseModifiesInfo.getEntryCode(), entryChanegVersion, entryBaseModifiesInfo.getExtraFields());
        }
        if (entryChanegVersion != 0) {
            operationHistoryDomainService.record(OperationRecordInfo.builder()
                    .entryCode(entryBaseModifiesInfo.getEntryCode())
                    .entryVersion(entryChanegVersion)
                    .operationType(DictOperationEnum.code_1)
                    .build());
        } else {
            throw new MetaDataException("meta-data-application-0000000001");
        }
    }

    @Override
    public List<EntryInfo> list(EntryQuery entryQuery, final MePage mePage) {
        return entryDomainService.query(entryQuery, mePage);
    }

    @Override
    public EntryBaseInfo details(EntryQuery entryQuery) {
        if (ObjUtil.isEmpty(entryQuery)) {
            throw new MetaDataException("meta-data-application-0000000002");
        }
        List<EntryBaseInfo> entryBaseInfos = entryDomainService.queryBase(entryQuery);
        if (CollUtil.isEmpty(entryBaseInfos)) {
            return null;
        }
        return entryBaseInfos.getFirst();
    }
}
