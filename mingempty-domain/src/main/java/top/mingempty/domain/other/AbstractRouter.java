package top.mingempty.domain.other;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 路由抽象类
 *
 * @author zzhao
 */
@Slf4j
@Getter
public abstract class AbstractRouter<T> {

    /**
     * 指定在找不到当前查找键的特定路由时，是否对默认路由应用宽松回退。
     */
    @Setter
    @Schema(title = "指定在找不到当前查找键的特定路由时，是否对默认路由应用宽松回退。")
    private boolean lenientFallback = true;

    /**
     * 最终路由目标集
     */
    @Schema(title = "最终路由目标集")
    private final Map<String, T> resolvedRouter;

    /**
     * 最终默认路由
     */
    @Schema(title = "最终默认路由")
    private final T resolvedDefaultRouter;

    /**
     * 最终默认路由名称
     */
    @Schema(title = "最终默认路由名称")
    private final String resolvedDefaultName;

    public AbstractRouter(String defaultTargetName, Map<String, T> targetRouter) {
        this(defaultTargetName, targetRouter, true);
    }

    public AbstractRouter(String defaultTargetName, Map<String, T> targetRouter, boolean lenientFallback) {
        if (Objects.isNull(defaultTargetName)) {
            log.error("属性[默认路由名称]是必须的");
            throw new IllegalArgumentException("属性[默认路由名称]是必须的");
        }

        if (Objects.isNull(targetRouter)) {
            log.error("属性[默认路由目标集]是必须的");
            throw new IllegalArgumentException("属性[默认路由目标集]是必须的");
        }

        this.lenientFallback = lenientFallback;
        this.resolvedDefaultName = defaultTargetName;
        Map<String, T> newTargetRouterMap = new ConcurrentHashMap<>(targetRouter.size());
        targetRouter.forEach((key, value) -> newTargetRouterMap.put(resolveSpecifiedLookupKey(key), value));
        this.resolvedRouter = newTargetRouterMap;
        if (Objects.isNull(getResolvedRouter(defaultTargetName))) {
            log.error("属性[默认路由名称]对应的路由是必须的");
            throw new IllegalArgumentException("属性[默认路由名称]对应的路由是必须的");
        }
        this.resolvedDefaultRouter = getResolvedRouter(defaultTargetName);

        //执行后续操作
        doAfterPropertiesSet();
    }

    /**
     * 获取路由
     *
     * @return
     */
    public T determineTargetRouter() {
        if (Objects.isNull(this.resolvedRouter)) {
            throw new IllegalStateException("路由未被初始化");
        }
        String determineCurrentLookupKey = this.finalDetermineCurrentLookupKey();
        T router = getResolvedRouter(determineCurrentLookupKey);
        if (Objects.isNull(router)) {
            String format = String.format("无法确定查找键[%s]的目标路由器", determineCurrentLookupKey);
            log.error(format);
            throw new IllegalStateException(format);
        }

        return router;
    }

    /**
     * 将目标映射中指定的给定查找键对象解析为要用于与 匹配 current lookup key的实际查找键。
     *
     * @param lookupKey 用户指定的查找键对象
     * @return 匹配所需的查找键
     */
    protected String resolveSpecifiedLookupKey(String lookupKey) {
        return lookupKey;
    }


    /**
     * 默认的后续初始化
     */
    protected void doAfterPropertiesSet() {
    }

    /**
     * 检索查找路由方式
     */
    protected abstract String determineCurrentLookupKey();


    /**
     * 检索查找路由方式，如果未找到，则使用默认名称
     */
    protected final String finalDetermineCurrentLookupKey() {
        String determineCurrentLookupKey = determineCurrentLookupKey();
        if (isLenientFallback() && Objects.isNull(determineCurrentLookupKey)) {
            return resolvedDefaultName;
        }
        return determineCurrentLookupKey;
    }


    /**
     * 通过路由名称获取路由
     *
     * @param routerName 路由名称
     * @return 路由
     */
    public T getResolvedRouter(String routerName) {
        if (Objects.isNull(this.resolvedRouter)) {
            throw new IllegalStateException("路由未被初始化");
        }
        return this.resolvedRouter.get(routerName);
    }


    /**
     * 通过路由名称获取路由
     *
     * @return 路由集合
     */
    public List<T> getResolvedRouters() {
        if (Objects.isNull(this.resolvedRouter)) {
            throw new IllegalStateException("路由未被初始化");
        }
        return new CopyOnWriteArrayList<>(this.resolvedRouter.values());
    }


    /**
     * 移除一个路由
     *
     * @param key 路由key
     */
    public void remove(String key) {
        this.resolvedRouter.remove(resolveSpecifiedLookupKey(key));
    }

    /**
     * 添加一个路由
     *
     * @param key 路由key
     */
    public void add(String key, T t) {
        this.resolvedRouter.put(resolveSpecifiedLookupKey(key), t);
    }


}
