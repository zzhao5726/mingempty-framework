package top.mingempty.cache.redis.excetion;


import top.mingempty.commons.exception.BaseCommonException;

/**
 * 缓存的异常类
 *
 * @author zzhao
 * @date 2023/3/10 10:39
 */
public class RedisCacheException extends BaseCommonException {


    public RedisCacheException(String errorCode, Object... message) {
        super(errorCode, message);
    }

    public RedisCacheException(String errorCode, Throwable cause, Object... message) {
        super(errorCode, cause, message);
    }

    public RedisCacheException(String errorCode, String errorMessage, Object... message) {
        super(errorCode, errorMessage, message);
    }

    public RedisCacheException(String errorCode, String errorMessage, Throwable cause, Object... message) {
        super(errorCode, errorMessage, cause, message);
    }

    public RedisCacheException(String errorCode, String errorMessage, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... message) {
        super(errorCode, errorMessage, cause, enableSuppression, writableStackTrace, message);
    }
}
