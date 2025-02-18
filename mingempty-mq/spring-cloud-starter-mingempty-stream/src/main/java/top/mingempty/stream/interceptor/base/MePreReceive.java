package top.mingempty.stream.interceptor.base;

import org.springframework.core.Ordered;
import org.springframework.messaging.MessageChannel;

/**
 * 在调用 receive 后立即调用，并在实际检索 Message 之前调用
 *
 * @author zzhao
 */
public interface MePreReceive extends Ordered {

    /**
     * 在从通道接收消息前调用。
     *
     * @param channel 消息来源通道
     * @return {@code true} 允许接收，{@code false} 阻止接收
     * @apiNote 典型场景：接收权限校验
     * <pre>{@code
     * public boolean preReceive(MessageChannel channel) {
     *     return checkPermission();
     * }
     * }</pre>
     */
     boolean preReceive(MessageChannel channel) ;

    @Override
    default int getOrder() {
        return 0;
    }
}
