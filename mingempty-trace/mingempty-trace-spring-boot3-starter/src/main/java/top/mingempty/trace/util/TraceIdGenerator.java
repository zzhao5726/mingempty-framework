package top.mingempty.trace.util;

import top.mingempty.commons.util.IpUtils;
import top.mingempty.commons.util.ProcessUtil;
import top.mingempty.trace.domain.TraceContext;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zzhao
 */
public class TraceIdGenerator {

    private static final AtomicInteger SEQUENCE = new AtomicInteger(0);
    private static final int MAX_SEQUENCE = 9999;
    private static final String PROCESS_ID_STR = String.format("%05d", ProcessUtil.processId());
    private static final String IP_HEX = IpUtils.ipv4ToHex();

    /**
     * 生成traceId
     *
     * @return traceId
     */
    public static String generateTraceId() {
        String timestamp = String.format("%013d", Instant.now().toEpochMilli());
        String sequenceStr = String.format("%04d", getNextSequence());
        return timestamp + PROCESS_ID_STR + IP_HEX + sequenceStr;
    }


    /**
     * 生成spanId
     *
     * @return traceId
     */
    public static String generateSpanId() {
        return generateSpanId(TraceAdapterUtil.getTraceContext());
    }


    /**
     * 生成spanId
     *
     * @return traceId
     */
    public static String generateSpanId(TraceContext traceContext) {
        //如果没有就说明是跟节点
        if (traceContext == null) {
            return "0";
        }
        //获取下一个节点的ID
        return traceContext.getSpanId()
                .concat(".")
                .concat(String.valueOf(traceContext.spanCountGetAndIncrement()));
    }

    /**
     * 获取traceId的自增序列
     *
     * @return
     */
    private synchronized static int getNextSequence() {
        int currentSeq = SEQUENCE.getAndIncrement();
        if (currentSeq >= MAX_SEQUENCE) {
            SEQUENCE.set(1000);
            currentSeq = SEQUENCE.getAndIncrement();
        }
        return currentSeq;
    }


}
