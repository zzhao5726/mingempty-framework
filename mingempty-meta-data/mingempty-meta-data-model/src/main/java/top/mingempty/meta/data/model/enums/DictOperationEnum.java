package top.mingempty.meta.data.model.enums;

import lombok.Getter;
import top.mingempty.domain.enums.BaseMetaData;

/**
 * 字典操作类型枚举
 *
 * @author zzhao
 */
@Getter
public enum DictOperationEnum implements BaseMetaData<DictOperationEnum, String> {

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
