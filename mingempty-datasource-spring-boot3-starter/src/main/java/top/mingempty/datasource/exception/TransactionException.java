package top.mingempty.datasource.exception;

import top.mingempty.commons.exception.BaseCommonException;

/**
 * 事务异常
 *
 */
public class TransactionException extends BaseCommonException {

    public TransactionException(String errorCode, Object... message) {
        super(errorCode, message);
    }

    public TransactionException(String errorCode, Throwable cause, Object... message) {
        super(errorCode, cause, message);
    }


}