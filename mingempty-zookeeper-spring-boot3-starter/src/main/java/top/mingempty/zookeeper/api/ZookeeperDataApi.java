package top.mingempty.zookeeper.api;

import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.api.PathAndBytesable;
import org.apache.curator.framework.api.Pathable;
import org.apache.curator.framework.api.SetDataBuilder;
import org.apache.zookeeper.data.Stat;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.zookeeper.entity.ZookeeperBackground;
import top.mingempty.zookeeper.entity.ZookeeperWatchable;

/**
 * zookeeper节点数据API
 *
 * @author zzhao
 */
public interface ZookeeperDataApi {

    /**
     * 获取默认实例的指定节点的数据构建器
     *
     * @return
     */
    default GetDataBuilder gainGetDataBuilder() {
        return gainGetDataBuilderForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 获取指定实例指定节点的数据构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    GetDataBuilder gainGetDataBuilderForInstance(String instanceName);

    /**
     * 获取默认实例的指定节点的数据
     *
     * @param decompressed 使用配置的压缩提供程序对数据进行解压缩
     * @param stat         获取当前节点状态
     * @param background   后台执行参数
     * @param watchable    zookeeper监听器
     * @return
     */
    default Pathable<byte[]> gainDataPathableForInstance(boolean decompressed, final Stat stat,
                                                         ZookeeperBackground background,
                                                         ZookeeperWatchable watchable) {
        return gainDataPathableForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, decompressed, stat,
                background, watchable);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param decompressed 使用配置的压缩提供程序对数据进行解压缩
     * @param stat         获取当前节点状态
     * @param background   后台执行参数
     * @param watchable    zookeeper监听器
     * @return
     */
    Pathable<byte[]> gainDataPathableForInstance(String instanceName, boolean decompressed, final Stat stat,
                                                 ZookeeperBackground background,
                                                 ZookeeperWatchable watchable);

    /**
     * 获取默认实例的指定节点的数据
     *
     * @param path 节点路径
     * @return
     */
    default String data(String path) {
        return dataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @return
     */
    default String dataForInstance(String instanceName, String path) {
        return dataForInstance(instanceName, path, false);
    }


    /**
     * 获取默认实例的指定节点的数据
     *
     * @param path 节点路径
     * @return
     */
    default String data(String path, boolean decompressed) {
        return dataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, decompressed);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @return
     */
    default String dataForInstance(String instanceName, String path, boolean decompressed) {
        return dataForInstance(instanceName, path, decompressed, (Stat) null);
    }

    /**
     * 获取默认实例的指定节点的数据
     *
     * @param path       节点路径
     * @param background 后台执行参数
     * @return
     */
    default String data(String path, boolean decompressed, ZookeeperBackground background) {
        return dataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, decompressed, background);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param background   后台执行参数
     * @return
     */
    default String dataForInstance(String instanceName, String path, boolean decompressed, ZookeeperBackground background) {
        return dataForInstance(instanceName, path, decompressed, background, null);

    }

    /**
     * 获取默认实例的指定节点的数据
     *
     * @param path      节点路径
     * @param watchable zookeeper监听器
     * @return
     */
    default String data(String path, ZookeeperWatchable watchable) {
        return dataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     * @return
     */
    default String dataForInstance(String instanceName, String path, ZookeeperWatchable watchable) {
        return dataForInstance(instanceName, path, false, watchable);
    }

    /**
     * 获取默认实例的指定节点的数据
     *
     * @param path      节点路径
     * @param watchable zookeeper监听器
     * @return
     */
    default String data(String path, boolean decompressed, ZookeeperWatchable watchable) {
        return dataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, decompressed, watchable);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     * @return
     */
    default String dataForInstance(String instanceName, String path,
                                   boolean decompressed, ZookeeperWatchable watchable) {
        return dataForInstance(instanceName, path, decompressed, (Stat) null, watchable);
    }

    /**
     * 获取默认实例的指定节点的数据
     *
     * @param path 节点路径
     * @param stat 获取当前节点状态
     * @return
     */
    default String data(String path, final Stat stat) {
        return dataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, stat);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param stat         获取当前节点状态
     * @return
     */
    default String dataForInstance(String instanceName, String path, final Stat stat) {
        return dataForInstance(instanceName, path, false, stat);
    }

    /**
     * 获取默认实例的指定节点的数据
     *
     * @param path 节点路径
     * @param stat 获取当前节点状态
     * @return
     */
    default String data(String path, boolean decompressed, final Stat stat) {
        return dataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, decompressed, stat);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param stat         获取当前节点状态
     * @return
     */
    default String dataForInstance(String instanceName, String path, boolean decompressed, final Stat stat) {
        return dataForInstance(instanceName, path, decompressed, stat, null);
    }

    /**
     * 获取默认实例的指定节点的数据
     *
     * @param path       节点路径
     * @param background 后台执行参数
     * @param watchable  zookeeper监听器
     * @return
     */
    default String data(String path, ZookeeperBackground background, ZookeeperWatchable watchable) {
        return dataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, background, watchable);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param background   后台执行参数
     * @param watchable    zookeeper监听器
     * @return
     */
    default String dataForInstance(String instanceName, String path,
                                   ZookeeperBackground background, ZookeeperWatchable watchable) {
        return dataForInstance(instanceName, path, false, background, watchable);

    }

    /**
     * 获取默认实例的指定节点的数据
     *
     * @param path       节点路径
     * @param background 后台执行参数
     * @param watchable  zookeeper监听器
     * @return
     */
    default String data(String path, boolean decompressed, ZookeeperBackground background, ZookeeperWatchable watchable) {
        return dataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, decompressed, background, watchable);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param background   后台执行参数
     * @param watchable    zookeeper监听器
     * @return
     */
    default String dataForInstance(String instanceName, String path, boolean decompressed,
                                   ZookeeperBackground background, ZookeeperWatchable watchable) {
        return dataForInstance(instanceName, path, decompressed, null, background, watchable);

    }

    /**
     * 获取默认实例的指定节点的数据
     *
     * @param path      节点路径
     * @param stat      获取当前节点状态
     * @param watchable zookeeper监听器
     * @return
     */
    default String data(String path, final Stat stat, ZookeeperWatchable watchable) {
        return dataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, stat, watchable);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param stat         获取当前节点状态
     * @param watchable    zookeeper监听器
     * @return
     */
    default String dataForInstance(String instanceName, String path,
                                   final Stat stat, ZookeeperWatchable watchable) {
        return dataForInstance(instanceName, path, false, stat, watchable);
    }

    /**
     * 获取默认实例的指定节点的数据
     *
     * @param path      节点路径
     * @param stat      获取当前节点状态
     * @param watchable zookeeper监听器
     * @return
     */
    default String data(String path, boolean decompressed, final Stat stat, ZookeeperWatchable watchable) {
        return dataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, decompressed, stat, watchable);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param stat         获取当前节点状态
     * @param watchable    zookeeper监听器
     * @return
     */
    default String dataForInstance(String instanceName, String path, boolean decompressed,
                                   final Stat stat, ZookeeperWatchable watchable) {
        return dataForInstance(instanceName, path, decompressed, stat, null, watchable);
    }

    /**
     * 获取默认实例的指定节点的数据
     *
     * @param path       节点路径
     * @param stat       获取当前节点状态
     * @param background 后台执行参数
     * @param watchable  zookeeper监听器
     * @return
     */
    default String data(String path, final Stat stat,
                        ZookeeperBackground background, ZookeeperWatchable watchable) {
        return dataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, stat, background, watchable);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param stat         获取当前节点状态
     * @param background   后台执行参数
     * @param watchable    zookeeper监听器
     * @return
     */
    default String dataForInstance(String instanceName, String path, final Stat stat,
                                   ZookeeperBackground background, ZookeeperWatchable watchable) {
        return dataForInstance(instanceName, path, false, stat, background, watchable);
    }

    /**
     * 获取默认实例的指定节点的数据
     *
     * @param path       节点路径
     * @param stat       获取当前节点状态
     * @param background 后台执行参数
     * @param watchable  zookeeper监听器
     * @return
     */
    default String data(String path, boolean decompressed, final Stat stat,
                        ZookeeperBackground background, ZookeeperWatchable watchable) {
        return dataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, decompressed, stat,
                background, watchable);
    }

    /**
     * 获取指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param stat         获取当前节点状态
     * @param background   后台执行参数
     * @param watchable    zookeeper监听器
     * @return
     */
    String dataForInstance(String instanceName, String path, boolean decompressed, final Stat stat,
                           ZookeeperBackground background, ZookeeperWatchable watchable);


    /*===========================获取数据👆  设置数据👇=================================================================*/


    /**
     * 获取默认实例的指定节点的设置数据构建器
     *
     * @return
     */
    default SetDataBuilder gainSetDataBuilder() {
        return gainSetDataBuilderForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 获取指定实例指定节点的设置数据构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    SetDataBuilder gainSetDataBuilderForInstance(String instanceName);

    /**
     * 获取设置默认实例的指定节点的数据 Pathable
     *
     * @param version    指定版本
     * @param background 后台执行参数
     * @return
     */
    default PathAndBytesable<Stat> gainSetDataPathable(int version,
                                                       ZookeeperBackground background) {
        return gainSetDataPathableForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, version, background);
    }

    /**
     * 获取设置指定实例的指定节点的数据 Pathable
     *
     * @param instanceName 实例名称
     * @param version      指定版本
     * @param background   后台执行参数
     * @return
     */
    PathAndBytesable<Stat> gainSetDataPathableForInstance(String instanceName, int version,
                                                          ZookeeperBackground background);


    /**
     * 设置默认实例的指定节点的数据
     *
     * @param path 节点路径
     * @return
     */
    default Stat setData(String path) {
        return setDataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path);
    }

    /**
     * 设置指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @return
     */
    default Stat setDataForInstance(String instanceName, String path) {
        return setDataForInstance(instanceName, path, -1);
    }


    /**
     * 设置默认实例的指定节点的数据
     *
     * @param path       节点路径
     * @param background 后台执行参数
     * @return
     */
    default Stat setData(String path, ZookeeperBackground background) {
        return setDataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, background);
    }

