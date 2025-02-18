package top.mingempty.stream.interceptor.base;

import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * 在发送完成后调用，而不管是否引发了任何异常，从而允许进行适当的资源清理
 */
public interface MeAfterSendCompletion extends Ordered {

    /**
     * 在消息发送完成后调用（无论成功或异常）。
     *
     * @param message 已发送的消息
     * @param channel 目标通道
     * @param sent    是否成功发送
     * @param ex      发送过程中的异常（无异常时为 {@code null}）
     * @implNote 典型场景：资源清理、异常处理
     * <pre>{@code
     * public void afterSendCompletion(Message<?> message, MessageChannel channel,
     *                                boolean sent, Exception ex) {
     *     if (ex != null) log.error("Send failed", ex);
     * }
     * }</pre>
     */
    void afterSendCompletion(
            Message<?> message, MessageChannel channel, boolean sent, @Nullable Exception ex);

    @Override
    default int getOrder() {
        return 0;
    }
}
