package top.mingempty.meta.data.repository.model.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.mingempty.domain.base.BaseDeletePoModel;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 字典项表 实体类。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "字典项表")
@Table("t_meta_data_item")
public class ItemPo implements BaseDeletePoModel {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    @Id
    @Column("item_id")
    @Schema(title = "字典ID")
    private Long itemId;

    /**
     * 条目编号
     */
    @Column("entry_code")
    @Schema(title = "条目编号")
    private String entryCode;

    /**
     * 字典父编号
     */
    @Column("item_parent_code")
    @Schema(title = "字典父编号")
    private String itemParentCode;

    /**
     * 字典项编号
     */
    @Column("item_code")
    @Schema(title = "字典项编号")
    private String itemCode;

    /**
     * 字典项名称
     */
    @Column("item_name")
    @Schema(title = "字典项名称")
    private String itemName;

    /**
     * 字典排序（默认0）
     */
    @Column("item_sort")
    @Schema(title = "字典排序（默认0）")
    private BigDecimal itemSort;

    /**
     * 字典层级（默认1）
     */
    @Column("item_level")
    @Schema(title = "字典层级（默认1）")
    private Long itemLevel;

    /**
     * 扩展字段
     * <pre class="code">
     * (以json格式进行存储)
     * </pre>
     */
    @Column("item_extra_field")
    @Schema(title = "扩展字段", description = "(以json格式进行存储)")
    private String itemExtraField;

    /**
     * 是否已逻辑删除
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目：zero_or_one)
     * </pre>
     */
    @Column(value = "delete_status")
    @Schema(title = "是否已逻辑删除", description = "0：否" +
			"1：是" +
			"(同字典条目：zero_or_one)")
    private String deleteStatus;

    /**
     * 删除时间
     */
    @Column("delete_time")
    @Schema(title = "删除时间")
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @Column("delete_operator")
    @Schema(title = "删除人")
    private String deleteOperator;

    /**
     * 创建时间
     */
    @Column("create_time")
    @Schema(title = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column("update_time")
    @Schema(title = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @Column("create_operator")
    @Schema(title = "创建人")
    private String createOperator;

    /**
     * 更新人
     */
    @Column("update_operator")
    @Schema(title = "更新人")
    private String updateOperator;

}
