package top.mingempty.meta.data.infrastructure.middleware.mq.dict;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import top.mingempty.meta.data.domain.middleware.mq.info.EntryChangeInfo;
import top.mingempty.meta.data.domain.middleware.mq.producer.dict.EntryChangeProducer;

import java.util.Set;

/**
 * 字典条目变更消息生产者实现类
 *
 * @author zzhao
 */
@Slf4j
@Component
@AllArgsConstructor
public class EntryChangeProducerImpl implements EntryChangeProducer {

    private final static String BINDING_NAME = "entry-change-out-0";

    private final StreamBridge streamBridge;

    @Override
    public void send(EntryChangeInfo entryChangeInfo) {
        if (ObjUtil.isEmpty(entryChangeInfo)) {
            log.warn("字典条目变更数据为空");
            return;
        }
        //去重一下
        if (CollUtil.isNotEmpty(entryChangeInfo.getItemCodes())) {
            entryChangeInfo.setItemCodes(Set.copyOf(entryChangeInfo.getItemCodes()));
        }
        streamBridge.send(BINDING_NAME, entryChangeInfo);
    }

}
