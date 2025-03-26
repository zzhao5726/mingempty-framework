package top.mingempty.meta.data.repository.impl.middleware.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.mingempty.meta.data.domain.middleware.cache.repository.LocalCacheRepository;

/**
 * 基于Caffeine的本地缓存的仓储接口实现
 *
 * @author zzhao
 */
@Slf4j
@Component
public class CaffeineCacheRepositoryImpl implements LocalCacheRepository {
}
