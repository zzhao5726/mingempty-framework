package top.mingempty.concurrent.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 线程优先级枚举
 *
 * @author zzhao
 * @date 2023/8/9 15:33
 */
@Getter
@Schema(title = "线程优先级枚举", description = "线程优先级枚举")
public enum PriorityEnum {


    U(5, "极高"),
    M(4, "高"),
    D(3, "默认"),
    S(2, "低"),
    E(1, "极低"),
    ;


    /**
     * 线程优先级值
     */
    @Schema(title = "线程优先级值", description = "线程优先级值")
    private final Integer priority;

    /**
     * 线程优先级描述
     */
    @Schema(title = "线程优先级描述", description = "线程优先级描述")
    private final String priorityDesc;

    PriorityEnum(Integer priority, String priorityDesc) {
        this.priority = priority;
        this.priorityDesc = priorityDesc;
    }
}
