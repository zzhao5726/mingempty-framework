package top.mingempty.stream.interceptor;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.stream.exception.StreamException;
import top.mingempty.stream.interceptor.base.MeAfterReceiveCompletion;
import top.mingempty.stream.interceptor.base.MeAfterSendCompletion;
import top.mingempty.stream.interceptor.base.MePostReceive;
import top.mingempty.stream.interceptor.base.MePostSend;
import top.mingempty.stream.interceptor.base.MePreReceive;
import top.mingempty.stream.interceptor.base.MePreSend;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * {@link ChannelInterceptor} 是 Spring Messaging 模块中用于拦截消息通道行为的接口，
 * 允许开发者在消息发送/接收的各个阶段插入自定义逻辑。以下是各方法的详细说明：
 * <p>
 * 发送流程：preSend → postSend → afterSendCompletion
 * <br>
 * 接收流程：preSend → postSend → afterSendCompletion
 *
 * @author zzhao
 */
@Slf4j
public class MeChannelInterceptor implements ChannelInterceptor {

    private final List<MePreSend> preSends;
    private final List<MePostSend> postSends;
    private final List<MeAfterSendCompletion> afterSendCompletions;
    private final List<MePreReceive> preReceives;
    private final List<MePostReceive> postReceives;
    private final List<MeAfterReceiveCompletion> afterReceiveCompletions;

    /**
     * 构造函数
     *
     * @param streamType              流的类型
     *                                {@link ZeroOrOneEnum#ONE}时表示输入通道，{@link ZeroOrOneEnum#ONE}时表示输出通道
     * @param preSends
     * @param postSends
     * @param afterSendCompletions
     * @param preReceives
     * @param postReceives
     * @param afterReceiveCompletions
     */
    public MeChannelInterceptor(ZeroOrOneEnum streamType,
                                List<MePreSend> preSends,
                                List<MePostSend> postSends,
                                List<MeAfterSendCompletion> afterSendCompletions,
                                List<MePreReceive> preReceives,
                                List<MePostReceive> postReceives,
                                List<MeAfterReceiveCompletion> afterReceiveCompletions) {

        assert streamType != null;
        switch (streamType) {
            case ZERO -> {
                //说明是输出通道
                this.preSends = preSends.parallelStream()
                        .filter(me -> me.getOrder() < 0)
                        .sorted(Comparator.comparingInt(MePreSend::getOrder))
                        .collect(Collectors.toList());
                this.postSends = postSends.parallelStream()
                        .filter(me -> me.getOrder() < 0)
                        .sorted(Comparator.comparingInt(MePostSend::getOrder))
                        .collect(Collectors.toList());
                this.afterSendCompletions = afterSendCompletions.parallelStream()
                        .filter(me -> me.getOrder() < 0)
                        .sorted(Comparator.comparingInt(MeAfterSendCompletion::getOrder))
                        .collect(Collectors.toList());
                this.preReceives = preReceives.parallelStream()
                        .filter(me -> me.getOrder() < 0)
                        .sorted(Comparator.comparingInt(MePreReceive::getOrder))
                        .collect(Collectors.toList());
                this.postReceives = postReceives.parallelStream()
                        .filter(me -> me.getOrder() < 0)
                        .sorted(Comparator.comparingInt(MePostReceive::getOrder))
                        .collect(Collectors.toList());
                this.afterReceiveCompletions = afterReceiveCompletions.parallelStream()
                        .filter(me -> me.getOrder() < 0)
                        .sorted(Comparator.comparingInt(MeAfterReceiveCompletion::getOrder))
                        .collect(Collectors.toList());
                return;
            }
            case ONE -> {
                //说明是输入通道
                this.preSends = preSends.parallelStream()
                        .filter(me -> me.getOrder() >= 0)
                        .sorted(Comparator.comparingInt(MePreSend::getOrder))
                        .collect(Collectors.toList());
                this.postSends = postSends.parallelStream()
                        .filter(me -> me.getOrder() >= 0)
                        .sorted(Comparator.comparingInt(MePostSend::getOrder))
                        .collect(Collectors.toList());
                this.afterSendCompletions = afterSendCompletions.parallelStream()
                        .filter(me -> me.getOrder() >= 0)
                        .sorted(Comparator.comparingInt(MeAfterSendCompletion::getOrder))
                        .collect(Collectors.toList());
                this.preReceives = preReceives.parallelStream()
                        .filter(me -> me.getOrder() >= 0)
                        .sorted(Comparator.comparingInt(MePreReceive::getOrder))
                        .collect(Collectors.toList());
                this.postReceives = postReceives.parallelStream()
                        .filter(me -> me.getOrder() >= 0)
                        .sorted(Comparator.comparingInt(MePostReceive::getOrder))
                        .collect(Collectors.toList());
                this.afterReceiveCompletions = afterReceiveCompletions.parallelStream()
                        .filter(me -> me.getOrder() >= 0)
                        .sorted(Comparator.comparingInt(MeAfterReceiveCompletion::getOrder))
                        .collect(Collectors.toList());
                return;
            }
        }
        throw new StreamException("stream-0000000001");
    }

