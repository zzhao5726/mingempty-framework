package top.mingempty.gateway.filter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.mingempty.commons.trace.TraceContext;
import top.mingempty.commons.trace.constants.TraceConstant;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.gateway.domain.GatewayConstant;
import top.mingempty.gateway.domain.GatewayProperties;
import top.mingempty.trace.util.TraceIdGenerator;

/**
 * 全局拦截器，作用所有的微服务
 * 1. 对请求头中进行清洗
 * 2. 传递链路数据
 *
 * @author zzhao
 */
@Slf4j
@AllArgsConstructor
public class MeGlobakFilter implements GlobalFilter, Ordered {


    private final GatewayProperties gatewayProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 当前仅记录日志，后续可以添加日志队列，来过滤请求慢的接口
        if (log.isDebugEnabled()) {
            log.debug("Method:{} Host:{} Path:{} Cookies:{} Query:{} FormData:{}",
                    exchange.getRequest().getMethod().name(), exchange.getRequest().getURI().getHost(),
                    exchange.getRequest().getURI().getPath(), exchange.getRequest().getCookies(),
                    exchange.getRequest().getQueryParams(), gainFormData(exchange.getFormData()));
        }

        Object attribute = exchange.getAttribute(TraceConstant.TRACE);
        exchange.getAttributes().put(GatewayConstant.START_TIME, System.currentTimeMillis());
        if (attribute != null) {
            TraceContext traceContext = (TraceContext) attribute;
            exchange.getRequest().mutate().headers(httpHeaders -> {
                // 清除内部请求标识
                httpHeaders.remove(GlobalConstant.REQUEST_FROM_INNER);
                // 清除配置的需要清洗的请求头
                gatewayProperties.getClearHeaders().parallelStream().forEach(httpHeaders::remove);

                // 设置链路ID
                httpHeaders.add(TraceConstant.TRACE_ID, TraceIdGenerator.generateTraceId(traceContext));
                httpHeaders.add(TraceConstant.SPAN_ID, TraceIdGenerator.generateSpanId(traceContext));
            });
        }


        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // TODO 预留请求后操作
        }));
    }

    public static String gainFormData(Mono<MultiValueMap<String, String>> mono) {
        // TODO 日志打印问题
        StringBuffer sb = new StringBuffer();
        mono.subscribe(formData -> {
            formData.toSingleValueMap()
                    .forEach((key, value) -> sb.append(key).append(":").append(value).append("\n"));

        });
        return sb.toString();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
