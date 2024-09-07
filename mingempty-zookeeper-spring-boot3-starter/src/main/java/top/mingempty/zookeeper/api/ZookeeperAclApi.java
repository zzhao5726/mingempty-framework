package top.mingempty.zookeeper.api;

import org.apache.curator.framework.api.GetACLBuilder;
import org.apache.curator.framework.api.Pathable;
import org.apache.curator.framework.api.SetACLBuilder;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.zookeeper.entity.ZookeeperBackground;

import java.util.List;

/**
 * zookeeper节点 ACL API
 *
 * @author zzhao
 */
public interface ZookeeperAclApi {

    /**
     * 获取默认实例的指定节点的获取ACL构建器
     *
     * @return
     */
    default GetACLBuilder gainGetACLBuilder() {
        return gainGetACLBuilderForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 获取指定实例指定节点的获取ACL构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    GetACLBuilder gainGetACLBuilderForInstance(String instanceName);

    /**
     * 获取默认实例的指定节点的ACL Pathable
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param stat       获取当前节点状态
     * @param background 后台执行参数
     * @return
     */
    default Pathable<List<ACL>> gainACLPathable(final Stat stat,
                                                ZookeeperBackground background) {
        return gainACLPathableForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, stat, background);
    }

    /**
     * 获取指定实例的指定节点的ACL Pathable
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param instanceName 实例名称
     * @param stat         获取当前节点状态
     * @param background   后台执行参数
     * @return
     */
    Pathable<List<ACL>> gainACLPathableForInstance(String instanceName, final Stat stat,
                                                   ZookeeperBackground background);


    /**
     * 获取默认实例的指定节点的ACL
     *
     * @param path 节点路径
     * @return
     */
    default List<ACL> acl(String path) {
        return aclForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path);
    }

    /**
     * 获取指定实例的指定节点的ACL
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @return
     */
    default List<ACL> aclForInstance(String instanceName, String path) {
        return aclForInstance(instanceName, path, (Stat) null);
    }

    /**
     * 获取默认实例的指定节点的ACL
     *
     * @param path       节点路径
     * @param background 后台执行参数
     * @return
     */
    default List<ACL> acl(String path, ZookeeperBackground background) {
        return aclForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, background);
    }

    /**
     * 获取指定实例的指定节点的ACL
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param background   后台执行参数
     * @return
     */
    default List<ACL> aclForInstance(String instanceName, String path, ZookeeperBackground background) {
        return aclForInstance(instanceName, path, (Stat) null, background);

    }

    /**
     * 获取默认实例的指定节点的ACL
     *
     * @param path 节点路径
     * @param stat 获取当前节点状态
     * @return
     */
    default List<ACL> acl(String path, final Stat stat) {
        return aclForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, stat);
    }

    /**
     * 获取指定实例的指定节点的ACL
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param stat         获取当前节点状态
     * @return
     */
    default List<ACL> aclForInstance(String instanceName, String path, final Stat stat) {
        return aclForInstance(instanceName, path, stat, null);
    }

    /**
     * 获取默认实例的指定节点的ACL
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param path       节点路径
     * @param stat       获取当前节点状态
     * @param background 后台执行参数
     * @return
     */
    default List<ACL> acl(String path, final Stat stat, ZookeeperBackground background) {
        return aclForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, stat, background);
    }

    /**
     * 获取指定实例的指定节点的ACL
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param stat         获取当前节点状态
     * @param background   后台执行参数
     * @return
     */
    List<ACL> aclForInstance(String instanceName, String path, final Stat stat, ZookeeperBackground background);


    /*===========================获取权限👆  设置权限👇=================================================================*/


    /**
     * 获取默认实例的指定节点的设置ACL构建器
     *
     * @return
     */
    default SetACLBuilder gainSetACLBuilder() {
        return gainSetACLBuilderForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 获取指定实例指定节点的设置ACL构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    SetACLBuilder gainSetACLBuilderForInstance(String instanceName);

    /**
     * 获取设置默认实例的指定节点的ACL Pathable
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param aclList    获取当前节点状态
     * @param version    指定版本
     * @param background 后台执行参数
     * @return
     */
    default Pathable<Stat> gainACLPathable(List<ACL> aclList, int version,
                                           ZookeeperBackground background) {
        return gainACLPathableForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, aclList, version, background);
    }

    /**
     * 获取设置指定实例的指定节点的ACL Pathable
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param instanceName 实例名称
     * @param aclList      获取当前节点状态
     * @param version      指定版本
     * @param background   后台执行参数
     * @return
     */
    Pathable<Stat> gainACLPathableForInstance(String instanceName, List<ACL> aclList, int version,
                                              ZookeeperBackground background);


    /**
     * 设置默认实例的指定节点的ACL
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param path       节点路径
     * @param aclList    获取当前节点状态
     * @param background 后台执行参数
     * @return
     */
    default Stat acl(String path, List<ACL> aclList, ZookeeperBackground background) {
        return aclForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, aclList, background);
    }

    /**
     * 设置指定实例的指定节点的ACL
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param aclList      获取当前节点状态
     * @param background   后台执行参数
     * @return
     */
    default Stat aclForInstance(String instanceName, String path, List<ACL> aclList, ZookeeperBackground background) {
        return aclForInstance(instanceName, path, aclList, -1, background);
    }


    /**
     * 设置默认实例的指定节点的ACL
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param path    节点路径
     * @param aclList 获取当前节点状态
     * @param version 指定版本
     * @return
     */
    default Stat acl(String path, List<ACL> aclList, int version) {
        return aclForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, aclList, version);
    }

    /**
     * 设置指定实例的指定节点的ACL
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param aclList      获取当前节点状态
     * @param version      指定版本
     * @return
     */
    default Stat aclForInstance(String instanceName, String path, List<ACL> aclList, int version) {
        return aclForInstance(instanceName, path, aclList, version, null);
    }


    /**
     * 设置默认实例的指定节点的ACL
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param path       节点路径
     * @param aclList    获取当前节点状态
     * @param version    指定版本
     * @param background 后台执行参数
     * @return
     */
    default Stat acl(String path, List<ACL> aclList, int version,
                     ZookeeperBackground background) {
        return aclForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, aclList, version, background);
    }

    /**
     * 设置指定实例的指定节点的ACL
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param aclList      获取当前节点状态
     * @param version      指定版本
     * @param background   后台执行参数
     * @return
     */
    Stat aclForInstance(String instanceName, String path, List<ACL> aclList, int version,
                        ZookeeperBackground background);


}
