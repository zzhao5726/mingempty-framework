package top.mingempty.meta.data.model.vo.out;

import com.mybatisflex.annotation.Column;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.mingempty.meta.data.model.vo.AuthorizationVo;
import top.mingempty.meta.data.model.vo.ExtraFieldVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 字典条目详情信息视图对象
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "字典条目详情信息视图对象", description = "字典条目详情信息视图对象")
public class EntryInfoResultVo extends EntryBaseResultVo {

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
     * 条目授权信息视图对象
     */
    @Schema(title = "条目授权信息视图对象")
    private AuthorizationVo authorizationVo;

    /**
     * 字典扩展字段视图对象
     */
    @Schema(title = "字典扩展字段视图对象")
    private List<ExtraFieldVo> extraFieldVos;
}
