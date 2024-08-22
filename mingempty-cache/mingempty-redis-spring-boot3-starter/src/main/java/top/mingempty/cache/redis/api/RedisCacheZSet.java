package top.mingempty.cache.redis.api;

import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.Limit;
import org.springframework.data.redis.connection.zset.Aggregate;
import org.springframework.data.redis.connection.zset.Weights;
import org.springframework.data.redis.core.ScanOptions;
import top.mingempty.cache.commons.api.CacheZSet;
import top.mingempty.domain.other.GlobalConstant;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 有序集合接口，用于对缓存中的有序集合进行操作。
 * </p>
 * 对redis的增强
 *
 * @author zzhao
 */
public interface RedisCacheZSet extends CacheZSet {

    /**
     * 获取当前缓存实例的有序集合缓存API。
     * <p>
     * 返回当前实例的 {@link RedisCacheZSet} 类型的API。
     * </p>
     *
     * @return 当前缓存实例的有序集合缓存集合
     */
    default RedisCacheZSet getRedisCacheZSet() {
        return this;
    }


    /**
     * 获取符合指定格式的元素集合
     *
     * @param instanceId        实例ID
     * @param key               元素的键
     * @param pattern           元素格式
     * @param eClass 元素的类型
     * @return 操作结果
     */
    @Override
    default <E> Map<E, Double> zsetScanForInstance(String instanceId, String key, String pattern,  Class<E> eClass) {
        if (pattern == null) {
            pattern = "*";
        }
        if (!pattern.startsWith("*")) {
            pattern = "*".concat(pattern);
        }
        if (!pattern.endsWith("*")) {
            pattern = pattern.concat("*");
        }
        return zsetScanForInstance(instanceId, key, ScanOptions.scanOptions().match(pattern).build(), eClass);
    }

