package top.mingempty.commons.trace;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.slf4j.MDC;
import top.mingempty.commons.data.StopWatch;
import top.mingempty.commons.trace.constants.TraceConstant;
import top.mingempty.commons.trace.enums.ProtocolEnum;
import top.mingempty.commons.trace.enums.SpanTypeEnum;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程变量上下文
 *
 * @author zzhao
 */
public class TraceContext {

    /**
     * 当前线程链路数据
     */
    @Schema(title = "当前线程链路数据")
    private final static ThreadLocal<TraceContext> TRACE_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 线程ID
     */
    @Getter
    @Schema(title = "线程ID")
    private final Long currentThreadId = Thread.currentThread().threadId();

    /**
     * 线程名称
     */
    @Getter
    @Schema(title = "线程名称")
    private final String currentThreadName = Thread.currentThread().getName();

    /**
     * 方法名称
     */
    @Getter
    @Schema(title = "方法名称")
    private final String functionName;


    /**
     * 整个调用链路树的唯一ID
     */
    @Getter
    @Schema(title = "整个调用链路树的唯一ID")
    private final String traceId;


    /**
     * 本次调用在整个调用链路树中的位置
     */
    @Getter
    @Schema(title = "本次调用在整个调用链路树中的位置")
    private final String spanId;


    /**
     * 本次调用在整个调用链路树中的位置
     */
    @Schema(title = "本次调用在整个调用链路树中的位置")
    private final AtomicInteger spanCount;

    /**
     * 线程重入次数
     */
    @Schema(title = "线程重入次数")
    private final AtomicInteger threadReentryCount;

    /**
     * 链路入口来源
     */
    @Getter
    @Schema(title = "链路入口来源")
    private final ProtocolEnum protocolEnum;

    /**
     * 链路树类型
     */
    @Getter
    @Schema(title = "链路树类型")
    private final SpanTypeEnum spanTypeEnum;

    /**
     * 线程计数器
     */
    @Getter
    @Schema(title = "线程计数器")
    private final StopWatch stopWatch = new StopWatch();

    /**
     * 是否是旧的链路树
     */
    @Getter
    @Schema(title = "是否是旧的链路树")
    private boolean oldSpan = false;


    public TraceContext(String functionName,
                        String traceId,
                        String spanId,
                        ProtocolEnum protocolEnum,
                        SpanTypeEnum spanTypeEnum) {
        this(functionName,
                traceId,
                spanId,
                1,
                new AtomicInteger(0),
                protocolEnum,
                spanTypeEnum);
    }

    public TraceContext(String functionName,
                        String traceId,
                        String spanId,
                        Integer spanCount,
                        AtomicInteger threadReentryCount,
                        ProtocolEnum protocolEnum,
                        SpanTypeEnum spanTypeEnum) {
        this(functionName, traceId, spanId, new AtomicInteger(spanCount), threadReentryCount, protocolEnum, spanTypeEnum);
    }

    private TraceContext(String functionName,
                         String traceId,
                         String spanId,
                         AtomicInteger spanCount,
                         AtomicInteger threadReentryCount,
                         ProtocolEnum protocolEnum,
                         SpanTypeEnum spanTypeEnum) {
        assert functionName != null;
        assert traceId != null;
        assert spanId != null;
        if (threadReentryCount != null
                && threadReentryCount.get() < 1) {
            threadReentryCount.set(1);
        }
        if (spanCount != null
                && spanCount.get() < 1) {
            spanCount.set(1);
        }
        this.functionName = functionName;
        this.traceId = traceId;
        this.spanId = spanId;
        this.spanCount = spanCount == null
                ? new AtomicInteger(1) : spanCount;
        this.threadReentryCount = threadReentryCount == null
                ? new AtomicInteger(1) : threadReentryCount;
        this.protocolEnum = protocolEnum == null
                ? ProtocolEnum.HTTP : protocolEnum;
        this.spanTypeEnum = spanTypeEnum == null
                ? SpanTypeEnum.NORMAL : spanTypeEnum;
    }

    /**
     * 拿到当前线程的线程上下文变量
     */
    public static TraceContext gainTraceContext() {
        return TRACE_CONTEXT_THREAD_LOCAL.get();
    }

    /**
     * 获取当前线程的可重入次数
     *
     * @return
     */
    public int threadReentryCountGet() {
        return threadReentryCount.get();
    }

    /**
     * 线程重入次数+1后返回当前值
     *
     * @return
     */
    public int threadReentryCountIncrementAndGet() {
        return threadReentryCount.incrementAndGet();
    }

    /**
     * 线程重入次数-1后返回当前值
     *
     * @return
     */
    public int threadReentryCountDecrementAndGet() {
        return threadReentryCount.decrementAndGet();
    }


    /**
     * 返回当前链路树的值，并将其+1
     *
     * @return
     */
    public int spanCountGetAndIncrement() {
        return spanCount.getAndIncrement();
    }


    /**
     * 获取当前链路的耗时
     *
     * @return 获取当前链路的耗时
     */
    public long gainTraceTimeConsuming() {
        if (this.getStopWatch().isRunning()) {
            return TimeUnit.MILLISECONDS
                    .convert(System.nanoTime()
                                    - this.getStopWatch().getStartTimeNanos(),
                            TimeUnit.NANOSECONDS);
        }
        return this.getStopWatch().getLastTaskTimeMillis();
    }


    /**
     * 开始
     */
    public void start() {
        TRACE_CONTEXT_THREAD_LOCAL.set(this);
        // 给日志变量设置值
        MDC.put(TraceConstant.TRACE_ID, this.getSpanId());
        MDC.put(TraceConstant.SPAN_ID, this.getSpanId());
        MDC.put(TraceConstant.PROTOCOL, String.valueOf(this.getProtocolEnum().getCode()));
        MDC.put(TraceConstant.SPAN_TYPE, String.valueOf(this.getSpanTypeEnum().getType()));
        if (!this.getStopWatch().isRunning()) {
            this.getStopWatch().start();
        }
    }

    /**
     * 结束
     */
    public void end() {
        TRACE_CONTEXT_THREAD_LOCAL.remove();
        // 同时，清空日志的环境变量
        MDC.remove(TraceConstant.TRACE_ID);
        MDC.remove(TraceConstant.SPAN_ID);
        MDC.remove(TraceConstant.PROTOCOL);
        MDC.remove(TraceConstant.SPAN_TYPE);
        if (this.getStopWatch().isRunning()) {
            this.getStopWatch().stop();
        }
    }


    /**
     * 同进程内需要无需创建新链路树节点时使用
     *
     * @return
     */
    public TraceContext cloneRow(SpanTypeEnum spanTypeEnum) {
        TraceContext traceContext = new TraceContext(this.functionName, this.traceId, this.spanId, this.spanCount,
                new AtomicInteger(1), this.protocolEnum, spanTypeEnum);
        traceContext.oldSpan = true;
        return traceContext;
    }
}
