package top.mingempty.cache.commons.api;

import top.mingempty.domain.base.IPage;
import top.mingempty.domain.other.GlobalConstant;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 队列接口，用于对缓存中的队列进行操作。
 *
 * @author zzhao
 */
public interface CacheQueue {

    /**
     * 获取当前缓存实例的队列缓存API。
     * <p>
     * 返回当前实例的 {@link CacheQueue} 类型的API。
     * </p>
     *
     * @return 当前缓存实例的队列缓存集合
     */
    default CacheQueue getCacheQueue() {
        return this;
    }

    /**
     * 将指定元素推送到指定键的列表中。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param element 要推送的元素
     * @return 操作是否成功
     */
    default <E> Boolean queuePush(String key, E element) {
        return queuePushForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element);
    }

    /**
     * 将指定元素推送到指定实例和键的列表中。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param element    要推送的元素
     * @return 操作是否成功
     */
    default <E> Boolean queuePushForInstance(String instanceId, String key, E element) {
        return queuePushForInstance(instanceId, key, element, 0L);
    }

    /**
     * 将指定元素推送到指定键的列表中，并设置过期时间。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param element 要推送的元素
     * @param expiry  过期时间（秒）
     * @return 操作是否成功
     */
    default <E> Boolean queuePush(String key, E element, long expiry) {
        return queuePushForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element, expiry);
    }

    /**
     * 将指定元素推送到指定实例和键的列表中，并设置过期时间。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param element    要推送的元素
     * @param expiry     过期时间（秒）
     * @return 操作是否成功
     */
    default <E> Boolean queuePushForInstance(String instanceId, String key, E element, long expiry) {
        return queuePushForInstance(instanceId, key, element, expiry, TimeUnit.SECONDS);
    }

    /**
     * 将指定元素推送到指定键的列表中，并设置过期时间。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param element 要推送的元素
     * @param expiry  过期时间
     * @param unit    过期时间单位
     * @return 操作是否成功
     */
    default <E> Boolean queuePush(String key, E element, long expiry, TimeUnit unit) {
        return queuePushForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element, expiry, unit);
    }

    /**
     * 将指定元素推送到指定实例和键的列表中，并设置过期时间。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param element    要推送的元素
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 操作是否成功
     */
    <E> Boolean queuePushForInstance(String instanceId, String key, E element, long expiry, TimeUnit unit);

    /**
     * 将指定元素集合推送到指定键的列表中。
     *
     * @param <E>      元素的类型
     * @param key      列表的键
     * @param elements 要推送的元素集合
     * @return 操作是否成功
     */
    default <E> Boolean queuePush(String key, Collection<E> elements) {
        return queuePushForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, elements);
    }

    /**
     * 将指定元素集合推送到指定实例和键的列表中。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param elements   要推送的元素集合
     * @return 操作是否成功
     */
    default <E> Boolean queuePushForInstance(String instanceId, String key, Collection<E> elements) {
        return queuePushForInstance(instanceId, key, elements, 0L);
    }

    /**
     * 将指定元素集合推送到指定键的列表中，并设置过期时间。
     *
     * @param <E>      元素的类型
     * @param key      列表的键
     * @param elements 要推送的元素集合
     * @param expiry   过期时间（秒）
     * @return 操作是否成功
     */
    default <E> Boolean queuePush(String key, Collection<E> elements, long expiry) {
        return queuePushForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, elements, expiry);
    }

    /**
     * 将指定元素集合推送到指定实例和键的列表中，并设置过期时间。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param elements   要推送的元素集合
     * @param expiry     过期时间（秒）
     * @return 操作是否成功
     */
    default <E> Boolean queuePushForInstance(String instanceId, String key, Collection<E> elements, long expiry) {
        return queuePushForInstance(instanceId, key, elements, expiry, TimeUnit.SECONDS);
    }

    /**
     * 将指定元素集合推送到指定键的列表中，并设置过期时间。
     *
     * @param <E>      元素的类型
     * @param key      列表的键
     * @param elements 要推送的元素集合
     * @param expiry   过期时间
     * @param unit     过期时间单位
     * @return 操作是否成功
     */
    default <E> Boolean queuePush(String key, Collection<E> elements, long expiry, TimeUnit unit) {
        return queuePushForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, elements, expiry, unit);
    }

    /**
     * 将指定元素集合推送到指定实例和键的列表中，并设置过期时间。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param elements   要推送的元素集合
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 操作是否成功
     */
    <E> Boolean queuePushForInstance(String instanceId, String key, Collection<E> elements, long expiry, TimeUnit unit);

    /**
     * 将指定元素推送到指定键的列表头中。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param element 要推送的元素
     * @return 操作是否成功
     */
    default <E> Boolean queueLpush(String key, E element) {
        return queueLpushForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element);
    }

    /**
     * 将指定元素推送到指定实例和键的列表头中。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param element    要推送的元素
     * @return 操作是否成功
     */
    default <E> Boolean queueLpushForInstance(String instanceId, String key, E element) {
        return queueLpushForInstance(instanceId, key, element, 0L);
    }

    /**
     * 将指定元素推送到指定键的列表头中，并设置过期时间。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param element 要推送的元素
     * @param expiry  过期时间（秒）
     * @return 操作是否成功
     */
    default <E> Boolean queueLpush(String key, E element, long expiry) {
        return queueLpushForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element, expiry);
    }

    /**
     * 将指定元素推送到指定实例和键的列表头中，并设置过期时间。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param element    要推送的元素
     * @param expiry     过期时间（秒）
     * @return 操作是否成功
     */
    default <E> Boolean queueLpushForInstance(String instanceId, String key, E element, long expiry) {
        return queueLpushForInstance(instanceId, key, element, expiry, TimeUnit.SECONDS);
    }

    /**
     * 将指定元素推送到指定键的列表头中，并设置过期时间。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param element 要推送的元素
     * @param expiry  过期时间
     * @param unit    过期时间单位
     * @return 操作是否成功
     */
    default <E> Boolean queueLpush(String key, E element, long expiry, TimeUnit unit) {
        return queueLpushForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element, expiry, unit);
    }

    /**
     * 将指定元素推送到指定实例和键的列表头中，并设置过期时间。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param element    要推送的元素
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 操作是否成功
     */
    <E> Boolean queueLpushForInstance(String instanceId, String key, E element, long expiry, TimeUnit unit);

    /**
     * 将指定元素集合推送到指定键的列表头中。
     *
     * @param <E>      元素的类型
     * @param key      列表的键
     * @param elements 要推送的元素集合
     * @return 操作是否成功
     */
    default <E> Boolean queueLpush(String key, Collection<E> elements) {
        return queueLpushForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, elements);
    }

    /**
     * 将指定元素集合推送到指定实例和键的列表头中。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param elements   要推送的元素集合
     * @return 操作是否成功
     */
    default <E> Boolean queueLpushForInstance(String instanceId, String key, Collection<E> elements) {
        return queueLpushForInstance(instanceId, key, elements, 0L);
    }

    /**
     * 将指定元素集合推送到指定键的列表头中，并设置过期时间。
     *
     * @param <E>      元素的类型
     * @param key      列表的键
     * @param elements 要推送的元素集合
     * @param expiry   过期时间（秒）
     * @return 操作是否成功
     */
    default <E> Boolean queueLpush(String key, Collection<E> elements, long expiry) {
        return queueLpushForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, elements, expiry);
    }

    /**
     * 将指定元素集合推送到指定实例和键的列表头中，并设置过期时间。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param elements   要推送的元素集合
     * @param expiry     过期时间（秒）
     * @return 操作是否成功
     */
    default <E> Boolean queueLpushForInstance(String instanceId, String key, Collection<E> elements, long expiry) {
        return queueLpushForInstance(instanceId, key, elements, expiry, TimeUnit.SECONDS);
    }

    /**
     * 将指定元素集合推送到指定键的列表头中，并设置过期时间。
     *
     * @param <E>      元素的类型
     * @param key      列表的键
     * @param elements 要推送的元素集合
     * @param expiry   过期时间
     * @param unit     过期时间单位
     * @return 操作是否成功
     */
    default <E> Boolean queueLpush(String key, Collection<E> elements, long expiry, TimeUnit unit) {
        return queueLpushForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, elements, expiry, unit);
    }

    /**
     * 将指定元素集合推送到指定实例和键的列表头中，并设置过期时间。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param elements   要推送的元素集合
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 操作是否成功
     */
    <E> Boolean queueLpushForInstance(String instanceId, String key, Collection<E> elements, long expiry, TimeUnit unit);

    /**
     * 设置指定键的列表中指定索引的元素。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param index   要设置的索引
     * @param element 要设置的元素
     * @return 操作是否成功
     */
    default <E> Boolean queueSet(String key, int index, E element) {
        return queueSetForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, index, element);
    }

    /**
     * 设置指定实例和键的列表中指定索引的元素。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param index      要设置的索引
     * @param element    要设置的元素
     * @return 操作是否成功
     */
    default <E> Boolean queueSetForInstance(String instanceId, String key, int index, E element) {
        return queueSetForInstance(instanceId, key, index, element, 0L);
    }

    /**
     * 设置指定键的列表中指定索引的元素，并设置过期时间。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param index   要设置的索引
     * @param element 要设置的元素
     * @param expiry  过期时间（秒）
     * @return 操作是否成功
     */
    default <E> Boolean queueSet(String key, int index, E element, long expiry) {
        return queueSetForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, index, element, expiry);
    }

    /**
     * 设置指定实例和键的列表中指定索引的元素，并设置过期时间。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param index      要设置的索引
     * @param element    要设置的元素
     * @param expiry     过期时间（秒）
     * @return 操作是否成功
     */
    default <E> Boolean queueSetForInstance(String instanceId, String key, int index, E element, long expiry) {
        return queueSetForInstance(instanceId, key, index, element, expiry, TimeUnit.SECONDS);
    }

    /**
     * 设置指定键的列表中指定索引的元素，并设置过期时间。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param index   要设置的索引
     * @param element 要设置的元素
     * @param expiry  过期时间
     * @param unit    过期时间单位
     * @return 操作是否成功
     */
    default <E> Boolean queueSet(String key, int index, E element, long expiry, TimeUnit unit) {
        return queueSetForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, index, element, expiry, unit);
    }

    /**
     * 设置指定实例和键的列表中指定索引的元素，并设置过期时间。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param index      要设置的索引
     * @param element    要设置的元素
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 操作是否成功
     */
    <E> Boolean queueSetForInstance(String instanceId, String key, int index, E element, long expiry, TimeUnit unit);

    /**
     * 从指定键的列表中弹出并删除第一个元素。
     *
     * @param <E>    元素的类型
     * @param key    列表的键
     * @param eClass 元素的类型类
     * @return 被弹出的元素
     */
    default <E> E queueLpop(String key, Class<E> eClass) {
        return queueLpopForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, eClass);
    }

    /**
     * 从指定实例和键的列表中弹出并删除第一个元素。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param eClass     元素的类型类
     * @return 被弹出的元素
     */
    <E> E queueLpopForInstance(String instanceId, String key, Class<E> eClass);

    /**
     * 从指定键的列表中在指定时间内弹出并删除第一个元素。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param timeout 等待时间（毫秒）
     * @param eClass  元素的类型类
     * @return 被弹出的元素
     */
    default <E> E queueLpop(String key, long timeout, Class<E> eClass) {
        return queueLpopForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, timeout, eClass);
    }

    /**
     * 从指定实例和键的列表在指定时间内中弹出并删除第一个元素。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param timeout    等待时间（毫秒）
     * @param eClass     元素的类型类
     * @return 被弹出的元素
     */
    default <E> E queueLpopForInstance(String instanceId, String key, long timeout, Class<E> eClass) {
        return queueLpopForInstance(instanceId, key, timeout, TimeUnit.MILLISECONDS, eClass);
    }

    /**
     * 从指定键的列表中在指定时间内弹出并删除第一个元素。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param timeout 等待时间
     * @param unit    等待时间单位
     * @param eClass  元素的类型类
     * @return 被弹出的元素
     */
    default <E> E queueLpop(String key, long timeout, TimeUnit unit, Class<E> eClass) {
        return queueLpopForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, timeout, unit, eClass);
    }

    /**
     * 从指定实例和键的列表中在指定时间内弹出并删除第一个元素。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param eClass     元素的类型类
     * @param timeout    等待时间
     * @param unit       等待时间单位
     * @return 被弹出的元素
     */
    <E> E queueLpopForInstance(String instanceId, String key, long timeout, TimeUnit unit, Class<E> eClass);

    /**
     * 从指定键的列表中弹出并删除最后一个元素。
     *
     * @param <E>    元素的类型
     * @param key    列表的键
     * @param eClass 元素的类型类
     * @return 被弹出的元素
     */
    default <E> E queueRPop(String key, Class<E> eClass) {
        return queueRPopForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, eClass);
    }

    /**
     * 从指定实例和键的列表中弹出并删除最后一个元素。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param eClass     元素的类型类
     * @return 被弹出的元素
     */
    <E> E queueRPopForInstance(String instanceId, String key, Class<E> eClass);

    /**
     * 从指定键的列表中在指定时间内弹出并删除最后一个元素。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param eClass  元素的类型类
     * @param timeout 等待时间
     * @return 被弹出的元素
     */
    default <E> E queueRPop(String key, long timeout, Class<E> eClass) {
        return queueRPopForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, timeout, eClass);
    }

    /**
     * 从指定实例和键的列表中在指定时间内弹出并删除最后一个元素。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param eClass     元素的类型类
     * @param timeout    等待时间
     * @return 被弹出的元素
     */
    default <E> E queueRPopForInstance(String instanceId, String key, long timeout, Class<E> eClass) {
        return queueRPopForInstance(instanceId, key, timeout, TimeUnit.MILLISECONDS, eClass);
    }

    /**
     * 从指定键的列表中在指定时间内弹出并删除最后一个元素。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param timeout 等待时间
     * @param unit    等待时间单位
     * @param eClass  元素的类型类
     * @return 被弹出的元素
     */
    default <E> E queueRPop(String key, long timeout, TimeUnit unit, Class<E> eClass) {
        return queueRPopForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, timeout, unit, eClass);
    }

    /**
     * 从指定实例和键的列表中在指定时间内弹出并删除最后一个元素。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param timeout    等待时间
     * @param unit       等待时间单位
     * @param eClass     元素的类型类
     * @return 被弹出的元素
     */
    <E> E queueRPopForInstance(String instanceId, String key, long timeout, TimeUnit unit, Class<E> eClass);

    /**
     * 从指定键的列表中在指定时间内弹出并删除最后一个元素。
     *
     * @param <E>    元素的类型
     * @param key    列表的键
     * @param eClass 元素的类型类
     * @return 被弹出的元素
     */
    default <E> List<E> queueAll(String key, Class<E> eClass) {
        return queueAllForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, eClass);
    }

    /**
     * 从指定实例和键的列表中在指定时间内弹出并删除最后一个元素。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param eClass     元素的类型类
     * @return 被弹出的元素
     */
    default <E> List<E> queueAllForInstance(String instanceId, String key, Class<E> eClass) {
        return queuePageForInstance(instanceId, key, IPage.build(1, 0).setSearchCount(false), eClass);
    }

    /**
     * 从指定键的列表中在指定时间内弹出并删除最后一个元素。
     *
     * @param <E>    元素的类型
     * @param key    列表的键
     * @param iPage  分页参数
     * @param eClass 元素的类型类
     * @return 被弹出的元素
     */
    default <E> List<E> queuePage(String key, final IPage iPage, Class<E> eClass) {
        return queuePageForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, iPage, eClass);
    }

    /**
     * 从指定实例和键的列表中在指定时间内弹出并删除最后一个元素。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param iPage      分页参数
     * @param eClass     元素的类型类
     * @return 被弹出的元素
     */
    <E> List<E> queuePageForInstance(String instanceId, String key, final IPage iPage, Class<E> eClass);

    /**
     * 从指定键的列表中删除指定索引的元素。
     *
     * @param key   列表的键
     * @param index 要删除的索引
     * @return 操作是否成功
     */
    default Boolean queueRemove(String key, int index) {
        return queueRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, index);
    }

    /**
     * 从指定实例和键的列表中删除指定索引的元素。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param index      要删除的索引
     * @return 操作是否成功
     */
    Boolean queueRemoveForInstance(String instanceId, String key, int index);

    /**
     * 从指定键的列表中删除指定元素。
     *
     * @param key     列表的键
     * @param element 要删除的元素
     * @return 删除的数量
     */
    default <E> Long queueRemove(String key, E element) {
        return queueRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element);
    }

    /**
     * 从指定实例和键的列表中删除所有指定元素。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param element    要删除的元素
     * @return 删除的数量
     */
    <E> Long queueRemoveForInstance(String instanceId, String key, E element);

    /**
     * 从指定键的列表中获取指定索引的元素。
     *
     * @param key   列表的键
     * @param index 要获取的索引
     * @return 指定索引的元素
     */
    default Object queueGet(String key, int index) {
        return queueGetForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, index);
    }

    /**
     * 从指定实例和键的列表中获取指定索引的元素。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param index      要获取的索引
     * @return 指定索引的元素
     */
    Object queueGetForInstance(String instanceId, String key, int index);

    /**
     * 从指定键的列表中获取指定索引的元素。
     *
     * @param <E>    元素的类型
     * @param key    列表的键
     * @param index  要获取的索引
     * @param eClass 元素的类型类
     * @return 指定索引的元素
     */
    default <E> E queueGet(String key, int index, Class<E> eClass) {
        return queueGetForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, index, eClass);
    }

    /**
     * 从指定实例和键的列表中获取指定索引的元素。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param index      要获取的索引
     * @param eClass     元素的类型类
     * @return 指定索引的元素
     */
    <E> E queueGetForInstance(String instanceId, String key, int index, Class<E> eClass);

    /**
     * 检查指定键的列表是否包含指定元素。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param element 要检查的元素
     * @return 如果列表中包含指定元素，返回 true；否则，返回 false
     */
    default <E> Boolean queueContains(String key, E element) {
        return queueContainsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element);
    }

    /**
     * 检查指定实例和键的列表是否包含指定元素。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param element    要检查的元素
     * @return 如果列表中包含指定元素，返回 true；否则，返回 false
     */
    default <E> Boolean queueContainsForInstance(String instanceId, String key, E element) {
        Long indexOf = queueIndexOfForInstance(instanceId, key, element);
        return indexOf != null && indexOf != -1;
    }

    /**
     * 查找指定元素在列表中的索引。
     *
     * @param <E>     元素的类型
     * @param key     列表的键
     * @param element 要查找的元素
     * @return 元素在列表中的索引，如果未找到，则返回 -1
     */
    default <E> Long queueIndexOf(String key, E element) {
        return queueIndexOfForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, element);
    }

    /**
     * 查找指定实例和键的列表中指定元素的索引。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param element    要查找的元素
     * @return 元素在列表中的索引，如果未找到，则返回 -1
     */
    <E> Long queueIndexOfForInstance(String instanceId, String key, E element);

    /**
     * 获取指定键的列表的大小。
     *
     * @param key 列表的键
     * @return 列表的大小(- 1代表异常)
     */
    default Long queueSize(String key) {
        return queueSizeForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 获取指定实例和键的列表的大小。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @return 列表的大小(- 1代表异常)
     */
    Long queueSizeForInstance(String instanceId, String key);

    /**
     * 对队列进行修剪，保留指定范围内的元素。
     *
     * @param key   队列的键
     * @param start 保留范围的起始索引
     * @param end   保留范围的终止索引
     * @return 操作是否成功
     */
    default Boolean queueTrim(String key, long start, long end) {
        return queueTrimForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, start, end);
    }

    /**
     * 对队列进行修剪，保留指定范围内的元素。
     *
     * @param instanceId 实例ID
     * @param key        队列的键
     * @param start      保留范围的起始索引
     * @param end        保留范围的终止索引
     * @return 操作是否成功
     */
    Boolean queueTrimForInstance(String instanceId, String key, long start, long end);

}
