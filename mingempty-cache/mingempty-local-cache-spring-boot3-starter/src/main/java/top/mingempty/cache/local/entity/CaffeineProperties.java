package top.mingempty.cache.local.entity;

import lombok.Data;
import top.mingempty.builder.BuilderWrapperParent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * CaffeineCache配置文件
 *
 * @author zzhao
 */
@Data
public class CaffeineProperties implements BuilderWrapperParent {

    /**
     * 将缓存管理器构建的通用缓存类型设置为异步.
     */
    private boolean asyncCacheMode = false;

    /**
     * 指定是否接受和转换缓存管理器中所有缓存的{@code null} 值。
     */
    private boolean allowNullValues = true;

    /**
     * 最大缓存大小
     */
    private Long maximumSize = 2000L;

    /**
     * 创建缓存时的过期策略--默认十分钟后刷新。
     */
    private Long expireAfterCreate = 10L;

    /**
     * 创建缓存时的过期策略时间单位。
     */
    private TimeUnit expireAfterCreateUnit = TimeUnit.MINUTES;

    /**
     * 是否启用多余过期时间配置
     */
    private boolean moreExpireConfig = false;

    /**
     * 是否启用相同的过期时间配置
     */
    private boolean moreSameExpireConfig = true;

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

    /**
     * 缓存的刷新策略--默认十分钟后刷新。
     */
    private Long refreshAfterWrite = 10L;

    /**
     * 缓存的刷新策略时间单位。
     */
    private TimeUnit refreshAfterWriteUnit = TimeUnit.MINUTES;

    /**
     * 是否启用缓存的统计功能
     */
    private boolean recordStats = false;

    /**
     * 是否启用API功能
     */
    private boolean enableLocalApi = false;

    /**
     * 是否注册到spring
     */
    private boolean registerToSpring = false;

    /**
     * 缓存别名集合
     */
    private List<String> alias = new ArrayList<>() {{
        add("SYS");
    }};

    /**
     * 是否注册到spring
     *
     * @return
     */
    @Override
    public boolean registerToSpring() {
        return false;
    }
}
