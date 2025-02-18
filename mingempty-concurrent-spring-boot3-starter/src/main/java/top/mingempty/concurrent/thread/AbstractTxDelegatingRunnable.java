package top.mingempty.concurrent.thread;


import cn.hutool.core.lang.UUID;
import top.mingempty.commons.trace.TraceContext;
import top.mingempty.concurrent.model.enums.PriorityEnum;
import top.mingempty.trace.util.TraceAdapterUtil;

import java.util.Map;

/**
 * 基于事务的Runnable
 *
 * @author zzhao
 * @date 2023/8/2 11:26
 */
public abstract class AbstractTxDelegatingRunnable
        extends AbstractDelegatingRunnable {

    public AbstractTxDelegatingRunnable() {
        this(Map.of());
    }

    public AbstractTxDelegatingRunnable(String txId) {
        this(Map.of(), txId);
    }

    public AbstractTxDelegatingRunnable(Map<String, Object> params) {
        this(params, PriorityEnum.D);
    }

    public AbstractTxDelegatingRunnable(Map<String, Object> params, String txId) {
        this(params, PriorityEnum.D, txId);
    }

    public AbstractTxDelegatingRunnable(Map<String, Object> params, PriorityEnum priorityEnum) {
        this(params, priorityEnum, TraceAdapterUtil.gainTraceContext());
    }

    public AbstractTxDelegatingRunnable(Map<String, Object> params, PriorityEnum priorityEnum, String txId) {
        this(params, priorityEnum, TraceAdapterUtil.gainTraceContext(), txId);
    }

    public AbstractTxDelegatingRunnable(Map<String, Object> params, TraceContext traceContext) {
        this(params, PriorityEnum.D, traceContext);
    }

    public AbstractTxDelegatingRunnable(Map<String, Object> params, TraceContext traceContext, String txId) {
        this(params, PriorityEnum.D, traceContext, txId);
    }

    public AbstractTxDelegatingRunnable(PriorityEnum priorityEnum, TraceContext traceContext) {
        this(Map.of(), priorityEnum, traceContext);
    }

    public AbstractTxDelegatingRunnable(PriorityEnum priorityEnum, TraceContext traceContext, String txId) {
        this(Map.of(), priorityEnum, traceContext, txId);
    }

    public AbstractTxDelegatingRunnable(Map<String, Object> params, PriorityEnum priorityEnum, TraceContext traceContext) {
        this(params, priorityEnum, traceContext, UUID.fastUUID().toString(true));
    }

    public AbstractTxDelegatingRunnable(Map<String, Object> params, PriorityEnum priorityEnum, TraceContext traceContext, String txId) {
        super(params, priorityEnum, traceContext);
        this.setTxId(txId);
    }

}
