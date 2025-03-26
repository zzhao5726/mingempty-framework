package top.mingempty.meta.data.application.biz.dict.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.application.biz.dict.service.ItemApplicationService;
import top.mingempty.meta.data.commons.exception.MetaDataException;
import top.mingempty.meta.data.domain.biz.dict.query.ItemQuery;
import top.mingempty.meta.data.domain.biz.dict.service.AuthorizationDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.EntryDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.ExtraFieldDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.ItemDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.LabelDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.OperationHistoryDomainService;
import top.mingempty.meta.data.domain.biz.dict.service.info.ItemInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ItemBaseModifiesInfo;

import java.util.List;

/**
 * 字典项应用层服务接口实现
 */
@Slf4j
@Service
@AllArgsConstructor
public class ItemApplicationServiceImpl implements ItemApplicationService {
    private final EntryDomainService entryDomainService;
    private final AuthorizationDomainService authorizationDomainService;
    private final ExtraFieldDomainService extraFieldDomainService;
    private final OperationHistoryDomainService operationHistoryDomainService;
    private final ItemDomainService itemDomainService;
    private final LabelDomainService labelDomainService;

    @Override
    public void modifies(ItemBaseModifiesInfo itemBaseModifiesInfo) {
        if (ObjUtil.isEmpty(itemBaseModifiesInfo)
                || StrUtil.isEmpty(itemBaseModifiesInfo.getEntryCode())) {
            throw new MetaDataException("meta-data-application-0000000003");
        }
        if (CollUtil.isEmpty(itemBaseModifiesInfo.getItems())) {
            return;
        }
        //权限校验
        authorizationDomainService.checkAuthorization(itemBaseModifiesInfo.getEntryCode());
        Long entryChanegVersion = operationHistoryDomainService.gainVersion(itemBaseModifiesInfo.getEntryCode());
        itemDomainService.modifies(itemBaseModifiesInfo.getEntryCode(), entryChanegVersion, itemBaseModifiesInfo.getItems());
    }

    @Override
    public List<ItemInfo> list(ItemQuery itemQuery, MePage mePage) {
        return itemDomainService.query(itemQuery, mePage);
    }
}