    /**
     * 设置指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param background   后台执行参数
     * @return
     */
    default Stat setDataForInstance(String instanceName, String path, ZookeeperBackground background) {
        return setDataForInstance(instanceName, path, -1, background);
    }


    /**
     * 设置默认实例的指定节点的数据
     *
     * @param path    节点路径
     * @param version 指定版本
     * @return
     */
    default Stat setData(String path, int version) {
        return setDataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, version);
    }

    /**
     * 设置指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param version      指定版本
     * @return
     */
    default Stat setDataForInstance(String instanceName, String path, int version) {
        return setDataForInstance(instanceName, path, version, null);
    }


    /**
     * 设置默认实例的指定节点的数据
     *
     * @param path       节点路径
     * @param version    指定版本
     * @param background 后台执行参数
     * @return
     */
    default Stat setData(String path, int version,
                         ZookeeperBackground background) {
        return setDataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, version, background);
    }

    /**
     * 设置指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param version      指定版本
     * @param background   后台执行参数
     * @return
     */
    Stat setDataForInstance(String instanceName, String path, int version,
                            ZookeeperBackground background);


    /**
     * 设置默认实例的指定节点的数据
     *
     * @param path 节点路径
     * @param data 获取当前节点状态
     * @return
     */
    default Stat setData(String path, Object data) {
        return setDataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data);
    }

    /**
     * 设置指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @return
     */
    default Stat setDataForInstance(String instanceName, String path, Object data) {
        return setDataForInstance(instanceName, path, data, -1);
    }


    /**
     * 设置默认实例的指定节点的数据
     *
     * @param path       节点路径
     * @param data       获取当前节点状态
     * @param background 后台执行参数
     * @return
     */
    default Stat setData(String path, Object data, ZookeeperBackground background) {
        return setDataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, background);
    }

    /**
     * 设置指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @param background   后台执行参数
     * @return
     */
    default Stat setDataForInstance(String instanceName, String path, Object data, ZookeeperBackground background) {
        return setDataForInstance(instanceName, path, data, -1, background);
    }


    /**
     * 设置默认实例的指定节点的数据
     *
     * @param path    节点路径
     * @param data    获取当前节点状态
     * @param version 指定版本
     * @return
     */
    default Stat setData(String path, Object data, int version) {
        return setDataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, version);
    }

    /**
     * 设置指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @param version      指定版本
     * @return
     */
    default Stat setDataForInstance(String instanceName, String path, Object data, int version) {
        return setDataForInstance(instanceName, path, data, version, null);
    }


    /**
     * 设置默认实例的指定节点的数据
     *
     * @param path       节点路径
     * @param data       获取当前节点状态
     * @param version    指定版本
     * @param background 后台执行参数
     * @return
     */
    default Stat setData(String path, Object data, int version,
                         ZookeeperBackground background) {
        return setDataForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, version, background);
    }

    /**
     * 设置指定实例的指定节点的数据
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @param version      指定版本
     * @param background   后台执行参数
     * @return
     */
    Stat setDataForInstance(String instanceName, String path, Object data, int version,
                            ZookeeperBackground background);

}
