package top.mingempty.meta.data.facade.domain.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.mingempty.domain.enums.ZeroOrOneEnum;

import java.math.BigDecimal;

/**
 * 创建或修改条目扩展字段的DTO
 */
@Data
@Schema(title = "创建或修改条目扩展字段的DTO")
public class ExtraFieldModifiesDto {

    /**
     * 扩展字段名称
     */
    @Schema(title = "扩展字段名称")
    private String extraFieldName;

    /**
     * 扩展字段编码
     */
    @Schema(title = "扩展字段编码")
    private String extraFieldCode;

    /**
     * 是否为数字类型
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目编码：zero_or_one)
     * </pre>
     */
    @Schema(title = "是否为数字类型", description = "0：否" +
            "1：是" +
            "(同字典条目编码：zero_or_one)")
    private ZeroOrOneEnum typeIsNumber;

    /**
     * 是否为其余字典项
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目编码：zero_or_one)
     * </pre>
     */
    @Schema(title = "是否为其余字典项", description = "0：否" +
            "1：是" +
            "(同字典条目编码：zero_or_one)")
    private String otherDictFlag;

    /**
     * 其余字典项条目编号
     * <pre class="code">
     * (同字典条目编码：entry_list)
     * </pre>
     */
    @Schema(title = "其余字典项条目编号", description = "(同字典条目编码：entry_list)")
    private String otherEntryCode;

    /**
     * 扩展字段排序（默认0）
     */
    @Schema(title = "扩展字段排序（默认0）")
    private BigDecimal extraFieldSort;
}
