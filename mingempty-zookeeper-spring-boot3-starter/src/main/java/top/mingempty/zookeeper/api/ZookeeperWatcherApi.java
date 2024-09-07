package top.mingempty.zookeeper.api;

import jakarta.validation.constraints.NotNull;
import org.apache.curator.framework.api.Pathable;
import org.apache.curator.framework.api.WatchesBuilder;
import org.apache.zookeeper.AddWatchMode;
import org.apache.zookeeper.Watcher;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.zookeeper.entity.ZookeeperBackground;
import top.mingempty.zookeeper.entity.ZookeeperWatchable;

/**
 * zookeeper监视节点API
 *
 * @author zzhao
 */
public interface ZookeeperWatcherApi {

    /**
     * 获取默认实例的节点监视构建器
     *
     * @return
     */
    default WatchesBuilder gainWatchesBuilder() {
        return gainWatchesBuilderForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 获取指定实例节点监视构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    WatchesBuilder gainWatchesBuilderForInstance(String instanceName);

    /**
     * 获取移除默认实例内的监听器的Pathable
     *
     * @param watchable   zookeeper监听器
     *                    当Watcher和CuratorWatcher值都为空时，移除全部
     * @param watcherType 监听器类型
     * @param locally     是否仅删除本地监听器
     *                    当无法连接到zk服务端时
     * @param quietly     是否静默
     * @param guaranteed  处理边界问题
     *                    操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     *                    当guaranteed为true时，参数watcherType locally quietly不生效
     * @param background  后台执行参数
     * @return
     */
    default Pathable<Void> gainRemoveWatchesPathable(@NotNull ZookeeperWatchable watchable,
                                                     Watcher.WatcherType watcherType, boolean locally, boolean quietly,
                                                     boolean guaranteed, ZookeeperBackground background) {
        return gainRemoveWatchesPathableForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, watchable, watcherType,
                locally, quietly, guaranteed, background);
    }

    /**
     * 获取移除指定实例内的监听器的Pathable
     *
     * @param instanceName 实例名称
     * @param watchable    zookeeper监听器
     *                     当Watcher和CuratorWatcher值都为空时，移除全部
     * @param watcherType  监听器类型
     * @param locally      是否仅删除本地监听器
     *                     当无法连接到zk服务端时
     * @param quietly      是否静默
     * @param guaranteed   处理边界问题
     *                     操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     *                     当guaranteed为true时，参数watcherType locally quietly不生效
     * @param background   后台执行参数
     * @return
     */
    Pathable<Void> gainRemoveWatchesPathableForInstance(String instanceName, @NotNull ZookeeperWatchable watchable,
                                                        Watcher.WatcherType watcherType,
                                                        boolean locally, boolean quietly,
                                                        boolean guaranteed, ZookeeperBackground background);


