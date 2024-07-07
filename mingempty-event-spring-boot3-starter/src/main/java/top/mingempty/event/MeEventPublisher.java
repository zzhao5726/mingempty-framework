package top.mingempty.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import top.mingempty.spring.util.SpringContextUtil;

import java.util.Optional;

/**
 * 事件发送统一入口
 *
 * @author zzhao
 */
@Slf4j
@AllArgsConstructor
public class MeEventPublisher {
    @Getter(value = lombok.AccessLevel.PRIVATE)
    private final ApplicationEventPublisher applicationEventPublisher;

    public static <D> void publishEvent(D data,
                                        Class<? extends MeEventListenerHandler<D>> meEventListenerHandlerClass) {
        ApplicationEventPublisher applicationEventPublisher
                = Optional.ofNullable(Optional
                        .ofNullable(SpringContextUtil.getBean(MeEventPublisher.class))
                        .orElseThrow(()
                                -> new IllegalArgumentException("事件发送者未初始化。。。。。。"))
                        .getApplicationEventPublisher())
                .orElseThrow(()
                        -> new IllegalArgumentException("事件发送者未初始化。。。。。。"));
        applicationEventPublisher.publishEvent(new MeApplicationEvent<D>(applicationEventPublisher, data, meEventListenerHandlerClass));
    }

}
