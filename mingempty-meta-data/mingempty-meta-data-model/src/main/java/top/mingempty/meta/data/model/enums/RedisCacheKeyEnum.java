package top.mingempty.meta.data.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * redis key模板的枚举
 */
@Getter
@Schema(title = "redis key模板的枚举")
public enum RedisCacheKeyEnum {


    CHANGE_LOCK("meta:data:entrty:%s:change:lock", "条目变化时加锁", "条目编码"),

    CHANGE_VERSION("meta:data:entrty:%s:change:version", "条目变更时，获取变更版本", "条目编码"),

    ;


    /**
     * key的模板
     */
    @Schema(title = "key的模板")
    private String key;

    /**
     * redis的key类型
     */
    @Schema(title = "redis的key类型")
    private String type;

    /**
     * 描述
     */
    @Schema(title = "描述")
    private String desc;

    /**
     * 参数列表
     */
    @Schema(title = "参数列表")
    private Object[] arguments;

    RedisCacheKeyEnum(String key, String desc, Object... arguments) {
        this.key = key;
        this.desc = desc;
        this.arguments = arguments;
    }

    /**
     * 获取当前redis的key
     *
     * @param arguments
     * @return
     */
    public String gainKey(Object... arguments) {
        return this.getKey().formatted(arguments);
    }


}
