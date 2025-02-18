package top.mingempty.stream.interceptor;

import org.springframework.integration.dispatcher.MessageHandlingTaskDecorator;
import org.springframework.messaging.support.MessageHandlingRunnable;
import top.mingempty.concurrent.thread.SimpleDelegatingRunnable;

/**
 * 使用异步消息时，对线程上线文进行进行封装
 *
 * @author zzhao
 */
public class MeMessageHandlingTaskDecorator implements MessageHandlingTaskDecorator {
    @Override
    public Runnable decorate(MessageHandlingRunnable task) {
        return new SimpleDelegatingRunnable(task::run);
    }
}
