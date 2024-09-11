package top.mingempty.sequence.api.impl.base;

import cn.hutool.core.lang.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.sequence.api.impl.AbstractCacheSequence;
import top.mingempty.sequence.enums.SeqRealizeEnum;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于redis实现带缓存的序号生成
 *
 * @author zzhao
 */
@Slf4j
public class RedisSequence extends AbstractCacheSequence {

    /**
     * 对指定key的值进行累加，并设置过期时间
     */
    private final static String SEQUENCE_SERIAL_EVERY_DAY_LUA_SCRIPT =
            """
                    local result = redis.call('incrby', KEYS[1], ARGV[1])
                    local resultNumber = tonumber(result)
                    if resultNumber == tonumber(ARGV[1]) and tonumber(ARGV[2]) ~= -1 then
                        redis.call('EXPIRE', KEYS[1], ARGV[2])
                    end
                    return resultNumber
                    """;

    private final RedisOperations<String, Object> redisOperations;

    /**
     * 缓存失效时间
     * 单位：秒
     * <p>
     * 值为-1时，表示缓存不过期
     */
    private final long expirySeconds;


    private RedisSequence(String seqName, RedisOperations<String, Object> redisOperations) {
        this(seqName, -1L, redisOperations);
    }

    private RedisSequence(String seqName, int step, RedisOperations<String, Object> redisOperations) {
        this(seqName, step, -1L, redisOperations);
    }

    private RedisSequence(String seqName, long expirySeconds, RedisOperations<String, Object> redisOperations) {
        this(seqName, 50, expirySeconds, redisOperations);
    }

    private RedisSequence(String seqName, int step, long expirySeconds, RedisOperations<String, Object> redisOperations) {
        super(SeqRealizeEnum.Redis, seqName, step);
        this.redisOperations = redisOperations;
        this.expirySeconds = expirySeconds;
    }

    /**
     * 获取新的最大值和步长
     * <p>
     * key：获取到的最大值
     * value：步长
     */
    @Override
    protected final Pair<Long, Integer> max() {
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
        defaultRedisScript.setResultType(Long.class);
        defaultRedisScript.setScriptText(SEQUENCE_SERIAL_EVERY_DAY_LUA_SCRIPT);
        Long maxSequence = redisOperations.execute(defaultRedisScript,
                List.of(key()),
                step(), expirySeconds);
        return Pair.of(maxSequence, step());
    }

    /**
     * 序号实现机制
     */
    @Override
    public SeqRealizeEnum seqRealize() {
        return SeqRealizeEnum.Redis;
    }

    public static RedisSequence gainInstance(String seqName) {
        return gainInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName);
    }


    public static RedisSequence gainInstance(String instanceName, String seqName) {
        return RedisSequenceHolder.REDIS_SEQUENCE_MAP.computeIfAbsent(instanceName, k -> new ConcurrentHashMap<>(0)).get(seqName);
    }

    public static class RedisSequenceHolder {

        private static final Map<String, Map<String, RedisSequence>> REDIS_SEQUENCE_MAP = new ConcurrentHashMap<>();

        public static void init(String seqName, RedisOperations<String, Object> redisOperations) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, redisOperations);
        }

        public static void init(String seqName, int step, RedisOperations<String, Object> redisOperations) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, step, redisOperations);
        }

        public static void init(String seqName, int step, long expirySeconds, RedisOperations<String, Object> redisOperations) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, step, expirySeconds, redisOperations);
        }

        public static void init(String instanceName, String seqName, RedisOperations<String, Object> redisOperations) {
            init(instanceName, seqName, 50, redisOperations);
        }

        public static void init(String instanceName, String seqName, int step, RedisOperations<String, Object> redisOperations) {
            init(instanceName, seqName, step, -1L, redisOperations);
        }

        public static void init(String instanceName, String seqName, int step, long expirySeconds, RedisOperations<String, Object> redisOperations) {
            if (REDIS_SEQUENCE_MAP.containsKey(instanceName)
                    && REDIS_SEQUENCE_MAP.get(instanceName).containsKey(seqName)) {
                log.error("RedisSequence has init!!!!!!!");
                return;
            }
            REDIS_SEQUENCE_MAP.computeIfAbsent(instanceName, k -> new ConcurrentHashMap<>(0))
                    .computeIfAbsent(seqName, k -> new RedisSequence(seqName, step, expirySeconds, redisOperations));
        }
    }
}
