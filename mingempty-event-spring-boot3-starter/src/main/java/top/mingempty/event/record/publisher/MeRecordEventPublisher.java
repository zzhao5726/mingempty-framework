package top.mingempty.event.record.publisher;

import cn.hutool.core.util.ObjUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import top.mingempty.event.model.enums.EventStatusEnum;
import top.mingempty.event.publisher.MeEventPublisher;
import top.mingempty.event.record.model.MeRecordApplicationEvent;
import top.mingempty.event.record.model.PublisherRecordPo;
import top.mingempty.event.record.service.PublisherRecordService;
import top.mingempty.util.SpringContextUtil;

import java.util.Optional;

/**
 * 基于事件记录的事件发送统一入口
 *
 * @author zzhao
 */
@Slf4j
public class MeRecordEventPublisher {

    @Autowired
    private PublisherRecordService publisherRecordService;

    @Transactional(rollbackFor = Exception.class)
    public void publishEvent(MeRecordApplicationEvent applicationEvent) {
        //注册事务
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                try {
                    //检查是否已发送成功
                    PublisherRecordPo publisherRecordPo = publisherRecordService.selectById(applicationEvent.getEventId());
                    if (publisherRecordPo != null
                            && !EventStatusEnum.INITIALIZED.getCode().equals(publisherRecordPo.getEventStatus())) {
                        log.warn("事件[{}]已成功发送", applicationEvent.getEventId());
                        return;
                    }
                    //事务成功提交后发送事件
                    MeEventPublisher.publishEventStatic(applicationEvent);
                } catch (Exception exception) {
                    log.error("事件发送异常，异常原因为：", exception);
                    throw exception;
                }
                //此处将事件发布记录表标记为已发送
                afterPublisherEvent(applicationEvent);
            }
        });
        beforePublisherEvent(applicationEvent);
    }

    public static void publishEventStatic(MeRecordApplicationEvent applicationEvent) {
        Optional.ofNullable(SpringContextUtil.gainBean(MeRecordEventPublisher.class))
                .orElseThrow(() -> new IllegalArgumentException("事件发送者未初始化。。。。。。"))
                .publishEvent(applicationEvent);
    }

    void beforePublisherEvent(MeRecordApplicationEvent applicationEvent) {
        PublisherRecordPo publisherRecordPo = publisherRecordService.selectById(applicationEvent.getEventId());
        if (ObjUtil.isNotEmpty(publisherRecordPo)) {
            return;
        }
        publisherRecordService.record(applicationEvent);
    }

    void afterPublisherEvent(MeRecordApplicationEvent applicationEvent) {
        //此处将事件发布记录表标记为已发送
        publisherRecordService.complete(applicationEvent);
    }
}
