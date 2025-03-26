package top.mingempty.meta.data.domain.biz.dict.query;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.domain.other.MePubConditions;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * 字典项查询参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ItemQuery implements BaseQuery<ItemQuery> {

    /**
     * 非详情查询字段
     */
    public final static Collection<String> NO_DETAILS_SELECT_COLUMNS = List.of("entry_code", "item_parent_code", "item_code", "item_name", "delete_status");

    /**
     * 查询字段
     */
    private Collection<String> selectColumns;

    /**
     * 条目编号
     */
    private String entryCode;

    /**
     * 模糊条目编号
     */
    private String entryCodeLike;

    /**
     * 条目编号集合
     */
    private Collection<String> entryCodes;

    /**
     * 条目版本（默认1）
     */
    private Long entryVersion;

    /**
     * 字典父编号
     */
    private String itemParentCode;

    /**
     * 字典标签编号
     */
    private String labelCode;

    /**
     * 字典项编号
     */
    private String itemCode;

    /**
     * 模糊典项编号
     */
    private String itemCodeLike;

    /**
     * 字典项编号集合
     */
    private Collection<String> itemCodes;

    /**
     * 字典项名称
     */
    private String itemName;

    /**
     * 模糊字典项名称
     */
    private String itemNameLike;

    /**
     * 是否已逻辑删除
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目：zero_or_one)
     * </pre>
     */
    private ZeroOrOneEnum deleteStatus;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 删除用户
     */
    private String deleteOperator;

    /**
     * 字典项扩展字段条件
     */
    private Collection<MePubConditions.ValueCondition> itemExtraFieldConditions;


    /**
     * 字典项扩展字段条件
     *
     * @return 字典项扩展字段条件
     */
    public String getExtraFieldConditions() {
        if (CollUtil.isEmpty(itemExtraFieldConditions)) {
            return null;
        }
        return MePubConditions.gainConditionsSql(itemExtraFieldConditions, "base", true, "item_extra_field");

    }
}
