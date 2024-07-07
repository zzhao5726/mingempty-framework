package top.mingempty.commons.trace.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 链路树类型
 *
 * @author zzhao
 */
@Getter
@Schema(title = "链路树类型", description = "链路树类型")
public enum SpanTypeEnum {

    NORMAL(0, false, "标准"),

    EVENT_SYNC(1, false, "同步事件"),

    EVENT_ASYNC(2, true, "异步事件"),

    MQ_RABBITMQ(3, true, "rabbitMq"),

    MQ_KAFKA(4, true, "kafka"),

    MQ_ROCKETMQ(5, true, "rocketMq"),

    MQ_OTHER(6, true, "otherMq"),

    THREAD_ASYNC(7, true, "异步线程"),

    STREAM_ASYNC(8, true, "流异步线程"),

    BATCH(9, false, "批处理"),
    ;

    /**
     * 类型
     */
    @Schema(title = "类型", description = "类型")
    private final int type;

    /**
     * 是否是异步
     */
    @Schema(title = "是否是异步", description = "是否是异步")
    private final boolean async;

    /**
     * 描述
     */
    @Schema(title = "描述", description = "描述")
    private final String desc;

    SpanTypeEnum(int type, boolean async, String desc) {
        this.type = type;
        this.async = async;
        this.desc = desc;
    }

    private final static Map<Integer, SpanTypeEnum> SPAN_TYPE_ENUM_OPTIONAL_MAP =
            Arrays.stream(SpanTypeEnum.values()).collect(Collectors.toMap(SpanTypeEnum::getType, Function.identity()));


    public static SpanTypeEnum getSpanType(Integer type) {
        return SPAN_TYPE_ENUM_OPTIONAL_MAP.get(type);
    }

}
