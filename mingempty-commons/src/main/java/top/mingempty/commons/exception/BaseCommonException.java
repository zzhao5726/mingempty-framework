package top.mingempty.commons.exception;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * mingempty cloud组件异常基类
 *
 * @author zzhao
 */
@Schema(title = "mingempty cloud组件异常基类")
public class BaseCommonException extends BaseException {

    public BaseCommonException(String errorCode, Object... message) {
        super(errorCode, message);
    }

    public BaseCommonException(String errorCode, Throwable cause, Object... message) {
        super(errorCode, cause, message);
    }

    public BaseCommonException(String errorCode, String errorMessage, Object... message) {
        super(errorCode, errorMessage, message);
    }

    public BaseCommonException(String errorCode, String errorMessage, Throwable cause, Object... message) {
        super(errorCode, errorMessage, cause, message);
    }

    public BaseCommonException(String errorCode, String errorMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... message) {
        super(errorCode, errorMessage, cause, enableSuppression, writableStackTrace, message);
    }
}
