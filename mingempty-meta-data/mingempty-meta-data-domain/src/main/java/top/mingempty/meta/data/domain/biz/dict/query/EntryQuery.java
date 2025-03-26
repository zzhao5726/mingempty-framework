package top.mingempty.meta.data.domain.biz.dict.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.enums.EntryTypeEnum;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 条目信息查询
 *
 * @author zzhao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EntryQuery implements BaseQuery<EntryQuery> {

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
     * 条目名称
     */
    private String entryName;

    /**
     * 模糊条目名称
     */
    private String entryNameLike;

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

}
