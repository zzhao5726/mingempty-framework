package top.mingempty.meta.data.domain.rpc.auth.vo;

import lombok.Data;

import java.util.List;

/**
 * 用户信息
 */
@Data
public class UserVo {

    private String userCode;

    private String userName;

    private List<RoleVo> roleVos;

}
