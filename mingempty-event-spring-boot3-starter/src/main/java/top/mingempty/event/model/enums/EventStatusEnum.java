package top.mingempty.event.model.enums;

import lombok.Getter;

/**
 * 事件状态
 *
 * @author zzhao
 */
@Getter
public enum EventStatusEnum {
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

    EventStatusEnum(String code) {
        this.code = code;
    }

}
