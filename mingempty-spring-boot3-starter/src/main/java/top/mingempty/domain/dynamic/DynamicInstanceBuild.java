package top.mingempty.domain.dynamic;

/**
 * 实例构建器
 *
 * @param <DP> 当前配置类类型
 * @param <DC> 子配置项类型
 * @param <DI> 实例类型
 * @author zzhao
 */
public interface DynamicInstanceBuild<DP extends DynamicProperty<DP, DC, DI>, DC extends DynamicConfig, DI extends DynamicInstance<DI, DP, DC>> {

    /**
     * 类型
     */
    Class<DynamicInstance<DI, DP, DC>> type();

    /**
     * 构建
     */
    DI build(DP dynamicProperty);
}
