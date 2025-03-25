package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 是否启用枚举
 *
 * @author zzhao
 */
@Getter
@Schema(title = "是否启用枚举", description = "是否启用枚举")
public enum EnabledOrNotEnum implements BaseMetaData<EnabledOrNotEnum, String> {

    @Schema(title = "未启用", description = "未启用")
    ZERO("0", "未启用"),

    @Schema(title = "启用", description = "启用")
    ONE("1", "启用"),

    ;


    EnabledOrNotEnum(String itemCode, String itemName) {
        this.itemCode = itemCode;
        this.itemName = itemName;
    }


    /**
     * 字典项编码
     */
    @Schema(title = "字典项编码")
    private final String itemCode;

    /**
     * 字典项名称
     */
    @Schema(title = "字典项名称")
    private final String itemName;
}