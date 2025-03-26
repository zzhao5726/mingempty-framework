package top.mingempty.meta.data.facade.domain.dict;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.other.DatePattern;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

/**
 * 展示条目详情的DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(title = "展示条目详情的DTO")
public class EntryDetailsDto {

    /**
     * 条目编号
     */
    @Schema(title = "条目编号")
    private String entryCode;

    /**
     * 条目名称
     */
    @Schema(title = "条目名称")
    private String entryName;

    /**
     * 条目类型
     * <pre class="code">
     * 1：普通字典
     * 2：树形字典
     * (同字典条目编码：dict_entry_type)
     * </pre>
     */
    @Schema(title = "条目类型", description = "1：普通字典" +
            "2：树形字典" +
            "(同字典条目编码：dict_entry_type)")
    private String entryType;

    /**
     * 是否分表
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目编码：zero_or_one)
     * </pre>
     */
    @Schema(title = "是否分表", description = "0：否" +
            "1：是" +
            "(同字典条目编码：zero_or_one)")
    private String entrySharding;

    /**
     * 条目排序（默认0）
     */
    @Schema(title = "条目排序（默认0）")
    private BigDecimal sort;

    /**
     * 条目数据逻辑删除状态
     */
    @Schema(title = "是否已逻辑删除", description = "0：否" +
            "1：是" +
            "(同字典条目：zero_or_one)")
    private String deleteStatus;

    /**
     * 删除用户
     */
    @Schema(title = "删除用户")
    private String deleteOperator;

    /**
     * 删除时间
     */
    @Schema(title = "删除时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime deleteTime;

    /**
     * 条目权限集
     * <p>
     * key为授权授权类型, value为授权编码
     * <pre class="code">
     * 1：角色编码
     * 2：用户编码
     * (含义同条目：entry_authorization_type)
     * </pre>
     */
    @Schema(title = "授权类型", description = "key为授权授权类型, value为授权编码。" +
            " 1：角色编码" +
            " 2：用户编码" +
            " (含义同条目：entry_authorization_type)")
    private Map<String, Collection<String>> authorizations;


    /**
     * 条目扩展字段集
     */
    @Schema(title = "条目扩展字段集")
    private Collection<ExtraFieldDetailsDto> extraFields;

    /**
     * 条目操作历史
     */
    @Schema(title = "条目操作历史")
    private Collection<OperationHistoryDetailsDto> operationHistorys;

}
