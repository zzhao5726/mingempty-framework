package top.mingempty.meta.data.domain.biz.dict.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.enums.AuthorizationTypeEnum;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 条目权限查询参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AuthorizationQuery implements BaseQuery<AuthorizationQuery> {

    /**
     * 查询字段
     */
    private Collection<String> selectColumns;

    /**
     * 条目编号
     */
    private String entryCode;

    /**
     * 模糊条目编号
     */
    private String entryCodeLike;

    /**
     * 条目编号集合
     */
    private Collection<String> entryCodes;

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
     * 授权编码集合
     */
    private Collection<String> authorizationCodes;

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
