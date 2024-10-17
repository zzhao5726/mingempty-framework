package top.mingempty.cache.commons.api;

import cn.hutool.core.lang.Pair;
import top.mingempty.commons.util.DateTimeUtil;
import top.mingempty.domain.base.IPage;
import top.mingempty.domain.other.GlobalConstant;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 有序集合接口，用于对缓存中的有序集合（ZSet）进行操作。
 *
 * @author zzhao
 */
public interface CacheZSet {

    /**
     * 获取当前缓存实例的有序集合缓存API。
     * <p>
     * 返回当前实例的 {@link CacheZSet} 类型的API。
     * </p>
     *
     * @return 当前缓存实例的有序集合缓存集合
     */
    default CacheZSet getCacheZSet() {
        return this;
    }

    /**
     * 向指定键的有序集合中添加元素。
     *
     * @param <E>   元素类型
     * @param key   有序集合的键
     * @param score 元素的分值
     * @param value 要添加的元素
     * @return 是否添加成功
     */
    default <E> Boolean zsetAdd(String key, double score, E value) {
        return zsetAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, score, value);
    }

    /**
     * 向指定实例和键的有序集合中添加元素。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param score      元素的分值
     * @param value      要添加的元素
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddForInstance(String instanceId, String key, double score, E value) {
        return zsetAddForInstance(instanceId, key, score, value, 0L);
    }

    /**
     * 向指定键的有序集合中添加元素，并设置过期时间。
     *
     * @param <E>    元素类型
     * @param key    有序集合的键
     * @param score  元素的分值
     * @param value  要添加的元素
     * @param expiry 过期时间（秒）
     * @return 是否添加成功
     */
    default <E> Boolean zsetAdd(String key, double score, E value, long expiry) {
        return zsetAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, score, value, expiry);
    }

    /**
     * 向指定实例和键的有序集合中添加元素，并设置过期时间。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param score      元素的分值
     * @param value      要添加的元素
     * @param expiry     过期时间（秒）
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddForInstance(String instanceId, String key, double score, E value, long expiry) {
        return zsetAddForInstance(instanceId, key, score, value, expiry, TimeUnit.SECONDS);
    }

    /**
     * 向指定键的有序集合中添加元素，并设置过期时间。
     *
     * @param <E>    元素类型
     * @param key    有序集合的键
     * @param score  元素的分值
     * @param value  要添加的元素
     * @param expiry 过期时间
     * @param unit   过期时间单位
     * @return 是否添加成功
     */
    default <E> Boolean zsetAdd(String key, double score, E value, long expiry, TimeUnit unit) {
        return zsetAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, score, value, expiry, unit);
    }

    /**
     * 向指定实例和键的有序集合中添加元素，并设置过期时间。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param score      元素的分值
     * @param value      要添加的元素
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddForInstance(String instanceId, String key, double score, E value, long expiry, TimeUnit unit) {
        return zsetAddForInstance(instanceId, key, score, value, expiry, unit, true);
    }

    /**
     * 向指定键的有序集合中添加元素。
     *
     * @param <E>      元素类型
     * @param key      有序集合的键
     * @param score    元素的分值
     * @param value    要添加的元素
     * @param override 是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean zsetAdd(String key, double score, E value, boolean override) {
        return zsetAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, score, value, override);
    }

    /**
     * 向指定实例和键的有序集合中添加元素。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param score      元素的分值
     * @param value      要添加的元素
     * @param override   是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddForInstance(String instanceId, String key, double score, E value, boolean override) {
        return zsetAddForInstance(instanceId, key, score, value, 0L, override);
    }

    /**
     * 向指定键的有序集合中添加元素，并设置过期时间。
     *
     * @param <E>      元素类型
     * @param key      有序集合的键
     * @param score    元素的分值
     * @param value    要添加的元素
     * @param expiry   过期时间（秒）
     * @param override 是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean zsetAdd(String key, double score, E value, long expiry, boolean override) {
        return zsetAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, score, value, expiry, override);
    }

    /**
     * 向指定实例和键的有序集合中添加元素，并设置过期时间。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param score      元素的分值
     * @param value      要添加的元素
     * @param expiry     过期时间（秒）
     * @param override   是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddForInstance(String instanceId, String key, double score, E value, long expiry, boolean override) {
        return zsetAddForInstance(instanceId, key, score, value, expiry, TimeUnit.SECONDS, override);
    }

    /**
     * 向指定键的有序集合中添加元素，并设置过期时间。
     *
     * @param <E>      元素类型
     * @param key      有序集合的键
     * @param score    元素的分值
     * @param value    要添加的元素
     * @param expiry   过期时间
     * @param unit     过期时间单位
     * @param override 是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean zsetAdd(String key, double score, E value, long expiry, TimeUnit unit, boolean override) {
        return zsetAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, score, value, expiry, unit, override);
    }

    /**
     * 向指定实例和键的有序集合中添加元素，并设置过期时间。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param score      元素的分值
     * @param value      要添加的元素
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @param override   是否覆盖已有值
     * @return 是否添加成功
     */
    <E> Boolean zsetAddForInstance(String instanceId, String key, double score, E value, long expiry, TimeUnit unit, boolean override);

    /**
     * 向指定键的有序集合中批量添加元素。
     *
     * @param <E>    元素类型
     * @param key    有序集合的键
     * @param values 批量添加的元素
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddMore(String key, Map<E, Double> values) {
        return zsetAddMoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, values);
    }

    /**
     * 向指定实例和键的有序集合中批量添加元素。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param values     批量添加的元素
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddMoreForInstance(String instanceId, String key, Map<E, Double> values) {
        return zsetAddMoreForInstance(instanceId, key, values, 0L);
    }

    /**
     * 向指定键的有序集合中批量添加元素，并设置过期时间。
     *
     * @param <E>    元素类型
     * @param key    有序集合的键
     * @param values 批量添加的元素
     * @param expiry 过期时间（秒）
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddMore(String key, Map<E, Double> values, long expiry) {
        return zsetAddMoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, values, expiry);
    }

    /**
     * 向指定实例和键的有序集合中批量添加元素，并设置过期时间。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param values     批量添加的元素
     * @param expiry     过期时间（秒）
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddMoreForInstance(String instanceId, String key, Map<E, Double> values, long expiry) {
        return zsetAddMoreForInstance(instanceId, key, values, expiry, TimeUnit.SECONDS);
    }

    /**
     * 向指定键的有序集合中批量添加元素，并设置过期时间。
     *
     * @param <E>    元素类型
     * @param key    有序集合的键
     * @param values 批量添加的元素
     * @param expiry 过期时间
     * @param unit   过期时间单位
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddMore(String key, Map<E, Double> values, long expiry, TimeUnit unit) {
        return zsetAddMoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, values, expiry, unit);
    }

    /**
     * 向指定实例和键的有序集合中批量添加元素，并设置过期时间。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param values     批量添加的元素
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddMoreForInstance(String instanceId, String key, Map<E, Double> values, long expiry, TimeUnit unit) {
        return zsetAddMoreForInstance(instanceId, key, values, expiry, unit, true);
    }

    /**
     * 向指定键的有序集合中批量添加元素。
     *
     * @param <E>      元素类型
     * @param key      有序集合的键
     * @param values   批量添加的元素
     * @param override 是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddMore(String key, Map<E, Double> values, boolean override) {
        return zsetAddMoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, values, override);
    }

    /**
     * 向指定实例和键的有序集合中批量添加元素。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param values     批量添加的元素
     * @param override   是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddMoreForInstance(String instanceId, String key, Map<E, Double> values, boolean override) {
        return zsetAddMoreForInstance(instanceId, key, values, 0L, override);
    }

    /**
     * 向指定键的有序集合中批量添加元素，并设置过期时间。
     *
     * @param <E>      元素类型
     * @param key      有序集合的键
     * @param values   批量添加的元素
     * @param expiry   过期时间（秒）
     * @param override 是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddMore(String key, Map<E, Double> values, long expiry, boolean override) {
        return zsetAddMoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, values, expiry, override);
    }

    /**
     * 向指定实例和键的有序集合中批量添加元素，并设置过期时间。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param values     批量添加的元素
     * @param expiry     过期时间（秒）
     * @param override   是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddMoreForInstance(String instanceId, String key, Map<E, Double> values, long expiry, boolean override) {
        return zsetAddMoreForInstance(instanceId, key, values, expiry, TimeUnit.SECONDS, override);
    }

    /**
     * 向指定键的有序集合中批量添加元素，并设置过期时间。
     *
     * @param <E>      元素类型
     * @param key      有序集合的键
     * @param values   批量添加的元素
     * @param expiry   过期时间
     * @param unit     过期时间单位
     * @param override 是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean zsetAddMore(String key, Map<E, Double> values, long expiry, TimeUnit unit, boolean override) {
        return zsetAddMoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, values, expiry, unit, override);
    }

    /**
     * 向指定实例和键的有序集合中批量添加元素，并设置过期时间。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param values     批量添加的元素
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @param override   是否覆盖已有值
     * @return 是否添加成功
     */
    <E> Boolean zsetAddMoreForInstance(String instanceId, String key, Map<E, Double> values, long expiry, TimeUnit unit, boolean override);

    /**
     * 获取符合指定格式的元素集合
     *
     * @param key         元素的键
     * @param pattern     元素格式
     * @param eClass 元素的类型
     * @return 操作结果
     */
    default <E> Map<E, Double> zsetScan(String key, String pattern, Class<E> eClass) {
        return zsetScanForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, pattern, eClass);
    }

    /**
     * 获取符合指定格式的元素集合
     *
     * @param instanceId  实例ID
     * @param key         元素的键
     * @param pattern     元素格式
     * @param eClass 元素的类型
     * @return 操作结果
     */
    <E> Map<E, Double> zsetScanForInstance(String instanceId, String key, String pattern, Class<E> eClass);

    /**
     * 增加键内特定项的分值。
     *
     * @param key   有序集合的键
     * @param value 表示要操作的映射表中的特定项
     * @param delta 增加的量
     * @return 返回增加后的值
     */
    default <E> Double zsetIncrementScore(String key, E value, double delta) {
        return zsetIncrementScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value, delta);
    }

    /**
     * 增加指定实例的键内特定项的分值
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param value      表示要操作的映射表中的特定项
     * @param delta      增加的量
     * @return 返回增加后的值
     */
    <E> Double zsetIncrementScoreForInstance(String instanceId, String key, E value, double delta);

    /**
     * 获取指定键的有序集合的元素数量。
     *
     * @param key 有序集合的键
     * @return 元素数量（-1时表示获取异常）
     */
    default Long zsetCard(String key) {
        return zsetCardForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 获取指定实例和键的有序集合的元素数量。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @return 元素数量（-1时表示获取异常）
     */
    Long zsetCardForInstance(String instanceId, String key);

    /**
     * 获取指定键的有序集合中，分值在指定范围内的元素数量。
     *
     * @param key 有序集合的键
     * @param min 最小分值
     * @param max 最大分值
     * @return 分值在指定范围内的元素数量（-1时表示获取异常）
     */
    default Long zsetCount(String key, double min, double max) {
        return zsetCountForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, min, max);
    }

    /**
     * 获取指定实例和键的有序集合中，分值在指定范围内的元素数量。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param min        最小分值
     * @param max        最大分值
     * @return 分值在指定范围内的元素数量（-1时表示获取异常）
     */
    Long zsetCountForInstance(String instanceId, String key, double min, double max);

    /**
     * 获取指定键的有序集合中，指定元素的分值。
     *
     * @param <E>   元素类型
     * @param key   有序集合的键
     * @param value 要获取分值的元素
     * @return 元素的分值
     */
    default <E> double zsetScore(String key, E value) {
        return zsetScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value);
    }

    /**
     * 获取指定实例和键的有序集合中，指定元素的分值。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param value      要获取分值的元素
     * @return 元素的分值
     */
    <E> double zsetScoreForInstance(String instanceId, String key, E value);

    /**
     * 移除指定键的有序集合中的一个元素。
     *
     * @param <E>   元素类型
     * @param key   有序集合的键
     * @param value 要移除的元素
     * @return 移除的元素数量(- 1表示移除异常)
     */
    default <E> Long zsetRemove(String key, E value) {
        return zsetRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value);
    }

    /**
     * 移除指定实例和键的有序集合中的一个元素。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param value      要移除的元素
     * @return 移除的元素数量(- 1表示移除异常)
     */
    default <E> Long zsetRemoveForInstance(String instanceId, String key, E value) {
        return zsetRemoveForInstance(instanceId, key, Collections.singletonList(value));
    }

    /**
     * 移除指定键的有序集合中的一个或多个元素。
     *
     * @param <E>   元素类型
     * @param key   有序集合的键
     * @param value 要移除的元素
     * @return 移除的元素数量(- 1表示移除异常)
     */
    default <E> Long zsetRemove(String key, List<E> value) {
        return zsetRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value);
    }

    /**
     * 移除指定实例和键的有序集合中的一个或多个元素。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param value      要移除的元素
     * @return 移除的元素数量(- 1表示移除异常)
     */
    <E> Long zsetRemoveForInstance(String instanceId, String key, List<E> value);

    /**
     * 移除指定键的有序集合中，排序在指定范围内的所有元素。
     *
     * @param key   有序集合的键
     * @param start 起始索引
     * @param end   截止索引
     * @return 移除的元素数量(- 1表示移除异常)
     */
    default Long zsetRemoveRange(String key, long start, long end) {
        return zsetRemoveRangeForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, start, end);
    }

    /**
     * 移除指定实例和键的有序集合中，排序在指定范围内的所有元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param start      起始索引
     * @param end        截止索引
     * @return 移除的元素数量(- 1表示移除异常)
     */
    Long zsetRemoveRangeForInstance(String instanceId, String key, long start, long end);

    /**
     * 移除指定键的有序集合中，值在指定范围内的所有元素。
     *
     * @param key        有序集合的键
     * @param lowerBound 范围的下限
     * @param upperBound 范围的上限
     * @return 移除的元素数量(- 1表示移除异常)
     */
    default Long zsetRemoveRangeByLex(String key, String lowerBound, String upperBound) {
        return zsetRemoveRangeByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound);
    }

    /**
     * 移除指定实例和键的有序集合中，值在指定范围内的所有元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 范围的下限
     * @param upperBound 范围的上限
     * @return 移除的元素数量(- 1表示移除异常)
     */
    Long zsetRemoveRangeByLexForInstance(String instanceId, String key, String lowerBound, String upperBound);

    /**
     * 移除指定键的有序集合中，分值在指定范围内的所有元素。
     *
     * @param key 有序集合的键
     * @param min 最小分值
     * @param max 最大分值
     * @return 移除的元素数量(- 1表示移除异常)
     */
    default Long zsetRemoveRangeByScore(String key, double min, double max) {
        return zsetRemoveRangeByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, min, max);
    }

    /**
     * 移除指定实例和键的有序集合中，分值在指定范围内的所有元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param min        最小分值
     * @param max        最大分值
     * @return 移除的元素数量(- 1表示移除异常)
     */
    Long zsetRemoveRangeByScoreForInstance(String instanceId, String key, double min, double max);

    /**
     * 计算两个集合之间的差异，返回一个包含在第一个集合中但不在第二个集合中的元素的集合
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param otherKey          其它集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> zsetDifference(String key, String otherKey, Class<E> eClass) {
        return zsetDifferenceForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, eClass);
    }

    /**
     * 计算两个集合之间的差异，返回一个包含在第一个集合中但不在第二个集合中的元素的集合
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param otherKey          其它集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> zsetDifferenceForInstance(String instanceId, String key, String otherKey, Class<E> eClass) {
        return zsetDifferenceForInstance(instanceId, key, Collections.singletonList(otherKey), eClass);
    }

    /**
     * 计算一个集合和多个集合之间的差异，返回一个包含在第一个集合中但不在其他集合中的元素的集合。
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param otherKeys         其它集合的键的集合
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> zsetDifference(String key, Collection<String> otherKeys, Class<E> eClass) {
        return zsetDifferenceForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, eClass);
    }

    /**
     * 计算一个集合和多个集合之间的差异，返回一个包含在第一个集合中但不在其他集合中的元素的集合。
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param otherKeys         其它集合的键的集合
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    <E> Set<E> zsetDifferenceForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> eClass);

    /**
     * 计算给定的集合与其他多个集合的差异，并将结果存储到指定的集合中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @return 存储的元素数量
     */
    default Long zsetDifferenceAndStore(String key, Collection<String> otherKeys, String destKey) {
        return zsetDifferenceAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey);
    }

    /**
     * 计算给定的集合与其他多个集合的差异，并将结果存储到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @return 存储的元素数量
     */
    default Long zsetDifferenceAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey) {
        return zsetDifferenceAndStoreForInstance(instanceId, key, otherKeys, destKey, 0L);
    }

    /**
     * 计算给定的集合与其他多个集合的差异，并将结果存储到指定的集合中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @param expiry    过期时间(秒)
     * @return 存储的元素数量
     */
    default Long zsetDifferenceAndStore(String key, Collection<String> otherKeys, String destKey, long expiry) {
        return zsetDifferenceAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, expiry);
    }

    /**
     * 计算给定的集合与其他多个集合的差异，并将结果存储到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间(秒)
     * @return 存储的元素数量
     */
    default Long zsetDifferenceAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry) {
        return zsetDifferenceAndStoreForInstance(instanceId, key, otherKeys, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 计算给定的集合与其他多个集合的差异，并将结果存储到指定的集合中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @param expiry    过期时间
     * @param unit      过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetDifferenceAndStore(String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit) {
        return zsetDifferenceAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, expiry, unit);
    }

    /**
     * 计算给定的集合与其他多个集合的差异，并将结果存储到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    Long zsetDifferenceAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit);

    /**
     * 计算给定的多个集合的差异，并将结果返回
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param otherKey    其他的键
     * @param eClass 元素的类型
     * @return 多个集合的差异
     */
    default <E> Map<E, Double> zsetDifferenceWithScores(String key, String otherKey, Class<E> eClass) {
        return zsetDifferenceWithScoresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, eClass);
    }

    /**
     * 计算给定的多个集合的差异，并将结果返回。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKey    其他的键
     * @param eClass 元素的类型
     * @return 多个集合的差异
     */
    default <E> Map<E, Double> zsetDifferenceWithScoresForInstance(String instanceId, String key, String otherKey, Class<E> eClass) {
        return zsetDifferenceWithScoresForInstance(instanceId, key, Collections.singletonList(otherKey), eClass);
    }

    /**
     * 计算给定的多个集合的差异，并将结果返回
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param otherKeys   其他的键的集合
     * @param eClass 元素的类型
     * @return 多个集合的差异
     */
    default <E> Map<E, Double> zsetDifferenceWithScores(String key, Collection<String> otherKeys, Class<E> eClass) {
        return zsetDifferenceWithScoresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, eClass);
    }

    /**
     * 计算给定的多个集合的差异，并将结果返回。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其他的键的集合
     * @param eClass 元素的类型
     * @return 多个集合的差异
     */
    <E> Map<E, Double> zsetDifferenceWithScoresForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> eClass);

    /**
     * 计算给定的两个集合的交集
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param otherKey          其它集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> zsetIntersect(String key, String otherKey, Class<E> eClass) {
        return zsetIntersectForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, eClass);
    }

    /**
     * 计算给定的两个集合的交集
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param otherKey          其它集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> zsetIntersectForInstance(String instanceId, String key, String otherKey, Class<E> eClass) {
        return zsetIntersectForInstance(instanceId, key, Collections.singletonList(otherKey), eClass);
    }

    /**
     * 计算给定的集合和多个其他集合的交集。
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param otherKeys         其它集合的键的集合
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> zsetIntersect(String key, Collection<String> otherKeys, Class<E> eClass) {
        return zsetIntersectForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, eClass);
    }

    /**
     * 计算给定的集合和多个其他集合的交集。
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param otherKeys         其它集合的键的集合
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    <E> Set<E> zsetIntersectForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> eClass);

    /**
     * 计算给定的两个集合的交集
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param otherKey    其它集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Map<E, Double> zsetIntersectWithScores(String key, String otherKey, Class<E> eClass) {
        return zsetIntersectWithScoresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, eClass);
    }

    /**
     * 计算给定的两个集合的交集
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKey    其它集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Map<E, Double> zsetIntersectWithScoresForInstance(String instanceId, String key, String otherKey, Class<E> eClass) {
        return zsetIntersectWithScoresForInstance(instanceId, key, Collections.singletonList(otherKey), eClass);
    }

    /**
     * 计算给定的集合和多个其他集合的交集。
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Map<E, Double> zsetIntersectWithScores(String key, Collection<String> otherKeys, Class<E> eClass) {
        return zsetIntersectWithScoresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, eClass);
    }

    /**
     * 计算给定的集合和多个其他集合的交集。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    <E> Map<E, Double> zsetIntersectWithScoresForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> eClass);

    /**
     * 计算给定的两个集合的交集，并将结果保存到指定的集合中。
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStore(String key, String otherKey, String destKey) {
        return zsetIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey);
    }

    /**
     * 计算给定的两个集合的交集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStoreForInstance(String instanceId, String key, String otherKey, String destKey) {
        return zsetIntersectAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return zsetIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey) {
        return zsetIntersectAndStoreForInstance(instanceId, key, otherKeys, destKey, 0L);
    }

    /**
     * 计算给定的两个集合的交集，并将结果保存到指定的集合中。
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @param expiry   过期时间(秒)
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStore(String key, String otherKey, String destKey, long expiry) {
        return zsetIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey, expiry);
    }

    /**
     * 计算给定的两个集合的交集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间(秒)
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStoreForInstance(String instanceId, String key, String otherKey, String destKey, long expiry) {
        return zsetIntersectAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey, expiry);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @param expiry    过期时间(秒)
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStore(String key, Collection<String> otherKeys, String destKey, long expiry) {
        return zsetIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, expiry);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间(秒)
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry) {
        return zsetIntersectAndStoreForInstance(instanceId, key, otherKeys, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 计算给定的两个集合的交集，并将结果保存到指定的集合中。
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @param expiry   过期时间
     * @param unit     过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStore(String key, String otherKey, String destKey, long expiry, TimeUnit unit) {
        return zsetIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey, expiry, unit);
    }

    /**
     * 计算给定的两个集合的交集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStoreForInstance(String instanceId, String key, String otherKey, String destKey, long expiry, TimeUnit unit) {
        return zsetIntersectAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey, expiry, unit);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @param expiry    过期时间
     * @param unit      过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStore(String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit) {
        return zsetIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, expiry, unit);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    Long zsetIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit);

    /**
     * 计算给定两个集合的并集
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param otherKey          其它集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> zsetUnion(String key, String otherKey, Class<E> eClass) {
        return zsetUnionForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, eClass);
    }

    /**
     * 计算给定两个集合的并集
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param otherKey          其它集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> zsetUnionForInstance(String instanceId, String key, String otherKey, Class<E> eClass) {
        return zsetUnionForInstance(instanceId, key, Collections.singletonList(otherKey), eClass);
    }

    /**
     * 计算给定的集合和多个其他集合的并集。
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param otherKeys         其它集合的键的集合
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> zsetUnion(String key, Collection<String> otherKeys, Class<E> eClass) {
        return zsetUnionForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, eClass);
    }

    /**
     * 计算给定的集合和多个其他集合的并集。
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param otherKeys         其它集合的键的集合
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    <E> Set<E> zsetUnionForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> eClass);

    /**
     * 计算给定两个集合的并集
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param otherKey    其它集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Map<E, Double> zsetUnionWithScores(String key, String otherKey, Class<E> eClass) {
        return zsetUnionWithScoresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, eClass);
    }

    /**
     * 计算给定两个集合的并集
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKey    其它集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Map<E, Double> zsetUnionWithScoresForInstance(String instanceId, String key, String otherKey, Class<E> eClass) {
        return zsetUnionWithScoresForInstance(instanceId, key, Collections.singletonList(otherKey), eClass);
    }

    /**
     * 计算给定的集合和多个其他集合的并集。
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Map<E, Double> zsetUnionWithScores(String key, Collection<String> otherKeys, Class<E> eClass) {
        return zsetUnionWithScoresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, eClass);
    }

    /**
     * 计算给定的集合和多个其他集合的并集。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    <E> Map<E, Double> zsetUnionWithScoresForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> eClass);

    /**
     * 计算给定两个集合的并集，并将结果保存到指定的集合中。
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStore(String key, String otherKey, String destKey) {
        return zsetUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey);
    }

    /**
     * 计算给定两个集合的并集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStoreForInstance(String instanceId, String key, String otherKey, String destKey) {
        return zsetUnionAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return zsetUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey) {
        return zsetUnionAndStoreForInstance(instanceId, key, otherKeys, destKey, 0L);
    }

    /**
     * 计算给定两个集合的并集，并将结果保存到指定的集合中。
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @param expiry   过期时间(秒)
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStore(String key, String otherKey, String destKey, long expiry) {
        return zsetUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey, expiry);
    }

    /**
     * 计算给定两个集合的并集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间(秒)
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStoreForInstance(String instanceId, String key, String otherKey, String destKey, long expiry) {
        return zsetUnionAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey, expiry);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @param expiry    过期时间(秒)
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStore(String key, Collection<String> otherKeys, String destKey, long expiry) {
        return zsetUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, expiry);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间(秒)
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry) {
        return zsetUnionAndStoreForInstance(instanceId, key, otherKeys, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 计算给定两个集合的并集，并将结果保存到指定的集合中。
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @param expiry   过期时间
     * @param unit     过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStore(String key, String otherKey, String destKey, long expiry, TimeUnit unit) {
        return zsetUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey, expiry, unit);
    }

    /**
     * 计算给定两个集合的并集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStoreForInstance(String instanceId, String key, String otherKey, String destKey, long expiry, TimeUnit unit) {
        return zsetUnionAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey, expiry, unit);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @param expiry    过期时间
     * @param unit      过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStore(String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit) {
        return zsetUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, expiry, unit);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    Long zsetUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit);

    /**
     * 从一个特定的集合中随机选择返回一个元素。
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param eClass 元素的类型
     * @return 集合中的随机元素
     */
    default <E> E zsetRandomMembers(String key, Class<E> eClass) {
        return zsetRandomMembersForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, eClass);
    }

    /**
     * 从一个特定的集合中随机选择返回一个元素。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param eClass 元素的类型
     * @return 集合中的随机元素
     */
    <E> E zsetRandomMembersForInstance(String instanceId, String key, Class<E> eClass);

    /**
     * 从一个特定的集合中随机选择一定数量的不同元素，并返回一个包含这些元素的集合。
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param count             返回的元素个数
     * @param eClass 元素的类型
     * @return 集合中的随机元素
     */
    default <E> Set<E> zsetDistinctRandomMembers(String key, long count, Class<E> eClass) {
        return zsetDistinctRandomMembersForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, count, eClass);
    }

    /**
     * 从一个特定的集合中随机选择一定数量的不同元素，并返回一个包含这些元素的集合。
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param count             返回的元素个数
     * @param eClass 元素的类型
     * @return 集合中的随机元素
     */
    <E> Set<E> zsetDistinctRandomMembersForInstance(String instanceId, String key, long count, Class<E> eClass);

    /**
     * 从一个特定的集合中随机选择一定数量的不同元素，并返回一个包含这些元素的集合。
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param count       返回的元素个数
     * @param eClass 元素的类型
     * @return 集合中的随机元素
     */
    default <E> Map<E, Double> zsetDistinctRandomMembersWithScore(String key, long count, Class<E> eClass) {
        return zsetDistinctRandomMembersWithScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, count, eClass);
    }

    /**
     * 从一个特定的集合中随机选择一定数量的不同元素，并返回一个包含这些元素的集合。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param count       返回的元素个数
     * @param eClass 元素的类型
     * @return 集合中的随机元素
     */
    <E> Map<E, Double> zsetDistinctRandomMembersWithScoreForInstance(String instanceId, String key, long count, Class<E> eClass);

    /**
     * 弹出有序集合中分数最高的元素。
     *
     * @param key         有序集合的键
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    default <E> Pair<E, Double> zsetPopMax(String key, Class<E> eClass) {
        return zsetPopMaxForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, eClass);
    }

    /**
     * 弹出指定实例中有序集合分数最高的元素。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    <E> Pair<E, Double> zsetPopMaxForInstance(String instanceId, String key, Class<E> eClass);

    /**
     * 弹出有序集合中分数最高的元素，并在指定持续时间内等待。
     *
     * @param key         有序集合的键
     * @param duration    等待持续时间
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    default <E> Pair<E, Double> zsetPopMax(String key, Duration duration, Class<E> eClass) {
        return zsetPopMaxForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, duration, eClass);
    }

    /**
     * 弹出指定实例中有序集合分数最高的元素，并在指定持续时间内等待。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param duration    等待持续时间
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    default <E> Pair<E, Double> zsetPopMaxForInstance(String instanceId, String key, Duration duration, Class<E> eClass) {
        return zsetPopMaxForInstance(instanceId, key, DateTimeUtil.toSeconds(duration), TimeUnit.SECONDS, eClass);
    }

    /**
     * 弹出有序集合中分数最高的元素，并在指定过期时间内等待。
     *
     * @param key         有序集合的键
     * @param expiry      过期时间
     * @param unit        时间单位
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    default <E> Pair<E, Double> zsetPopMax(String key, long expiry, TimeUnit unit, Class<E> eClass) {
        return zsetPopMaxForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, expiry, unit, eClass);
    }

    /**
     * 弹出指定实例中有序集合分数最高的元素，并在指定过期时间内等待。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param expiry      过期时间
     * @param unit        时间单位
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    <E> Pair<E, Double> zsetPopMaxForInstance(String instanceId, String key, long expiry, TimeUnit unit, Class<E> eClass);

    /**
     * 弹出有序集合中指定数量的分数最高的元素。
     *
     * @param key         有序集合的键
     * @param count       弹出元素的数量
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Map对象
     */
    default <E> Map<E, Double> zsetPopMax(String key, long count, Class<E> eClass) {
        return zsetPopMaxForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, count, eClass);
    }

    /**
     * 弹出指定实例中有序集合指定数量的分数最高的元素。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param count       弹出元素的数量
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Map对象
     */
    <E> Map<E, Double> zsetPopMaxForInstance(String instanceId, String key, long count, Class<E> eClass);

    /**
     * 弹出有序集合中分数最低的元素。
     *
     * @param key         有序集合的键
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    default <E> Pair<E, Double> zsetPopMin(String key, Class<E> eClass) {
        return zsetPopMinForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, eClass);
    }

    /**
     * 弹出指定实例中有序集合分数最低的元素。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    <E> Pair<E, Double> zsetPopMinForInstance(String instanceId, String key, Class<E> eClass);

    /**
     * 弹出有序集合中分数最低的元素，并在指定持续时间内等待。
     *
     * @param key         有序集合的键
     * @param duration    等待持续时间
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    default <E> Pair<E, Double> zsetPopMin(String key, Duration duration, Class<E> eClass) {
        return zsetPopMinForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, duration, eClass);
    }

    /**
     * 弹出指定实例中有序集合分数最低的元素，并在指定持续时间内等待。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param duration    等待持续时间
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    default <E> Pair<E, Double> zsetPopMinForInstance(String instanceId, String key, Duration duration, Class<E> eClass) {
        return zsetPopMinForInstance(instanceId, key, DateTimeUtil.toSeconds(duration), TimeUnit.SECONDS, eClass);
    }

    /**
     * 弹出有序集合中分数最低的元素，并在指定过期时间内等待。
     *
     * @param key         有序集合的键
     * @param expiry      过期时间
     * @param unit        时间单位
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    default <E> Pair<E, Double> zsetPopMin(String key, long expiry, TimeUnit unit, Class<E> eClass) {
        return zsetPopMinForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, expiry, unit, eClass);
    }

    /**
     * 弹出指定实例中有序集合分数最低的元素，并在指定过期时间内等待。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param expiry      过期时间
     * @param unit        时间单位
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    <E> Pair<E, Double> zsetPopMinForInstance(String instanceId, String key, long expiry, TimeUnit unit, Class<E> eClass);

    /**
     * 弹出有序集合中指定数量的分数最低的元素。
     *
     * @param key         有序集合的键
     * @param count       弹出元素的数量
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Map对象
     */
    default <E> Map<E, Double> zsetPopMin(String key, long count, Class<E> eClass) {
        return zsetPopMinForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, count, eClass);
    }

    /**
     * 弹出指定实例中有序集合指定数量的分数最低的元素。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param count       弹出元素的数量
     * @param eClass 元素的类型
     * @param <E>         元素类型
     * @return 包含弹出元素及其分数的Map对象
     */
    <E> Map<E, Double> zsetPopMinForInstance(String instanceId, String key, long count, Class<E> eClass);

    /**
     * 获取元素的排序
     *
     * @param <E>   元素的类型
     * @param key   有序集合的键
     * @param value 指定元素
     * @return 元素的排序（异常时返回-1）
     */
    default <E> Long zsetRank(String key, E value) {
        return zsetRankForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value);
    }

    /**
     * 获取元素的排序
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param value      指定元素
     * @return 元素的排序（异常时返回-1）
     */
    <E> Long zsetRankForInstance(String instanceId, String key, E value);

    /**
     * 获取指定范围的元素。
     *
     * @param key               有序集合的键
     * @param iPage             分页参数
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    default <E> Set<E> zsetRange(String key, IPage iPage, Class<E> eClass) {
        return zsetRangeForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, iPage, eClass);
    }

    /**
     * 获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param iPage             分页参数
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    default <E> Set<E> zsetRangeForInstance(String instanceId, String key, IPage iPage, Class<E> eClass) {
        return zsetRangeForInstance(instanceId, key, iPage.getStartIndex(), iPage.getEndIndex(), eClass);
    }

    /**
     * 获取指定范围的元素。
     *
     * @param key               有序集合的键
     * @param min               最小值
     * @param max               最大值
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    default <E> Set<E> zsetRange(String key, long min, long max, Class<E> eClass) {
        return zsetRangeForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, min, max, eClass);
    }

    /**
     * 获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param min               最小值
     * @param max               最大值
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    <E> Set<E> zsetRangeForInstance(String instanceId, String key, long min, long max, Class<E> eClass);

    /**
     * 根据字典序获取指定范围的元素。
     *
     * @param key               有序集合的键
     * @param lowerBound        下边界
     * @param upperBound        上边界
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    default <E> Set<E> zsetRangeByLex(String key, String lowerBound, String upperBound, Class<E> eClass) {
        return zsetRangeByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, eClass);
    }

    /**
     * 根据字典序获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param lowerBound        下边界
     * @param upperBound        上边界
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    <E> Set<E> zsetRangeByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, Class<E> eClass);

    /**
     * 根据分数获取指定范围的元素。
     *
     * @param key               有序集合的键
     * @param min               最小值
     * @param max               最大值
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    default <E> Set<E> zsetRangeByScore(String key, double min, double max, Class<E> eClass) {
        return zsetRangeByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, min, max, eClass);
    }

    /**
     * 根据分数获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param min               最小值
     * @param max               最大值
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    <E> Set<E> zsetRangeByScoreForInstance(String instanceId, String key, double min, double max, Class<E> eClass);

    /**
     * 根据分数和偏移量获取指定范围的元素。
     *
     * @param key               有序集合的键
     * @param min               最小值
     * @param max               最大值
     * @param offset            偏移量
     * @param count             数量
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    default <E> Set<E> zsetRangeByScore(String key, double min, double max, long offset, long count, Class<E> eClass) {
        return zsetRangeByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, min, max, offset, count, eClass);
    }

    /**
     * 根据分数和偏移量获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param min               最小值
     * @param max               最大值
     * @param offset            偏移量
     * @param count             数量
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    <E> Set<E> zsetRangeByScoreForInstance(String instanceId, String key, double min, double max, long offset, long count, Class<E> eClass);

    /**
     * 根据分数获取指定范围的元素及其分数。
     *
     * @param key         有序集合的键
     * @param min         最小值
     * @param max         最大值
     * @param eClass 元素类型
     * @param <E>         元素类型
     * @return 指定范围的元素及其分数的映射
     */
    default <E> Map<E, Double> zsetRangeByScoreWithScores(String key, double min, double max, Class<E> eClass) {
        return zsetRangeByScoreWithScoresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, min, max, eClass);
    }

    /**
     * 根据分数获取指定实例中指定范围的元素及其分数。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param min         最小值
     * @param max         最大值
     * @param eClass 元素类型
     * @param <E>         元素类型
     * @return 指定范围的元素及其分数的映射
     */
    <E> Map<E, Double> zsetRangeByScoreWithScoresForInstance(String instanceId, String key, double min, double max, Class<E> eClass);

    /**
     * 根据分数和偏移量获取指定范围的元素及其分数。
     *
     * @param key         有序集合的键
     * @param min         最小值
     * @param max         最大值
     * @param offset      偏移量
     * @param count       数量
     * @param eClass 元素类型
     * @param <E>         元素类型
     * @return 指定范围的元素及其分数的映射
     */
    default <E> Map<E, Double> zsetRangeByScoreWithScores(String key, double min, double max, long offset, long count, Class<E> eClass) {
        return zsetRangeByScoreWithScoresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, min, max, offset, count, eClass);
    }

    /**
     * 根据分数和偏移量获取指定实例中指定范围的元素及其分数。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param min         最小值
     * @param max         最大值
     * @param offset      偏移量
     * @param count       数量
     * @param eClass 元素类型
     * @param <E>         元素类型
     * @return 指定范围的元素及其分数的映射
     */
    <E> Map<E, Double> zsetRangeByScoreWithScoresForInstance(String instanceId, String key, double min, double max, long offset, long count, Class<E> eClass);

    /**
     * 获取指定范围的元素及其分数。
     *
     * @param key         有序集合的键
     * @param start       起始位置
     * @param end         结束位置
     * @param eClass 元素类型
     * @param <E>         元素类型
     * @return 指定范围的元素及其分数的映射
     */
    default <E> Map<E, Double> zsetRangeWithScores(String key, long start, long end, Class<E> eClass) {
        return zsetRangeWithScoresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, start, end, eClass);
    }

    /**
     * 获取指定实例中指定范围的元素及其分数。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param start       起始位置
     * @param end         结束位置
     * @param eClass 元素类型
     * @param <E>         元素类型
     * @return 指定范围的元素及其分数的映射
     */
    <E> Map<E, Double> zsetRangeWithScoresForInstance(String instanceId, String key, long start, long end, Class<E> eClass);

    /**
     * 根据字典序获取并存储指定范围的元素。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLex(String key, String lowerBound, String upperBound, String destKey) {
        return zsetRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, destKey);
    }

    /**
     * 根据字典序获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, String destKey) {
        return zsetRangeAndStoreByLexForInstance(instanceId, key, lowerBound, upperBound, destKey, 0L);
    }

    /**
     * 根据字典序获取并存储指定范围的元素。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLex(String key, String lowerBound, String upperBound, String destKey, long expiry) {
        return zsetRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, destKey, expiry);
    }

    /**
     * 根据字典序获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, String destKey, long expiry) {
        return zsetRangeAndStoreByLexForInstance(instanceId, key, lowerBound, upperBound, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 根据字典序获取并存储指定范围的元素。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLex(String key, String lowerBound, String upperBound, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, destKey, expiry, unit);
    }

    /**
     * 根据字典序获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, String destKey, long expiry, TimeUnit unit);

    /**
     * 根据分数获取并存储指定范围的元素。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScore(String key, Number lowerBound, Number upperBound, String destKey) {
        return zsetRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, destKey);
    }

    /**
     * 根据分数获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, String destKey) {
        return zsetRangeAndStoreByScoreForInstance(instanceId, key, lowerBound, upperBound, destKey, 0L);
    }

    /**
     * 根据分数获取并存储指定范围的元素。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScore(String key, Number lowerBound, Number upperBound, String destKey, long expiry) {
        return zsetRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, destKey, expiry);
    }

    /**
     * 根据分数获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, String destKey, long expiry) {
        return zsetRangeAndStoreByScoreForInstance(instanceId, key, lowerBound, upperBound, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 根据分数获取并存储指定范围的元素。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScore(String key, Number lowerBound, Number upperBound, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, destKey, expiry, unit);
    }

    /**
     * 根据分数获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, String destKey, long expiry, TimeUnit unit);

    /**
     * 根据指定的分数范围中反向获取元素，并将其作为集合返回。
     *
     * @param key               有序集合的键
     * @param iPage             分页参数
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    default <E> Set<E> zsetReverseRange(String key, IPage iPage, Class<E> eClass) {
        return zsetReverseRangeForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, iPage, eClass);
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param iPage             分页参数
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    default <E> Set<E> zsetReverseRangeForInstance(String instanceId, String key, IPage iPage, Class<E> eClass) {
        return zsetReverseRangeForInstance(instanceId, key, iPage.getStartIndex(), iPage.getEndIndex(), eClass);
    }

    /**
     * 根据指定的分数范围中反向获取元素，并将其作为集合返回。
     *
     * @param key               有序集合的键
     * @param min               最小分数
     * @param max               最大分数
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    default <E> Set<E> zsetReverseRange(String key, long min, long max, Class<E> eClass) {
        return zsetReverseRangeForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, min, max, eClass);
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param min               最小分数
     * @param max               最大分数
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    <E> Set<E> zsetReverseRangeForInstance(String instanceId, String key, long min, long max, Class<E> eClass);

    /**
     * 根据指定的字典范围中反向获取元素，并将其作为集合返回。
     *
     * @param key               有序集合的键
     * @param lowerBound        下边界
     * @param upperBound        上边界
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    default <E> Set<E> zsetReverseRangeByLex(String key, String lowerBound, String upperBound, Class<E> eClass) {
        return zsetReverseRangeByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, eClass);
    }

    /**
     * 根据指定的字典范围从指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param lowerBound        下边界
     * @param upperBound        上边界
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    <E> Set<E> zsetReverseRangeByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, Class<E> eClass);

    /**
     * 根据指定的分数范围中反向获取元素，并将其作为集合返回。
     *
     * @param key               有序集合的键
     * @param min               最小分数
     * @param max               最大分数
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    default <E> Set<E> zsetReverseRangeByScore(String key, double min, double max, Class<E> eClass) {
        return zsetReverseRangeByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, min, max, eClass);
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param min               最小分数
     * @param max               最大分数
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    <E> Set<E> zsetReverseRangeByScoreForInstance(String instanceId, String key, double min, double max, Class<E> eClass);

    /**
     * 根据指定的分数范围和分页信息中反向获取元素，并将其作为集合返回。
     *
     * @param key               有序集合的键
     * @param min               最小分数
     * @param max               最大分数
     * @param offset            偏移量
     * @param count             数量
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    default <E> Set<E> zsetReverseRangeByScore(String key, double min, double max, long offset, long count, Class<E> eClass) {
        return zsetReverseRangeByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, min, max, offset, count, eClass);
    }

    /**
     * 根据指定的分数范围和分页信息从指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param min               最小分数
     * @param max               最大分数
     * @param offset            偏移量
     * @param count             数量
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    <E> Set<E> zsetReverseRangeByScoreForInstance(String instanceId, String key, double min, double max, long offset, long count, Class<E> eClass);

    /**
     * 根据指定的分数范围中反向获取元素及其分数，并将其作为映射返回。
     *
     * @param key         有序集合的键
     * @param min         最小分数
     * @param max         最大分数
     * @param eClass 元素类型
     * @param <E>         元素类型
     * @return 反向获取的元素及其分数的映射
     */
    default <E> Map<E, Double> zsetReverseRangeByScoreWithScores(String key, double min, double max, Class<E> eClass) {
        return zsetReverseRangeByScoreWithScoresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, min, max, eClass);
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素及其分数，并将其作为映射返回。
     *
     * @param instanceId  实例 ID
     * @param key         有序集合的键
     * @param min         最小分数
     * @param max         最大分数
     * @param eClass 元素类型
     * @param <E>         元素类型
     * @return 反向获取的元素及其分数的映射
     */
    <E> Map<E, Double> zsetReverseRangeByScoreWithScoresForInstance(String instanceId, String key, double min, double max, Class<E> eClass);

    /**
     * 根据指定的分数范围和分页信息中反向获取元素及其分数，并将其作为映射返回。
     *
     * @param key         有序集合的键
     * @param min         最小分数
     * @param max         最大分数
     * @param offset      偏移量
     * @param count       数量
     * @param eClass 元素类型
     * @param <E>         元素类型
     * @return 反向获取的元素及其分数的映射
     */
    default <E> Map<E, Double> zsetReverseRangeByScoreWithScores(String key, double min, double max, long offset, long count, Class<E> eClass) {
        return zsetReverseRangeByScoreWithScoresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, min, max, offset, count, eClass);
    }

    /**
     * 根据指定的分数范围和分页信息从指定的实例中反向获取元素及其分数，并将其作为映射返回。
     *
     * @param instanceId  实例 ID
     * @param key         有序集合的键
     * @param min         最小分数
     * @param max         最大分数
     * @param offset      偏移量
     * @param count       数量
     * @param eClass 元素类型
     * @param <E>         元素类型
     * @return 反向获取的元素及其分数的映射
     */
    <E> Map<E, Double> zsetReverseRangeByScoreWithScoresForInstance(String instanceId, String key, double min, double max, long offset, long count, Class<E> eClass);

    /**
     * 根据指定的范围中反向获取元素及其分数，并将其作为映射返回。
     *
     * @param key         有序集合的键
     * @param start       开始索引
     * @param end         结束索引
     * @param eClass 元素类型
     * @param <E>         元素类型
     * @return 反向获取的元素及其分数的映射
     */
    default <E> Map<E, Double> zsetReverseRangeWithScores(String key, long start, long end, Class<E> eClass) {
        return zsetReverseRangeWithScoresForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, start, end, eClass);
    }

    /**
     * 根据指定的范围从指定的实例中反向获取元素及其分数，并将其作为映射返回。
     *
     * @param instanceId  实例 ID
     * @param key         有序集合的键
     * @param start       开始索引
     * @param end         结束索引
     * @param eClass 元素类型
     * @param <E>         元素类型
     * @return 反向获取的元素及其分数的映射
     */
    <E> Map<E, Double> zsetReverseRangeWithScoresForInstance(String instanceId, String key, long start, long end, Class<E> eClass);

    /**
     * 根据指定的字典范围中反向获取元素，并存储到目标键中。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLex(String key, String lowerBound, String upperBound, String destKey) {
        return zsetReverseRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, destKey);
    }

    /**
     * 根据指定的字典范围从指定的实例中反向获取元素，并存储到目标键中。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, String destKey) {
        return zsetReverseRangeAndStoreByLexForInstance(instanceId, key, lowerBound, upperBound, destKey, 0L);
    }

    /**
     * 根据指定的字典范围中反向获取元素，并存储到目标键中，并设置过期时间。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLex(String key, String lowerBound, String upperBound, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, destKey, expiry);
    }

    /**
     * 根据指定的字典范围从指定的实例中反向获取元素，并存储到目标键中，并设置过期时间。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByLexForInstance(instanceId, key, lowerBound, upperBound, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 根据指定的字典范围中反向获取元素，并存储到目标键中，并设置过期时间和时间单位。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLex(String key, String lowerBound, String upperBound, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, destKey, expiry, unit);
    }

    /**
     * 根据指定的字典范围从指定的实例中反向获取元素，并存储到目标键中，并设置过期时间和时间单位。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, String destKey, long expiry, TimeUnit unit);

    /**
     * 根据指定的分数范围中反向获取元素，并存储到目标键中。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScore(String key, Number lowerBound, Number upperBound, String destKey) {
        return zsetReverseRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, destKey);
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并存储到目标键中。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, String destKey) {
        return zsetReverseRangeAndStoreByScoreForInstance(instanceId, key, lowerBound, upperBound, destKey, 0L);
    }

    /**
     * 根据指定的分数范围中反向获取元素，并存储到目标键中，并设置过期时间。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScore(String key, Number lowerBound, Number upperBound, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, destKey, expiry);
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并存储到目标键中，并设置过期时间。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByScoreForInstance(instanceId, key, lowerBound, upperBound, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 根据指定的分数范围中反向获取元素，并存储到目标键中，并设置过期时间和时间单位。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScore(String key, Number lowerBound, Number upperBound, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, destKey, expiry, unit);
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并存储到目标键中，并设置过期时间和时间单位。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, String destKey, long expiry, TimeUnit unit);

}
