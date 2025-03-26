package top.mingempty.meta.data.repository.impl.dict.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.domain.enums.BaseMetaData;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.biz.dict.model.ItemModel;
import top.mingempty.meta.data.domain.biz.dict.value.DeleteVo;
import top.mingempty.meta.data.domain.biz.dict.value.ItemVo;
import top.mingempty.meta.data.repository.model.po.ChangeItemPo;
import top.mingempty.meta.data.repository.model.po.ItemPo;
import top.mingempty.sequence.enums.SeqRealizeEnum;
import top.mingempty.sequence.factory.SequenceFactory;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 字典条目字典项仓储层转换器
 *
 * @author zzhao
 */
public class ItemRepositoryConverter {

    /**
     * po数据库实体转model领域模型
     *
     * @param itemPo po数据库实体
     * @return model领域模型
     */
    public static ItemVo poToVo(ItemPo itemPo) {
        if (ObjUtil.isEmpty(itemPo)) {
            return null;
        }
        DeleteVo deleteVo = DeleteVo.builder()
                .deleteStatus(BaseMetaData.EnumHelper.INSTANCE.findOptional(ZeroOrOneEnum.class, itemPo.getDeleteStatus())
                        .orElse(ZeroOrOneEnum.ZERO))
                .deleteOperator(itemPo.getDeleteOperator())
                .deleteTime(itemPo.getDeleteTime())
                .build();
        ItemVo itemVo = ItemVo.builder()
                .itemCode(itemPo.getItemCode())
                .itemParentCode(itemPo.getItemParentCode())
                .itemName(itemPo.getItemName())
                .itemSort(itemPo.getItemSort())
                .itemLevel(itemPo.getItemLevel())
                .deleteStatus(deleteVo)
                .build();
        return poToVoByExtraField(itemVo, itemPo.getItemExtraField());
    }


    /**
     * po数据库实体转model领域模型
     *
     * @param changeItemPo po数据库实体
     * @return model领域模型
     */
    public static ItemVo changePoToToVo(ChangeItemPo changeItemPo) {
        if (ObjUtil.isEmpty(changeItemPo)) {
            return null;
        }
        DeleteVo deleteVo = DeleteVo.builder()
                .deleteStatus(BaseMetaData.EnumHelper.INSTANCE.findOptional(ZeroOrOneEnum.class, changeItemPo.getDeleteStatus())
                        .orElse(ZeroOrOneEnum.ZERO))
                .deleteOperator(changeItemPo.getDeleteOperator())
                .deleteTime(changeItemPo.getDeleteTime())
                .build();
        ItemVo itemVo = ItemVo.builder()
                .itemCode(changeItemPo.getItemCode())
                .itemParentCode(changeItemPo.getItemParentCode())
                .itemName(changeItemPo.getItemName())
                .itemSort(changeItemPo.getItemSort())
                .itemLevel(changeItemPo.getItemLevel())
                .deleteStatus(deleteVo)
                .build();

        return poToVoByExtraField(itemVo, changeItemPo.getItemExtraField());
    }

    /**
     * po数据库实体转model领域模型
     * <p>
     * 处理扩展字段
     *
     * @param itemVo
     * @param itemExtraField
     * @return
     */
    private static ItemVo poToVoByExtraField(ItemVo itemVo, String itemExtraField) {
        if (StrUtil.isEmpty(itemExtraField)) {
            return itemVo;
        }

        Map<String, Object> itemExtraFields = JsonUtil.toObj(itemExtraField, new TypeReference<>() {
            @Override
            public Type getType() {
                return Map.class;
            }
        });
        return itemVo.modifiesExtraField(itemExtraFields);
    }


    /**
     * model领域值对象转po数据库实体
     *
     * @param itemVo model领域值对象
     * @return po数据库实体
     */
    public static ItemPo voToPo(String entryCode, ItemVo itemVo) {
        if (StrUtil.isEmpty(entryCode)
                || ObjUtil.isEmpty(itemVo)) {
            return null;
        }

        return ItemPo.builder()
                .itemId(SequenceFactory.seqRealize(SeqRealizeEnum.Zookeeper).snowflakeIdWorker().next())
                .entryCode(entryCode)
                .itemCode(itemVo.getItemCode())
                .itemParentCode(itemVo.getItemParentCode())
                .itemName(itemVo.getItemName())
                .itemSort(itemVo.getItemSort())
                .itemLevel(itemVo.getItemLevel())
                .itemExtraField(JsonUtil.toStr(itemVo.getItemExtraFields()))
                .deleteStatus(Optional.ofNullable(itemVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteStatus)
                        .map(ZeroOrOneEnum::getItemCode)
                        .orElse(ZeroOrOneEnum.ZERO.getItemCode()))
                .deleteOperator(Optional.ofNullable(itemVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteOperator)
                        .orElse(null))
                .deleteTime(Optional.ofNullable(itemVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteTime)
                        .orElse(null))
                .build();
    }


