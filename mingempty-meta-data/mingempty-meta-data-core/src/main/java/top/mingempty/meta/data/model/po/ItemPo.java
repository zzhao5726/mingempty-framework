package top.mingempty.meta.data.model.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.mingempty.domain.base.BasePoModel;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 字典项表 实体类。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "字典项表")
@Table("t_meta_data_item")
public class ItemPo implements BasePoModel {

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
     * <pre class="code">
     * (同字典条目表条目编号)
     * </pre>
     */
    @Column("entry_code")
    @Schema(title = "条目编号", description = "(同字典条目表条目编号)")
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
     * 字典启用状态
     * <pre class="code">
     * 0：未启用
     * 1：启用
     * (同字典条目编码：enable_or_not)
     * </pre>
     */
    @Column("item_status")
    @Schema(title = "字典启用状态", description = "0：未启用" +
			"1：启用" +
			"(同字典条目编码：enable_or_not)")
    private String itemStatus;

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
