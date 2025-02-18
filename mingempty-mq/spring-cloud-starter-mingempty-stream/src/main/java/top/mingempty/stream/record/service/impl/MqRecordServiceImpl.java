package top.mingempty.stream.record.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import top.mingempty.commons.trace.TraceContext;
import top.mingempty.commons.trace.enums.ProtocolEnum;
import top.mingempty.commons.trace.enums.SpanTypeEnum;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.commons.util.ReflectionUtil;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.stream.exception.StreamException;
import top.mingempty.stream.model.StreamConstant;
import top.mingempty.stream.record.RedordStreamBridge;
import top.mingempty.stream.record.mapper.MqRecordMapper;
import top.mingempty.stream.record.model.MqRecordExample;
import top.mingempty.stream.record.model.MqRecordPo;
import top.mingempty.stream.record.model.enums.MessageStatusEnum;
import top.mingempty.stream.record.service.MqRecordService;
import top.mingempty.trace.util.TraceAdapterUtil;
import top.mingempty.util.SpringContextUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * mq记录服务实现类
 */
@Slf4j
@Service
@AllArgsConstructor
public class MqRecordServiceImpl implements MqRecordService {

    private final MqRecordMapper mqRecordMapper;

    private final RedordStreamBridge redordStreamBridge;

    @Override
    public Message<?> record(String recordType, Message<?> message, MessageChannel channel) {
        String messageId = message.getHeaders().get(StreamConstant.RECORD_MESSAGE_ID, String.class);
        String componentName = ReflectionUtil.getValue("componentName", channel);
        String beanName = ReflectionUtil.getValue("beanName", channel);
        MqRecordExample mqRecordExample = new MqRecordExample();
        mqRecordExample.createCriteria()
                .andMessageIdEqualTo(messageId)
                .andComponentNameEqualTo(componentName);
        List<MqRecordPo> mqRecordPos = mqRecordMapper.selectByExample(mqRecordExample);
        if (CollUtil.isNotEmpty(mqRecordPos)) {
            return MessageBuilder.fromMessage(message)
                    .setHeader(StreamConstant.RECORD_ID, mqRecordPos.getFirst().getMqId())
                    .build();
        }
        MqRecordPo mqRecordPo = new MqRecordPo();
        mqRecordPo.setMqId(UUID.fastUUID().toString(true));
        mqRecordPo.setMessageId(messageId);
        mqRecordPo.setRecordType(recordType);
        mqRecordPo.setComponentName(componentName);
        mqRecordPo.setBeanName(beanName == null ? "" : beanName.toString());
        mqRecordPo.setMessageStatus(MessageStatusEnum.INITIALIZED.getCode());
        mqRecordPo.setRecordTime(LocalDateTime.now());
        TraceContext traceContext = TraceAdapterUtil.gainTraceContext();
        if (traceContext != null) {
            mqRecordPo.setTraceId(traceContext.getTraceId());
            mqRecordPo.setSpanId(traceContext.getSpanId());
        }
        mqRecordPo.setCreateTime(LocalDateTime.now());
        mqRecordPo.setUpdateTime(LocalDateTime.now());
        Object payload = message.getPayload();
        if (payload instanceof byte[] bytes) {
            mqRecordPo.setMessagePayload(new String(bytes));
        } else {
            mqRecordPo.setMessagePayload(JsonUtil.toStr(payload));
        }
        MessageHeaders headers = message.getHeaders();
        Map<String, Object> newHeader = headers.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("sourceData"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (ZeroOrOneEnum.ZERO.getItemCode().equals(mqRecordPo.getRecordType())) {
            mqRecordPo.setMessageHeaders(JsonUtil.toStr(newHeader));
        } else {
            MimeType mimeType = headers.get(MessageHeaders.CONTENT_TYPE, MimeType.class);
            newHeader.put("contentType", mimeType.toString());
            mqRecordPo.setMessageHeaders(JsonUtil.toStr(newHeader));
        }
        mqRecordMapper.insert(mqRecordPo);
        return MessageBuilder.fromMessage(message)
                .setHeader(StreamConstant.RECORD_ID, mqRecordPo.getMqId())
                .build();
    }

    @Override
    public void complete(String mqId) {
        MqRecordPo mqRecordPo = new MqRecordPo();
        mqRecordPo.setMqId(mqId);
        mqRecordPo.setMessageStatus(MessageStatusEnum.PROCESSED.getCode());
        mqRecordPo.setCompletionTime(LocalDateTime.now());
        mqRecordPo.setUpdateTime(LocalDateTime.now());
        mqRecordMapper.updateByPrimaryKeySelective(mqRecordPo);
    }

    @Override
    public void retry(String mqId) {
        MqRecordPo mqRecordPo = mqRecordMapper.selectByPrimaryKey(mqId);
        if (mqRecordPo == null) {
            throw new StreamException("stream-0000000001");
        }
        if (MessageStatusEnum.PROCESSED.getCode().equals(mqRecordPo.getMessageHeaders())) {
            if (ZeroOrOneEnum.ZERO.getItemCode().equals(mqRecordPo.getRecordType())) {
                throw new StreamException("stream-0000000002");
            } else {
                throw new StreamException("stream-0000000003");
            }

        }
        if (StrUtil.isNotEmpty(mqRecordPo.getTraceId())
                && StrUtil.isNotEmpty(mqRecordPo.getSpanId())) {
            TraceContext traceContext = new TraceContext("mq-retry",
                    mqRecordPo.getTraceId(), mqRecordPo.getSpanId(),
                    ProtocolEnum.RPC, SpanTypeEnum.MQ_OTHER);
            traceContext.start();
        }
        Map<String, Object> messageHeaders = JsonUtil.toMap(mqRecordPo.getMessageHeaders());

        Message<String> message = MessageBuilder.withPayload(mqRecordPo.getMessagePayload())
                .copyHeaders(messageHeaders)
                .setHeader(StreamConstant.RECORD_MESSAGE_ID, mqRecordPo.getMessageId())
                .build();

        if (ZeroOrOneEnum.ZERO.getItemCode().equals(mqRecordPo.getRecordType())) {
            redordStreamBridge.send(mqRecordPo.getComponentName(), message);
        } else {
            Optional.ofNullable(SpringContextUtil.gainBean(mqRecordPo.getBeanName(), MessageChannel.class))
                    .orElseThrow(() -> new StreamException("stream-0000000004"))
                    .send(message);
        }
    }

}
