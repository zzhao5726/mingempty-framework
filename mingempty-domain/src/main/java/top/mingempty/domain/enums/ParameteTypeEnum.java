package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 参数类型枚举
 *
 * @author zzhao
 */
@Getter
@Schema(title = "参数类型枚举")
public enum ParameteTypeEnum {


    /**
     * 请求参数
     */
    @Schema(title = "请求参数")
    REQUEST(1, "请求参数"),

    /**
     * 响应参数
     */
    @Schema(title = "响应参数")
    RESPONSE(2, "响应参数");


    /**
     * 编码
     */
    @Schema(title = "编码")
    private final int code;

    /**
     * 描述
     */
    @Schema(title = "描述")
    private final String desc;

    ParameteTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    private final static Map<Integer, ParameteTypeEnum> PARAMETE_TYPE_ENUM_OPTIONAL_MAP =
            Arrays.stream(ParameteTypeEnum.values())
                    .parallel()
                    .collect(Collectors.toMap(ParameteTypeEnum::getCode, Function.identity()));


    /**
     * 通过编码查找
     */
    public static Optional<ParameteTypeEnum> find(Integer code) {
        return Optional.ofNullable(PARAMETE_TYPE_ENUM_OPTIONAL_MAP.get(code));
    }
}
