package top.mingempty.meta.data.model.bo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 字典条目权限列表查询条件对象
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Data
public class AuthorizationQueryBo {

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
    private String authorizationType;

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
    private String deleteStatus;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    private String deleteOperator;
}
