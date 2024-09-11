package top.mingempty.sequence.api.impl.base;

import lombok.extern.slf4j.Slf4j;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.sequence.api.Sequence;
import top.mingempty.sequence.enums.SeqRealizeEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The class Snowflake id worker.
 * 推特的snowflake算法实现类
 *
 * <p>
 * Twitter_Snowflake
 * SnowFlake的结构如下(每部分用-分开):
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0
 * 41位时间截(毫秒级)，注意，39位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的startTime属性）。
 * 41位的时间截，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1023个节点，包括5位datacenterId和5位workerId
 * 12位序列，毫秒内的计数，10位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号
 * 加起来刚好64位，为一个Long型。
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高。
 *
 * @author zzhao
 */
@Slf4j
public class SnowflakeIdWorkerSequence implements Sequence<Long> {

    /**
     * 开始时间截 (2024-09-12 00:00:00)
     */
    public static final long TWEPOCH = 1725984000000L;
    /**
     * 节点内机器id所占的位数
     */
    public static final long WORKER_ID_BITS = 5L;
    /**
     * 节点id所占的位数
     */
    public static final long DATA_CENTER_ID_BITS = 10 - WORKER_ID_BITS;
    /**
     * 序列在id中占的位数
     */
    public static final long SEQUENCE_BITS = 12L;
    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    public static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

    // ==============================Fields===========================================
    /**
     * 支持的最大数据标识id，结果是31
     */
    public static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    /**
     * 机器ID向左移数据库表和毫秒内序列所占的位数和
     */
    public static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    /**
     * 数据标识id基于机器id再次左移机器id所占位数
     */
    public static final long DATA_CENTER_ID_SHIFT = WORKER_ID_BITS + WORKER_ID_SHIFT;
    /**
     * 时间截基于数据标志id左移数据标志id所占位数
     */
    public static final long TIMESTAMP_LEFT_SHIFT = DATA_CENTER_ID_BITS + DATA_CENTER_ID_SHIFT;
    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    public static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    /**
     * 工作机器ID(0~31)
     */
    private final long workerId;
    /**
     * 数据中心ID(0~31)
     */
    private final long dataCenterId;
    /**
     * 毫秒内序列(0~4095)
     */
    private volatile long sequence = 0L;
    /**
     * 上次生成ID的时间截
     */
    private volatile long lastTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param baseId 基础ID (0~1023)
     */
    private SnowflakeIdWorkerSequence(int baseId) {
        this(baseId & MAX_WORKER_ID, baseId >> WORKER_ID_BITS & MAX_DATA_CENTER_ID);
    }

    /**
     * 构造函数
     *
     * @param workerId     工作ID (0~31)
     * @param dataCenterId 数据中心ID (0~31)
     */
    private SnowflakeIdWorkerSequence(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("data center Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    private synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate appId for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (dataCenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }


    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 下一个序号
     */
    @Override
    public Long next() {
        return nextId();
    }


    public static SnowflakeIdWorkerSequence gainInstance(SeqRealizeEnum seqRealize) {
        return gainInstance(seqRealize, GlobalConstant.DEFAULT_INSTANCE_NAME);
    }


    public static SnowflakeIdWorkerSequence gainInstance(SeqRealizeEnum seqRealize, String instanceName) {
        return SnowflakeIdWorkerHolder.SNOWFLAKE_ID_WORKER_MAP.get(seqRealize).get(instanceName);
    }


    public static class SnowflakeIdWorkerHolder {


        private static final Map<SeqRealizeEnum, Map<String, SnowflakeIdWorkerSequence>> SNOWFLAKE_ID_WORKER_MAP = new ConcurrentHashMap<>() {{
            this.put(SeqRealizeEnum.Redis, new ConcurrentHashMap<>(0));
            this.put(SeqRealizeEnum.Database, new ConcurrentHashMap<>(0));
            this.put(SeqRealizeEnum.Zookeeper, new ConcurrentHashMap<>(0));
        }};

        public static void init(SeqRealizeEnum seqRealize, int baseId) {
            init(seqRealize, GlobalConstant.DEFAULT_INSTANCE_NAME, baseId);
        }

        public static synchronized void init(SeqRealizeEnum seqRealize, String instanceName, int baseId) {
            init(seqRealize, instanceName, baseId & MAX_WORKER_ID, baseId >> WORKER_ID_BITS & MAX_DATA_CENTER_ID);
        }

        public static void init(SeqRealizeEnum seqRealize, long workerId, long dataCenterId) {
            init(seqRealize, GlobalConstant.DEFAULT_INSTANCE_NAME, workerId, dataCenterId);
        }

        public static synchronized void init(SeqRealizeEnum seqRealize, String instanceName, long workerId, long dataCenterId) {
            if (SNOWFLAKE_ID_WORKER_MAP.get(seqRealize).containsKey(instanceName)) {
                log.error("SnowflakeIdWorkerSequence has init!!!!!!!");
                return;
            }
            SNOWFLAKE_ID_WORKER_MAP.get(seqRealize).computeIfAbsent(instanceName, k -> new SnowflakeIdWorkerSequence(workerId, dataCenterId));
        }

        public static void reInit(SeqRealizeEnum seqRealize, int baseId) {
            reInit(seqRealize, GlobalConstant.DEFAULT_INSTANCE_NAME, baseId);
        }

        public static synchronized void reInit(SeqRealizeEnum seqRealize, String instanceName, int baseId) {
            reInit(seqRealize, instanceName, baseId & MAX_WORKER_ID, baseId >> WORKER_ID_BITS & MAX_DATA_CENTER_ID);
        }

        public static void reInit(SeqRealizeEnum seqRealize, long workerId, long dataCenterId) {
            reInit(seqRealize, GlobalConstant.DEFAULT_INSTANCE_NAME, workerId, dataCenterId);
        }

        public static synchronized void reInit(SeqRealizeEnum seqRealize, String instanceName, long workerId, long dataCenterId) {
            SNOWFLAKE_ID_WORKER_MAP.get(seqRealize).put(instanceName, new SnowflakeIdWorkerSequence(workerId, dataCenterId));
        }

    }


}