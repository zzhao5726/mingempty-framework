package top.mingempty.meta.data.model.vo.in;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 条目创建视图对象
 *
 * @author zzhao
 */
@Data
@Schema(title = "条目创建视图对象", description = "条目创建视图对象")
public class EntryCreateVo {

    /**
     * 条目编号
     */
    @Schema(title = "条目编号")
    private String entryCode;

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
    private String entryStatus;

    /**
     * 条目类别
     * <pre class="code">
     * (同字典条目编码：dict_entry_category下010000：数据字典)
     * </pre>
     */
    @Schema(title = "条目类别", description = "(同字典条目编码：dict_entry_category下010000：数据字典)")
    private String entryCategory;

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
