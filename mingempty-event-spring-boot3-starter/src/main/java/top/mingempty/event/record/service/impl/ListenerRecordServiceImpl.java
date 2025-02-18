package top.mingempty.event.record.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import top.mingempty.commons.trace.TraceContext;
import top.mingempty.commons.trace.enums.ProtocolEnum;
import top.mingempty.commons.trace.enums.SpanTypeEnum;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.event.exception.EventException;
import top.mingempty.event.model.enums.EventStatusEnum;
import top.mingempty.event.record.listener.MeRecordApplicationListener;
import top.mingempty.event.record.mapper.ListenerRecordMapper;
import top.mingempty.event.record.model.ListenerRecordExample;
import top.mingempty.event.record.model.ListenerRecordPo;
import top.mingempty.event.record.model.MeRecordApplicationEvent;
import top.mingempty.event.record.model.PublisherRecordPo;
import top.mingempty.event.record.service.ListenerRecordService;
import top.mingempty.event.record.service.PublisherRecordService;
import top.mingempty.trace.util.TraceAdapterUtil;
import top.mingempty.util.SpringContextUtil;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 事件监听记录服务实现类
 *
 * @author zzhao
 */
@Service
public class ListenerRecordServiceImpl implements ListenerRecordService {


    @Autowired
    private ListenerRecordMapper listenerRecordMapper;

    @Lazy
    @Autowired
    private PublisherRecordService publisherRecordService;

    @Override
    public void cancel(String eventId) {
        ListenerRecordPo listenerRecordPo = new ListenerRecordPo();
        listenerRecordPo.setEventId(eventId);
        listenerRecordPo.setEventStatus(EventStatusEnum.CANCELED.getCode());
        listenerRecordPo.setUpdateTime(LocalDateTime.now());
        ListenerRecordExample listenerRecordExample = new ListenerRecordExample();
        listenerRecordExample.createCriteria()
                .andEventIdEqualTo(eventId)
                .andEventStatusEqualTo(EventStatusEnum.INITIALIZED.getCode());
        listenerRecordMapper.updateByExampleSelective(listenerRecordPo, listenerRecordExample);
    }

    @Override
    public void cancelWithId(String eventListenerId) {
        ListenerRecordPo listenerRecordPo = new ListenerRecordPo();
        listenerRecordPo.setEventListenerId(eventListenerId);
        listenerRecordPo.setEventStatus(EventStatusEnum.CANCELED.getCode());
        listenerRecordPo.setUpdateTime(LocalDateTime.now());
        ListenerRecordExample listenerRecordExample = new ListenerRecordExample();
        listenerRecordExample.createCriteria()
                .andEventListenerIdEqualTo(eventListenerId)
                .andEventStatusEqualTo(EventStatusEnum.INITIALIZED.getCode());
        listenerRecordMapper.updateByExampleSelective(listenerRecordPo, listenerRecordExample);
    }

    @Override
    public void cancel(String eventId, String eventListenerClass) {
        ListenerRecordPo listenerRecordPo = new ListenerRecordPo();
        listenerRecordPo.setEventId(eventId);
        listenerRecordPo.setEventListenerClass(eventListenerClass);
        listenerRecordPo.setEventStatus(EventStatusEnum.CANCELED.getCode());
        listenerRecordPo.setUpdateTime(LocalDateTime.now());
        ListenerRecordExample listenerRecordExample = new ListenerRecordExample();
        listenerRecordExample.createCriteria()
                .andEventIdEqualTo(eventId)
                .andEventListenerClassEqualTo(eventListenerClass)
                .andEventStatusEqualTo(EventStatusEnum.INITIALIZED.getCode());
        listenerRecordMapper.updateByExampleSelective(listenerRecordPo, listenerRecordExample);
    }

    @Override
    public ListenerRecordPo selectByUnique(String eventId, String eventListenerClass) {
        ListenerRecordExample listenerRecordExample = new ListenerRecordExample();
        listenerRecordExample.createCriteria()
                .andEventIdEqualTo(eventId)
                .andEventListenerClassEqualTo(eventListenerClass);
        List<ListenerRecordPo> listenerRecordPos = listenerRecordMapper.selectByExample(listenerRecordExample);
        return CollUtil.isNotEmpty(listenerRecordPos) ? listenerRecordPos.getFirst() : null;
    }

