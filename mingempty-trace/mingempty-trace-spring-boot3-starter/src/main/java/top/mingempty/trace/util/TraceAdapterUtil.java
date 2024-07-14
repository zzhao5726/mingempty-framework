package top.mingempty.trace.util;

import io.swagger.v3.oas.annotations.media.Schema;
import top.mingempty.commons.data.StopWatch;
import top.mingempty.commons.trace.TraceContext;
import top.mingempty.commons.trace.enums.ProtocolEnum;
import top.mingempty.commons.trace.enums.SpanTypeEnum;
import top.mingempty.domain.enums.ParameteTypeEnum;
import top.mingempty.trace.domain.TracePushBlockingQueue;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 链路适配器工具
 *
 * @author zzhao
 */
public class TraceAdapterUtil {

    /**
     * 链路是否初始化
     */
    @Schema(title = "链路是否初始化")
    private final static ThreadLocal<Boolean> IS_INITIALIZED_THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取链路初始化标识
     */
    public static boolean initialized() {
        return Optional
                .ofNullable(IS_INITIALIZED_THREAD_LOCAL.get())
                .orElse(false);
    }

    /**
     * 获取当前链路数据
     *
     * @return 链路数据
     */
    public static TraceContext gainTraceContext() {
        if (!initialized()) {
            return null;
        }
        return TraceContext.gainTraceContext();
    }

    /**
     * 获取当前链路计数器
     *
     * @return 链路计数器
     */
    public static StopWatch gainStopWatch() {
        if (!initialized()
                || gainTraceContext() == null) {
            return null;
        }
        return Objects.requireNonNull(gainTraceContext()).getStopWatch();
    }


    /**
     * 初始化方法</br>
     * <p>
     * 适用于链路发起者 如接口第一次被调用
     *
     * @param functionName     方法名称
     * @param protocolEnum     链路入口来源
     * @param spanTypeEnum     链路树类型
     * @param requestParameter 请求参数
     */
    public static void initTraceContext(String functionName, ProtocolEnum protocolEnum,
                                        SpanTypeEnum spanTypeEnum, Object requestParameter) {
        initTraceContext(functionName, TraceIdGenerator.generateTraceId(), TraceIdGenerator.generateSpanId(),
                protocolEnum, spanTypeEnum, requestParameter);
    }


    /**
     * 初始化方法</br>
     * <p>
     * 适用于下游初始化，如服务间调用、MQ
     *
     * @param functionName     方法名称
     * @param traceId          整个调用链路树的唯一ID
     * @param spanId           本次调用在整个调用链路树中的位置
     * @param protocolEnum     链路入口来源
     * @param spanTypeEnum     链路树类型
     * @param requestParameter 请求参数
     */
    public static void initTraceContext(String functionName, String traceId,
                                        String spanId, ProtocolEnum protocolEnum,
                                        SpanTypeEnum spanTypeEnum, Object requestParameter) {
        if (initialized()
                && gainTraceContext() != null) {
            if (Objects.equals(Thread.currentThread().threadId(), Objects.requireNonNull(gainTraceContext()).getCurrentThreadId())) {
                //说明是同一个线程，此时其余参数失效
                // 线程重入次数+1
                Objects.requireNonNull(gainTraceContext()).threadReentryCountIncrementAndGet();
                return;
            }
            return;
        }
        TraceContext traceContext = new TraceContext(functionName,
                traceId == null ? TraceIdGenerator.generateTraceId() : traceId,
                spanId == null ? TraceIdGenerator.generateSpanId() : spanId,
                1, new AtomicInteger(1), protocolEnum, spanTypeEnum);
        //初始化方法
        initialized(traceContext, requestParameter);
    }

    /**
     * 初始化方法</br>
     * <p>
     * 适用于事件和异步线程
     *
     * @param traceContext     发起方线程链路数据
     * @param newSpan          是否开启新的链路树
     * @param spanTypeEnum     链路树类型
     * @param functionName     方法名称
     * @param requestParameter 链路入参
     */
    public static void initTraceContextAsync(TraceContext traceContext, boolean newSpan,
                                             SpanTypeEnum spanTypeEnum, String functionName,
                                             Object requestParameter) {
        if (traceContext == null) {
            //说明没有链路数据，直接开启新的
            initTraceContext(functionName, ProtocolEnum.OTHER, spanTypeEnum, requestParameter);
            return;
        }

        if (Objects.equals(Thread.currentThread().threadId(), traceContext.getCurrentThreadId())) {
            //说明是同一个线程，此时其余参数失效
            // 线程重入次数+1
            traceContext.threadReentryCountIncrementAndGet();
            gainTraceContext();
            return;
        }
        //说明已经不是同一个线程了
        if (newSpan) {
            //说明需要开启一个新的链路树
            initTraceContext(functionName, traceContext.getTraceId(), TraceIdGenerator.generateSpanId(traceContext),
                    traceContext.getProtocolEnum(),
                    spanTypeEnum, requestParameter);
            gainTraceContext();
            return;
        }

        //说明不需要开启一个新的链路树,需设置当前线程的链路树类型
        initialized(traceContext.cloneRow(spanTypeEnum));
        gainTraceContext();
    }


    /**
     * 设置初始化标识为true
     */
    private static void initialized(TraceContext traceContext, Object requestParameter) {
        initialized(traceContext);
        TracePushBlockingQueue.offer(traceContext, ParameteTypeEnum.REQUEST, requestParameter);
    }

    /**
     * 设置初始化标识为true
     */
    private static void initialized(TraceContext traceContext) {
        traceContext.start();
        IS_INITIALIZED_THREAD_LOCAL.set(true);
    }

    /**
     * 结束链路
     */
    public static void endTraceContext() {
        endTraceContext(null);
    }

    /**
     * 结束链路
     *
     * @param responseParameter 链路出参
     */
    public static void endTraceContext(Object responseParameter) {
        //当前线程链路信息不为空并且线程重入次数等于0时，直接结束
        TraceContext traceContext = gainTraceContext();
        if (traceContext == null) {
            return;
        }

        if (traceContext.threadReentryCountDecrementAndGet() > 0) {
            return;
        }
        traceContext.end();
        if (!traceContext.isOldSpan()) {
            //只有非旧的链路才需要记录
            TracePushBlockingQueue.offer(traceContext, ParameteTypeEnum.RESPONSE, responseParameter,
                    traceContext.gainTraceTimeConsuming());

        }
        IS_INITIALIZED_THREAD_LOCAL.remove();
    }

}
