package top.mingempty.stream.interceptor;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import top.mingempty.commons.trace.TraceContext;
import top.mingempty.commons.trace.constants.TraceConstant;
import top.mingempty.commons.trace.enums.ProtocolEnum;
import top.mingempty.commons.trace.enums.SpanTypeEnum;
import top.mingempty.stream.interceptor.base.MeAfterSendCompletion;
import top.mingempty.stream.interceptor.base.MePreSend;
import top.mingempty.trace.util.TraceAdapterUtil;
import top.mingempty.trace.util.TraceIdGenerator;


/**
 * 消息链路拦截器
 *
 * @author zzhao
 */
@Slf4j
@Configuration
@ConditionalOnClass(name = {"top.mingempty.trace.util.TraceAdapterUtil"})
public class MeTraceChannelInterceptor {

    @Bean
    public MeTraceOutChannelInterceptor meTraceOutChannelInterceptor() {
        return new MeTraceOutChannelInterceptor();
    }

    @Bean
    public MeTraceInChannelInterceptor meTraceInChannelInterceptor() {
        return new MeTraceInChannelInterceptor();
    }


    public static class MeTraceOutChannelInterceptor implements MePreSend {
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            return pushTrace(message);
        }

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE;
        }
    }

    public static class MeTraceInChannelInterceptor implements MePreSend, MeAfterSendCompletion {
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            initTrace(message);
            return message;
        }

        @Override
        public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
            log.debug("清空链路ID");
            TraceAdapterUtil.endTraceContext();
        }

        @Override
        public int getOrder() {
            return 0;
        }
    }

    /**
     * 创建新的Message，并将链路信息放在消息头内
     * <br>
     * 如果链路信息不存在，则执行初始化链路信息流程
     *
     * @param message
     * @return
     */
    public static Message<?> pushTrace(Message<?> message) {
        TraceContext traceContext = TraceAdapterUtil.gainTraceContext();
        if (traceContext == null) {
            TraceAdapterUtil.initTraceContext("me-mq-send", ProtocolEnum.OTHER, SpanTypeEnum.MQ_OTHER, null);
            log.debug("当前上下文不存在链路信息，初始化链路ID");
        }
        return MessageBuilder.fromMessage(message)
                .setHeader(TraceConstant.TRACE_ID, TraceIdGenerator.generateTraceId())
                .setHeader(TraceConstant.SPAN_ID, TraceIdGenerator.generateSpanId())
                .build();
    }

    /**
     * 初始化链路
     *
     * @param message
     */
    public static void initTrace(Message<?> message) {
        String traceId = message.getHeaders().get(TraceConstant.TRACE_ID, String.class);
        String spanId = message.getHeaders().get(TraceConstant.SPAN_ID, String.class);
        if (StrUtil.isEmpty(traceId)) {
            traceId = TraceIdGenerator.generateTraceId();
            spanId = TraceIdGenerator.generateSpanId();
        }
        if (StrUtil.isEmpty(spanId)) {
            spanId = TraceIdGenerator.generateSpanId();
        }
        TraceAdapterUtil
                .initTraceContext("me-mq-consumer", traceId, spanId,
                        ProtocolEnum.RPC, SpanTypeEnum.MQ_OTHER, message.getPayload());
        log.debug("链路ID初始化完成");
    }


}
