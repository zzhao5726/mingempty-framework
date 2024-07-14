package top.mingempty.commons.trace.constants;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 静态常量类
 *
 * @author zzhao
 * @date 2023/2/13 11:21
 */
@Schema(title = "链路常量类", description = "链路常量类")
public interface TraceConstant {

    /**
     * 整个调用链路树的唯一ID
     */
    @Schema(title = "整个调用链路树的唯一ID", description = "整个调用链路树的唯一ID")
    String TRACE_ID = "mingempty-trace-id";

    /**
     * 本次调用在整个调用链路树中的位置
     */
    @Schema(title = "本次调用在整个调用链路树中的位置", description = "本次调用在整个调用链路树中的位置")
    String SPAN_ID = "mingempty-trace-span-id";

    /**
     * 本次调用在整个调用链路树中的位置
     */
    @Schema(title = "本次调用在整个调用链路树中的位置", description = "本次调用在整个调用链路树中的位置")
    String PROTOCOL = "mingempty-trace-protocol";

    /**
     * 本次调用在整个调用链路树中的位置
     */
    @Schema(title = "本次调用在整个调用链路树中的位置", description = "本次调用在整个调用链路树中的位置")
    String SPAN_TYPE = "mingempty-trace-span-type";

    /**
     * 链路内方法名称
     */
    @Schema(title = "链路内方法名称", description = "链路内方法名称")
    String FUNCTION_NAME = "mingempty-trace-function-name";

    /**
     * 是否开启一个新的链路树节点常量
     */
    @Schema(title = "是否开启一个新的链路树节点常量", description = "是否开启一个新的链路树节点常量")
    String NEW_SPAN = "mingempty-trace-new-span";
}
