package top.mingempty.distributed.lock.exception;

import top.mingempty.commons.exception.BaseCommonException;
import top.mingempty.domain.enums.DefaultResultEnum;

/**
 * 解锁失败
 *
 * @author zzhao
 */
public class ResubmitUnLockException extends BaseCommonException {
    public ResubmitUnLockException() {
        super(DefaultResultEnum.RESUBMIT.getCode(), DefaultResultEnum.RESUBMIT.getMessage());
    }

    public ResubmitUnLockException(Throwable cause) {
        super(DefaultResultEnum.RESUBMIT.getCode(), cause, DefaultResultEnum.RESUBMIT.getMessage());
    }

    public ResubmitUnLockException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
