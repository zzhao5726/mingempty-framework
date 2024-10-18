package top.mingempty.domain.dynamic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.mingempty.domain.other.GlobalConstant;

/**
 * 子配置项
 *
 * @author zzhao
 */
@Data
public class DynamicConfig {

    /**
     * 是否启用
     */
    @Schema(title = "是否启用")
    private boolean enabled = true;

    /**
     * 实例名称
     */
    @Schema(title = "实例名称")
    private String name = GlobalConstant.DEFAULT_INSTANCE_NAME;


}
