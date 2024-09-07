package top.mingempty.zookeeper.api;

import org.apache.curator.framework.api.DeleteBuilder;
import org.apache.curator.framework.api.Pathable;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.zookeeper.entity.ZookeeperBackground;

/**
 * zookeeper删除节点API
 *
 * @author zzhao
 */
public interface ZookeeperDeleteApi {

    /**
     * 获取默认实例的节点删除构建器
     *
     * @return
     */
    default DeleteBuilder gainDeleteBuilder() {
        return gainDeleteBuilderForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 获取指定实例节点删除构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    DeleteBuilder gainDeleteBuilderForInstance(String instanceName);


    /**
     * 获取默认实例节点删除构建器
     *
     * @param quietly                  是否静默
     * @param guaranteed               处理边界问题
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param version                  匹配版本
     * @param background               后台执行参数
     * @return
     */
    default Pathable<Void> gainDeletePathable(boolean quietly, boolean guaranteed, boolean deletingChildrenIfNeeded,
                                              int version, ZookeeperBackground background) {
        return gainDeletePathableForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, quietly, guaranteed,
                deletingChildrenIfNeeded, version, background);
    }


    /**
     * 获取指定实例节点删除构建器
     *
     * @param instanceName             实例名称
     * @param quietly                  是否静默
     * @param guaranteed               处理边界问题
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param version                  匹配版本
     * @param background               后台执行参数
     * @return
     */
    Pathable<Void> gainDeletePathableForInstance(String instanceName, boolean quietly,
                                                 boolean guaranteed, boolean deletingChildrenIfNeeded,
                                                 int version, ZookeeperBackground background);


