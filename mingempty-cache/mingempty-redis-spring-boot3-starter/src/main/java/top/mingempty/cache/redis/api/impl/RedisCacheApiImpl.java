package top.mingempty.cache.redis.api.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonRxClient;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Range;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.Limit;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.zset.Aggregate;
import org.springframework.data.redis.connection.zset.Weights;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import top.mingempty.cache.redis.entity.wapper.RedisCacheManagerWrapper;
import top.mingempty.cache.redis.entity.wapper.RedisTemplateWapper;
import top.mingempty.cache.redis.entity.wapper.RedissonClientWapper;
import top.mingempty.cache.redis.entity.wapper.RedissonRxClientWapper;
import top.mingempty.cache.redis.api.RedisCacheApi;
import top.mingempty.commons.util.CollectionUtil;
import top.mingempty.commons.util.JacksonUtil;
import top.mingempty.domain.base.IPage;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis缓存API实现类
 *
 * @author zzhao
 */
@Slf4j
@AllArgsConstructor
public class RedisCacheApiImpl implements RedisCacheApi {

    private final ObjectMapper redisObjectMapper;
    private final RedisCacheManagerWrapper redisCacheManagerWrapper;
    private final RedissonClientWapper redissonClientWapper;
    private final RedissonRxClientWapper redissonRxClientWapper;
    private final RedisTemplateWapper redisTemplateWapper;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取RedisCacheManagerWrapper
     *
     * @return CacheManager
     */
    @Override
    public CacheManager cacheManager() {
        return redisCacheManagerWrapper;
    }

    /**
     * 获取RedissonClientWapper
     *
     * @return RedissonClient
     */
    @Override
    public RedissonClient redissonClient() {
        return redissonClientWapper;
    }

    /**
     * 获取RedissonRxClientWapper
     *
     * @return RedissonRxClient
     */
    @Override
    public RedissonRxClient redissonRxClient() {
        return redissonRxClientWapper;
    }

    /**
     * 获取RedisTemplateWapper
     *
     * @return RedisOperations<String, Object>
     */
    @Override
    public RedisOperations<String, Object> redisOperations() {
        return redisTemplateWapper;
    }

    /**
     * 获取RedisTemplate
     *
     * @return RedisTemplate<String, Object>
     */
    @Override
    public RedisTemplate<String, Object> redisTemplate() {
        return redisTemplate;
    }

