package top.mingempty.meta.data.mq.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.meta.data.model.mq.EntryChange;
import top.mingempty.stream.listener.MeAbstractListener;

/**
 * 条目变化监听
 */
@Slf4j
@Component(value = "entryChange")
public class EntryChangeListener extends MeAbstractListener<EntryChange> {

    @Override
    public void onListener(Message<EntryChange> message, EntryChange data) {
        log.info("条目变化，变化内容为:[{}]", JsonUtil.toStr(data));
        // TODO  预留
    }
}
