package top.mingempty.meta.data.domain.biz.dict.service.info.modifies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.enums.AuthorizationTypeEnum;

/**
 * 条目权限信息变更改内部传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AuthorizationModifiesInfo {

    /**
     * 授权类型
     * <pre class="code">
     * 1：角色编码
     * 2：用户编码
     * (含义同条目：entry_authorization_type)
     * </pre>
     */
    private AuthorizationTypeEnum authorizationType;

    /**
     * 授权编码
     */
    private String authorizationCode;

    /**
     * 是否已逻辑删除
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目：zero_or_one)
     * </pre>
     */
    private ZeroOrOneEnum deleteStatus;
}