    /**
     * 在默认实例内删除一个节点
     *
     * @param path 节点路径
     * @return
     */
    default void delete(String path) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @return
     */
    default void deleteForInstance(String instanceName, String path) {
        deleteForInstance(instanceName, path, false);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path                     节点路径
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @return
     */
    default void delete(String path, boolean deletingChildrenIfNeeded) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, deletingChildrenIfNeeded);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName             实例名称
     * @param path                     节点路径
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @return
     */
    default void deleteForInstance(String instanceName, String path, boolean deletingChildrenIfNeeded) {
        deleteForInstance(instanceName, path, deletingChildrenIfNeeded, null);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path                     节点路径
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param version                  匹配版本
     * @return
     */
    default void delete(String path, boolean deletingChildrenIfNeeded, int version) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, deletingChildrenIfNeeded, version);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName             实例名称
     * @param path                     节点路径
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param version                  匹配版本
     * @return
     */
    default void deleteForInstance(String instanceName, String path, boolean deletingChildrenIfNeeded, int version) {
        deleteForInstance(instanceName, path, deletingChildrenIfNeeded, version, null);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path                     节点路径
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param background               后台执行参数
     * @return
     */
    default void delete(String path, boolean deletingChildrenIfNeeded, ZookeeperBackground background) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, deletingChildrenIfNeeded, background);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName             实例名称
     * @param path                     节点路径
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param background               后台执行参数
     * @return
     */
    default void deleteForInstance(String instanceName, String path,
                                   boolean deletingChildrenIfNeeded, ZookeeperBackground background) {
        deleteForInstance(instanceName, path, deletingChildrenIfNeeded, -1, background);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path                     节点路径
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param version                  匹配版本
     * @param background               后台执行参数
     * @return
     */
    default void delete(String path, boolean deletingChildrenIfNeeded, int version, ZookeeperBackground background) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, deletingChildrenIfNeeded, version, background);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName             实例名称
     * @param path                     节点路径
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param version                  匹配版本
     * @param background               后台执行参数
     * @return
     */
    default void deleteForInstance(String instanceName, String path, boolean deletingChildrenIfNeeded,
                                   int version, ZookeeperBackground background) {
        deleteForInstance(instanceName, path, false, false, deletingChildrenIfNeeded,
                version, background);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path    节点路径
     * @param version 匹配版本
     * @return
     */
    default void delete(String path, int version) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, version);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param version      匹配版本
     * @return
     */
    default void deleteForInstance(String instanceName, String path, int version) {
        deleteForInstance(instanceName, path, version, null);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path       节点路径
     * @param version    匹配版本
     * @param background 后台执行参数
     * @return
     */
    default void delete(String path, int version, ZookeeperBackground background) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, version, background);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param version      匹配版本
     * @param background   后台执行参数
     * @return
     */
    default void deleteForInstance(String instanceName, String path, int version, ZookeeperBackground background) {
        deleteForInstance(instanceName, path, false, false,
                false, version, background);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path       节点路径
     * @param background 后台执行参数
     * @return
     */
    default void delete(String path, ZookeeperBackground background) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, background);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param background   后台执行参数
     * @return
     */
    default void deleteForInstance(String instanceName, String path, ZookeeperBackground background) {
        deleteForInstance(instanceName, path, false, false,
                false, -1, background);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path       节点路径
     * @param quietly    是否静默
     * @param guaranteed 处理边界问题
     *                   操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @return
     */
    default void delete(String path, boolean quietly, boolean guaranteed) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, quietly, guaranteed);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param quietly      是否静默
     * @param guaranteed   处理边界问题
     *                     操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @return
     */
    default void deleteForInstance(String instanceName, String path,
                                   boolean quietly, boolean guaranteed) {
        deleteForInstance(instanceName, path, quietly, guaranteed, false);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path                     节点路径
     * @param quietly                  是否静默
     * @param guaranteed               处理边界问题
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @return
     */
    default void delete(String path, boolean quietly, boolean guaranteed, boolean deletingChildrenIfNeeded) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, quietly, guaranteed,
                deletingChildrenIfNeeded);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName             实例名称
     * @param path                     节点路径
     * @param quietly                  是否静默
     * @param guaranteed               处理边界问题
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @return
     */
    default void deleteForInstance(String instanceName, String path,
                                   boolean quietly, boolean guaranteed, boolean deletingChildrenIfNeeded) {
        deleteForInstance(instanceName, path, quietly, guaranteed, deletingChildrenIfNeeded, -1);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path                     节点路径
     * @param quietly                  是否静默
     * @param guaranteed               处理边界问题
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param version                  匹配版本
     * @return
     */
    default void delete(String path, boolean quietly, boolean guaranteed,
                        boolean deletingChildrenIfNeeded, int version) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, quietly, guaranteed,
                deletingChildrenIfNeeded, version);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName             实例名称
     * @param path                     节点路径
     * @param quietly                  是否静默
     * @param guaranteed               处理边界问题
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param version                  匹配版本
     * @return
     */
    default void deleteForInstance(String instanceName, String path,
                                   boolean quietly, boolean guaranteed,
                                   boolean deletingChildrenIfNeeded, int version) {
        deleteForInstance(instanceName, path, quietly, guaranteed,
                deletingChildrenIfNeeded, version, null);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path                     节点路径
     * @param guaranteed               处理边界问题
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param version                  匹配版本
     * @return
     */
    default void delete(String path, boolean guaranteed, boolean deletingChildrenIfNeeded, int version) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, deletingChildrenIfNeeded, guaranteed, version);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName             实例名称
     * @param path                     节点路径
     * @param guaranteed               处理边界问题
     * @param deletingChildrenIfNeeded 是否删除子节点
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param version                  匹配版本
     * @return
     */
    default void deleteForInstance(String instanceName, String path,
                                   boolean guaranteed, boolean deletingChildrenIfNeeded, int version) {
        deleteForInstance(instanceName, path, guaranteed, deletingChildrenIfNeeded, version, null);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path                     节点路径
     * @param guaranteed               处理边界问题
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param background               后台执行参数
     * @return
     */
    default void delete(String path, boolean guaranteed, boolean deletingChildrenIfNeeded,
                        ZookeeperBackground background) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, guaranteed, deletingChildrenIfNeeded, background);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName             实例名称
     * @param path                     节点路径
     * @param guaranteed               处理边界问题
     * @param deletingChildrenIfNeeded 是否删除子节点
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param background               后台执行参数
     * @return
     */
    default void deleteForInstance(String instanceName, String path,
                                   boolean guaranteed, boolean deletingChildrenIfNeeded,
                                   ZookeeperBackground background) {
        deleteForInstance(instanceName, path, guaranteed, deletingChildrenIfNeeded, -1, background);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path                     节点路径
     * @param guaranteed               处理边界问题
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param version                  匹配版本
     * @param background               后台执行参数
     * @return
     */
    default void delete(String path, boolean guaranteed,
                        boolean deletingChildrenIfNeeded, int version, ZookeeperBackground background) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, guaranteed,
                deletingChildrenIfNeeded, version, background);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName             实例名称
     * @param path                     节点路径
     * @param guaranteed               处理边界问题
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param version                  匹配版本
     * @param background               后台执行参数
     * @return
     */
    default void deleteForInstance(String instanceName, String path, boolean guaranteed,
                                   boolean deletingChildrenIfNeeded, int version, ZookeeperBackground background) {
        deleteForInstance(instanceName, path, false, guaranteed, deletingChildrenIfNeeded, version, background);
    }


    /**
     * 在默认实例内删除一个节点
     *
     * @param path                     节点路径
     * @param quietly                  是否静默
     * @param guaranteed               处理边界问题
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param version                  匹配版本
     * @param background               后台执行参数
     */
    default void delete(String path, boolean quietly,
                        boolean guaranteed, boolean deletingChildrenIfNeeded,
                        int version, ZookeeperBackground background) {
        deleteForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, quietly,
                guaranteed, deletingChildrenIfNeeded, version, background);
    }


    /**
     * 在指定实例内删除一个节点
     *
     * @param instanceName             实例名称
     * @param path                     节点路径
     * @param quietly                  是否静默
     * @param guaranteed               处理边界问题
     *                                 操作可能在服务器上成功，但在将响应成功返回到客户端之前发生连接失败
     * @param deletingChildrenIfNeeded 是否删除子节点
     * @param version                  匹配版本
     * @param background               后台执行参数
     */
    void deleteForInstance(String instanceName, String path, boolean quietly,
                           boolean guaranteed, boolean deletingChildrenIfNeeded,
                           int version, ZookeeperBackground background);
}
