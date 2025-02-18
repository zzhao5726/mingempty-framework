package top.mingempty.concurrent.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.mingempty.concurrent.model.enums.RejectedExecutionHandlerEnum;
import top.mingempty.concurrent.model.enums.WorkQueueEnum;
import top.mingempty.domain.dynamic.DynamicConfig;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置文件
 *
 * @author zzhao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ThreadPoolConfig extends DynamicConfig {


    /**
     * 核心线程数
     * <br>
     * 默认为CPU核心数除以2取整
     */
    private Integer corePoolSize = Runtime.getRuntime().availableProcessors() / 2;

    /**
     * 最大线程数
     * <br>
     * 默认为CPU核心数
     */
    private Integer maximumPoolSize = Runtime.getRuntime().availableProcessors();

    /**
     * 非核心线程空闲时间
     * <br>
     * 默认为一分钟
     */
    private Long keepAliveTime = 1L;

    /**
     * 非核心线程空闲时间单位
     * <br>
     * 默认为一分钟
     */
    private TimeUnit keepAliveTimeUnit = TimeUnit.MINUTES;

    /**
     * 线程池关闭时，等待任务执行时间
     * <br>
     * 默认为一分钟
     */
    private Long awaitTermination = 1L;

    /**
     * 线程池关闭时，等待任务执行时间单位
     * <br>
     * 默认为一分钟
     */
    private TimeUnit awaitTerminationTimeUnit = TimeUnit.MINUTES;


    /**
     * 队列最大数
     */
    private Integer maxPoolSize = 500;


    /**
     * 线程别名
     */
    private String threadName = ConcurrentConstant.DEFAULT_THREAD_POOL_NAME;


    /**
     * 阻塞队列类型
     */
    private WorkQueueEnum workQueueEnum = WorkQueueEnum.Priority;

    /**
     * 线程池拒绝策略
     */
    private RejectedExecutionHandlerEnum rejectedExecutionHandlerEnum = RejectedExecutionHandlerEnum.RejectedRunsPolicy;


    /**
     * 是否守护线程
     * <br>
     * 默认false
     */
    private Boolean daemon = false;

    /**
     * 线程优先级
     */
    private Integer priority = Thread.NORM_PRIORITY;

    /**
     * 其余线程池配置
     */
    private Map<String, ThreadPoolConfig> otherThreadPoolProperties;

}
