package top.mingempty.advice;

import cn.hutool.extra.expression.ExpressionException;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.mingempty.commons.exception.BaseException;
import top.mingempty.commons.trace.TraceContext;
import top.mingempty.commons.trace.constants.TraceConstant;
import top.mingempty.domain.base.MeRsp;

import java.util.Objects;
import java.util.Optional;

/**
 * 统一拦截异常
 *
 * @author zzhao
 */
@Slf4j
@RestControllerAdvice
public class GloableExceptionAdvice implements Ordered {

    /**
     * 异常统一处理
     */
    @ExceptionHandler({Exception.class})
    public Object handle(Exception ex) {
        return exceptionAdvice(ex);
    }

    /**
     * 异常拦截器
     *
     * @param exception 当前异常
     * @return 异常封装结果
     */
    public static Object exceptionAdvice(Throwable exception) {
        String message = "系统异常，请联系管理员";
        switch (exception) {
            case BaseException baseException -> {
                log.error("BaseException异常，异常原因:", exception);
                message = baseException.getErrorMessage();
            }
            case MethodArgumentNotValidException methodArgumentNotValidException -> {
                log.error("body参数校验异常，异常原因:", exception);
                BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
                StringBuilder sb = new StringBuilder("校验失败:");
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
                }
                message = sb.toString();
            }
            case ConstraintViolationException constraintViolationException -> {
                log.error("参数校验异常，异常原因:", exception);
                message = exception.getMessage();
            }
            case ExpressionException expressionException -> log.error("表达式解析异常，异常原因:", expressionException);
            case ErrorResponse errorResponse -> {
                log.error("ErrorResponse异常，异常原因:", exception);
                if (Objects.equals(HttpStatus.NOT_FOUND.value(), errorResponse.getStatusCode().value())) {
                    return MeRsp.notFound();
                }
            }
            case ServletException servletException -> {
                Throwable rootCause = servletException.getRootCause();
                if (rootCause != null) {
                    return exceptionAdvice(rootCause);
                }
                log.error("Servlet异常，异常原因:", exception);
            }
            case null, default -> log.error("系统异常，异常原因:", exception);
        }
        MeRsp<Object> failed = MeRsp.failed(message);
        Optional.ofNullable(TraceContext.gainTraceContext())
                .ifPresent(traceContext -> {
                    failed.putParameter(TraceConstant.TRACE_ID, traceContext.getTraceId());
                    failed.putParameter(TraceConstant.SPAN_ID, traceContext.getSpanId());
                });
        return failed;
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
