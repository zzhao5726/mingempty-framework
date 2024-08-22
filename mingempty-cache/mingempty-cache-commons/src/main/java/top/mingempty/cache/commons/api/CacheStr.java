package top.mingempty.cache.commons.api;

import top.mingempty.domain.other.GlobalConstant;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存接口，用于对缓存中的字符串进行操作。
 * </p>
 * 该接口继承了缓存{@link CacheCounter}接口
 *
 * @author zzhao
 */
public interface CacheStr extends CacheCounter {

    /**
     * 获取当前缓存实例的字符串缓存API。
     * <p>
     * 返回当前实例的 {@link CacheStr} 类型的API。
     * </p>
     *
     * @return 当前缓存实例的字符串缓存集合
     */
    default CacheStr getCacheStr() {
        return this;
    }

    /**
     * 将字节数组值存储到指定的键中。
     * <p>
     * 使用默认的实例名，并且不设置过期时间。
     * </p>
     *
     * @param key   存储的键
     * @param value 要存储的字节数组值
     * @return 操作是否成功
     */
    default Boolean putBytes(String key, byte[] value) {
        return putBytes(key, value, 0L);
    }

    /**
     * 将字节数组值存储到指定实例和键中。
     * <p>
     * 使用默认的过期时间（-1L）。
     * </p>
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param value      要存储的字节数组值
     * @return 操作是否成功
     */
    default Boolean putBytesForInstance(String instanceId, String key, byte[] value) {
        return putBytesForInstance(instanceId, key, value, 0L);
    }

