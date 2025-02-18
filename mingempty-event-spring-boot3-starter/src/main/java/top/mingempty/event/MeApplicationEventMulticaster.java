package top.mingempty.event;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.ResolvableType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * 对监听的处理
 */
@Getter
@Slf4j
public class MeApplicationEventMulticaster extends SimpleApplicationEventMulticaster {

    private final ExecutorService executorService;

    public MeApplicationEventMulticaster(@Autowired(required = false) ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        multicastEvent(event, null);
    }

    @Override
    public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
        ResolvableType type = (eventType != null ? eventType : ResolvableType.forInstance(event));
        ExecutorService executorService = getExecutorService();
        for (ApplicationListener<?> listener : getApplicationListeners(event, type)) {
            if (executorService != null && listener.supportsAsyncExecution()) {
                try {
                    executorService.submit(() -> invokeListener(listener, event));
                } catch (RejectedExecutionException ex) {
                    // Probably on shutdown -> invoke listener locally instead
                    invokeListener(listener, event);
                }
            } else {
                invokeListener(listener, event);
            }
        }
    }
}
