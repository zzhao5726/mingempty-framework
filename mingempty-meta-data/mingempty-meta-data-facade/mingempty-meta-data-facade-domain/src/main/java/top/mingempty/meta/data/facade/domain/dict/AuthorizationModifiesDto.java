package top.mingempty.meta.data.facade.domain.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 创建或修改条目权限的DTO
 */
@Data
@Schema(title = "创建或修改条目权限的DTO")
public class AuthorizationModifiesDto {

    /**
     * 授权类型
     * <pre class="code">
     * 1：角色编码
     * 2：用户编码
     * (含义同条目：entry_authorization_type)
     * </pre>
     */
    @Schema(title = "授权类型", description = "1：角色编码" +
            "2：用户编码" +
            "(含义同条目：entry_authorization_type)")
    private String authorizationType;

    /**
     * 授权编码
     */
    @Schema(title = "授权编码")
    private String authorizationCode;

    /**
     * 是否已逻辑删除
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目：zero_or_one)
     * </pre>
     */
    @Schema(title = "是否已逻辑删除", description = "0：否" +
            "1：是" +
            "(同字典条目：zero_or_one)")
    private String deleteStatus;
}
