package top.mingempty.meta.data.domain.biz.dict.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 条目扩展字段查询参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ExtraFieldQuery implements BaseQuery<ExtraFieldQuery> {

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
     * 扩展字段名称
     */
    private String extraFieldName;

    /**
     * 模糊扩展字段名称
     */
    private String extraFieldNameLike;

    /**
     * 扩展字段编码
     */
    private String extraFieldCode;

    /**
     * 扩展字段编码集合
     */
    private Collection<String> extraFieldCodes;

    /**
     * 是否为数字类型
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目编码：zero_or_one)
     * </pre>
     */
    private ZeroOrOneEnum typeIsNumber;

    /**
     * 是否为其余字典项
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目编码：zero_or_one)
     * </pre>
     */
    private ZeroOrOneEnum otherDictFlag;

    /**
     * 其余字典项条目编号
     * <pre class="code">
     * (同字典条目编码：entry_list)
     * </pre>
     */
    private String otherEntryCode;

    @Override
    public ExtraFieldQuery setDeleteStatus(ZeroOrOneEnum deleteStatus) {
        throw new UnsupportedOperationException("条目扩展字段不支持删除");
    }

    @Override
    public ExtraFieldQuery setDeleteTime(LocalDateTime deleteTime) {
        throw new UnsupportedOperationException("条目扩展字段不支持删除");
    }

    @Override
    public ExtraFieldQuery setDeleteOperator(String deleteOperator) {
        throw new UnsupportedOperationException("条目扩展字段不支持删除");
    }
}
