package top.mingempty.meta.data.domain.enums;

import lombok.Getter;
import top.mingempty.domain.enums.BaseMetaData;

/**
 * 条目授权类型
 *
 * @author zzhao
 */
@Getter
public enum AuthorizationTypeEnum implements BaseMetaData<AuthorizationTypeEnum, String> {

    ONE("1", "角色编码"),

    TWO("2", "用户编码"),
    ;


    /**
     * 编码
     */
    private final String itemCode;

    /**
     * 名称
     */
    private final String itemName;


    AuthorizationTypeEnum(String itemCode, String itemName) {
        this.itemCode = itemCode;
        this.itemName = itemName;
    }

}
