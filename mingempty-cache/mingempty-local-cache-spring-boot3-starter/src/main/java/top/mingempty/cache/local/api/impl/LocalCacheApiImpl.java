package top.mingempty.cache.local.api.impl;

import cn.hutool.core.lang.Pair;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import top.mingempty.cache.local.api.LocalCacheApi;
import top.mingempty.domain.base.IPage;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zzhao
 */
@AllArgsConstructor
public class LocalCacheApiImpl implements LocalCacheApi {

    private final CacheManager cacheManager;

    /**
     * 在指定实例中重命名缓存中的键。
     *
     * @param instanceId 实例ID
     * @param oldKey     原键
     * @param newKey     新键
     * @return 操作是否成功
     */
    @Override
    public Boolean renameForInstance(String instanceId, String oldKey, String newKey) {
        return null;
    }

    /**
     * 在指定实例中检查缓存中指定键是否存在。
     *
     * @param instanceId 实例ID
     * @param key        要检查的键
     * @return 键是否存在
     */
    @Override
    public Boolean existsForInstance(String instanceId, String key) {
        return null;
    }

    /**
     * 在指定实例中获取缓存中指定键的过期时间。
     *
     * @param instanceId 实例ID
     * @param key        要检查的键
     * @param unit       时间单位
     * @return 过期时间（秒）
     */
    @Override
    public Long ttlForInstance(String instanceId, String key, TimeUnit unit) {
        return 0L;
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
    @Override
    public Boolean updateExpiresForInstance(String instanceId, String key, long expiry, TimeUnit unit) {
        return null;
    }

    /**
     * 在指定实例中删除缓存中的指定键。
     *
     * @param instanceId 实例ID
     * @param key        要删除的键
     * @return 操作是否成功
     */
    @Override
    public Boolean delForInstance(String instanceId, String key) {
        return null;
    }

    /**
     * 在指定实例中删除缓存中的指定键。
     *
     * @param instanceId 实例ID
     * @param keys       要删除的键集合
     * @return 已删除的数量
     */
    @Override
    public Long delForInstance(String instanceId, Collection<String> keys) {
        return 0L;
    }

    /**
     * 在指定实例中根据命名空间删除所有相关的缓存数据。
     *
     * @param instanceId 实例ID
     * @param namespace  要删除的命名空间
     * @return 操作是否成功
     */
    @Override
    public Boolean delByNamespaceForInstance(String instanceId, String namespace) {
        return null;
    }

    /**
     * 在指定实例中清空缓存中的所有数据。
     *
     * @param instanceId 实例ID
     * @return 操作是否成功
     */
    @Override
    public Boolean clearForInstance(String instanceId) {
        return null;
    }

    /**
     * 在指定实例中关闭缓存连接。
     *
     * @param instanceId 实例ID
     * @return 操作是否成功
     */
    @Override
    public Boolean closeForInstance(String instanceId) {
        return null;
    }

    /**
     * 在指定实例中取消缓存中指定键的异步删除操作。
     *
     * @param instanceId 实例ID
     * @param key        要取消的键
     * @return 操作是否成功
     */
    @Override
    public Boolean unlinkForInstance(String instanceId, String key) {
        return null;
    }

    /**
     * 在指定实例中根据前缀删除所有相关的缓存数据，使用扫描操作。
     *
     * @param instanceId 实例ID
     * @param prefix     要删除的前缀
     * @return 操作是否成功
     */
    @Override
    public Boolean delByPrefixUseScanForInstance(String instanceId, String prefix) {
        return null;
    }

    /**
     * 在指定实例中从给定的键中删除过期时间
     *
     * @param instanceId 实例ID
     * @param key        要删除过期时间的键
     * @return 操作是否成功
     */
    @Override
    public Boolean persistForInstance(String instanceId, String key) {
        return null;
    }

    /**
     * 在指定实例中扫描指定格式的键
     *
     * @param instanceId 实例ID
     * @param pattern    格式
     * @return 键集合
     */
    @Override
    public Set<String> scanForInstance(String instanceId, String pattern) {
        return Set.of();
    }

    /**
     * 设置指定实例和键的映射。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param map        要设置的映射
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 是否设置成功
     */
    @Override
    public <E> Boolean mapSetForInstance(String instanceId, String key, Map<String, E> map, long expiry, TimeUnit unit) {
        return null;
    }

    /**
     * 向指定实例和键的映射中添加带过期时间的键-值对。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要添加的键
     * @param value      要添加的值
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @param override   是否覆盖已有值
     * @return 是否添加成功
     */
    @Override
    public <E> Boolean mapAddForInstance(String instanceId, String key, String field, E value, long expiry, TimeUnit unit, boolean override) {
        return null;
    }

    /**
     * 从指定实例和键的映射中删除指定的字段。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param fields     要删除的字段数组
     * @return 删除的字段数量
     */
    @Override
    public Long mapDelFieldsForInstance(String instanceId, String key, List<String> fields) {
        return 0L;
    }

    /**
     * 删除指定实例和键的整个映射。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @return 删除的字段数量
     */
    @Override
    public Boolean mapDelForInstance(String instanceId, String key) {
        return null;
    }

    /**
     * 获取指定实例和键的映射中指定字段的值。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要获取值的字段
     * @param valueType  值的类型
     * @return 字段的值
     */
    @Override
    public <E> E mapGetFieldForInstance(String instanceId, String key, String field, Class<E> valueType) {
        return null;
    }

    /**
     * 获取指定实例和键的映射中指定字段的值，并将其转换为指定的类型。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param fields     要获取值的字段数组
     * @param valueType  值的类型
     * @return 字段的值列表
     */
    @Override
    public <E> List<E> mapMultiGetForInstance(String instanceId, String key, Collection<String> fields, Class<E> valueType) {
        return List.of();
    }

    /**
     * 获取指定实例和键的映射中指定字段的值，并将其转换为指定的类型。
     *
     * @param instanceId        实例ID
     * @param key               映射的键
     * @param fields            要获取值的字段数组
     * @param listTypeReference 值的类型
     * @return 字段的值列表
     */
    @Override
    public <E> List<E> mapMultiGetForInstance(String instanceId, String key, Collection<String> fields, TypeReference<List<E>> listTypeReference) {
        return List.of();
    }

    /**
     * 获取指定实例和键的映射，返回映射中的所有键-值对。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param valueType  映射中值的类型
     * @return 映射中的所有键-值对
     */
    @Override
    public <E> Map<String, E> mapGetForInstance(String instanceId, String key, Class<E> valueType) {
        return Map.of();
    }

    /**
     * 获取指定实例和键的映射，分页返回映射中的键-值对。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param iPage      分页参数
     * @param valueType  映射中值的类型
     * @return 映射中的所有键-值对
     */
    @Override
    public <E> Map<String, E> mapGetPageForInstance(String instanceId, String key, IPage iPage, Class<E> valueType) {
        return Map.of();
    }

    /**
     * 获取指定实例和键的映射大小。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @return 映射的大小
     */
    @Override
    public int mapSizeForInstance(String instanceId, String key) {
        return 0;
    }

    /**
     * 检查指定实例和键的映射中是否包含指定字段。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要检查的字段
     * @return 如果包含该字段，则返回 {@code true}，否则返回 {@code false}
     */
    @Override
    public Boolean mapContainsFieldForInstance(String instanceId, String key, String field) {
        return null;
    }

    /**
     * 检查指定实例和键的映射中是否包含指定值。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param value      要检查的值
     * @return 如果包含该值，则返回 {@code true}，否则返回 {@code false}
     */
    @Override
    public <E> Boolean mapContainsValueForInstance(String instanceId, String key, E value) {
        return null;
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
    @Override
    public Double mapCounterForInstance(String instanceId, String key, String field, double dela) {
        return 0.0;
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
    @Override
    public Long mapCounterForInstance(String instanceId, String key, String field, Long dela) {
        return 0L;
    }

    /**
     * 获取映射键内所有的hash键。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @return 映射键内所有的hash键
     */
    @Override
    public Set<String> mapKeysForInstance(String instanceId, String key) {
        return Set.of();
    }

    /**
     * 分页获取映射键内的hash键。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param iPage      分页参数
     * @return 映射键内所有的hash键
     */
    @Override
    public Set<String> mapKeysPageForInstance(String instanceId, String key, IPage iPage) {
        return Set.of();
    }

    /**
     * 获取映射键内所有的值。
     *
     * @param instanceId        实例ID
     * @param key               映射的键
     * @param listTypeReference 值的类型
     * @return 映射键内所有的值
     */
    @Override
    public <E> List<E> mapValuesForInstance(String instanceId, String key, TypeReference<List<E>> listTypeReference) {
        return List.of();
    }

    /**
     * 获取映射键内指定hash键值的长度。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      hash的键
     * @return 映射键内指定hash键值的长度
     */
    @Override
    public Long mapLengthOfValueForInstance(String instanceId, String key, String field) {
        return 0L;
    }

    /**
     * 随机获取映射键内数据。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param valueType  映射中值的类型
     * @return 随机的数据
     */
    @Override
    public <E> Pair<String, E> mapRandomEntryForInstance(String instanceId, String key, Class<E> valueType) {
        return null;
    }

    /**
     * 随机获取映射键内指定数量值。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param count      随机取的数量
     * @param valueType  映射中值的类型
     * @return 随机的数据
     */
    @Override
    public <E> Map<String, E> mapRandomEntriesForInstance(String instanceId, String key, long count, Class<E> valueType) {
        return Map.of();
    }

    /**
     * 随机获取映射键内指定数量值。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param pattern    元素格式
     * @param valueType  映射中值的类型
     * @return 随机的数据
     */
    @Override
    public <E> Map<String, E> mapScanForInstance(String instanceId, String key, String pattern, Class<E> valueType) {
        return Map.of();
    }

    /**
     * 随机获取映射键内的hash键。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @return 随机的数据
     */
    @Override
    public String mapRandomKeyForInstance(String instanceId, String key) {
        return "";
    }

    /**
     * 随机获取映射键内指定数量的hash键。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param count      随机取的数量
     * @return 随机的数据
     */
    @Override
    public List<String> mapRandomKeysForInstance(String instanceId, String key, long count) {
        return List.of();
    }

    /**
     * 将指定元素推送到指定实例和键的列表中，并设置过期时间。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param element    要推送的元素
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 操作是否成功
     */
    @Override
    public <E> Boolean queuePushForInstance(String instanceId, String key, E element, long expiry, TimeUnit unit) {
        return null;
    }

    /**
     * 将指定元素集合推送到指定实例和键的列表中，并设置过期时间。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param elements   要推送的元素集合
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 操作是否成功
     */
    @Override
    public <E> Boolean queuePushForInstance(String instanceId, String key, Collection<E> elements, long expiry, TimeUnit unit) {
        return null;
    }

    /**
     * 将指定元素推送到指定实例和键的列表头中，并设置过期时间。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param element    要推送的元素
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 操作是否成功
     */
    @Override
    public <E> Boolean queueLpushForInstance(String instanceId, String key, E element, long expiry, TimeUnit unit) {
        return null;
    }

    /**
     * 将指定元素集合推送到指定实例和键的列表头中，并设置过期时间。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param elements   要推送的元素集合
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 操作是否成功
     */
    @Override
    public <E> Boolean queueLpushForInstance(String instanceId, String key, Collection<E> elements, long expiry, TimeUnit unit) {
        return null;
    }

    /**
     * 设置指定实例和键的列表中指定索引的元素，并设置过期时间。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param index      要设置的索引
     * @param element    要设置的元素
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 操作是否成功
     */
    @Override
    public <E> Boolean queueSetForInstance(String instanceId, String key, int index, E element, long expiry, TimeUnit unit) {
        return null;
    }

    /**
     * 从指定实例和键的列表中弹出并删除第一个元素。
     *
     * @param instanceId  实例ID
     * @param key         列表的键
     * @param elementType 元素的类型类
     * @return 被弹出的元素
     */
    @Override
    public <E> E queueLpopForInstance(String instanceId, String key, Class<E> elementType) {
        return null;
    }

    /**
     * 从指定实例和键的列表中在指定时间内弹出并删除第一个元素。
     *
     * @param instanceId  实例ID
     * @param key         列表的键
     * @param timeout     等待时间
     * @param unit        等待时间单位
     * @param elementType 元素的类型类
     * @return 被弹出的元素
     */
    @Override
    public <E> E queueLpopForInstance(String instanceId, String key, long timeout, TimeUnit unit, Class<E> elementType) {
        return null;
    }

    /**
     * 从指定实例和键的列表中弹出并删除最后一个元素。
     *
     * @param instanceId  实例ID
     * @param key         列表的键
     * @param elementType 元素的类型类
     * @return 被弹出的元素
     */
    @Override
    public <E> E queueRPopForInstance(String instanceId, String key, Class<E> elementType) {
        return null;
    }

    /**
     * 从指定实例和键的列表中在指定时间内弹出并删除最后一个元素。
     *
     * @param instanceId  实例ID
     * @param key         列表的键
     * @param timeout     等待时间
     * @param unit        等待时间单位
     * @param elementType 元素的类型类
     * @return 被弹出的元素
     */
    @Override
    public <E> E queueRPopForInstance(String instanceId, String key, long timeout, TimeUnit unit, Class<E> elementType) {
        return null;
    }

    /**
     * 从指定实例和键的列表中在指定时间内弹出并删除最后一个元素。
     *
     * @param instanceId        实例ID
     * @param key               列表的键
     * @param iPage             分页参数
     * @param listTypeReference 元素的类型类
     * @return 被弹出的元素
     */
    @Override
    public <E> List<E> queuePageForInstance(String instanceId, String key, IPage iPage, TypeReference<List<E>> listTypeReference) {
        return List.of();
    }

    /**
     * 从指定实例和键的列表中删除指定索引的元素。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param index      要删除的索引
     * @return 操作是否成功
     */
    @Override
    public Boolean queueRemoveForInstance(String instanceId, String key, int index) {
        return null;
    }

    /**
     * 从指定实例和键的列表中删除所有指定元素。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param element    要删除的元素
     * @return 删除的数量
     */
    @Override
    public <E> Long queueRemoveForInstance(String instanceId, String key, E element) {
        return 0L;
    }

    /**
     * 从指定实例和键的列表中获取指定索引的元素。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param index      要获取的索引
     * @return 指定索引的元素
     */
    @Override
    public Object queueGetForInstance(String instanceId, String key, int index) {
        return null;
    }

    /**
     * 从指定实例和键的列表中获取指定索引的元素。
     *
     * @param instanceId  实例ID
     * @param key         列表的键
     * @param index       要获取的索引
     * @param elementType 元素的类型类
     * @return 指定索引的元素
     */
    @Override
    public <E> E queueGetForInstance(String instanceId, String key, int index, Class<E> elementType) {
        return null;
    }

    /**
     * 查找指定实例和键的列表中指定元素的索引。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @param element    要查找的元素
     * @return 元素在列表中的索引，如果未找到，则返回 -1
     */
    @Override
    public <E> Long queueIndexOfForInstance(String instanceId, String key, E element) {
        return 0L;
    }

    /**
     * 获取指定实例和键的列表的大小。
     *
     * @param instanceId 实例ID
     * @param key        列表的键
     * @return 列表的大小(- 1代表异常)
     */
    @Override
    public Long queueSizeForInstance(String instanceId, String key) {
        return 0L;
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
    @Override
    public Boolean queueTrimForInstance(String instanceId, String key, long start, long end) {
        return null;
    }

    /**
     * 向指定实例和键的集合中添加元素集合。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param elements   要添加的元素集合
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 是否添加成功
     */
    @Override
    public <E> Boolean setAddForInstance(String instanceId, String key, Collection<E> elements, long expiry, TimeUnit unit) {
        return null;
    }

    /**
     * 从指定实例和键的集合中移除元素集合。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param elements   要移除的元素集合
     * @return 是否移除成功
     */
    @Override
    public <E> Boolean setRemoveForInstance(String instanceId, String key, Collection<E> elements) {
        return null;
    }

    /**
     * 判断指定实例和键的集合中是否包含某个元素。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param element    要检查的元素
     * @return 是否包含该元素
     */
    @Override
    public <E> Boolean setContainsForInstance(String instanceId, String key, E element) {
        return null;
    }

    /**
     * 获取指定实例和键的集合的元素数量。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @return 元素数量
     */
    @Override
    public int setSizeForInstance(String instanceId, String key) {
        return 0;
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
    @Override
    public <E> Boolean setMoveForInstance(String instanceId, String key, E value, String destKey) {
        return null;
    }

    /**
     * 获取符合指定格式的元素集合
     *
     * @param instanceId        实例ID
     * @param key               元素的键
     * @param pattern           元素格式
     * @param listTypeReference 元素的类型
     * @return 操作结果
     */
    @Override
    public <E> Set<E> setScanForInstance(String instanceId, String key, String pattern, TypeReference<List<E>> listTypeReference) {
        return Set.of();
    }

    /**
     * 获取指定实例和键的集合中的随机一个元素，并移除。
     *
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param elementType 元素的类型
     * @return 集合中的随机元素
     */
    @Override
    public <E> E setPopForInstance(String instanceId, String key, Class<E> elementType) {
        return null;
    }

    /**
     * 获取指定实例和键的集合中的随机一个元素。
     *
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param elementType 元素的类型
     * @return 集合中的随机元素
     */
    @Override
    public <E> E setRandomMemberForInstance(String instanceId, String key, Class<E> elementType) {
        return null;
    }

    /**
     * 获取指定实例和键的集合中的随机多个元素（可能重复）。
     *
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param count             返回的元素个数
     * @param listTypeReference 元素的类型
     * @return 集合中的随机元素
     */
    @Override
    public <E> List<E> setRandomMembersForInstance(String instanceId, String key, long count, TypeReference<List<E>> listTypeReference) {
        return List.of();
    }

    /**
     * 从一个特定的集合中随机选择一定数量的不同元素，并返回一个包含这些元素的集合。
     *
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param count             返回的元素个数
     * @param listTypeReference 元素的类型
     * @return 集合中的随机元素
     */
    @Override
    public <E> Set<E> setDistinctRandomMembersForInstance(String instanceId, String key, long count, TypeReference<List<E>> listTypeReference) {
        return Set.of();
    }

    /**
     * 获取指定实例和键的集合中的所有元素。
     *
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param iPage             分页参数
     * @param listTypeReference 元素的类型
     * @return 集合中的所有元素
     */
    @Override
    public <E> Set<E> setGetPageForInstance(String instanceId, String key, IPage iPage, TypeReference<List<E>> listTypeReference) {
        return Set.of();
    }

    /**
     * 计算多个集合之间的差异，返回一个包含在第一个集合中但不在其他任何集合中的元素的集合。
     *
     * @param instanceId        实例ID
     * @param keys              集合的键
     * @param listTypeReference 元素的类型
     * @return 集合中的所有元素
     */
    @Override
    public <E> Set<E> setDifferenceForInstance(String instanceId, Collection<String> keys, TypeReference<List<E>> listTypeReference) {
        return Set.of();
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
    @Override
    public Long setDifferenceAndStoreForInstance(String instanceId, Collection<String> keys, String destKey, long expiry, TimeUnit unit) {
        return 0L;
    }

    /**
     * 计算给定的多个集合的交集。
     *
     * @param instanceId        实例ID
     * @param keys              集合的键
     * @param listTypeReference 元素的类型
     * @return 集合中的所有元素
     */
    @Override
    public <E> Set<E> setIntersectForInstance(String instanceId, Collection<String> keys, TypeReference<List<E>> listTypeReference) {
        return Set.of();
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
    @Override
    public Long setIntersectAndStoreForInstance(String instanceId, Collection<String> keys, String destKey, long expiry, TimeUnit unit) {
        return 0L;
    }

    /**
     * 计算给定多个集合的并集。
     *
     * @param instanceId        实例ID
     * @param keys              集合的键
     * @param listTypeReference 元素的类型
     * @return 集合中的所有元素
     */
    @Override
    public <E> Set<E> setUnionForInstance(String instanceId, Collection<String> keys, TypeReference<List<E>> listTypeReference) {
        return Set.of();
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
    @Override
    public Long setUnionAndStoreForInstance(String instanceId, Collection<String> keys, String destKey, long expiry, TimeUnit unit) {
        return 0L;
    }

    /**
     * 从指定实例中获取指定键的字节数组值。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @return 获取的字节数组值
     */
    @Override
    public byte[] getBytesForInstance(String instanceId, String key) {
        return new byte[0];
    }

    /**
     * 从指定实例中获取指定键的字符串值。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @return 获取的字符串值
     */
    @Override
    public String getStrForInstance(String instanceId, String key) {
        return "";
    }

    /**
     * 从指定实例中获取指定键的对象值。
     *
     * @param instanceId  实例ID
     * @param key         存储的键
     * @param elementType 对象的类型类
     * @return 获取的对象值
     */
    @Override
    public <V> V getObjForInstance(String instanceId, String key, Class<V> elementType) {
        return null;
    }

    /**
     * 从指定实例中获取指定键的对象列表值。
     *
     * @param instanceId        实例ID
     * @param key               存储的键
     * @param listTypeReference 列表中元素的类型类
     * @return 获取的对象列表值
     */
    @Override
    public <E> List<E> getListForInstance(String instanceId, String key, TypeReference<List<E>> listTypeReference) {
        return List.of();
    }

    /**
     * 根据多个键获取字符串值。
     *
     * @param instanceId 实例ID
     * @param keys       键的集合
     * @return 字符串值的列表
     */
    @Override
    public List<String> multiGetStrForInstance(String instanceId, Collection<String> keys) {
        return List.of();
    }

    /**
     * 根据多个键获取对象值。
     *
     * @param instanceId  实例ID
     * @param keys        键的集合
     * @param elementType 元素的类型类对象
     * @return 对象值的列表
     */
    @Override
    public <E> List<E> multiGetObjForInstance(String instanceId, Collection<String> keys, Class<E> elementType) {
        return List.of();
    }

    /**
     * 根据多个键获取列表值。
     *
     * @param instanceId        实例ID
     * @param keys              键的集合
     * @param listTypeReference 列表元素的类型类对象
     * @return 列表值的列表
     */
    @Override
    public <E> List<List<E>> multiGetListForInstance(String instanceId, Collection<String> keys, TypeReference<List<E>> listTypeReference) {
        return List.of();
    }

    /**
     * 从指定实例中获取指定键的对象值。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @return 获取的对象值
     */
    @Override
    public Object getObjForInstance(String instanceId, String key) {
        return null;
    }

    /**
     * 根据多个键获取对象值。
     *
     * @param instanceId 实例ID
     * @param keys       键的集合
     * @return 对象值的列表
     */
    @Override
    public List<Object> multiGetObjForInstance(String instanceId, Collection<String> keys) {
        return List.of();
    }

    /**
     * 将指定键的值存储到指定实例中，并设置过期时间和是否覆盖已有值。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param value      要存储的值
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @param override   是否覆盖已有值
     * @return 操作是否成功
     */
    @Override
    public Boolean putForInstance(String instanceId, String key, Object value, long expiry, TimeUnit unit, boolean override) {
        return null;
    }

    /**
     * 将字符串值追加到指定实例和键对应的值后面。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param value      要追加的字符串值
     * @return 操作是否成功
     */
    @Override
    public Boolean appendForInstance(String instanceId, String key, String value) {
        return null;
    }

    /**
     * 获取指定实例和键的计数值。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @return 实例和键对应的计数值
     */
    @Override
    public String counterForInstance(String instanceId, String key) {
        return "";
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
    @Override
    public Long counterForInstance(String instanceId, String key, Long initial, Long dela, long expiry, TimeUnit unit) {
        return 0L;
    }

    /**
     * 增加指定实例和键的计数值并设置初始值和过期时间（双精度类型）。
     *
     * @param instanceId 实例ID
     * @param key        计数器的键
     * @param initial    初始值（双精度类型）
     * @param dela       增加的值（双精度类型）
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 实例和键对应的计数值
     */
    @Override
    public Double counterForInstance(String instanceId, String key, Double initial, Double dela, long expiry, TimeUnit unit) {
        return 0.0;
    }

    /**
     * 向指定实例和键的有序集合中添加元素，并设置过期时间。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param score      元素的分值
     * @param value      要添加的元素
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @param override   是否覆盖已有值
     * @return 是否添加成功
     */
    @Override
    public <E> Boolean zsetAddForInstance(String instanceId, String key, double score, E value, long expiry, TimeUnit unit, boolean override) {
        return null;
    }

    /**
     * 向指定实例和键的有序集合中批量添加元素，并设置过期时间。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param values     批量添加的元素
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @param override   是否覆盖已有值
     * @return 是否添加成功
     */
    @Override
    public <E> Boolean zsetAddMoreForInstance(String instanceId, String key, Map<E, Double> values, long expiry, TimeUnit unit, boolean override) {
        return null;
    }

    /**
     * 获取符合指定格式的元素集合
     *
     * @param instanceId  实例ID
     * @param key         元素的键
     * @param pattern     元素格式
     * @param elementType 元素的类型
     * @return 操作结果
     */
    @Override
    public <E> Map<E, Double> zsetScanForInstance(String instanceId, String key, String pattern, Class<E> elementType) {
        return Map.of();
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
    @Override
    public <E> Double zsetIncrementScoreForInstance(String instanceId, String key, E value, double delta) {
        return 0.0;
    }

    /**
     * 获取指定实例和键的有序集合的元素数量。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @return 元素数量（-1时表示获取异常）
     */
    @Override
    public Long zsetCardForInstance(String instanceId, String key) {
        return 0L;
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
    @Override
    public Long zsetCountForInstance(String instanceId, String key, double min, double max) {
        return 0L;
    }

    /**
     * 获取指定实例和键的有序集合中，指定元素的分值。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param value      要获取分值的元素
     * @return 元素的分值
     */
    @Override
    public <E> double zsetScoreForInstance(String instanceId, String key, E value) {
        return 0L;
    }

    /**
     * 移除指定实例和键的有序集合中的一个或多个元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param value      要移除的元素
     * @return 移除的元素数量(- 1表示移除异常)
     */
    @Override
    public <E> Long zsetRemoveForInstance(String instanceId, String key, List<E> value) {
        return 0L;
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
    @Override
    public Long zsetRemoveRangeForInstance(String instanceId, String key, long start, long end) {
        return 0L;
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
    @Override
    public Long zsetRemoveRangeByLexForInstance(String instanceId, String key, String lowerBound, String upperBound) {
        return 0L;
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
    @Override
    public Long zsetRemoveRangeByScoreForInstance(String instanceId, String key, double min, double max) {
        return 0L;
    }

    /**
     * 计算一个集合和多个集合之间的差异，返回一个包含在第一个集合中但不在其他集合中的元素的集合。
     *
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param otherKeys         其它集合的键的集合
     * @param listTypeReference 元素的类型
     * @return 集合中的所有元素
     */
    @Override
    public <E> Set<E> zsetDifferenceForInstance(String instanceId, String key, Collection<String> otherKeys, TypeReference<List<E>> listTypeReference) {
        return Set.of();
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
    @Override
    public Long zsetDifferenceAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit) {
        return 0L;
    }

    /**
     * 计算给定的多个集合的差异，并将结果返回。
     *
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其他的键的集合
     * @param elementType 元素的类型
     * @return 多个集合的差异
     */
    @Override
    public <E> Map<E, Double> zsetDifferenceWithScoresForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> elementType) {
        return Map.of();
    }

    /**
     * 计算给定的集合和多个其他集合的交集。
     *
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param otherKeys         其它集合的键的集合
     * @param listTypeReference 元素的类型
     * @return 集合中的所有元素
     */
    @Override
    public <E> Set<E> zsetIntersectForInstance(String instanceId, String key, Collection<String> otherKeys, TypeReference<List<E>> listTypeReference) {
        return Set.of();
    }

    /**
     * 计算给定的集合和多个其他集合的交集。
     *
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param elementType 元素的类型
     * @return 集合中的所有元素
     */
    @Override
    public <E> Map<E, Double> zsetIntersectWithScoresForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> elementType) {
        return Map.of();
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
    @Override
    public Long zsetIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit) {
        return 0L;
    }

    /**
     * 计算给定的集合和多个其他集合的并集。
     *
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param otherKeys         其它集合的键的集合
     * @param listTypeReference 元素的类型
     * @return 集合中的所有元素
     */
    @Override
    public <E> Set<E> zsetUnionForInstance(String instanceId, String key, Collection<String> otherKeys, TypeReference<List<E>> listTypeReference) {
        return Set.of();
    }

    /**
     * 计算给定的集合和多个其他集合的并集。
     *
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param elementType 元素的类型
     * @return 集合中的所有元素
     */
    @Override
    public <E> Map<E, Double> zsetUnionWithScoresForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> elementType) {
        return Map.of();
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
    @Override
    public Long zsetUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, long expiry, TimeUnit unit) {
        return 0L;
    }

    /**
     * 从一个特定的集合中随机选择返回一个元素。
     *
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param elementType 元素的类型
     * @return 集合中的随机元素
     */
    @Override
    public <E> E zsetRandomMembersForInstance(String instanceId, String key, Class<E> elementType) {
        return null;
    }

    /**
     * 从一个特定的集合中随机选择一定数量的不同元素，并返回一个包含这些元素的集合。
     *
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param count             返回的元素个数
     * @param listTypeReference 元素的类型
     * @return 集合中的随机元素
     */
    @Override
    public <E> Set<E> zsetDistinctRandomMembersForInstance(String instanceId, String key, long count, TypeReference<List<E>> listTypeReference) {
        return Set.of();
    }

    /**
     * 从一个特定的集合中随机选择一定数量的不同元素，并返回一个包含这些元素的集合。
     *
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param count       返回的元素个数
     * @param elementType 元素的类型
     * @return 集合中的随机元素
     */
    @Override
    public <E> Map<E, Double> zsetDistinctRandomMembersWithScoreForInstance(String instanceId, String key, long count, Class<E> elementType) {
        return Map.of();
    }

    /**
     * 弹出指定实例中有序集合分数最高的元素。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param elementType 元素的类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    @Override
    public <E> Pair<E, Double> zsetPopMaxForInstance(String instanceId, String key, Class<E> elementType) {
        return null;
    }

    /**
     * 弹出指定实例中有序集合分数最高的元素，并在指定过期时间内等待。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param expiry      过期时间
     * @param unit        时间单位
     * @param elementType 元素的类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    @Override
    public <E> Pair<E, Double> zsetPopMaxForInstance(String instanceId, String key, long expiry, TimeUnit unit, Class<E> elementType) {
        return null;
    }

    /**
     * 弹出指定实例中有序集合指定数量的分数最高的元素。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param count       弹出元素的数量
     * @param elementType 元素的类型
     * @return 包含弹出元素及其分数的Map对象
     */
    @Override
    public <E> Map<E, Double> zsetPopMaxForInstance(String instanceId, String key, long count, Class<E> elementType) {
        return Map.of();
    }

    /**
     * 弹出指定实例中有序集合分数最低的元素。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param elementType 元素的类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    @Override
    public <E> Pair<E, Double> zsetPopMinForInstance(String instanceId, String key, Class<E> elementType) {
        return null;
    }

    /**
     * 弹出指定实例中有序集合分数最低的元素，并在指定过期时间内等待。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param expiry      过期时间
     * @param unit        时间单位
     * @param elementType 元素的类型
     * @return 包含弹出元素及其分数的Pair对象
     */
    @Override
    public <E> Pair<E, Double> zsetPopMinForInstance(String instanceId, String key, long expiry, TimeUnit unit, Class<E> elementType) {
        return null;
    }

    /**
     * 弹出指定实例中有序集合指定数量的分数最低的元素。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param count       弹出元素的数量
     * @param elementType 元素的类型
     * @return 包含弹出元素及其分数的Map对象
     */
    @Override
    public <E> Map<E, Double> zsetPopMinForInstance(String instanceId, String key, long count, Class<E> elementType) {
        return Map.of();
    }

    /**
     * 获取元素的排序
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param value      指定元素
     * @return 元素的排序（异常时返回-1）
     */
    @Override
    public <E> Long zsetRankForInstance(String instanceId, String key, E value) {
        return 0L;
    }

    /**
     * 获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param min               最小值
     * @param max               最大值
     * @param listTypeReference 类型引用
     * @return 指定范围的元素集合
     */
    @Override
    public <E> Set<E> zsetRangeForInstance(String instanceId, String key, long min, long max, TypeReference<List<E>> listTypeReference) {
        return Set.of();
    }

    /**
     * 根据字典序获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param lowerBound        下边界
     * @param upperBound        上边界
     * @param listTypeReference 类型引用
     * @return 指定范围的元素集合
     */
    @Override
    public <E> Set<E> zsetRangeByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, TypeReference<List<E>> listTypeReference) {
        return Set.of();
    }

    /**
     * 根据分数获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param min               最小值
     * @param max               最大值
     * @param listTypeReference 类型引用
     * @return 指定范围的元素集合
     */
    @Override
    public <E> Set<E> zsetRangeByScoreForInstance(String instanceId, String key, double min, double max, TypeReference<List<E>> listTypeReference) {
        return Set.of();
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
     * @param listTypeReference 类型引用
     * @return 指定范围的元素集合
     */
    @Override
    public <E> Set<E> zsetRangeByScoreForInstance(String instanceId, String key, double min, double max, long offset, long count, TypeReference<List<E>> listTypeReference) {
        return Set.of();
    }

    /**
     * 根据分数获取指定实例中指定范围的元素及其分数。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param min         最小值
     * @param max         最大值
     * @param elementType 元素类型
     * @return 指定范围的元素及其分数的映射
     */
    @Override
    public <E> Map<E, Double> zsetRangeByScoreWithScoresForInstance(String instanceId, String key, double min, double max, Class<E> elementType) {
        return Map.of();
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
     * @param elementType 元素类型
     * @return 指定范围的元素及其分数的映射
     */
    @Override
    public <E> Map<E, Double> zsetRangeByScoreWithScoresForInstance(String instanceId, String key, double min, double max, long offset, long count, Class<E> elementType) {
        return Map.of();
    }

    /**
     * 获取指定实例中指定范围的元素及其分数。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param start       起始位置
     * @param end         结束位置
     * @param elementType 元素类型
     * @return 指定范围的元素及其分数的映射
     */
    @Override
    public <E> Map<E, Double> zsetRangeWithScoresForInstance(String instanceId, String key, long start, long end, Class<E> elementType) {
        return Map.of();
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
    @Override
    public Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, String destKey, long expiry, TimeUnit unit) {
        return 0L;
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
    @Override
    public Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, String destKey, long expiry, TimeUnit unit) {
        return 0L;
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param min               最小分数
     * @param max               最大分数
     * @param listTypeReference 类型引用，用于指定元素的类型
     * @return 反向获取的元素集合
     */
    @Override
    public <E> Set<E> zsetReverseRangeForInstance(String instanceId, String key, long min, long max, TypeReference<List<E>> listTypeReference) {
        return Set.of();
    }

    /**
     * 根据指定的字典范围从指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param lowerBound        下边界
     * @param upperBound        上边界
     * @param listTypeReference 类型引用，用于指定元素的类型
     * @return 反向获取的元素集合
     */
    @Override
    public <E> Set<E> zsetReverseRangeByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, TypeReference<List<E>> listTypeReference) {
        return Set.of();
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param min               最小分数
     * @param max               最大分数
     * @param listTypeReference 类型引用，用于指定元素的类型
     * @return 反向获取的元素集合
     */
    @Override
    public <E> Set<E> zsetReverseRangeByScoreForInstance(String instanceId, String key, double min, double max, TypeReference<List<E>> listTypeReference) {
        return Set.of();
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
     * @param listTypeReference 类型引用，用于指定元素的类型
     * @return 反向获取的元素集合
     */
    @Override
    public <E> Set<E> zsetReverseRangeByScoreForInstance(String instanceId, String key, double min, double max, long offset, long count, TypeReference<List<E>> listTypeReference) {
        return Set.of();
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素及其分数，并将其作为映射返回。
     *
     * @param instanceId  实例 ID
     * @param key         有序集合的键
     * @param min         最小分数
     * @param max         最大分数
     * @param elementType 元素类型
     * @return 反向获取的元素及其分数的映射
     */
    @Override
    public <E> Map<E, Double> zsetReverseRangeByScoreWithScoresForInstance(String instanceId, String key, double min, double max, Class<E> elementType) {
        return Map.of();
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
     * @param elementType 元素类型
     * @return 反向获取的元素及其分数的映射
     */
    @Override
    public <E> Map<E, Double> zsetReverseRangeByScoreWithScoresForInstance(String instanceId, String key, double min, double max, long offset, long count, Class<E> elementType) {
        return Map.of();
    }

    /**
     * 根据指定的范围从指定的实例中反向获取元素及其分数，并将其作为映射返回。
     *
     * @param instanceId  实例 ID
     * @param key         有序集合的键
     * @param start       开始索引
     * @param end         结束索引
     * @param elementType 元素类型
     * @return 反向获取的元素及其分数的映射
     */
    @Override
    public <E> Map<E, Double> zsetReverseRangeWithScoresForInstance(String instanceId, String key, long start, long end, Class<E> elementType) {
        return Map.of();
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
    @Override
    public Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String key, String lowerBound, String upperBound, String destKey, long expiry, TimeUnit unit) {
        return 0L;
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
    @Override
    public Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Number lowerBound, Number upperBound, String destKey, long expiry, TimeUnit unit) {
        return 0L;
    }
}
