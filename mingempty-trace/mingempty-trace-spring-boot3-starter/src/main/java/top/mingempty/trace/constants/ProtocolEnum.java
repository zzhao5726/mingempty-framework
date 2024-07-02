package top.mingempty.trace.constants;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 链路入口来源
 * <p>
 * 只能通过http请求或者RPC调用，其实RPC调用时也说明会有一个前置应用
 *
 * @author zzhao
 */
@Getter
@Schema(title = "链路入口来源", description = "链路入口来源")
public enum ProtocolEnum {

    @Schema(title = "HTTP", description = "HTTP")
    HTTP(1, "HTTP"),

    @Schema(title = "RPC", description = "RPC")
    RPC(2, "RPC"),

    @Schema(title = "OTHER", description = "OTHER")
    OTHER(0, "OTHER"),
    ;

    /**
     * 编码
     */
    @Schema(title = "编码", description = "编码")
    private final int code;

    /**
     * 描述
     */
    @Schema(title = "描述", description = "描述")
    private final String desc;

    private ProtocolEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 通过描述获取来源
     *
     * @param desc
     * @return
     */
    public static ProtocolEnum getProtocol(String desc) {
        if (null == desc) {
            return OTHER;
        }

        return switch (desc.toLowerCase()) {
            case "http" -> HTTP;
            case "rpc" -> RPC;
            default -> OTHER;
        };
    }

    /**
     * 通过编码获取来源
     *
     * @param code
     * @return
     */
    public static ProtocolEnum getProtocol(int code) {
        if (code == 1) {
            return HTTP;
        } else if (code == 2) {
            return RPC;
        } else {
            return OTHER;
        }
    }
}
