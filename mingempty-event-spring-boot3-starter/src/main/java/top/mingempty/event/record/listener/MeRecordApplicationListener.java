package top.mingempty.event.record.listener;

import cn.hutool.core.util.ObjUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import top.mingempty.event.model.enums.EventStatusEnum;
import top.mingempty.event.record.model.ListenerRecordPo;
import top.mingempty.event.record.model.MeRecordApplicationEvent;
import top.mingempty.event.record.service.ListenerRecordService;

/**
 * 基于事件记录的的事件监听器
 */
@Slf4j
public abstract class MeRecordApplicationListener<E extends MeRecordApplicationEvent> implements ApplicationListener<E> {

    @Lazy
    @Autowired
    private ListenerRecordService listenerRecordService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onApplicationEvent(E event) {
        String className = this.getClass().getName();
        //注册事务
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                try {
                    //判断是否已成功监听消费
                    ListenerRecordPo listenerRecordPo = listenerRecordService.selectByUnique(event.getEventId(), className);
                    if (listenerRecordPo != null
                            && !EventStatusEnum.INITIALIZED.getCode().equals(listenerRecordPo.getEventStatus())) {
                        log.warn("事件[{}]对应监听[{}]已处理", event.getEventId(), className);
                        return;
                    }

                    //事务成功提交后，进行监听处理
                    listenerEvent(event);
                } catch (Exception exception) {
                    log.error("事件监听处理异常，异常原因为：", exception);
                    throw exception;
                }
                //此处将事件发布记录表标记为已发送
                afterListenerEvent(event, className);
            }
        });
        beforeListenerEvent(event, className);
    }

    void beforeListenerEvent(E event, String className) {
        ListenerRecordPo listenerRecordPo = listenerRecordService.selectByUnique(event.getEventId(), className);
        if (ObjUtil.isNotEmpty(listenerRecordPo)) {
            return;
        }
        listenerRecordService.record(event, className
        );
    }

    void afterListenerEvent(E event, String className) {
        listenerRecordService.complete(event.getEventId(), className);
    }

    /**
     * 真实事件处理方法
     *
     * @param event
     */
    public abstract void listenerEvent(E event);

}
