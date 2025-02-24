package top.mingempty.meta.data.model.mq;

import lombok.Data;
import top.mingempty.meta.data.model.enums.DictOperationEnum;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 条目变化消息通知实体
 *
 * @author zzhao
 */
@Data
public class EntryChange {

    /**
     * 操作类型
     */
    private DictOperationEnum dictOperationType;

    /**
     * 条目编码集合
     */
    private Collection<String> entryCodes;

    /**
     * 字典项编码
     */
    private Map<String, List<String>> itemCodes;
}
