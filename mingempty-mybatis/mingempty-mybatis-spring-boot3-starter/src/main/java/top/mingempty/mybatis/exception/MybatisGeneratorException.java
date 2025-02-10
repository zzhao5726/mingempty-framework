package top.mingempty.mybatis.exception;

public class MybatisGeneratorException extends MybatisException {


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

