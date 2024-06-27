package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import top.mingempty.domain.base.IResult;

/**
 * 接口结果枚举
 *
 * @author zzhao
 */
public enum DefaultResultEnum implements IResult {

    SUCCESS("G-D-00000", "接口调用成功"),

    FAILED("G-D-00001", "系统异常"),
    RESUBMIT("G-D-00002", "接口重复提交"),
    NOT_FOUND("G-D-00003", "接口未找到"),
    VALIDATE_FAILED("G-D-00004", "参数校验失败"),
    LOGIN_ERROR("G-D-0005", "登录验证失败"),
    FORBIDDEN("G-D-00006", "没有权限访问资源"),
    NO_LOGIN("G-D-00007", "用户未登陆"),
    TOKEN_OVERDUE("G-D-00008", "非法token或token过期，请刷新token"),
    ;

    /**
     * 接口返回编码
     */
    @Schema(title = "接口返回编码")
    private final String code;

    /**
     * 接口返回信息
     */
    @Schema(title = "接口返回信息")
    private final String message;

    DefaultResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
