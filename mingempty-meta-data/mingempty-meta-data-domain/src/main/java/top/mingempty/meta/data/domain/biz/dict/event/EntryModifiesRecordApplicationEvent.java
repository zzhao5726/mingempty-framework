package top.mingempty.meta.data.domain.biz.dict.event;

import lombok.Getter;
import top.mingempty.event.record.model.MeRecordApplicationEvent;
import top.mingempty.meta.data.domain.biz.dict.service.EntryDomainService;

import java.util.Optional;

/**
 * 字典条目修改事件
 *
 * @author zzhao
 */
@Getter
public class EntryModifiesRecordApplicationEvent extends MeRecordApplicationEvent {

    private final EntryModifiesEventInfo entryModifies;

    public EntryModifiesRecordApplicationEvent(EntryModifiesEventInfo entryModifiesEventInfo) {
        super("ENTRY_MODIFIES", EntryDomainService.class);
        this.entryModifies = entryModifiesEventInfo;
        this.setBizNo(Optional.ofNullable(entryModifiesEventInfo)
                .map(EntryModifiesEventInfo::getEntryCode)
                .orElse(null));
    }
}
