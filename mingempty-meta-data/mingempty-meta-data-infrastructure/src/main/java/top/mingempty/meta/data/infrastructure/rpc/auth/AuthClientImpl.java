package top.mingempty.meta.data.infrastructure.rpc.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.mingempty.meta.data.domain.rpc.auth.AuthClient;
import top.mingempty.meta.data.domain.rpc.auth.vo.RoleVo;
import top.mingempty.meta.data.domain.rpc.auth.vo.UserVo;

import java.util.List;

/**
 * 认证服务客户端实现
 *
 * @author zzhao
 */
@Slf4j
@Component
public class
AuthClientImpl implements AuthClient {
    @Override
    public UserVo gainUser(String userCode) {
        UserVo userVo = new UserVo();
        userVo.setUserCode(userCode);
        userVo.setUserName("系统管理员");
        userVo.setRoleVos(gainRole(userCode));
        return userVo;
    }

    @Override
    public List<RoleVo> gainRole(String userCode) {
        RoleVo roleVo = new RoleVo();
        roleVo.setRoleCode("admin");
        roleVo.setRoleName("系统管理员");

        RoleVo roleVo2 = new RoleVo();
        roleVo2.setRoleCode("dict_admin");
        roleVo2.setRoleName("公共码值系统管理员");

        RoleVo roleVo3 = new RoleVo();
        roleVo3.setRoleCode("demo_role");
        roleVo3.setRoleName("测试角色");
        return List.of(roleVo, roleVo2, roleVo3);
    }
}
