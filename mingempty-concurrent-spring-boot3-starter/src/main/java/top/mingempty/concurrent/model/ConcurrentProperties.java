package top.mingempty.concurrent.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.mingempty.concurrent.pool.PoolRouterExecutorService;
import top.mingempty.domain.dynamic.DynamicProperty;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 并发配置文件
 *
 * @author zzhao
 */
@Data
@ConfigurationProperties(prefix = "me.concurrent")
public class ConcurrentProperties extends DynamicProperty<ConcurrentProperties, ThreadPoolConfig, PoolRouterExecutorService> {

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 是否启用并发记录
     */
    private Boolean enabledRecord;

    /**
     * 虚拟线程名称
     */
    private String virtualName = ConcurrentConstant.VIRTUAL_THREAD_POOL_NAME;

    /**
     * 非线程池关闭时，等待任务执行时间（默认为一分钟）
     */
    private Long awaitTermination = 1L;

    /**
     * 非线程池关闭时，等待任务执行时间单位（默认为一分钟）
     */
    private TimeUnit awaitTerminationTimeUnit = TimeUnit.MINUTES;


    /**
     * 默认配置文件
     */
    private ThreadPoolConfig def = new ThreadPoolConfig();

    /**
     * 其余线程池配置
     */
    private List<ThreadPoolConfig> other;


    @Override
    protected Class<PoolRouterExecutorService> type() {
        return PoolRouterExecutorService.class;
    }

    @Override
    public ThreadPoolConfig def() {
        return def;
    }

    @Override
    public List<ThreadPoolConfig> other() {
        return other;
    }
}
