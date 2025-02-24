package top.mingempty.meta.data.model.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.mingempty.domain.base.BasePoModel;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 条目授权表 实体类。
 *
 * @author zzhao
 * @since 2025-03-20 22:58:54
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "条目授权表")
@Table("t_meta_data_authorization")
public class AuthorizationPo implements BasePoModel {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 条目授权ID
     */
    @Id
    @Column("authorization_id")
    @Schema(title = "条目授权ID")
    private Long authorizationId;

    /**
     * 条目编号
     */
    @Column("entry_code")
    @Schema(title = "条目编号")
    private String entryCode;

    /**
     * 授权类型
     * <pre class="code">
     * 1：角色编码
     * 2：用户编码
     * (含义同条目：entry_authorization_type)
     * </pre>
     */
    @Column("authorization_type")
    @Schema(title = "授权类型", description = "1：角色编码" +
			"2：用户编码" +
			"(含义同条目：entry_authorization_type)")
    private String authorizationType;

    /**
     * 授权编码
     */
    @Column("authorization_code")
    @Schema(title = "授权编码")
    private String authorizationCode;

    /**
     * 创建时间
     */
    @Column("create_time")
    @Schema(title = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column("update_time")
    @Schema(title = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @Column("create_operator")
    @Schema(title = "创建人")
    private String createOperator;

    /**
     * 更新人
     */
    @Column("update_operator")
    @Schema(title = "更新人")
    private String updateOperator;

}
