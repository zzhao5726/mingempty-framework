package top.mingempty.stream.record.service;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

/**
 * mq记录服务接口
 */
public interface MqRecordService {

    /**
     * 消息记录
     *
     * @param recordType
     * @param message
     * @param channel
     */
    Message<?> record(String recordType, Message<?> message, MessageChannel channel);


    /**
     * 完成修改记录
     *
     * @param mqId
     */
    void complete(String mqId);

    /**
     * 重试任务
     *
     * @param mqId
     */
    void retry(String mqId);
}
