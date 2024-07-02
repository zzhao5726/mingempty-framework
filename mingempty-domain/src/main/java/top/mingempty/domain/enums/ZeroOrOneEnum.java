package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 是否-01枚举
 *
 * @author zzhao
 */
@Getter
@Schema(title = "是否-01枚举", description = "是否-01枚举")
public enum ZeroOrOneEnum {

    @Schema(title = "否", description = "否")
    ZERO("0", "否"),

    @Schema(title = "是", description = "是")
    ONE("1", "是"),

    ;


    ZeroOrOneEnum(String itemCode, String itemName) {
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

    private final static Map<String, ZeroOrOneEnum> ZERO_OR_ONE_ENUM_OPTIONAL_MAP =
            Arrays.stream(ZeroOrOneEnum.values())
                    .parallel()
                    .collect(Collectors.toMap(ZeroOrOneEnum::getItemCode, Function.identity()));

    /**
     * 通过编码查找
     */
    public static Optional<ZeroOrOneEnum> find(String itemCode) {
        return Optional.ofNullable(ZERO_OR_ONE_ENUM_OPTIONAL_MAP.get(itemCode));
    }
}