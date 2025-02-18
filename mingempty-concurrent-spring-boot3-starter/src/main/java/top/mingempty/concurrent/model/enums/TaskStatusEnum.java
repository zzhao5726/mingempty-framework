package top.mingempty.concurrent.model.enums;

import lombok.Getter;

/**
 * 任务状态
 *
 * @author zzhao
 */
@Getter
public enum TaskStatusEnum {
    /**
     * 初始化
     */
    INITIALIZED("0"),
    /**
     * 已处理
     */
    PROCESSED("1"),
    /**
     * 已撤销
     */
    CANCELED("2");

    private final String code;

    TaskStatusEnum(String code) {
        this.code = code;
    }

}
