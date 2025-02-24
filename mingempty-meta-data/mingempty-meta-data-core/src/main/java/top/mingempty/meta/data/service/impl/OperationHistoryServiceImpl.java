package top.mingempty.meta.data.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mingempty.cache.redis.api.RedisCacheApi;
import top.mingempty.meta.data.mapper.OperationHistoryMapper;
import top.mingempty.meta.data.model.enums.DictOperationEnum;
import top.mingempty.meta.data.model.enums.RedisCacheKeyEnum;
import top.mingempty.meta.data.model.po.OperationHistoryPo;
import top.mingempty.meta.data.service.OperationHistoryService;
import top.mingempty.meta.data.util.IDUtil;
import top.mingempty.mybatis.tool.AuditOperatorTool;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 字典操作历史表 服务层实现。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Service
public class OperationHistoryServiceImpl extends ServiceImpl<OperationHistoryMapper, OperationHistoryPo> implements OperationHistoryService {

    /**
     * 生成版本的序号控制器
     */
    private static final Map<String, RAtomicLong> GAIN_VERSION_MAP = new ConcurrentHashMap<>();

    @Autowired
    private RedisCacheApi redisCacheApi;

    @Override
    @SneakyThrows
    public Long gainVersion(String entryCode) {
        RedissonClient redissonClient = redisCacheApi.redissonClient();
        RLock lock = redissonClient.getLock(RedisCacheKeyEnum.CHANGE_LOCK.gainKey(entryCode));
        try {
            if (lock.tryLock(1, 1, TimeUnit.MINUTES)) {
                RAtomicLong atomicLong = GAIN_VERSION_MAP.computeIfAbsent(entryCode,
                        key
                                -> expireWithMaxVersion(redissonClient
                                .getAtomicLong(RedisCacheKeyEnum.CHANGE_VERSION.gainKey(entryCode))));
                long version = atomicLong.incrementAndGet();
                if (version > 1) {
                    return version;
                }
                //说明是第一次，或者是缓存不存在了
                //数据库查询最大版本
                Long maxVersion = this.mapper.gainMaxVersion(entryCode);
                //设置缓存
                atomicLong.set(maxVersion + 1);
                expireWithMaxVersion(atomicLong);
                return maxVersion + 1;
            }
            return 0L;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 设置获取条目版本的key的过期时间
     *
     * @param atomicLong
     * @return
     */
    private static RAtomicLong expireWithMaxVersion(RAtomicLong atomicLong) {
        //设置过期时间
        atomicLong.expireAsync(Duration.of(30, ChronoUnit.MINUTES));
        return atomicLong;
    }

    @Override
    public void record(Map<String, Long> entryVersionMap, DictOperationEnum dictOperationType) {
        Long batchId = IDUtil.gainId();
        List<OperationHistoryPo> operationHistoryPos = entryVersionMap.entrySet()
                .parallelStream()
                .map(entry -> OperationHistoryPo.builder()
                        .operationHistoryId(IDUtil.gainId())
                        .entryCode(entry.getKey())
                        .operationType(dictOperationType.getItemCode())
                        .entryVersion(entry.getValue())
                        .operatorCode(AuditOperatorTool.gainAuditOperator())
                        .operationTime(LocalDateTime.now())
                        .batchId(batchId)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        this.saveBatch(operationHistoryPos);
    }
}
