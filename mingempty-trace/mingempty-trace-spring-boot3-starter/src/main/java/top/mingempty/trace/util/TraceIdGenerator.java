package top.mingempty.trace.util;

import top.mingempty.commons.trace.TraceContext;
import top.mingempty.commons.util.IpUtils;
import top.mingempty.commons.util.ProcessUtil;

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
        return generateTraceId(TraceAdapterUtil.gainTraceContext());
    }

    /**
     * 生成traceId
     *
     * @return traceId
     */
    public static String generateTraceId(TraceContext traceContext) {
        if (traceContext != null) {
            return traceContext.getTraceId();
        }

        return String.format("%013d", Instant.now().toEpochMilli())
                .concat(PROCESS_ID_STR + IP_HEX)
                .concat(String.format("%04d", getNextSequence()));
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


    /**
     * 生成spanId
     *
     * @return traceId
     */
    public static String generateSpanId() {
        return generateSpanId(TraceAdapterUtil.gainTraceContext());
    }


    /**
     * 生成spanId
     *
     * @return traceId
     */
    public static String generateSpanId(TraceContext traceContext) {
        //如果没有就说明是跟节点
        if (traceContext == null) {
            return "1";
        }
        //获取下一个节点的ID
        return traceContext.getSpanId()
                .concat(".")
                .concat(String.valueOf(traceContext.spanCountGetAndIncrement()));
    }


}
