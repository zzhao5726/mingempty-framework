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
 * 字典项标签表 实体类。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "字典项标签表")
@Table("t_meta_data_label")
public class LabelPo implements BasePoModel {

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
     * 标签编号
     * <pre class="code">
     * (含义同条目：dict_label)
     * </pre>
     */
    @Column("item_label_code")
    @Schema(title = "标签编号", description = "(含义同条目：dict_label)")
    private String itemLabelCode;

    /**
     * 字典项编号
     */
    @Column("item_code")
    @Schema(title = "字典项编号")
    private String itemCode;

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
