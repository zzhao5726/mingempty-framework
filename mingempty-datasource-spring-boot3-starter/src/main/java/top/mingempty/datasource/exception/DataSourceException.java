package top.mingempty.datasource.exception;

import top.mingempty.commons.exception.BaseCommonException;

/**
 * 数据源自定义异常
 *
 * @author zzhao
 */
public class DataSourceException extends BaseCommonException {


    public DataSourceException(String errorCode, Throwable cause, Object... message) {
        super(errorCode, cause, message);
    }


    public DataSourceException(String errorCode, Object... message) {
        super(errorCode, message);
    }
}