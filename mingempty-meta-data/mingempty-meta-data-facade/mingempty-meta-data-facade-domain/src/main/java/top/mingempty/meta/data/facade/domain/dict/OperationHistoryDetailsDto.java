package top.mingempty.meta.data.facade.domain.dict;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.other.DatePattern;

import java.time.LocalDateTime;


/**
 * 展示条目操作历史详情的DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(title = "展示条目操作历史详情的DTO")
public class OperationHistoryDetailsDto {

    /**
     * 操作类型
     * <pre class="code">
     * 1.条目基础信息新增或修改
     * 2.字典项新增或修改
     * 3.excel导入
     * 4.内部自定义格式导入
     * 5.版本还原
     * (同字典条目编码：dict_operation_type)
     * </pre>
     */
    @Schema(title = "操作类型", description = "1.条目基础信息新增或修改" +
            "2.字典项新增或修改" +
            "3.excel导入" +
            "4.内部自定义格式导入" +
            "5.版本还原" +
            "(同字典条目编码：dict_operation_type)")
    private String operationType;

    /**
     * 条目版本（默认1）
     */
    private Long entryVersion;

    /**
     * 还原条目版本
     * <pre class="code">
     * (仅当操作类型为5时才有值)
     * </pre>
     */
    @Schema(title = "还原条目版本", description = "(仅当操作类型为5时才有值)")
    private Long restoreEntryVersion;

    /**
     * 操作人
     */
    @Schema(title = "操作人")
    private String operatorCode;

    /**
     * 操作时间
     */
    @Schema(title = "操作时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime operationTime;

    /**
     * 批次ID
     * <pre class="code">
     * (仅当操作类型为3和4时才有值)
     * </pre>
     */
    @Schema(title = "批次ID", description = "(仅当操作类型为3和4时才有值)")
    private Long batchId;

}
