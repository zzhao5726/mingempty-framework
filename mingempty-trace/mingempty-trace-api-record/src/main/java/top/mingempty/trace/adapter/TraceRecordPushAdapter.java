package top.mingempty.trace.adapter;

import top.mingempty.trace.domain.Message;

/**
 * 链路追踪适配器
 *
 * @author zzhao
 */
public interface TraceRecordPushAdapter {

    /**
     * 将链路日志发送出去
     *
     * @param message 链路信息
     */
    void push(Message message);

}
