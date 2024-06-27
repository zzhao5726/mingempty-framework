package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 是否-YN枚举
 *
 * @author zzhao
 */
@Getter
@Schema(title = "是否-YN枚举", description = "是否-YN枚举")
public enum YesOrNoEnum {

    @Schema(title = "否", description = "否")
    YES("Y", "否"),

    @Schema(title = "是", description = "是")
    NO("N", "是"),

    ;


    YesOrNoEnum(String itemCode, String itemName) {
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

    private final static Map<String, YesOrNoEnum> YES_OR_NO_ENUM_OPTIONAL_MAP =
            new ConcurrentHashMap<>(YesOrNoEnum.values().length) {
                {
                    Arrays.stream(YesOrNoEnum.values())
                            .parallel()
                            .forEach(yesOrNoEnum
                                    -> YES_OR_NO_ENUM_OPTIONAL_MAP.put(yesOrNoEnum.getItemCode(), yesOrNoEnum));
                }
            };

    /**
     * 通过编码查找
     */
    public static Optional<YesOrNoEnum> find(String itemCode) {
        return Optional.ofNullable(YES_OR_NO_ENUM_OPTIONAL_MAP.get(itemCode));
    }
}