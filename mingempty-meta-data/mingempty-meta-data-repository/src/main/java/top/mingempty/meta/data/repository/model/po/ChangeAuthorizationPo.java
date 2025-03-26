package top.mingempty.meta.data.repository.model.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.mingempty.domain.base.BaseDeletePoModel;
import top.mingempty.meta.data.domain.enums.AuthorizationTypeEnum;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 条目授权变化流水表 实体类。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "条目授权变化流水表")
@Table("t_meta_data_change_authorization")
public class ChangeAuthorizationPo implements BaseDeletePoModel {

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
     * 条目版本（默认1）
     */
    @Column("entry_version")
    @Schema(title = "条目版本（默认1）")
    private Long entryVersion;

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
    private AuthorizationTypeEnum authorizationType;

    /**
     * 授权编码
     */
    @Column("authorization_code")
    @Schema(title = "授权编码")
    private String authorizationCode;

    /**
     * 是否已逻辑删除
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目：zero_or_one)
     * </pre>
     */
    @Column(value = "delete_status")
    @Schema(title = "是否已逻辑删除", description = "0：否" +
			"1：是" +
			"(同字典条目：zero_or_one)")
    private String deleteStatus;

    /**
     * 删除时间
     */
    @Column("delete_time")
    @Schema(title = "删除时间")
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @Column("delete_operator")
    @Schema(title = "删除人")
    private String deleteOperator;

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
