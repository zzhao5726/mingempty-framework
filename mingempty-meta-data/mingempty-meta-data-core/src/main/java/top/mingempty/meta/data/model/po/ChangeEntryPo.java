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
 * 字典条目变化流水表 实体类。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "字典条目变化流水表")
@Table("t_meta_data_change_entry")
public class ChangeEntryPo implements BasePoModel {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 条目ID
     */
    @Id
    @Column("entry_id")
    @Schema(title = "条目ID")
    private Long entryId;

    /**
     * 条目编号
     */
    @Column("entry_code")
    @Schema(title = "条目编号")
    private String entryCode;

    /**
     * 条目版本（默认1）
     */
    @Column("entry_version")
    @Schema(title = "条目版本（默认1）")
    private Long entryVersion;

    /**
     * 条目名称
     */
    @Column("entry_name")
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
    @Column("entry_type")
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
    @Column("entry_status")
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
    @Column("entry_category")
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
    @Column("entry_sharding")
    @Schema(title = "是否分表", description = "0：否" +
			"1：是" +
			"(同字典条目编码：zero_or_one)")
    private String entrySharding;

    /**
     * 条目排序（默认0）
     */
    @Column("sort")
    @Schema(title = "条目排序（默认0）")
    private BigDecimal sort;

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
