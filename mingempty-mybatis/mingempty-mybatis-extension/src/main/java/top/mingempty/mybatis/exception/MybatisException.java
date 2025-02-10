package top.mingempty.mybatis.exception;

import top.mingempty.commons.exception.BaseCommonException;

public class MybatisException extends BaseCommonException {

    public MybatisException(String errorCode, Object... message) {
        super(errorCode, message);
    }

    public MybatisException(String errorCode, Throwable cause, Object... message) {
        super(errorCode, cause, message);
    }

    public MybatisException(String errorCode, String errorMessage, Object... message) {
        super(errorCode, errorMessage, message);
    }

    public MybatisException(String errorCode, String errorMessage, Throwable cause, Object... message) {
        super(errorCode, errorMessage, cause, message);
    }

    public MybatisException(String errorCode, String errorMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... message) {
        super(errorCode, errorMessage, cause, enableSuppression, writableStackTrace, message);
    }
}

