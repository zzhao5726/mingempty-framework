package top.mingempty.meta.data.domain.biz.dict.model;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import top.mingempty.commons.util.CollectionUtil;
import top.mingempty.commons.util.MapUtil;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.commons.exception.MetaDataException;
import top.mingempty.meta.data.domain.biz.dict.value.DeleteVo;
import top.mingempty.meta.data.domain.biz.dict.value.ItemVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 字典项领域模型
 */
public class ItemModel {

    /**
     * 条目编号
     */
    @Getter
    private final String entryCode;

    /**
     * 条目版本（默认1）
     */
    @Setter
    @Getter
    private Long entryVersion;


    /**
     * 条目字典项信息值对象
     */
    private final List<ItemVo> itemVos = new CopyOnWriteArrayList<>();

    /**
     * 条目字典项信息值对象备份数据
     */
    private final List<ItemVo> itemVosByBack = new CopyOnWriteArrayList<>();


    /**
     * 条目字典项信息值对象
     */
    private final Map<String, ItemVo> items = new ConcurrentHashMap<>();

    /**
     * 条目字典项信息值对象备份数据
     */
    private final Map<String, ItemVo> itemsByBack = new ConcurrentHashMap<>();

    /**
     * 条目字典项信息值对象修改数据
     */
    private final Collection<ItemVo> itemsByChange = new CopyOnWriteArraySet<>();

