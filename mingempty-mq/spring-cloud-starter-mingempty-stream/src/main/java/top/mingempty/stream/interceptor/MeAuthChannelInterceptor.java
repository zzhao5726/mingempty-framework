package top.mingempty.stream.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import top.mingempty.stream.interceptor.base.MeAfterSendCompletion;
import top.mingempty.stream.interceptor.base.MePreSend;


/**
 * 用户拦截器
 *
 * @author zzhao
 */
@Slf4j
public class MeAuthChannelInterceptor {


    public static class MeAuthOutChannelInterceptor implements MePreSend {
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            // TODO 预留 设置消息头内用户信息
            return message;
        }

        @Override
        public int getOrder() {
            return Ordered.HIGHEST_PRECEDENCE + 1;
        }
    }


    public static class MeAuthInChannelInterceptor implements MePreSend, MeAfterSendCompletion {
        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            // TODO 预留 设置当前线程的用户信息
            return message;
        }

        @Override
        public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
            // TODO 预留 清空当前线程的用户信息
        }

        @Override
        public int getOrder() {
            return 1;
        }
    }

}
