package top.mingempty.cache.redis.api;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import top.mingempty.domain.other.GlobalConstant;

import java.util.List;
import java.util.Map;

/**
 * 提供对地理空间数据进行操作的接口。
 * <p>
 * 此接口定义了对Redis中地理空间数据的增删改查等操作。
 *
 * @author zzhao
 */
public interface RedisCacheGeo {

    /**
     * 获取当前缓存实例的地理空间数据操作Api。
     * <p>
     * 返回当前实例的 {@link RedisCacheGeo} 类型的Api。
     * </p>
     *
     * @return 当前缓存实例的地理空间数据操作Api
     */
    default RedisCacheGeo getCacheGeo() {
        return this;
    }

    /**
     * 向指定key的地理空间数据集中添加地理位置。
     *
     * @param key    键
     * @param point  地理位置的坐标
     * @param member 成员
     * @return 添加的地理位置数量
     */
    default Long geoAdd(String key, Point point, Object member) {
        return geoAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, point, member);
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
    Long geoAddForInstance(String instanceId, String key, Point point, Object member);

    /**
     * 向指定key的地理空间数据集中添加地理位置。
     *
     * @param key      键
     * @param location 地理位置
     * @return 添加的地理位置数量
     */
    default Long geoAdd(String key, RedisGeoCommands.GeoLocation<Object> location) {
        return geoAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, location);
    }

    /**
     * 向指定实例的地理空间数据集中添加地理位置。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param location   地理位置
     * @return 添加的地理位置数量
     */
    Long geoAddForInstance(String instanceId, String key, RedisGeoCommands.GeoLocation<Object> location);

    /**
     * 向指定key的地理空间数据集中添加多个地理位置。
     *
     * @param key                 键
     * @param memberCoordinateMap 成员与坐标的映射
     * @return 添加的地理位置数量
     */
    default Long geoAdd(String key, Map<Object, Point> memberCoordinateMap) {
        return geoAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, memberCoordinateMap);
    }

    /**
     * 向指定实例的地理空间数据集中添加多个地理位置。
     *
     * @param instanceId          实例ID
     * @param key                 键
     * @param memberCoordinateMap 成员与坐标的映射
     * @return 添加的地理位置数量
     */
    Long geoAddForInstance(String instanceId, String key, Map<Object, Point> memberCoordinateMap);

    /**
     * 向指定key的地理空间数据集中添加多个地理位置。
     *
     * @param key       键
     * @param locations 地理位置列表
     * @return 添加的地理位置数量
     */
    default Long geoAdd(String key, Iterable<RedisGeoCommands.GeoLocation<Object>> locations) {
        return geoAddForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, locations);
    }

    /**
     * 向指定实例的地理空间数据集中添加多个地理位置。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param locations  地理位置列表
     * @return 添加的地理位置数量
     */
    Long geoAddForInstance(String instanceId, String key, Iterable<RedisGeoCommands.GeoLocation<Object>> locations);

    /**
     * 计算两个成员之间的距离。
     *
     * @param key     键
     * @param member1 成员1
     * @param member2 成员2
     * @return 距离
     */
    default Distance geoDist(String key, Object member1, Object member2) {
        return geoDistForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, member1, member2);
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
    Distance geoDistForInstance(String instanceId, String key, Object member1, Object member2);

    /**
     * 计算两个成员之间的距离，并指定距离单位。
     *
     * @param key     键
     * @param member1 成员1
     * @param member2 成员2
     * @param metric  距离单位
     * @return 距离
     */
    default Distance geoDist(String key, Object member1, Object member2, Metric metric) {
        return geoDistForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, member1, member2, metric);
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
    Distance geoDistForInstance(String instanceId, String key, Object member1, Object member2, Metric metric);

    /**
     * 获取指定成员的Geohash字符串。
     *
     * @param key     键
     * @param members 成员列表
     * @return Geohash字符串列表
     */
    default List<String> geoHash(String key, Object... members) {
        return geoHashForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, members);
    }

    /**
     * 获取指定实例的成员的Geohash字符串。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param members    成员列表
     * @return Geohash字符串列表
     */
    List<String> geoHashForInstance(String instanceId, String key, Object... members);

    /**
     * 获取指定成员的坐标。
     *
     * @param key     键
     * @param members 成员列表
     * @return 坐标列表
     */
    default List<Point> geoPos(String key, Object... members) {
        return geoPosForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, members);
    }

    /**
     * 获取指定实例的成员的坐标。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param members    成员列表
     * @return 坐标列表
     */
    List<Point> geoPosForInstance(String instanceId, String key, Object... members);

    /**
     * 获取指定圆范围内的成员及其位置。
     *
     * @param key    键
     * @param within 圆范围
     * @return 地理位置结果
     */
    default GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusForInstance(String key, Circle within) {
        return geoRadiusForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, within);
    }

    /**
     * 获取指定实例的圆范围内的成员及其位置。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param within     圆范围
     * @return 地理位置结果
     */
    GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusForInstance(String instanceId, String key, Circle within);

    /**
     * 获取指定圆范围内的成员及其位置，并指定查询参数。
     *
     * @param key    键
     * @param within 圆范围
     * @param args   查询参数
     * @return 地理位置结果
     */
    default GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusForInstance(String key, Circle within, RedisGeoCommands.GeoRadiusCommandArgs args) {
        return geoRadiusForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, within, args);
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
    GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusForInstance(String instanceId, String key, Circle within, RedisGeoCommands.GeoRadiusCommandArgs args);

    /**
     * 获取指定成员为中心的圆范围内的成员及其位置。
     *
     * @param key    键
     * @param member 成员
     * @param radius 半径
     * @return 地理位置结果
     */
    default GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusByMemberForInstance(String key, Object member, double radius) {
        return geoRadiusByMemberForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, member, radius);
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
    GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusByMemberForInstance(String instanceId, String key, Object member, double radius);

    /**
     * 获取指定成员为中心的圆范围内的成员及其位置。
     *
     * @param key      键
     * @param member   成员
     * @param distance 距离
     * @return 地理位置结果
     */
    default GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusByMemberForInstance(String key, Object member, Distance distance) {
        return geoRadiusByMemberForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, member, distance);
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
    GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusByMemberForInstance(String instanceId, String key, Object member, Distance distance);

    /**
     * 获取指定成员为中心的圆范围内的成员及其位置，并指定查询参数。
     *
     * @param key      键
     * @param member   成员
     * @param distance 距离
     * @param args     查询参数
     * @return 地理位置结果
     */
    default GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusByMemberForInstance(String key, Object member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args) {
        return geoRadiusByMemberForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, member, distance, args);
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
    GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusByMemberForInstance(String instanceId, String key, Object member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs args);

    /**
     * 移除指定成员的地理位置。
     *
     * @param key     键
     * @param members 成员列表
     * @return 移除的地理位置数量
     */
    default Long geoRemove(String key, Object... members) {
        return geoRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, key, members);
    }

    /**
     * 移除指定实例的成员的地理位置。
     *
     * @param instanceId 实例ID
     * @param key        键
     * @param members    成员列表
     * @return 移除的地理位置数量
     */
    Long geoRemoveForInstance(String instanceId, String key, Object... members);
}
