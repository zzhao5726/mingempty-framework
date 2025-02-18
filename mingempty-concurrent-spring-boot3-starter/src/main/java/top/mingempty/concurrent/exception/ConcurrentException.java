package top.mingempty.concurrent.exception;

import top.mingempty.commons.exception.BaseCommonException;

/**
 * 并发异常
 *
 * @author zzhao
 */
public class ConcurrentException extends BaseCommonException {
    public ConcurrentException(String errorCode, Object... message) {
        super(errorCode, message);
    }

    public ConcurrentException(String errorCode, Throwable cause, Object... message) {
        super(errorCode, cause, message);
    }

    public ConcurrentException(String errorCode, String errorMessage, Object... message) {
        super(errorCode, errorMessage, message);
    }

    public ConcurrentException(String errorCode, String errorMessage, Throwable cause, Object... message) {
        super(errorCode, errorMessage, cause, message);
    }

    public ConcurrentException(String errorCode, String errorMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... message) {
        super(errorCode, errorMessage, cause, enableSuppression, writableStackTrace, message);
    }
}
