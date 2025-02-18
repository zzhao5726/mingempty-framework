package top.mingempty.event.record.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.mingempty.domain.base.BasePoModel;

import java.time.LocalDateTime;

/**
 * 事件发布记录表
 *
 * @author zzhao
 * @date 2025-02-21 14:07:01
 */
@Schema(title = "事件发布记录表")
@Data
@EqualsAndHashCode
public class PublisherRecordPo implements BasePoModel {
    /**
     * 事件ID
     */
    @Schema(title = "事件ID")
    private String eventId;

    /**
     * 事件类型
     */
    @Schema(title = "事件类型")
    private String eventType;

    /**
     * 业务号码
     */
    @Schema(title = "业务号码")
    private String bizNo;

    /**
     * 事件类
     */
    @Schema(title = "事件类")
    private String eventClass;

    /**
     * 事件是否已处理
     * <pre class="code">
     * 0：初始化
     * 1：已处理
     * 2：已撤销
     * (同字典条目编码：event_status)
     * </pre>
     */
    @Schema(title = "事件是否已处理", description = "0：初始化" +
			"1：已处理" +
			"2：已撤销" +
			"(同字典条目编码：event_status)")
    private String eventStatus;

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
     * 跨度ID
     */
    @Schema(title = "跨度ID")
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
     * 事件数据
     */
    @Schema(title = "事件数据")
    private String eventData;
}