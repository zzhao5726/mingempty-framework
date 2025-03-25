package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 参数类型枚举
 *
 * @author zzhao
 */
@Getter
@Schema(title = "参数类型枚举")
public enum ParameteTypeEnum implements BaseMetaData<ParameteTypeEnum, Integer> {


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
    private final Integer itemCode;

    /**
     * 描述
     */
    @Schema(title = "描述")
    private final String itemName;

    ParameteTypeEnum(Integer itemCode, String itemName) {
        this.itemCode = itemCode;
        this.itemName = itemName;
    }
}
