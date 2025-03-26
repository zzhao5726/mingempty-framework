package top.mingempty.meta.data.domain.biz.dict.service.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.biz.dict.model.ItemModel;
import top.mingempty.meta.data.domain.biz.dict.service.info.ItemInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ItemModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.value.DeleteVo;
import top.mingempty.meta.data.domain.biz.dict.value.ItemVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 字典项信息领域服务层转换器
 *
 * @author zzhao
 */
public class ItemDomainConverter {


    public static ItemInfo voToInfo(ItemVo itemVo) {
        if (ObjUtil.isEmpty(itemVo)) {
            return null;
        }

        return ItemInfo.builder()
                .itemCode(itemVo.getItemCode())
                .itemParentCode(itemVo.getItemParentCode())
                .itemName(itemVo.getItemName())
                .itemSort(itemVo.getItemSort())
                .itemLevel(itemVo.getItemLevel())
                .deleteStatus(Optional.ofNullable(itemVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteStatus)
                        .orElse(ZeroOrOneEnum.ZERO))
                .deleteOperator(Optional.ofNullable(itemVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteOperator)
                        .orElse(null))
                .deleteTime(Optional.ofNullable(itemVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteTime)
                        .orElse(null))
                .extraFields(itemVo.getItemExtraFields())
                .build();

    }

    /**
     * 将领域模型转换为内部传输对象
     *
     * @param itemModel
     * @return
     */
    public static List<ItemInfo> modelToInfos(ItemModel itemModel) {
        if (ObjUtil.isEmpty(itemModel)
                || CollUtil.isEmpty(itemModel.getItems())) {
            return new CopyOnWriteArrayList<>();
        }
        return itemModel.getItems()
                .parallelStream()
                .map(ItemDomainConverter::voToInfo)
                .filter(Objects::nonNull)
                .peek(itemInfo -> itemInfo.setEntryCode(itemModel.getEntryCode())
                        .setEntryVersion(itemModel.getEntryVersion()))
                .collect(Collectors.toList());
    }

    /**
     * 将领域模型集合转换为内部传输对象集合
     *
     * @param itemModels
     * @return
     */
    public static List<ItemInfo> modelToInfos(Collection<ItemModel> itemModels) {
        if (CollUtil.isEmpty(itemModels)) {
            return new CopyOnWriteArrayList<>();
        }

        return itemModels
                .parallelStream()
                .map(ItemDomainConverter::modelToInfos)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());

    }

    /**
     * 条目字典项修改信息转换
     *
     * @param itemModifiesInfo 条目字典项修改信息
     * @return
     */
    public static ItemVo modifiesInfoToVo(ItemModifiesInfo itemModifiesInfo) {
        if (ObjUtil.isEmpty(itemModifiesInfo)) {
            return null;
        }
        ItemVo itemVo = ItemVo.builder()
                .itemCode(itemModifiesInfo.getItemCode())
                .itemParentCode(itemModifiesInfo.getItemParentCode())
                .itemName(itemModifiesInfo.getItemName())
                .itemSort(itemModifiesInfo.getItemSort())
                .itemLevel(itemModifiesInfo.getItemLevel())
                .deleteStatus(DeleteVo.builder()
                        .deleteStatus(Optional
                                .ofNullable(itemModifiesInfo.getDeleteStatus())
                                .orElse(ZeroOrOneEnum.ZERO))
                        .build())
                .build();
        Map<String, Object> modifiesExtraField = Optional.ofNullable(itemModifiesInfo.getExtraFields())
                .orElse(Map.of());
        return itemVo.modifiesExtraField(modifiesExtraField);
    }

    /**
     * 条目字典项修改信息集合转换
     *
     * @param itemModifiesInfos 条目字典项修改信息集合
     * @return
     */
    public static List<ItemVo> modifiesInfoToVos(Collection<ItemModifiesInfo> itemModifiesInfos) {
        if (CollUtil.isEmpty(itemModifiesInfos)) {
            return new CopyOnWriteArrayList<>();
        }
        return itemModifiesInfos.parallelStream()
                .map(ItemDomainConverter::modifiesInfoToVo)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
