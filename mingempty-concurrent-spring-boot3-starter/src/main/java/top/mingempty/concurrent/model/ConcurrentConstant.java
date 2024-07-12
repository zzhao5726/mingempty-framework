package top.mingempty.concurrent.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 并发静态常量类
 *
 * @author zzhao
 */
@Schema(title = "并发静态常量类", description = "并发静态常量类")
public interface ConcurrentConstant {


    /**
     * 默认线程池名称
     */
    @Schema(title = "默认线程池名称", description = "默认线程池名称")
    String DEFAULT_THREAD_POOL_NAME = "Delegating-ThreadPool-%d";


    /**
     * 虚拟线程执行器名称
     */
    @Schema(title = "虚拟线程执行器名称", description = "虚拟线程执行器名称")
    String VIRTUAL = "virtual";
}
