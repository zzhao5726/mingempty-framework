package top.mingempty.cache.redis.api;

import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonRxClient;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import top.mingempty.cache.commons.api.CacheApi;
import top.mingempty.domain.other.GlobalConstant;

import java.util.Set;

/**
 * Redis缓存API
 *
 * @author zzhao
 */
public interface RedisCacheApi extends RedisCacheMap, RedisCacheSet, RedisCacheZSet, RedisCacheGeo, CacheApi {

    /**
     * 获取CacheManagerWrapper
     *
     * @return CacheManager
     */
    default CacheManager cacheManager() {
        return cacheManagerForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 获取CacheManagerWrapper
     *
     * @return CacheManager
     */
    CacheManager cacheManagerForInstance(String instanceId);

    /**
     * 获取RedissonClientWrapper
     *
     * @return RedissonClient
     */
    default RedissonClient redissonClient() {
        return redissonClientForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 获取RedissonClientWrapper
     *
     * @return RedissonClient
     */
    RedissonClient redissonClientForInstance(String instanceId);

    /**
     * 获取RedissonRxClientWrapper
     *
     * @return RedissonRxClient
     */
    default RedissonRxClient redissonRxClient() {
        return redissonRxClientForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 获取RedissonRxClientWrapper
     *
     * @return RedissonRxClient
     */
    RedissonRxClient redissonRxClientForInstance(String instanceId);

    /**
     * 获取RedisTemplate
     *
     * @return RedisOperations
     */
    default RedisOperations<String, Object> redisOperations() {
        return redisOperationsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 获取RedisTemplate
     *
     * @return RedisOperations
     */
    RedisOperations<String, Object> redisOperationsForInstance(String instanceId);

    /**
     * 获取RedisTemplate
     *
     * @return RedisTemplate
     */
    RedisTemplate<String, Object> redisTemplate();


    /**
     * 在指定实例中根据前缀删除所有相关的缓存数据，使用扫描操作。
     *
     * @param instanceId 实例ID
     * @param prefix     要删除的前缀
     * @return 操作是否成功
     */
    @Override
    default Boolean delByPrefixUseScanForInstance(String instanceId, String prefix) {
        return delByPatternForInstance(instanceId, prefix);
    }
    /**
     * 扫描指定格式的键
     *
     * @param options 格式
     * @return 键集合
     */
    default Set<String> scan(ScanOptions options) {
        return scanForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, options);
    }

    /**
     * 在指定实例中扫描指定格式的键
     *
     * @param instanceId 实例ID
     * @param pattern    格式
     * @return 键集合
     */
    @Override
    default Set<String> scanForInstance(String instanceId, String pattern) {
        if (pattern == null) {
            pattern = "*";
        }
        if (!pattern.startsWith("*")) {
            pattern = "*".concat(pattern);
        }
        if (!pattern.endsWith("*")) {
            pattern = pattern.concat("*");
        }
        return scanForInstance(instanceId, ScanOptions.scanOptions().match(pattern).build());
    }

    /**
     * 在指定实例中扫描指定格式的键
     *
     * @param instanceId 实例ID
     * @param options    格式
     * @return 键集合
     */
    Set<String> scanForInstance(String instanceId, ScanOptions options);

}
