package top.mingempty.cache.commons.api;

import cn.hutool.core.lang.Pair;
import top.mingempty.domain.base.IPage;
import top.mingempty.domain.other.GlobalConstant;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存映射接口，用于操作缓存中的映射（键-值对）。
 */
public interface CacheMap {

    /**
     * 获取当前缓存实例的 Map 类型API。
     * <p>
     * 返回当前实例的 {@link CacheMap} 类型的API。
     * </p>
     *
     * @return 当前缓存实例的 Map 类型的缓存集合
     */
    default CacheMap getCacheMap() {
        return this;
    }

    /**
     * 设置指定键的映射。
     *
     * @param <E> 值的类型
     * @param key 映射的键
     * @param map 要设置的映射
     * @return 是否设置成功
     */
    default <E> Boolean mapSet(String key, Map<String, E> map) {
        return mapSetForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, map);
    }

    /**
     * 设置指定实例和键的映射。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param map        要设置的映射
     * @return 是否设置成功
     */
    default <E> Boolean mapSetForInstance(String instanceId, String key, Map<String, E> map) {
        return mapSetForInstance(instanceId, key, map, 0L);
    }

    /**
     * 设置指定键的映射。
     *
     * @param <E>    值的类型
     * @param key    映射的键
     * @param map    要设置的映射
     * @param expiry 过期时间
     * @return 是否设置成功
     */
    default <E> Boolean mapSet(String key, Map<String, E> map, long expiry) {
        return mapSetForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, map, expiry);
    }

    /**
     * 设置指定实例和键的映射。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param map        要设置的映射
     * @param expiry     过期时间
     * @return 是否设置成功
     */
    default <E> Boolean mapSetForInstance(String instanceId, String key, Map<String, E> map, long expiry) {
        return mapSetForInstance(instanceId, key, map, expiry, TimeUnit.SECONDS);
    }

    /**
     * 设置指定键的映射。
     *
     * @param <E>    值的类型
     * @param key    映射的键
     * @param map    要设置的映射
     * @param expiry 过期时间
     * @param unit   过期时间单位
     * @return 是否设置成功
     */
    default <E> Boolean mapSet(String key, Map<String, E> map, long expiry, TimeUnit unit) {
        return mapSetForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, map, expiry, unit);
    }

    /**
     * 设置指定实例和键的映射。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param map        要设置的映射
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 是否设置成功
     */
    <E> Boolean mapSetForInstance(String instanceId, String key, Map<String, E> map, long expiry, TimeUnit unit);

    /**
     * 向指定键的映射中添加键-值对。
     *
     * @param <E>   值的类型
     * @param key   映射的键
     * @param field 要添加的键
     * @param value 要添加的值
     * @return 是否添加成功
     */
    default <E> Boolean mapAdd(String key, String field, E value) {
        return mapAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, field, value);
    }

    /**
     * 向指定实例和键的映射中添加键-值对。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要添加的键
     * @param value      要添加的值
     * @return 是否添加成功
     */
    default <E> Boolean mapAddForInstance(String instanceId, String key, String field, E value) {
        return mapAddForInstance(instanceId, key, field, value, 0L);
    }

    /**
     * 向指定键的映射中添加带过期时间的键-值对。
     *
     * @param <E>    值的类型
     * @param key    映射的键
     * @param field  要添加的键
     * @param value  要添加的值
     * @param expiry 过期时间（秒）
     * @return 是否添加成功
     */
    default <E> Boolean mapAdd(String key, String field, E value, long expiry) {
        return mapAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, field, value, expiry);
    }

    /**
     * 向指定实例和键的映射中添加带过期时间的键-值对。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要添加的键
     * @param value      要添加的值
     * @param expiry     过期时间（秒）
     * @return 是否添加成功
     */
    default <E> Boolean mapAddForInstance(String instanceId, String key, String field, E value, long expiry) {
        return mapAddForInstance(instanceId, key, field, value, expiry, TimeUnit.SECONDS);
    }

    /**
     * 向指定键的映射中添加带过期时间的键-值对。
     *
     * @param <E>    值的类型
     * @param key    映射的键
     * @param field  要添加的键
     * @param value  要添加的值
     * @param expiry 过期时间（秒）
     * @param unit   过期时间单位
     * @return 是否添加成功
     */
    default <E> Boolean mapAdd(String key, String field, E value, long expiry, TimeUnit unit) {
        return mapAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, field, value, expiry, unit);
    }

    /**
     * 向指定实例和键的映射中添加带过期时间的键-值对。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要添加的键
     * @param value      要添加的值
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 是否添加成功
     */
    default <E> Boolean mapAddForInstance(String instanceId, String key, String field, E value, long expiry, TimeUnit unit) {
        return mapAddForInstance(instanceId, key, field, value, expiry, unit, true);
    }

    /**
     * 向指定键的映射中添加键-值对。
     *
     * @param <E>      值的类型
     * @param key      映射的键
     * @param field    要添加的键
     * @param value    要添加的值
     * @param override 是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean mapAdd(String key, String field, E value, boolean override) {
        return mapAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, field, value, override);
    }

    /**
     * 向指定实例和键的映射中添加键-值对。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要添加的键
     * @param value      要添加的值
     * @param override   是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean mapAddForInstance(String instanceId, String key, String field, E value, boolean override) {
        return mapAddForInstance(instanceId, key, field, value, 0L, override);
    }

    /**
     * 向指定键的映射中添加带过期时间的键-值对。
     *
     * @param <E>      值的类型
     * @param key      映射的键
     * @param field    要添加的键
     * @param value    要添加的值
     * @param expiry   过期时间（秒）
     * @param override 是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean mapAdd(String key, String field, E value, long expiry, boolean override) {
        return mapAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, field, value, expiry, override);
    }

    /**
     * 向指定实例和键的映射中添加带过期时间的键-值对。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要添加的键
     * @param value      要添加的值
     * @param expiry     过期时间（秒）
     * @param override   是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean mapAddForInstance(String instanceId, String key, String field, E value, long expiry, boolean override) {
        return mapAddForInstance(instanceId, key, field, value, expiry, TimeUnit.SECONDS, override);
    }

    /**
     * 向指定键的映射中添加带过期时间的键-值对。
     *
     * @param <E>      值的类型
     * @param key      映射的键
     * @param field    要添加的键
     * @param value    要添加的值
     * @param expiry   过期时间（秒）
     * @param unit     过期时间单位
     * @param override 是否覆盖已有值
     * @return 是否添加成功
     */
    default <E> Boolean mapAdd(String key, String field, E value, long expiry, TimeUnit unit, boolean override) {
        return mapAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, field, value, expiry, unit, override);
    }

    /**
     * 向指定实例和键的映射中添加带过期时间的键-值对。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要添加的键
     * @param value      要添加的值
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @param override   是否覆盖已有值
     * @return 是否添加成功
     */
    <E> Boolean mapAddForInstance(String instanceId, String key, String field, E value, long expiry, TimeUnit unit, boolean override);

    /**
     * 从指定键的映射中删除指定的字段。
     *
     * @param key   映射的键
     * @param field 要删除的字段
     * @return 删除的字段数量
     */
    default Long mapDelFields(String key, String field) {
        return mapDelFieldsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, field);
    }

    /**
     * 从指定实例和键的映射中删除指定的字段。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要删除的字段
     * @return 删除的字段数量
     */
    default Long mapDelFieldsForInstance(String instanceId, String key, String field) {
        return mapDelFieldsForInstance(instanceId, key, List.of(field));
    }

    /**
     * 从指定键的映射中删除指定的字段。
     *
     * @param key    映射的键
     * @param fields 要删除的字段数组
     * @return 删除的字段数量
     */
    default Long mapDelFields(String key, List<String> fields) {
        return mapDelFieldsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, fields);
    }

    /**
     * 从指定实例和键的映射中删除指定的字段。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param fields     要删除的字段数组
     * @return 删除的字段数量
     */
    Long mapDelFieldsForInstance(String instanceId, String key, List<String> fields);

    /**
     * 删除指定键的整个映射。
     *
     * @param key 映射的键
     * @return 删除的字段数量
     */
    default Boolean mapDel(String key) {
        return mapDelForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 删除指定实例和键的整个映射。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @return 删除的字段数量
     */
    Boolean mapDelForInstance(String instanceId, String key);

    /**
     * 获取指定键的映射中指定字段的值。
     *
     * @param <E>       值的类型
     * @param key       映射的键
     * @param field     要获取值的字段
     * @param eClass 值的类型
     * @return 字段的值
     */
    default <E> E mapGetField(String key, String field, Class<E> eClass) {
        return mapGetFieldForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, field, eClass);
    }

    /**
     * 获取指定实例和键的映射中指定字段的值。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要获取值的字段
     * @param eClass  值的类型
     * @return 字段的值
     */
    <E> E mapGetFieldForInstance(String instanceId, String key, String field, Class<E> eClass);

    /**
     * 获取指定键的映射中指定字段的值，并将其转换为指定的类型。
     *
     * @param <E>       值的类型
     * @param key       映射的键
     * @param fields    要获取值的字段数组
     * @param eClass 值的类型
     * @return 字段的值列表
     */
    default <E> List<E> mapMultiGet(String key, Collection<String> fields, Class<E> eClass) {
        return mapMultiGetForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, fields, eClass);
    }

    /**
     * 获取指定实例和键的映射中指定字段的值，并将其转换为指定的类型。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param fields     要获取值的字段数组
     * @param eClass  值的类型
     * @return 字段的值列表
     */
    <E> List<E> mapMultiGetForInstance(String instanceId, String key, Collection<String> fields, Class<E> eClass);


    /**
     * 获取指定键的映射，返回映射中的所有键-值对。
     *
     * @param <E>       值的类型
     * @param key       映射的键
     * @param eClass 映射中值的类型
     * @return 映射中的所有键-值对
     */
    default <E> Map<String, E> mapGet(String key, Class<E> eClass) {
        return mapGetForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, eClass);
    }

    /**
     * 获取指定实例和键的映射，返回映射中的所有键-值对。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param eClass  映射中值的类型
     * @return 映射中的所有键-值对
     */
    <E> Map<String, E> mapGetForInstance(String instanceId, String key, Class<E> eClass);

    /**
     * 获取指定键的映射，分页返回映射中的键-值对。
     *
     * @param <E>       值的类型
     * @param key       映射的键
     * @param iPage     分页参数
     * @param eClass 映射中值的类型
     * @return 映射中的所有键-值对
     */
    default <E> Map<String, E> mapGetPage(String key, final IPage iPage, Class<E> eClass) {
        return mapGetPageForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, iPage, eClass);
    }

    /**
     * 获取指定实例和键的映射，分页返回映射中的键-值对。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param iPage      分页参数
     * @param eClass  映射中值的类型
     * @return 映射中的所有键-值对
     */
    <E> Map<String, E> mapGetPageForInstance(String instanceId, String key, final IPage iPage, Class<E> eClass);

    /**
     * 获取指定键的映射大小。
     *
     * @param key 映射的键
     * @return 映射的大小
     */
    default int mapSize(String key) {
        return mapSizeForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 获取指定实例和键的映射大小。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @return 映射的大小
     */
    int mapSizeForInstance(String instanceId, String key);

    /**
     * 检查指定键的映射中是否包含指定字段。
     *
     * @param key   映射的键
     * @param field 要检查的字段
     * @return 如果包含该字段，则返回 {@code true}，否则返回 {@code false}
     */
    default Boolean mapContainsField(String key, String field) {
        return mapContainsFieldForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, field);
    }

    /**
     * 检查指定实例和键的映射中是否包含指定字段。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要检查的字段
     * @return 如果包含该字段，则返回 {@code true}，否则返回 {@code false}
     */
    Boolean mapContainsFieldForInstance(String instanceId, String key, String field);

    /**
     * 检查指定键的映射中是否包含指定值。
     *
     * @param <E>   值的类型
     * @param key   映射的键
     * @param value 要检查的值
     * @return 如果包含该值，则返回 {@code true}，否则返回 {@code false}
     */
    default <E> Boolean mapContainsValue(String key, E value) {
        return mapContainsValueForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value);
    }

    /**
     * 检查指定实例和键的映射中是否包含指定值。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param value      要检查的值
     * @return 如果包含该值，则返回 {@code true}，否则返回 {@code false}
     */
    <E> Boolean mapContainsValueForInstance(String instanceId, String key, E value);

    /**
     * 增加或减少指定键的映射中指定字段的计数器。
     *
     * @param key   映射的键
     * @param field 要增加或减少的字段
     * @param dela  增加或减少的值
     * @return 字段的新值
     */
    default Double mapCounter(String key, String field, double dela) {
        return mapCounterForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, field, dela);
    }

    /**
     * 增加或减少指定实例和键的映射中指定字段的计数器。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要增加或减少的字段
     * @param dela       增加或减少的值
     * @return 字段的新值
     */
    Double mapCounterForInstance(String instanceId, String key, String field, double dela);

    /**
     * 增加或减少指定键的映射中指定字段的计数器。
     *
     * @param key   映射的键
     * @param field 要增加或减少的字段
     * @param dela  增加或减少的值
     * @return 字段的新值
     */
    default Long mapCounter(String key, String field, Long dela) {
        return mapCounterForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, field, dela);
    }

    /**
     * 增加或减少指定实例和键的映射中指定字段的计数器。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要增加或减少的字段
     * @param dela       增加或减少的值
     * @return 字段的新值
     */
    Long mapCounterForInstance(String instanceId, String key, String field, Long dela);

    /**
     * 获取映射键内所有的hash键
     *
     * @param key 映射的键
     * @return 映射键内所有的hash键
     */
    default Set<String> mapKeys(String key) {
        return mapKeysForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 获取映射键内所有的hash键。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @return 映射键内所有的hash键
     */
    Set<String> mapKeysForInstance(String instanceId, String key);

    /**
     * 分页获取映射键内的hash键
     *
     * @param key 映射的键
     * @return 映射键内所有的hash键
     */
    default Set<String> mapKeysPage(String key, final IPage iPage) {
        return mapKeysPageForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, iPage);
    }

    /**
     * 分页获取映射键内的hash键。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param iPage      分页参数
     * @return 映射键内所有的hash键
     */
    Set<String> mapKeysPageForInstance(String instanceId, String key, final IPage iPage);

    /**
     * 获取映射键内所有的值
     *
     * @param key               映射的键
     * @param eClass 值的类型
     * @return 映射键内所有的值
     */
    default <E> List<E> mapValues(String key, Class<E> eClass) {
        return mapValuesForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, eClass);
    }

    /**
     * 获取映射键内所有的值。
     *
     * @param instanceId        实例ID
     * @param key               映射的键
     * @param eClass 值的类型
     * @return 映射键内所有的值
     */
    <E> List<E> mapValuesForInstance(String instanceId, String key, Class<E> eClass);

    /**
     * 获取映射键内指定hash键值的长度
     *
     * @param key   映射的键
     * @param field hash的键
     * @return 映射键内指定hash键值的长度
     */
    default Long mapLengthOfValue(String key, String field) {
        return mapLengthOfValueForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, field);
    }

    /**
     * 获取映射键内指定hash键值的长度。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      hash的键
     * @return 映射键内指定hash键值的长度
     */
    Long mapLengthOfValueForInstance(String instanceId, String key, String field);

    /**
     * 随机获取映射键内数据
     *
     * @param <E>       值的类型
     * @param key       映射的键
     * @param eClass 映射中值的类型
     * @return 随机的数据
     */
    default <E> Pair<String, E> mapRandomEntry(String key, Class<E> eClass) {
        return mapRandomEntryForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, eClass);
    }

    /**
     * 随机获取映射键内数据。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param eClass  映射中值的类型
     * @return 随机的数据
     */
    <E> Pair<String, E> mapRandomEntryForInstance(String instanceId, String key, Class<E> eClass);

    /**
     * 随机获取映射键内指定数量值
     *
     * @param <E>       值的类型
     * @param key       映射的键
     * @param count     随机取的数量
     * @param eClass 映射中值的类型
     * @return 随机的数据
     */
    default <E> Map<String, E> mapRandomEntries(String key, long count, Class<E> eClass) {
        return mapRandomEntriesForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, count, eClass);
    }

    /**
     * 随机获取映射键内指定数量值。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param count      随机取的数量
     * @param eClass  映射中值的类型
     * @return 随机的数据
     */
    <E> Map<String, E> mapRandomEntriesForInstance(String instanceId, String key, long count, Class<E> eClass);

    /**
     * 随机获取映射键内指定数量值
     *
     * @param <E>       值的类型
     * @param key       映射的键
     * @param pattern   元素格式
     * @param eClass 映射中值的类型
     * @return 随机的数据
     */
    default <E> Map<String, E> mapScan(String key, String pattern, Class<E> eClass) {
        return mapScanForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, pattern, eClass);
    }

    /**
     * 随机获取映射键内指定数量值。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param pattern    元素格式
     * @param eClass  映射中值的类型
     * @return 随机的数据
     */
    <E> Map<String, E> mapScanForInstance(String instanceId, String key, String pattern, Class<E> eClass);

    /**
     * 随机获取映射键内的hash键
     *
     * @param key 映射的键
     * @return 随机的数据
     */
    default String mapRandomKey(String key) {
        return mapRandomKeyForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 随机获取映射键内的hash键。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @return 随机的数据
     */
    String mapRandomKeyForInstance(String instanceId, String key);

    /**
     * 随机获取映射键内指定数量的hash键
     *
     * @param key   映射的键
     * @param count 随机取的数量
     * @return 随机的数据
     */
    default List<String> mapRandomKeys(String key, long count) {
        return mapRandomKeysForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, count);
    }

    /**
     * 随机获取映射键内指定数量的hash键。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param count      随机取的数量
     * @return 随机的数据
     */
    List<String> mapRandomKeysForInstance(String instanceId, String key, long count);

}
