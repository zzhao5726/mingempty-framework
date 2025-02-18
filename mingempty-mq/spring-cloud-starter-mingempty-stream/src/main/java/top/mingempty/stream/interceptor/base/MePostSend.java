package top.mingempty.stream.interceptor.base;

import org.springframework.core.Ordered;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * 在 send 调用后立即调用
 *
 * @author zzhao
 */
public interface MePostSend extends Ordered {

    /**
     * 在消息发送到通道后调用（无论成功与否）。
     *
     * @param message 已发送的消息
     * @param channel 目标通道
     * @param sent    是否成功传递到通道
     * @implNote 典型场景：记录发送日志
     * <pre>{@code
     * public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
     *     if (sent) log.info("Sent: {}", message.getPayload());
     * }
     * }</pre>
     */
    void postSend(Message<?> message, MessageChannel channel, boolean sent);

    @Override
    default int getOrder() {
        return 0;
    }
}
