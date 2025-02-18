package top.mingempty.event.record.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.commons.trace.TraceContext;
import top.mingempty.commons.trace.enums.ProtocolEnum;
import top.mingempty.commons.trace.enums.SpanTypeEnum;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.event.exception.EventException;
import top.mingempty.event.model.enums.EventStatusEnum;
import top.mingempty.event.record.mapper.PublisherRecordMapper;
import top.mingempty.event.record.model.MeRecordApplicationEvent;
import top.mingempty.event.record.model.PublisherRecordPo;
import top.mingempty.event.record.publisher.MeRecordEventPublisher;
import top.mingempty.event.record.service.ListenerRecordService;
import top.mingempty.event.record.service.PublisherRecordService;
import top.mingempty.trace.util.TraceAdapterUtil;

import java.time.LocalDateTime;

/**
 * 事件发布记录服务实现类
 *
 * @author zzhao
 */
@Service
@AllArgsConstructor
public class PublisherRecordServiceImpl implements PublisherRecordService {

    @Autowired
    private PublisherRecordMapper publisherRecordMapper;

    @Lazy
    @Autowired
    private ListenerRecordService listenerRecordService;

    @Override
    public void record(MeRecordApplicationEvent applicationEvent) {
        PublisherRecordPo publisherRecordPo = new PublisherRecordPo();
        publisherRecordPo.setEventId(applicationEvent.getEventId());
        publisherRecordPo.setEventType(applicationEvent.getEventType());
        publisherRecordPo.setBizNo(applicationEvent.getBizNo());
        publisherRecordPo.setEventClass(applicationEvent.getClass().getName());
        publisherRecordPo.setEventStatus(EventStatusEnum.INITIALIZED.getCode());
        publisherRecordPo.setEventData(JsonUtil.toStr(applicationEvent));
        publisherRecordPo.setRecordTime(LocalDateTime.now());
        TraceContext traceContext = TraceAdapterUtil.gainTraceContext();
        if (traceContext != null) {
            publisherRecordPo.setTraceId(traceContext.getTraceId());
            publisherRecordPo.setSpanId(traceContext.getSpanId());
        }
        publisherRecordPo.setCreateTime(LocalDateTime.now());
        publisherRecordPo.setUpdateTime(LocalDateTime.now());
        publisherRecordMapper.insert(publisherRecordPo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void complete(MeRecordApplicationEvent applicationEvent) {
        PublisherRecordPo publisherRecordPo = new PublisherRecordPo();
        publisherRecordPo.setEventId(applicationEvent.getEventId());
        publisherRecordPo.setEventStatus(EventStatusEnum.PROCESSED.getCode());
        publisherRecordPo.setUpdateTime(LocalDateTime.now());
        publisherRecordPo.setCompletionTime(LocalDateTime.now());
        publisherRecordMapper.updateByPrimaryKeySelective(publisherRecordPo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancel(String eventId) {
        //将监听记录状态设置为已撤销
        listenerRecordService.cancel(eventId);
        PublisherRecordPo publisherRecordPo = new PublisherRecordPo();
        publisherRecordPo.setEventId(eventId);
        publisherRecordPo.setEventStatus(EventStatusEnum.CANCELED.getCode());
        publisherRecordPo.setUpdateTime(LocalDateTime.now());
        publisherRecordMapper.updateByPrimaryKeySelective(publisherRecordPo);
    }

    @Override
    @SneakyThrows
    public void retry(String eventId) {
        PublisherRecordPo publisherRecordPo = selectById(eventId);
        if (publisherRecordPo == null) {
            throw new EventException("event-0000000002");
        }
        if (EventStatusEnum.PROCESSED.getCode().equals(publisherRecordPo.getEventStatus())) {
            throw new EventException("event-0000000003");
        }
        MeRecordApplicationEvent meRecordApplicationEvent
                = (MeRecordApplicationEvent) JsonUtil.toObj(publisherRecordPo.getEventData(), Class.forName(publisherRecordPo.getEventClass()));
        if (StrUtil.isNotEmpty(publisherRecordPo.getTraceId())
                && StrUtil.isNotEmpty(publisherRecordPo.getSpanId())) {
            TraceContext traceContext = new TraceContext("publisher-retry",
                    publisherRecordPo.getTraceId(), publisherRecordPo.getSpanId(),
                    ProtocolEnum.OTHER, SpanTypeEnum.EVENT_ASYNC);
            traceContext.start();
        }
        MeRecordEventPublisher.publishEventStatic(meRecordApplicationEvent);
    }

    @Override
    public PublisherRecordPo selectById(String eventId) {
        return publisherRecordMapper.selectByPrimaryKey(eventId);
    }
}
