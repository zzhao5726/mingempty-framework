package top.mingempty.domain.dynamic;

import cn.hutool.core.lang.Assert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.domain.other.GlobalConstant;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public abstract class AbstractDynamicInstance<T> {

    /**
     * 是否启用
     */
    @Schema(title = "是否启用")
    private boolean enabled = true;

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

    protected AbstractDynamicInstance(T instance) {
        this(instance, true);
    }

    protected AbstractDynamicInstance(T instance, boolean lenientFallback) {
        this.change(GlobalConstant.DEFAULT_INSTANCE_NAME, Assert.notNull(instance, "属性[默认实例]是必须的"));
        this.lenientFallback = lenientFallback;
        //执行后续操作
        doAfterPropertiesSet();
    }

    /**
     * 获取路由
     *
     * @return
     */
    public T determineInstance() {
        String determineCurrentLookupKey = this.finalDetermineCurrentLookupKey();
        T router = gainInstance(determineCurrentLookupKey);
        if (Objects.isNull(router)) {
            String format = String.format("无法确定查找键[%s]的目标路由器", determineCurrentLookupKey);
            log.error(format);
            throw new IllegalStateException(format);
        }

        return router;
    }


    /**
     * 禁用
     */
    public void disable() {
        this.enabled = false;
    }


    /**
     * 禁用
     */
    public void enable() {
        this.enabled = true;
    }


    /**
     * 检索查找实例的方式，如果未找到，则使用默认名称
     */
    protected final String finalDetermineCurrentLookupKey() {
        String determineCurrentLookupKey = determineCurrentLookupKey();
        if (isLenientFallback() && Objects.isNull(determineCurrentLookupKey)) {
            return GlobalConstant.DEFAULT_INSTANCE_NAME;
        }
        return determineCurrentLookupKey;
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
        return gainInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
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
     */
    public void change(String instanceName, T t) {
        this.instances.put(instanceName, t);
    }


    /**
     * 默认的后续初始化
     */
    protected void doAfterPropertiesSet() {
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