    /**
     * model领域值对象转po数据库实体
     *
     * @param itemVo model领域值对象
     * @return po数据库实体
     */
    public static ChangeItemPo voToChangePoTo(String entryCode, Long entryVersion, ItemVo itemVo) {
        if (StrUtil.isEmpty(entryCode)
                || ObjUtil.isEmpty(itemVo)) {
            return null;
        }
        return ChangeItemPo.builder()
                .itemId(SequenceFactory.seqRealize(SeqRealizeEnum.Zookeeper).snowflakeIdWorker().next())
                .entryCode(entryCode)
                .entryVersion(entryVersion)
                .itemCode(itemVo.getItemCode())
                .itemParentCode(itemVo.getItemParentCode())
                .itemName(itemVo.getItemName())
                .itemSort(itemVo.getItemSort())
                .itemLevel(itemVo.getItemLevel())
                .itemExtraField(JsonUtil.toStr(itemVo.getItemExtraFields()))
                .deleteStatus(Optional.ofNullable(itemVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteStatus)
                        .map(ZeroOrOneEnum::getItemCode)
                        .orElse(ZeroOrOneEnum.ZERO.getItemCode()))
                .deleteOperator(Optional.ofNullable(itemVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteOperator)
                        .orElse(null))
                .deleteTime(Optional.ofNullable(itemVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteTime)
                        .orElse(null))
                .build();
    }


    /**
     * model领域模型集合转po数据库实体集合
     *
     * @param itemModel model领域模型集合
     * @return po数据库集合
     */
    public static List<ItemPo> modelToPos(ItemModel itemModel) {
        if (ObjUtil.isEmpty(itemModel)) {
            return List.of();
        }
        return Optional.ofNullable(itemModel.getItemsByChange())
                .orElse(List.of())
                .parallelStream()
                .map(itemVo
                        -> voToPo(itemModel.getEntryCode(),
                        itemVo))
                .collect(Collectors.toList());
    }


    /**
     * model领域模型集合转po数据库实体集合
     *
     * @param itemModels model领域模型集合
     * @return po数据库实体集合
     */
    public static List<ItemPo> modelToPos(Collection<ItemModel> itemModels) {
        if (CollUtil.isEmpty(itemModels)) {
            return List.of();
        }
        return itemModels
                .parallelStream()
                .map(ItemRepositoryConverter::modelToPos)
                .filter(Objects::nonNull)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
    }


    /**
     * model领域模型转changePo数据库实体
     *
     * @param itemModel model领域模型
     * @return changePo数据库实体集合
     */
    public static List<ChangeItemPo> modelToChangePos(ItemModel itemModel) {
        if (ObjUtil.isEmpty(itemModel)) {
            return null;
        }
        return Optional.ofNullable(itemModel.getItemsByChange())
                .orElse(List.of())
                .parallelStream()
                .map(itemVo
                        -> voToChangePoTo(itemModel.getEntryCode(),
                        itemModel.getEntryVersion(),
                        itemVo))
                .collect(Collectors.toList());
    }


    /**
     * model领域模型集合转changePo数据库实体集合
     *
     * @param itemModels model领域模型集合
     * @return changePo数据库实体集合
     */
    public static List<ChangeItemPo> modelToChangePos(Collection<ItemModel> itemModels) {
        if (CollUtil.isEmpty(itemModels)) {
            return List.of();
        }
        return itemModels
                .parallelStream()
                .map(ItemRepositoryConverter::modelToChangePos)
                .filter(Objects::nonNull)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
    }

    /**
     * po数据库实体集合转model领域模型集合
     *
     * @param itemPos po数据库实体集合
     * @return model领域模型集合
     */
    public static List<ItemModel> poToModels(Collection<ItemPo> itemPos) {
        if (CollUtil.isEmpty(itemPos)) {
            return List.of();
        }
        return itemPos
                .parallelStream()
                .collect(Collectors.groupingBy(ItemPo::getEntryCode,
                        Collectors.mapping(ItemRepositoryConverter::poToVo, Collectors.toSet())))
                .entrySet()
                .parallelStream()
                .map(entry -> new ItemModel(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * changePo数据库实体集合转model领域模型集合
     *
     * @param changeItemPos changePo数据库实体集合
     * @return model领域模型集合
     */
    public static List<ItemModel> changePoToModels(Collection<ChangeItemPo> changeItemPos) {
        if (CollUtil.isEmpty(changeItemPos)) {
            return List.of();
        }
        return changeItemPos
                .parallelStream()
                .collect(Collectors.groupingBy(ChangeItemPo::getEntryCode,
                        Collectors.mapping(ItemRepositoryConverter::changePoToToVo, Collectors.toSet())))
                .entrySet()
                .parallelStream()
                .map(entry -> new ItemModel(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
