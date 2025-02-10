package top.mingempty.mybatis.generator.exception;

import top.mingempty.commons.exception.BaseCommonException;

/**
 * mybatis 逆向生成异常
 */
public class MybatisGeneratorException extends BaseCommonException {


    public MybatisGeneratorException(String errorCode, Object... message) {
        super(errorCode, message);
    }

    public MybatisGeneratorException(String errorCode, Throwable cause, Object... message) {
        super(errorCode, cause, message);
    }

    public MybatisGeneratorException(String errorCode, String errorMessage, Object... message) {
        super(errorCode, errorMessage, message);
    }

    public MybatisGeneratorException(String errorCode, String errorMessage, Throwable cause, Object... message) {
        super(errorCode, errorMessage, cause, message);
    }

    public MybatisGeneratorException(String errorCode, String errorMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... message) {
        super(errorCode, errorMessage, cause, enableSuppression, writableStackTrace, message);
    }
}

