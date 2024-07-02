package top.mingempty.trace.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.mingempty.commons.data.StopWatch;
import top.mingempty.domain.enums.ParameteTypeEnum;
import top.mingempty.trace.constants.ProtocolEnum;
import top.mingempty.trace.constants.SpanTypeEnum;
import top.mingempty.trace.constants.TraceConstant;
import top.mingempty.trace.util.TraceIdGenerator;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程变量上下文
 *
 * @author zzhao
 * @date: 2023/8/1 16:43
 */
@Slf4j
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
     * 链路耗时统计
     */
    @Schema(title = "链路耗时统计")
    private final StopWatch stopWatch = new StopWatch();
    /**
     * 用于访问与请求关联的属性对象的抽象
     */
    @Getter
    @Schema(title = "用于访问与请求关联的属性对象的抽象")
    private RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();


    public TraceContext(String functionName, String traceId, String spanId,
                        ProtocolEnum protocolEnum, SpanTypeEnum spanTypeEnum) {
        this(functionName, traceId, spanId, null, null, protocolEnum, spanTypeEnum);
    }

    public TraceContext(String functionName, String traceId, String spanId,
                        AtomicInteger spanCount, AtomicInteger threadReentryCount,
                        ProtocolEnum protocolEnum, SpanTypeEnum spanTypeEnum) {
        this.functionName = functionName == null ? TraceConstant.DEFAULT_FUNCTION_NAME : functionName;
        this.traceId = traceId == null ? TraceIdGenerator.generateTraceId() : traceId;
        this.spanId = spanId == null ? TraceIdGenerator.generateSpanId() : spanId;
        this.spanCount = spanCount == null ? new AtomicInteger(0) : spanCount;
        this.threadReentryCount = threadReentryCount == null ? new AtomicInteger(1) : threadReentryCount;
        this.protocolEnum = protocolEnum == null ? ProtocolEnum.HTTP : protocolEnum;
        this.spanTypeEnum = spanTypeEnum == null ? SpanTypeEnum.NORMAL : spanTypeEnum;
    }

    /**
     * 拿到当前线程的线程上下文变量
     */
    public static TraceContext getTraceContext() {
        return TRACE_CONTEXT_THREAD_LOCAL.get();
    }


    /**
     * 设置线程变量
     */
    public static void putTraceContext(TraceContext traceContext) {
        TRACE_CONTEXT_THREAD_LOCAL.set(traceContext);
    }

    /**
     * 设置线程变量
     */
    public static void removeTraceContext() {
        TRACE_CONTEXT_THREAD_LOCAL.remove();
    }

    public void start(Object requestParameter) {
        // 给日志变量设置值
        MDC.put(TraceConstant.TRACE_ID, this.traceId);
        MDC.put(TraceConstant.SPAN_ID, this.spanId);
        MDC.put(TraceConstant.PROTOCOL, String.valueOf(this.protocolEnum.getCode()));
        MDC.put(TraceConstant.SPAN_TYPE, String.valueOf(this.spanTypeEnum.getType()));
        stopWatch.start();
        TracePushBlockingQueue.offer(this, ParameteTypeEnum.REQUEST, requestParameter);
    }

    public void end(Object responseParameter) {
        removeTraceContext();
        // 同时，清空日志的环境变量
        MDC.remove(TraceConstant.TRACE_ID);
        MDC.remove(TraceConstant.SPAN_ID);
        MDC.remove(TraceConstant.PROTOCOL);
        MDC.remove(TraceConstant.SPAN_TYPE);
        stopWatch.stop();
        TracePushBlockingQueue.offer(this, ParameteTypeEnum.RESPONSE, responseParameter);
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
     * 线程重入次数-1后返回当前值
     *
     * @return
     */
    public int threadReentryCountDecrementAndGet() {
        return threadReentryCount.decrementAndGet();
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
     * 返回当前链路树的值，并将其+1
     *
     * @return
     */
    public int spanCountGetAndIncrement() {
        return spanCount.getAndIncrement();
    }

    /**
     * 获取任务耗时
     *
     * @return
     */
    public long timeConsuming() {
        if (stopWatch.isRunning()) {
            return 0;
        }
        return stopWatch.getTotalTimeMillis();
    }


    public TraceContext clone(String functionName,
                              SpanTypeEnum spanTypeEnum) {
        return new TraceContext(functionName, this.getTraceId(),
                TraceIdGenerator.generateSpanId(this), new AtomicInteger(0),
                this.threadReentryCount,
                this.protocolEnum, spanTypeEnum);

    }
}
