package top.mingempty.meta.data.model.exception;


import top.mingempty.commons.exception.BaseBizException;

/**
 * 公共码值异常
 *
 * @author zzhao
 */
public class MetaDataException extends BaseBizException {
    public MetaDataException(String errorCode, Object... message) {
        super(errorCode, message);
    }

    public MetaDataException(String errorCode, Throwable cause, Object... message) {
        super(errorCode, cause, message);
    }

    public MetaDataException(String errorCode, String errorMessage, Object... message) {
        super(errorCode, errorMessage, message);
    }

    public MetaDataException(String errorCode, String errorMessage, Throwable cause, Object... message) {
        super(errorCode, errorMessage, cause, message);
    }

    public MetaDataException(String errorCode, String errorMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... message) {
        super(errorCode, errorMessage, cause, enableSuppression, writableStackTrace, message);
    }
}
