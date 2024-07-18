package top.mingempty.cache.redis.excetion;

import top.mingempty.commons.exception.BaseCommonException;

/**
 * 无效Redis连接url异常
 *
 * @author zzhao
 */
public class RedisUrlSyntaxException extends BaseCommonException {


    private final String url;

    public RedisUrlSyntaxException(String url, Exception cause) {
        super("0000000002", cause, url);
        this.url = url;
    }

    public RedisUrlSyntaxException(String url) {
        super("0000000002", url);
        this.url = url;
    }

    String getUrl() {
        return this.url;
    }

}
