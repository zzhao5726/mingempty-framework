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
public class TraceFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (RpcContext.getClientAttachment().isConsumerSide()) {
            Result result = null;
            try {
                // 是消费者端
                String traceId = invocation.getAttachment(TraceConstant.TRACE_ID);
                String spanId = invocation.getAttachment(TraceConstant.SPAN_ID);
                TraceAdapterUtil.initTraceContext(
                        invocation.getServiceName() + "#" + invocation.getMethodName(),
                        traceId, spanId, ProtocolEnum.RPC, SpanTypeEnum.NORMAL, invocation.getArguments());
                result = invoker.invoke(invocation);
            } finally {
                TraceAdapterUtil.endTraceContext(result);
            }
            return result;
        }

        if (RpcContext.getClientAttachment().isProviderSide()) {
            // 是消费者端
            invocation.setAttachment(TraceConstant.TRACE_ID, TraceIdGenerator.generateTraceId());
            invocation.setAttachment(TraceConstant.SPAN_ID, TraceIdGenerator.generateSpanId());
        }
        return invoker.invoke(invocation);
    }
}
