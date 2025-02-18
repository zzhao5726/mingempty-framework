package top.mingempty.stream.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.stream.interceptor.MeTraceChannelInterceptor;
import top.mingempty.trace.util.TraceAdapterUtil;

import java.util.function.Consumer;


@Slf4j
public abstract class MeAbstractListener<D> implements Consumer<Message<D>> {

    @Override
    @Transactional
    public void accept(Message<D> message) {
        Message<D> beforeMessage = null;
        boolean traceInit = false;
        try {
            if (!TraceAdapterUtil.initialized()) {
                MeTraceChannelInterceptor.initTrace(message);
                traceInit = true;
            }
            beforeMessage = before(message);
            onListener(beforeMessage, beforeMessage.getPayload());
        } catch (Exception e) {
            exception(beforeMessage, e);
        } finally {
            try {
                after(beforeMessage == null ? message : beforeMessage);
            } finally {
                if (traceInit) {
                    log.debug("清空链路ID");
                    TraceAdapterUtil.endTraceContext();
                }
            }
        }
    }

    public Message<D> before(Message<D> message) {
        return message;
    }

    public abstract void onListener(Message<D> message, D data);

    public void exception(Message<D> message, Exception e) {

    }

    public void after(Message<D> message) {

    }

}
