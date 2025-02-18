package top.mingempty.concurrent.trace;

import top.mingempty.commons.trace.TraceContext;
import top.mingempty.commons.trace.enums.SpanTypeEnum;
import top.mingempty.domain.function.IBuilder;
import top.mingempty.trace.util.TraceAdapterUtil;

/**
 * 并行流链路跟踪
 *
 * @author zzhao
 */
public class ParallelStreamTrace implements IBuilder<ParallelStreamTrace> {

    private final TraceContext traceContext = TraceAdapterUtil.gainTraceContext();

    /**
     * 私有化构造器
     */
    private ParallelStreamTrace() {
    }

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public ParallelStreamTrace build() {
        return new ParallelStreamTrace();
    }

    /**
     * 初始化当前流的链路数据
     */
    public void init() {
        init(null, null, null);
    }

    /**
     * 初始化当前流的链路数据
     *
     * @param newSpan      是否开启新的链路树
     * @param functionName 方法名称
     */

    public void init(Boolean newSpan, String functionName) {
        init(newSpan, functionName, null);
    }


    /**
     * 初始化当前流的链路数据
     *
     * @param newSpan          是否开启新的链路树
     * @param functionName     方法名称
     * @param requestParameter 链路入参
     */
    public void init(Boolean newSpan, String functionName, Object requestParameter) {
        newSpan = newSpan != null && newSpan;
        functionName = functionName != null ? functionName : "parallelStream";
        TraceAdapterUtil.initTraceContextAsync(traceContext,
                newSpan,
                SpanTypeEnum.STREAM_ASYNC,
                functionName,
                requestParameter);
    }

    /**
     * 清空当前链路数据
     */
    public void end() {
        end(null);
    }


    /**
     * 清空当前链路数据
     *
     * @param responseParameter 链路出参
     */
    public void end(Object responseParameter) {
        TraceAdapterUtil.endTraceContext(responseParameter);
    }


}
