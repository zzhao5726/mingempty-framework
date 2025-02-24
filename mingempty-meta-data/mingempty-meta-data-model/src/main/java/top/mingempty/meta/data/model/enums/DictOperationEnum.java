package top.mingempty.meta.data.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 字典操作类型枚举
 *
 * @author zzhao
 */
@Getter
public enum DictOperationEnum {

    code_01("01", "条目-新增"),
    code_02("02", "条目-修改"),
    code_03("03", "条目-状态变更"),
    code_04("04", "条目权限-新增"),
    code_05("05", "条目权限-删除"),
    code_06("06", "扩展字段-新增"),
    code_07("07", "扩展字段-修改"),
    code_08("08", "字典项-新增"),
    code_09("09", "字典项-修改"),
    code_10("10", "字典项-状态变更"),
    code_11("11", "导入-excel"),
    code_12("12", "导入-zip"),
    ;


    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;


    DictOperationEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private static final Map<String, Optional<DictOperationEnum>> DICT_OPERATION_ENUM_OPTIONAL_MAP
            = new ConcurrentHashMap<>(12) {{
        Arrays.stream(DictOperationEnum.values())
                .parallel()
                .forEach(dictOperationTypeEnum
                        -> put(dictOperationTypeEnum.getCode(), Optional.of(dictOperationTypeEnum)));
    }};

    /**
     * 通过编码查找
     *
     * @param code
     * @return
     */
    public static Optional<DictOperationEnum> operationTypeEnumOptional(String code) {
        return DICT_OPERATION_ENUM_OPTIONAL_MAP.computeIfAbsent(code, s -> Arrays.stream(values())
                .parallel()
                .filter(dictOperationEnum -> dictOperationEnum.getCode().equals(code))
                .findFirst());
    }

    /**
     * 通过编码查找
     *
     * @param code
     * @return
     */
    public static String operationTypeStringOptional(String code) {
        return operationTypeEnumOptional(code).map(DictOperationEnum::getName).orElse(null);
    }

}
