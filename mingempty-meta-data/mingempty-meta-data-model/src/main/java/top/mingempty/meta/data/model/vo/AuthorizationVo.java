package top.mingempty.meta.data.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Collection;


/**
 * * 条目授权信息视图对象
 * *
 * * @author zzhao
 * * @since 2025-02-10 23:12:12
 */
@Data
@Schema(title = "条目授权信息视图对象", description = "条目授权信息视图对象")
public class AuthorizationVo {

    /**
     * 条目编号
     */
    @Schema(title = "条目编号")
    private String entryCode;

    /**
     * 角色授权列表
     */
    @Schema(title = "角色授权列表")
    private Collection<String> roleAuthorizations;

    /**
     * 用户授权列表
     */
    @Schema(title = "用户授权列表")
    private Collection<String> userAuthorizations;
}
