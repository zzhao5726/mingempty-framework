package top.mingempty.stream.interceptor.base;

import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * 在检索 Message 之后，但在将 Message 返回给调用方之前立即调用
 *
 * @author zzhao
 */
public interface MePostReceive extends Ordered {

    /**
     * 在消息接收后、处理前调用，可修改或丢弃消息。
     *
     * @param message 接收到的消息
     * @param channel 来源通道
     * @return 修改后的消息，返回 {@code null} 将丢弃消息
     * @implNote 典型场景：消息解密、内容校验
     * <pre>{@code
     * public Message<?> postReceive(Message<?> message, MessageChannel channel) {
     *     String decrypted = decrypt(message.getPayload());
     *     return MessageBuilder.withPayload(decrypted).build();
     * }
     * }</pre>
     */
    @Nullable
     Message<?> postReceive(Message<?> message, MessageChannel channel);

    @Override
    default int getOrder() {
        return 0;
    }
}
