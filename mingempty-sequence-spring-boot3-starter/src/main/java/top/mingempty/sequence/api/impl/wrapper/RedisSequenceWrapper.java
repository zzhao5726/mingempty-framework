package top.mingempty.sequence.api.impl.wrapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.sequence.api.impl.AbstractExpirationStrategySequence;
import top.mingempty.sequence.api.impl.base.RedisSequence;
import top.mingempty.sequence.enums.ExpirationStrategyEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * redis序号生成器包装
 *
 * @author zzhao
 */
@Slf4j
public class RedisSequenceWrapper extends AbstractExpirationStrategySequence<RedisSequence> {


    private RedisSequenceWrapper(RedisSequence redisSequence, ExpirationStrategyEnum expirationStrategy) {
        super(redisSequence, expirationStrategy);
    }


    public static RedisSequenceWrapper gainInstance(String seqName) {
        return gainInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName);
    }


    public static RedisSequenceWrapper gainInstance(String instanceName, String seqName) {
        return RedisSequenceHolder.REDIS_SEQUENCE_WRAPPER_MAP.computeIfAbsent(instanceName, k -> new ConcurrentHashMap<>(0)).get(seqName);
    }

    public static class RedisSequenceHolder {

        private static final Map<String, Map<String, RedisSequenceWrapper>> REDIS_SEQUENCE_WRAPPER_MAP = new ConcurrentHashMap<>();

        public static void init(String seqName, RedisOperations<String, Object> redisOperations) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, redisOperations);
        }

        public static void init(String seqName, boolean expirationDelete,
                                RedisOperations<String, Object> redisOperations) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, expirationDelete, redisOperations);
        }

        public static void init(String seqName, int step, RedisOperations<String, Object> redisOperations) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, step, redisOperations);
        }

        public static void init(String seqName, int step, boolean expirationDelete,
                                RedisOperations<String, Object> redisOperations) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, step, expirationDelete, redisOperations);
        }

        public static void init(String seqName, int step, ExpirationStrategyEnum expirationStrategy,
                                RedisOperations<String, Object> redisOperations) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, step, expirationStrategy, redisOperations);
        }

        public static void init(String seqName, int step, boolean expirationDelete,
                                ExpirationStrategyEnum expirationStrategy,
                                RedisOperations<String, Object> redisOperations) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, step, expirationDelete, expirationStrategy, redisOperations);
        }

        public static void init(String instanceName, String seqName, RedisOperations<String, Object> redisOperations) {
            init(instanceName, seqName, 50, redisOperations);
        }

        public static void init(String instanceName, String seqName, boolean expirationDelete,
                                RedisOperations<String, Object> redisOperations) {
            init(instanceName, seqName, 50, expirationDelete, redisOperations);
        }

        public static void init(String instanceName, String seqName, int step,
                                RedisOperations<String, Object> redisOperations) {
            init(instanceName, seqName, step, ExpirationStrategyEnum.NONE, redisOperations);
        }

        public static void init(String instanceName, String seqName, int step, boolean expirationDelete,
                                RedisOperations<String, Object> redisOperations) {
            init(instanceName, seqName, step, expirationDelete, ExpirationStrategyEnum.NONE, redisOperations);
        }

        public static void init(String instanceName, String seqName, int step,
                                ExpirationStrategyEnum expirationStrategy,
                                RedisOperations<String, Object> redisOperations) {
            init(instanceName, seqName, step, false, expirationStrategy, redisOperations);
        }

        public static void init(String instanceName, String seqName, int step, boolean expirationDelete,
                                ExpirationStrategyEnum expirationStrategy,
                                RedisOperations<String, Object> redisOperations) {
            if (REDIS_SEQUENCE_WRAPPER_MAP.containsKey(instanceName)) {
                log.error("RedisSequenceWrapper has init!!!!!!!");
                return;
            }
            RedisSequence.RedisSequenceHolder.init(instanceName, seqName, step, expirationDelete ? expirationStrategy.gainSeconds() : -1L, redisOperations);
            RedisSequence redisSequence = RedisSequence.gainInstance(instanceName, seqName);
            REDIS_SEQUENCE_WRAPPER_MAP.computeIfAbsent(instanceName, k -> new ConcurrentHashMap<>(0))
                    .computeIfAbsent(seqName, k -> new RedisSequenceWrapper(redisSequence, expirationStrategy));
        }
    }

}
