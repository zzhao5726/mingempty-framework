package top.mingempty.stream.interceptor.base;

import org.springframework.core.Ordered;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * 在接收完成后调用，而不管是否引发了任何异常，从而允许进行适当的资源清理
 *
 * @author zzhao
 */
public interface MeAfterReceiveCompletion extends Ordered {

    /**
     * 在消息接收和处理完成后调用（无论成功或异常）。
     *
     * @param message 接收的消息（可能为 {@code null}）
     * @param channel 来源通道
     * @param ex      处理过程中的异常（无异常时为 {@code null}）
     * @implNote 典型场景：记录消费日志
     * <pre>{@code
     * public void afterReceiveCompletion(Message<?> message,
     *                                   MessageChannel channel, Exception ex) {
     *     if (ex != null) log.error("Process failed", ex);
     * }
     * }</pre>
     */
    void afterReceiveCompletion(@Nullable Message<?> message, MessageChannel channel, @Nullable Exception ex);

    @Override
    default int getOrder() {
        return 0;
    }
}
