package top.mingempty.mybatis.plus.extension.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.mingempty.domain.base.BasePoModel;

import java.time.LocalDateTime;

/**
 * 数据库实体基础类
 */
@Data
@Schema(title = "数据库实体基础类", description = "数据库实体基础类")
public abstract class MpBasePoModel implements BasePoModel {

    /**
     *创建时间
     */
    @Schema(description = "创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     *修改时间
     */
    @Schema(description = "修改时间")
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     *创建人
     */
    @Schema(description = "创建人")
    @TableField(value = "create_operator")
    private String createOperator;

    /**
     *修改人
     */
    @Schema(description = "修改人")
    @TableField(value = "update_operator")
    private String updateOperator;
}
