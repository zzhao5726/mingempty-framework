package top.mingempty.distributed.lock.enums;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 锁层级枚举
 *
 * @author zzhao
 */
@Schema(title = "锁层级枚举")
public enum TierEnum {

    /**
     * 接口层级
     */
    @Schema(title = "接口层级")
    Interface,

    /**
     * 业务层级
     */
    @Schema(title = "业务层级")
    Business,
    ;
}
