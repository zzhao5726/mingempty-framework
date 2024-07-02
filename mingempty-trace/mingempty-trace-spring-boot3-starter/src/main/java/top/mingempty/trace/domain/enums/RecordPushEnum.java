package top.mingempty.trace.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 链路日志推送枚举
 *
 * @author zzhao
 */
@Schema(title = "链路日志推送枚举")
public enum RecordPushEnum {
    DEFAULT,
    KAFKA,
    ES,
    ;
}
