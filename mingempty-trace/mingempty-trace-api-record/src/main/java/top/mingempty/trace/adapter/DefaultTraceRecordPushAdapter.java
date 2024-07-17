package top.mingempty.trace.adapter;

import lombok.extern.slf4j.Slf4j;
import top.mingempty.commons.util.JacksonUtil;
import top.mingempty.domain.enums.ParameteTypeEnum;
import top.mingempty.trace.domain.Message;

/**
 * 链路追踪适配器
 *
 * @author zzhao
 */
@Slf4j
public class DefaultTraceRecordPushAdapter implements TraceRecordPushAdapter {

    /**
     * 将链路日志发送出去
     *
     * @param message 链路信息
     */
    @Override
    public void push(Message message) {
        if (message == null) {
            return;
        }

        switch (message.getParameteTypeEnum()) {
            case ParameteTypeEnum.REQUEST -> {
                log.debug("trace start：[{}]", JacksonUtil.toStr(message));
            }
            case ParameteTypeEnum.RESPONSE -> {
                log.debug("trace end：[{}]", JacksonUtil.toStr(message));
            }
        }

    }

}
