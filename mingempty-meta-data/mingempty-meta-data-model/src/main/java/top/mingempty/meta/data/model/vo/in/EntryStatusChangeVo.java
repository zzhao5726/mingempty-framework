package top.mingempty.meta.data.model.vo.in;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.mingempty.domain.enums.ZeroOrOneEnum;

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
     * 是否已逻辑删除
     * 0：否
     * 1：是
     * (同字典条目：zero_or_one)
     */
    @Schema(title = "是否已删除", description = "是否已逻辑删除" +
            "0：否" +
            "1：是" +
            "(同字典条目：zero_or_one)")
    private String deleteStatus = ZeroOrOneEnum.ZERO.getItemCode();
}
