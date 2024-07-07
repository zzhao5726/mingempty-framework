package top.mingempty.event;

import cn.hutool.core.lang.Assert;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import top.mingempty.commons.trace.TraceContext;

import java.time.Clock;


/**
 * 事件监听上下文
 *
 * @author zzhao
 */
public class MeApplicationEvent<D> extends ApplicationEvent {

    @Getter(value = lombok.AccessLevel.MODULE)
    private final TraceContext traceContext = TraceContext.gainTraceContext();

    @Getter
    private final D data;

    @Getter(value = lombok.AccessLevel.MODULE)
    private final Class<? extends MeEventListenerHandler<D>> meEventListenerHandlerClass;


    /**
     * 创建一个事件监听的上下文</br>
     * <p>
     * 默认非异步执行
     *
     * @param source                      来源
     * @param data                        事件数据
     * @param meEventListenerHandlerClass 事件处理器类型
     */
    public MeApplicationEvent(Object source, D data,
                              Class<? extends MeEventListenerHandler<D>> meEventListenerHandlerClass) {
        super(source, Clock.systemDefaultZone());
        Assert.notNull(meEventListenerHandlerClass, "meEventListenerHandlerClass is null");

        this.data = data;
        this.meEventListenerHandlerClass = meEventListenerHandlerClass;
    }
}
