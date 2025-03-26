package top.mingempty.meta.data.domain.biz.dict.value;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字典项值对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ItemVo {

    /**
     * 字典项编号
     */
    private String itemCode;

    /**
     * 字典父编号
     */
    private String itemParentCode;

    /**
     * 字典项名称
     */
    private String itemName;

    /**
     * 字典排序（默认0）
     */
    private BigDecimal itemSort;

    /**
     * 字典层级（默认1）
     */
    private Long itemLevel;

    /**
     * 扩展字段
     * <pre class="code">
     * (以json格式进行存储)
     * </pre>
     */
    private final Map<String, Object> itemExtraFields = new ConcurrentHashMap<>(0);

    /**
     * 条目字典项数据逻辑删除状态
     */
    private DeleteVo deleteStatus;

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ItemVo itemVo)) return false;
        return Objects.equals(getItemCode(), itemVo.getItemCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getItemCode());
    }

    /**
     * 获取条目权限数据值对象的删除状态
     *
     * @return 删除状态
     */
    public ZeroOrOneEnum deleteStatus() {
        return Optional.ofNullable(deleteStatus)
                .map(DeleteVo::getDeleteStatus)
                .orElse(ZeroOrOneEnum.ZERO);
    }

    public ItemVo cloneOne() {
        ItemVo itemVo = ItemVo.builder()
                .itemCode(itemCode)
                .itemParentCode(itemParentCode)
                .itemName(itemName)
                .itemSort(itemSort)
                .itemLevel(itemLevel)
                .deleteStatus(Optional.ofNullable(deleteStatus)
                        .map(DeleteVo::cloneOne)
                        .orElse(DeleteVo.builder().deleteStatus(ZeroOrOneEnum.ZERO)
                                .build()))
                .build();
        itemVo.itemExtraFields.putAll(this.getItemExtraFields());
        return itemVo;
    }

    /**
     * 构件一个基于当前权限的删除副本
     *
     * @return 条目权限数据值对象
     */
    public ItemVo delete() {
        return cloneOne().setDeleteStatus(DeleteVo.builder().deleteStatus(ZeroOrOneEnum.ONE).build());
    }

    /**
     * 修改字典项扩展字段
     *
     * @param extraFieldCode  字典项扩展字段编码
     * @param extraFieldValue 字典项扩展字段值
     */
    public ItemVo modifiesExtraField(String extraFieldCode, Object extraFieldValue) {
        if (StrUtil.isEmpty(extraFieldCode)
                || ObjectUtil.isEmpty(extraFieldValue)) {
            return this;
        }
        ItemVo itemVo = cloneOne();
        itemVo.itemExtraFields.put(extraFieldCode, extraFieldValue);
        return itemVo;
    }

    /**
     * 修改字典项扩展字段
     *
     * @param itemExtraFields 字典项扩展字段信息
     */
    public ItemVo modifiesExtraField(Map<String, Object> itemExtraFields) {
        if (CollUtil.isEmpty(itemExtraFields)) {
            return this;
        }
        ItemVo itemVo = cloneOne();
        itemVo.itemExtraFields.putAll(itemExtraFields);
        return itemVo;
    }

    public Map<String, Object> getItemExtraFields() {
        return new ConcurrentHashMap<>(itemExtraFields);
    }
}
