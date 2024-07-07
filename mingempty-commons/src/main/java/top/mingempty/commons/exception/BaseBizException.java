package top.mingempty.commons.exception;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * mingempty cloud业务异常基类
 *
 * @author zzhao
 */
@Schema(title = "mingempty cloud业务异常基类")
public class BaseBizException extends BaseException {

    public BaseBizException(String errorCode, Object... message) {
        super(errorCode, message);
    }

    public BaseBizException(String errorCode, Throwable cause, Object... message) {
        super(errorCode, cause, message);
    }

    public BaseBizException(String errorCode, String errorMessage, Object... message) {
        super(errorCode, errorMessage, message);
    }

    public BaseBizException(String errorCode, String errorMessage, Throwable cause, Object... message) {
        super(errorCode, errorMessage, cause, message);
    }

    public BaseBizException(String errorCode, String errorMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... message) {
        super(errorCode, errorMessage, cause, enableSuppression, writableStackTrace, message);
    }
}
