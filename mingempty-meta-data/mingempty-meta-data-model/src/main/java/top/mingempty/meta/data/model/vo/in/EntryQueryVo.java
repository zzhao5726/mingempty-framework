package top.mingempty.meta.data.model.vo.in;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.model.vo.AuthorizationVo;

import java.util.Collection;

/**
 * 字典条目列表查询条件视图对象
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Data
@Schema(title = "字典条目列表查询视图对象", description = "字典条目列表查询视图对象")
public class EntryQueryVo {

    /**
     * 条目编号
     */
    @Schema(title = "条目编号")
    private String entryCode;

    /**
     * 模糊条目编号
     */
    @Schema(title = "模糊条目编号")
    private String entryCodeLike;

    /**
     * 条目编号集合
     */
    @Schema(title = "条目编号集合")
    private Collection<String> entryCodes;

    /**
     * 条目版本（默认1）
     */
    @Schema(title = "条目版本（默认1）")
    private Long entryVersion;

    /**
     * 条目名称
     */
    @Schema(title = "条目名称", description = "支持模糊查询")
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
}
