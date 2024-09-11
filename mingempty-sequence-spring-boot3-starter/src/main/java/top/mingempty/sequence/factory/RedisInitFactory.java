package top.mingempty.sequence.factory;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import top.mingempty.cache.redis.entity.wapper.RedisTemplateWrapper;
import top.mingempty.sequence.api.impl.base.RedisSequence;
import top.mingempty.sequence.api.impl.base.SnowflakeIdWorkerSequence;
import top.mingempty.sequence.api.impl.wrapper.RedisSequenceWrapper;
import top.mingempty.sequence.enums.SeqRealizeEnum;
import top.mingempty.sequence.exception.SequenceException;
import top.mingempty.sequence.model.CacheSequenceProperties;
import top.mingempty.util.SpringContextUtil;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 执行序号对redis相关的初始化
 */
@Slf4j
public class RedisInitFactory implements InitFactory {


    /**
     * 对指定key的值进行累加，并设置过期时间
     */
    private final static String SEQUENCE_SNOWFLAKE_ID_LUA_SCRIPT =
            """
                    local max_id = tonumber(ARGV[1])
                    local new_key_prefix = KEYS[2]
                    
                    local function generate_unique_key()
                      local attempts = 0
                      repeat
                        attempts = attempts + 1
                        local current_id = redis.call('INCR', KEYS[1]) - 1
                        if current_id >= max_id then
                          redis.call('SET', KEYS[1], 0)
                          current_id = 0
                        end
                        local new_key = new_key_prefix .. current_id
                    
                        -- 使用 SETNX 来判断 Key 是否存在并设置值
                        if redis.call('SETNX', new_key, '') == 1 then
                          redis.call('EXPIRE', new_key, 300)
                          return current_id
                        end
                      until attempts >= max_id
                      return -1
                    end
                    
                    return generate_unique_key()
                    """;

    private final static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);

    private final RedisTemplateWrapper redisTemplateWrapper;

    public RedisInitFactory() {
        this.redisTemplateWrapper = SpringContextUtil.gainBean("redisTemplateWrapper", RedisTemplateWrapper.class);
    }

    /**
     * 初始化类型
     */
    @Override
    public SeqRealizeEnum setRealize() {
        return SeqRealizeEnum.Redis;
    }

    /**
     * 初始化雪花算法
     *
     * @param instanceNames 实例名称集合
     */
    @Override
    public void snowflakeIdWorker(Set<String> instanceNames) {
        if (CollUtil.isEmpty(instanceNames)) {
            return;
        }

        String keyPrefix = SeqRealizeEnum.Redis.getKeyPrefix().concat("SnowflakeId").concat(SeqRealizeEnum.Redis.getSeparator());
        String allWorkFolder = "{".concat(keyPrefix).concat("all}");
        String nowWorkFolder = allWorkFolder.concat(SeqRealizeEnum.Redis.getSeparator());

        instanceNames.forEach(instanceName -> {
            RedisOperations<String, Object> redisOperations = redisTemplateWrapper.getResolvedRouter(instanceName);
            Assert.notNull(redisOperations, "redisOperations is null");

            DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>();
            defaultRedisScript.setResultType(Long.class);
            defaultRedisScript.setScriptText(SEQUENCE_SNOWFLAKE_ID_LUA_SCRIPT);
            int maxId = 1 << (SnowflakeIdWorkerSequence.DATA_CENTER_ID_BITS + SnowflakeIdWorkerSequence.WORKER_ID_BITS);
            Long baseId = redisOperations.execute(defaultRedisScript,
                    List.of(allWorkFolder, nowWorkFolder), maxId);
            if (baseId == null
                    || baseId == -1) {
                throw new SequenceException("seq00000002", SeqRealizeEnum.Redis, instanceName);
            }
            SnowflakeIdWorkerSequence.SnowflakeIdWorkerHolder.init(SeqRealizeEnum.Redis, instanceName, baseId.intValue());

            //设置定时任务，每隔两分半重新续期
            scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
                        if (log.isDebugEnabled()) {
                            log.debug("雪花算法baseId[{}]续期开始", baseId);
                        }
                        redisOperations
                                .expire(nowWorkFolder.concat(String.valueOf(baseId)), 60 * 5, TimeUnit.SECONDS);
                        if (log.isDebugEnabled()) {
                            log.debug("雪花算法baseId[{}]续期完成", baseId);
                        }
                    },
                    60 * 5 / 2, 60 * 5 / 2, TimeUnit.SECONDS);

        });
    }

    /**
     * 初始化缓存序号
     *
     * @param cacheSequenceProperties 缓存序号配置
     */
    @Override
    public void cacheSequence(CacheSequenceProperties cacheSequenceProperties) {
        if (cacheSequenceProperties.isExpirationStrategyInit()) {
            expirationStrategySequence(cacheSequenceProperties);
            return;
        }
        RedisOperations<String, Object> redisOperations
                = redisTemplateWrapper.getResolvedRouter(cacheSequenceProperties.getInstanceName());
        RedisSequence.RedisSequenceHolder.init(cacheSequenceProperties.getInstanceName(),
                cacheSequenceProperties.getSeqName(), cacheSequenceProperties.getStep(),
                cacheSequenceProperties.getExpirySeconds(),
                redisOperations);
    }

    /**
     * 初始化包装序号
     *
     * @param cacheSequenceProperties 缓存序号配置
     */
    @Override
    public void expirationStrategySequence(CacheSequenceProperties cacheSequenceProperties) {
        RedisOperations<String, Object> redisOperations
                = redisTemplateWrapper.getResolvedRouter(cacheSequenceProperties.getInstanceName());
        RedisSequenceWrapper.RedisSequenceHolder.init(cacheSequenceProperties.getInstanceName(),
                cacheSequenceProperties.getSeqName(),
                cacheSequenceProperties.getStep(),
                cacheSequenceProperties.isExpirationDelete(),
                cacheSequenceProperties.getExpirationStrategy(),
                redisOperations);
    }
}
