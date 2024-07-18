package top.mingempty.cache.commons.entity.wrapper;

import cn.hutool.core.util.ObjUtil;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import top.mingempty.domain.other.AbstractRouter;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zzhao
 */
public abstract class CacheManagerWrapper<T extends CacheManager> extends AbstractRouter<T> implements CacheManager {

    public CacheManagerWrapper(String defaultTargetName, Map<String, T> targetRouter) {
        super(defaultTargetName, targetRouter);
    }

    public CacheManagerWrapper(String defaultTargetName, Map<String, T> targetRouter, boolean lenientFallback) {
        super(defaultTargetName, targetRouter, lenientFallback);
    }

    /**
     * Get the cache associated with the given name.
     * <p>Note that the cache may be lazily created at runtime if the
     * native provider supports it.
     *
     * @param name the cache identifier (must not be {@code null})
     * @return the associated cache, or {@code null} if such a cache
     * does not exist or could be not created
     */
    @Override
    public Cache getCache(String name) {
        Cache cache = determineTargetRouter().getCache(name);
        if (ObjUtil.isNotEmpty(cache)) {
            return cache;
        }
        return getResolvedRouter()
                .entrySet()
                .parallelStream()
                .flatMap(entry -> entry.getValue().getCacheNames().parallelStream()
                        .map(innerCacheName -> Map.entry(entry.getKey().concat(":").concat(innerCacheName),
                                Objects.requireNonNull(entry.getValue().getCache(innerCacheName)))))
                .filter(entry -> entry.getKey().equals(name))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }

    /**
     * Get a collection of the cache names known by this manager.
     *
     * @return the names of all caches known by the cache manager
     */
    @Override
    public Collection<String> getCacheNames() {
        return getResolvedRouter().entrySet()
                .parallelStream()
                .flatMap(entry -> entry.getValue().getCacheNames().parallelStream()
                        .map(cacheName -> entry.getKey().concat(":").concat(cacheName)))
                .collect(Collectors.toSet());
    }
}
