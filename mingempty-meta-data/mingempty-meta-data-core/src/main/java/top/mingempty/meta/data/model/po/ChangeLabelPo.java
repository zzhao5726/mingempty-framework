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
import java.time.LocalDateTime;

/**
 * 字典项标签变化流水表 实体类。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "字典项标签变化流水表")
@Table("t_meta_data_change_label")
public class ChangeLabelPo implements BasePoModel {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典ID
     */
    @Id
    @Column("label_id")
    @Schema(title = "字典ID")
    private Long labelId;

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
     * 条目版本（默认1）
     */
    @Column("entry_version")
    @Schema(title = "条目版本（默认1）")
    private Long entryVersion;

    /**
     * 标签编号
     * <pre class="code">
     * (含义同条目：dict_label)
     * </pre>
     */
    @Column("label_code")
    @Schema(title = "标签编号", description = "(含义同条目：dict_label)")
    private String labelCode;

    /**
     * 字典项编号
     */
    @Column("item_code")
    @Schema(title = "字典项编号")
    private String itemCode;

    /**
     * 标签启用状态
     * <pre class="code">
     * 0：未启用
     * 1：启用
     * (同字典条目编码：enable_or_not)
     * </pre>
     */
    @Column("label_status")
    @Schema(title = "标签启用状态", description = "0：未启用" +
			"1：启用" +
			"(同字典条目编码：enable_or_not)")
    private String labelStatus;

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
