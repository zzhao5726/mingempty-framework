package top.mingempty.concurrent.model.enums;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 阻塞队列类型
 *
 * @author zzhao
 * @date 2023/8/4 11:13
 */
@Schema(title = "阻塞队列类型")
public enum WorkQueueEnum {

    /**
     * 数组
     */
    @Schema(title = "数组")
    array,
    /**
     * 链表
     */
    @Schema(title = "链表")
    linked,
    /**
     * 有序
     */
    @Schema(title = "有序")
    Priority;


    public static <T> BlockingQueue<T> blockingQueue(WorkQueueEnum workQueueEnum, Integer maxPoolSize) {
        switch (workQueueEnum) {
            case array: {
                return new ArrayBlockingQueue<>(maxPoolSize);
            }
            case linked: {
                return new LinkedBlockingQueue<>(maxPoolSize);
            }
            // Priority类型采用默认分支
            default: {
                return new PriorityBlockingQueue<>(maxPoolSize);
            }
        }
    }

}
