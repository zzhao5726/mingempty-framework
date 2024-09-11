package top.mingempty.distributed.lock.enums;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 锁实现机制枚举
 *
 * @author zzhao
 */
@Getter
@Schema(title = "锁实现机制枚举")
public enum RealizeEnum {

    /**
     * Redis
     */
    @Schema(title = "redis")
    Redis("RESUBMIT:LOCK:", ":"),

    /**
     * zookeeper
     */
    @Schema(title = "Zookeeper")
    Zookeeper("/RESUBMIT/LOCK/", "/"),

    ;


    /**
     * 分隔符
     */
    @Schema(title = "分隔符")
    private final String separator;

    /**
     * 前缀
     */
    @Schema(title = "前缀")
    private final String keyPrefix;

    RealizeEnum(String separator, String keyPrefix) {
        this.separator = separator;
        this.keyPrefix = keyPrefix;
    }
}
