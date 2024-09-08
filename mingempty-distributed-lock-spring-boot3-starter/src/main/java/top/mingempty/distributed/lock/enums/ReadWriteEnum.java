package top.mingempty.distributed.lock.enums;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 读写枚举
 *
 * @author zzhao
 */
@Schema(title = "读写枚举")
public enum ReadWriteEnum {

    /**
     * 无
     */
    @Schema(title = "无")
    None,

    /**
     * 读
     */
    @Schema(title = "读")
    Read,

    /**
     * 写
     */
    @Schema(title = "写")
    Write,
    ;
}
