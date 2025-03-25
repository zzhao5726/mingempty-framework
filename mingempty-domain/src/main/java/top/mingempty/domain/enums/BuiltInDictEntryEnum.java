package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 内置字典条目枚举
 *
 * @author zzhao
 */
@Getter
@Schema(title = "内置字典条目枚举", description = "内置字典条目枚举")
public enum BuiltInDictEntryEnum implements BaseMetaData<BuiltInDictEntryEnum, String> {

    @Schema(title = "启用状态", description = "启用状态")
    ENABLED_OR_NOT("enabled_or_not", "启用状态"),

    @Schema(title = "是否-01", description = "是否-01")
    ZERO_OR_ONE("zero_or_one", "是否-01"),

    @Schema(title = "是否-YN", description = "是否-YN")
    YES_OR_NO("yes_or_no", "是否-YN"),

    @Schema(title = "字典标签", description = "字典标签")
    DICT_TAG("dict_tag", "字典标签"),

    @Schema(title = "业务类别", description = "业务类别")
    SERVICE_CATEGORY("service_category", "业务类别"),

    @Schema(title = "业务参数", description = "业务参数")
    SERVICE_PARAMETER("service_parameter", "业务参数"),
    ;

    /**
     * 条目编码
     */
    @Schema(title = "条目编码", description = "条目编码")
    private final String entryCode;

    /**
     * 条目名称
     */
    @Schema(title = "条目名称", description = "条目名称")
    private final String entryName;

    BuiltInDictEntryEnum(String entryCode, String entryName) {
        this.entryCode = entryCode;
        this.entryName = entryName;
    }


    @Override
    public String getItemCode() {
        return getEntryCode();
    }

    @Override
    public String getItemName() {
        return getEntryName();
    }

}
