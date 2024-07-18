package top.mingempty.cache.redis.api;

import org.springframework.data.redis.core.ScanOptions;
import top.mingempty.cache.commons.api.CacheMap;
import top.mingempty.domain.other.GlobalConstant;

import java.util.Map;

/**
 * 缓存映射接口，用于操作缓存中的映射（键-值对）。
 * </p>
 * 对redis的增强
 *
 * @author zzhao
 */
public interface RedisCacheMap extends CacheMap {

    /**
     * 获取当前缓存实例的 Map 类型API。
     * <p>
     * 返回当前实例的 {@link RedisCacheMap} 类型的API。
     * </p>
     *
     * @return 当前缓存实例的 Map 类型的缓存集合
     */
    default RedisCacheMap getRedisCacheMap() {
        return this;
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
    default <E> Map<String, E> mapScanForInstance(String instanceId, String key, String pattern, Class<E> valueType) {
        if (pattern == null) {
            pattern = "*";
        }
        if (!pattern.startsWith("*")) {
            pattern = "*".concat(pattern);
        }
        if (!pattern.endsWith("*")) {
            pattern = pattern.concat("*");
        }
        return mapScanForInstance(instanceId, key, ScanOptions.scanOptions().match(pattern).build(), valueType);
    }

    /**
     * 随机获取映射键内指定数量值
     *
     * @param <E>       值的类型
     * @param key       映射的键
     * @param options   元素格式
     * @param valueType 映射中值的类型
     * @return 随机的数据
     */
    default <E> Map<String, E> mapScan(String key, ScanOptions options, Class<E> valueType) {
        return mapScanForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, options, valueType);
    }

    /**
     * 随机获取映射键内指定数量值。
     *
     * @param <E>        值的类型
     * @param instanceId 实例ID
     * @param key        映射的键
     * @param options    元素格式
     * @param valueType  映射中值的类型
     * @return 随机的数据
     */
    <E> Map<String, E> mapScanForInstance(String instanceId, String key, ScanOptions options, Class<E> valueType);
}
