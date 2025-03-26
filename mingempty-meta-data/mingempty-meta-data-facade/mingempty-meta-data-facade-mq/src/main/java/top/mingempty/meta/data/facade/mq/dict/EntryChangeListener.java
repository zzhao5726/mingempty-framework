package top.mingempty.meta.data.facade.mq.dict;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import top.mingempty.meta.data.domain.middleware.mq.consumer.dict.EntryChangeConsumer;
import top.mingempty.meta.data.domain.middleware.mq.info.EntryChangeInfo;
import top.mingempty.stream.listener.MeAbstractListener;


/**
 * 条目变化监听
 */
@Slf4j
@Component(value = "entryChange")
public class EntryChangeListener extends MeAbstractListener<EntryChangeInfo> {

    @Autowired
    private EntryChangeConsumer entryChangeConsumer;

    @Override
    public void onListener(Message<EntryChangeInfo> message, EntryChangeInfo entryChangeInfo) {
        entryChangeConsumer.onEntryChange(entryChangeInfo);
    }
}