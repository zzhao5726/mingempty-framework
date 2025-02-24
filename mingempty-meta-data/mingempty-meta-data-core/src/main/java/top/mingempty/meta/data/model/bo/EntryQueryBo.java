package top.mingempty.meta.data.model.bo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 字典条目列表查询条件对象
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Data
public class EntryQueryBo {

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
     * 条目名称
     * <br>
     * 支持模糊查询
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
    private String entryType;

    /**
     * 是否分表
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目编码：zero_or_one)
     * </pre>
     */
    private String entrySharding;

    /**
     * 条目启用状态
     * <pre class="code">
     * 0：未启用
     * 1：启用
     * (同字典条目编码：enable_or_not)
     * </pre>
     */
    private String deleteStatus;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    private String deleteOperator;
}
