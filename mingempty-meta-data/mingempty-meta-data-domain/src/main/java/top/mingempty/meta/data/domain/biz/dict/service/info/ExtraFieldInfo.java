package top.mingempty.meta.data.domain.biz.dict.service.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;

import java.math.BigDecimal;

/**
 * 字典扩展字段信息内部传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ExtraFieldInfo {

    /**
     * 条目编号
     */
    private String entryCode;

    /**
     * 条目版本（默认1）
     */
    private Long entryVersion;

    /**
     * 扩展字段名称
     */
    private String extraFieldName;

    /**
     * 扩展字段编码
     */
    private String extraFieldCode;

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

    /**
     * 扩展字段排序（默认0）
     */
    private BigDecimal extraFieldSort;

}
