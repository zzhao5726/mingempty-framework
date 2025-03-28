package top.mingempty.domain.other;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 静态常量类
 *
 * @author zzhao
 */
@Schema(title = "常量类", description = "常量类")
public interface GlobalConstant {

    /**
     * 默认实例名称
     */
    @Schema(title = "默认实例名称", description = "默认实例名称")
    String DEFAULT_INSTANCE_NAME = "def";

    /**
     * 业务类型
     */
    @Schema(title = "业务类型", description = "业务类型")
    String BUSS_TYPE = "ME-BUSS-TYPE";

    /**
     * 业务号码
     */
    @Schema(title = "业务号码", description = "业务号码")
    String BUSS_NO = "ME-BUSS-NO";


    /**
     * 字符串常量：{@code "null"} <br>
     * 注意：{@code "null" != null}
     */
    @Schema(title = "字符串常量", description = "null")
    String NULL = "null";

    /**
     * 字符串常量：空字符串 {@code ""}
     */
    @Schema(title = "字符串常量", description = "空字符串")
    String EMPTY = "";

    /**
     * 字符串常量：空格符 {@code " "}
     */
    @Schema(title = "字符串常量", description = "空格符")
    String SPACE = " ";

    /**
     * 字符串常量：{@code "request-from-inner"}
     * </p>
     * 标识当前请求来源于内部
     */
    @Schema(title = "字符串常量", description = "标识当前请求来源于内部")
    String REQUEST_FROM_INNER = "request-from-inner";


    /**
     * Access-Control-Expose-Headers
     */
    @Schema(title = "Access-Control-Expose-Headers", description = "Access-Control-Expose-Headers")
    String ALL = "*";

    /**
     * Access-Control-Max-Age
     */
    @Schema(title = "Access-Control-Max-Age", description = "Access-Control-Max-Age")
    Long MAX_AGE = 18000L;
}
