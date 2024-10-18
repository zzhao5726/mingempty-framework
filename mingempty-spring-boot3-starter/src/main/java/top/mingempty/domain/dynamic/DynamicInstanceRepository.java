package top.mingempty.domain.dynamic;


import cn.hutool.core.lang.Assert;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实例仓库
 */
public class DynamicInstanceRepository {

    /**
     * 实例构建器
     */
    @Schema(title = "实例构建器")
    private static final Map<Class<? extends DynamicInstance<?, ?, ?>>, DynamicInstanceBuild<?, ?, ?>> PROPERTIES_BUILDS = new ConcurrentHashMap<>(2);

    /**
     * 系统实例集
     */
    @Schema(title = "系统实例集")
    private static final Map<Class<? extends DynamicInstance<?, ?, ?>>, DynamicInstance<?, ?, ?>> ME_BEANS = new ConcurrentHashMap<>(2);

    /**
     * 添加实例
     *
     * @param type     实例类型
     * @param dynamicInstance 实例
     */
    public static void addInstance(Class<? extends DynamicInstance<?, ?, ?>> type, DynamicInstance<?, ?, ?> dynamicInstance) {
        ME_BEANS.put(Assert.notNull(type, "属性[实例类型]是必须的"),
                Assert.notNull(dynamicInstance, "属性[实例]是必须的"));
    }

    /**
     * 启用实例
     *
     * @param type 实例类型
     */
    public static void enableInstance(Class<? extends DynamicInstance<?, ?, ?>> type) {
        DynamicInstance dynamicInstance = ME_BEANS.get(Assert.notNull(type, "属性[实例类型]是必须的"));
        Assert.notNull(dynamicInstance, "类型[{}]对应实例不存在", type).enable();
    }

    /**
     * 禁用实例
     *
     * @param type 实例类型
     */
    public static void disableInstance(Class<? extends DynamicInstance<?, ?, ?>> type) {
        DynamicInstance dynamicInstance = ME_BEANS.get(Assert.notNull(type, "属性[实例类型]是必须的"));
        Assert.notNull(dynamicInstance, "类型[{}]对应实例不存在", type).disable();
    }

    /**
     * 判断实例是否存在
     *
     * @param type 实例类型
     * @return
     */
    public static boolean existInstance(Class<? extends DynamicInstance<?, ?, ?>> type) {
        return ME_BEANS.containsKey(Assert.notNull(type, "属性[实例类型]是必须的"));
    }

    /**
     * 添加一个实例构建器
     *
     * @param type          实例类型
     * @param dynamicInstanceBuild 实例构建器
     */
    public static void addPropertiesBuild(Class<? extends DynamicInstance<?, ?, ?>> type, DynamicInstanceBuild<?, ?, ?> dynamicInstanceBuild) {
        PROPERTIES_BUILDS.put(Assert.notNull(type, "属性[实例类型]是必须的"),
                Assert.notNull(dynamicInstanceBuild, "属性[实例构建器]是必须的"));
    }

    /**
     * 判断实例构建器是否存在
     *
     * @param type 实例类型
     * @return
     */
    public static boolean existPropertiesBuild(Class<? extends DynamicInstance<?, ?, ?>> type) {
        return PROPERTIES_BUILDS.containsKey(Assert.notNull(type, "属性[实例构建器]是必须的"));
    }

}
