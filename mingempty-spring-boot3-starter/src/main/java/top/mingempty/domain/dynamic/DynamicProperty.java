package top.mingempty.domain.dynamic;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;


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
     * 是否启用其余配置
     */
    @Schema(title = "是否启用其余配置")
    private boolean otherEnabled = false;

    /**
     * 类型
     */
    protected abstract Class<DynamicInstance<DI, DP, DC>> type();

    /**
     * 获取默认配置
     */
    public abstract DC def();

    /**
     * 获取其他配置
     */
    public Map<String, DC> other() {
        return Map.of();
    }
}
