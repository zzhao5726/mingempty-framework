package top.mingempty.concurrent.thread;


import cn.hutool.core.map.MapUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.commons.trace.TraceContext;
import top.mingempty.commons.trace.constants.TraceConstant;
import top.mingempty.commons.trace.enums.SpanTypeEnum;
import top.mingempty.commons.util.JacksonUtil;
import top.mingempty.concurrent.model.enums.PriorityEnum;
import top.mingempty.trace.util.TraceAdapterUtil;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 抽象线程委托上下文类
 *
 * @param <V> 异步返回值
 * @author zzhao
 */
@Slf4j
public abstract class AbstractDelegatingContext<V> implements Comparable<AbstractDelegatingContext<V>> {
    @Getter
    @Schema(title = "发起线程的线程变量", description = "发起线程的线程变量")
    private final TraceContext traceContext;

    /**
     * 线程比较值
     */
    @Getter
    @Schema(title = "线程比较值", description = "线程比较值")
    private final Integer priority;

    /**
     * 异步所需要的参数
     */
    @Schema(title = "异步所需要的参数(封装为不可变MAP)", description = "异步所需要的参数(封装为不可变MAP)")
    protected final Map<String, Object> params;

    /**
     * 闭锁
     */
    @Schema(title = "闭锁", description = "闭锁")
    private final CountDownLatch countDownLatch;

    public AbstractDelegatingContext() {
        this(null, null, null, null);
    }

    public AbstractDelegatingContext(TraceContext traceContext) {
        this(null, null, null, traceContext);
    }

    public AbstractDelegatingContext(PriorityEnum priorityEnum) {
        this(null, priorityEnum, null, null);
    }

    public AbstractDelegatingContext(Map<String, Object> params) {
        this(params, null, null, null);
    }

    public AbstractDelegatingContext(CountDownLatch countDownLatch) {
        this(null, null, countDownLatch);
    }

    public AbstractDelegatingContext(CountDownLatch countDownLatch, TraceContext traceContext) {
        this(null, null, countDownLatch, traceContext);
    }

    public AbstractDelegatingContext(Map<String, Object> params, TraceContext traceContext) {
        this(params, null, null, traceContext);
    }

    public AbstractDelegatingContext(PriorityEnum priorityEnum, TraceContext traceContext) {
        this(null, priorityEnum, null, traceContext);
    }

    public AbstractDelegatingContext(Map<String, Object> params, PriorityEnum priorityEnum) {
        this(params, priorityEnum, null, null);
    }

    public AbstractDelegatingContext(Map<String, Object> params, CountDownLatch countDownLatch) {
        this(params, null, countDownLatch);
    }

    public AbstractDelegatingContext(CountDownLatch countDownLatch, PriorityEnum priorityEnum) {
        this(null, priorityEnum, countDownLatch);
    }


    public AbstractDelegatingContext(PriorityEnum priorityEnum, CountDownLatch countDownLatch) {
        this(null, priorityEnum, countDownLatch);
    }


    public AbstractDelegatingContext(Map<String, Object> params, CountDownLatch countDownLatch, TraceContext traceContext) {
        this(params, null, countDownLatch, traceContext);
    }

    public AbstractDelegatingContext(Map<String, Object> params, PriorityEnum priorityEnum, TraceContext traceContext) {
        this(params, priorityEnum, null, traceContext);
    }

    public AbstractDelegatingContext(CountDownLatch countDownLatch, PriorityEnum priorityEnum, TraceContext traceContext) {
        this(null, priorityEnum, countDownLatch, traceContext);
    }


    public AbstractDelegatingContext(PriorityEnum priorityEnum, CountDownLatch countDownLatch, TraceContext traceContext) {
        this(null, priorityEnum, countDownLatch, traceContext);
    }


    public AbstractDelegatingContext(Map<String, Object> params, PriorityEnum priorityEnum,
                                     CountDownLatch countDownLatch) {
        this(params, priorityEnum, countDownLatch, TraceAdapterUtil.gainTraceContext());
    }


    public AbstractDelegatingContext(Map<String, Object> params, PriorityEnum priorityEnum,
                                     CountDownLatch countDownLatch, TraceContext traceContext) {
        this.traceContext = traceContext == null ? TraceAdapterUtil.gainTraceContext() : traceContext;
        this.priority = priorityEnum == null ? PriorityEnum.D.getPriority() : priorityEnum.getPriority();
        this.countDownLatch = countDownLatch;
        if (MapUtil.isNotEmpty(params)) {
            this.params = Collections.unmodifiableMap(params);
        } else {
            this.params = Collections.emptyMap();
        }
        log.debug("线程池入参--------\npriority[{}]\nparams[{}]",
                this.priority, JacksonUtil.toStr(params));
    }

    @Override
    public int compareTo(AbstractDelegatingContext abstractDelegatingContext) {
        return this.getPriority().compareTo(abstractDelegatingContext.getPriority());
    }

    /**
     * 抽象的异步上下文内部逻辑
     *
     * @return 线程执行结果
     */
    @SneakyThrows
    protected final V abstractRun() {
        V v = null;
        try {
            Object newSpan = this.params.get(TraceConstant.NEW_SPAN);
            Object functionName = this.params.get(TraceConstant.FUNCTION_NAME);
            TraceAdapterUtil.initTraceContextAsync(traceContext,
                    newSpan != null && Boolean.parseBoolean(newSpan.toString()),
                    SpanTypeEnum.THREAD_ASYNC,
                    functionName != null ? functionName.toString() : this.getClass().getName(),
                    params);
            v = realRun();
            return v;
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                // 虽然打印的是error级别的日志，但是在异步容器内发生的异常，通常指的是业务的异常，需要由业务系统进行捕获处理。
                // 如果业务系统没有处理，并且需要日志时，可以开启debug级别日志，打印线程池的异常
                log.error("线程池执行异常。异常信息为：", e);
            }
            throw e;
        } finally {
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
            TraceAdapterUtil.endTraceContext(v);
        }
    }

    /**
     * 线程真正执行的业务方法
     *
     * @return 线程执行结果
     */
    @Schema(title = "线程真正执行业务的方法", description = "线程真正执行业务的方法")
    public abstract V realRun() throws Exception;

}
