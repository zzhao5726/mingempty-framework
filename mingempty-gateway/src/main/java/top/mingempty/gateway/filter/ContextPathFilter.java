package top.mingempty.gateway.filter;

import lombok.AllArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import top.mingempty.domain.MeGloableProperty;


/**
 * 实际请求路径重写
 * ContextPath配置后路径无法识别
 *
 * @author zzhao
 */
@AllArgsConstructor
public class ContextPathFilter implements WebFilter, Ordered {

    private final MeGloableProperty meGloableProperty;

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
        ServerHttpRequest request = exchange.getRequest();

        String path = request.getURI().getRawPath();
        if (!path.startsWith(meGloableProperty.getBasePath())) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.BAD_GATEWAY);
            DataBuffer buffer = response
                    .bufferFactory()
                    .wrap(HttpStatus.BAD_GATEWAY.getReasonPhrase().getBytes());
            return response.writeWith(Mono.just(buffer));
        }

        return chain.filter(exchange.mutate()
                .request(request.mutate()
                        .path(path.replaceFirst(meGloableProperty.getBasePath(), ""))
                        .build())
                .build());
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