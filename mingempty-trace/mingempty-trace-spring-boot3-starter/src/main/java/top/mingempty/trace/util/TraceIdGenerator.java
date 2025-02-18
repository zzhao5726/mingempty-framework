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

    private static final int INITIAL_SEQUENCE = 1000;
    private static final AtomicInteger SEQUENCE = new AtomicInteger(INITIAL_SEQUENCE - 1);
    private static final int MAX_SEQUENCE = 9999;
    private static final String PROCESS_ID_STR = String.format("%05d", ProcessUtil.processId());
    private static final String IP_HEX = IpUtils.ipv4ToHex();

    /**
     * 生成traceId
     *
     * @return traceId
     */
    public static String generateTraceId() {
        return generateTraceId(TraceContext.gainTraceContext());
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
    private static int getNextSequence() {
        int current;
        int next;
        do {
            current = SEQUENCE.get();
            next = current >= MAX_SEQUENCE ? INITIAL_SEQUENCE : current + 1;
        } while (!SEQUENCE.compareAndSet(current, next));
        return next;
    }


    /**
     * 生成spanId
     *
     * @return traceId
     */
    public static String generateSpanId() {
        return generateSpanId(TraceContext.gainTraceContext());
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
