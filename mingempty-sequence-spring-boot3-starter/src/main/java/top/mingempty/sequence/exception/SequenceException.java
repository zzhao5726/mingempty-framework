package top.mingempty.sequence.exception;


import top.mingempty.commons.exception.BaseCommonException;

/**
 * 获取序号异常
 *
 * @author zzhao
 */
public class SequenceException extends BaseCommonException {

    public SequenceException(String errorCode, Object... message) {
        super(errorCode,message);
    }

    public SequenceException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
