package top.mingempty.commons.exception;

import cn.hutool.core.map.MapUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import top.mingempty.commons.trace.TraceContext;
import top.mingempty.commons.trace.constants.TraceConstant;
import top.mingempty.commons.util.JacksonUtil;

import java.io.Serial;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mingempty cloud基础异常类
 *
 * @author zzhao
 */
@Getter
@Schema(title = "mingempty cloud基础异常类")
public class BaseException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = -6853310712844466349L;

    /**
     * 异常编码
     */
    @Schema(title = "异常编码")
    private String errorCode = "-1";

    /**
     * 异常增强特殊属性
     */
    @Schema(title = "异常增强特殊属性")
    private final Map<String, Object> errorProperties = new ConcurrentHashMap<>(2);


    protected BaseException(String errorCode, Object... message) {
        super(ErrorFormatter.getInstance().format(errorCode, message));
        this.errorCode = errorCode;
        Optional.ofNullable(TraceContext.gainTraceContext())
                .ifPresent(traceContext -> {
                    errorProperties.put(TraceConstant.TRACE_ID, traceContext.getTraceId());
                    errorProperties.put(TraceConstant.SPAN_ID, traceContext.getSpanId());
                });
    }


    protected BaseException(String errorCode, Throwable cause, Object... message) {
        super(ErrorFormatter.getInstance().format(errorCode, message), cause);
        this.errorCode = errorCode;
        Optional.ofNullable(TraceContext.gainTraceContext())
                .ifPresent(traceContext -> {
                    errorProperties.put(TraceConstant.TRACE_ID, traceContext.getTraceId());
                    errorProperties.put(TraceConstant.SPAN_ID, traceContext.getSpanId());
                });

    }

    protected BaseException(String errorCode, String errorMessage, Object... message) {
        super(MessageFormat.format(errorMessage, message));
        this.errorCode = errorCode;
        Optional.ofNullable(TraceContext.gainTraceContext())
                .ifPresent(traceContext -> {
                    errorProperties.put(TraceConstant.TRACE_ID, traceContext.getTraceId());
                    errorProperties.put(TraceConstant.SPAN_ID, traceContext.getSpanId());
                });
    }


    protected BaseException(String errorCode, String errorMessage, Throwable cause, Object... message) {
        super(MessageFormat.format(errorMessage, message), cause);
        this.errorCode = errorCode;
        Optional.ofNullable(TraceContext.gainTraceContext())
                .ifPresent(traceContext -> {
                    errorProperties.put(TraceConstant.TRACE_ID, traceContext.getTraceId());
                    errorProperties.put(TraceConstant.SPAN_ID, traceContext.getSpanId());
                });
    }


    protected BaseException(String errorCode, String errorMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... message) {
        super(MessageFormat.format(errorMessage, message), cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        Optional.ofNullable(TraceContext.gainTraceContext())
                .ifPresent(traceContext -> {
                    errorProperties.put(TraceConstant.TRACE_ID, traceContext.getTraceId());
                    errorProperties.put(TraceConstant.SPAN_ID, traceContext.getSpanId());
                });
    }

    public void putErrorProperty(String name, Object prop) {
        Optional.ofNullable(name)
                .ifPresent(key -> this.errorProperties.put(key, prop));
    }

    public void putErrorProperty(Map<String, Object> errorProperties) {
        Optional.of(errorProperties)
                .orElse(Collections.emptyMap())
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> this.putErrorProperty(entry.getKey(), entry.getValue()));


    }

    /**
     * 获取属性
     *
     * @param name
     * @return
     */
    public Object getErrorProperty(String name) {
        return Optional.ofNullable(name)
                .map(this.errorProperties::get)
                .orElse(null);
    }

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    public String getErrorMessage() {
        return Optional.ofNullable(super.getMessage())
                .orElse("");
    }

    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("[" + this.errorCode + "]");
        Optional.ofNullable(super.getMessage())
                .ifPresent(message -> sb.append("[").append(message).append("]"));
        if (MapUtil.isNotEmpty(this.errorProperties)) {
            sb.append("[");
            sb.append(JacksonUtil.toStr(this.errorProperties));
            sb.append("]");
        }
        return sb.toString();
    }
}
