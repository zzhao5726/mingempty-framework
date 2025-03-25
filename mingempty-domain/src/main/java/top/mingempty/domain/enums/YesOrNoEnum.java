package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Optional;

/**
 * 是否-YN枚举
 *
 * @author zzhao
 */
@Getter
@Schema(title = "是否-YN枚举", description = "是否-YN枚举")
public enum YesOrNoEnum implements BaseMetaData<YesOrNoEnum, String> {

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

    /**
     * 将字典项编码转换为小写
     *
     * @return
     */
    @Schema(title = "将字典项编码转换为小写")
    public String toLowerCase() {
        return itemCode.toLowerCase();
    }


    /**
     * 根据小写字典项编码获取枚举
     *
     * @param itemCode 字典项编码
     * @return 枚举
     */
    public static YesOrNoEnum findOneWithLowerCase(String itemCode) {
        return EnumHelper.INSTANCE.findOne(YesOrNoEnum.class, itemCode.toUpperCase());
    }

    /**
     * 根据小写字典项编码获取枚举
     *
     * @param itemCode 字典项编码
     * @return 枚举
     */
    public static Optional<YesOrNoEnum> findOptionalWithLowerCase(String itemCode) {
        return EnumHelper.INSTANCE.findOptional(YesOrNoEnum.class, itemCode.toUpperCase());
    }
}