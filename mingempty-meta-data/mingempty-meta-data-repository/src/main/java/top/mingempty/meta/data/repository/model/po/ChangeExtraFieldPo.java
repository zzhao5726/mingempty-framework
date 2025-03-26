package top.mingempty.meta.data.repository.model.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.mingempty.domain.base.BasePoModel;
import top.mingempty.domain.enums.ZeroOrOneEnum;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 字典扩展字段信息变化流水表 实体类。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "字典扩展字段信息变化流水表")
@Table("t_meta_data_change_extra_field")
public class ChangeExtraFieldPo implements BasePoModel {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 条目扩展字段关系ID
     */
    @Id
    @Column("extra_field_id")
    @Schema(title = "条目扩展字段关系ID")
    private Long extraFieldId;

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
     * 扩展字段名称
     */
    @Column("extra_field_name")
    @Schema(title = "扩展字段名称")
    private String extraFieldName;

    /**
     * 扩展字段编码
     */
    @Column("extra_field_code")
    @Schema(title = "扩展字段编码")
    private String extraFieldCode;

    /**
     * 是否为其余字典项
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目编码：zero_or_one)
     * </pre>
     */
    @Column("other_dict_flag")
    @Schema(title = "是否为其余字典项", description = "0：否" +
			"1：是" +
			"(同字典条目编码：zero_or_one)")
    private ZeroOrOneEnum otherDictFlag;

    /**
     * 其余字典项条目编号
     * <pre class="code">
     * (同字典条目编码：entry_code)
     * </pre>
     */
    @Column("other_entry_code")
    @Schema(title = "其余字典项条目编号", description = "(同字典条目编码：entry_code)")
    private String otherEntryCode;

    /**
     * 扩展字段排序（默认0）
     */
    @Column("extra_field_sort")
    @Schema(title = "扩展字段排序（默认0）")
    private BigDecimal extraFieldSort;

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
