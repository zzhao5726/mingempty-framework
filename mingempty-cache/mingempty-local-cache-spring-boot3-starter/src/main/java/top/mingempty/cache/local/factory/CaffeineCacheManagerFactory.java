package top.mingempty.cache.local.factory;

import com.github.benmanes.caffeine.cache.CacheLoader;
import lombok.AllArgsConstructor;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import top.mingempty.cache.local.entity.CaffeineProperties;
import top.mingempty.cache.local.entity.LocalCacheProperties;
import top.mingempty.cache.local.entity.wrapper.CaffeineCacheManagerWrapper;
import top.mingempty.domain.function.IBuilder;
import top.mingempty.domain.other.GlobalConstant;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CaffeineCacheManager 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class CaffeineCacheManagerFactory implements IBuilder<CaffeineCacheManagerWrapper> {
    private final LocalCacheProperties localCacheProperties;
    private final Map<String, CacheLoader<Object, Object>> cacheLoaderMap;

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public CaffeineCacheManagerWrapper build() {
        Map<String, CaffeineCacheManager> map = new ConcurrentHashMap<>();
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, caffeineCacheManager(localCacheProperties.getCaffeine()));
        localCacheProperties.getMoreCaffeine()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(),
                        caffeineCacheManager(entry.getValue())));
        return new CaffeineCacheManagerWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }


    private CaffeineCacheManager caffeineCacheManager(CaffeineProperties caffeineProperties) {
        List<String> alias = Optional.ofNullable(caffeineProperties.getAlias())
                .orElse(new CopyOnWriteArrayList<>());

        if (caffeineProperties.getEnableLocalApi()
                && !alias.contains("API")) {
            alias.add("API");
        }
        CaffeineCacheManager caffeineCacheManager
                = new CaffeineCacheManager(alias.toArray(new String[0]));

        caffeineCacheManager.setCaffeine(CaffeineFactory.getCaffeine(caffeineProperties));

        // 设置通用缓存类型是否为异步
        caffeineCacheManager.setAsyncCacheMode(caffeineProperties.isAsyncCacheMode());
        // 设置是否允许缓存中存在null值
        caffeineCacheManager.setAllowNullValues(caffeineProperties.isAllowNullValues());

        return caffeineCacheManager;
    }
}
