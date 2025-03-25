package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 是否-01枚举
 *
 * @author zzhao
 */
@Getter
@Schema(title = "是否-01枚举", description = "是否-01枚举")
public enum ZeroOrOneEnum implements BaseMetaData<ZeroOrOneEnum, String> {

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
}