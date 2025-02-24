package top.mingempty.meta.data.model.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
/**
 * 字典条目变化流水表 实体类。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "字典条目变化流水表")
public class EntryBo {

    /**
     * 条目ID
     */
    @Schema(title = "条目ID")
    private Long entryId;

    /**
     * 条目编号
     */
    @Schema(title = "条目编号")
    private String entryCode;

    /**
     * 条目版本（默认1）
     */
    @Schema(title = "条目版本（默认1）")
    private Long entryVersion;

    /**
     * 条目名称
     */
    @Schema(title = "条目名称")
    private String entryName;

    /**
     * 条目类型
     * <pre class="code">
     * 1：普通字典
     * 2：树形字典
     * (同字典条目编码：dict_entry_type)
     * </pre>
     */
    @Schema(title = "条目类型", description = "1：普通字典" +
            "2：树形字典" +
            "(同字典条目编码：dict_entry_type)")
    private String entryType;

    /**
     * 条目启用状态
     * <pre class="code">
     * 0：未启用
     * 1：启用
     * (同字典条目编码：enable_or_not)
     * </pre>
     */
    @Schema(title = "条目启用状态", description = "0：未启用" +
            "1：启用" +
            "(同字典条目编码：enable_or_not)")
    private String deleteStatus;

    /**
     * 是否分表
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目编码：zero_or_one)
     * </pre>
     */
    @Schema(title = "是否分表", description = "0：否" +
            "1：是" +
            "(同字典条目编码：zero_or_one)")
    private String entrySharding;

    /**
     * 条目排序（默认0）
     */
    @Schema(title = "条目排序（默认0）")
    private BigDecimal sort;
}
