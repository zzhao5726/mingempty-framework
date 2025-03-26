package top.mingempty.meta.data.facade.domain.dict;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.mingempty.domain.other.DatePattern;
import top.mingempty.domain.other.MePubConditions;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 字典项列表查询的DTO
 */
@Data
@Schema(title = "字典项列表查询的DTO")
public class ItemListQueryDto {

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
     * 字典父编号
     */
    @Schema(title = "字典父编号")
    private String itemParentCode;

    /**
     * 字典标签编号
     */
    @Schema(title = "字典标签编号")
    private String labelCode;

    /**
     * 字典项编号
     */
    @Schema(title = "字典项编号")
    private String itemCode;

    /**
     * 模糊典项编号
     */
    @Schema(title = "模糊典项编号")
    private String itemCodeLike;

    /**
     * 字典项编号集合
     */
    @Schema(title = "字典项编号集合")
    private Collection<String> itemCodes;

    /**
     * 字典项名称
     */
    @Schema(title = "字典项名称")
    private String itemName;

    /**
     * 模糊字典项名称
     */
    @Schema(title = "模糊字典项名称")
    private String itemNameLike;

    /**
     * 是否已逻辑删除
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目：zero_or_one)
     * </pre>
     */
    @Schema(title = "是否已逻辑删除", description = "0：否" +
            "1：是" +
            "(同字典条目：zero_or_one)")
    private String deleteStatus;

    /**
     * 删除时间
     */
    @Schema(title = "删除时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime deleteTime;

    /**
     * 删除用户
     */
    @Schema(title = "删除用户")
    private String deleteOperator;

    /**
     * 是否查询详情
     */
    @Schema(title = "是否查询详情")
    private Boolean detailsFlag = Boolean.FALSE;

    /**
     * 是否组装树形结构
     * <p>
     * 仅当查询当前条目的全量数据时可用
     */
    @Schema(title = "是否组装树形结构", description = "仅当查询当前条目的全量数据时可用")
    private Boolean treeFlag = Boolean.FALSE;

    /**
     * 字典项扩展字段条件
     */
    @Schema(title = "字典项扩展字段条件")
    private Collection<MePubConditions.ValueCondition> itemExtraFieldConditions;

}
