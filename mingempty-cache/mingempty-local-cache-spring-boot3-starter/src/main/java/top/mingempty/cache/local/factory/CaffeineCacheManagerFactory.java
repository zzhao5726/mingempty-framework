package top.mingempty.cache.local.factory;

import com.github.benmanes.caffeine.cache.CacheLoader;
import lombok.AllArgsConstructor;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import top.mingempty.builder.WrapperBuilder;
import top.mingempty.cache.local.entity.CaffeineProperties;
import top.mingempty.cache.local.entity.LocalCacheProperties;
import top.mingempty.cache.local.entity.wrapper.CaffeineCacheManagerWrapper;
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
public class CaffeineCacheManagerFactory implements WrapperBuilder<CaffeineCacheManagerWrapper, CaffeineCacheManager, CaffeineProperties> {
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
        map.put(GlobalConstant.DEFAULT_INSTANCE_NAME, build(GlobalConstant.DEFAULT_INSTANCE_NAME, localCacheProperties.getCaffeine()));
        localCacheProperties.getMoreCaffeine()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(),
                        build(entry.getKey(), entry.getValue())));
        return new CaffeineCacheManagerWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }

    /**
     * 构建
     *
     * @param caffeineProperties
     * @return 被构建的对象
     */
    @Override
    public CaffeineCacheManager buildToSub(String instanceName,CaffeineProperties caffeineProperties) {
        List<String> alias = Optional.ofNullable(caffeineProperties.getAlias())
                .orElse(new CopyOnWriteArrayList<>());

        if (caffeineProperties.isEnableLocalApi()
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
