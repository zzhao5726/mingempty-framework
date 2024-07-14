package top.mingempty.trace.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import top.mingempty.commons.trace.constants.TraceConstant;
import top.mingempty.commons.trace.enums.ProtocolEnum;
import top.mingempty.commons.trace.enums.SpanTypeEnum;
import top.mingempty.trace.util.TraceAdapterUtil;

import java.io.IOException;

/**
 * 链路日志过滤器
 *
 * @author zzhao
 */
@Slf4j
@WebFilter
public class TraceMvcFilter implements Filter, Ordered {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            try {
                //初始化TraceContext
                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                String requestUrl = httpServletRequest.getRequestURI();
                String traceId = httpServletRequest.getHeader(TraceConstant.TRACE_ID);
                String spanId = httpServletRequest.getHeader(TraceConstant.SPAN_ID);
                // TODO 请求参数待定
                TraceAdapterUtil.initTraceContext(requestUrl, traceId, spanId, ProtocolEnum.HTTP, SpanTypeEnum.NORMAL,
                        null);
            } catch (Exception e) {
                log.debug("链路初始化异常", e);
            }
            //放行
            filterChain.doFilter(servletRequest, servletResponse);
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
