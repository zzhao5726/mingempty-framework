package top.mingempty.domain.dynamic;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.commons.exception.BaseBizException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 实例抽象类
 *
 * @author zzhao
 */

/**
 * 实例抽象类
 *
 * @param <T> 实例类型
 * @author zzhao
 */
@Slf4j
@Getter
public abstract class AbstractDynamicInstance<T, DI extends DynamicInstance<DI, DP, DC>, DP extends DynamicProperty<DP, DC, DI>, DC extends DynamicConfig>
        implements DynamicInstance<DI, DP, DC> {

    /**
     * 是否启用
     */
    @Schema(title = "是否启用")
    private boolean enabled = true;

    /**
     * 配置文件
     */
    @Schema(title = "配置文件")
    private DP property;

    /**
     * 全量实例集
     */
    @Schema(title = "全量实例集")
    private final Map<String, T> instances = new ConcurrentHashMap<>(2);

    /**
     * 指定在找不到当前查找键的特定路由时，是否对默认路由应用宽松回退。
     */
    @Setter
    @Schema(title = "指定在找不到当前查找键的特定路由时，是否对默认路由应用宽松回退。")
    private boolean lenientFallback;

    protected AbstractDynamicInstance(DP property, T instance) {
        this(property, instance, true);
    }

    protected AbstractDynamicInstance(DP property, T instance, boolean lenientFallback) {
        this.put(property.getPrimary(), Assert.notNull(instance, "属性[默认实例]是必须的"));
        this.lenientFallback = property.getLenientFallback();
        this.property = property;
    }

    /**
     * 获取路由
     *
     * @return
     */
    public T determineInstance() {
        String determineCurrentLookupKey = determineCurrentLookupKey();
        if (StrUtil.isEmpty(determineCurrentLookupKey)) {
            if (!isLenientFallback()) {
                return this.gainInstance();
            }
            throw new BaseBizException("di-0000000001");
        }
        T router = gainInstance(determineCurrentLookupKey);
        if (ObjUtil.isNotEmpty(router)) {
            return router;
        }
        if (!isLenientFallback()) {
            return this.gainInstance();
        }
        throw new BaseBizException("di-0000000002", determineCurrentLookupKey);
    }


    /**
     * 禁用
     */
    @Override
    public void disable() {
        this.enabled = false;
    }


    /**
     * 禁用
     */
    @Override
    public void enable() {
        this.enabled = true;
    }

    /**
     * 当前实例对应配置文件
     */
    @Override
    public DP property() {
        return this.property;
    }


    /**
     * 判断实例是否存在
     *
     * @param instanceName 实例名称
     */
    public boolean exist(String instanceName) {
        return this.instances.containsKey(instanceName);
    }


    /**
     * 获取默认实例
     *
     * @return 实例
     */
    public T gainInstance() {
        return gainInstance(this.getProperty().getPrimary());
    }


    /**
     * 通过实例名称获取实例
     *
     * @param instanceName 实例名称
     * @return 实例
     */
    public T gainInstance(String instanceName) {
        return this.instances.get(instanceName);
    }


    /**
     * 获取实例列表
     *
     * @return 路由集合
     */
    public List<T> gainInstances() {
        return this.instances.entrySet().stream().map(Map.Entry::getValue).toList();
    }


    /**
     * 获取实例名称列表
     *
     * @return 路由集合
     */
    public Set<String> gainInstanceNames() {
        return this.instances.keySet().stream().collect(Collectors.toUnmodifiableSet());
    }


    /**
     * 移除一个路由
     *
     * @param instanceName 实例名称
     */
    public void remove(String instanceName) {
        this.instances.remove(instanceName);
    }

    /**
     * 添加一个路由
     *
     * @param instanceName 实例名称
     * @param t            实例
     */
    public void put(String instanceName, T t) {
        this.instances.put(instanceName, t);
    }

    /**
     * 添加多个个路由
     *
     * @param tMap 实例集合
     */
    public void putAll(Map<String, T> tMap) {
        this.instances.putAll(tMap);
    }


    /**
     * 默认的后续初始化
     */
    public void doAfterPropertiesSet() {
    }

    /**
     * 检索查找实例的方式
     */
    protected abstract String determineCurrentLookupKey();

    /**
     * 当前实例集类型
     *
     * @return
     */
    protected abstract Class<T> type();
}