    public ItemModel(String entryCode, Collection<ItemVo> itemVos) {
        this.entryCode = entryCode;
        if (CollUtil.isEmpty(itemVos)) {
            return;
        }
        itemVos.parallelStream()
                .forEach(item -> {
                    this.itemVos.add(item);
                    this.itemVosByBack.add(item);
                    this.items.put(item.getItemCode(), item);
                    this.itemsByBack.put(item.getItemCode(), item);
                });
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ItemModel itemModel)) return false;
        return Objects.equals(getEntryCode(), itemModel.getEntryCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEntryCode());
    }


    public List<ItemVo> getItems() {
        return List.copyOf(this.itemVos);
    }

    public List<ItemVo> getItemsByBack() {
        return List.copyOf(this.itemVosByBack);
    }

    public List<ItemVo> getItemsByChange() {
        return List.copyOf(itemsByChange);
    }

    /**
     * 获取字典项
     *
     * @param itemCode 字典项编号
     * @return 字典项
     */
    public ItemVo gainOne(String itemCode) {
        return items.get(itemCode);
    }

    /**
     * 获取字典项备份
     *
     * @param itemCode 字典项编号
     * @return 字典项
     */
    public ItemVo gainBackOne(String itemCode) {
        return itemsByBack.get(itemCode);
    }

    /**
     * 添加字典项
     *
     * @param itemVo 字典项
     */
    public void add(ItemVo itemVo) {
        modifies(itemVo, ZeroOrOneEnum.ZERO);
    }


    /**
     * 删除字典项
     *
     * @param itemVo 字典项
     */
    public void delete(ItemVo itemVo) {
        modifies(itemVo, ZeroOrOneEnum.ONE);
    }

    /**
     * 全量修改字典项
     *
     * @param itemVos 字典项
     */
    public void modifies(Collection<ItemVo> itemVos) {
        if (itemVos == null) {
            itemVos = new CopyOnWriteArraySet<>();
        }
        // 处理删除逻辑
        Collection<ItemVo> toDelete = CollectionUtil.subtract(this.itemsByBack.values(), itemVos);
        toDelete.parallelStream()
                .filter(itemVo -> !isDelete(itemVo.getItemCode()))
                .map(ItemVo::delete)
                .forEach(itemVos::add);
        itemVos.parallelStream()
                .forEach(itemVo
                        -> modifies(itemVo, itemVo.deleteStatus())
                );
    }

    /**
     * 修改字典项字典项
     *
     * @param itemCode        字典项编码
     * @param extraFieldCode  字典项字典项编码
     * @param extraFieldValue 字典项字典项值
     */
    public void modifiesExtraField(String itemCode, String extraFieldCode, String extraFieldValue) {
        if (StrUtil.isEmpty(itemCode)) {
            return;
        }
        ItemVo itemVo = Optional.ofNullable(gainOne(itemCode))
                .map(item -> item.modifiesExtraField(extraFieldCode, extraFieldValue))
                .orElse(null);
        if (ObjUtil.isEmpty(itemVo)) {
            return;
        }
        this.modifies(itemVo, itemVo.deleteStatus());
    }

    /**
     * 修改字典项
     *
     * @param itemVo 字典项
     */
    public void modifies(ItemVo itemVo, ZeroOrOneEnum deleteStatus) {
        checkModifies(itemVo);
        itemVo.setDeleteStatus(DeleteVo.builder()
                .deleteStatus(Optional.ofNullable(deleteStatus).orElse(ZeroOrOneEnum.ZERO))
                .build());
        ItemVo itemVoByExists = gainOne(itemVo.getItemCode());
        if (ObjUtil.isNotEmpty(itemVoByExists)) {
            if (checkSame(itemVoByExists, itemVo)) {
                return;
            }
            Map<String, Object> itemExtraFields = itemVo.getItemExtraFields();
            for (Map.Entry<String, Object> entry : itemExtraFields.entrySet()) {
                if (itemExtraFields.containsKey(entry.getKey())) {
                    continue;
                }
                itemVo = itemVoByExists.modifiesExtraField(entry.getKey(), entry.getValue());
            }
        }
        this.itemVos.add(itemVo);
        this.items.put(itemVo.getItemCode(), itemVo);
        this.itemsByChange.add(itemVo);
    }


    /**
     * 检查字典项修改
     *
     * @param itemVo 字典项
     */
    private void checkModifies(ItemVo itemVo) {
        if (ObjUtil.isEmpty(itemVo)
                || StrUtil.isEmpty(itemVo.getItemCode())
                || StrUtil.isEmpty(itemVo.getItemParentCode())
                || ObjUtil.isEmpty(itemVo.getItemLevel())
                || StrUtil.isEmpty(itemVo.getItemName())) {
            throw new MetaDataException("meta-data-domain-0000000007");
        }
    }

    /**
     * 判断条目字典项是否存在
     *
     * @param itemCode 字典项编码
     * @return
     */
    public boolean exists(String itemCode) {
        if (ObjUtil.isEmpty(itemCode)) {
            return false;
        }
        return items.containsKey(itemCode);
    }

    /**
     * 判断条目字典项是否存在
     *
     * @param itemCode 字典项编码
     * @return
     */
    public boolean existsBack(String itemCode) {
        if (ObjUtil.isEmpty(itemCode)) {
            return false;
        }
        return itemsByBack.containsKey(itemCode);
    }

    /**
     * 条目字典项是否被删除
     * <p>
     * 当未找到当前条目字典项数据时，默认为被删除
     *
     * @param itemCode 字典项编码
     * @return
     */
    public boolean isDelete(String itemCode) {
        if (ObjUtil.isEmpty(itemCode)) {
            return true;
        }
        return Optional.ofNullable(items.get(itemCode))
                .map(ItemVo::deleteStatus)
                .orElse(ZeroOrOneEnum.ONE)
                .equals(ZeroOrOneEnum.ONE);
    }

    /**
     * 检查数据是否完全一致
     *
     * @param itemVo
     * @return
     */
    public boolean checkSame(ItemVo itemVo) {
        if (ObjUtil.isEmpty(itemVo)) {
            return false;
        }
        ItemVo itemVoByExists = gainOne(itemVo.getItemCode());
        if (ObjUtil.isEmpty(itemVoByExists)) {
            return false;
        }
        return checkSame(itemVo, itemVoByExists);
    }

    /**
     * 检查数据是否完全一致
     *
     * @param itemVo
     * @param itemVo2
     * @return
     */
    public boolean checkSame(ItemVo itemVo, ItemVo itemVo2) {
        return Objects.equals(itemVo, itemVo2)
                && Objects.equals(itemVo.getItemParentCode(), itemVo2.getItemParentCode())
                && Objects.equals(itemVo.getItemName(), itemVo2.getItemName())
                && ObjUtil.compare(itemVo.getItemSort(), itemVo2.getItemSort()) == 0
                && Objects.equals(itemVo.getItemLevel(), itemVo2.getItemLevel())
                && MapUtil.equals(itemVo.getItemExtraFields(), itemVo2.getItemExtraFields())
                && Objects.equals(itemVo.getDeleteStatus(), itemVo2.getDeleteStatus())
                ;
    }

    /**
     * 克隆一个对象
     *
     * @return
     */
    public ItemModel cloneOne() {
        ItemModel itemModel = new ItemModel(this.entryCode, this.getItems());
        itemModel.setEntryVersion(this.getEntryVersion());
        return itemModel;
    }


    /**
     * 克隆一个对象
     *
     * @return
     */
    public static ItemModel cloneOne(String entryCode, ItemModel itemModel) {
        if (ObjUtil.isEmpty(itemModel)) {
            return new ItemModel(entryCode, List.of());
        }
        return itemModel.cloneOne();
    }
}
