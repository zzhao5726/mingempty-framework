package top.mingempty.trace.adapter;

import lombok.extern.slf4j.Slf4j;
import top.mingempty.commons.util.JacksonUtil;
import top.mingempty.domain.enums.ParameteTypeEnum;
import top.mingempty.trace.domain.Message;

import java.time.LocalDateTime;

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
        String appName = message.getAppName();
        String appGroup = message.getAppGroup();
        String appVersion = message.getAppVersion();
        String traceId = message.getTraceId();
        String spanId = message.getSpanId();
        String currentThreadName = message.getCurrentThreadName();
        Long currentThreadId = message.getCurrentThreadId();
        String functionName = message.getFunctionName();
        LocalDateTime traceTime = message.getNow();
        int protocolCode = message.getProtocolCode();
        int spanType = message.getSpanType();
        long timeConsuming = message.getTimeConsuming();
        String parameter = JacksonUtil.toStr(message.getParameter());
        switch (message.getParameteTypeEnum()) {
            case ParameteTypeEnum.REQUEST -> {
                log.debug("消息链路记录发送默认实现---appName:[{}],appGroup:[{}],appVersion:[{}],traceId:[{}],spanId:[{}]," +
                                "traceThreadId:[{}],traceThreadName:[{}],traceFunctionName:[{}],traceStartTime:[{}]," +
                                "protocolCode:[{}],spanType:[{}],requestParameter:[{}]",
                        appName, appGroup, appVersion, traceId, spanId, currentThreadId, currentThreadName,
                        functionName, traceTime, protocolCode, spanType, parameter);
            }
            case ParameteTypeEnum.RESPONSE -> {
                log.debug("消息链路记录发送默认实现---appName:[{}],appGroup:[{}],appVersion:[{}],traceId:[{}],spanId:[{}]," +
                                "traceThreadId:[{}],traceThreadName:[{}],traceFunctionName:[{}],traceEndTime:[{}]," +
                                "protocolCode:[{}],spanType:[{}],timeConsuming:[{}],responseParameter:[{}]",
                        appName, appGroup, appVersion, traceId, spanId, currentThreadId, currentThreadName,
                        functionName, traceTime, protocolCode, spanType, timeConsuming, parameter);
            }
        }
    }

}
