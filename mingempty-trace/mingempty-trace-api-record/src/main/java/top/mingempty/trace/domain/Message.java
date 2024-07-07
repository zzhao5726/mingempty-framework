package top.mingempty.trace.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import top.mingempty.domain.enums.ParameteTypeEnum;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 请求链路信息记录
 *
 * @author zzhao
 */
@Getter
@Builder()
@AllArgsConstructor
@Schema(title = "请求链路信息记录")
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 项目名称
     */
    @Schema(title = "项目名称")
    private final String appName;

    /**
     * 项目分组
     */
    @Schema(title = "项目分组")
    private final String appGroup;

    /**
     * 项目版本
     */
    @Schema(title = "项目版本")
    private final String appVersion;


    /**
     * 当前对象创建时间
     */
    @Schema(title = "当前对象创建时间")
    private final LocalDateTime now = LocalDateTime.now();

    /**
     * 参数
     */
    @Schema(title = "参数")
    private final Object parameter;

    /**
     * 参数类型
     */
    @Schema(title = "参数类型")
    private final ParameteTypeEnum parameteTypeEnum;


    /**
     * 整个调用链路树的唯一ID
     */
    @Schema(title = "整个调用链路树的唯一ID")
    private final String traceId;

    /**
     * 本次调用在整个调用链路树中的位置
     */
    @Schema(title = "本次调用在整个调用链路树中的位置")
    private final String spanId;

    /**
     * 线程名称
     */
    @Schema(title = "线程名称")
    private final String currentThreadName;

    /**
     * 线程ID
     */
    @Schema(title = "线程ID")
    private final Long currentThreadId;

    /**
     * 方法名称
     */
    @Schema(title = "方法名称")
    private final String functionName;

    /**
     * 链路入口来源
     */
    @Schema(title = "链路入口来源")
    private final int protocolCode;

    /**
     * 链路树类型
     */
    @Schema(title = "链路树类型")
    private final int spanType;

    /**
     * 接口耗时
     */
    @Schema(title = "接口耗时")
    private final Long timeConsuming;


}
