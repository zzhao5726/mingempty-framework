package top.mingempty.distributed.lock.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import top.mingempty.distributed.lock.aspect.ResubmitLockAspect;

/**
 * 分布式锁自动配置类
 *
 * @author zzhao
 */
@ConditionalOnProperty(prefix = "me.distributed.lock", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ResubmitLockConfig {

    @Bean
    public ResubmitLockAspect resubmitLockAspect() {
        return new ResubmitLockAspect();
    }
}

