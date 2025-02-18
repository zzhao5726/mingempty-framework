package top.mingempty.stream.exception;

import top.mingempty.commons.exception.BaseCommonException;

/**
 * 流式异常
 */
public class StreamException extends BaseCommonException {
    public StreamException(String errorCode, Object... message) {
        super(errorCode, message);
    }

    public StreamException(String errorCode, Throwable cause, Object... message) {
        super(errorCode, cause, message);
    }

    public StreamException(String errorCode, String errorMessage, Object... message) {
        super(errorCode, errorMessage, message);
    }

    public StreamException(String errorCode, String errorMessage, Throwable cause, Object... message) {
        super(errorCode, errorMessage, cause, message);
    }

    public StreamException(String errorCode, String errorMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... message) {
        super(errorCode, errorMessage, cause, enableSuppression, writableStackTrace, message);
    }
}
