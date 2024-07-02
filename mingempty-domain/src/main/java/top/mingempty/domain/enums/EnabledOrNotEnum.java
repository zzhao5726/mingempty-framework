package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 是否启用枚举
 *
 * @author zzhao
 */
@Getter
@Schema(title = "是否启用枚举", description = "是否启用枚举")
public enum EnabledOrNotEnum {

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



    private final static Map<String, EnabledOrNotEnum> ENABLED_OR_NOT_ENUM_OPTIONAL_MAP =
            Arrays.stream(EnabledOrNotEnum.values())
                    .parallel()
                    .collect(Collectors.toMap(EnabledOrNotEnum::getItemCode, Function.identity()));


    /**
     * 通过编码查找
     */
    public static Optional<EnabledOrNotEnum> find(String itemCode) {
        return Optional.ofNullable(ENABLED_OR_NOT_ENUM_OPTIONAL_MAP.get(itemCode));
    }
}