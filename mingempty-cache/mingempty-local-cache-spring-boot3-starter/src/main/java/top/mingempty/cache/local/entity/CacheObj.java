package top.mingempty.cache.local.entity;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * 对cache的封装
 *
 * @author zzhao
 */
@Data
public class CacheObj {

    private final Object cacheObj;

    public CacheObj(Object cacheObj) {
        this.cacheObj = cacheObj;
    }

    /**
     * 创建缓存时的过期策略--默认十分钟后刷新。
     */
    private Long expireAfterCreate = 10L;

    /**
     * 创建缓存时的过期策略时间单位。
     */
    private TimeUnit expireAfterCreateUnit = TimeUnit.MINUTES;

    /**
     * 更新缓存时的过期策略--默认十分钟后刷新。
     */
    private Long expireAfterUpdate = 10L;

    /**
     * 更新缓存时的过期策略时间单位。
     */
    private TimeUnit expireAfterUpdateUnit = TimeUnit.MINUTES;

    /**
     * 读取缓存时的过期策略--默认十分钟后刷新。
     */
    private Long expireAfterRead = 10L;

    /**
     * 读取缓存时的过期策略时间单位。
     */
    private TimeUnit expireAfterReadUnit = TimeUnit.MINUTES;

}
