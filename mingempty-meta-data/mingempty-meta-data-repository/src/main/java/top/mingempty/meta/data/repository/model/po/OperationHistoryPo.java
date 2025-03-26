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
import top.mingempty.meta.data.domain.enums.DictOperationEnum;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 字典操作历史表 实体类。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "字典操作历史表")
@Table("t_meta_data_operation_history")
public class OperationHistoryPo implements BasePoModel {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 操作历史ID
     */
    @Id
    @Column("operation_history_id")
    @Schema(title = "操作历史ID")
    private Long operationHistoryId;

    /**
     * 条目编号
     */
    @Column("entry_code")
    @Schema(title = "条目编号")
    private String entryCode;

    /**
     * 操作类型
     * <pre class="code">
     * 01.条目-新增
     * 02.条目-修改
     * 03.条目-逻辑删除
     * 04.条目权限-新增
     * 05.条目权限-逻辑删除
     * 06.扩展字段-新增
     * 07.扩展字段-修改
     * 08.字典项-新增
     * 09.字典项-修改
     * 10.字典项-逻辑删除
     * 11.导入-excel
     * 12.导入-zip
     * (同字典条目编码：dict_operation_type)
     * </pre>
     */
    @Column("operation_type")
    @Schema(title = "操作类型", description = "01.条目-新增" +
			"02.条目-修改" +
			"03.条目-逻辑删除" +
			"04.条目权限-新增" +
			"05.条目权限-逻辑删除" +
			"06.扩展字段-新增" +
			"07.扩展字段-修改" +
			"08.字典项-新增" +
			"09.字典项-修改" +
			"10.字典项-逻辑删除" +
			"11.导入-excel" +
			"12.导入-zip" +
			"(同字典条目编码：dict_operation_type)")
    private DictOperationEnum operationType;

    /**
     * 条目版本（默认1）
     */
    @Column("entry_version")
    @Schema(title = "条目版本（默认1）")
    private Long entryVersion;

    /**
     * 操作人
     */
    @Column("operator_code")
    @Schema(title = "操作人")
    private String operatorCode;

    /**
     * 操作时间
     */
    @Column("operation_time")
    @Schema(title = "操作时间")
    private LocalDateTime operationTime;

    /**
     * 批次ID
     * <pre class="code">
     * (使用Excel或Zip导入时有值)
     * </pre>
     */
    @Column("batch_id")
    @Schema(title = "批次ID", description = "(使用Excel或Zip导入时有值)")
    private Long batchId;

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
