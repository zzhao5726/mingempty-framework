package top.mingempty.dubbo.filter;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import top.mingempty.commons.trace.constants.TraceConstant;
import top.mingempty.commons.trace.enums.ProtocolEnum;
import top.mingempty.commons.trace.enums.SpanTypeEnum;
import top.mingempty.trace.util.TraceAdapterUtil;
import top.mingempty.trace.util.TraceIdGenerator;

/**
 * 链路追踪过滤器
 */
@Activate(group = {CommonConstants.PROVIDER, CommonConstants.CONSUMER})
public class DubboTraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Result result = null;
        boolean endTraceContext = false;
        try {
            if (RpcContext.getClientAttachment().isConsumerSide()) {
                // 是消费者端
                String traceId = invocation.getAttachment(TraceConstant.TRACE_ID);
                String spanId = invocation.getAttachment(TraceConstant.SPAN_ID);
                endTraceContext = true;
                TraceAdapterUtil.initTraceContext(
                        invocation.getServiceName() + "#" + invocation.getMethodName(),
                        traceId, spanId, ProtocolEnum.RPC, SpanTypeEnum.NORMAL, invocation.getArguments());
            }
            if (RpcContext.getClientAttachment().isProviderSide()) {
                // 是提供者端
                if (!TraceAdapterUtil.initialized()) {
                    //进行初始化
                    TraceAdapterUtil.initTraceContext(
                            "dubbo#" + invocation.getServiceName() + "#" + invocation.getMethodName(),
                            TraceIdGenerator.generateTraceId(), TraceIdGenerator.generateSpanId(),
                            ProtocolEnum.RPC, SpanTypeEnum.NORMAL,null);
                }
                invocation.setAttachment(TraceConstant.TRACE_ID, TraceIdGenerator.generateTraceId());
                invocation.setAttachment(TraceConstant.SPAN_ID, TraceIdGenerator.generateSpanId());
            }
            result = invoker.invoke(invocation);
        } finally {
            if (endTraceContext) {
                if (result != null) {
                    TraceAdapterUtil.endTraceContext(result.getValue());
                } else {
                    TraceAdapterUtil.endTraceContext();
                }
            }
        }
        return result;

    }
}
