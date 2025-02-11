package top.mingempty.cache.commons.api;

import top.mingempty.domain.other.GlobalConstant;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存操作的抽象接口，提供了一系列对缓存进行管理和操作的方法。
 * <p>
 * 该接口继承了多个缓存相关的接口，包括 {@link CacheStr}、 {@link CacheQueue}、 {@link CacheSet}、
 * {@link CacheZSet}、 {@link CacheMap}
 * </p>
 *
 * @author zzhao
 */
public interface CacheApi extends CacheStr, CacheQueue, CacheSet, CacheZSet, CacheMap {

    /**
     * 重命名缓存中的键。
     * <p>
     * 使用默认的实例名进行操作。
     * </p>
     *
     * @param oldKey 原键
     * @param newKey 新键
     * @return 操作是否成功
     */
    default Boolean rename(String oldKey, String newKey) {
        return renameForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, oldKey, newKey);
    }

    /**
     * 在指定实例中重命名缓存中的键。
     *
     * @param instanceId 实例ID
     * @param oldKey     原键
     * @param newKey     新键
     * @return 操作是否成功
     */
    Boolean renameForInstance(String instanceId, String oldKey, String newKey);

    /**
     * 检查缓存中指定键是否存在。
     * <p>
     * 使用默认的实例名进行操作。
     * </p>
     *
     * @param key 要检查的键
     * @return 键是否存在
     */
    default Boolean exists(String key) {
        return existsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 在指定实例中检查缓存中指定键是否存在。
     *
     * @param instanceId 实例ID
     * @param key        要检查的键
     * @return 键是否存在
     */
    Boolean existsForInstance(String instanceId, String key);

    /**
     * 获取缓存中指定键的过期时间（秒）。
     * <p>
     * 使用默认的实例名进行操作。
     * </p>
     *
     * @param key 要检查的键
     * @return 过期时间（秒）
     */
    default Long ttl(String key) {
        return ttlForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 在指定实例中获取缓存中指定键的过期时间（秒）。
     *
     * @param instanceId 实例ID
     * @param key        要检查的键
     * @return 过期时间（秒）
     */
    default Long ttlForInstance(String instanceId, String key) {
        return ttlForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存中指定键的过期时间。
     * <p>
     * 使用默认的实例名进行操作。
     * </p>
     *
     * @param key  要检查的键
     * @param unit 时间单位
     * @return 过期时间（秒）
     */
    default Long ttl(String key, TimeUnit unit) {
        return ttlForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, unit);
    }

    /**
     * 在指定实例中获取缓存中指定键的过期时间。
     *
     * @param instanceId 实例ID
     * @param key        要检查的键
     * @param unit       时间单位
     * @return 过期时间（秒）
     */
    Long ttlForInstance(String instanceId, String key, TimeUnit unit);

    /**
     * 更新缓存中指定键的过期时间。
     * <p>
     * 使用默认的实例名进行操作。
     * </p>
     *
     * @param key    要更新的键
     * @param expiry 新的过期时间（秒）
     * @return 操作是否成功
     */
    default Boolean updateExpires(String key, long expiry) {
        return updateExpiresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, expiry);
    }

    /**
     * 在指定实例中更新缓存中指定键的过期时间。
     *
     * @param instanceId 实例ID
     * @param key        要更新的键
     * @param expiry     新的过期时间（秒）
     * @return 操作是否成功
     */
    default Boolean updateExpiresForInstance(String instanceId, String key, long expiry) {
        return updateExpiresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, expiry, TimeUnit.SECONDS);
    }

    /**
     * 更新缓存中指定键的过期时间。
     * <p>
     * 使用默认的实例名进行操作。
     * </p>
     *
     * @param key    要更新的键
     * @param expiry 新的过期时间
     * @param unit   时间单位
     * @return 操作是否成功
     */
    default Boolean updateExpires(String key, long expiry, TimeUnit unit) {
        return updateExpiresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, expiry, unit);
    }

    /**
     * 在指定实例中更新缓存中指定键的过期时间。
     *
     * @param instanceId 实例ID
     * @param key        要更新的键
     * @param expiry     新的过期时间
     * @param unit       时间单位
     * @return 操作是否成功
     */
    Boolean updateExpiresForInstance(String instanceId, String key, long expiry, TimeUnit unit);

    /**
     * 从缓存中删除指定键。
     * <p>
     * 使用默认的实例名进行操作。
     * </p>
     *
     * @param key 要删除的键
     * @return 操作是否成功
     */
    default Boolean del(String key) {
        return delForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 在指定实例中删除缓存中的指定键。
     *
     * @param instanceId 实例ID
     * @param key        要删除的键
     * @return 操作是否成功
     */
    Boolean delForInstance(String instanceId, String key);

    /**
     * 从缓存中删除指定键。
     * <p>
     * 使用默认的实例名进行操作。
     * </p>
     *
     * @param keys 要删除的键集合
     * @return 已删除的数量
     */
    default Long del(Collection<String> keys) {
        return delForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys);
    }

    /**
     * 在指定实例中删除缓存中的指定键。
     *
     * @param instanceId 实例ID
     * @param keys       要删除的键集合
     * @return 已删除的数量
     */
    Long delForInstance(String instanceId, Collection<String> keys);

    /**
     * 根据指定格式的键删除所有相关的缓存数据。
     * <p>
     * 使用默认的实例名进行操作。
     * </p>
     *
     * @param pattern 要删除的指定格式的键
     * @return 操作是否成功
     */
    default Boolean delByPattern(String pattern) {
        return delByPatternForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, pattern);
    }

    /**
     * 在指定实例中根据指定格式的键删除所有相关的缓存数据。
     *
     * @param instanceId 实例ID
     * @param pattern  要删除的指定格式的键
     * @return 操作是否成功
     */
    Boolean delByPatternForInstance(String instanceId, String pattern);

    /**
     * 清空缓存中的所有数据。
     * <p>
     * 使用默认的实例名进行操作。
     * </p>
     *
     * @return 操作是否成功
     */
    default Boolean clear() {
        return clearForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 在指定实例中清空缓存中的所有数据。
     *
     * @param instanceId 实例ID
     * @return 操作是否成功
     */
    Boolean clearForInstance(String instanceId);

    /**
     * 关闭缓存连接。
     * <p>
     * 使用默认的实例名进行操作。
     * </p>
     *
     * @return 操作是否成功
     */
    default Boolean close() {
        return closeForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 在指定实例中关闭缓存连接。
     *
     * @param instanceId 实例ID
     * @return 操作是否成功
     */
    Boolean closeForInstance(String instanceId);

    /**
     * 取消缓存中指定键的异步删除操作。
     * <p>
     * 使用默认的实例名进行操作。
     * </p>
     *
     * @param key 要取消的键
     * @return 操作是否成功
     */
    default Boolean unlink(String key) {
        return unlinkForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 在指定实例中取消缓存中指定键的异步删除操作。
     *
     * @param instanceId 实例ID
     * @param key        要取消的键
     * @return 操作是否成功
     */
    Boolean unlinkForInstance(String instanceId, String key);

    /**
     * 根据前缀删除所有相关的缓存数据，使用扫描操作。
     * <p>
     * 使用默认的实例名进行操作。
     * </p>
     *
     * @param prefix 要删除的前缀
     * @return 操作是否成功
     */
    default Boolean delByPrefixUseScan(String prefix) {
        return delByPrefixUseScanForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, prefix);
    }

    /**
     * 在指定实例中根据前缀删除所有相关的缓存数据，使用扫描操作。
     *
     * @param instanceId 实例ID
     * @param prefix     要删除的前缀
     * @return 操作是否成功
     */
    Boolean delByPrefixUseScanForInstance(String instanceId, String prefix);

    /**
     * 从给定的键中删除过期时间
     *
     * @param key 要删除过期时间的键
     * @return 操作是否成功
     */
    default Boolean persist(String key) {
        return persistForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 在指定实例中从给定的键中删除过期时间
     *
     * @param instanceId 实例ID
     * @param key        要删除过期时间的键
     * @return 操作是否成功
     */
    Boolean persistForInstance(String instanceId, String key);

    /**
     * 扫描指定格式的键
     *
     * @param pattern 格式
     * @return 键集合
     */
    default Set<String> scan(String pattern) {
        return scanForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, pattern);
    }

    /**
     * 在指定实例中扫描指定格式的键
     *
     * @param instanceId 实例ID
     * @param pattern    格式
     * @return 键集合
     */
    Set<String> scanForInstance(String instanceId, String pattern);

}
