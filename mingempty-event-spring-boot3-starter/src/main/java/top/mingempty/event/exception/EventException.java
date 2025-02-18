package top.mingempty.event.exception;

import top.mingempty.commons.exception.BaseCommonException;

/**
 * 事件异常类
 */
public class EventException extends BaseCommonException {
    public EventException(String errorCode, Object... message) {
        super(errorCode, message);
    }

    public EventException(String errorCode, Throwable cause, Object... message) {
        super(errorCode, cause, message);
    }

    public EventException(String errorCode, String errorMessage, Object... message) {
        super(errorCode, errorMessage, message);
    }

    public EventException(String errorCode, String errorMessage, Throwable cause, Object... message) {
        super(errorCode, errorMessage, cause, message);
    }

    public EventException(String errorCode, String errorMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... message) {
        super(errorCode, errorMessage, cause, enableSuppression, writableStackTrace, message);
    }
}
