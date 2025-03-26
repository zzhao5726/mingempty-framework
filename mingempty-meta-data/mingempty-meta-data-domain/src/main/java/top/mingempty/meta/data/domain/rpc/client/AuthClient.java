package top.mingempty.meta.data.domain.rpc.client;

import top.mingempty.meta.data.domain.rpc.vo.UserVo;

/**
 * 权限客户端
 *
 * @author zzhao
 */
public interface AuthClient {

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    default UserVo gainUser() {
        // TODO 获取当前用户编码
        return gainUser("");
    }

    /**
     * 获取用户信息
     *
     * @param userCode 用户编号
     * @return 用户信息
     */
    UserVo gainUser(String userCode);
}
