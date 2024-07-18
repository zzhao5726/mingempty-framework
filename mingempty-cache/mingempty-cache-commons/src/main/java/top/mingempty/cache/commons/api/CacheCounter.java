package top.mingempty.cache.commons.api;

import top.mingempty.domain.other.GlobalConstant;

import java.util.concurrent.TimeUnit;

/**
 * 计数器接口，用于对缓存中的计数进行操作。
 *
 * @author zzhao
 */
public interface CacheCounter {

    /**
     * 获取当前缓存实例的计数器API
     * <p>
     * 返回当前实例的 {@link CacheCounter} 类型的API。
     * </p>
     *
     * @return 当前缓存实例的计数器缓存集合
     */
    default CacheCounter getCacheCounter() {
        return this;
    }

    /**
     * 获取指定键的计数值。
     *
     * @param key 计数器的键
     * @return 键对应的计数值
     */
    default String counter(String key) {
        return counterForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 获取指定实例和键的计数值。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @return 实例和键对应的计数值
     */
    String counterForInstance(String instanceId, String key);

    /**
     * 获取指定键的计数值。
     *
     * @param key 计数器的键
     * @return 键对应的计数值
     */
    default Double counterDouble(String key) {
        return counterDoubleForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 获取指定实例和键的计数值。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @return 实例和键对应的计数值
     */
    default Double counterDoubleForInstance(String instanceId, String key) {
        return Double.parseDouble(counterForInstance(instanceId, key));
    }

    /**
     * 获取指定键的计数值。
     *
     * @param key 计数器的键
     * @return 键对应的计数值
     */
    default Long counterLong(String key) {
        return counterLongForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 获取指定实例和键的计数值。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @return 实例和键对应的计数值
     */
    default Long counterLongForInstance(String instanceId, String key) {
        return Long.parseLong(counterForInstance(instanceId, key));
    }

    /**
     * 增加指定键的计数值。
     *
     * @param key  计数器的键
     * @param dela 增加的值
     * @return 增加后的计数值
     */
    default Long counter(String key, Long dela) {
        return counterForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, dela);
    }

    /**
     * 增加指定实例和键的计数值。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @param dela       增加的值
     * @return 实例和键对应的计数值
     */
    default Long counterForInstance(String instanceId, String key, Long dela) {
        return counterForInstance(instanceId, key, 0L, dela);
    }

    /**
     * 增加指定键的计数值。
     *
     * @param key    计数器的键
     * @param dela   增加的值
     * @param expiry 过期时间
     * @param unit   过期时间单位
     * @return 增加后的计数值
     */
    default Long counter(String key, Long dela, long expiry, TimeUnit unit) {
        return counterForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, dela, expiry, unit);
    }

    /**
     * 增加指定实例和键的计数值。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @param dela       增加的值
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 实例和键对应的计数值
     */
    default Long counterForInstance(String instanceId, String key, Long dela, long expiry, TimeUnit unit) {
        return counterForInstance(instanceId, key, 0L, dela, expiry, unit);
    }

    /**
     * 增加指定键的计数值并设置初始值。
     *
     * @param key     计数器的键
     * @param dela    增加的值
     * @param initial 初始值
     * @return 增加后的计数值
     */
    default Long counter(String key, Long initial, Long dela) {
        return counterForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, dela, initial);
    }

    /**
     * 增加指定实例和键的计数值并设置初始值。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @param dela       增加的值
     * @param initial    初始值
     * @return 实例和键对应的计数值
     */
    default Long counterForInstance(String instanceId, String key, Long initial, Long dela) {
        return counterForInstance(instanceId, key, dela, initial, 0L);
    }

