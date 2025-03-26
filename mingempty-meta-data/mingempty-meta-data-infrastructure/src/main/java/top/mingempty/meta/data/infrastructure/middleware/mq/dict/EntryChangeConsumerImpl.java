package top.mingempty.meta.data.infrastructure.middleware.mq.dict;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.meta.data.domain.middleware.mq.consumer.dict.EntryChangeConsumer;
import top.mingempty.meta.data.domain.middleware.mq.info.EntryChangeInfo;

/**
 * 监听字典条目变更实现类
 *
 * @author zzhao
 */
@Slf4j
@Component
public class EntryChangeConsumerImpl implements EntryChangeConsumer {

    @Override
    @Transactional()
    public void onEntryChange(EntryChangeInfo entryChangeInfo) {
        log.info("条目变化，变化内容为:[{}]", JsonUtil.toStr(entryChangeInfo));
        // TODO  预留
    }
}
