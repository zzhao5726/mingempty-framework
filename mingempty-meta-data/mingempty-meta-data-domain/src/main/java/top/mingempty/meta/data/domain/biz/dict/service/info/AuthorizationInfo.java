package top.mingempty.meta.data.domain.biz.dict.service.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.enums.AuthorizationTypeEnum;

import java.time.LocalDateTime;

/**
 * 字典条目权限内部传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AuthorizationInfo {

    /**
     * 条目编号
     */
    private String entryCode;

    /**
     * 条目版本（默认1）
     */
    private Long entryVersion;

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

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 删除用户
     */
    private String deleteOperator;

}
