package top.mingempty.distributed.lock.exception;


import top.mingempty.commons.exception.BaseCommonException;
import top.mingempty.domain.enums.DefaultResultEnum;

/**
 * 加锁失败
 *
 * @author zzhao
 */
public class ResubmitLockException extends BaseCommonException {

    public ResubmitLockException() {
        super(DefaultResultEnum.RESUBMIT.getCode(), DefaultResultEnum.RESUBMIT.getMessage());
    }

    public ResubmitLockException(Throwable cause) {
        super(DefaultResultEnum.RESUBMIT.getCode(), cause, DefaultResultEnum.RESUBMIT.getMessage());
    }

    public ResubmitLockException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
