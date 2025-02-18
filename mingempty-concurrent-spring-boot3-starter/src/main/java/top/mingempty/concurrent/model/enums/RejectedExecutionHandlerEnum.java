package top.mingempty.concurrent.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池拒绝策略
 *
 * @author zzhao
 * @date 2023/8/4 11:02
 */
@Schema(title = "线程池拒绝策略", description = "线程池拒绝策略")
public enum RejectedExecutionHandlerEnum {

    /**
     * 默认策略。使用该策略时，如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常。
     */
    @Schema(title = "默认策略", description = "使用该策略时，如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常。")
    AbortPolicy,
    /**
     * AbortPolicy的slient版本，如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常。
     */
    @Schema(title = "AbortPolicy的slient版本", description = "线程池队列满了，会直接丢掉这个任务并且不会有任何异常。")
    DiscardPolicy,
    /**
     * 丢弃线程池内最老的。也就是说如果队列满了，会将最早进入队列的任务删掉腾出空间，再尝试加入队列。（使用优先级队列时，将丢弃优先级最高的）
     */
    @Schema(title = "丢弃线程池内最老的", description = "如果队列满了，会将最早进入队列的任务删掉腾出空间，再尝试加入队列。（使用优先级队列时，将丢弃优先级最高的）")
    DiscardOldestPolicy,
    /**
     * 如果添加到线程池失败，并且线程池未关闭时，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行。
     */
    @Schema(title = "线程池未关闭时，主线程自己执行", description = "如果添加到线程池失败，并且线程池未关闭时，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行。")
    CallerRunsPolicy,
    /**
     * 如果添加到线程池失败，不管线程池是否关闭，主线程都会自己去执行该任务。
     */
    @Schema(title = "主线程自己执行", description = "如果添加到线程池失败，不管线程池是否关闭，主线程都会自己去执行该任务。")
    RejectedRunsPolicy,
    ;


    public static RejectedExecutionHandler rejectedExecutionHandler(RejectedExecutionHandlerEnum rejectedExecutionHandlerEnum) {
        return switch (rejectedExecutionHandlerEnum) {
            case DiscardPolicy -> new ThreadPoolExecutor.DiscardPolicy();
            case DiscardOldestPolicy -> new ThreadPoolExecutor.DiscardOldestPolicy();
            case CallerRunsPolicy -> new ThreadPoolExecutor.CallerRunsPolicy();
            case RejectedRunsPolicy -> top.mingempty.domain.other.RejectedRunsPolicy.INSTANCE;
            // AbortPolicy类型采用默认分支
            default -> new ThreadPoolExecutor.AbortPolicy();
        };
    }

}
