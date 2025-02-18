package top.mingempty.stream.interceptor.base;

import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * 在 Message 实际发送到通道之前调用
 *
 * @author zzhao
 */
public interface MePreSend extends Ordered {

    /**
     * 在消息发送到通道前调用，可修改消息或取消发送。
     *
     * @param message 待发送的消息
     * @param channel 目标消息通道
     * @return 修改后的消息（返回新对象），返回 {@code null} 将取消发送
     * @apiNote 典型场景：添加消息头、权限校验
     * <pre>{@code
     * public Message<?> preSend(Message<?> message, MessageChannel channel) {
     *     return MessageBuilder.fromMessage(message)
     *         .setHeader("traceId", UUID.randomUUID().toString())
     *         .build();
     * }
     * }</pre>
     */
    @Nullable
    Message<?> preSend(Message<?> message, MessageChannel channel);

    @Override
    default int getOrder() {
        return 0;
    }
}
