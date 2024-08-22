package top.mingempty.cache.commons.api;

import top.mingempty.domain.base.IPage;
import top.mingempty.domain.other.GlobalConstant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 集合接口，用于对缓存中的集合进行操作。
 *
 * @author zzhao
 */
public interface CacheSet {

    /**
     * 获取当前缓存实例的集合缓存API。
     * <p>
     * 返回当前实例的 {@link CacheSet} 类型的API。
     * </p>
     *
     * @return 当前缓存实例的集合缓存集合
     */
    default CacheSet getCacheSet() {
        return this;
    }

    /**
     * 向指定键的集合中添加元素。
     *
     * @param <E>     元素类型
     * @param key     集合的键
     * @param element 要添加的元素
     * @return 是否添加成功
     */
    default <E> Boolean setAdd(String key, E element) {
        return setAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element);
    }

    /**
     * 向指定实例和键的集合中添加元素。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param element    要添加的元素
     * @return 是否添加成功
     */
    default <E> Boolean setAddForInstance(String instanceId, String key, E element) {
        return setAddForInstance(instanceId, key, element, 0L);
    }

    /**
     * 向指定键的集合中添加元素。
     *
     * @param <E>     元素类型
     * @param key     集合的键
     * @param element 要添加的元素
     * @param expiry  过期时间(秒)
     * @return 是否添加成功
     */
    default <E> Boolean setAdd(String key, E element, long expiry) {
        return setAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element, expiry);
    }

    /**
     * 向指定实例和键的集合中添加元素。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param element    要添加的元素
     * @param expiry     过期时间(秒)
     * @return 是否添加成功
     */
    default <E> Boolean setAddForInstance(String instanceId, String key, E element, long expiry) {
        return setAddForInstance(instanceId, key, element, expiry, TimeUnit.SECONDS);
    }

    /**
     * 向指定键的集合中添加元素。
     *
     * @param <E>     元素类型
     * @param key     集合的键
     * @param element 要添加的元素
     * @param expiry  过期时间
     * @param unit    过期时间单位
     * @return 是否添加成功
     */
    default <E> Boolean setAdd(String key, E element, long expiry, TimeUnit unit) {
        return setAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element, expiry, unit);
    }

    /**
     * 向指定实例和键的集合中添加元素。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param element    要添加的元素
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 是否添加成功
     */
    default <E> Boolean setAddForInstance(String instanceId, String key, E element, long expiry, TimeUnit unit) {
        return setAddForInstance(instanceId, key, Set.of(element), expiry, unit);
    }

    /**
     * 向指定键的集合中添加元素集合。
     *
     * @param <E>      元素类型
     * @param key      集合的键
     * @param elements 要添加的元素集合
     * @return 是否添加成功
     */
    default <E> Boolean setAdd(String key, Collection<E> elements) {
        return setAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, elements);
    }

    /**
     * 向指定实例和键的集合中添加元素集合。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param elements   要添加的元素集合
     * @return 是否添加成功
     */
    default <E> Boolean setAddForInstance(String instanceId, String key, Collection<E> elements) {
        return setAddForInstance(instanceId, key, elements, 0L, TimeUnit.SECONDS);
    }

    /**
     * 向指定键的集合中添加元素集合。
     *
     * @param <E>      元素类型
     * @param key      集合的键
     * @param elements 要添加的元素集合
     * @param expiry   过期时间(秒)
     * @return 是否添加成功
     */
    default <E> Boolean setAdd(String key, Collection<E> elements, long expiry) {
        return setAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, elements, expiry);
    }

    /**
     * 向指定实例和键的集合中添加元素集合。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param elements   要添加的元素集合
     * @param expiry     过期时间(秒)
     * @return 是否添加成功
     */
    default <E> Boolean setAddForInstance(String instanceId, String key, Collection<E> elements, long expiry) {
        return setAddForInstance(instanceId, key, elements, expiry, TimeUnit.SECONDS);
    }

    /**
     * 向指定键的集合中添加元素集合。
     *
     * @param <E>      元素类型
     * @param key      集合的键
     * @param elements 要添加的元素集合
     * @param expiry   过期时间
     * @param unit     过期时间单位
     * @return 是否添加成功
     */
    default <E> Boolean setAdd(String key, Collection<E> elements, long expiry, TimeUnit unit) {
        return setAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, elements, expiry, unit);
    }

    /**
     * 向指定实例和键的集合中添加元素集合。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param elements   要添加的元素集合
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 是否添加成功
     */
    <E> Boolean setAddForInstance(String instanceId, String key, Collection<E> elements, long expiry, TimeUnit unit);

    /**
     * 从指定键的集合中移除元素。
     *
     * @param <E>     元素类型
     * @param key     集合的键
     * @param element 要移除的元素
     * @return 是否移除成功
     */
    default <E> Boolean setRemove(String key, E element) {
        return setRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element);
    }

    /**
     * 从指定实例和键的集合中移除元素。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param element    要移除的元素
     * @return 是否移除成功
     */
    default <E> Boolean setRemoveForInstance(String instanceId, String key, E element) {
        return setRemoveForInstance(instanceId, key, Set.of(element));
    }

    /**
     * 从指定键的集合中移除元素集合。
     *
     * @param <E>      元素类型
     * @param key      集合的键
     * @param elements 要移除的元素集合
     * @return 是否移除成功
     */
    default <E> Boolean setRemove(String key, Collection<E> elements) {
        return setRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, elements);
    }

    /**
     * 从指定实例和键的集合中移除元素集合。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param elements   要移除的元素集合
     * @return 是否移除成功
     */
    <E> Boolean setRemoveForInstance(String instanceId, String key, Collection<E> elements);

    /**
     * 判断指定键的集合中是否包含某个元素。
     *
     * @param <E>     元素类型
     * @param key     集合的键
     * @param element 要检查的元素
     * @return 是否包含该元素
     */
    default <E> Boolean setContains(String key, E element) {
        return setContainsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element);
    }

    /**
     * 判断指定实例和键的集合中是否包含某个元素。
     *
     * @param <E>        元素类型
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param element    要检查的元素
     * @return 是否包含该元素
     */
    <E> Boolean setContainsForInstance(String instanceId, String key, E element);

    /**
     * 获取指定键的集合的元素数量。
     *
     * @param key 集合的键
     * @return 元素数量
     */
    default int setSize(String key) {
        return setSizeForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 获取指定实例和键的集合的元素数量。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @return 元素数量
     */
    int setSizeForInstance(String instanceId, String key);

    /**
     * 将元素{@code value}从{@code key}移动到{@code destKey}
     *
     * @param key     元素的键
     * @param value   元素
     * @param destKey 元素的新键
     * @return 操作结果
     */
    default <E> Boolean setMove(String key, E value, String destKey) {
        return setMoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value, destKey);
    }

    /**
     * 将元素{@code value}从{@code key}移动到{@code destKey}
     *
     * @param instanceId 实例ID
     * @param key        元素的键
     * @param value      元素
     * @param destKey    元素的新键
     * @return 操作结果
     */
    <E> Boolean setMoveForInstance(String instanceId, String key, E value, String destKey);

    /**
     * 获取符合指定格式的元素集合
     *
     * @param key               元素的键
     * @param pattern           元素格式
     * @param eClass 元素的类型
     * @return 操作结果
     */
    default <E> Set<E> setScan(String key, String pattern, Class<E> eClass) {
        return setScanForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, pattern, eClass);
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
    <E> Set<E> setScanForInstance(String instanceId, String key, String pattern, Class<E> eClass);

    /**
     * 获取指定键的集合中的随机一个元素，并移除。
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param eClass 元素的类型
     * @return 集合中的随机元素
     */
    default <E> E setPop(String key, Class<E> eClass) {
        return setPopForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, eClass);
    }

    /**
     * 获取指定实例和键的集合中的随机一个元素，并移除。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param eClass 元素的类型
     * @return 集合中的随机元素
     */
    <E> E setPopForInstance(String instanceId, String key, Class<E> eClass);

    /**
     * 获取指定键的集合中的随机一个元素。
     *
     * @param <E>         元素类型
     * @param key         集合的键
     * @param eClass 元素的类型
     * @return 集合中的随机元素
     */
    default <E> E setRandomMember(String key, Class<E> eClass) {
        return setRandomMemberForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, eClass);
    }

    /**
     * 获取指定实例和键的集合中的随机一个元素。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param eClass 元素的类型
     * @return 集合中的随机元素
     */
    <E> E setRandomMemberForInstance(String instanceId, String key, Class<E> eClass);

    /**
     * 获取指定键的集合中的随机多个元素（可能重复）。
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param count             返回的元素个数
     * @param eClass 元素的类型
     * @return 集合中的随机元素
     */
    default <E> List<E> setRandomMembers(String key, long count, Class<E> eClass) {
        return setRandomMembersForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, count, eClass);
    }

    /**
     * 获取指定实例和键的集合中的随机多个元素（可能重复）。
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param count             返回的元素个数
     * @param eClass 元素的类型
     * @return 集合中的随机元素
     */
    <E> List<E> setRandomMembersForInstance(String instanceId, String key, long count, Class<E> eClass);

    /**
     * 从一个特定的集合中随机选择一定数量的不同元素，并返回一个包含这些元素的集合。
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param count             返回的元素个数
     * @param eClass 元素的类型
     * @return 集合中的随机元素
     */
    default <E> Set<E> setDistinctRandomMembers(String key, long count, Class<E> eClass) {
        return setDistinctRandomMembersForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, count, eClass);
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
    <E> Set<E> setDistinctRandomMembersForInstance(String instanceId, String key, long count, Class<E> eClass);

    /**
     * 获取指定键的集合中的所有元素。
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> setGetAll(String key, Class<E> eClass) {
        return setGetAllForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, eClass);
    }

    /**
     * 获取指定实例和键的集合中的所有元素。
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> setGetAllForInstance(String instanceId, String key, Class<E> eClass) {
        return setGetPageForInstance(instanceId, key, IPage.build(1, 0).setSearchCount(false), eClass);
    }

    /**
     * 获取指定键的集合中的所有元素。
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param iPage             分页参数
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> setGetPage(String key, final IPage iPage, Class<E> eClass) {
        return setGetPageForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, iPage, eClass);
    }

    /**
     * 获取指定实例和键的集合中的所有元素。
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param iPage             分页参数
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    <E> Set<E> setGetPageForInstance(String instanceId, String key, final IPage iPage, Class<E> eClass);

    /**
     * 计算两个集合之间的差异，返回一个包含在第一个集合中但不在第二个集合中的元素的集合
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param otherKey          其它集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> setDifference(String key, String otherKey, Class<E> eClass) {
        return setDifferenceForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, eClass);
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
    default <E> Set<E> setDifferenceForInstance(String instanceId, String key, String otherKey, Class<E> eClass) {
        return setDifferenceForInstance(instanceId, key, Collections.singletonList(otherKey), eClass);
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
    default <E> Set<E> setDifference(String key, Collection<String> otherKeys, Class<E> eClass) {
        return setDifferenceForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, eClass);
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
    default <E> Set<E> setDifferenceForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> eClass) {
        List<String> newKeys = new ArrayList<>();
        newKeys.add(key);
        newKeys.addAll(otherKeys);
        return setDifferenceForInstance(instanceId, newKeys, eClass);
    }

    /**
     * 计算多个集合之间的差异，返回一个包含在第一个集合中但不在其他任何集合中的元素的集合。
     *
     * @param <E>               元素类型
     * @param keys              集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> setDifference(Collection<String> keys, Class<E> eClass) {
        return setDifferenceForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, eClass);
    }

    /**
     * 计算多个集合之间的差异，返回一个包含在第一个集合中但不在其他任何集合中的元素的集合。
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param keys              集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    <E> Set<E> setDifferenceForInstance(String instanceId, Collection<String> keys, Class<E> eClass);

    /**
     * 计算给定的两个集合的差异，并将结果存储到指定的集合中
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @return 存储的元素数量
     */
    default Long setDifferenceAndStore(String key, String otherKey, String destKey) {
        return setDifferenceAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey);
    }

    /**
     * 计算给定的两个集合的差异，并将结果存储到指定的集合中
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @return 存储的元素数量
     */
    default Long setDifferenceAndStoreForInstance(String instanceId, String key, String otherKey, String destKey) {
        return setDifferenceAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey);
    }

    /**
     * 计算给定的集合与其他多个集合的差异，并将结果存储到指定的集合中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @return 存储的元素数量
     */
    default Long setDifferenceAndStore(String key, Collection<String> otherKeys, String destKey) {
        return setDifferenceAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey);
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
    default Long setDifferenceAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey) {
        List<String> newKeys = new ArrayList<>();
        newKeys.add(key);
        newKeys.addAll(otherKeys);
        return setDifferenceAndStoreForInstance(instanceId, newKeys, destKey);
    }

    /**
     * 计算给定的多个集合的差异，并将结果存储到指定的集合中。
     *
     * @param keys    集合的键
     * @param destKey 新的集合的键
     * @return 存储的元素数量
     */
    default Long setDifferenceAndStore(Collection<String> keys, String destKey) {
        return setDifferenceAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, destKey);
    }

    /**
     * 计算给定的多个集合的差异，并将结果存储到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param keys       集合的键
     * @param destKey    新的集合的键
     * @return 存储的元素数量
     */
    default Long setDifferenceAndStoreForInstance(String instanceId, Collection<String> keys, String destKey) {
        return setDifferenceAndStoreForInstance(instanceId, keys, destKey, 0L);
    }

    /**
     * 计算给定的两个集合的差异，并将结果存储到指定的集合中
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @param expiry   过期时间(秒)
     * @return 存储的元素数量
     */
    default Long setDifferenceAndStore(String key, String otherKey, String destKey, long expiry) {
        return setDifferenceAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey, expiry);
    }

    /**
     * 计算给定的两个集合的差异，并将结果存储到指定的集合中
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间(秒)
     * @return 存储的元素数量
     */
    default Long setDifferenceAndStoreForInstance(String instanceId, String key, String otherKey, String destKey, long expiry) {
        return setDifferenceAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey, expiry);
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
    default Long setDifferenceAndStore(String key, Collection<String> otherKeys, String destKey, long expiry) {
        return setDifferenceAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, expiry);
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
    default Long setDifferenceAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry) {
        List<String> newKeys = new ArrayList<>();
        newKeys.add(key);
        newKeys.addAll(otherKeys);
        return setDifferenceAndStoreForInstance(instanceId, newKeys, destKey, expiry);
    }

    /**
     * 计算给定的多个集合的差异，并将结果存储到指定的集合中。
     *
     * @param keys    集合的键
     * @param destKey 新的集合的键
     * @param expiry  过期时间(秒)
     * @return 存储的元素数量
     */
    default Long setDifferenceAndStore(Collection<String> keys, String destKey, long expiry) {
        return setDifferenceAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, destKey, expiry);
    }

    /**
     * 计算给定的多个集合的差异，并将结果存储到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param keys       集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间(秒)
     * @return 存储的元素数量
     */
    default Long setDifferenceAndStoreForInstance(String instanceId, Collection<String> keys, String destKey, long expiry) {
        return setDifferenceAndStoreForInstance(instanceId, keys, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 计算给定的两个集合的差异，并将结果存储到指定的集合中
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @param expiry   过期时间
     * @param unit     过期时间单位
     * @return 存储的元素数量
     */
    default Long setDifferenceAndStore(String key, String otherKey, String destKey, long expiry, TimeUnit unit) {
        return setDifferenceAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey, expiry, unit);
    }

    /**
     * 计算给定的两个集合的差异，并将结果存储到指定的集合中
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    default Long setDifferenceAndStoreForInstance(String instanceId, String key, String otherKey, String destKey, long expiry, TimeUnit unit) {
        return setDifferenceAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey, expiry, unit);
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
    default Long setDifferenceAndStore(String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit) {
        return setDifferenceAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, expiry, unit);
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
    default Long setDifferenceAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit) {
        List<String> newKeys = new ArrayList<>();
        newKeys.add(key);
        newKeys.addAll(otherKeys);
        return setDifferenceAndStoreForInstance(instanceId, newKeys, destKey, expiry, unit);
    }

    /**
     * 计算给定的多个集合的差异，并将结果存储到指定的集合中。
     *
     * @param keys    集合的键
     * @param destKey 新的集合的键
     * @param expiry  过期时间
     * @param unit    过期时间单位
     * @return 存储的元素数量
     */
    default Long setDifferenceAndStore(Collection<String> keys, String destKey, long expiry, TimeUnit unit) {
        return setDifferenceAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, destKey, expiry, unit);
    }

    /**
     * 计算给定的多个集合的差异，并将结果存储到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param keys       集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    Long setDifferenceAndStoreForInstance(String instanceId, Collection<String> keys, String destKey, long expiry, TimeUnit unit);

    /**
     * 计算给定的两个集合的交集
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param otherKey          其它集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> setIntersect(String key, String otherKey, Class<E> eClass) {
        return setIntersectForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, eClass);
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
    default <E> Set<E> setIntersectForInstance(String instanceId, String key, String otherKey, Class<E> eClass) {
        return setIntersectForInstance(instanceId, key, Collections.singletonList(otherKey), eClass);
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
    default <E> Set<E> setIntersect(String key, Collection<String> otherKeys, Class<E> eClass) {
        return setIntersectForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, eClass);
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
    default <E> Set<E> setIntersectForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> eClass) {
        List<String> newKeys = new ArrayList<>();
        newKeys.add(key);
        newKeys.addAll(otherKeys);
        return setIntersectForInstance(instanceId, newKeys, eClass);
    }

    /**
     * 计算给定的多个集合的交集。
     *
     * @param <E>               元素类型
     * @param keys              集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> setIntersect(Collection<String> keys, Class<E> eClass) {
        return setIntersectForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, eClass);
    }

    /**
     * 计算给定的多个集合的交集。
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param keys              集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    <E> Set<E> setIntersectForInstance(String instanceId, Collection<String> keys, Class<E> eClass);

    /**
     * 计算给定两个集合的交集，并将结果存储在destKey中。
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @return 存储的元素数量
     */
    default Long setIntersectAndStore(String key, String otherKey, String destKey) {
        return setIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey);
    }

    /**
     * 计算给定两个集合的交集，并将结果存储在destKey中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @return 存储的元素数量
     */
    default Long setIntersectAndStoreForInstance(String instanceId, String key, String otherKey, String destKey) {
        return setIntersectAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey);
    }

    /**
     * 计算给定一个集合和多个其他集合的交集，并将结果存储在destKey中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @return 存储的元素数量
     */
    default Long setIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return setIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey);
    }

    /**
     * 计算给定一个集合和多个其他集合的交集，并将结果存储在destKey中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @return 存储的元素数量
     */
    default Long setIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey) {
        List<String> newKeys = new ArrayList<>();
        newKeys.add(key);
        newKeys.addAll(otherKeys);
        return setIntersectAndStoreForInstance(instanceId, newKeys, destKey);
    }

    /**
     * 计算给定多个集合的交集，并将结果存储在destKey中。
     *
     * @param keys    集合的键
     * @param destKey 新的集合的键
     * @return 存储的元素数量
     */
    default Long setIntersectAndStore(Collection<String> keys, String destKey) {
        return setIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, destKey);
    }

    /**
     * 计算给定多个集合的交集，并将结果存储在destKey中。
     *
     * @param instanceId 实例ID
     * @param keys       集合的键
     * @param destKey    新的集合的键
     * @return 存储的元素数量
     */
    default Long setIntersectAndStoreForInstance(String instanceId, Collection<String> keys, String destKey) {
        return setIntersectAndStoreForInstance(instanceId, keys, destKey, 0L);
    }

    /**
     * 计算给定两个集合的交集，并将结果存储在destKey中。
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @param expiry   过期时间（秒）
     * @return 存储的元素数量
     */
    default Long setIntersectAndStore(String key, String otherKey, String destKey, long expiry) {
        return setIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey, expiry);
    }

    /**
     * 计算给定两个集合的交集，并将结果存储在destKey中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long setIntersectAndStoreForInstance(String instanceId, String key, String otherKey, String destKey, long expiry) {
        return setIntersectAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey, expiry);
    }

    /**
     * 计算给定一个集合和多个其他集合的交集，并将结果存储在destKey中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @param expiry    过期时间（秒）
     * @return 存储的元素数量
     */
    default Long setIntersectAndStore(String key, Collection<String> otherKeys, String destKey, long expiry) {
        return setIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, expiry);
    }

    /**
     * 计算给定一个集合和多个其他集合的交集，并将结果存储在destKey中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long setIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry) {
        List<String> newKeys = new ArrayList<>();
        newKeys.add(key);
        newKeys.addAll(otherKeys);
        return setIntersectAndStoreForInstance(instanceId, newKeys, destKey, expiry);
    }

    /**
     * 计算给定多个集合的交集，并将结果存储在destKey中。
     *
     * @param keys    集合的键
     * @param destKey 新的集合的键
     * @param expiry  过期时间（秒）
     * @return 存储的元素数量
     */
    default Long setIntersectAndStore(Collection<String> keys, String destKey, long expiry) {
        return setIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, destKey, expiry);
    }

    /**
     * 计算给定多个集合的交集，并将结果存储在destKey中。
     *
     * @param instanceId 实例ID
     * @param keys       集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long setIntersectAndStoreForInstance(String instanceId, Collection<String> keys, String destKey, long expiry) {
        return setIntersectAndStoreForInstance(instanceId, keys, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 计算给定两个集合的交集，并将结果存储在destKey中。
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @param expiry   过期时间
     * @param unit     过期时间单位
     * @return 存储的元素数量
     */
    default Long setIntersectAndStore(String key, String otherKey, String destKey, long expiry, TimeUnit unit) {
        return setIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey, expiry, unit);
    }

    /**
     * 计算给定两个集合的交集，并将结果存储在destKey中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    default Long setIntersectAndStoreForInstance(String instanceId, String key, String otherKey, String destKey, long expiry, TimeUnit unit) {
        return setIntersectAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey, expiry, unit);
    }

    /**
     * 计算给定一个集合和多个其他集合的交集，并将结果存储在destKey中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @param expiry    过期时间
     * @param unit      过期时间单位
     * @return 存储的元素数量
     */
    default Long setIntersectAndStore(String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit) {
        return setIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, expiry, unit);
    }

    /**
     * 计算给定一个集合和多个其他集合的交集，并将结果存储在destKey中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    default Long setIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit) {
        List<String> newKeys = new ArrayList<>();
        newKeys.add(key);
        newKeys.addAll(otherKeys);
        return setIntersectAndStoreForInstance(instanceId, newKeys, destKey, expiry, unit);
    }

    /**
     * 计算给定多个集合的交集，并将结果存储在destKey中。
     *
     * @param keys    集合的键
     * @param destKey 新的集合的键
     * @param expiry  过期时间
     * @param unit    过期时间单位
     * @return 存储的元素数量
     */
    default Long setIntersectAndStore(Collection<String> keys, String destKey, long expiry, TimeUnit unit) {
        return setIntersectAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, destKey, expiry, unit);
    }

    /**
     * 计算给定多个集合的交集，并将结果存储在destKey中。
     *
     * @param instanceId 实例ID
     * @param keys       集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    Long setIntersectAndStoreForInstance(String instanceId, Collection<String> keys, String destKey, long expiry, TimeUnit unit);

    /**
     * 计算给定两个集合的并集
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param otherKey          其它集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> setUnion(String key, String otherKey, Class<E> eClass) {
        return setUnionForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, eClass);
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
    default <E> Set<E> setUnionForInstance(String instanceId, String key, String otherKey, Class<E> eClass) {
        return setUnionForInstance(instanceId, key, Collections.singletonList(otherKey), eClass);
    }

    /**
     * 计算给定一个集合和多个其他集合的并集。
     *
     * @param <E>               元素类型
     * @param key               集合的键
     * @param otherKeys         其它集合的键的集合
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> setUnion(String key, Collection<String> otherKeys, Class<E> eClass) {
        return setUnionForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, eClass);
    }

    /**
     * 计算给定一个集合和多个其他集合的并集。
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param otherKeys         其它集合的键的集合
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> setUnionForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> eClass) {
        List<String> newKeys = new ArrayList<>();
        newKeys.add(key);
        newKeys.addAll(otherKeys);
        return setUnionForInstance(instanceId, newKeys, eClass);
    }

    /**
     * 计算给定多个集合的并集。
     *
     * @param <E>               元素类型
     * @param keys              集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    default <E> Set<E> setUnion(Collection<String> keys, Class<E> eClass) {
        return setUnionForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, eClass);
    }

    /**
     * 计算给定多个集合的并集。
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param keys              集合的键
     * @param eClass 元素的类型
     * @return 集合中的所有元素
     */
    <E> Set<E> setUnionForInstance(String instanceId, Collection<String> keys, Class<E> eClass);

    /**
     * 将键key和otherKey对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @return 存储的元素数量
     */
    default Long setUnionAndStore(String key, String otherKey, String destKey) {
        return setUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey);
    }

    /**
     * 将键key和otherKey对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @return 存储的元素数量
     */
    default Long setUnionAndStoreForInstance(String instanceId, String key, String otherKey, String destKey) {
        return setUnionAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey);
    }

    /**
     * 将键key和otherKeys中所有键对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @return 存储的元素数量
     */
    default Long setUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return setUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey);
    }

    /**
     * 将键key和otherKeys中所有键对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @return 存储的元素数量
     */
    default Long setUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey) {
        List<String> newKeys = new ArrayList<>();
        newKeys.add(key);
        newKeys.addAll(otherKeys);
        return setUnionAndStoreForInstance(instanceId, newKeys, destKey);
    }

    /**
     * 将keys中所有键对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param keys    集合的键
     * @param destKey 新的集合的键
     * @return 存储的元素数量
     */
    default Long setUnionAndStore(Collection<String> keys, String destKey) {
        return setUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, destKey);
    }

    /**
     * 将keys中所有键对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param instanceId 实例ID
     * @param keys       集合的键
     * @param destKey    新的集合的键
     * @return 存储的元素数量
     */
    default Long setUnionAndStoreForInstance(String instanceId, Collection<String> keys, String destKey) {
        return setUnionAndStoreForInstance(instanceId, keys, destKey, 0L);
    }

    /**
     * 将键key和otherKey对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @param expiry   过期时间（秒）
     * @return 存储的元素数量
     */
    default Long setUnionAndStore(String key, String otherKey, String destKey, long expiry) {
        return setUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey, expiry);
    }

    /**
     * 将键key和otherKey对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long setUnionAndStoreForInstance(String instanceId, String key, String otherKey, String destKey, long expiry) {
        return setUnionAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey, expiry);
    }

    /**
     * 将键key和otherKeys中所有键对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @param expiry    过期时间（秒）
     * @return 存储的元素数量
     */
    default Long setUnionAndStore(String key, Collection<String> otherKeys, String destKey, long expiry) {
        return setUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, expiry);
    }

    /**
     * 将键key和otherKeys中所有键对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long setUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry) {
        List<String> newKeys = new ArrayList<>();
        newKeys.add(key);
        newKeys.addAll(otherKeys);
        return setUnionAndStoreForInstance(instanceId, newKeys, destKey, expiry);
    }

    /**
     * 将keys中所有键对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param keys    集合的键
     * @param destKey 新的集合的键
     * @param expiry  过期时间（秒）
     * @return 存储的元素数量
     */
    default Long setUnionAndStore(Collection<String> keys, String destKey, long expiry) {
        return setUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, destKey, expiry);
    }

    /**
     * 将keys中所有键对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param instanceId 实例ID
     * @param keys       集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间（秒）
     * @return 存储的元素数量
     */
    default Long setUnionAndStoreForInstance(String instanceId, Collection<String> keys, String destKey, long expiry) {
        return setUnionAndStoreForInstance(instanceId, keys, destKey, expiry, TimeUnit.SECONDS);
    }

    /**
     * 将键key和otherKey对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param key      集合的键
     * @param otherKey 其它集合的键
     * @param destKey  新的集合的键
     * @param expiry   过期时间
     * @param unit     过期时间单位
     * @return 存储的元素数量
     */
    default Long setUnionAndStore(String key, String otherKey, String destKey, long expiry, TimeUnit unit) {
        return setUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKey, destKey, expiry, unit);
    }

    /**
     * 将键key和otherKey对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKey   其它集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    default Long setUnionAndStoreForInstance(String instanceId, String key, String otherKey, String destKey, long expiry, TimeUnit unit) {
        return setUnionAndStoreForInstance(instanceId, key, Collections.singletonList(otherKey), destKey, expiry, unit);
    }

    /**
     * 将键key和otherKeys中所有键对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param key       集合的键
     * @param otherKeys 其它集合的键的集合
     * @param destKey   新的集合的键
     * @param expiry    过期时间
     * @param unit      过期时间单位
     * @return 存储的元素数量
     */
    default Long setUnionAndStore(String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit) {
        return setUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, otherKeys, destKey, expiry, unit);
    }

    /**
     * 将键key和otherKeys中所有键对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    default Long setUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit) {
        List<String> newKeys = new ArrayList<>();
        newKeys.add(key);
        newKeys.addAll(otherKeys);
        return setUnionAndStoreForInstance(instanceId, newKeys, destKey, expiry, unit);
    }

    /**
     * 将keys中所有键对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param keys    集合的键
     * @param destKey 新的集合的键
     * @param expiry  过期时间
     * @param unit    过期时间单位
     * @return 存储的元素数量
     */
    default Long setUnionAndStore(Collection<String> keys, String destKey, long expiry, TimeUnit unit) {
        return setUnionAndStoreForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, destKey, expiry, unit);
    }

    /**
     * 将keys中所有键对应的集合进行并集计算，并将结果存储在键destKey中。
     *
     * @param instanceId 实例ID
     * @param keys       集合的键
     * @param destKey    新的集合的键
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    Long setUnionAndStoreForInstance(String instanceId, Collection<String> keys, String destKey, long expiry, TimeUnit unit);
}

