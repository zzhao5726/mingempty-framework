package top.mingempty.meta.data.domain.rpc.auth;

import cn.hutool.core.collection.CollUtil;
import top.mingempty.meta.data.domain.rpc.auth.vo.RoleVo;
import top.mingempty.meta.data.domain.rpc.auth.vo.UserVo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限客户端
 *
 * @author zzhao
 */
public interface AuthClient {

    default String gainUserCode() {
        // TODO 获取当前用户编码
        return "SYS";
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    default UserVo gainUser() {
        return gainUser(gainUserCode());
    }

    /**
     * 获取用户信息
     *
     * @param userCode 用户编号
     * @return 用户信息
     */
    UserVo gainUser(String userCode);

    default Set<String> gainRoleCode() {
        return gainRoleCode(gainUserCode());
    }


    /**
     * 获取角色信息
     *
     * @return 角色信息
     */
    default List<RoleVo> gainRole() {
        return gainRole(gainUserCode());
    }

    default Set<String> gainRoleCode(String userCode) {
        List<RoleVo> roleVos = gainRole(userCode);
        if (CollUtil.isEmpty(roleVos)) {
            return Set.of();
        }
        return roleVos.stream()
                .map(RoleVo::getRoleCode)
                .collect(Collectors.toSet());
    }


    /**
     * 获取角色信息
     *
     * @param userCode 用户编号
     * @return 角色信息
     */
    List<RoleVo> gainRole(String userCode);

}

