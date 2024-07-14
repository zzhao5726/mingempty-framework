package top.mingempty.trace.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import top.mingempty.commons.trace.constants.TraceConstant;
import top.mingempty.commons.trace.enums.ProtocolEnum;
import top.mingempty.commons.trace.enums.SpanTypeEnum;
import top.mingempty.trace.util.TraceAdapterUtil;

/**
 * 链路日志过滤器
 *
 * @author zzhao
 */
@Slf4j
public class TraceFluxFilter implements WebFilter, Ordered {
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
                TraceAdapterUtil.initTraceContext(requestUrl, traceId, spanId, ProtocolEnum.HTTP, SpanTypeEnum.NORMAL,
                        "");
            } catch (Exception e) {
                log.debug("链路初始化异常", e);
            }
            return chain.filter(exchange);
        } finally {
            // TODO 响应参数待定
            TraceAdapterUtil.endTraceContext();
        }
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}