    /**
     * 将字节数组值存储到指定的键中，并设置过期时间。
     *
     * @param key    存储的键
     * @param value  要存储的字节数组值
     * @param expiry 过期时间（秒）
     * @return 操作是否成功
     */
    default Boolean putBytes(String key, byte[] value, long expiry) {
        return putBytesForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value, expiry);
    }

    /**
     * 将字节数组值存储到指定实例和键中，并设置过期时间。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param value      要存储的字节数组值
     * @param expiry     过期时间（秒）
     * @return 操作是否成功
     */
    default Boolean putBytesForInstance(String instanceId, String key, byte[] value, long expiry) {
        return putBytesForInstance(instanceId, key, value, expiry, TimeUnit.SECONDS);
    }

    /**
     * 将字节数组值存储到指定的键中，并设置过期时间。
     *
     * @param key    存储的键
     * @param value  要存储的字节数组值
     * @param expiry 过期时间
     * @param unit   过期时间单位
     * @return 操作是否成功
     */
    default Boolean putBytes(String key, byte[] value, long expiry, TimeUnit unit) {
        return putBytesForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value, expiry, unit);
    }

    /**
     * 将字节数组值存储到指定实例和键中，并设置过期时间。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param value      要存储的字节数组值
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 操作是否成功
     */
    default Boolean putBytesForInstance(String instanceId, String key, byte[] value, long expiry, TimeUnit unit) {
        return putBytesForInstance(instanceId, key, value, expiry, unit, true);
    }

    /**
     * 将字节数组值存储到指定的键中，并设置过期时间。
     *
     * @param key      存储的键
     * @param value    要存储的字节数组值
     * @param expiry   过期时间
     * @param unit     过期时间单位
     * @param override 是否覆盖已有值
     * @return 操作是否成功
     */
    default Boolean putBytes(String key, byte[] value, long expiry, TimeUnit unit, boolean override) {
        return putBytesForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value, expiry, unit, override);
    }

    /**
     * 将字节数组值存储到指定实例和键中，并设置过期时间。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param value      要存储的字节数组值
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @param override   是否覆盖已有值
     * @return 操作是否成功
     */
    default Boolean putBytesForInstance(String instanceId, String key, byte[] value, long expiry, TimeUnit unit, boolean override) {
        return putForInstance(instanceId, key, value, expiry, unit, override);
    }

    /**
     * 从缓存中获取指定键的字节数组值。
     * <p>
     * 使用默认的实例名。
     * </p>
     *
     * @param key 存储的键
     * @return 获取的字节数组值
     */
    default byte[] getBytes(String key) {
        return getBytesForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 从指定实例中获取指定键的字节数组值。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @return 获取的字节数组值
     */
    byte[] getBytesForInstance(String instanceId, String key);

    /**
     * 从缓存中获取指定键的字符串值。
     * <p>
     * 使用默认的实例名。
     * </p>
     *
     * @param key 存储的键
     * @return 获取的字符串值
     */
    default String getStr(String key) {
        return getStrForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 从指定实例中获取指定键的字符串值。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @return 获取的字符串值
     */
    String getStrForInstance(String instanceId, String key);

    /**
     * 从缓存中获取指定键的对象值。
     * <p>
     * 使用默认的实例名。
     * </p>
     *
     * @param <V>    对象的类型
     * @param key    存储的键
     * @param vClass 对象的类型类
     * @return 获取的对象值
     */
    default <V> V getObj(String key, Class<V> vClass) {
        return getObjForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, vClass);
    }

    /**
     * 从指定实例中获取指定键的对象值。
     *
     * @param <V>        对象的类型
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param vClass     对象的类型类
     * @return 获取的对象值
     */
    <V> V getObjForInstance(String instanceId, String key, Class<V> vClass);

    /**
     * 从缓存中获取指定键的对象列表值。
     * <p>
     * 使用默认的实例名。
     * </p>
     *
     * @param <E>    列表中元素的类型
     * @param key    存储的键
     * @param eClass 列表中元素的类型类
     * @return 获取的对象列表值
     */
    default <E> List<E> getList(String key, Class<E> eClass) {
        return getListForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, eClass);
    }

    /**
     * 从指定实例中获取指定键的对象列表值。
     *
     * @param <E>        列表中元素的类型
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param eClass     列表中元素的类型类
     * @return 获取的对象列表值
     */
    <E> List<E> getListForInstance(String instanceId, String key, Class<E> eClass);

    /**
     * 根据多个键获取字符串值。
     * <p>
     * 使用默认的实例名。
     * </p>
     *
     * @param keys 键的集合
     * @return 字符串值的列表
     */
    default List<String> multiGetStr(Collection<String> keys) {
        return multiGetStrForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys);
    }

    /**
     * 根据多个键获取字符串值。
     *
     * @param instanceId 实例ID
     * @param keys       键的集合
     * @return 字符串值的列表
     */
    List<String> multiGetStrForInstance(String instanceId, Collection<String> keys);

    /**
     * 根据多个键获取对象值。
     * <p>
     * 使用默认的实例名。
     * </p>
     *
     * @param <E>    元素的类型
     * @param keys   键的集合
     * @param eClass 元素的类型类对象
     * @return 对象值的列表
     */
    default <E> List<E> multiGetObj(Collection<String> keys, Class<E> eClass) {
        return multiGetObjForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, eClass);
    }

    /**
     * 根据多个键获取对象值。
     *
     * @param <E>        元素的类型
     * @param instanceId 实例ID
     * @param keys       键的集合
     * @param eClass     元素的类型类对象
     * @return 对象值的列表
     */
    <E> List<E> multiGetObjForInstance(String instanceId, Collection<String> keys, Class<E> eClass);

    /**
     * 根据多个键获取列表值。
     * <p>
     * 使用默认的实例名。
     * </p>
     *
     * @param <E>    列表元素的类型
     * @param keys   键的集合
     * @param eClass 列表元素的类型类对象
     * @return 列表值的列表
     */
    default <E> List<List<E>> multiGetList(Collection<String> keys, Class<E> eClass) {
        return multiGetListForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys, eClass);
    }

    /**
     * 根据多个键获取列表值。
     *
     * @param <E>        列表元素的类型
     * @param instanceId 实例ID
     * @param keys       键的集合
     * @param eClass     列表元素的类型类对象
     * @return 列表值的列表
     */
    <E> List<List<E>> multiGetListForInstance(String instanceId, Collection<String> keys, Class<E> eClass);

    /**
     * 从缓存中获取指定键的对象值。
     * <p>
     * 使用默认的实例名。
     * </p>
     *
     * @param key 存储的键
     * @return 获取的对象值
     */
    default Object getObj(String key) {
        return getObjForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key);
    }

    /**
     * 从指定实例中获取指定键的对象值。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @return 获取的对象值
     */
    Object getObjForInstance(String instanceId, String key);

    /**
     * 根据多个键获取对象值。
     * <p>
     * 使用默认的实例名。
     * </p>
     *
     * @param keys 键的集合
     * @return 对象值的列表
     */
    default List<Object> multiGetObj(Collection<String> keys) {
        return multiGetObjForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, keys);
    }

    /**
     * 根据多个键获取对象值。
     *
     * @param instanceId 实例ID
     * @param keys       键的集合
     * @return 对象值的列表
     */
    List<Object> multiGetObjForInstance(String instanceId, Collection<String> keys);

    /**
     * 将指定键的值存储到缓存中。
     * <p>
     * 使用默认的实例名，并且不设置过期时间。
     * </p>
     *
     * @param key   存储的键
     * @param value 要存储的值
     * @return 操作是否成功
     */
    default Boolean put(String key, Object value) {
        return putForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value);
    }

    /**
     * 将指定键的值存储到指定实例中。
     * <p>
     * 使用默认的过期时间（-1L）。
     * </p>
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param value      要存储的值
     * @return 操作是否成功
     */
    default Boolean putForInstance(String instanceId, String key, Object value) {
        return putForInstance(instanceId, key, value, 0L);
    }

    /**
     * 将指定键的值存储到缓存中，并设置过期时间。
     *
     * @param key    存储的键
     * @param value  要存储的值
     * @param expiry 过期时间（秒）
     * @return 操作是否成功
     */
    default Boolean put(String key, Object value, long expiry) {
        return putForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value, expiry);
    }

    /**
     * 将指定键的值存储到指定实例中，并设置过期时间。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param value      要存储的值
     * @param expiry     过期时间（秒）
     * @return 操作是否成功
     */
    default Boolean putForInstance(String instanceId, String key, Object value, long expiry) {
        return putForInstance(instanceId, key, value, expiry, TimeUnit.SECONDS);
    }

    /**
     * 将指定键的值存储到缓存中，并设置过期时间。
     *
     * @param key    存储的键
     * @param value  要存储的值
     * @param expiry 过期时间
     * @param unit   过期时间单位
     * @return 操作是否成功
     */
    default Boolean put(String key, Object value, long expiry, TimeUnit unit) {
        return putForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value, expiry, unit);
    }

    /**
     * 将指定键的值存储到指定实例中，并设置过期时间。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param value      要存储的值
     * @param expiry     过期时间
     * @param unit       过期时间单位
     * @return 操作是否成功
     */
    default Boolean putForInstance(String instanceId, String key, Object value, long expiry, TimeUnit unit) {
        return putForInstance(instanceId, key, value, expiry, unit, true);
    }

    /**
     * 将指定键的值存储到缓存中，并指定是否覆盖已有值。
     * <p>
     * 使用默认的过期时间（-1L）。
     * </p>
     *
     * @param key      存储的键
     * @param value    要存储的值
     * @param override 是否覆盖已有值
     * @return 操作是否成功
     */
    default Boolean put(String key, Object value, boolean override) {
        return putForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value, override);
    }

    /**
     * 将指定键的值存储到指定实例中，并指定是否覆盖已有值。
     * <p>
     * 使用默认的过期时间（-1L）。
     * </p>
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param value      要存储的值
     * @param override   是否覆盖已有值
     * @return 操作是否成功
     */
    default Boolean putForInstance(String instanceId, String key, Object value, boolean override) {
        return putForInstance(instanceId, key, value, 0L, override);
    }

    /**
     * 将指定键的值存储到缓存中，并设置过期时间和是否覆盖已有值。
     *
     * @param key      存储的键
     * @param value    要存储的值
     * @param expiry   过期时间（秒）
     * @param override 是否覆盖已有值
     * @return 操作是否成功
     */
    default Boolean put(String key, Object value, long expiry, boolean override) {
        return putForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value, expiry, override);
    }

    /**
     * 将指定键的值存储到指定实例中，并设置过期时间和是否覆盖已有值。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param value      要存储的值
     * @param expiry     过期时间（秒）
     * @param override   是否覆盖已有值
     * @return 操作是否成功
     */
    default Boolean putForInstance(String instanceId, String key, Object value, long expiry, boolean override) {
        return putForInstance(instanceId, key, value, expiry, TimeUnit.SECONDS, override);
    }

    /**
     * 将指定键的值存储到缓存中，并设置过期时间和是否覆盖已有值。
     *
     * @param key      存储的键
     * @param value    要存储的值
     * @param expiry   过期时间
     * @param unit     过期时间单位
     * @param override 是否覆盖已有值
     * @return 操作是否成功
     */
    default Boolean put(String key, Object value, long expiry, TimeUnit unit, boolean override) {
        return putForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value, expiry, unit, override);
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
    Boolean putForInstance(String instanceId, String key, Object value, long expiry, TimeUnit unit, boolean override);

    /**
     * 将字符串值追加到指定键对应的值后面。
     * <p>
     * 使用默认的实例名。
     * </p>
     *
     * @param key   存储的键
     * @param value 要追加的字符串值
     * @return 操作是否成功
     */
    default Boolean append(String key, String value) {
        return appendForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, value);
    }

    /**
     * 将字符串值追加到指定实例和键对应的值后面。
     *
     * @param instanceId 实例ID
     * @param key        存储的键
     * @param value      要追加的字符串值
     * @return 操作是否成功
     */
    Boolean appendForInstance(String instanceId, String key, String value);
}
