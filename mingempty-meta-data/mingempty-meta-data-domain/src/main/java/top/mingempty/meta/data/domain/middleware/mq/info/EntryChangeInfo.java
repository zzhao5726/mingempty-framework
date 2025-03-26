package top.mingempty.meta.data.domain.middleware.mq.info;

import lombok.Data;
import top.mingempty.meta.data.domain.enums.DictOperationEnum;

import java.util.Collection;

/**
 * 条目变化消息通知实体
 *
 * @author zzhao
 */
@Data
public class EntryChangeInfo {

    /**
     * 条目编码
     */
    private String entryCode;

    /**
     * 操作类型
     */
    private DictOperationEnum dictOperationType;

    /**
     * 字典项编码
     */
    private Collection<String> itemCodes;
}
