package top.mingempty.meta.data.facade.domain.dict;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.other.DatePattern;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

/**
 * 展示字典项列表的DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(title = "展示字典项列表的DTO")
public class ItemListDto {

    /**
     * 条目编号
     */
    @Schema(title = "条目编号")
    private String entryCode;

    /**
     * 字典父编号
     */
    @Schema(title = "字典父编号")
    private String itemParentCode;

    /**
     * 字典项编号
     */
    @Schema(title = "字典项编号")
    private String itemCode;

    /**
     * 字典项名称
     */
    @Schema(title = "字典项名称")
    private String itemName;

    /**
     * 字典排序（默认0）
     */
    @Schema(title = "字典排序（默认0）")
    private BigDecimal itemSort;

    /**
     * 字典层级（默认1）
     */
    @Schema(title = "字典层级（默认1）")
    private Long itemLevel;

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
     * 字典项扩展数据
     */
    @Schema(title = "字典项扩展数据")
    private Map<String, Object> extraFields;

    /**
     * 子字典项列表
     */
    @Schema(title = "子字典项列表")
    private Collection<ItemListDto> children;

}
