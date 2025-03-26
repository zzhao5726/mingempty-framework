package top.mingempty.meta.data.domain.biz.dict.value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.enums.AuthorizationTypeEnum;

import java.util.Objects;
import java.util.Optional;

/**
 * 条目权限数据值对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AuthorizationVo {

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
     * 条目权限数据逻辑删除状态
     */
    private DeleteVo deleteStatus;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AuthorizationVo that)) return false;
        return getAuthorizationType() == that.getAuthorizationType() && Objects.equals(getAuthorizationCode(), that.getAuthorizationCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthorizationType(), getAuthorizationCode());
    }


    /**
     * 获取条目权限数据值对象的删除状态
     *
     * @return 删除状态
     */
    public ZeroOrOneEnum deleteStatus() {
        return Optional.ofNullable(deleteStatus)
                .map(DeleteVo::getDeleteStatus)
                .orElse(ZeroOrOneEnum.ZERO);
    }

    /**
     * 构件一个基于当前权限的删除副本
     *
     * @return 条目权限数据值对象
     */
    public AuthorizationVo delete() {
        return AuthorizationVo.builder()
                .authorizationType(authorizationType)
                .authorizationCode(authorizationCode)
                .deleteStatus(DeleteVo.builder().deleteStatus(ZeroOrOneEnum.ONE).build())
                .build();
    }
}
