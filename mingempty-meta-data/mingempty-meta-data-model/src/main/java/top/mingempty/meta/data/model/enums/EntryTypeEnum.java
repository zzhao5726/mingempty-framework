package top.mingempty.meta.data.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 条目类型
 */
public enum EntryTypeEnum  {

    ONE("1", "普通字典", "t_dict_item"),

    TWO("2", "树形字典", "t_dict_tree"),
    ;


    /**
     * 编码
     */
    @Getter
    private String code;

    /**
     * 名称
     */
    @Getter
    private String name;

    /**
     * 表名
     */
    @Getter
    private String tableName;


    EntryTypeEnum(String code, String name, String tableName) {
        this.code = code;
        this.name = name;
        this.tableName = tableName;
    }

    private static final Map<String, Optional<EntryTypeEnum>> entryTypeEnumOptionalMap = new ConcurrentHashMap<>(2);

    /**
     * 通过编码查找
     *
     * @param code
     * @return
     */
    public static Optional<EntryTypeEnum> entryTypeEnumOptional(String code) {
        return entryTypeEnumOptionalMap.computeIfAbsent(code, s -> Arrays.stream(values())
                .parallel()
                .filter(entryTypeEnum -> entryTypeEnum.getCode().equals(code))
                .findFirst());
    }

    /**
     * 通过编码查找
     *
     * @param code
     * @return
     */
    public static String entryTypeStringOptional(String code) {
        return entryTypeEnumOptional(code).map(EntryTypeEnum::getName).orElse(null);
    }


    /**
     * 生成表名称
     *
     * @param entryCode
     * @return
     */
    public String generateTableName(String entryCode) {
        return this.tableName.concat("_").concat(entryCode);
    }

}
