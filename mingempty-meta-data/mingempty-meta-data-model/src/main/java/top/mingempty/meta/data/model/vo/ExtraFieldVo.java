package top.mingempty.meta.data.model.vo;

import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 字典扩展字段视图对象
 *
 * @author zzhao
 * @since 2025-03-20 22:58:54
 */
@Data
@Schema(title = "字典扩展字段视图对象", description = "字典扩展字段视图对象")
public class ExtraFieldVo {

    /**
     * 条目编号
     */
    @Schema(title = "条目编号")
    private String entryCode;

    /**
     * 扩展字段编码
     */
    @Column("extra_field_code")
    @Schema(title = "扩展字段编码")
    private String extraFieldCode;

    /**
     * 扩展字段名称
     */
    @Column("extra_field_name")
    @Schema(title = "扩展字段名称")
    private String extraFieldName;

    /**
     * 是否为其余字典项
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目编码：zero_or_one)
     * </pre>
     */
    @Column("other_dict_flag")
    @Schema(title = "是否为其余字典项", description = "0：否" +
            "1：是" +
            "(同字典条目编码：zero_or_one)")
    private String otherDictFlag;

    /**
     * 其余字典项条目编号
     * <pre class="code">
     * (同字典条目编码：entry_code)
     * </pre>
     */
    @Column("other_entry_code")
    @Schema(title = "其余字典项条目编号", description = "(同字典条目编码：entry_code)")
    private String otherEntryCode;

    /**
     * 扩展字段排序（默认0）
     */
    @Column("extra_field_sort")
    @Schema(title = "扩展字段排序（默认0）")
    private BigDecimal extraFieldSort;

}
