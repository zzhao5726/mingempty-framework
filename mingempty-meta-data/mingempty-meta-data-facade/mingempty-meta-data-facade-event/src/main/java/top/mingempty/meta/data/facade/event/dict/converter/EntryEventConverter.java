package top.mingempty.meta.data.facade.event.dict.converter;

import cn.hutool.core.util.ObjUtil;
import top.mingempty.domain.enums.BuiltInDictEntryEnum;
import top.mingempty.meta.data.domain.biz.dict.event.EntryModifiesEventInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ItemBaseModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ItemModifiesInfo;

import java.util.List;

/**
 * 条目事件转换器
 */
public class EntryEventConverter {


    public static ItemBaseModifiesInfo eventToModifiesInfo(EntryModifiesEventInfo entryModifiesEventInfo) {
        if (ObjUtil.isNotEmpty(entryModifiesEventInfo)) {
            return null;
        }
        ItemModifiesInfo itemModifiesInfo = ItemModifiesInfo.builder()
                .itemParentCode("#")
                .itemCode(entryModifiesEventInfo.getEntryCode())
                .itemName(entryModifiesEventInfo.getEntryName())
                .itemSort(entryModifiesEventInfo.getSort())
                .itemLevel(1L)
                .deleteStatus(entryModifiesEventInfo.getDeleteStatus())
                .build();
        return ItemBaseModifiesInfo.builder()
                .entryCode(BuiltInDictEntryEnum.ENTRY_LIST.getEntryCode())
                .items(List.of(itemModifiesInfo))
                .build();
    }
}
