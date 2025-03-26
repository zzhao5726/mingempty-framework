package top.mingempty.meta.data.domain.enums;

import lombok.Getter;
import top.mingempty.domain.enums.BaseMetaData;

/**
 * 字典操作类型枚举
 *
 * @author zzhao
 */
@Getter
public enum DictOperationEnum implements BaseMetaData<DictOperationEnum, String> {

    code_1("1", "条目基础信息新增或修改"),
    code_2("2", "字典项新增或修改"),
    code_3("3", "excel导入"),
    code_4("4", "内部自定义格式导入"),
    code_5("5", "版本还原"),
    ;


    /**
     * 编码
     */
    private final String itemCode;

    /**
     * 名称
     */
    private final String itemName;


    DictOperationEnum(String itemCode, String itemName) {
        this.itemCode = itemCode;
        this.itemName = itemName;
    }

}
