package top.mingempty.meta.data.domain.biz.dict.model;

import cn.hutool.core.util.ObjUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.biz.dict.value.DeleteVo;
import top.mingempty.meta.data.domain.enums.EntryTypeEnum;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 字典条目领域模型
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EntryModel {

    /**
     * 条目编号
     */
    private String entryCode;

    /**
     * 条目版本（默认1）
     */
    private Long entryVersion;

    /**
     * 条目名称
     */
    private String entryName;

    /**
     * 条目类型
     * <pre class="code">
     * 1：普通字典
     * 2：树形字典
     * (同字典条目编码：dict_entry_type)
     * </pre>
     */
    private EntryTypeEnum entryType;

    /**
     * 是否分表
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目编码：zero_or_one)
     * </pre>
     */
    private ZeroOrOneEnum entrySharding;

    /**
     * 条目排序（默认0）
     */
    private BigDecimal sort;

    /**
     * 条目数据逻辑删除状态
     */
    private DeleteVo deleteStatus;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EntryModel that)) return false;
        return Objects.equals(getEntryCode(), that.getEntryCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEntryCode());
    }


    /**
     * 检查数据是否完全一致
     *
     * @param entryModel
     * @return
     */
    public boolean checkSame(EntryModel entryModel) {
        return Objects.equals(this, entryModel)
                && Objects.equals(this.getEntryName(), entryModel.getEntryName())
                && Objects.equals(this.getEntryType(), entryModel.getEntryType())
                && Objects.equals(this.getEntrySharding(), entryModel.getEntrySharding())
                && ObjUtil.compare(this.getSort(), entryModel.getSort()) == 0
                && Objects.equals(this.getDeleteStatus(), entryModel.getDeleteStatus())
                ;
    }

    public EntryModel cloneOne() {
        return EntryModel.builder()
                .entryCode(this.getEntryCode())
                .entryVersion(this.getEntryVersion())
                .entryName(this.getEntryName())
                .entryType(this.getEntryType())
                .entrySharding(this.getEntrySharding())
                .deleteStatus(this.getDeleteStatus())
                .sort(this.getSort())
                .build();
    }
}
