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
    String DEFAULT_INSTANCE_NAME = "default";

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
}