    /**
     * 在指定实例中扫描指定格式的键
     *
     * @param instanceId 实例ID
     * @param options    格式
     * @return 键集合
     */
    @Override
    public Set<String> scanForInstance(String instanceId, ScanOptions options) {
        try (Cursor<String> scanCursor = redisTemplateWapper.getResolvedRouter(instanceId).scan(options)) {
            return scanCursor.stream().collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("Error while scan key", e);
            return null;
        }
    }

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
        try {
            redisTemplateWapper.getResolvedRouter(instanceId).rename(oldKey, newKey);
            return true;
        } catch (Exception e) {
            log.error("rename error", e);
            return false;
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).hasKey(key);
        } catch (Exception e) {
            log.error("exists error", e);
            return false;
        }
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
        try {
            Long expire = redisTemplateWapper.getResolvedRouter(instanceId).getExpire(key, unit);
            return expire == null ? -3 : expire;
        } catch (Exception e) {
            log.error("ttl error", e);
            return -2L;
        }
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
        try {
            return expiry == 0L
                    //等于0时不做任何处理
                    ? Boolean.TRUE :
                    (expiry < 0L
                            //小于0时，清空过期时间
                            ? this.persistForInstance(instanceId, key) :
                            //大于0设置过期时间
                            this.redisTemplateWapper.getResolvedRouter(instanceId).expire(key, expiry, unit));
        } catch (Exception e) {
            log.error("update expires error", e);
            return false;
        }
    }

    /**
     * 在指定实例中删除缓存中的指定键。
     *
     * @param instanceId 实例ID
     * @param key        要删除的键
     */
    @Override
    public Boolean delForInstance(String instanceId, String key) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).delete(key);
        } catch (Exception e) {
            log.error("evict error", e);
            return false;
        }
    }

    /**
     * 在指定实例中删除缓存中的指定键。
     *
     * @param instanceId 实例ID
     * @param keys       已删除的数量
     */
    @Override
    public Long delForInstance(String instanceId, Collection<String> keys) {
        try {
            Long delete = redisTemplateWapper.getResolvedRouter(instanceId).delete(keys);
            return delete == null ? -4L : delete;
        } catch (Exception e) {
            log.error("evict error", e);
            return -2L;
        }
    }

    /**
     * 在指定实例中根据命名空间删除所有相关的缓存数据。
     *
     * @param instanceId 实例ID
     * @param namespace  要删除的命名空间
     */
    @Override
    public Boolean delByNamespaceForInstance(String instanceId, String namespace) {
        try {
            if (namespace == null) {
                namespace = "";
            }
            Set<String> keysToDelete = scanForInstance(instanceId, namespace);
            if (CollUtil.isEmpty(keysToDelete)) {
                return true;
            }
            return delForInstance(instanceId, keysToDelete) >= 0;
        } catch (Exception e) {
            log.error("del by namespace error", e);
            return false;
        }
    }

    /**
     * 在指定实例中清空缓存中的所有数据。
     *
     * @param instanceId 实例ID
     * @return 操作是否成功
     */
    @Override
    public Boolean clearForInstance(String instanceId) {
        try {
            redisTemplateWapper.getResolvedRouter(instanceId).execute((RedisCallback<Object>) connection
                    -> {
                connection.serverCommands().flushAll();
                return null;
            });
            return Boolean.TRUE;
        } catch (Exception e) {
            log.error("clear error", e);
            return Boolean.FALSE;
        }
    }

    /**
     * 在指定实例中关闭缓存连接。
     *
     * @param instanceId 实例ID
     * @return 操作是否成功
     */
    @Override
    public Boolean closeForInstance(String instanceId) {
        //throw new RedisCacheException("0000000004");'
        return Boolean.TRUE;
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).unlink(key);
        } catch (Exception e) {
            log.error("Error while unlinking key", e);
            return false;
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).persist(key);
        } catch (Exception e) {
            log.error("Error while persist key", e);
            return false;
        }
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
        ValueOperations<String, Object> ops = redisTemplateWapper.getResolvedRouter(instanceId).opsForValue();
        Object object = ops.get(key);
        return object != null ? JacksonUtil.toStr(redisObjectMapper, object) : null;
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
        ValueOperations<String, Object> ops = redisTemplateWapper.getResolvedRouter(instanceId).opsForValue();
        if (0 == initial) {
            if (!existsForInstance(instanceId, key)) {
                ops.set(key, initial);
            }
        } else {
            ops.set(key, initial);
        }
        Long increment = ops.increment(key, dela);
        updateExpiresForInstance(instanceId, key, expiry, unit);
        return increment;
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
    @Override
    public Double counterForInstance(String instanceId, String key, Double initial, Double dela, long expiry, TimeUnit unit) {
        ValueOperations<String, Object> ops = redisTemplateWapper.getResolvedRouter(instanceId).opsForValue();
        if (Double.compare(0, initial) == 0) {
            if (!existsForInstance(instanceId, key)) {
                ops.set(key, initial);
            }
        } else {
            ops.set(key, initial);
        }
        Double increment = ops.increment(key, dela);
        updateExpiresForInstance(instanceId, key, expiry, unit);
        return increment;
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
        try {
            redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().putAll(key, map);
            updateExpiresForInstance(instanceId, key, expiry, unit);
            return true;
        } catch (Exception e) {
            log.error("map set error", e);
            return false;
        }
    }

    /**
     * 向指定实例和键的映射中添加键-值对。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      要添加的键
     * @param value      要添加的值
     * @return 是否添加成功
     */
    @Override
    public <E> Boolean mapAddForInstance(String instanceId, String key, String field, E value) {
        try {
            redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().put(key, field, value);
            return true;
        } catch (Exception e) {
            log.error("map add error", e);
            return false;
        }
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
        try {
            if (override) {
                redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().put(key, field, value);
            } else {
                redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().putIfAbsent(key, field, value);
            }
            updateExpiresForInstance(instanceId, key, expiry, unit);
            return true;
        } catch (Exception e) {
            log.error("map add error", e);
            return false;
        }
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
        try {
            if (CollUtil.isEmpty(fields)) {
                return 0L;
            }
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().delete(key, fields.toArray());
        } catch (Exception e) {
            log.error("map del fields error", e);
            return -2L;
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).delete(key);
        } catch (Exception e) {
            log.error("map del error", e);
            return Boolean.FALSE;
        }
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
        try {
            Object object = redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().get(key, field);
            if (object == null) {
                return null;
            }
            return JacksonUtil.toObj(redisObjectMapper, object, valueType);
        } catch (Exception e) {
            log.error("map get field error", e);
            return null;
        }
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
        try {
            if (CollUtil.isEmpty(fields)) {
                return List.of();
            }
            List<Object> objects = redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().multiGet(key, new ArrayList<>(fields));
            if (CollUtil.isEmpty(objects)) {
                return List.of();
            }
            return objects.parallelStream()
                    .map(object -> JacksonUtil.toObj(redisObjectMapper, object, valueType))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("map multi get error", e);
            return List.of();
        }
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
        try {
            if (CollUtil.isEmpty(fields)) {
                return List.of();
            }
            List<Object> objects = redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().multiGet(key, new ArrayList<>(fields));
            return JacksonUtil.toList(redisObjectMapper, objects, listTypeReference);
        } catch (Exception e) {
            log.error("map multi get error", e);
            return List.of();
        }
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
        try {
            Map<Object, Object> entries = redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().entries(key);
            return entries.entrySet()
                    .stream()
                    .collect(Collectors.toMap(e -> String.valueOf(e.getKey()), e -> JacksonUtil.toObj(redisObjectMapper, e.getValue(), valueType)));
        } catch (Exception e) {
            log.error("map get error", e);
            return Map.of();
        }
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
        try {
            Set<String> keys = mapKeysPageForInstance(instanceId, key, iPage);
            List<String> keyList = new ArrayList<>(keys);
            List<E> multiGet = mapMultiGetForInstance(instanceId, key, keyList, valueType);
            Map<String, E> map = new HashMap<>();
            for (int i = 0; i < keyList.size(); i++) {
                map.put(keyList.get(i), multiGet.get(i));
            }
            return map;
        } catch (Exception e) {
            log.error("map get page error", e);
            return Map.of();
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().size(key).intValue();
        } catch (Exception e) {
            log.error("map size error", e);
            return 0;
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().hasKey(key, field);
        } catch (Exception e) {
            log.error("map contains field error", e);
            return false;
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().values(key).contains(value);
        } catch (Exception e) {
            log.error("map contains value error", e);
            return false;
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().increment(key, field, dela);
        } catch (Exception e) {
            log.error("map increment error", e);
            return null;
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().increment(key, field, dela);
        } catch (Exception e) {
            log.error("map increment error", e);
            return null;
        }
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
        try {
            Set<Object> fields = redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().keys(key);
            if (CollUtil.isEmpty(fields)) {
                return Set.of();
            }
            return fields.parallelStream()
                    .map(field
                            -> JacksonUtil.toStr(redisObjectMapper, field))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("map keys error", e);
            return Set.of();
        }
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
    public Set<String> mapKeysPageForInstance(String instanceId, String key, final IPage iPage) {
        try {
            Set<String> keys = mapKeysForInstance(instanceId, key);
            if (ObjUtil.isEmpty(iPage)) {
                return keys;
            }
            if (iPage.isSearchCount()) {
                iPage.setTotal(mapSizeForInstance(instanceId, key));
            }
            return new HashSet<>(CollectionUtil.batchSubList(keys, iPage));
        } catch (Exception e) {
            log.error("map keys page error", e);
            return Set.of();
        }
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
        try {
            List<Object> values = redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().values(key);
            return JacksonUtil.toList(redisObjectMapper, values, listTypeReference);
        } catch (Exception e) {
            log.error("map values error", e);
            return List.of();
        }
    }

    /**
     * 获取映射键内指定hash键值的长度
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param field      hash的键
     * @return 映射键内指定hash键值的长度
     */
    @Override
    public Long mapLengthOfValueForInstance(String instanceId, String key, String field) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().lengthOfValue(key, field);
        } catch (Exception e) {
            log.error("map length of value error", e);
            return -1L;
        }
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
        try {
            Map.Entry<Object, Object> entry =
                    redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().randomEntry(key);
            if (ObjUtil.isEmpty(entry)) {
                return null;
            }
            return Pair.of(JacksonUtil.toStr(redisObjectMapper, entry.getKey()), JacksonUtil.toObj(redisObjectMapper, entry.getValue(), valueType));
        } catch (Exception e) {
            log.error("map random_entry error", e);
            return null;
        }
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
        try {
            if (count == 0) {
                return Map.of();
            }
            if (count < 0) {
                List<String> randomKeysForInstance = mapRandomKeysForInstance(instanceId, key, count);
                if (CollUtil.isEmpty(randomKeysForInstance)) {
                    return Map.of();
                }
                List<Object> objectList = mapMultiGetForInstance(instanceId, key, randomKeysForInstance, new TypeReference<>() {
                });
                Map<String, E> map = new HashMap<>(objectList.size());

                for (int i = 0; i < randomKeysForInstance.size(); i++) {
                    map.put(randomKeysForInstance.get(i), JacksonUtil.toObj(redisObjectMapper, objectList.get(i), valueType));
                }
                return map;
            }

            if (count == 1) {
                Pair<String, E> pair = mapRandomEntryForInstance(instanceId, key, valueType);
                if (ObjUtil.isEmpty(pair)) {
                    return Map.of();
                }
                return Map.of(pair.getKey(), pair.getValue());
            }

            Map<Object, Object> objectMap = redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().randomEntries(key, count);
            if (CollUtil.isEmpty(objectMap)) {
                return Map.of();
            }
            return objectMap.entrySet()
                    .parallelStream()
                    .collect(Collectors.toMap(entry -> JacksonUtil.toStr(redisObjectMapper, entry.getKey()), entry -> JacksonUtil.toObj(redisObjectMapper, entry.getValue(), valueType)));

        } catch (Exception e) {
            log.error("map random entries error", e);
            return Map.of();
        }
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
        try {
            Object object = redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().randomKey(key);
            return JacksonUtil.toStr(redisObjectMapper, object);
        } catch (Exception e) {
            log.error("map random key error", e);
            return null;
        }
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
        try {
            if (count == 0) {
                return List.of();
            }
            List<Object> object = redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().randomKeys(key, count);
            if (CollUtil.isEmpty(object)) {
                return List.of();
            }
            return object.parallelStream()
                    .map(field -> JacksonUtil.toStr(redisObjectMapper, field))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("map random keys error", e);
            return List.of();
        }
    }

    /**
     * 随机获取映射键内指定数量值。
     *
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param options    元素格式
     * @param valueType  映射中值的类型
     * @return 随机的数据
     */
    @Override
    public <E> Map<String, E> mapScanForInstance(String instanceId, String key, ScanOptions options, Class<E> valueType) {
        try (Cursor<Map.Entry<Object, Object>> scanCursor =
                     redisTemplateWapper.getResolvedRouter(instanceId).opsForHash().scan(key, options)) {
            return scanCursor.stream().parallel()
                    .collect(Collectors.toMap(entry -> JacksonUtil.toStr(redisObjectMapper, entry.getKey()), entry -> JacksonUtil.toObj(redisObjectMapper, entry.getValue(), valueType)));

        } catch (Exception e) {
            log.error("Error while set scan key", e);
            return Map.of();
        }
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
        try {
            redisTemplateWapper.getResolvedRouter(instanceId).opsForList().rightPush(key, element);
            updateExpiresForInstance(instanceId, key, expiry, unit);
            return true;
        } catch (Exception e) {
            log.error("queue push error", e);
            return false;
        }
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
        try {
            redisTemplateWapper.getResolvedRouter(instanceId).opsForList().rightPushAll(key, elements);
            updateExpiresForInstance(instanceId, key, expiry, unit);
            return true;
        } catch (Exception e) {
            log.error("queue push all error", e);
            return false;
        }
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
        try {
            redisTemplateWapper.getResolvedRouter(instanceId).opsForList().leftPush(key, element);
            updateExpiresForInstance(instanceId, key, expiry, unit);
            return true;
        } catch (Exception e) {
            log.error("queue push error", e);
            return false;
        }
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
        try {
            redisTemplateWapper.getResolvedRouter(instanceId).opsForList().leftPushAll(key, elements);
            updateExpiresForInstance(instanceId, key, expiry, unit);
            return true;
        } catch (Exception e) {
            log.error("queue lpush all error", e);
            return false;
        }
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
        try {
            redisTemplateWapper.getResolvedRouter(instanceId).opsForList().set(key, index, element);
            updateExpiresForInstance(instanceId, key, expiry, unit);
            return true;
        } catch (Exception e) {
            log.error("queue set error", e);
            return false;
        }
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
        try {
            Object object = redisTemplateWapper.getResolvedRouter(instanceId).opsForList().leftPop(key);
            return JacksonUtil.toObj(redisObjectMapper, object, elementType);
        } catch (Exception e) {
            log.error("queue lpop error", e);
            return null;
        }
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
        try {
            Object object = redisTemplateWapper.getResolvedRouter(instanceId).opsForList().leftPop(key, timeout, unit);
            return JacksonUtil.toObj(redisObjectMapper, object, elementType);
        } catch (Exception e) {
            log.error("queue lpop error", e);
            return null;
        }
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
        try {
            Object object = redisTemplateWapper.getResolvedRouter(instanceId).opsForList().rightPop(key);
            return JacksonUtil.toObj(redisObjectMapper, object, elementType);
        } catch (Exception e) {
            log.error("queue rpop error", e);
            return null;
        }
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
        try {
            Object object = redisTemplateWapper.getResolvedRouter(instanceId).opsForList().rightPop(key, timeout, unit);
            return JacksonUtil.toObj(redisObjectMapper, object, elementType);
        } catch (Exception e) {
            log.error("queue rpop error", e);
            return null;
        }
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
    public <E> List<E> queuePageForInstance(String instanceId, String key, final IPage iPage, TypeReference<List<E>> listTypeReference) {
        try {
            List<Object> objects = redisTemplateWapper.getResolvedRouter(instanceId).opsForList()
                    .range(key, iPage.getStartIndex(), iPage.getEndIndex());

            if (iPage.isSearchCount()) {
                iPage.setTotal(queueSizeForInstance(instanceId, key));
            }
            return JacksonUtil.toList(redisObjectMapper, objects, listTypeReference);
        } catch (Exception e) {
            log.error("queue rpop error", e);
            return null;
        }
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
        try {
            Object element = queueGetForInstance(instanceId, key, index);
            if (element != null) {
                redisTemplateWapper.getResolvedRouter(instanceId).opsForList().remove(key, 1, element);
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("queue remove index error", e);
            return false;
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForList().remove(key, 0, element);
        } catch (Exception e) {
            log.error("queue remove all error", e);
            return -1L;
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForList().index(key, index);
        } catch (Exception e) {
            log.error("queue get error", e);
            return null;
        }
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
        try {
            Object object = queueGetForInstance(instanceId, key, index);
            return JacksonUtil.toObj(redisObjectMapper, object, elementType);
        } catch (Exception e) {
            log.error("queue get error", e);
            return null;
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForList().indexOf(key, element);
        } catch (Exception e) {
            log.error("queue index of error", e);
            return -1L;
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForList().size(key);
        } catch (Exception e) {
            log.error("queue size error", e);
            return -1L;
        }
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
        try {
            redisTemplateWapper.getResolvedRouter(instanceId).opsForList().trim(key, start, end);
            return true;
        } catch (Exception e) {
            log.error("queue trim error", e);
            return false;
        }
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
        try {
            if (CollUtil.isEmpty(elements)) {
                return Boolean.TRUE;
            }
            redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().add(key, elements.toArray());
            updateExpiresForInstance(instanceId, key, expiry, unit);
            return true;
        } catch (Exception e) {
            log.error("set add error", e);
        }
        return false;
    }

    /**
     * 从指定实例和键的集合中移除元素集合。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param elements   要移除的元素
     * @return 是否移除成功
     */
    @Override
    public <E> Boolean setRemoveForInstance(String instanceId, String key, Collection<E> elements) {
        try {
            if (CollUtil.isEmpty(elements)) {
                return Boolean.TRUE;
            }
            redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().remove(key, elements.toArray());
            return true;
        } catch (Exception e) {
            log.error("set remove error", e);
            return false;
        }
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
        try {
            Boolean isMember = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().isMember(key, element);
            return isMember != null && isMember;
        } catch (Exception e) {
            log.error("set contains error", e);
            return false;
        }
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
        try {
            Long size = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().size(key);
            return size != null ? size.intValue() : 0;
        } catch (Exception e) {
            log.error("set size error", e);
            return 0;
        }
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
        try {
            Boolean aBoolean = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().move(key, value, destKey);
            return aBoolean != null ? aBoolean : Boolean.FALSE;
        } catch (Exception e) {
            log.error("set move error", e);
            return Boolean.FALSE;
        }
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
        try {
            Object object = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().pop(key);
            return JacksonUtil.toObj(redisObjectMapper, object, elementType);
        } catch (Exception e) {
            log.error("set pop error", e);
            return null;
        }
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
        try {
            Object object = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().randomMember(key);
            return JacksonUtil.toObj(redisObjectMapper, object, elementType);
        } catch (Exception e) {
            log.error("set random member error", e);
            return null;
        }
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
        try {
            if (count <= 0) {
                return List.of();
            }
            List<Object> object = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().randomMembers(key, count);
            return JacksonUtil.toList(redisObjectMapper, object, listTypeReference);
        } catch (Exception e) {
            log.error("set random members error", e);
            return null;
        }
    }

    /**
     * 获取指定实例和键的集合中的随机多个元素。
     *
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param count             返回的元素个数
     * @param listTypeReference 元素的类型
     * @return 集合中的随机元素
     */
    @Override
    public <E> Set<E> setDistinctRandomMembersForInstance(String instanceId, String key, long count, TypeReference<List<E>> listTypeReference) {
        try {
            if (count <= 0) {
                return Set.of();
            }
            Set<Object> objects
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().distinctRandomMembers(key, count);
            if (CollUtil.isEmpty(objects)) {
                return Set.of();
            }
            return new HashSet<>(JacksonUtil.toList(redisObjectMapper, objects, listTypeReference));
        } catch (Exception e) {
            log.error("set  distinct random members error", e);
            return Set.of();
        }
    }

    /**
     * 获取指定实例和键的集合中的所有元素。
     *
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param listTypeReference 元素的类型
     * @return 集合中的所有元素
     */
    @Override
    public <E> Set<E> setGetPageForInstance(String instanceId, String key, final IPage iPage, TypeReference<List<E>> listTypeReference) {
        try {
            Set<Object> members = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().members(key);
            List<Object> objectList = CollectionUtil.batchSubList(members, iPage);
            List<E> list = JacksonUtil.toList(redisObjectMapper, objectList, listTypeReference);
            if (CollUtil.isEmpty(list)) {
                return Set.of();
            }
            return new HashSet<>(list);
        } catch (Exception e) {
            log.error("set get error", e);
            return Set.of();
        }
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
        if (CollUtil.isEmpty(keys)) {
            return Set.of();
        }
        try {
            Set<Object> difference = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().difference(keys);
            List<E> list = JacksonUtil.toList(redisObjectMapper, difference, listTypeReference);
            if (CollUtil.isEmpty(list)) {
                return Set.of();
            }
            return new HashSet<>(list);
        } catch (Exception e) {
            log.error("set difference error", e);
            return Set.of();
        }
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
        if (CollUtil.isEmpty(keys)) {
            return -1L;
        }
        try {
            Long differenceAndStore = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().differenceAndStore(keys, destKey);
            updateExpiresForInstance(instanceId, destKey, expiry, unit);
            return differenceAndStore;
        } catch (Exception e) {
            log.error("set difference store error", e);
            return -2L;
        }
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
        if (CollUtil.isEmpty(keys)) {
            return Set.of();
        }
        try {
            Set<Object> intersect = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().intersect(keys);
            List<E> list = JacksonUtil.toList(redisObjectMapper, intersect, listTypeReference);
            if (CollUtil.isEmpty(list)) {
                return Set.of();
            }
            return new HashSet<>(list);
        } catch (Exception e) {
            log.error("set intersect error", e);
            return Set.of();
        }
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
        if (CollUtil.isEmpty(keys)) {
            return -1L;
        }
        try {
            Long intersectAndStore = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet()
                    .intersectAndStore(keys, destKey);
            updateExpiresForInstance(instanceId, destKey, expiry, unit);
            return intersectAndStore;
        } catch (Exception e) {
            log.error("set intersect store error", e);
            return -2L;
        }
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
    public <E> Set<E> setUnionForInstance(String instanceId, Collection<String> keys, TypeReference<List<E>> listTypeReference) {
        if (CollUtil.isEmpty(keys)) {
            return Set.of();
        }
        try {
            Set<Object> union = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().union(keys);
            List<E> list = JacksonUtil.toList(redisObjectMapper, union, listTypeReference);
            if (CollUtil.isEmpty(list)) {
                return Set.of();
            }
            return new HashSet<>(list);
        } catch (Exception e) {
            log.error("set union error", e);
            return Set.of();
        }
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
        if (CollUtil.isEmpty(keys)) {
            return -1L;
        }
        try {
            Long unionAndStore = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet()
                    .unionAndStore(keys, destKey);
            updateExpiresForInstance(instanceId, destKey, expiry, unit);
            return unionAndStore;
        } catch (Exception e) {
            log.error("set union store error", e);
            return -2L;
        }
    }

    /**
     * 获取符合指定格式的元素集合
     *
     * @param instanceId        实例ID
     * @param key               元素的键
     * @param options           元素格式
     * @param listTypeReference 元素的类型
     * @return 操作结果
     */
    @Override
    public <E> Set<E> setScanForInstance(String instanceId, String key, ScanOptions options, TypeReference<List<E>> listTypeReference) {
        try (Cursor<Object> scanCursor = redisTemplateWapper.getResolvedRouter(instanceId).opsForSet().scan(key, options)) {
            return new HashSet<>(JacksonUtil.toList(redisObjectMapper, scanCursor.stream().parallel()
                    .collect(Collectors.toSet()), listTypeReference));
        } catch (Exception e) {
            log.error("Error while set scan key", e);
            return Set.of();
        }
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
        try {
            Object object = getObjForInstance(instanceId, key);
            String str = JacksonUtil.toStr(redisObjectMapper, object);
            if (str != null) {
                return str.getBytes(StandardCharsets.UTF_8);
            }
        } catch (Exception e) {
            log.error("get bytes error", e);
        }
        return null;
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
        try {
            Object object = getObjForInstance(instanceId, key);
            return JacksonUtil.toStr(redisObjectMapper, object);
        } catch (Exception e) {
            log.error("get str error", e);
        }
        return null;
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
        try {
            Object object = getObjForInstance(instanceId, key);
            return JacksonUtil.toObj(redisObjectMapper, object, elementType);
        } catch (Exception e) {
            log.error("set get error", e);
        }
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
        try {
            Object object = getObjForInstance(instanceId, key);
            return JacksonUtil.toList(redisObjectMapper, object, listTypeReference);
        } catch (Exception e) {
            log.error("get list error", e);
        }
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
        try {
            List<Object> objectList = multiGetObjForInstance(instanceId, keys);
            if (CollUtil.isEmpty(objectList)) {
                return List.of();
            }
            return objectList.parallelStream()
                    .map(obj -> JacksonUtil.toStr(redisObjectMapper, obj))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("multi get str error", e);
        }
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
        try {
            List<Object> objectList = multiGetObjForInstance(instanceId, keys);
            if (CollUtil.isEmpty(objectList)) {
                return List.of();
            }
            return objectList.parallelStream()
                    .map(obj -> JacksonUtil.toObj(redisObjectMapper, obj, elementType))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("multi get obj error", e);
        }
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
        try {
            List<Object> objectList = multiGetObjForInstance(instanceId, keys);
            if (CollUtil.isEmpty(objectList)) {
                return List.of();
            }
            return objectList.parallelStream()
                    .map(obj -> JacksonUtil.toList(redisObjectMapper, obj, listTypeReference))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("multi get list error", e);
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).boundValueOps(key).get();
        } catch (Exception e) {
            log.error("set get error", e);
        }
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
        try {
            if (CollUtil.isEmpty(keys)) {
                return List.of();
            }
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForValue().multiGet(keys);
        } catch (Exception e) {
            log.error("multi get obj error", e);
        }
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
        try {
            if (override) {
                if (expiry > 0) {
                    redisTemplateWapper.getResolvedRouter(instanceId).opsForValue().set(key, value, expiry, unit);
                } else {
                    redisTemplateWapper.getResolvedRouter(instanceId).opsForValue().set(key, value);
                }
            } else {
                if (expiry > 0) {
                    redisTemplateWapper.getResolvedRouter(instanceId).opsForValue()
                            .setIfAbsent(key, value, expiry, unit);
                } else {
                    redisTemplateWapper.getResolvedRouter(instanceId).opsForValue()
                            .setIfAbsent(key, value);
                }
            }
            return true;
        } catch (Exception e) {
            log.error("put error", e);
            return false;
        }
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
        try {
            redisTemplateWapper.getResolvedRouter(instanceId).opsForValue().append(key, value);
            return true;
        } catch (Exception e) {
            log.error("append error", e);
            return false;
        }
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
        try {
            if (override) {
                redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().add(key, value, score);
            } else {
                redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().addIfAbsent(key, value, score);
            }
            updateExpiresForInstance(instanceId, key, expiry, unit);
            return true;
        } catch (Exception e) {
            log.error("zset add error", e);
            return false;
        }
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
        if (MapUtil.isEmpty(values)) {
            return true;
        }
        try {
            Set<ZSetOperations.TypedTuple<Object>> typedTuples = values.entrySet()
                    .parallelStream()
                    .map(entry -> ZSetOperations.TypedTuple.of((Object) entry.getKey(), entry.getValue() == null ? 0 :
                            entry.getValue()))
                    .collect(Collectors.toSet());
            if (override) {
                redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().add(key, typedTuples);
            } else {
                redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().addIfAbsent(key, typedTuples);
            }
            updateExpiresForInstance(instanceId, key, expiry, unit);
            return true;
        } catch (Exception e) {
            log.error("zset add more error", e);
            return false;
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().incrementScore(key, value, delta);
        } catch (Exception e) {
            log.error("zset increment score errod", e);
            return 0.0;
        }
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
        try {
            Long size = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().zCard(key);
            return size != null ? size : 0L;
        } catch (Exception e) {
            log.error("zset card error", e);
            return -1L;
        }
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
        try {
            Long count = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().count(key, min, max);
            return count != null ? count : 0L;
        } catch (Exception e) {
            log.error("zset count error", e);
            return -1L;
        }
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
        try {
            Double score = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().score(key, value);
            return score != null ? score : 0;
        } catch (Exception e) {
            log.error("zset score error", e);
            return 0;
        }
    }

    /**
     * 移除指定实例和键的有序集合中的一个或多个元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param value      要移除的元素
     * @return 移除的元素数量
     */
    @Override
    public <E> Long zsetRemoveForInstance(String instanceId, String key, List<E> value) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().remove(key, value);
        } catch (Exception e) {
            log.error("zset remove error", e);
            return -1L;
        }
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
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().removeRange(key, start, end);
        } catch (Exception e) {
            log.error("zset remove range error", e);
            return -1L;
        }
    }

    /**
     * 获取符合指定格式的元素集合
     *
     * @param instanceId  实例ID
     * @param key         元素的键
     * @param options     元素格式
     * @param elementType 元素的类型
     * @return 操作结果
     */
    @Override
    public <E> Map<E, Double> zsetScanForInstance(String instanceId, String key, ScanOptions options, Class<E> elementType) {
        try (Cursor<ZSetOperations.TypedTuple<Object>> tupleCursor = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().scan(key, options)) {
            return tupleCursor.stream().parallel().collect(Collectors.toMap(tuple -> JacksonUtil.toObj(tuple.getValue(), elementType), ZSetOperations.TypedTuple::getScore, (v1, v2) -> v1));
        } catch (Exception e) {
            log.error("zset scan error", e);
            return Map.of();
        }
    }

    /**
     * 移除指定实例和键的有序集合中，值在指定范围内的所有元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param range      范围
     * @return 移除的元素数量(- 1表示移除异常)
     */
    @Override
    public Long zsetRemoveRangeByLexForInstance(String instanceId, String key, Range<String> range) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().removeRangeByLex(key, range);
        } catch (Exception e) {
            log.error("zset remove range by lex error", e);
            return -1L;
        }
    }

    /**
     * 移除指定实例和键的有序集合中，分值在指定范围内的所有元素。
     *
     * @param instanceId 实例ID
     * @param key        有序集合的键
     * @param min        最小分值
     * @param max        最大分值
     * @return 移除的元素数量
     */
    @Override
    public Long zsetRemoveRangeByScoreForInstance(String instanceId, String key, double min, double max) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().removeRangeByScore(key, min, max);
        } catch (Exception e) {
            log.error("zset remove range by score error", e);
            return -1L;
        }
    }

    /**
     * 计算一个集合和多个集合之间的差异，返回一个包含在第一个集合中但不在其他集合中的元素的集合。
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param otherKeys         其它集合的键的集合
     * @param listTypeReference 元素的类型
     * @return 集合中的所有元素
     */
    @Override
    public <E> Set<E> zsetDifferenceForInstance(String instanceId, String key, Collection<String> otherKeys, TypeReference<List<E>> listTypeReference) {
        try {
            Set<Object> objects = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().difference(key, otherKeys);
            if (ObjUtil.isEmpty(objects)) {
                return Set.of();
            }
            return new LinkedHashSet<>(JacksonUtil.toList(redisObjectMapper, objects, listTypeReference));
        } catch (Exception e) {
            log.error("zset difference error", e);
            return Set.of();
        }
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
        try {
            Long differenceAndStore = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().differenceAndStore(key, otherKeys, destKey);
            updateExpiresForInstance(instanceId, destKey, expiry, unit);
            return differenceAndStore != null ? differenceAndStore : -1L;
        } catch (Exception e) {
            log.error("zset difference and store error", e);
            return -2L;
        }
    }

    /**
     * 计算给定的多个集合的差异，并将结果返回。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其他的键的集合
     * @param elementType 元素的类型
     * @return 多个集合的差异
     */
    @Override
    public <E> Map<E, Double> zsetDifferenceWithScoresForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> elementType) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().differenceWithScores(key, otherKeys);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset difference with store error", e);
            return Map.of();
        }
    }

    /**
     * 计算给定的集合和多个其他集合的交集。
     *
     * @param <E>               元素类型
     * @param instanceId        实例ID
     * @param key               集合的键
     * @param otherKeys         其它集合的键的集合
     * @param listTypeReference 元素的类型
     * @return 集合中的所有元素
     */
    @Override
    public <E> Set<E> zsetIntersectForInstance(String instanceId, String key, Collection<String> otherKeys, TypeReference<List<E>> listTypeReference) {
        try {
            Set<Object> intersect = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().intersect(key, otherKeys);
            if (CollUtil.isEmpty(intersect)) {
                return Set.of();
            }
            return new LinkedHashSet<>(JacksonUtil.toList(redisObjectMapper, intersect, listTypeReference));
        } catch (Exception e) {
            log.error("zset intersect error", e);
            return Set.of();
        }
    }

    /**
     * 计算给定的集合和多个其他集合的交集。
     *
     * @param <E>         元素类型
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param elementType 元素的类型
     * @return 集合中的所有元素
     */
    @Override
    public <E> Map<E, Double> zsetIntersectWithScoresForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> elementType) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().intersectWithScores(key, otherKeys);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset intersect with store error", e);
            return Map.of();
        }
    }

    /**
     * 计算给定的集合和多个其他集合的交集。
     *
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param elementType 元素的类型
     * @param aggregate   :  分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights     :    每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 集合中的所有元素
     */
    @Override
    public <E> Map<E, Double> zsetIntersectWithStoreForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> elementType, Aggregate aggregate, Weights weights) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().intersectWithScores(key, otherKeys, aggregate, weights);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset intersect with store error", e);
            return Map.of();
        }
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
        try {
            Long intersectAndStore = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().intersectAndStore(key, otherKeys, destKey);
            updateExpiresForInstance(instanceId, destKey, expiry, unit);
            return intersectAndStore != null ? intersectAndStore : -1L;
        } catch (Exception e) {
            log.error("zset intersect add store error", e);
            return -2L;
        }
    }

    /**
     * 计算给定的集合和多个其他集合的交集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param aggregate  : 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights    :   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    @Override
    public Long zsetIntersectAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights, long expiry, TimeUnit unit) {
        try {
            Long intersectAndStore =
                    redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().intersectAndStore(key, otherKeys, destKey, aggregate, weights);
            updateExpiresForInstance(instanceId, destKey, expiry, unit);
            return intersectAndStore != null ? intersectAndStore : -1L;
        } catch (Exception e) {
            log.error("zset intersect add store error", e);
            return -2L;
        }
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
        try {
            Set<Object> intersect
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().union(key, otherKeys);
            if (CollUtil.isEmpty(intersect)) {
                return Set.of();
            }
            return new LinkedHashSet<>(JacksonUtil.toList(redisObjectMapper, intersect, listTypeReference));
        } catch (Exception e) {
            log.error("zset union error", e);
            return Set.of();
        }
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
        try {
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().unionWithScores(key, otherKeys);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset union with store error", e);
            return Map.of();
        }
    }

    /**
     * 计算给定的集合和多个其他集合的并集。
     *
     * @param instanceId  实例ID
     * @param key         集合的键
     * @param otherKeys   其它集合的键的集合
     * @param elementType 元素的类型
     * @param aggregate   :  分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights     :    每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @return 集合中的所有元素
     */
    @Override
    public <E> Map<E, Double> zsetUnionWithStoreForInstance(String instanceId, String key, Collection<String> otherKeys, Class<E> elementType, Aggregate aggregate, Weights weights) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().unionWithScores(key, otherKeys, aggregate, weights);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset union with store error", e);
            return Map.of();
        }
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
        try {
            Long intersectAndStore = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().unionAndStore(key, otherKeys, destKey);
            updateExpiresForInstance(instanceId, destKey, expiry, unit);
            return intersectAndStore != null ? intersectAndStore : -1L;
        } catch (Exception e) {
            log.error("zset union add store error", e);
            return -2L;
        }
    }

    /**
     * 计算给定的集合和多个其他集合的并集，并将结果保存到指定的集合中。
     *
     * @param instanceId 实例ID
     * @param key        集合的键
     * @param otherKeys  其它集合的键的集合
     * @param destKey    新的集合的键
     * @param aggregate  : 分数的聚合方式，可以是 Aggregate.SUM 或者 Aggregate.MIN 或者 Aggregate.MAX。
     * @param weights    :   每个集合的权重，如果集合数量大于1，则必须指定 weights
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 存储的元素数量
     */
    @Override
    public Long zsetUnionAndStoreForInstance(String instanceId, String key, Collection<String> otherKeys, String destKey, Aggregate aggregate, Weights weights, long expiry, TimeUnit unit) {
        try {
            Long intersectAndStore =
                    redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().unionAndStore(key, otherKeys, destKey, aggregate, weights);
            updateExpiresForInstance(instanceId, destKey, expiry, unit);

            return intersectAndStore != null ? intersectAndStore : -1L;
        } catch (Exception e) {
            log.error("zset union add store error", e);
            return -2L;
        }
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
        try {
            Object object = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().randomMember(key);
            return JacksonUtil.toObj(object, elementType);
        } catch (Exception e) {
            log.error("zset random member error", e);
            return null;
        }
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
        try {
            if (count <= 0) {
                return Set.of();
            }
            Set<Object> objects
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().distinctRandomMembers(key, count);
            if (CollUtil.isEmpty(objects)) {
                return Set.of();
            }
            return new LinkedHashSet<>(JacksonUtil.toList(redisObjectMapper, objects, listTypeReference));
        } catch (Exception e) {
            log.error("zset  distinct random members error", e);
            return Set.of();
        }
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
        try {
            if (count <= 0) {
                return Map.of();
            }
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().distinctRandomMembersWithScore(key, count);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset  distinct random members with score error", e);
            return Map.of();
        }
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
        try {
            ZSetOperations.TypedTuple<Object> typedTuple
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().popMax(key);
            if (ObjUtil.isEmpty(typedTuple)) {
                return null;
            }
            return Pair.of(JacksonUtil.toObj(redisObjectMapper, typedTuple.getValue(), elementType), typedTuple.getScore());
        } catch (Exception e) {
            log.error("zset  pop max error", e);
            return null;
        }
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
        try {
            ZSetOperations.TypedTuple<Object> typedTuple
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().popMax(key, expiry, unit);
            if (ObjUtil.isEmpty(typedTuple)) {
                return null;
            }
            return Pair.of(JacksonUtil.toObj(redisObjectMapper, typedTuple.getValue(), elementType), typedTuple.getScore());
        } catch (Exception e) {
            log.error("zset  pop max error", e);
            return null;
        }
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
    public <E> Map<E, Double> zsetPopMaxForInstance(String instanceId, String key, long count, Class<
            E> elementType) {
        try {
            if (count <= 0) {
                return Map.of();
            }
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().popMax(key, count);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset  pop max count error", e);
            return Map.of();
        }
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
        try {
            ZSetOperations.TypedTuple<Object> typedTuple
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().popMin(key);
            if (ObjUtil.isEmpty(typedTuple)) {
                return null;
            }
            return Pair.of(JacksonUtil.toObj(redisObjectMapper, typedTuple.getValue(), elementType), typedTuple.getScore());
        } catch (Exception e) {
            log.error("zset  pop min error", e);
            return null;
        }
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
        try {
            ZSetOperations.TypedTuple<Object> typedTuple
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().popMin(key, expiry, unit);
            if (ObjUtil.isEmpty(typedTuple)) {
                return null;
            }
            return Pair.of(JacksonUtil.toObj(redisObjectMapper, typedTuple.getValue(), elementType), typedTuple.getScore());
        } catch (Exception e) {
            log.error("zset  pop min error", e);
            return null;
        }
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
    public <E> Map<E, Double> zsetPopMinForInstance(String instanceId, String key, long count, Class<
            E> elementType) {
        try {
            if (count <= 0) {
                return Map.of();
            }
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().popMin(key, count);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset  pop min count error", e);
            return Map.of();
        }
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

        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().rank(key, value);
        } catch (Exception e) {
            log.error("zset rank error", e);
            return -1L;
        }
    }

    /**
     * 获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param min               最小值
     * @param max               最大值
     * @param listTypeReference 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    @Override
    public <E> Set<E> zsetRangeForInstance(String instanceId, String key, long min, long max, TypeReference<List<E>> listTypeReference) {
        try {
            Set<Object> objects = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().range(key, min, max);
            if (CollUtil.isEmpty(objects)) {
                return Set.of();
            }
            return new LinkedHashSet<>(JacksonUtil.toList(redisObjectMapper, objects, listTypeReference));
        } catch (Exception e) {
            log.error("zset range error", e);
            return Set.of();
        }
    }

    /**
     * 根据字典序获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param range             范围
     * @param limit             限制
     * @param listTypeReference 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    @Override
    public <E> Set<E> zsetRangeByLexForInstance(String instanceId, String key, Range<String> range, Limit limit, TypeReference<List<E>> listTypeReference) {
        try {
            Set<Object> objects = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().rangeByLex(key, range, limit);
            if (CollUtil.isEmpty(objects)) {
                return Set.of();
            }
            return new LinkedHashSet<>(JacksonUtil.toList(redisObjectMapper, objects, listTypeReference));
        } catch (Exception e) {
            log.error("zset range by lex error", e);
            return Set.of();
        }
    }

    /**
     * 根据分数获取指定实例中指定范围的元素。
     *
     * @param instanceId        实例ID
     * @param key               有序集合的键
     * @param min               最小值
     * @param max               最大值
     * @param listTypeReference 类型引用
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    @Override
    public <E> Set<E> zsetRangeByScoreForInstance(String instanceId, String key, double min, double max, TypeReference<List<E>> listTypeReference) {
        try {
            Set<Object> objects = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().rangeByScore(key, min, max);
            if (CollUtil.isEmpty(objects)) {
                return Set.of();
            }
            return new LinkedHashSet<>(JacksonUtil.toList(redisObjectMapper, objects, listTypeReference));
        } catch (Exception e) {
            log.error("zset range by score error", e);
            return Set.of();
        }
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
     * @param <E>               元素类型
     * @return 指定范围的元素集合
     */
    @Override
    public <E> Set<E> zsetRangeByScoreForInstance(String instanceId, String key, double min, double max, long offset, long count, TypeReference<List<E>> listTypeReference) {
        try {
            Set<Object> objects = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().rangeByScore(key, min, max, offset, count);
            if (CollUtil.isEmpty(objects)) {
                return Set.of();
            }
            return new LinkedHashSet<>(JacksonUtil.toList(redisObjectMapper, objects, listTypeReference));
        } catch (Exception e) {
            log.error("zset range by score error", e);
            return Set.of();
        }
    }

    /**
     * 根据分数获取指定实例中指定范围的元素及其分数。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param min         最小值
     * @param max         最大值
     * @param elementType 元素类型
     * @param <E>         元素类型
     * @return 指定范围的元素及其分数的映射
     */
    @Override
    public <E> Map<E, Double> zsetRangeByScoreWithScoresForInstance(String instanceId, String key, double min, double max, Class<E> elementType) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().rangeByScoreWithScores(key, min, max);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset range by score with scores error", e);
            return Map.of();
        }

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
     * @param <E>         元素类型
     * @return 指定范围的元素及其分数的映射
     */
    @Override
    public <E> Map<E, Double> zsetRangeByScoreWithScoresForInstance(String instanceId, String key, double min, double max, long offset, long count, Class<E> elementType) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset range by score with scores error", e);
            return Map.of();
        }
    }

    /**
     * 获取指定实例中指定范围的元素及其分数。
     *
     * @param instanceId  实例ID
     * @param key         有序集合的键
     * @param start       起始位置
     * @param end         结束位置
     * @param elementType 元素类型
     * @param <E>         元素类型
     * @return 指定范围的元素及其分数的映射
     */
    @Override
    public <E> Map<E, Double> zsetRangeWithScoresForInstance(String instanceId, String key, long start, long end, Class<E> elementType) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().rangeWithScores(key, start, end);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset range with scores error", e);
            return Map.of();
        }
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
    @Override
    public Long zsetRangeAndStoreByLexForInstance(String instanceId, String key, Range<String> range, Limit limit, String destKey, long expiry, TimeUnit unit) {
        try {
            Long rangeAndStoreByLex = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().rangeAndStoreByLex(key, destKey, range, limit);
            updateExpiresForInstance(instanceId, destKey, expiry, unit);
            return rangeAndStoreByLex != null ? rangeAndStoreByLex : -1L;
        } catch (Exception e) {
            log.error("zset range and store by lex error", e);
            return -2L;
        }
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
    @Override
    public Long zsetRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, Limit limit, String destKey, long expiry, TimeUnit unit) {
        try {
            Long rangeAndStoreByLex = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().rangeAndStoreByScore(key, destKey, range, limit);
            updateExpiresForInstance(instanceId, destKey, expiry, unit);
            return rangeAndStoreByLex != null ? rangeAndStoreByLex : -1L;
        } catch (Exception e) {
            log.error("zset range and store by score error", e);
            return -2L;
        }
    }

    /**
     * 根据指定的字典范围和限制条件从指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param range             字典范围（包含下限和上限）
     * @param limit             限制条件（例如最大数量）
     * @param listTypeReference 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    @Override
    public <E> Set<E> zsetReverseRangeByLexForInstance(String instanceId, String key, Range<String> range, Limit limit, TypeReference<List<E>> listTypeReference) {
        try {
            Set<Object> objects = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().reverseRangeByLex(key, range, limit);
            if (CollUtil.isEmpty(objects)) {
                return Set.of();
            }
            return new LinkedHashSet<>(JacksonUtil.toList(redisObjectMapper, objects, listTypeReference));
        } catch (Exception e) {
            log.error("zset reverse range by lex error", e);
            return Set.of();
        }
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param min               最小分数
     * @param max               最大分数
     * @param listTypeReference 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    @Override
    public <E> Set<E> zsetReverseRangeForInstance(String instanceId, String key, long min, long max, TypeReference<List<E>> listTypeReference) {
        try {
            Set<Object> objects
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().reverseRange(key, min, max);
            if (CollUtil.isEmpty(objects)) {
                return Set.of();
            }
            return new LinkedHashSet<>(JacksonUtil.toList(redisObjectMapper, objects, listTypeReference));
        } catch (Exception e) {
            log.error("zset reverse range error", e);
            return Set.of();
        }
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素，并将其作为集合返回。
     *
     * @param instanceId        实例 ID
     * @param key               有序集合的键
     * @param min               最小分数
     * @param max               最大分数
     * @param listTypeReference 类型引用，用于指定元素的类型
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    @Override
    public <E> Set<E> zsetReverseRangeByScoreForInstance(String instanceId, String key, double min, double max, TypeReference<List<E>> listTypeReference) {
        try {
            Set<Object> objects = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().reverseRangeByScore(key, min, max);
            if (CollUtil.isEmpty(objects)) {
                return Set.of();
            }
            return new LinkedHashSet<>(JacksonUtil.toList(redisObjectMapper, objects, listTypeReference));
        } catch (Exception e) {
            log.error("zset reverse range by score error", e);
            return Set.of();
        }
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
     * @param <E>               元素类型
     * @return 反向获取的元素集合
     */
    @Override
    public <E> Set<E> zsetReverseRangeByScoreForInstance(String instanceId, String key, double min, double max, long offset, long count, TypeReference<List<E>> listTypeReference) {
        try {
            Set<Object> objects = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().reverseRangeByScore(key, min, max, offset, count);
            if (CollUtil.isEmpty(objects)) {
                return Set.of();
            }
            return new LinkedHashSet<>(JacksonUtil.toList(redisObjectMapper, objects, listTypeReference));
        } catch (Exception e) {
            log.error("zset reverse range by score error", e);
            return Set.of();
        }
    }

    /**
     * 根据指定的分数范围从指定的实例中反向获取元素及其分数，并将其作为映射返回。
     *
     * @param instanceId  实例 ID
     * @param key         有序集合的键
     * @param min         最小分数
     * @param max         最大分数
     * @param elementType 元素类型
     * @param <E>         元素类型
     * @return 反向获取的元素及其分数的映射
     */
    @Override
    public <E> Map<E, Double> zsetReverseRangeByScoreWithScoresForInstance(String instanceId, String key, double min, double max, Class<E> elementType) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().reverseRangeByScoreWithScores(key, min, max);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset reverse range by score with scores error", e);
            return Map.of();
        }
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
     * @param <E>         元素类型
     * @return 反向获取的元素及其分数的映射
     */
    @Override
    public <E> Map<E, Double> zsetReverseRangeByScoreWithScoresForInstance(String instanceId, String key, double min, double max, long offset, long count, Class<E> elementType) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset reverse range by score with scores error", e);
            return Map.of();
        }
    }

    /**
     * 根据指定的范围从指定的实例中反向获取元素及其分数，并将其作为映射返回。
     *
     * @param instanceId  实例 ID
     * @param key         有序集合的键
     * @param start       开始索引
     * @param end         结束索引
     * @param elementType 元素类型
     * @param <E>         元素类型
     * @return 反向获取的元素及其分数的映射
     */
    @Override
    public <E> Map<E, Double> zsetReverseRangeWithScoresForInstance(String instanceId, String key, long start, long end, Class<E> elementType) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> typedTuples
                    = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().reverseRangeWithScores(key, start, end);
            return zsetWithScores(typedTuples, elementType);
        } catch (Exception e) {
            log.error("zset reverse range with scores error", e);
            return Map.of();
        }
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
    @Override
    public Long zsetReverseRangeAndStoreByLexForInstance(String instanceId, String
            key, Range<String> range, Limit limit, String destKey, long expiry, TimeUnit unit) {
        try {
            Long rangeAndStoreByLex = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().reverseRangeAndStoreByLex(key, destKey, range, limit);
            updateExpiresForInstance(instanceId, destKey, expiry, unit);
            return rangeAndStoreByLex != null ? rangeAndStoreByLex : -1L;
        } catch (Exception e) {
            log.error("zset reverse range and store by lex error", e);
            return -2L;
        }
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
    @Override
    public Long zsetReverseRangeAndStoreByScoreForInstance(String instanceId, String key, Range<Number> range, Limit limit, String destKey, long expiry, TimeUnit unit) {
        try {
            Long rangeAndStoreByLex = redisTemplateWapper.getResolvedRouter(instanceId).opsForZSet().reverseRangeAndStoreByScore(key, destKey, range, limit);
            updateExpiresForInstance(instanceId, destKey, expiry, unit);
            return rangeAndStoreByLex != null ? rangeAndStoreByLex : -1L;
        } catch (Exception e) {
            log.error("zset reverse range and store by score error", e);
            return -2L;
        }
    }

    /**
     * 向指定实例的地理空间数据集中添加地理位置。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param point      地理位置的坐标
     * @param member     成员
     * @return 添加的地理位置数量
     */
    @Override
    public Long geoAddForInstance(String instanceId, String key, Point point, Object member) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo().add(key, point, member);
        } catch (Exception e) {
            log.error("geo add error", e);
            return 0L;
        }
    }

    /**
     * 向指定实例的地理空间数据集中添加地理位置。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param location   地理位置
     * @return 添加的地理位置数量
     */
    @Override
    public Long geoAddForInstance(String instanceId, String key, RedisGeoCommands.GeoLocation<Object> location) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo().add(key, location);
        } catch (Exception e) {
            log.error("geo add error", e);
            return 0L;
        }
    }

    /**
     * 向指定实例的地理空间数据集中添加多个地理位置。
     *
     * @param instanceId          实例ID
     * @param key                 键
     * @param memberCoordinateMap 成员与坐标的映射
     * @return 添加的地理位置数量
     */
    @Override
    public Long geoAddForInstance(String instanceId, String key, Map<Object, Point> memberCoordinateMap) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo().add(key, memberCoordinateMap);
        } catch (Exception e) {
            log.error("geo add error", e);
            return 0L;
        }
    }

    /**
     * 向指定实例的地理空间数据集中添加多个地理位置。
     *
     * @param instanceId   实例ID
     * @param key          键
     * @param geoLocations 地理位置列表
     * @return 添加的地理位置数量
     */
    @Override
    public Long geoAddForInstance(String instanceId, String key, Iterable<RedisGeoCommands.GeoLocation<Object>> geoLocations) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo().add(key, geoLocations);
        } catch (Exception e) {
            log.error("geo add error", e);
            return 0L;
        }
    }

    /**
     * 计算两个成员之间的距离。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param member1    成员1
     * @param member2    成员2
     * @return 距离
     */
    @Override
    public Distance geoDistForInstance(String instanceId, String key, Object member1, Object member2) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo().distance(key, member1, member2);
        } catch (Exception e) {
            log.error("geo dist error", e);
            return null;
        }
    }

    /**
     * 计算两个成员之间的距离，并指定距离单位。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param member1    成员1
     * @param member2    成员2
     * @param metric     距离单位
     * @return 距离
     */
    @Override
    public Distance geoDistForInstance(String instanceId, String key, Object member1, Object member2, Metric metric) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo()
                    .distance(key, member1, member2, metric);
        } catch (Exception e) {
            log.error("geo dist error", e);
            return null;
        }
    }

    /**
     * 获取指定实例的成员的Geohash字符串。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param members    成员列表
     * @return Geohash字符串列表
     */
    @Override
    public List<String> geoHashForInstance(String instanceId, String key, Object... members) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo().hash(key, members);
        } catch (Exception e) {
            log.error("geo hash error", e);
            return List.of();
        }
    }

    /**
     * 获取指定实例的成员的坐标。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param members    成员列表
     * @return 坐标列表
     */
    @Override
    public List<Point> geoPosForInstance(String instanceId, String key, Object... members) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo().position(key, members);
        } catch (Exception e) {
            log.error("geo pos error", e);
            return List.of();
        }
    }

    /**
     * 获取指定实例的圆范围内的成员及其位置。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param within     圆范围
     * @return 地理位置结果
     */
    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusForInstance(String instanceId, String key, Circle within) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo().radius(key, within);
        } catch (Exception e) {
            log.error("geo radius error", e);
            return null;
        }
    }

    /**
     * 获取指定实例的圆范围内的成员及其位置，并指定查询参数。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param within     圆范围
     * @param args       查询参数
     * @return 地理位置结果
     */
    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusForInstance(String instanceId, String key, Circle within, RedisGeoCommands.GeoRadiusCommandArgs args) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo().radius(key, within, args);
        } catch (Exception e) {
            log.error("geo radius error", e);
            return null;
        }
    }

    /**
     * 获取指定实例的成员为中心的圆范围内的成员及其位置。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param member     成员
     * @param radius     半径
     * @return 地理位置结果
     */
    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusByMemberForInstance(String instanceId, String key, Object member, double radius) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo()
                    .radius(key, member, new Distance(radius));
        } catch (Exception e) {
            log.error("geo radius error", e);
            return null;
        }
    }

    /**
     * 获取指定实例的成员为中心的圆范围内的成员及其位置。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param member     成员
     * @param distance   距离
     * @return 地理位置结果
     */
    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusByMemberForInstance(String instanceId, String key, Object member, Distance distance) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo().radius(key, member, distance);
        } catch (Exception e) {
            log.error("geo radius error", e);
            return null;
        }
    }

    /**
     * 获取指定实例的成员为中心的圆范围内的成员及其位置，并指定查询参数。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param member     成员
     * @param distance   距离
     * @param args       查询参数
     * @return 地理位置结果
     */
    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusByMemberForInstance(String instanceId, String key, Object member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo().radius(key, member, distance, args);
        } catch (Exception e) {
            log.error("geo radius error", e);
            return null;
        }
    }

    /**
     * 移除指定实例的成员的地理位置。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param members    成员列表
     * @return 移除的地理位置数量
     */
    @Override
    public Long geoRemoveForInstance(String instanceId, String key, Object... members) {
        try {
            return redisTemplateWapper.getResolvedRouter(instanceId).opsForGeo().remove(key, members);
        } catch (Exception e) {
            log.error("geo remove error", e);
            return 0L;
        }
    }

    /*=====================*/

    /**
     * 将TypedTuple转换为Map
     *
     * @param typedTuples zset数据
     * @param elementType 类型
     * @param <E>         泛型
     * @return map
     */
    private <E> Map<E, Double> zsetWithScores(Set<ZSetOperations.TypedTuple<Object>> typedTuples, Class<E> elementType) {
        if (CollUtil.isEmpty(typedTuples)) {
            return Map.of();
        }

        Map<E, Double> map = new LinkedHashMap<>();
        for (ZSetOperations.TypedTuple<Object> typedTuple : typedTuples) {
            map.put(JacksonUtil.toObj(redisObjectMapper, typedTuple.getValue(), elementType), typedTuple.getScore());
        }
        return map;
    }
}
