package top.mingempty.meta.data.domain.middleware.mq.consumer.dict;

import top.mingempty.meta.data.domain.middleware.mq.info.EntryChangeInfo;

/**
 * 条目变更监听
 *
 * @author zzhao
 */
public interface EntryChangeConsumer {

    /**
     * 条目变更监听
     *
     * @param entryChangeInfo 条目变更信息
     */
    void onEntryChange(EntryChangeInfo entryChangeInfo);
}
