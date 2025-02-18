package top.mingempty.event.publisher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import top.mingempty.util.SpringContextUtil;

import java.util.Optional;

/**
 * 事件发送统一入口
 *
 * @author zzhao
 */
@Slf4j
@AllArgsConstructor
public class MeEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(ApplicationEvent applicationEvent) {
        applicationEventPublisher.publishEvent(applicationEvent);
    }

    public static void publishEventStatic(ApplicationEvent applicationEvent) {
        Optional.ofNullable(SpringContextUtil.gainBean(MeEventPublisher.class))
                .orElseThrow(() -> new IllegalArgumentException("事件发送者未初始化。。。。。。"))
                .publishEvent(applicationEvent);
    }

}
