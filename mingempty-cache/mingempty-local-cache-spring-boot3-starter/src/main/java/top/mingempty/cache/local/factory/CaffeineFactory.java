package top.mingempty.cache.local.factory;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.checkerframework.checker.index.qual.NonNegative;
import top.mingempty.cache.local.entity.CacheObj;
import top.mingempty.cache.local.entity.CaffeineProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Caffeine工厂
 *
 * @author zzhao
 */
public class CaffeineFactory {


    private static final Map<CaffeineProperties, Caffeine<Object, Object>> CAFFEINES = new ConcurrentHashMap<>();


    public static Caffeine<Object, Object> getCaffeine(CaffeineProperties caffeineProperties) {
        return CAFFEINES.computeIfAbsent(caffeineProperties, CaffeineFactory::build);
    }

    private static Caffeine<Object, Object> build(CaffeineProperties caffeineProperties) {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .maximumSize(caffeineProperties.getMaximumSize());

        if (!caffeineProperties.isMoreExpireConfig()) {
            if (caffeineProperties.getExpireAfterCreate() >= 0) {
                caffeine.expireAfterWrite(caffeineProperties.getExpireAfterCreate(), caffeineProperties.getExpireAfterCreateUnit());
            }
        } else {
            if (caffeineProperties.isMoreSameExpireConfig()) {
                if (caffeineProperties.getExpireAfterCreate() >= 0) {
                    caffeine.expireAfterAccess(caffeineProperties.getExpireAfterCreate(), caffeineProperties.getExpireAfterCreateUnit());
                }
            } else {
                caffeine.expireAfter(new Expiry<>() {
                    @Override
                    public long expireAfterCreate(Object key, Object value, long currentTime) {
                        Long expireAfterCreate = caffeineProperties.getExpireAfterCreate();
                        TimeUnit expireAfterCreateUnit = caffeineProperties.getExpireAfterCreateUnit();
                        if (value instanceof CacheObj cacheObj) {
                            expireAfterCreate = cacheObj.getExpireAfterCreate();
                            expireAfterCreateUnit = cacheObj.getExpireAfterCreateUnit();
                        }
                        if (Long.valueOf(-1).equals(expireAfterCreate)) {
                            return Long.MAX_VALUE;
                        }
                        // 缓存创建时过期时间
                        return expireAfterCreateUnit.toNanos(expireAfterCreate);
                    }

                    @Override
                    public long expireAfterUpdate(Object key, Object value, long currentTime, @NonNegative long currentDuration) {
                        Long expireAfterUpdate = caffeineProperties.getExpireAfterUpdate();
                        TimeUnit expireAfterUpdateUnit = caffeineProperties.getExpireAfterUpdateUnit();
                        if (value instanceof CacheObj cacheObj) {
                            expireAfterUpdate = cacheObj.getExpireAfterUpdate();
                            expireAfterUpdateUnit = cacheObj.getExpireAfterUpdateUnit();
                        }

                        if (Long.valueOf(-1).equals(expireAfterUpdate)) {
                            return currentDuration;
                        }
                        // 缓存更新后过期时间
                        return expireAfterUpdateUnit.toNanos(expireAfterUpdate);
                    }

                    @Override
                    public long expireAfterRead(Object key, Object value, long currentTime, @NonNegative long currentDuration) {
                        Long expireAfterRead = caffeineProperties.getExpireAfterRead();
                        TimeUnit expireAfterReadUnit = caffeineProperties.getExpireAfterReadUnit();
                        if (value instanceof CacheObj cacheObj) {
                            expireAfterRead = cacheObj.getExpireAfterRead();
                            expireAfterReadUnit = cacheObj.getExpireAfterReadUnit();
                        }

                        if (Long.valueOf(-1).equals(expireAfterRead)) {
                            return currentDuration;
                        }
                        // 缓存读取后过期时间
                        return expireAfterReadUnit.toNanos(expireAfterRead);
                    }
                });
            }
        }

        // 设置是否启用缓存的统计功能
        if (caffeineProperties.isRecordStats()) {
            caffeine.recordStats();
        }
        caffeine.build();
        return caffeine;
    }

}
