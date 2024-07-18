package top.mingempty.cache.redis.api;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.core.ScanOptions;
import top.mingempty.cache.commons.api.CacheSet;
import top.mingempty.domain.other.GlobalConstant;

import java.util.List;
import java.util.Set;

/**
 * 集合接口，用于对缓存中的集合进行操作。
 * </p>
 * 对redis的增强
 *
 * @author zzhao
 */
public interface RedisCacheSet extends CacheSet {

    /**
     * 获取当前缓存实例的集合缓存API。
     * <p>
     * 返回当前实例的 {@link RedisCacheSet} 类型的API。
     * </p>
     *
     * @return 当前缓存实例的集合缓存集合
     */
    default RedisCacheSet getRedisCacheSet() {
        return this;
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
    default <E> Set<E> setScanForInstance(String instanceId, String key, String pattern, TypeReference<List<E>> listTypeReference) {
        if (pattern == null) {
            pattern = "*";
        }
        if (!pattern.startsWith("*")) {
            pattern = "*".concat(pattern);
        }
        if (!pattern.endsWith("*")) {
            pattern = pattern.concat("*");
        }
        return setScanForInstance(instanceId, key, ScanOptions.scanOptions().match(pattern).build(), listTypeReference);
    }

    /**
     * 获取符合指定格式的元素集合
     *
     * @param key               元素的键
     * @param options           元素格式
     * @param listTypeReference 元素的类型
     * @return 操作结果
     */
    default <E> Set<E> setScan(String key, ScanOptions options, TypeReference<List<E>> listTypeReference) {
        return setScanForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, options, listTypeReference);
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
    <E> Set<E> setScanForInstance(String instanceId, String key, ScanOptions options, TypeReference<List<E>> listTypeReference);

}
