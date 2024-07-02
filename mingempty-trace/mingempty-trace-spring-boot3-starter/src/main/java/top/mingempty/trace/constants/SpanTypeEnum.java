package top.mingempty.trace.constants;

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

    @Schema(title = "标准", description = "标准")
    NORMAL(0, false, "标准"),

    @Schema(title = "事件同步", description = "事件同步")
    EVENT_SYNC(1, false, "事件同步"),

    @Schema(title = "事件异步", description = "事件异步")
    EVENT_ASYNC(2, true, "事件异步"),

    @Schema(title = "rabbitMq", description = "rabbitMq")
    MQ_RABBITMQ(3, true, "rabbitMq"),

    @Schema(title = "kafka", description = "kafka")
    MQ_KAFKA(4, true, "kafka"),

    @Schema(title = "rocketMq", description = "rocketMq")
    MQ_ROCKETMQ(5, true, "rocketMq"),

    @Schema(title = "otherMq", description = "otherMq")
    MQ_OTHER(6, true, "otherMq"),

    @Schema(title = "线程异步", description = "线程异步")
    THREAD_ASYNC(7, true, "线程异步"),

    @Schema(title = "批处理", description = "批处理")
    BATCH(8, false, "批处理"),
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
    private final boolean asyn;

    /**
     * 描述
     */
    @Schema(title = "描述", description = "描述")
    private final String desc;

    SpanTypeEnum(int type, boolean asyn, String desc) {
        this.type = type;
        this.asyn = asyn;
        this.desc = desc;
    }


    private final static Map<Integer, SpanTypeEnum> SPAN_TYPE_ENUM_OPTIONAL_MAP =
            Arrays.stream(SpanTypeEnum.values()).collect(Collectors.toMap(SpanTypeEnum::getType, Function.identity()));


    public static SpanTypeEnum getSpanType(Integer type) {
        return SPAN_TYPE_ENUM_OPTIONAL_MAP.get(type);
    }

}