    /**
     * 在消息发送到通道前调用，可修改消息或取消发送。
     *
     * @param message 待发送的消息
     * @param channel 目标消息通道
     * @return 修改后的消息（返回新对象），返回 {@code null} 将取消发送
     * @apiNote 典型场景：添加消息头、权限校验
     * <pre>{@code
     * public Message<?> preSend(Message<?> message, MessageChannel channel) {
     *     return MessageBuilder.fromMessage(message)
     *         .setHeader("traceId", UUID.randomUUID().toString())
     *         .build();
     * }
     * }</pre>
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        if (CollUtil.isEmpty(preSends)) {
            return message;
        }
        AtomicReference<Message<?>> messageAtomicReference = new AtomicReference<>(message);
        preSends.forEach(preSend -> {
            if (messageAtomicReference.get() == null) {
                messageAtomicReference.set(null);
                return;
            }
            messageAtomicReference.set(preSend.preSend(messageAtomicReference.get(), channel));
        });
        return messageAtomicReference.get();
    }

    /**
     * 在消息发送到通道后调用（无论成功与否）。
     *
     * @param message 已发送的消息
     * @param channel 目标通道
     * @param sent    是否成功传递到通道
     * @implNote 典型场景：记录发送日志
     * <pre>{@code
     * public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
     *     if (sent) log.info("Sent: {}", message.getPayload());
     * }
     * }</pre>
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        if (CollUtil.isEmpty(postSends)) {
            return;
        }
        postSends.forEach(postSend -> postSend.postSend(message, channel, sent));
    }

    /**
     * 在消息发送完成后调用（无论成功或异常）。
     *
     * @param message 已发送的消息
     * @param channel 目标通道
     * @param sent    是否成功发送
     * @param ex      发送过程中的异常（无异常时为 {@code null}）
     * @implNote 典型场景：资源清理、异常处理
     * <pre>{@code
     * public void afterSendCompletion(Message<?> message, MessageChannel channel,
     *                                boolean sent, Exception ex) {
     *     if (ex != null) log.error("Send failed", ex);
     * }
     * }</pre>
     */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        if (CollUtil.isEmpty(afterSendCompletions)) {
            return;
        }
        afterSendCompletions.forEach(afterSendCompletion
                -> afterSendCompletion.afterSendCompletion(message, channel, sent, ex));
    }

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
    @Override
    public boolean preReceive(MessageChannel channel) {
        if (CollUtil.isEmpty(preReceives)) {
            return true;
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        preReceives.forEach(preReceive -> {
            if (!atomicBoolean.get()) {
                return;
            }
            preReceive.preReceive(channel);
        });
        return atomicBoolean.get();
    }

    /**
     * 在消息接收后、处理前调用，可修改或丢弃消息。
     *
     * @param message 接收到的消息
     * @param channel 来源通道
     * @return 修改后的消息，返回 {@code null} 将丢弃消息
     * @implNote 典型场景：消息解密、内容校验
     * <pre>{@code
     * public Message<?> postReceive(Message<?> message, MessageChannel channel) {
     *     String decrypted = decrypt(message.getPayload());
     *     return MessageBuilder.withPayload(decrypted).build();
     * }
     * }</pre>
     */
    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        if (CollUtil.isEmpty(postReceives)) {
            return message;
        }
        AtomicReference<Message<?>> messageAtomicReference = new AtomicReference<>(message);
        postReceives.forEach(postReceive -> {
            if (messageAtomicReference.get() == null) {
                messageAtomicReference.set(null);
                return;
            }
            messageAtomicReference.set(postReceive.postReceive(messageAtomicReference.get(), channel));
        });
        return messageAtomicReference.get();
    }

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
    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        if (CollUtil.isEmpty(afterReceiveCompletions)) {
            return;
        }
        afterReceiveCompletions.forEach(afterReceiveCompletion
                -> afterReceiveCompletion.afterReceiveCompletion(message, channel, ex));
    }
}
