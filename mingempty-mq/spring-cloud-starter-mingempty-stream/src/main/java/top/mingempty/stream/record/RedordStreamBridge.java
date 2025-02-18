package top.mingempty.stream.record;

import cn.hutool.core.lang.UUID;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.cloud.stream.function.StreamOperations;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeType;
import top.mingempty.stream.model.StreamConstant;

/**
 * 消息发送记录
 *
 * @author zzhao
 */
@AllArgsConstructor
public class RedordStreamBridge implements StreamOperations {


    private final StreamBridge streamBridge;

    @Override
    public boolean send(String bindingName, Object data) {
        return streamBridge.send(bindingName, record(data));
    }

    @Override
    public boolean send(String bindingName, Object data, MimeType outputContentType) {
        return streamBridge.send(bindingName, record(data), outputContentType);
    }

    @Override
    public boolean send(String bindingName, String binderName, Object data) {
        return streamBridge.send(bindingName, binderName, record(data));
    }

    @Override
    public boolean send(String bindingName, String binderName, Object data, MimeType outputContentType) {
        return streamBridge.send(bindingName, binderName, record(data), outputContentType);
    }

    /**
     * 消息头增加记录标识
     *
     * @param object
     * @return
     */
    public Message<?> record(Object object) {
        MessageBuilder<?> messageBuilder;
        if (object instanceof Message<?> message) {
            messageBuilder = MessageBuilder.fromMessage(message);
            if (!message.getHeaders().containsKey(StreamConstant.RECORD_MESSAGE_ID)) {
                messageBuilder.setHeader(StreamConstant.RECORD_MESSAGE_ID, UUID.fastUUID().toString(true));
            }
        } else {
            messageBuilder = MessageBuilder.withPayload(object)
                    .setHeader(StreamConstant.RECORD_MESSAGE_ID, UUID.fastUUID().toString(true));
        }
        return messageBuilder.build();
    }


}
