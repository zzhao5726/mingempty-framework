package top.mingempty.meta.data.model.po;

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
import java.time.LocalDateTime;

/**
 * 字典项标签变化流水表 实体类。
 *
 * @author zzhao
 * @since 2025-03-20 22:58:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "字典项标签变化流水表")
@Table("t_meta_data_change_label")
public class ChangeLabelPo implements BaseDeletePoModel {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典项标签ID
     */
    @Id
    @Column("label_id")
    @Schema(title = "字典项标签ID")
    private Long labelId;

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
     * 是否已逻辑删除
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目：zero_or_one)
     * </pre>
     */
    @Column(value = "delete_status", isLogicDelete = true)
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
