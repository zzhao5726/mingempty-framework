package top.mingempty.trace.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import top.mingempty.trace.constants.ProtocolEnum;
import top.mingempty.trace.constants.SpanTypeEnum;
import top.mingempty.trace.constants.TraceConstant;
import top.mingempty.trace.util.TraceAdapterUtil;

/**
 * 链路日志过滤器
 *
 * @author zzhao
 */
@Slf4j
public class TraceFluxFilter implements WebFilter {
    /**
     * Process the Web request and (optionally) delegate to the next
     * {@code WebFilter} through the given {@link WebFilterChain}.
     *
     * @param exchange the current server exchange
     * @param chain    provides a way to delegate to the next filter
     * @return {@code Mono<Void>} to indicate when request processing is complete
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            try {
                //初始化TraceContext
                ServerHttpRequest serverHttpRequest = exchange.getRequest();
                HttpHeaders headers = serverHttpRequest.getHeaders();
                RequestPath requestPath = serverHttpRequest.getPath();
                String traceId = headers.getFirst(TraceConstant.TRACE_ID);
                String spanId = headers.getFirst(TraceConstant.SPAN_ID);
                String requestUrl = requestPath.value();
                // TODO 请求参数待定
                TraceAdapterUtil.initTraceContext(requestUrl, traceId, spanId, ProtocolEnum.HTTP, SpanTypeEnum.NORMAL, "");
            } catch (Exception e) {
                log.debug("链路初始化异常", e);
            }
            return chain.filter(exchange);
        } finally {
            // TODO 响应参数待定
            TraceAdapterUtil.clearTraceContext();
        }
    }
}