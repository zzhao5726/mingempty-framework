package top.mingempty.event;


import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import top.mingempty.commons.trace.enums.SpanTypeEnum;
import top.mingempty.spring.util.SpringContextUtil;
import top.mingempty.trace.util.TraceAdapterUtil;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * 事件监听统一入口
 *
 * @author zzhao
 */
@Slf4j
@AllArgsConstructor
public class MeEventtListener {


    @EventListener(classes = MeApplicationEvent.class)
    public <D> void handleEvent(MeApplicationEvent<D> meApplicationEvent) {

        List<? extends MeEventListenerHandler<D>> meEventListenerHandlers = SpringContextUtil.getBeanListOfType(meApplicationEvent.getMeEventListenerHandlerClass());
        if (meEventListenerHandlers.isEmpty()) {
            return;
        }
        meEventListenerHandlers
                .stream()
                .sorted(Comparator.comparing(MeEventListenerHandler::getOrder))
                .forEach(meEventListenerHandler -> {
                    if (meEventListenerHandler.gainAsync()) {
                        Executor executor = null;
                        if (StrUtil.isNotEmpty(meEventListenerHandler.gainAsyncBeanName())) {
                            executor = SpringContextUtil.getBean(meEventListenerHandler.gainAsyncBeanName(), Executor.class);
                        }
                        if (executor == null) {
                            executor = ForkJoinPool.commonPool();
                        }
                        executor.execute(() -> eventHandler(meApplicationEvent, meEventListenerHandler));
                    } else {
                        eventHandler(meApplicationEvent, meEventListenerHandler);
                    }
                });
    }

    private static <D> void eventHandler(MeApplicationEvent<D> meApplicationEvent, MeEventListenerHandler<D> meEventListenerHandler) {
        try {
            TraceAdapterUtil.initTraceContextAsync(meApplicationEvent.getTraceContext(),
                    meEventListenerHandler.gainNewSpan(),
                    SpanTypeEnum.EVENT_SYNC, meEventListenerHandler.gainFunctionName(),
                    meApplicationEvent.getData());
            meEventListenerHandler.handle(meApplicationEvent.getData());
        } finally {
            TraceAdapterUtil.endTraceContext(null);
        }
    }

}
