package top.mingempty.trace.util;

import io.swagger.v3.oas.annotations.media.Schema;
import top.mingempty.trace.constants.ProtocolEnum;
import top.mingempty.trace.constants.SpanTypeEnum;
import top.mingempty.trace.domain.TraceContext;

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
     * 设置初始化标识为true
     */
    private static void initialized() {
        IS_INITIALIZED_THREAD_LOCAL.set(true);
    }

    /**
     * 清空链路初始化标识
     */
    public static void removeInitialized() {
        IS_INITIALIZED_THREAD_LOCAL.remove();
    }

    /**
     * 获取链路初始化标识
     */
    public static boolean getInitialized() {
        return Optional
                .ofNullable(IS_INITIALIZED_THREAD_LOCAL.get())
                .orElse(false);
    }


    /**
     * 获取当前链路数据
     *
     * @return 链路数据
     */
    public static TraceContext getTraceContext() {
        if (TraceContext.getTraceContext() != null
                && getInitialized()) {
            return TraceContext.getTraceContext();
        }
        return null;
    }


    /**
     * 获取当前链路数据
     *
     * @return 链路数据
     */
    public static TraceContext initTraceContext(String functionName, String traceId, String spanId,
                                                ProtocolEnum protocolEnum,
                                                SpanTypeEnum spanTypeEnum, Object requestParameter) {
        return initTraceContext(functionName, traceId, spanId, null, null,
                protocolEnum, spanTypeEnum, requestParameter);
    }


    /**
     * 获取当前链路数据
     *
     * @return 链路数据
     */
    public static TraceContext initTraceContext(String functionName, ProtocolEnum protocolEnum,
                                                SpanTypeEnum spanTypeEnum, Object requestParameter) {
        return initTraceContext(functionName, null, null, null, null,
                protocolEnum, spanTypeEnum, requestParameter);
    }


    /**
     * 获取当前链路数据
     *
     * @return 链路数据
     */
    public static TraceContext initTraceContext(String functionName, String traceId, String spanId,
                                                AtomicInteger spanCount, AtomicInteger threadReentryCount,
                                                ProtocolEnum protocolEnum, SpanTypeEnum spanTypeEnum,
                                                Object requestParameter) {
        if (TraceContext.getTraceContext() != null
                && getInitialized()) {
            return TraceContext.getTraceContext();
        }
        synchronized (Thread.currentThread()) {
            if (TraceContext.getTraceContext() != null
                    && getInitialized()) {
                return TraceContext.getTraceContext();
            }
            TraceContext traceContext = new TraceContext(functionName, traceId, spanId, spanCount,
                    threadReentryCount, protocolEnum, spanTypeEnum);
            traceContext.start(requestParameter);
            //初始化
            TraceContext.putTraceContext(traceContext);
            initialized();
            return TraceContext.getTraceContext();
        }
    }


    /**
     * 获取当前链路数据
     *
     * @return 链路数据
     */
    public static TraceContext initTraceContext(TraceContext partentTraceContext, String functionName,
                                                SpanTypeEnum spanTypeEnum,
                                                Object requestParameter) {
        if (!getInitialized()) {
            return null;
        }

        if (TraceContext.getTraceContext() != null
                && getInitialized()) {
            return TraceContext.getTraceContext();
        }
        synchronized (Thread.currentThread()) {
            if (TraceContext.getTraceContext() != null
                    && getInitialized()) {
                return TraceContext.getTraceContext();
            }
            if (partentTraceContext == null) {
                //如果为空直接重新初始化
                return initTraceContext(functionName, null, spanTypeEnum, requestParameter);
            }
            TraceContext traceContext = partentTraceContext.clone(functionName, spanTypeEnum);
            traceContext.start(requestParameter);
            //初始化
            TraceContext.putTraceContext(traceContext);
            initialized();
            return TraceContext.getTraceContext();
        }
    }

    /**
     * 清空线程变量内的线程上线文数据
     */
    public static int clearTraceContext() {
        return clearTraceContext(null);
    }


    /**
     * 清空线程变量内的线程上线文数据。
     * 同时在清理前执行一个没有返回值的方法
     *
     * @param responseParameter 接口返回结果集
     * @return
     */
    public static int clearTraceContext(Object responseParameter) {
        TraceContext traceContext = getTraceContext();
        if (traceContext == null) {
            //说明当前线程没有初始化链路
            return -1;
        }
        synchronized (Thread.currentThread()) {
            int threadReentryCountByAfter = traceContext.threadReentryCountGet() - 1;
            if (threadReentryCountByAfter == -1) {
                //说明已经清理过了
                return 0;
            }
            if (threadReentryCountByAfter == 0
                    && traceContext.getCurrentThreadId().equals(Thread.currentThread().threadId())) {
                // 当线程重入次数等于0并且上线文线程ID等于当前线程ID的时候，才会清空线程变量内的值
                traceContext.end(responseParameter);
            }
            //此时返回线程重入次数
            return traceContext.threadReentryCountDecrementAndGet();

        }
    }

    /**
     * 开启异步链路ID计数器
     */
    public static TraceContext beginAsyncCounter(TraceContext partentTraceContext, String functionName,
                                                 ProtocolEnum protocolEnum,
                                                 SpanTypeEnum spanTypeEnum, Object requestParameter) {
        if (partentTraceContext == null) {
            //说明是一个新的链路,重新生成
            return initTraceContext(functionName, protocolEnum, spanTypeEnum, requestParameter);
        }
        if (partentTraceContext.getCurrentThreadId().equals(Thread.currentThread().threadId())) {
            //说明是同一个线程
            //spanCount为0，线程重入次数+1
            partentTraceContext.threadReentryCountIncrementAndGet();
            return initTraceContext(partentTraceContext, functionName, spanTypeEnum, requestParameter);
        }

        //说明不是同一个线程
        return initTraceContext(functionName, partentTraceContext.getTraceId(),
                TraceIdGenerator.generateSpanId(partentTraceContext), new AtomicInteger(0),
                new AtomicInteger(1),
                protocolEnum, spanTypeEnum, requestParameter);
    }

    public static void aaa(SpanTypeEnum protocolEnum) {

    }

}
