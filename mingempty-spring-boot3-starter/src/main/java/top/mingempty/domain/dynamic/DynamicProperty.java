package top.mingempty.domain.dynamic;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.mingempty.domain.other.GlobalConstant;

import java.util.List;


/**
 * 配置项
 *
 * @param <DP> 当前配置类类型
 * @param <DC> 子配置项类型
 * @param <DI> 实例类型
 * @author zzhao
 */
@Data
public abstract class DynamicProperty<DP extends DynamicProperty<DP, DC, DI>, DC extends DynamicConfig, DI extends DynamicInstance<DI, DP, DC>> {
    /**
     * 是否启用
     */
    @Schema(title = "是否启用")
    private boolean enabled = true;

    /**
     * 默认实例名称
     * <p>
     * 默认def
     */
    @Schema(title = "默认实例名称", description = "默认def")
    private String primary = GlobalConstant.DEFAULT_INSTANCE_NAME;

    /**
     * 是否启用严格模式。
     * </p>
     * 默认不启动。
     * </p>
     * 严格模式下未匹配到实例直接报错, 非严格模式下则使用primary所设置的实例
     */
    @Schema(title = "是否启用严格模式", description = "默认不启动。严格模式下未匹配到实例直接报错, 非严格模式下则使用primary所设置的实例")
    private Boolean lenientFallback = false;

    /**
     * 是否启用其余配置
     */
    @Schema(title = "是否启用其余配置")
    private boolean otherEnabled = false;

    /**
     * 类型
     */
    protected abstract Class<DI> type();

    /**
     * 获取默认配置
     */
    public abstract DC def();

    /**
     * 获取其他配置
     */
    public List<DC> other() {
        return List.of();
    }
}