    /**
     * 增加指定键的计数值并设置初始值和过期时间。
     *
     * @param key     计数器的键
     * @param dela    增加的值
     * @param initial 初始值
     * @param expiry  过期时间（秒）
     * @return 增加后的计数值
     */
    default Long counter(String key, Long initial, Long dela, long expiry) {
        return counterForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, dela, initial, expiry);
    }

    /**
     * 增加指定实例和键的计数值并设置初始值和过期时间。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @param initial    初始值
     * @param dela       增加的值
     * @param expiry     过期时间（秒）
     * @return 实例和键对应的计数值
     */
    default Long counterForInstance(String instanceId, String key, Long initial, Long dela, long expiry) {
        return counterForInstance(instanceId, key, dela, initial, expiry, TimeUnit.SECONDS);
    }

    /**
     * 增加指定实例和键的计数值并设置初始值和过期时间。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @param initial    初始值
     * @param dela       增加的值
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 实例和键对应的计数值
     */
    Long counterForInstance(String instanceId, String key, Long initial, Long dela, long expiry, TimeUnit unit);

    /**
     * 增加指定键的计数值（双精度类型）。
     *
     * @param key  计数器的键
     * @param dela 增加的值（双精度类型）
     * @return 增加后的计数值
     */
    default Double counter(String key, Double dela) {
        return counterForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, dela);
    }

    /**
     * 增加指定实例和键的计数值（双精度类型）。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @param dela       增加的值（双精度类型）
     * @return 实例和键对应的计数值
     */
    default Double counterForInstance(String instanceId, String key, Double dela) {
        return counterForInstance(instanceId, key, 0D, dela);
    }

    /**
     * 增加指定键的计数值（双精度类型）。
     *
     * @param key    计数器的键
     * @param dela   增加的值（双精度类型）
     * @param expiry 过期时间（秒）
     * @return 增加后的计数值
     */
    default Double counter(String key, Double dela, long expiry) {
        return counterForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, dela, expiry);
    }

    /**
     * 增加指定实例和键的计数值（双精度类型）。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @param dela       增加的值（双精度类型）
     * @param expiry     过期时间（秒）
     * @return 实例和键对应的计数值
     */
    default Double counterForInstance(String instanceId, String key, Double dela, long expiry) {
        return counterForInstance(instanceId, key, 0D, dela, expiry, TimeUnit.SECONDS);
    }

    /**
     * 增加指定键的计数值（双精度类型）。
     *
     * @param key    计数器的键
     * @param dela   增加的值（双精度类型）
     * @param expiry 过期时间
     * @param unit   过期时间单位
     * @return 增加后的计数值
     */
    default Double counter(String key, Double dela, long expiry, TimeUnit unit) {
        return counterForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, dela, expiry, unit);
    }

    /**
     * 增加指定实例和键的计数值（双精度类型）。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @param dela       增加的值（双精度类型）
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 实例和键对应的计数值
     */
    default Double counterForInstance(String instanceId, String key, Double dela, long expiry, TimeUnit unit) {
        return counterForInstance(instanceId, key, 0D, dela, expiry, unit);
    }

    /**
     * 增加指定键的计数值并设置初始值（双精度类型）。
     *
     * @param key     计数器的键
     * @param dela    增加的值（双精度类型）
     * @param initial 初始值（双精度类型）
     * @return 增加后的计数值
     */
    default Double counter(String key, Double initial, Double dela) {
        return counterForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, dela, initial);
    }

    /**
     * 增加指定实例和键的计数值并设置初始值（双精度类型）。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @param dela       增加的值（双精度类型）
     * @param initial    初始值（双精度类型）
     * @return 实例和键对应的计数值
     */
    default Double counterForInstance(String instanceId, String key, Double initial, Double dela) {
        return counterForInstance(instanceId, key, dela, initial, 0L);
    }

    /**
     * 增加指定键的计数值并设置初始值和过期时间（双精度类型）。
     *
     * @param key     计数器的键
     * @param dela    增加的值（双精度类型）
     * @param initial 初始值（双精度类型）
     * @param expiry  过期时间（秒）
     * @return 增加后的计数值
     */
    default Double counter(String key, Double initial, Double dela, long expiry) {
        return counterForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, dela, initial, expiry);
    }

    /**
     * 增加指定实例和键的计数值并设置初始值和过期时间（双精度类型）。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @param dela       增加的值（双精度类型）
     * @param initial    初始值（双精度类型）
     * @param expiry     过期时间（秒）
     * @return 实例和键对应的计数值
     */
    default Double counterForInstance(String instanceId, String key, Double initial, Double dela, long expiry) {
        return counterForInstance(instanceId, key, dela, initial, expiry, TimeUnit.SECONDS);
    }

    /**
     * 增加指定实例和键的计数值并设置初始值和过期时间（双精度类型）。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @param dela       增加的值（双精度类型）
     * @param initial    初始值（双精度类型）
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 实例和键对应的计数值
     */
    Double counterForInstance(String instanceId, String key, Double initial, Double dela, long expiry, TimeUnit unit);

}
