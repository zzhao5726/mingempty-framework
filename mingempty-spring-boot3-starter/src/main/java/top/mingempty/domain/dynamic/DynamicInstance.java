package top.mingempty.domain.dynamic;


/**
 * 配置类标识
 *
 *
 * @param <DI> 实例类型
 * @param <DP> 当前配置类类型
 * @param <DC> 子配置项类型
 * @author zzhao
 */
public interface DynamicInstance<DI extends DynamicInstance<DI, DP, DC>, DP extends DynamicProperty<DP, DC, DI>, DC extends DynamicConfig> {

    /**
     * 是否启用
     */
    boolean isEnabled();

    /**
     * 启用
     */
    void enable();

    /**
     * 禁用
     */
    void disable();

    /**
     * 当前实例对应配置文件
     */
    DP property();
}
