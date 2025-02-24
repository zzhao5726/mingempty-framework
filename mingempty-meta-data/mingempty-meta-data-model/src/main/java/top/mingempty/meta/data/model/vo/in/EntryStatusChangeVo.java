package top.mingempty.meta.data.model.vo.in;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 条目状态变更视图对象
 *
 * @author zzhao
 */
@Data
@Schema(title = "条目状态变更视图对象", description = "条目状态变更视图对象")
public class EntryStatusChangeVo {
    /**
     * 条目编号集合
     */
    @Schema(title = "条目编号集合")
    private List<String> entryCodes;

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
}
