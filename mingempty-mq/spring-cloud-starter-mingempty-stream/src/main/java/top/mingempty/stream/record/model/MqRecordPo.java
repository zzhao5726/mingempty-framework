package top.mingempty.stream.record.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.mingempty.domain.base.BasePoModel;

import java.time.LocalDateTime;

/**
 * MQ消息记录表
 *
 * @author zzhao
 * @date 2025-02-23 21:21:57
 */
@Schema(title = "MQ消息记录表")
@Data
@EqualsAndHashCode
public class MqRecordPo implements BasePoModel {
    /**
     * ID
     */
    @Schema(title = "ID")
    private String mqId;

    /**
     * 消息ID
     */
    @Schema(title = "消息ID")
    private String messageId;

    /**
     * 消息记录类型
     * <pre class="code">
     * 0：发送者
     * 1：消费者
     * </pre>
     */
    @Schema(title = "消息记录类型", description = "0：发送者" +
			"1：消费者")
    private String recordType;

    /**
     * 绑定元件名称
     */
    @Schema(title = "绑定元件名称")
    private String componentName;

    /**
     * 绑定元件名称
     */
    @Schema(title = "绑定元件名称")
    private String beanName;

    /**
     * 任务状态
     * <pre class="code">
     * 0：初始化
     * 1：已处理
     * 2：已撤销
     * (同字典条目编码：message_status)
     * </pre>
     */
    @Schema(title = "任务状态", description = "0：初始化" +
			"1：已处理" +
			"2：已撤销" +
			"(同字典条目编码：message_status)")
    private String messageStatus;

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
    private String traceId;

    /**
     * spanID
     */
    @Schema(title = "spanID")
    private String spanId;

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
     * mq消息数据
     */
    @Schema(title = "mq消息数据")
    private String messagePayload;

    /**
     * mq消息头数据
     */
    @Schema(title = "mq消息头数据")
    private String messageHeaders;
}