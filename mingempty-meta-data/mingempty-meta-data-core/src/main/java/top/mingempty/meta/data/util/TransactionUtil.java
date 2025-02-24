package top.mingempty.meta.data.util;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import top.mingempty.meta.data.model.enums.DictOperationEnum;
import top.mingempty.meta.data.model.exception.MetaDataException;
import top.mingempty.meta.data.model.mq.EntryChange;
import top.mingempty.meta.data.mq.producer.EntryChangeProducer;
import top.mingempty.util.SpringContextUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 事务工具类
 */
public class TransactionUtil {

    /**
     * 条目变更，事务提交后，注册消息通知的事务
     *
     * @param entryCode
     * @param dictOperationType
     */
    public static void registerSynchronization(String entryCode, DictOperationEnum dictOperationType) {
        registerSynchronization(entryCode, dictOperationType, null);
    }

    /**
     * 条目变更，事务提交后，注册消息通知的事务
     *
     * @param entryCode
     * @param dictOperationType
     * @param itemCodes
     */
    public static void registerSynchronization(String entryCode, DictOperationEnum dictOperationType, List<String> itemCodes) {
        EntryChange entryChange = new EntryChange();
        entryChange.setEntryCodes(Set.of(entryCode));
        entryChange.setDictOperationType(dictOperationType);
        if (itemCodes != null) {
            entryChange.setItemCodes(Map.of(entryCode, itemCodes));
        }
        registerSynchronization(entryChange);
    }

    /**
     * 条目变更，事务提交后，注册消息通知的事务
     *
     * @param entryChange
     */
    public static void registerSynchronization(EntryChange entryChange) {
        //注册事务
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                Optional.ofNullable(SpringContextUtil.gainBean(EntryChangeProducer.class))
                        .orElseThrow(() -> new MetaDataException("meta-data-core-0000000003"))
                        .send(entryChange);
            }
        });
    }


}