    @Override
    public String record(MeRecordApplicationEvent applicationEvent, String eventListenerClass) {
        ListenerRecordPo listenerRecordPo = new ListenerRecordPo();
        listenerRecordPo.setEventListenerId(UUID.fastUUID().toString(true));
        listenerRecordPo.setEventId(applicationEvent.getEventId());
        listenerRecordPo.setEventType(applicationEvent.getEventType());
        listenerRecordPo.setBizNo(applicationEvent.getBizNo());
        listenerRecordPo.setEventListenerClass(eventListenerClass);
        listenerRecordPo.setEventStatus(EventStatusEnum.INITIALIZED.getCode());
        listenerRecordPo.setRecordTime(LocalDateTime.now());
        TraceContext traceContext = TraceAdapterUtil.gainTraceContext();
        if (traceContext != null) {
            listenerRecordPo.setTraceId(traceContext.getTraceId());
            listenerRecordPo.setSpanId(traceContext.getSpanId());
        }
        listenerRecordPo.setCreateTime(LocalDateTime.now());
        listenerRecordPo.setUpdateTime(LocalDateTime.now());
        listenerRecordMapper.insert(listenerRecordPo);
        return listenerRecordPo.getEventListenerId();
    }

    @Override
    public void complete(String eventListenerId) {
        ListenerRecordPo listenerRecordPo = new ListenerRecordPo();
        listenerRecordPo.setEventListenerId(eventListenerId);
        listenerRecordPo.setEventStatus(EventStatusEnum.PROCESSED.getCode());
        listenerRecordPo.setUpdateTime(LocalDateTime.now());
        listenerRecordPo.setCompletionTime(LocalDateTime.now());
        listenerRecordMapper.updateByPrimaryKeySelective(listenerRecordPo);
    }

    @Override
    public void complete(String eventId, String eventListenerClass) {
        ListenerRecordPo listenerRecordPo = new ListenerRecordPo();
        listenerRecordPo.setEventId(eventId);
        listenerRecordPo.setEventListenerClass(eventListenerClass);
        listenerRecordPo.setEventStatus(EventStatusEnum.PROCESSED.getCode());
        listenerRecordPo.setUpdateTime(LocalDateTime.now());
        listenerRecordPo.setCompletionTime(LocalDateTime.now());
        ListenerRecordExample listenerRecordExample = new ListenerRecordExample();
        listenerRecordExample.createCriteria()
                .andEventIdEqualTo(eventId)
                .andEventListenerClassEqualTo(eventListenerClass);
        listenerRecordMapper.updateByExampleSelective(listenerRecordPo, listenerRecordExample);
    }

    @Override
    @SneakyThrows
    public void retry(String eventListenerId) {
        ListenerRecordPo listenerRecordPo = listenerRecordMapper.selectByPrimaryKey(eventListenerId);
        if (listenerRecordPo == null) {
            throw new EventException("event-0000000004");
        }
        if (EventStatusEnum.PROCESSED.getCode().equals(listenerRecordPo.getEventStatus())) {
            throw new EventException("event-0000000005");
        }
        MeRecordApplicationListener applicationListener
                = (MeRecordApplicationListener) SpringContextUtil.gainBean(Class.forName(listenerRecordPo.getEventListenerClass()));

        PublisherRecordPo publisherRecordPo = publisherRecordService.selectById(listenerRecordPo.getEventId());
        MeRecordApplicationEvent meRecordApplicationEvent
                = (MeRecordApplicationEvent) JsonUtil.toObj(publisherRecordPo.getEventData(), Class.forName(publisherRecordPo.getEventClass()));
        if (StrUtil.isNotEmpty(listenerRecordPo.getTraceId())
                && StrUtil.isNotEmpty(listenerRecordPo.getSpanId())) {
            TraceContext traceContext = new TraceContext("listener-retry",
                    listenerRecordPo.getTraceId(), listenerRecordPo.getSpanId(),
                    ProtocolEnum.OTHER, SpanTypeEnum.EVENT_ASYNC);
            traceContext.start();
        }
        applicationListener.onApplicationEvent(meRecordApplicationEvent);
    }
}