    /**
     * 移除默认实例内指定路径的监听器
     *
     * @param path      节点路径
     * @param watchable zookeeper监听器
     *                  当Watcher和CuratorWatcher值都为空时，移除全部
     */
    default void watchesRemove(String path, ZookeeperWatchable watchable) {
        watchesRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable);
    }

    /**
     * 移除指定实例内指定路径的监听器
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     *                     当Watcher和CuratorWatcher值都为空时，移除全部
     */
    default void watchesRemoveForInstance(String instanceName, String path, ZookeeperWatchable watchable) {
        watchesRemoveForInstance(instanceName, path, watchable, false);
    }

    /**
     * 移除默认实例内指定路径的监听器
     *
     * @param path        节点路径
     * @param watchable   zookeeper监听器
     *                    当Watcher和CuratorWatcher值都为空时，移除全部
     * @param watcherType 监听器类型
     */
    default void watchesRemove(String path, ZookeeperWatchable watchable, Watcher.WatcherType watcherType) {
        watchesRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable, watcherType);
    }

    /**
     * 移除指定实例内指定路径的监听器
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     *                     当Watcher和CuratorWatcher值都为空时，移除全部
     * @param watcherType  监听器类型
     */
    default void watchesRemoveForInstance(String instanceName, String path, ZookeeperWatchable watchable,
                                          Watcher.WatcherType watcherType) {
        watchesRemoveForInstance(instanceName, path, watchable, watcherType, false);
    }

    /**
     * 移除默认实例内指定路径的监听器
     *
     * @param path      节点路径
     * @param watchable zookeeper监听器
     *                  当Watcher和CuratorWatcher值都为空时，移除全部
     * @param locally   是否仅删除本地监听器
     *                  当无法连接到zk服务端时
     */
    default void watchesRemove(String path, ZookeeperWatchable watchable, boolean locally) {
        watchesRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable);
    }

    /**
     * 移除指定实例内指定路径的监听器
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     *                     当Watcher和CuratorWatcher值都为空时，移除全部
     * @param locally      是否仅删除本地监听器
     *                     当无法连接到zk服务端时
     */
    default void watchesRemoveForInstance(String instanceName, String path,
                                          ZookeeperWatchable watchable, boolean locally) {
        watchesRemoveForInstance(instanceName, path, watchable, locally, null);
    }

    /**
     * 移除默认实例内指定路径的监听器
     *
     * @param path       节点路径
     * @param watchable  zookeeper监听器
     *                   当Watcher和CuratorWatcher值都为空时，移除全部
     * @param background 后台执行参数
     */
    default void watchesRemove(String path, ZookeeperWatchable watchable, ZookeeperBackground background) {
        watchesRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable, background);
    }

    /**
     * 移除指定实例内指定路径的监听器
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     *                     当Watcher和CuratorWatcher值都为空时，移除全部
     * @param background   后台执行参数
     */
    default void watchesRemoveForInstance(String instanceName, String path,
                                          ZookeeperWatchable watchable, ZookeeperBackground background) {
        watchesRemoveForInstance(instanceName, path, watchable, false, background);
    }

    /**
     * 移除默认实例内指定路径的监听器
     *
     * @param path        节点路径
     * @param watchable   zookeeper监听器
     *                    当Watcher和CuratorWatcher值都为空时，移除全部
     * @param watcherType 监听器类型
     * @param locally     是否仅删除本地监听器
     *                    当无法连接到zk服务端时
     */
    default void watchesRemove(String path, ZookeeperWatchable watchable,
                               Watcher.WatcherType watcherType, boolean locally) {
        watchesRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable, watcherType, locally);
    }

    /**
     * 移除指定实例内指定路径的监听器
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     *                     当Watcher和CuratorWatcher值都为空时，移除全部
     * @param watcherType  监听器类型
     * @param locally      是否仅删除本地监听器
     *                     当无法连接到zk服务端时
     */
    default void watchesRemoveForInstance(String instanceName, String path, ZookeeperWatchable watchable,
                                          Watcher.WatcherType watcherType, boolean locally) {
        watchesRemoveForInstance(instanceName, path, watchable, watcherType, locally, false, null);
    }

    /**
     * 移除默认实例内指定路径的监听器
     *
     * @param path        节点路径
     * @param watchable   zookeeper监听器
     *                    当Watcher和CuratorWatcher值都为空时，移除全部
     * @param watcherType 监听器类型
     * @param background  后台执行参数
     */
    default void watchesRemove(String path, ZookeeperWatchable watchable,
                               Watcher.WatcherType watcherType, ZookeeperBackground background) {
        watchesRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable, watcherType, background);
    }

    /**
     * 移除指定实例内指定路径的监听器
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     *                     当Watcher和CuratorWatcher值都为空时，移除全部
     * @param watcherType  监听器类型
     * @param background   后台执行参数
     */
    default void watchesRemoveForInstance(String instanceName, String path, ZookeeperWatchable watchable,
                                          Watcher.WatcherType watcherType, ZookeeperBackground background) {
        watchesRemoveForInstance(instanceName, path, watchable, watcherType, false, false, background);
    }


    /**
     * 移除默认实例内指定路径的监听器
     *
     * @param path       节点路径
     * @param watchable  zookeeper监听器
     *                   当Watcher和CuratorWatcher值都为空时，移除全部
     * @param locally    是否仅删除本地监听器
     *                   当无法连接到zk服务端时
     * @param quietly    是否静默
     * @param background 后台执行参数
     */
    default void watchesRemove(String path, ZookeeperWatchable watchable,
                               boolean locally, ZookeeperBackground background, boolean quietly) {
        watchesRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable, locally, background, quietly);
    }

    /**
     * 移除指定实例内指定路径的监听器
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     *                     当Watcher和CuratorWatcher值都为空时，移除全部
     * @param locally      是否仅删除本地监听器
     *                     当无法连接到zk服务端时
     * @param quietly      是否静默
     * @param background   后台执行参数
     */
    default void watchesRemoveForInstance(String instanceName, String path, ZookeeperWatchable watchable,
                                          boolean locally, ZookeeperBackground background, boolean quietly) {
        watchesRemoveForInstance(instanceName, path, watchable, null, locally, quietly, background);
    }

    /**
     * 移除默认实例内指定路径的监听器
     *
     * @param path        节点路径
     * @param watchable   zookeeper监听器
     *                    当Watcher和CuratorWatcher值都为空时，移除全部
     * @param watcherType 监听器类型
     * @param locally     是否仅删除本地监听器
     *                    当无法连接到zk服务端时
     * @param quietly     是否静默
     * @param background  后台执行参数
     */
    default void watchesRemove(String path, ZookeeperWatchable watchable, Watcher.WatcherType watcherType,
                               boolean locally, boolean quietly, ZookeeperBackground background) {
        watchesRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable, watcherType,
                locally, quietly, background);
    }

    /**
     * 移除指定实例内指定路径的监听器
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     *                     当Watcher和CuratorWatcher值都为空时，移除全部
     * @param watcherType  监听器类型
     * @param locally      是否仅删除本地监听器
     *                     当无法连接到zk服务端时
     * @param quietly      是否静默
     * @param background   后台执行参数
     */
    default void watchesRemoveForInstance(String instanceName, String path, ZookeeperWatchable watchable,
                                          Watcher.WatcherType watcherType, boolean locally, boolean quietly,
                                          ZookeeperBackground background) {
        watchesRemoveForInstance(instanceName, path, watchable, watcherType, locally, quietly, false, background);

    }

    /**
     * 移除默认实例内指定路径的监听器
     *
     * @param path       节点路径
     * @param watchable  zookeeper监听器
     *                   当Watcher和CuratorWatcher值都为空时，移除全部
     * @param guaranteed 处理边界问题
     *                   操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     *                   当guaranteed为true时，参数watcherType locally quietly不生效
     * @param background 后台执行参数
     */
    default void watchesRemove(String path, ZookeeperWatchable watchable,
                               boolean guaranteed, ZookeeperBackground background) {
        watchesRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable, guaranteed, background);
    }

    /**
     * 移除指定实例内指定路径的监听器
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     *                     当Watcher和CuratorWatcher值都为空时，移除全部
     * @param guaranteed   处理边界问题
     *                     操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     *                     当guaranteed为true时，参数watcherType locally quietly不生效
     * @param background   后台执行参数
     */
    default void watchesRemoveForInstance(String instanceName, String path, ZookeeperWatchable watchable,
                                          boolean guaranteed, ZookeeperBackground background) {
        watchesRemoveForInstance(instanceName, path, watchable, null, false, false,
                guaranteed, background);

    }

    /**
     * 移除默认实例内指定路径的监听器
     *
     * @param path       节点路径
     * @param watchable  zookeeper监听器
     *                   当Watcher和CuratorWatcher值都为空时，移除全部
     * @param guaranteed 处理边界问题
     *                   操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     *                   当guaranteed为true时，参数watcherType locally quietly不生效
     * @param quietly    是否静默
     * @param background 后台执行参数
     */
    default void watchesRemove(String path, ZookeeperWatchable watchable,
                               boolean guaranteed, boolean quietly, ZookeeperBackground background) {
        watchesRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable, guaranteed, quietly, background);
    }

    /**
     * 移除指定实例内指定路径的监听器
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     *                     当Watcher和CuratorWatcher值都为空时，移除全部
     * @param guaranteed   处理边界问题
     *                     操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     *                     当guaranteed为true时，参数watcherType locally quietly不生效
     * @param quietly      是否静默
     * @param background   后台执行参数
     */
    default void watchesRemoveForInstance(String instanceName, String path, ZookeeperWatchable watchable,
                                          boolean guaranteed, boolean quietly, ZookeeperBackground background) {
        watchesRemoveForInstance(instanceName, path, watchable, null, false, quietly,
                guaranteed, background);
    }

    /**
     * 移除默认实例内指定路径的监听器
     *
     * @param path        节点路径
     * @param watchable   zookeeper监听器
     *                    当Watcher和CuratorWatcher值都为空时，移除全部
     * @param watcherType 监听器类型
     * @param locally     是否仅删除本地监听器
     *                    当无法连接到zk服务端时
     * @param quietly     是否静默
     * @param guaranteed  处理边界问题
     *                    操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     *                    当guaranteed为true时，参数watcherType locally quietly不生效
     * @param background  后台执行参数
     */
    default void watchesRemove(String path, ZookeeperWatchable watchable, Watcher.WatcherType watcherType,
                               boolean locally, boolean quietly, boolean guaranteed, ZookeeperBackground background) {
        watchesRemoveForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable, watcherType,
                locally, quietly, guaranteed, background);
    }

    /**
     * 移除指定实例内指定路径的监听器
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     *                     当Watcher和CuratorWatcher值都为空时，移除全部
     * @param watcherType  监听器类型
     * @param locally      是否仅删除本地监听器
     *                     当无法连接到zk服务端时
     * @param quietly      是否静默
     * @param guaranteed   处理边界问题
     *                     操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     *                     当guaranteed为true时，参数watcherType locally quietly不生效
     * @param background   后台执行参数
     */
    void watchesRemoveForInstance(String instanceName, String path, ZookeeperWatchable watchable,
                                  Watcher.WatcherType watcherType, boolean locally, boolean quietly,
                                  boolean guaranteed, ZookeeperBackground background);



    /*===========================移除监听👆  添加监听👇=================================================================*/


    /**
     * 获取默认实例节点监视构建器
     *
     * @param addWatchMode 监听器的模式
     * @param watchable    zookeeper监听器
     * @param background   后台执行参数
     * @return
     */
    default Pathable<Void> gainAddWatchesPathable(AddWatchMode addWatchMode,
                                                  ZookeeperWatchable watchable,
                                                  ZookeeperBackground background) {
        return gainAddWatchesForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, addWatchMode,
                watchable, background);
    }


    /**
     * 获取指定实例节点监视构建器
     *
     * @param instanceName 实例名称
     * @param addWatchMode 监听器的模式
     * @param watchable    zookeeper监听器
     * @param background   后台执行参数
     * @return
     */
    Pathable<Void> gainAddWatchesForInstance(String instanceName, AddWatchMode addWatchMode,
                                             ZookeeperWatchable watchable, ZookeeperBackground background);


    /**
     * 在默认实例内监视一个节点
     *
     * @param path 节点路径
     * @return
     */
    default void watchers(String path) {
        watchersForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path);
    }


    /**
     * 在指定实例内监视一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @return
     */
    default void watchersForInstance(String instanceName, String path) {
        watchersForInstance(instanceName, path, (AddWatchMode) null);
    }


    /**
     * 在默认实例内监视一个节点
     *
     * @param path         节点路径
     * @param addWatchMode 是否静默
     * @return
     */
    default void watchers(String path, AddWatchMode addWatchMode) {
        watchersForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, addWatchMode);
    }


    /**
     * 在指定实例内监视一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param addWatchMode 是否静默
     * @return
     */
    default void watchersForInstance(String instanceName, String path, AddWatchMode addWatchMode) {
        watchersForInstance(instanceName, path, addWatchMode, (ZookeeperBackground) null);
    }


    /**
     * 在默认实例内监视一个节点
     *
     * @param path      节点路径
     * @param watchable zookeeper监听器
     * @return
     */
    default void watchers(String path, ZookeeperWatchable watchable) {
        watchersForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable);
    }


    /**
     * 在指定实例内监视一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     * @return
     */
    default void watchersForInstance(String instanceName, String path, ZookeeperWatchable watchable) {
        watchersForInstance(instanceName, path, null, watchable);
    }


    /**
     * 在默认实例内监视一个节点
     *
     * @param path       节点路径
     * @param background 后台执行参数
     * @return
     */
    default void watchers(String path, ZookeeperBackground background) {
        watchersForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, background);
    }


    /**
     * 在指定实例内监视一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param background   后台执行参数
     * @return
     */
    default void watchersForInstance(String instanceName, String path, ZookeeperBackground background) {
        watchersForInstance(instanceName, path, (AddWatchMode) null, background);
    }


    /**
     * 在默认实例内监视一个节点
     *
     * @param path         节点路径
     * @param addWatchMode 监听器的模式
     * @param watchable    zookeeper监听器
     * @return
     */
    default void watchers(String path, AddWatchMode addWatchMode, ZookeeperWatchable watchable) {
        watchersForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, addWatchMode, watchable);
    }


    /**
     * 在指定实例内监视一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param addWatchMode 监听器的模式
     * @param watchable    zookeeper监听器
     * @return
     */
    default void watchersForInstance(String instanceName, String path,
                                     AddWatchMode addWatchMode, ZookeeperWatchable watchable) {
        watchersForInstance(instanceName, path, addWatchMode, watchable, null);

    }


    /**
     * 在默认实例内监视一个节点
     *
     * @param path         节点路径
     * @param addWatchMode 是否静默
     * @param background   后台执行参数
     * @return
     */
    default void watchers(String path, AddWatchMode addWatchMode, ZookeeperBackground background) {
        watchersForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, addWatchMode, background);
    }


    /**
     * 在指定实例内监视一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param addWatchMode 是否静默
     * @param backgrounde  后台执行参数
     * @return
     */
    default void watchersForInstance(String instanceName, String path,
                                     AddWatchMode addWatchMode, ZookeeperBackground backgrounde) {
        watchersForInstance(instanceName, path, addWatchMode, null, backgrounde);
    }


    /**
     * 在默认实例内监视一个节点
     *
     * @param path       节点路径
     * @param watchable  zookeeper监听器
     * @param background 后台执行参数
     * @return
     */
    default void watchers(String path, ZookeeperWatchable watchable, ZookeeperBackground background) {
        watchersForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable, background);
    }


    /**
     * 在指定实例内监视一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     * @param backgrounde  后台执行参数
     * @return
     */
    default void watchersForInstance(String instanceName, String path,
                                     ZookeeperWatchable watchable, ZookeeperBackground backgrounde) {
        watchersForInstance(instanceName, path, null, watchable, backgrounde);
    }


    /**
     * 在默认实例内监视一个节点
     *
     * @param path         节点路径
     * @param addWatchMode 监听器的模式
     * @param watchable    zookeeper监听器
     * @param background   后台执行参数
     * @return
     */
    default void watchers(String path, AddWatchMode addWatchMode, ZookeeperWatchable watchable,
                          ZookeeperBackground background) {
        watchersForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, addWatchMode, watchable, background);
    }


    /**
     * 在指定实例内监视一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param addWatchMode 监听器的模式
     * @param watchable    zookeeper监听器
     * @param backgrounde  后台执行参数
     * @return
     */
    void watchersForInstance(String instanceName, String path, AddWatchMode addWatchMode,
                             ZookeeperWatchable watchable, ZookeeperBackground backgrounde);
}
