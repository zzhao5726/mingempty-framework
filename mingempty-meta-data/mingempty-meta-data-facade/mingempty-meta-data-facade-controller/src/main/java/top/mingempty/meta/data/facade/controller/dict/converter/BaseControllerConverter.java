package top.mingempty.meta.data.facade.controller.dict.converter;

import top.mingempty.domain.enums.BaseMetaData;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.enums.AuthorizationTypeEnum;
import top.mingempty.meta.data.domain.enums.EntryTypeEnum;

/**
 * 基础控制器转换器
 *
 * @author zzhao
 */
public class BaseControllerConverter {


    public static EntryTypeEnum entryType(String itemCode) {
        return BaseMetaData.EnumHelper.INSTANCE
                .findOptional(EntryTypeEnum.class, itemCode)
                .orElse(null);
    }


    public static EntryTypeEnum entryTypeWithDefault(String itemCode) {
        return BaseMetaData.EnumHelper.INSTANCE
                .findOptional(EntryTypeEnum.class, itemCode)
                .orElse(EntryTypeEnum.ONE);
    }


    public static ZeroOrOneEnum zeroOrOneEnum(String itemCode) {
        return BaseMetaData.EnumHelper.INSTANCE
                .findOptional(ZeroOrOneEnum.class, itemCode)
                .orElse(null);
    }


    public static ZeroOrOneEnum zeroOrOneEnumWithDefault(String itemCode) {
        return BaseMetaData.EnumHelper.INSTANCE
                .findOptional(ZeroOrOneEnum.class, itemCode)
                .orElse(ZeroOrOneEnum.ZERO);
    }


    public static AuthorizationTypeEnum authorizationType(String itemCode) {
        return BaseMetaData.EnumHelper.INSTANCE
                .findOptional(AuthorizationTypeEnum.class, itemCode)
                .orElse(null);
    }


    public static AuthorizationTypeEnum authorizationTypeWithDefault(String itemCode) {
        return BaseMetaData.EnumHelper.INSTANCE
                .findOptional(AuthorizationTypeEnum.class, itemCode)
                .orElse(AuthorizationTypeEnum.ONE);
    }
}
