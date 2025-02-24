package top.mingempty.meta.data.model.vo.in;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 条目更新视图对象
 *
 * @author zzhao
 */
@Data
@Schema(title = "条目更新视图对象", description = "条目更新视图对象")
public class EntryUpdateVo {

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
     * 条目类别
     * <pre class="code">
     * (同字典条目编码：dict_entry_category下010000：数据字典)
     * </pre>
     */
    @Schema(title = "条目类别", description = "(同字典条目编码：dict_entry_category下010000：数据字典)")
    private String entryCategory;

    /**
     * 条目排序（默认0）
     */
    @Schema(title = "条目排序（默认0）")
    private BigDecimal sort;
}
