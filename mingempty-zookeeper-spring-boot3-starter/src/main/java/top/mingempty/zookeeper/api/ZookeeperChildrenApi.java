package top.mingempty.zookeeper.api;

import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.Pathable;
import org.apache.zookeeper.data.Stat;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.zookeeper.entity.ZookeeperBackground;
import top.mingempty.zookeeper.entity.ZookeeperWatchable;

import java.util.List;

/**
 * zookeeper子节点节点API
 *
 * @author zzhao
 */
public interface ZookeeperChildrenApi {

    /**
     * 获取默认实例的指定节点的子节点构建器
     *
     * @return
     */
    default GetChildrenBuilder gainChildrenBuilder() {
        return gainChildrenBuilderForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 获取指定实例指定节点的子节点构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    GetChildrenBuilder gainChildrenBuilderForInstance(String instanceName);

    /**
     * 获取默认实例的指定节点的子节点
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param stat       获取当前节点状态
     * @param background 后台执行参数
     * @param watchable  zookeeper监听器
     * @return
     */
    default Pathable<List<String>> gainChildrenPathable(final Stat stat,
                                                        ZookeeperBackground background,
                                                        ZookeeperWatchable watchable) {
        return gainChildrenPathableForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, stat, background, watchable);
    }

    /**
     * 获取指定实例的指定节点的子节点
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param instanceName 实例名称
     * @param stat         获取当前节点状态
     * @param background   后台执行参数
     * @param watchable    zookeeper监听器
     * @return
     */
    Pathable<List<String>> gainChildrenPathableForInstance(String instanceName, final Stat stat,
                                                           ZookeeperBackground background,
                                                           ZookeeperWatchable watchable);


    /**
     * 获取默认实例的指定节点的子节点
     *
     * @param path 节点路径
     * @return
     */
    default List<String> children(String path) {
        return childrenForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path);
    }

    /**
     * 获取指定实例的指定节点的子节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @return
     */
    default List<String> childrenForInstance(String instanceName, String path) {
        return childrenForInstance(instanceName, path, (Stat) null);
    }

    /**
     * 获取默认实例的指定节点的子节点
     *
     * @param path       节点路径
     * @param background 后台执行参数
     * @return
     */
    default List<String> children(String path, ZookeeperBackground background) {
        return childrenForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, background);
    }

    /**
     * 获取指定实例的指定节点的子节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param background   后台执行参数
     * @return
     */
    default List<String> childrenForInstance(String instanceName, String path, ZookeeperBackground background) {
        return childrenForInstance(instanceName, path, background, null);

    }

    /**
     * 获取默认实例的指定节点的子节点
     *
     * @param path      节点路径
     * @param watchable zookeeper监听器
     * @return
     */
    default List<String> children(String path, ZookeeperWatchable watchable) {
        return childrenForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable);
    }

    /**
     * 获取指定实例的指定节点的子节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     * @return
     */
    default List<String> childrenForInstance(String instanceName, String path, ZookeeperWatchable watchable) {
        return childrenForInstance(instanceName, path, (Stat) null, watchable);
    }

    /**
     * 获取默认实例的指定节点的子节点
     *
     * @param path 节点路径
     * @param stat 获取当前节点状态
     * @return
     */
    default List<String> children(String path, final Stat stat) {
        return childrenForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, stat);
    }

    /**
     * 获取指定实例的指定节点的子节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param stat         获取当前节点状态
     * @return
     */
    default List<String> childrenForInstance(String instanceName, String path, final Stat stat) {
        return childrenForInstance(instanceName, path, stat, null);
    }

    /**
     * 获取默认实例的指定节点的子节点
     *
     * @param path       节点路径
     * @param background 后台执行参数
     * @param watchable  zookeeper监听器
     * @return
     */
    default List<String> children(String path, ZookeeperBackground background, ZookeeperWatchable watchable) {
        return childrenForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, background, watchable);
    }

    /**
     * 获取指定实例的指定节点的子节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param background   后台执行参数
     * @param watchable    zookeeper监听器
     * @return
     */
    default List<String> childrenForInstance(String instanceName, String path,
                                             ZookeeperBackground background, ZookeeperWatchable watchable) {
        return childrenForInstance(instanceName, path, null, background, watchable);

    }

    /**
     * 获取默认实例的指定节点的子节点
     *
     * @param path      节点路径
     * @param stat      获取当前节点状态
     * @param watchable zookeeper监听器
     * @return
     */
    default List<String> children(String path, final Stat stat, ZookeeperWatchable watchable) {
        return childrenForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, stat, watchable);
    }

    /**
     * 获取指定实例的指定节点的子节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param stat         获取当前节点状态
     * @param watchable    zookeeper监听器
     * @return
     */
    default List<String> childrenForInstance(String instanceName, String path,
                                             final Stat stat, ZookeeperWatchable watchable) {
        return childrenForInstance(instanceName, path, stat, null, watchable);
    }

    /**
     * 获取默认实例的指定节点的子节点
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param path       节点路径
     * @param stat       获取当前节点状态
     * @param background 后台执行参数
     * @param watchable  zookeeper监听器
     * @return
     */
    default List<String> children(String path, final Stat stat,
                                  ZookeeperBackground background, ZookeeperWatchable watchable) {
        return childrenForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, stat, background, watchable);
    }

    /**
     * 获取指定实例的指定节点的子节点
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param stat         获取当前节点状态
     * @param background   后台执行参数
     * @param watchable    zookeeper监听器
     * @return
     */
    List<String> childrenForInstance(String instanceName, String path, final Stat stat,
                                     ZookeeperBackground background, ZookeeperWatchable watchable);
}
