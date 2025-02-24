package top.mingempty.meta.data.mq.producer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import top.mingempty.meta.data.model.mq.EntryChange;

/**
 * 条目变化发送者
 */
@Slf4j
@Component
@AllArgsConstructor
public class EntryChangeProducer {

    private final static String BINDING_NAME = "entry-change-out-0";

    private final StreamBridge streamBridge;


    public void send(EntryChange entryChange) {
        streamBridge.send(BINDING_NAME, entryChange);
    }


}
