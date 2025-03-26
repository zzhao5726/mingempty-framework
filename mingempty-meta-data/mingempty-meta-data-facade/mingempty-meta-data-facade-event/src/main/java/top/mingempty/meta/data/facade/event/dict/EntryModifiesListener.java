package top.mingempty.meta.data.facade.event.dict;

import cn.hutool.core.util.ObjUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.mingempty.event.record.listener.MeRecordApplicationListener;
import top.mingempty.meta.data.application.biz.dict.service.ItemApplicationService;
import top.mingempty.meta.data.domain.biz.dict.event.EntryModifiesEventInfo;
import top.mingempty.meta.data.domain.biz.dict.event.EntryModifiesRecordApplicationEvent;
import top.mingempty.meta.data.domain.biz.dict.model.AuthorizationModel;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ItemBaseModifiesInfo;
import top.mingempty.meta.data.facade.event.dict.converter.EntryEventConverter;


/**
 * 条目变化监听
 */
@Slf4j
@Component
@AllArgsConstructor
public class EntryModifiesListener
        extends MeRecordApplicationListener<EntryModifiesRecordApplicationEvent> {

    private final ItemApplicationService itemApplicationService;

    @Override
    public void listenerEvent(EntryModifiesRecordApplicationEvent entryModifiesRecordApplicationEvent) {
        EntryModifiesEventInfo entryModifies = entryModifiesRecordApplicationEvent.getEntryModifies();
        if (ObjUtil.isEmpty(entryModifies)) {
            return;
        }
        try {
            AuthorizationModel.disableCheckAuthorization();
            ItemBaseModifiesInfo itemBaseModifiesInfo = EntryEventConverter.eventToModifiesInfo(entryModifies);
            itemApplicationService.modifies(itemBaseModifiesInfo);
        } finally {
            AuthorizationModel.clearDisableCheckAuthorization();
        }
    }
}