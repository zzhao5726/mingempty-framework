package top.mingempty.meta.data.domain.enums;

import lombok.Getter;
import top.mingempty.domain.enums.BaseMetaData;

/**
 * 条目类型
 */
@Getter
public enum EntryTypeEnum implements BaseMetaData<EntryTypeEnum, String> {

    ONE("1", "普通字典", "t_dict_item"),

    TWO("2", "树形字典", "t_dict_tree"),
    ;


    /**
     * 编码
     */
    private final String itemCode;

    /**
     * 名称
     */
    private final String itemName;

    /**
     * 表名
     */
    @Getter
    private final String tableName;


    EntryTypeEnum(String itemCode, String itemName, String tableName) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.tableName = tableName;
    }

}
