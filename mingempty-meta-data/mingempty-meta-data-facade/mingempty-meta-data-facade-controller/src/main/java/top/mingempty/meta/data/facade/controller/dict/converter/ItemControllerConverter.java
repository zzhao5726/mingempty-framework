package top.mingempty.meta.data.facade.controller.dict.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import top.mingempty.commons.util.CollectionUtil;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.domain.other.MePubConditions;
import top.mingempty.meta.data.domain.biz.dict.query.ItemQuery;
import top.mingempty.meta.data.domain.biz.dict.service.info.ItemInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ItemBaseModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ItemModifiesInfo;
import top.mingempty.meta.data.facade.domain.dict.ItemBaseModifiesDto;
import top.mingempty.meta.data.facade.domain.dict.ItemListDto;
import top.mingempty.meta.data.facade.domain.dict.ItemListQueryDto;
import top.mingempty.meta.data.facade.domain.dict.ItemModifiesDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 条目相关控制器转换器
 *
 * @author zzhao
 */
public class ItemControllerConverter {


    public static ItemBaseModifiesInfo modifiesDtoToInfo(ItemBaseModifiesDto itemBaseModifiesDto) {
        if (ObjUtil.isEmpty(itemBaseModifiesDto)) {
            return null;
        }
        return ItemBaseModifiesInfo.builder()
                .entryCode(itemBaseModifiesDto.getEntryCode())
                .items(modifiesDtoToInfo(itemBaseModifiesDto.getItems()))
                .build();
    }

    public static ItemModifiesInfo modifiesDtoToInfo(ItemModifiesDto itemModifiesDto) {
        if (ObjUtil.isEmpty(itemModifiesDto)) {
            return null;
        }
        return ItemModifiesInfo.builder()
                .itemParentCode(itemModifiesDto.getItemParentCode())
                .itemCode(itemModifiesDto.getItemCode())
                .itemName(itemModifiesDto.getItemName())
                .itemSort(itemModifiesDto.getItemSort())
                .itemLevel(itemModifiesDto.getItemLevel())
                .deleteStatus(BaseControllerConverter.zeroOrOneEnumWithDefault(itemModifiesDto.getDeleteStatus()))
                .extraFields(new ConcurrentHashMap<>(Optional.ofNullable(itemModifiesDto.getExtraFields()).orElse(Map.of())))
                .build();
    }

    public static List<ItemModifiesInfo> modifiesDtoToInfo(Collection<ItemModifiesDto> itemModifiesDtos) {
        if (CollUtil.isEmpty(itemModifiesDtos)) {
            return List.of();
        }

        return itemModifiesDtos.parallelStream()
                .map(ItemControllerConverter::modifiesDtoToInfo)
                .filter(ObjUtil::isNotNull)
                .collect(Collectors.toList());
    }

    public static ItemQuery listQueryDtoToQueryInfo(ItemListQueryDto itemListQueryDto) {
        if (ObjUtil.isEmpty(itemListQueryDto)) {
            return ItemQuery.builder().build();
        }
        Collection<MePubConditions.ValueCondition> itemExtraFieldConditions
                = Optional.ofNullable(itemListQueryDto.getItemExtraFieldConditions())
                .map(MePubConditions::cloneValueConditions)
                .orElse(List.of());

        return ItemQuery.builder()
                .selectColumns(itemListQueryDto.getDetailsFlag() ? null : ItemQuery.NO_DETAILS_SELECT_COLUMNS)
                .entryCode(itemListQueryDto.getEntryCode())
                .entryCodeLike(itemListQueryDto.getEntryCodeLike())
                .entryCodes(itemListQueryDto.getEntryCodes())
                .entryVersion(itemListQueryDto.getEntryVersion())
                .itemParentCode(itemListQueryDto.getItemParentCode())
                .labelCode(itemListQueryDto.getLabelCode())
                .itemCode(itemListQueryDto.getItemCode())
                .itemCodeLike(itemListQueryDto.getItemCodeLike())
                .itemCodes(itemListQueryDto.getItemCodes())
                .itemName(itemListQueryDto.getItemName())
                .itemNameLike(itemListQueryDto.getItemNameLike())
                .deleteStatus(BaseControllerConverter.zeroOrOneEnum(itemListQueryDto.getDeleteStatus()))
                .deleteTime(itemListQueryDto.getDeleteTime())
                .deleteOperator(itemListQueryDto.getDeleteOperator())
                .itemExtraFieldConditions(itemExtraFieldConditions)
                .build();
    }

    public static Map<String, List<ItemListDto>> infoToMap(List<ItemInfo> itemInfos) {
        if (CollUtil.isEmpty(itemInfos)) {
            return Map.of();
        }
        return itemInfos.parallelStream()
                .map(ItemControllerConverter::infoToDto)
                .filter(ObjUtil::isNotNull)
                .collect(Collectors.groupingBy(ItemListDto::getEntryCode));
    }

    private static ItemListDto infoToDto(ItemInfo itemInfo) {
        if (ObjUtil.isEmpty(itemInfo)) {
            return null;
        }
        return ItemListDto.builder()
                .entryCode(itemInfo.getEntryCode())
                .itemParentCode(itemInfo.getItemParentCode())
                .itemCode(itemInfo.getItemCode())
                .itemName(itemInfo.getItemName())
                .itemSort(itemInfo.getItemSort())
                .itemLevel(itemInfo.getItemLevel())
                .deleteStatus(Optional.ofNullable(itemInfo.getDeleteStatus())
                        .map(ZeroOrOneEnum::getItemCode)
                        .orElse(ZeroOrOneEnum.ZERO.getItemCode()))
                .deleteTime(itemInfo.getDeleteTime())
                .deleteOperator(itemInfo.getDeleteOperator())
                .extraFields(itemInfo.getExtraFields() == null ? Map.of() : Map.copyOf(itemInfo.getExtraFields()))
                .build();
    }

    public static void toTree(Map<String, List<ItemListDto>> itemListDtosMap) {
        if (CollUtil.isEmpty(itemListDtosMap)) {
            return;
        }
        itemListDtosMap
                .entrySet()
                .forEach(entry -> {
                    Collection<ItemListDto> itemListDtos = entry.getValue();
                    if (CollUtil.isEmpty(itemListDtos)) {
                        return;
                    }
                    List<ItemListDto> ts = CollectionUtil.bulidTree(new CopyOnWriteArrayList<>(itemListDtos),
                            ItemListDto::getItemCode,
                            ItemListDto::getItemParentCode,
                            ItemListDto::setChildren,
                            "#");
                    entry.setValue(ts);
                });
    }
}
