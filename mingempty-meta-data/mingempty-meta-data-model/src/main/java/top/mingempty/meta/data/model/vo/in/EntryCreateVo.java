package top.mingempty.meta.data.model.vo.in;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.model.enums.EntryTypeEnum;
import top.mingempty.meta.data.model.vo.AuthorizationVo;
import top.mingempty.meta.data.model.vo.ExtraFieldVo;

import java.math.BigDecimal;
import java.util.List;

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
    private String entryType = EntryTypeEnum.ONE.getItemCode();

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
    private String entrySharding = ZeroOrOneEnum.ZERO.getItemCode();

    /**
     * 条目排序（默认0）
     */
    @Schema(title = "条目排序（默认0）")
    private BigDecimal sort = BigDecimal.ZERO;

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

    /**
     * 条目授权信息视图对象
     */
    @Schema(title = "条目授权信息视图对象")
    private AuthorizationVo authorizationVo;

    /**
     * 字典扩展字段视图对象
     */
    @Schema(title = "字典扩展字段视图对象")
    private List<ExtraFieldVo> extraFieldVos;

}
