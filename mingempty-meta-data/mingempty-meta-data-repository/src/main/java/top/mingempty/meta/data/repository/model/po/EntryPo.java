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
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.enums.EntryTypeEnum;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 字典条目表 实体类。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "字典条目表")
@Table("t_meta_data_entry")
public class EntryPo implements BaseDeletePoModel {

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
    private EntryTypeEnum entryType;

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
    private ZeroOrOneEnum entrySharding;

    /**
     * 条目排序（默认0）
     */
    @Column("sort")
    @Schema(title = "条目排序（默认0）")
    private BigDecimal sort;

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