    /**
     * 获取符合指定格式的元素集合
     *
     * @param key               元素的键
     * @param options           元素格式
     * @param eClass 元素的类型
     * @return 操作结果
     */
    default <E> Map<E, Double> zsetScan(String key, ScanOptions options,  Class<E> eClass) {
        return zsetScanForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, options, eClass);
    }

    /**
     * 获取符合指定格式的元素集合
     *
     * @param instanceId        实例ID
     * @param key               元素的键
     * @param options           元素格式
     * @param eClass 元素的类型
     * @return 操作结果
     */
    <E> Map<E, Double> zsetScanForInstance(String instanceId, String key, ScanOptions options,  Class<E> eClass);

    /**
     * 移除指定实例和键的有序集合中，值在指定范围内的所有元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 范围的下限
     * @param upperBound 范围的上限
     * @return 移除的元素数量(- 1表示移除异常)
     */
    @Override
    default Long zsetRemoveRangeByLexForInstance(String instanceId, String key, String lowerBound, String upperBound) {
        return zsetRemoveRangeByLexForInstance(instanceId, key, Range.closed(lowerBound, upperBound));
    }

    /**
     * 移除指定键的有序集合中，值在指定范围内的所有元素。
     *
     * @param key   有序集合的键
     * @param range 范围
     * @return 移除的元素数量(- 1表示移除异常)
     */
    default Long zsetRemoveRangeByLex(String key, Range<String> range) {
        return zsetRemoveRangeByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range);
    }

    /**
     * 移除指定实例和键的有序集合中，值在指定范围内的所有元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      范围
     * @return 移除的元素数量(- 1表示移除异常)
     */
    Long zsetRemoveRangeByLexForInstance(String instanceId, String key, Range<String> range);

    /**
     * 计算给定的集合和多个其他集合的交集。
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param eClass 元素的类型
     * @param aggregate:  分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 集合中的所有元素
     */
    default <E> Map<E, Double> zsetIntersectWithStore(String key, Collection<String> otherKeys, Class<E> eClass, Aggregate aggregate) {
        return zsetIntersectWithStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, eClass, aggregate);
    }

    /**
     * 计算给定的集合和多个其他集合的交集。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param eClass 元素的类型
     * @param aggregate:  分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 集合中的所有元素
     */
    default <E> Map<E, Double> zsetIntersectWithStoreForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> eClass, Aggregate aggregate) {
        return zsetIntersectWithStoreForInstance(instanceId, key, otherKeys, eClass, aggregate, Weights.fromSetCount(1 + otherKeys.size()));
    }

    /**
     * 计算给定的集合和多个其他集合的交集。
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param eClass 元素的类型
     * @param aggregate:  分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:    每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 集合中的所有元素
     */
    default <E> Map<E, Double> zsetIntersectWithStore(String key, Collection<String> otherKeys, Class<E> eClass, Aggregate aggregate, Weights weights) {
        return zsetIntersectWithStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, eClass, aggregate, weights);
    }

    /**
     * 计算给定的集合和多个其他集合的交集。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param eClass 元素的类型
     * @param aggregate:  分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:    每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 集合中的所有元素
     */
    <E> Map<E, Double> zsetIntersectWithStoreForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> eClass, Aggregate aggregate, Weights weights);

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate) {
        return zsetIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, aggregate);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate) {
        return zsetIntersectAndStoreForInstance(instanceId, key, otherKeys, destKey, aggregate, 0L);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, long expiry) {
        return zsetIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, aggregate, expiry);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, long expiry) {
        return zsetIntersectAndStoreForInstance(instanceId, key, otherKeys, destKey, aggregate, expiry, TimeUnit.SECONDS);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, long expiry, TimeUnit unit) {
        return zsetIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, aggregate, expiry, unit);
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
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, long expiry, TimeUnit unit) {
        return zsetIntersectAndStoreForInstance(instanceId, key, otherKeys, destKey, aggregate, Weights.fromSetCount(1 + otherKeys.size()), expiry, unit);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights) {
        return zsetIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, aggregate, weights);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights) {
        return zsetIntersectAndStoreForInstance(instanceId, key, otherKeys, destKey, aggregate, weights, 0L);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights, long expiry) {
        return zsetIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, aggregate, weights, expiry);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights, long expiry) {
        return zsetIntersectAndStoreForInstance(instanceId, key, otherKeys, destKey, aggregate, weights, expiry, TimeUnit.SECONDS);
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 存储的元素数量
     */
    default Long zsetIntersectAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights, long expiry, TimeUnit unit) {
        return zsetIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, aggregate, weights, expiry, unit);
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
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 存储的元素数量
     */
    Long zsetIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights, long expiry, TimeUnit unit);

    /**
     * 计算给定的集合和多个其他集合的并集。
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param eClass 元素的类型
     * @param aggregate:  分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 集合中的所有元素
     */
    default <E> Map<E, Double> zsetUnionWithStore(String key, Collection<String> otherKeys, Class<E> eClass, Aggregate aggregate) {
        return zsetUnionWithStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, eClass, aggregate);
    }

    /**
     * 计算给定的集合和多个其他集合的并集。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param eClass 元素的类型
     * @param aggregate:  分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 集合中的所有元素
     */
    default <E> Map<E, Double> zsetUnionWithStoreForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> eClass, Aggregate aggregate) {
        return zsetUnionWithStoreForInstance(instanceId, key, otherKeys, eClass, aggregate, Weights.fromSetCount(1 + otherKeys.size()));
    }

    /**
     * 计算给定的集合和多个其他集合的并集。
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param eClass 元素的类型
     * @param aggregate:  分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:    每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 集合中的所有元素
     */
    default <E> Map<E, Double> zsetUnionWithStore(String key, Collection<String> otherKeys, Class<E> eClass, Aggregate aggregate, Weights weights) {
        return zsetUnionWithStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, eClass, aggregate, weights);
    }

    /**
     * 计算给定的集合和多个其他集合的并集。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param eClass 元素的类型
     * @param aggregate:  分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:    每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 集合中的所有元素
     */
    <E> Map<E, Double> zsetUnionWithStoreForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> eClass, Aggregate aggregate, Weights weights);

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate) {
        return zsetUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, aggregate);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate) {
        return zsetUnionAndStoreForInstance(instanceId, key, otherKeys, destKey, aggregate, 0L);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, long expiry) {
        return zsetUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, aggregate, expiry);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, long expiry) {
        return zsetUnionAndStoreForInstance(instanceId, key, otherKeys, destKey, aggregate, expiry, TimeUnit.SECONDS);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, long expiry, TimeUnit unit) {
        return zsetUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, aggregate, expiry, unit);
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
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, long expiry, TimeUnit unit) {
        return zsetUnionAndStoreForInstance(instanceId, key, otherKeys, destKey, aggregate, Weights.fromSetCount(1 + otherKeys.size()), expiry, unit);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights) {
        return zsetUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, aggregate, weights);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights) {
        return zsetUnionAndStoreForInstance(instanceId, key, otherKeys, destKey, aggregate, weights, 0L);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights, long expiry) {
        return zsetUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, aggregate, weights, expiry);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights, long expiry) {
        return zsetUnionAndStoreForInstance(instanceId, key, otherKeys, destKey, aggregate, weights, expiry, TimeUnit.SECONDS);
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 存储的元素数量
     */
    default Long zsetUnionAndStore(String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights, long expiry, TimeUnit unit) {
        return zsetUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, aggregate, weights, expiry, unit);
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
     * @param aggregate: 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights:   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 存储的元素数量
     */
    Long zsetUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights, long expiry, TimeUnit unit);

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
    @Override
    default <E> Set<E> zsetRangeByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, Class<E> eClass) {
        return zsetRangeByLexForInstance(instanceId, key, Range.closed(lowerBound, upperBound), eClass);
    }

    /**
     * 根据字典序获取指定范围的元素。
     *
     * @param key               有序集合的键
     * @param range             范围
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    default <E> Set<E> zsetRangeByLex(String key, Range<String> range, Class<E> eClass) {
        return zsetRangeByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, eClass);
    }

    /**
     * 根据字典序获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param range             范围
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    default <E> Set<E> zsetRangeByLexForInstance(String instanceId, String key, Range<String> range, Class<E> eClass) {
        return zsetRangeByLexForInstance(instanceId, key, range, Limit.unlimited(), eClass);
    }

    /**
     * 根据字典序获取指定范围的元素。
     *
     * @param key               有序集合的键
     * @param lowerBound        下边界
     * @param upperBound        上边界
     * @param limit             限制
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    default <E> Set<E> zsetRangeByLex(String key, String lowerBound, String upperBound, Limit limit, Class<E> eClass) {
        return zsetRangeByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, eClass);
    }

    /**
     * 根据字典序获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param lowerBound        下边界
     * @param upperBound        上边界
     * @param limit             限制
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    default <E> Set<E> zsetRangeByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, Limit limit, Class<E> eClass) {
        return zsetRangeByLexForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, eClass);
    }

    /**
     * 根据字典序获取指定范围的元素。
     *
     * @param key               有序集合的键
     * @param range             范围
     * @param limit             限制
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    default <E> Set<E> zsetRangeByLex(String key, Range<String> range, Limit limit, Class<E> eClass) {
        return zsetRangeByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, eClass);
    }

    /**
     * 根据字典序获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param range             范围
     * @param limit             限制
     * @param eClass 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    <E> Set<E> zsetRangeByLexForInstance(String instanceId, String key, Range<String> range, Limit limit, Class<E> eClass);

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
    @Override
    default Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByLexForInstance(instanceId, key, Range.closed(lowerBound, upperBound), destKey, expiry, unit);
    }

    /**
     * 根据字典序获取并存储指定范围的元素。
     *
     * @param key     有序集合的键
     * @param range   范围
     * @param destKey 目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLex(String key, Range<String> range, String destKey) {
        return zsetRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, destKey);
    }

    /**
     * 根据字典序获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      范围
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, Range<String> range, String destKey) {
        return zsetRangeAndStoreByLexForInstance(instanceId, key, range, Limit.unlimited(), destKey);
    }

    /**
     * 根据字典序获取并存储指定范围的元素。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLex(String key, String lowerBound, String upperBound, Limit limit, String destKey) {
        return zsetRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, destKey);
    }

    /**
     * 根据字典序获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, Limit limit, String destKey) {
        return zsetRangeAndStoreByLexForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, destKey);
    }

    /**
     * 根据字典序获取并存储指定范围的元素。
     *
     * @param key     有序集合的键
     * @param range   范围
     * @param limit   限制
     * @param destKey 目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLex(String key, Range<String> range, Limit limit, String destKey) {
        return zsetRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, destKey);
    }

    /**
     * 根据字典序获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      范围
     * @param limit      限制
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, Range<String> range, Limit limit, String destKey) {
        return zsetRangeAndStoreByLexForInstance(instanceId, key, range, limit, destKey, 0L);
    }

    /**
     * 根据字典序获取并存储指定范围的元素。
     *
     * @param key     有序集合的键
     * @param range   范围
     * @param destKey 目标键
     * @param expiry  过期时间
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLex(String key, Range<String> range, String destKey, long expiry) {
        return zsetRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, destKey, expiry);
    }

    /**
     * 根据字典序获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      范围
     * @param destKey    目标键
     * @param expiry     过期时间
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, Range<String> range, String destKey, long expiry) {
        return zsetRangeAndStoreByLexForInstance(instanceId, key, range, Limit.unlimited(), destKey, expiry);
    }

    /**
     * 根据字典序获取并存储指定范围的元素。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制
     * @param destKey    目标键
     * @param expiry     过期时间
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLex(String key, String lowerBound, String upperBound, Limit limit, String destKey, long expiry) {
        return zsetRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, destKey, expiry);
    }

    /**
     * 根据字典序获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制
     * @param destKey    目标键
     * @param expiry     过期时间
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, Limit limit, String destKey, long expiry) {
        return zsetRangeAndStoreByLexForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, destKey, expiry);
    }

    /**
     * 根据字典序获取并存储指定范围的元素。
     *
     * @param key     有序集合的键
     * @param range   范围
     * @param limit   限制
     * @param destKey 目标键
     * @param expiry  过期时间
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLex(String key, Range<String> range, Limit limit, String destKey, long expiry) {
        return zsetRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, destKey, expiry);
    }

    /**
     * 根据字典序获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      范围
     * @param limit      限制
     * @param destKey    目标键
     * @param expiry     过期时间
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, Range<String> range, Limit limit, String destKey, long expiry) {
        return zsetRangeAndStoreByLexForInstance(instanceId, key, range, limit, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 根据字典序获取并存储指定范围的元素。
     *
     * @param key     有序集合的键
     * @param range   范围
     * @param destKey 目标键
     * @param expiry  过期时间
     * @param unit    时间单位
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLex(String key, Range<String> range, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, destKey, expiry, unit);
    }

    /**
     * 根据字典序获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      范围
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, Range<String> range, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByLexForInstance(instanceId, key, range, Limit.unlimited(), destKey, expiry, unit);
    }

    /**
     * 根据字典序获取并存储指定范围的元素。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLex(String key, String lowerBound, String upperBound, Limit limit, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, destKey, expiry, unit);
    }

    /**
     * 根据字典序获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, Limit limit, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByLexForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, destKey, expiry, unit);
    }

    /**
     * 根据字典序获取并存储指定范围的元素。
     *
     * @param key     有序集合的键
     * @param range   范围
     * @param limit   限制
     * @param destKey 目标键
     * @param expiry  过期时间
     * @param unit    时间单位
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByLex(String key, Range<String> range, Limit limit, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, destKey, expiry, unit);
    }

    /**
     * 根据字典序获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      范围
     * @param limit      限制
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, Range<String> range, Limit limit, String destKey, long expiry, TimeUnit unit);

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
    @Override
    default Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByScoreForInstance(instanceId, key, Range.closed(lowerBound, upperBound), destKey, expiry, unit);
    }

    /**
     * 根据分数范围获取并存储指定范围的元素。
     *
     * @param key     有序集合的键
     * @param range   分数范围
     * @param destKey 目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScore(String key, Range<Number> range, String destKey) {
        return zsetRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, destKey);
    }

    /**
     * 根据分数范围获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      分数范围
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, String destKey) {
        return zsetRangeAndStoreByScoreForInstance(instanceId, key, range, Limit.unlimited(), destKey);
    }

    /**
     * 根据分数范围获取并存储指定范围的元素。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScore(String key, Number lowerBound, Number upperBound, Limit limit, String destKey) {
        return zsetRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, destKey);
    }

    /**
     * 根据分数范围获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, Limit limit, String destKey) {
        return zsetRangeAndStoreByScoreForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, destKey);
    }

    /**
     * 根据分数范围获取并存储指定范围的元素。
     *
     * @param key     有序集合的键
     * @param range   分数范围
     * @param limit   限制
     * @param destKey 目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScore(String key, Range<Number> range, Limit limit, String destKey) {
        return zsetRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, destKey);
    }

    /**
     * 根据分数范围获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      分数范围
     * @param limit      限制
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, Limit limit, String destKey) {
        return zsetRangeAndStoreByScoreForInstance(instanceId, key, range, limit, destKey, 0L);
    }

    /**
     * 根据分数范围获取并存储指定范围的元素。
     *
     * @param key     有序集合的键
     * @param range   分数范围
     * @param destKey 目标键
     * @param expiry  过期时间
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScore(String key, Range<Number> range, String destKey, long expiry) {
        return zsetRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, destKey, expiry);
    }

    /**
     * 根据分数范围获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      分数范围
     * @param destKey    目标键
     * @param expiry     过期时间
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, String destKey, long expiry) {
        return zsetRangeAndStoreByScoreForInstance(instanceId, key, range, Limit.unlimited(), destKey, expiry);
    }

    /**
     * 根据分数范围获取并存储指定范围的元素。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制
     * @param destKey    目标键
     * @param expiry     过期时间
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScore(String key, Number lowerBound, Number upperBound, Limit limit, String destKey, long expiry) {
        return zsetRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, destKey, expiry);
    }

    /**
     * 根据分数范围获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制
     * @param destKey    目标键
     * @param expiry     过期时间
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, Limit limit, String destKey, long expiry) {
        return zsetRangeAndStoreByScoreForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, destKey, expiry);
    }

    /**
     * 根据分数范围获取并存储指定范围的元素。
     *
     * @param key     有序集合的键
     * @param range   分数范围
     * @param limit   限制
     * @param destKey 目标键
     * @param expiry  过期时间
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScore(String key, Range<Number> range, Limit limit, String destKey, long expiry) {
        return zsetRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, destKey, expiry);
    }

    /**
     * 根据分数范围获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      分数范围
     * @param limit      限制
     * @param destKey    目标键
     * @param expiry     过期时间
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, Limit limit, String destKey, long expiry) {
        return zsetRangeAndStoreByScoreForInstance(instanceId, key, range, limit, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 根据分数范围获取并存储指定范围的元素。
     *
     * @param key     有序集合的键
     * @param range   分数范围
     * @param destKey 目标键
     * @param expiry  过期时间
     * @param unit    时间单位
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScore(String key, Range<Number> range, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, destKey, expiry, unit);
    }

    /**
     * 根据分数范围获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      分数范围
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByScoreForInstance(instanceId, key, range, Limit.unlimited(), destKey, expiry, unit);
    }

    /**
     * 根据分数范围获取并存储指定范围的元素。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScore(String key, Number lowerBound, Number upperBound, Limit limit, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, destKey, expiry, unit);
    }

    /**
     * 根据分数范围获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, Limit limit, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByScoreForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, destKey, expiry, unit);
    }

    /**
     * 根据分数范围获取并存储指定范围的元素。
     *
     * @param key     有序集合的键
     * @param range   分数范围
     * @param limit   限制
     * @param destKey 目标键
     * @param expiry  过期时间
     * @param unit    时间单位
     * @return 存储的元素数量
     */
    default Long zsetRangeAndStoreByScore(String key, Range<Number> range, Limit limit, String destKey, long expiry, TimeUnit unit) {
        return zsetRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, destKey, expiry, unit);
    }

    /**
     * 根据分数范围获取并存储指定实例中指定范围的元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      分数范围
     * @param limit      限制
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       时间单位
     * @return 存储的元素数量
     */
    Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, Limit limit, String destKey, long expiry, TimeUnit unit);

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
    @Override
    default <E> Set<E> zsetReverseRangeByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, Class<E> eClass) {
        return zsetReverseRangeByLexForInstance(instanceId, key, Range.closed(lowerBound, upperBound), eClass);
    }

    /**
     * 根据指定的字典范围中反向获取元素，并将其作为集合返回。
     *
     * @param key               有序集合的键
     * @param range             字典范围（包含下限和上限）
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    default <E> Set<E> zsetReverseRangeByLex(String key, Range<String> range, Class<E> eClass) {
        return zsetReverseRangeByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, eClass);
    }

    /**
     * 根据指定的字典范围从指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param range             字典范围（包含下限和上限）
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    default <E> Set<E> zsetReverseRangeByLexForInstance(String instanceId, String key, Range<String> range, Class<E> eClass) {
        return zsetReverseRangeByLexForInstance(instanceId, key, range, Limit.unlimited(), eClass);
    }

    /**
     * 根据指定的字典范围和限制条件中反向获取元素，并将其作为集合返回。
     *
     * @param key               有序集合的键
     * @param lowerBound        下边界
     * @param upperBound        上边界
     * @param limit             限制条件（例如最大数量）
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    default <E> Set<E> zsetReverseRangeByLex(String key, String lowerBound, String upperBound, Limit limit, Class<E> eClass) {
        return zsetReverseRangeByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, eClass);
    }

    /**
     * 根据指定的字典范围、限制条件和指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param lowerBound        下边界
     * @param upperBound        上边界
     * @param limit             限制条件（例如最大数量）
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    default <E> Set<E> zsetReverseRangeByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, Limit limit, Class<E> eClass) {
        return zsetReverseRangeByLexForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, eClass);
    }

    /**
     * 根据指定的字典范围和限制条件中反向获取元素，并将其作为集合返回。
     *
     * @param key               有序集合的键
     * @param range             字典范围（包含下限和上限）
     * @param limit             限制条件（例如最大数量）
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    default <E> Set<E> zsetReverseRangeByLex(String key, Range<String> range, Limit limit, Class<E> eClass) {
        return zsetReverseRangeByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, eClass);
    }

    /**
     * 根据指定的字典范围和限制条件从指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param range             字典范围（包含下限和上限）
     * @param limit             限制条件（例如最大数量）
     * @param eClass 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    <E> Set<E> zsetReverseRangeByLexForInstance(String instanceId, String key, Range<String> range, Limit limit, Class<E> eClass);

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
    @Override
    default Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByLexForInstance(instanceId, key, Range.closed(lowerBound, upperBound), destKey, expiry, unit);
    }

    /**
     * 根据指定的字典范围中反向获取元素，并将其存储到目标键中。
     *
     * @param key     有序集合的键
     * @param range   字典范围（包含下限和上限）
     * @param destKey 目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLex(String key, Range<String> range, String destKey) {
        return zsetReverseRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, destKey);
    }

    /**
     * 根据指定的字典范围从指定的实例中反向获取元素，并将其存储到目标键中。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param range      字典范围（包含下限和上限）
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, Range<String> range, String destKey) {
        return zsetReverseRangeAndStoreByLexForInstance(instanceId, key, range, Limit.unlimited(), destKey);
    }

    /**
     * 根据指定的字典范围、限制条件中反向获取元素，并将其存储到目标键中。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制条件（例如最大数量）
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLex(String key, String lowerBound, String upperBound, Limit limit, String destKey) {
        return zsetReverseRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, destKey);
    }

    /**
     * 根据指定的字典范围、限制条件从指定的实例中反向获取元素，并将其存储到目标键中。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制条件（例如最大数量）
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, Limit limit, String destKey) {
        return zsetReverseRangeAndStoreByLexForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, destKey);
    }

    /**
     * 根据指定的字典范围、限制条件中反向获取元素，并将其存储到目标键中。
     *
     * @param key     有序集合的键
     * @param range   字典范围（包含下限和上限）
     * @param limit   限制条件（例如最大数量）
     * @param destKey 目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLex(String key, Range<String> range, Limit limit, String destKey) {
        return zsetReverseRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, destKey);
    }

    /**
     * 根据指定的字典范围、限制条件从指定的实例中反向获取元素，并将其存储到目标键中。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param range      字典范围（包含下限和上限）
     * @param limit      限制条件（例如最大数量）
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, Range<String> range, Limit limit, String destKey) {
        return zsetReverseRangeAndStoreByLexForInstance(instanceId, key, range, limit, destKey, 0L);
    }

    /**
     * 根据指定的字典范围中反向获取元素，并将其存储到目标键中，并设置过期时间。
     *
     * @param key     有序集合的键
     * @param range   字典范围（包含下限和上限）
     * @param destKey 目标键
     * @param expiry  过期时间（单位：秒）
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLex(String key, Range<String> range, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, destKey, expiry);
    }

    /**
     * 根据指定的字典范围从指定的实例中反向获取元素，并将其存储到目标键中，并设置过期时间。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param range      字典范围（包含下限和上限）
     * @param destKey    目标键
     * @param expiry     过期时间（单位：秒）
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, Range<String> range, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByLexForInstance(instanceId, key, range, Limit.unlimited(), destKey, expiry);
    }

    /**
     * 根据指定的字典范围、限制条件中反向获取元素，并将其存储到目标键中，并设置过期时间。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制条件（例如最大数量）
     * @param destKey    目标键
     * @param expiry     过期时间（单位：秒）
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLex(String key, String lowerBound, String upperBound, Limit limit, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, destKey, expiry);
    }

    /**
     * 根据指定的字典范围、限制条件从指定的实例中反向获取元素，并将其存储到目标键中，并设置过期时间。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制条件（例如最大数量）
     * @param destKey    目标键
     * @param expiry     过期时间（单位：秒）
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, Limit limit, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByLexForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, destKey, expiry);
    }

    /**
     * 根据指定的字典范围、限制条件中反向获取元素，并将其存储到目标键中，并设置过期时间。
     *
     * @param key     有序集合的键
     * @param range   字典范围（包含下限和上限）
     * @param limit   限制条件（例如最大数量）
     * @param destKey 目标键
     * @param expiry  过期时间
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLex(String key, Range<String> range, Limit limit, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, destKey, expiry);
    }

    /**
     * 根据指定的字典范围、限制条件从指定的实例中反向获取元素，并将其存储到目标键中，并设置过期时间。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param range      字典范围（包含下限和上限）
     * @param limit      限制条件（例如最大数量）
     * @param destKey    目标键
     * @param expiry     过期时间
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, Range<String> range, Limit limit, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByLexForInstance(instanceId, key, range, limit, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 根据指定的字典范围中反向获取元素，并将其存储到目标键中，设置过期时间和时间单位。
     *
     * @param key     有序集合的键
     * @param range   字典范围（包含下限和上限）
     * @param destKey 目标键
     * @param expiry  过期时间
     * @param unit    过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLex(String key, Range<String> range, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, destKey, expiry, unit);
    }

    /**
     * 根据指定的字典范围从指定的实例中反向获取元素，并将其存储到目标键中，设置过期时间和时间单位。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param range      字典范围（包含下限和上限）
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, Range<String> range, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByLexForInstance(instanceId, key, range, Limit.unlimited(), destKey, expiry, unit);
    }

    /**
     * 根据指定的字典范围、限制条件中反向获取元素，并将其存储到目标键中，并设置过期时间和时间单位。
     *
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制条件（例如最大数量）
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLex(String key, String lowerBound, String upperBound, Limit limit, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, destKey, expiry, unit);
    }

    /**
     * 根据指定的字典范围、限制条件从指定的实例中反向获取元素，并将其存储到目标键中，并设置过期时间和时间单位。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param lowerBound 下边界
     * @param upperBound 上边界
     * @param limit      限制条件（例如最大数量）
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, Limit limit, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByLexForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, destKey, expiry, unit);
    }

    /**
     * 根据指定的字典范围中反向获取元素，并将其存储到目标键中，设置限制、过期时间和时间单位。
     *
     * @param key     有序集合的键
     * @param range   字典范围（包含下限和上限）
     * @param limit   限制（如最大元素数量）
     * @param destKey 目标键
     * @param expiry  过期时间
     * @param unit    过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByLex(String key, Range<String> range, Limit limit, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByLexForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, destKey, expiry, unit);
    }

    /**
     * 根据指定的字典范围从指定的实例中反向获取元素，并将其存储到目标键中，设置限制、过期时间和时间单位。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param range      字典范围（包含下限和上限）
     * @param limit      限制（如最大元素数量）
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, Range<String> range, Limit limit, String destKey, long expiry, TimeUnit unit);

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
    @Override
    default Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByScoreForInstance(instanceId, key, Range.closed(lowerBound, upperBound), destKey, expiry, unit);
    }

    /**
     * 根据指定的分数范围中反向获取元素，并将其存储到目标键中。
     *
     * @param key     有序集合的键
     * @param range   分数范围（包含下限和上限）
     * @param destKey 目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScore(String key, Range<Number> range, String destKey) {
        return zsetReverseRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, destKey);
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并将其存储到目标键中。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param range      分数范围（包含下限和上限）
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, String destKey) {
        return zsetReverseRangeAndStoreByScoreForInstance(instanceId, key, range, Limit.unlimited(), destKey);
    }

    /**
     * 根据指定的分数范围和限制中反向获取元素，并将其存储到目标键中。
     *
     * @param key        有序集合的键
     * @param lowerBound 下限分数
     * @param upperBound 上限分数
     * @param limit      限制（如最大元素数量）
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScore(String key, Number lowerBound, Number upperBound, Limit limit, String destKey) {
        return zsetReverseRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, destKey);
    }

    /**
     * 根据指定的分数范围和限制从指定的实例中反向获取元素，并将其存储到目标键中。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param lowerBound 下限分数
     * @param upperBound 上限分数
     * @param limit      限制（如最大元素数量）
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, Limit limit, String destKey) {
        return zsetReverseRangeAndStoreByScoreForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, destKey);
    }

    /**
     * 根据指定的分数范围和限制中反向获取元素，并将其存储到目标键中。
     *
     * @param key     有序集合的键
     * @param range   分数范围（包含下限和上限）
     * @param limit   限制（如最大元素数量）
     * @param destKey 目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScore(String key, Range<Number> range, Limit limit, String destKey) {
        return zsetReverseRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, destKey);
    }

    /**
     * 根据指定的分数范围和限制从指定的实例中反向获取元素，并将其存储到目标键中。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param range      分数范围（包含下限和上限）
     * @param limit      限制（如最大元素数量）
     * @param destKey    目标键
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, Limit limit, String destKey) {
        return zsetReverseRangeAndStoreByScoreForInstance(instanceId, key, range, limit, destKey, 0L);
    }

    /**
     * 根据指定的分数范围中反向获取元素，并将其存储到目标键中，设置过期时间。
     *
     * @param key     有序集合的键
     * @param range   分数范围（包含下限和上限）
     * @param destKey 目标键
     * @param expiry  过期时间
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScore(String key, Range<Number> range, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, destKey, expiry);
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并将其存储到目标键中，设置过期时间。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param range      分数范围（包含下限和上限）
     * @param destKey    目标键
     * @param expiry     过期时间
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByScoreForInstance(instanceId, key, range, Limit.unlimited(), destKey, expiry);
    }

    /**
     * 根据指定的分数范围和限制中反向获取元素，并将其存储到目标键中，设置过期时间。
     *
     * @param key        有序集合的键
     * @param lowerBound 下限分数
     * @param upperBound 上限分数
     * @param limit      限制（如最大元素数量）
     * @param destKey    目标键
     * @param expiry     过期时间
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScore(String key, Number lowerBound, Number upperBound, Limit limit, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, destKey, expiry);
    }

    /**
     * 根据指定的分数范围和限制从指定的实例中反向获取元素，并将其存储到目标键中，设置过期时间。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param lowerBound 下限分数
     * @param upperBound 上限分数
     * @param limit      限制（如最大元素数量）
     * @param destKey    目标键
     * @param expiry     过期时间
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, Limit limit, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByScoreForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, destKey, expiry);
    }

    /**
     * 根据指定的分数范围和限制中反向获取元素，并将其存储到目标键中，设置过期时间。
     *
     * @param key     有序集合的键
     * @param range   分数范围（包含下限和上限）
     * @param limit   限制（如最大元素数量）
     * @param destKey 目标键
     * @param expiry  过期时间
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScore(String key, Range<Number> range, Limit limit, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, destKey, expiry);
    }

    /**
     * 根据指定的分数范围和限制从指定的实例中反向获取元素，并将其存储到目标键中，设置过期时间。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param range      分数范围（包含下限和上限）
     * @param limit      限制（如最大元素数量）
     * @param destKey    目标键
     * @param expiry     过期时间
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, Limit limit, String destKey, long expiry) {
        return zsetReverseRangeAndStoreByScoreForInstance(instanceId, key, range, limit, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 根据指定的分数范围中反向获取元素，并将其存储到目标键中，设置过期时间和时间单位。
     *
     * @param key     有序集合的键
     * @param range   分数范围（包含下限和上限）
     * @param destKey 目标键
     * @param expiry  过期时间
     * @param unit    过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScore(String key, Range<Number> range, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, destKey, expiry, unit);
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并将其存储到目标键中，设置过期时间和时间单位。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param range      分数范围（包含下限和上限）
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByScoreForInstance(instanceId, key, range, Limit.unlimited(), destKey, expiry, unit);
    }

    /**
     * 根据指定的分数范围和限制中反向获取元素，并将其存储到目标键中，设置过期时间和时间单位。
     *
     * @param key        有序集合的键
     * @param lowerBound 下限分数
     * @param upperBound 上限分数
     * @param limit      限制（如最大元素数量）
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScore(String key, Number lowerBound, Number upperBound, Limit limit, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, lowerBound, upperBound, limit, destKey, expiry, unit);
    }

    /**
     * 根据指定的分数范围和限制中反向获取元素，并将其存储到目标键中，设置过期时间和时间单位。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param lowerBound 下限分数
     * @param upperBound 上限分数
     * @param limit      限制（如最大元素数量）
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, Limit limit, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByScoreForInstance(instanceId, key, Range.closed(lowerBound, upperBound), limit, destKey, expiry, unit);
    }

    /**
     * 根据指定的分数范围中反向获取元素，并将其存储到目标键中，设置过期时间和时间单位。
     *
     * @param key     有序集合的键
     * @param range   分数范围（包含下限和上限）
     * @param limit   限制（如最大元素数量）
     * @param destKey 目标键
     * @param expiry  过期时间
     * @param unit    过期时间单位
     * @return 存储的元素数量
     */
    default Long zsetReverseRangeAndStoreByScore(String key, Range<Number> range, Limit limit, String destKey, long expiry, TimeUnit unit) {
        return zsetReverseRangeAndStoreByScoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, range, limit, destKey, expiry, unit);
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并将其存储到目标键中，设置过期时间和时间单位。
     *
     * @param instanceId 实例 ID
     * @param key        有序集合的键
     * @param range      分数范围（包含下限和上限）
     * @param limit      限制（如最大元素数量）
     * @param destKey    目标键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, Limit limit, String destKey, long expiry, TimeUnit unit);

}
