package top.mingempty.mybatis.plus.extension.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.mingempty.domain.base.BaseDeletePoModel;

import java.time.LocalDateTime;

/**
 * 数据库实体逻辑删除基础类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "数据库实体逻辑删除基础类", description = "数据库实体逻辑删除基础类" )
public abstract class MpDeleteBasePoModel extends MpBasePoModel implements BaseDeletePoModel {

    /**
     * 是否已逻辑删除
     * 0：否
     * 1：是
     * (同字典条目：zero_or_one)
     */
    @Schema(title = "是否已删除", description = "是否已逻辑删除" +
            "0：否" +
            "1：是" +
            "(同字典条目：zero_or_one)" )
    @TableField(value = "delete_status" )
    private String deleteStatus;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    @TableField(value = "delete_time" )
    private LocalDateTime deleteTime;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    @TableField(value = "delete_operator" )
    private String deleteOperator;
}
