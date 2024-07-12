package top.mingempty.concurrent.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 线程执行结果记录
 *
 * @author zzhao
 * @date 2023/8/4 11:30
 */
@Data
@Schema(title = "线程执行结果记录", description = "线程执行结果记录")
public class ConcurrentResult<T> {

    /**
     * 线程执行返回结果
     */
    @Schema(title = "线程执行返回结果", description = "线程执行返回结果")
    private T data;

    /**
     * 线程执行异常
     */
    @Schema(title = "线程执行异常", description = "线程执行异常")
    private Exception exception;

}