package top.mingempty.distributed.lock.enums;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 锁实现机制枚举
 *
 * @author zzhao
 */
@Schema(title = "锁实现机制枚举")
public enum RealizeEnum {


    /**
     * Redis
     */
    @Schema(title = "Redis")
    Redis,

    /**
     * ZooKeeper
     */
    @Schema(title = "ZooKeeper")
    ZooKeeper,
}
