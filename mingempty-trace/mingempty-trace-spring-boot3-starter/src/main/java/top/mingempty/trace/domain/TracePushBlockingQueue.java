package top.mingempty.trace.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import top.mingempty.cloud.util.SpringContextUtil;
import top.mingempty.domain.enums.ParameteTypeEnum;
import top.mingempty.trace.adapter.TraceRecordPushAdapter;
import top.mingempty.trace.constants.ProtocolEnum;
import top.mingempty.trace.constants.SpanTypeEnum;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 日志队列
 *
 * @author zzhao
 */
@Slf4j
@AllArgsConstructor
public class TracePushBlockingQueue implements InitializingBean {

    /**
     * 消息队列
     */
    private static final LinkedBlockingDeque<Message> MESSAGE_BLOCKING_QUEUE = new LinkedBlockingDeque<>();

    /**
     * 链路追踪适配器
     */
    @Schema(title = "链路追踪适配器")
    private final TraceRecordPushAdapter traceRecordPushAdapter;

    /**
     * 项目名称
     */
    @Schema(title = "项目名称")
    private final String appName;

    /**
     * 项目分组
     */
    @Schema(title = "项目分组")
    private final String appGroup;

    /**
     * 项目版本
     */
    @Schema(title = "项目版本")
    private final String appVersion;


    /**
     * 获取自身的Bean用于静态赋值
     */
    private static TracePushBlockingQueue TRACE_PUSH_BLOCKING_QUEUE;


    /**
     * 接受链路数据，并做转换
     *
     * @param traceContext
     * @param parameteTypeEnum
     * @param parameter
     */
    public static void offer(TraceContext traceContext, ParameteTypeEnum parameteTypeEnum, Object parameter) {
        if (traceContext == null) {
            return;
        }
        String traceId = traceContext.getTraceId();
        String spanId = traceContext.getSpanId();
        String currentThreadName = traceContext.getCurrentThreadName();
        Long currentThreadId = traceContext.getCurrentThreadId();
        String functionName = traceContext.getFunctionName();
        ProtocolEnum protocolEnum = traceContext.getProtocolEnum();
        SpanTypeEnum spanTypeEnum = traceContext.getSpanTypeEnum();
        long timeConsuming = traceContext.timeConsuming();
        Message.MessageBuilder messageBuilder = Message.builder()
                .currentThreadId(currentThreadId)
                .currentThreadName(currentThreadName)
                .traceId(traceId)
                .spanId(spanId)
                .functionName(functionName)
                .protocolCode(protocolEnum.getCode())
                .spanType(spanTypeEnum.getType())
                .parameter(parameter)
                .parameteTypeEnum(parameteTypeEnum)
                .timeConsuming(timeConsuming);
        initApplication(messageBuilder);
        MESSAGE_BLOCKING_QUEUE.offer(messageBuilder.build());
    }


    /**
     * 阻塞获取队列
     *
     * @return
     */
    public static Message takeFirst() {
        try {
            return MESSAGE_BLOCKING_QUEUE.takeFirst();
        } catch (InterruptedException e) {
            log.error("链路队列等待出队中断", e);
            return null;
        }
    }

    private static void initApplication(Message.MessageBuilder messageBuilder) {
        if (messageBuilder == null) {
            return;
        }
        TracePushBlockingQueue tracePushBlockingQueue = getTracePushBlockingQueue();
        if (tracePushBlockingQueue == null) {
            return;
        }

        messageBuilder.appName(tracePushBlockingQueue.appName)
                .appVersion(tracePushBlockingQueue.appVersion)
                .appGroup(tracePushBlockingQueue.appGroup);

    }

    public static TracePushBlockingQueue getTracePushBlockingQueue() {
        if (TRACE_PUSH_BLOCKING_QUEUE != null) {
            return TRACE_PUSH_BLOCKING_QUEUE;
        }

        synchronized (TracePushBlockingQueue.class) {
            if (TRACE_PUSH_BLOCKING_QUEUE != null) {
                return TRACE_PUSH_BLOCKING_QUEUE;
            }
            TRACE_PUSH_BLOCKING_QUEUE = SpringContextUtil.getBean(TracePushBlockingQueue.class);
        }
        return TRACE_PUSH_BLOCKING_QUEUE;
    }

    /**
     * Invoked by the containing {@code BeanFactory} after it has set all bean properties
     * and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
     * <p>This method allows the bean instance to perform validation of its overall
     * configuration and final initialization when all bean properties have been set.
     *
     * @throws Exception in the event of misconfiguration (such as failure to set an
     *                   essential property) or if initialization fails for any other reason
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化后开始消费
        for (int i = 0; i < Runtime.getRuntime().availableProcessors() / 2; i++) {
            new Thread(() -> {
                while (true) {
                    traceRecordPushAdapter.push(takeFirst());
                }
            }, String.format("TracePush-%d", i))
                    .start();
        }
        TRACE_PUSH_BLOCKING_QUEUE = this;
    }
}
