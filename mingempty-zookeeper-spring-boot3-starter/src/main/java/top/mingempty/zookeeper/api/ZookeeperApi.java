package top.mingempty.zookeeper.api;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.PathAndBytesable;
import org.apache.curator.framework.api.Pathable;
import top.mingempty.domain.other.GlobalConstant;

/**
 * zookeeper常用方法
 *
 * @author zzhao
 */
public interface ZookeeperApi
        extends ZookeeperCreateApi, ZookeeperDeleteApi, ZookeeperChildrenApi, ZookeepCheckExistsApi,
        ZookeeperAclApi, ZookeeperDataApi, ZookeeperWatcherApi {

    /**
     * 将默认实例的CuratorFramework客户端
     *
     * @return
     */
    default CuratorFramework gainCuratorFramework() {
        return gainCuratorFrameworkForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 将指定实例的CuratorFramework客户端
     *
     * @return
     */
    CuratorFramework gainCuratorFrameworkForInstance(String instanceName);


    /**
     * 通过PathAndBytesable处理一个节点
     *
     * @param pathAndBytesable 节点管理器
     * @param path             节点路径
     * @return
     */
    <T> T pathAndBytesable(PathAndBytesable<T> pathAndBytesable, String path);


    /**
     * 通过PathAndBytesable处理一个节点
     *
     * @param pathAndBytesable 节点管理器
     * @param path             节点路径
     * @param data             节点数据
     * @return
     */
    <T> T pathAndBytesable(PathAndBytesable<T> pathAndBytesable, String path, Object data);

    /**
     * 通过Pathable处理一个节点
     *
     * @param pathable 节点管理器
     * @param path     节点路径
     */
    <T> T pathable(Pathable<T> pathable, String path);

    /**
     * 将默认实例内的指定节点设置为容器节点
     *
     * @param path 节点路径
     */
    default void createContainers(String path) {
        createContainersForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path);
    }

    /**
     * 将指定实例内的指定节点设置为容器节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     */
    void createContainersForInstance(String instanceName, String path);
}
