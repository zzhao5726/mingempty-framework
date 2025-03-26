package top.mingempty.meta.data.domain.middleware.mq.producer.dict;

import top.mingempty.meta.data.domain.enums.DictOperationEnum;
import top.mingempty.meta.data.domain.middleware.mq.info.EntryChangeInfo;

import java.util.Set;

/**
 * 字典条目变化消息生产者
 *
 * @author zzhao
 */
public interface EntryChangeProducer {

    /**
     * 发送字典条目变化消息
     *
     * @param entryCode     字典条目编码
     * @param dictOperation 字典条目操作
     * @param itemCodes     字典条目编码集
     */
    default void send(String entryCode, DictOperationEnum dictOperation, String... itemCodes) {
        EntryChangeInfo entryChangeInfo = new EntryChangeInfo();
        entryChangeInfo.setEntryCode(entryCode);
        entryChangeInfo.setDictOperationType(dictOperation);
        if (itemCodes != null) {
            entryChangeInfo.setItemCodes(Set.of(itemCodes));
        }
        send(entryChangeInfo);
    }

    /**
     * 发送字典条目变化消息
     *
     * @param entryChangeInfo 条目变化消息通知
     */
    void send(EntryChangeInfo entryChangeInfo);
}
