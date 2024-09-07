package top.mingempty.zookeeper.api;

import org.apache.curator.framework.api.ExistsBuilder;
import org.apache.curator.framework.api.Pathable;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import top.mingempty.domain.enums.YesOrNoEnum;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.zookeeper.entity.ZookeeperBackground;
import top.mingempty.zookeeper.entity.ZookeeperWatchable;

import java.util.List;

/**
 * zookeeper校验节点是否存在API
 *
 * @author zzhao
 */
public interface ZookeepCheckExistsApi {

    /**
     * 获取默认实例的指定节点的存在构建器
     *
     * @return
     */
    default ExistsBuilder gainExistsBuilder() {
        return gainExistsBuilderForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 获取指定实例指定节点的存在构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    ExistsBuilder gainExistsBuilderForInstance(String instanceName);

    /**
     * 获取默认实例的指定节点的存在状态
     *
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param aclList         权限列表
     * @param background      后台执行参数
     * @param watchable       zookeeper监听器
     * @return
     */
    default Pathable<Stat> gainExistsPathable(YesOrNoEnum creatingParents, List<ACL> aclList,
                                              ZookeeperBackground background, ZookeeperWatchable watchable) {
        return gainExistsPathableForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, creatingParents, aclList,
                background, watchable);
    }

    /**
     * 获取指定实例的指定节点的存在状态
     *
     * @param instanceName    实例名称
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param aclList         权限列表
     * @param background      后台执行参数
     * @param watchable       zookeeper监听器
     * @return
     */
    Pathable<Stat> gainExistsPathableForInstance(String instanceName,
                                                 YesOrNoEnum creatingParents, List<ACL> aclList,
                                                 ZookeeperBackground background, ZookeeperWatchable watchable);


    /**
     * 获取默认实例的指定节点的存在状态
     *
     * @param path 节点路径
     * @return
     */
    default Stat exists(String path) {
        return existsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path);
    }

    /**
     * 获取指定实例的指定节点的存在状态
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @return
     */
    default Stat existsForInstance(String instanceName, String path) {
        return existsForInstance(instanceName, path, null, List.of());
    }


    /**
     * 获取默认实例的指定节点的存在状态
     *
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @return
     */
    default Stat exists(String path, YesOrNoEnum creatingParents) {
        return existsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, creatingParents);
    }

    /**
     * 获取指定实例的指定节点的存在状态
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @return
     */
    default Stat existsForInstance(String instanceName, String path, YesOrNoEnum creatingParents) {
        return existsForInstance(instanceName, path, creatingParents, List.of(), (ZookeeperWatchable) null);
    }


    /**
     * 获取默认实例的指定节点的存在状态
     *
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param aclList         权限列表
     * @return
     */
    default Stat exists(String path, YesOrNoEnum creatingParents, List<ACL> aclList) {
        return existsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, creatingParents, aclList);
    }

    /**
     * 获取指定实例的指定节点的存在状态
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param aclList         权限列表
     * @return
     */
    default Stat existsForInstance(String instanceName, String path, YesOrNoEnum creatingParents, List<ACL> aclList) {
        return existsForInstance(instanceName, path, creatingParents, aclList, (ZookeeperWatchable) null);
    }

    /**
     * 获取默认实例的指定节点的存在状态
     *
     * @param path       节点路径
     * @param background 后台执行参数
     * @return
     */
    default Stat exists(String path, ZookeeperBackground background) {
        return existsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, background);
    }

    /**
     * 获取指定实例的指定节点的存在状态
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param background   后台执行参数
     * @return
     */
    default Stat existsForInstance(String instanceName, String path, ZookeeperBackground background) {
        return existsForInstance(instanceName, path, background, null);
    }

    /**
     * 获取默认实例的指定节点的存在状态
     *
     * @param path      节点路径
     * @param watchable zookeeper监听器
     * @return
     */
    default Stat exists(String path, ZookeeperWatchable watchable) {
        return existsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, watchable);
    }

    /**
     * 获取指定实例的指定节点的存在状态
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param watchable    zookeeper监听器
     * @return
     */
    default Stat existsForInstance(String instanceName, String path,
                                   ZookeeperWatchable watchable) {
        return existsForInstance(instanceName, path, null, watchable);
    }

    /**
     * 获取默认实例的指定节点的存在状态
     *
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param aclList         权限列表
     * @param background      后台执行参数
     * @return
     */
    default Stat exists(String path, YesOrNoEnum creatingParents, List<ACL> aclList, ZookeeperBackground background) {
        return existsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, creatingParents, aclList, background);
    }

    /**
     * 获取指定实例的指定节点的存在状态
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param aclList         权限列表
     * @param background      后台执行参数
     * @return
     */
    default Stat existsForInstance(String instanceName, String path, YesOrNoEnum creatingParents, List<ACL> aclList,
                                   ZookeeperBackground background) {
        return existsForInstance(instanceName, path, creatingParents, aclList, background, null);
    }

    /**
     * 获取默认实例的指定节点的存在状态
     *
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param aclList         权限列表
     * @param watchable       zookeeper监听器
     * @return
     */
    default Stat exists(String path, YesOrNoEnum creatingParents, List<ACL> aclList, ZookeeperWatchable watchable) {
        return existsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, creatingParents, aclList, watchable);
    }

    /**
     * 获取指定实例的指定节点的存在状态
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param aclList         权限列表
     * @param watchable       zookeeper监听器
     * @return
     */
    default Stat existsForInstance(String instanceName, String path, YesOrNoEnum creatingParents, List<ACL> aclList,
                                   ZookeeperWatchable watchable) {
        return existsForInstance(instanceName, path, creatingParents, aclList, null, watchable);
    }


    /**
     * 获取默认实例的指定节点的存在状态
     *
     * @param path       节点路径
     * @param background 后台执行参数
     * @param watchable  zookeeper监听器
     * @return
     */
    default Stat exists(String path, ZookeeperBackground background, ZookeeperWatchable watchable) {
        return existsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, background, watchable);
    }

    /**
     * 获取指定实例的指定节点的存在状态
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param background   后台执行参数
     * @param watchable    zookeeper监听器
     * @return
     */
    default Stat existsForInstance(String instanceName, String path,
                                   ZookeeperBackground background, ZookeeperWatchable watchable) {
        return existsForInstance(instanceName, path, null, List.of(), background, watchable);

    }

    /**
     * 获取默认实例的指定节点的存在状态
     *
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param aclList         权限列表
     * @param background      后台执行参数
     * @param watchable       zookeeper监听器
     * @return
     */
    default Stat exists(String path, YesOrNoEnum creatingParents, List<ACL> aclList,
                        ZookeeperBackground background, ZookeeperWatchable watchable) {
        return existsForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, creatingParents, aclList, background, watchable);
    }

    /**
     * 获取指定实例的指定节点的存在状态
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param aclList         权限列表
     * @param background      后台执行参数
     * @param watchable       zookeeper监听器
     * @return
     */
    Stat existsForInstance(String instanceName, String path, YesOrNoEnum creatingParents, List<ACL> aclList,
                           ZookeeperBackground background, ZookeeperWatchable watchable);
}
