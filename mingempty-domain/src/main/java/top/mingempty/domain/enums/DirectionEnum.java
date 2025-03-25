package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.Optional;

/**
 * 排序方式枚举
 *
 * @author zzhao
 */
@Slf4j
@Getter
@Schema(title = "排序方式枚举", description = "排序方式枚举")
public enum DirectionEnum implements BaseMetaData<DirectionEnum, String> {

    @Schema(title = "升序", description = "升序")
    ASC("asc", "升序"),

    @Schema(title = "降序", description = "降序")
    DESC("desc", "降序"),
    ;


    DirectionEnum(String itemCode, String itemName) {
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

    /**
     * 是否是升序
     */
    public boolean isAscending() {
        return this.equals(ASC);
    }

    /**
     * 是否是降序
     */
    public boolean isDescending() {
        return this.equals(DESC);
    }

    public static DirectionEnum fromString(String value) {
        try {
            return valueOf(value.toUpperCase(Locale.US));
        } catch (Exception exception) {
            log.warn("Invalid value '{}' for orders given; Has to be either 'desc' or 'asc' (case insensitive)",
                    value, exception);
            return null;
        }
    }

    public static Optional<DirectionEnum> fromOptionalString(String value) {
        try {
            return Optional.ofNullable(fromString(value));
        } catch (IllegalArgumentException var2) {
            return Optional.empty();
        }
    }
}