package top.mingempty.concurrent.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.mingempty.concurrent.model.enums.RejectedExecutionHandlerEnum;
import top.mingempty.concurrent.model.enums.WorkQueueEnum;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置文件
 *
 * @author zzhao
 */
@Data
@ConfigurationProperties(prefix = "me.concurrent")
@Schema(title = "线程池配置文件", description = "线程池配置文件")
public class ThreadPoolProperties {

    /**
     * 是否开启默认线程池
     */
    @Schema(title = "是否开启默认线程池", description = "是否开启默认线程池")
    private Boolean enabled;

    /**
     * 核心线程数（默认为CPU核心数除以2取整）
     */
    @Schema(title = "核心线程数（默认为CPU核心数除以2取整）", description = "核心线程数（默认为CPU核心数除以2取整）")
    private Integer corePoolSize = Runtime.getRuntime().availableProcessors() / 2;

    /**
     * 最大线程数（默认为CPU核心数）
     */
    @Schema(title = "最大线程数（默认为CPU核心数）", description = "最大线程数（默认为CPU核心数）")
    private Integer maximumPoolSize = Runtime.getRuntime().availableProcessors();

    /**
     * 非核心线程空闲时间（默认为一分钟）
     */
    @Schema(title = "非核心线程空闲时间（默认为一分钟）", description = "非核心线程空闲时间（默认为一分钟）")
    private Long keepAliveTime = 1L;

    /**
     * 非核心线程空闲时间单位（默认为一分钟）
     */
    @Schema(title = "非核心线程空闲时间单位（默认为一分钟）", description = "非核心线程空闲时间单位（默认为一分钟）")
    private TimeUnit keepAliveTimeUnit = TimeUnit.MINUTES;

    /**
     * 线程池关闭时，等待任务执行时间（默认为一分钟）
     */
    @Schema(title = "线程池关闭时，等待任务执行时间（默认为一分钟）", description = "线程池关闭时，等待任务执行时间（默认为一分钟）")
    private Long awaitTermination = 1L;

    /**
     * 线程池关闭时，等待任务执行时间单位（默认为一分钟）
     */
    @Schema(title = "线程池关闭时，等待任务执行时间单位（默认为一分钟）", description = "线程池关闭时，等待任务执行时间单位（默认为一分钟）")
    private TimeUnit awaitTerminationTimeUnit = TimeUnit.MINUTES;


    /**
     * 队列最大数
     */
    @Schema(title = "队列最大数", description = "队列最大数")
    private Integer maxPoolSize = 500;


    /**
     * 线程别名
     */
    @Schema(title = "线程别名", description = "线程别名")
    private String threadName = "Bt-Delegating-ThreadPool-%d";


    /**
     * 阻塞队列类型
     */
    @Schema(title = "阻塞队列类型", description = "阻塞队列类型")
    private WorkQueueEnum workQueueEnum = WorkQueueEnum.Priority;

    /**
     * 线程池拒绝策略
     */
    @Schema(title = "线程池拒绝策略", description = "线程池拒绝策略")
    private RejectedExecutionHandlerEnum rejectedExecutionHandlerEnum = RejectedExecutionHandlerEnum.RejectedRunsPolicy;


    /**
     * 是否守护线程（默认false）
     */
    @Schema(title = "是否守护线程（默认false）", description = "是否守护线程（默认false）")
    private Boolean daemon = false;

    /**
     * 线程优先级
     */
    @Schema(title = "线程优先级", description = "线程优先级")
    private Integer priority = Thread.NORM_PRIORITY;

    /**
     * 其余线程池配置
     */
    @Schema(title = "其余线程池配置", description = "其余线程池配置")
    private Map<String, ThreadPoolProperties> otherThreadPoolProperties;

}
