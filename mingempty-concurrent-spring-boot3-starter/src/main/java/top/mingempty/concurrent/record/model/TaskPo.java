package top.mingempty.concurrent.record.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.mingempty.domain.base.BasePoModel;

import java.time.LocalDateTime;

/**
 * 线程池任务记录表
 *
 * @author zzhao
 * @date 2025-02-21 14:03:44
 */
@Schema(title = "线程池任务记录表")
@Data
@EqualsAndHashCode
public class TaskPo implements BasePoModel {
    /**
     * 线程任务ID
     */
    @Schema(title = "线程任务ID")
    private String taskId;

    /**
     * 线程池名称
     */
    @Schema(title = "线程池名称")
    private String poolName;

    /**
     * 提交任务类
     */
    @Schema(title = "提交任务类")
    private String taskClass;

    /**
     * 任务状态
     * <pre class="code">
     * 0：初始化
     * 1：已处理
     * 2：已撤销
     * (同字典条目编码：thread_status)
     * </pre>
     */
    @Schema(title = "任务状态", description = "0：初始化" +
			"1：已处理" +
			"2：已撤销" +
			"(同字典条目编码：thread_status)")
    private String taskStatus;

    /**
     * 记录时间
     */
    @Schema(title = "记录时间")
    private LocalDateTime recordTime;

    /**
     * 完成时间
     */
    @Schema(title = "完成时间")
    private LocalDateTime completionTime;

    /**
     * 链路ID
     */
    @Schema(title = "链路ID")
    private String traceId = "";

    /**
     * spanID
     */
    @Schema(title = "spanID")
    private String spanId = "";

    /**
     * 创建时间
     */
    @Schema(title = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(title = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @Schema(title = "创建人")
    private String createOperator;

    /**
     * 更新人
     */
    @Schema(title = "更新人")
    private String updateOperator;

    /**
     * 线程数据
     */
    @Schema(title = "线程数据")
    private String threadData;
}