package top.mingempty.commons.trace.constants;

/**
 * 静态常量类
 *
 * @author zzhao
 * @date: 2023/2/13 11:21
 */
public interface TraceConstant {

    /**
     * 整个调用链路树的唯一ID
     */
    String TRACE_ID = "mingempty-TraceId";

    /**
     * 本次调用在整个调用链路树中的位置
     */
    String SPAN_ID = "mingempty-SpanId";

    /**
     * 本次调用在整个调用链路树中的位置
     */
    String PROTOCOL = "mingempty-Protocol";

    /**
     * 本次调用在整个调用链路树中的位置
     */
    String SPAN_TYPE = "mingempty-SpanType";
}
