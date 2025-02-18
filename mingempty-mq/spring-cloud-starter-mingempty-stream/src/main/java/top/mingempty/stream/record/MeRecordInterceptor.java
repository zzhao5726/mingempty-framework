package top.mingempty.stream.record;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.stream.interceptor.base.MeAfterSendCompletion;
import top.mingempty.stream.interceptor.base.MePreSend;
import top.mingempty.stream.model.StreamConstant;
import top.mingempty.stream.record.service.MqRecordService;

/**
 * 消息记录拦截器
 */
@Slf4j
public class MeRecordInterceptor {

    @AllArgsConstructor
    public static class RecordOutStartChannelInterceptor implements MePreSend {

        private final MqRecordService mqRecordService;

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            if (!message.getHeaders().containsKey(StreamConstant.RECORD_MESSAGE_ID)) {
                return message;
            }
            return mqRecordService.record(ZeroOrOneEnum.ZERO.getItemCode(), message, channel);
        }

        @Override
        public int getOrder() {
            return -1;
        }
    }

    @AllArgsConstructor
    public static class RecordOutEndChannelInterceptor implements MeAfterSendCompletion {

        private final MqRecordService mqRecordService;

        @Override
        public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
            if (!sent
                    || !message.getHeaders().containsKey(StreamConstant.RECORD_ID)
                    || !message.getHeaders().containsKey(StreamConstant.RECORD_MESSAGE_ID)) {
                return;
            }
            mqRecordService.complete(message.getHeaders().get(StreamConstant.RECORD_ID, String.class));
        }

        @Override
        public int getOrder() {
            return Integer.MIN_VALUE;
        }
    }

    @AllArgsConstructor
    public static class RecordInStartChannelInterceptor implements MePreSend {

        private final MqRecordService mqRecordService;

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            if (!message.getHeaders().containsKey(StreamConstant.RECORD_MESSAGE_ID)) {
                return message;
            }
            return mqRecordService.record(ZeroOrOneEnum.ONE.getItemCode(), message, channel);
        }

        @Override
        public int getOrder() {
            return Integer.MAX_VALUE;
        }
    }

    @AllArgsConstructor
    public static class RecordInEndChannelInterceptor implements MeAfterSendCompletion {

        private final MqRecordService mqRecordService;

        @Override
        public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
            if (!sent
                    || !message.getHeaders().containsKey(StreamConstant.RECORD_ID)
                    || !message.getHeaders().containsKey(StreamConstant.RECORD_MESSAGE_ID)) {
                return;
            }
            mqRecordService.complete(message.getHeaders().get(StreamConstant.RECORD_ID, String.class));
        }

        @Override
        public int getOrder() {
            return 0;
        }
    }

}
