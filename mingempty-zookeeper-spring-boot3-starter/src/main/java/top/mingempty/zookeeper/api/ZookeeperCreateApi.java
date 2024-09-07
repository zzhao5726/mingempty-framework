package top.mingempty.zookeeper.api;

import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.PathAndBytesable;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.server.EphemeralType;
import top.mingempty.domain.enums.YesOrNoEnum;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.zookeeper.entity.ZookeeperBackground;

import java.util.List;

/**
 * zookeeper创建节点API
 *
 * @author zzhao
 */
public interface ZookeeperCreateApi {


    /**
     * 获取默认实例的节点创建构建器
     *
     * @return
     */
    default CreateBuilder gainCreateBuilder() {
        return gainCreateBuilderForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    /**
     * 获取指定实例节点创建构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    CreateBuilder gainCreateBuilderForInstance(String instanceName);

    /**
     * 获取默认实例的节点创建构建器
     *
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background      后台执行参数
     * @return
     */
    default PathAndBytesable<String> gainPathAndBytesable(List<ACL> aclList, YesOrNoEnum creatingParents,
                                                          CreateMode createMode, long ttl,
                                                          ZookeeperBackground background) {
        return gainPathAndBytesableForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, aclList, creatingParents,
                createMode, ttl, background);
    }

    /**
     * 获取指定实例节点创建构建器
     *
     * @param instanceName    实例名称
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background      后台执行参数
     * @return
     */
    PathAndBytesable<String> gainPathAndBytesableForInstance(String instanceName,
                                                             List<ACL> aclList, YesOrNoEnum creatingParents,
                                                             CreateMode createMode, long ttl,
                                                             ZookeeperBackground background);


    /**
     * 在默认实例创建一个节点
     *
     * @param path 节点路径
     * @return
     */
    default String create(String path) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @return
     */
    default String createForInstance(String instanceName, String path) {
        return createForInstance(instanceName, path, null, EphemeralType.TTL.maxValue(), null);
    }


    /**
     * 在默认实例创建一个节点
     *
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @return
     */
    default String create(String path, YesOrNoEnum creatingParents) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @return
     */
    default String createForInstance(String instanceName, String path, YesOrNoEnum creatingParents) {
        return createForInstance(instanceName, path, creatingParents, null, EphemeralType.TTL.maxValue());
    }


    /**
     * 在默认实例创建一个节点
     *
     * @param path    节点路径
     * @param aclList 权限列表
     * @return
     */
    default String create(String path, List<ACL> aclList) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, aclList);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param aclList      权限列表
     * @return
     */
    default String createForInstance(String instanceName, String path, List<ACL> aclList) {
        return createForInstance(instanceName, path, aclList,
                null, EphemeralType.TTL.maxValue(), null);
    }


    /**
     * 在默认实例创建一个节点
     *
     * @param path            节点路径
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @return
     */
    default String create(String path, List<ACL> aclList, YesOrNoEnum creatingParents) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, aclList, creatingParents);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @return
     */
    default String createForInstance(String instanceName, String path, List<ACL> aclList, YesOrNoEnum creatingParents) {
        return createForInstance(instanceName, path, aclList,
                creatingParents, null, EphemeralType.TTL.maxValue());
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path       节点路径
     * @param createMode 节点类型
     * @return
     */
    default String create(String path, CreateMode createMode) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, createMode);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param createMode   节点类型
     * @return
     */
    default String createForInstance(String instanceName, String path, CreateMode createMode) {
        return createForInstance(instanceName, path, createMode, EphemeralType.TTL.maxValue(), null);
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @return
     */
    default String create(String path, YesOrNoEnum creatingParents, CreateMode createMode) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, creatingParents, createMode);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @return
     */
    default String createForInstance(String instanceName, String path,
                                     YesOrNoEnum creatingParents, CreateMode createMode) {
        return createForInstance(instanceName, path, creatingParents, createMode, EphemeralType.TTL.maxValue());
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path       节点路径
     * @param aclList    权限列表
     * @param createMode 节点类型
     * @return
     */
    default String create(String path, List<ACL> aclList, CreateMode createMode) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, aclList, createMode);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param aclList      权限列表
     * @param createMode   节点类型
     * @return
     */
    default String createForInstance(String instanceName, String path, List<ACL> aclList, CreateMode createMode) {
        return createForInstance(instanceName, path, aclList,
                createMode, EphemeralType.TTL.maxValue(), null);
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path            节点路径
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @return
     */
    default String create(String path, List<ACL> aclList, YesOrNoEnum creatingParents, CreateMode createMode) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, aclList, creatingParents, createMode);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @return
     */
    default String createForInstance(String instanceName, String path, List<ACL> aclList,
                                     YesOrNoEnum creatingParents, CreateMode createMode) {
        return createForInstance(instanceName, path, aclList, creatingParents,
                createMode, EphemeralType.TTL.maxValue());
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path       节点路径
     * @param createMode 节点类型
     * @param ttl        过期时间，当 {@code createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                   or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String create(String path, CreateMode createMode, long ttl) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, createMode, ttl);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param createMode   节点类型
     * @param ttl          过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                     or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String createForInstance(String instanceName, String path, CreateMode createMode, long ttl) {
        return createForInstance(instanceName, path, createMode, ttl, null);
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String create(String path, YesOrNoEnum creatingParents, CreateMode createMode, long ttl) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, creatingParents, createMode, ttl);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String createForInstance(String instanceName, String path, YesOrNoEnum creatingParents,
                                     CreateMode createMode, long ttl) {
        return createForInstance(instanceName, path, creatingParents, createMode, ttl, null);
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path       节点路径
     * @param aclList    权限列表
     * @param createMode 节点类型
     * @param ttl        过期时间，当 {@code createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                   or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String create(String path, List<ACL> aclList, CreateMode createMode, long ttl) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, aclList, createMode, ttl);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param aclList      权限列表
     * @param createMode   节点类型
     * @param ttl          过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                     or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String createForInstance(String instanceName, String path, List<ACL> aclList,
                                     CreateMode createMode, long ttl) {
        return createForInstance(instanceName, path, aclList, createMode, ttl, null);
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path            节点路径
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String create(String path, List<ACL> aclList, YesOrNoEnum creatingParents,
                          CreateMode createMode, long ttl) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, aclList, creatingParents, createMode, ttl);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String createForInstance(String instanceName, String path,
                                     List<ACL> aclList, YesOrNoEnum creatingParents, CreateMode createMode, long ttl) {
        return createForInstance(instanceName, path, aclList, creatingParents, createMode, ttl, null);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path       节点路径
     * @param background 后台执行参数
     * @return
     */
    default String create(String path, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, background);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param background   后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path, ZookeeperBackground background) {
        return createForInstance(instanceName, path, null, EphemeralType.TTL.maxValue(), background);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param background      后台执行参数
     * @return
     */
    default String create(String path, YesOrNoEnum creatingParents, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, creatingParents, background);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param background      后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path,
                                     YesOrNoEnum creatingParents, ZookeeperBackground background) {
        return createForInstance(instanceName, path, creatingParents,
                null, EphemeralType.TTL.maxValue(), background);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path       节点路径
     * @param aclList    权限列表
     * @param background 后台执行参数
     * @return
     */
    default String create(String path, List<ACL> aclList, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, aclList, background);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param aclList      权限列表
     * @param background   后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path, List<ACL> aclList,
                                     ZookeeperBackground background) {
        return createForInstance(instanceName, path, aclList, null, EphemeralType.TTL.maxValue(), background);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path            节点路径
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param background      后台执行参数
     * @return
     */
    default String create(String path, List<ACL> aclList, YesOrNoEnum creatingParents, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, aclList, creatingParents, background);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param background      后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path,
                                     List<ACL> aclList, YesOrNoEnum creatingParents, ZookeeperBackground background) {
        return createForInstance(instanceName, path, aclList, creatingParents,
                null, EphemeralType.TTL.maxValue(), background);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path       节点路径
     * @param createMode 节点类型
     * @param ttl        过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                   or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background 后台执行参数
     * @return
     */
    default String create(String path, CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, createMode, ttl, background);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param createMode   节点类型
     * @param ttl          过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                     or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background   后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path,
                                     CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(instanceName, path, List.of(), createMode, ttl, background);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background      后台执行参数
     * @return
     */
    default String create(String path, YesOrNoEnum creatingParents,
                          CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, creatingParents, createMode, ttl, background);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background      后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path, YesOrNoEnum creatingParents,
                                     CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(instanceName, path, List.of(), creatingParents, createMode, ttl, background);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path       节点路径
     * @param aclList    权限列表
     * @param createMode 节点类型
     * @param ttl        过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                   or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background 后台执行参数
     * @return
     */
    default String create(String path, List<ACL> aclList,
                          CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, aclList, createMode, ttl, background);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param aclList      权限列表
     * @param createMode   节点类型
     * @param ttl          过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                     or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background   后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path, List<ACL> aclList,
                                     CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(instanceName, path, aclList, (YesOrNoEnum) null, createMode, ttl, background);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path            节点路径
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background      后台执行参数
     * @return
     */
    default String create(String path, List<ACL> aclList, YesOrNoEnum creatingParents,
                          CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, aclList, creatingParents, createMode, ttl, background);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background      后台执行参数
     * @return
     */
    String createForInstance(String instanceName, String path, List<ACL> aclList, YesOrNoEnum creatingParents,
                             CreateMode createMode, long ttl, ZookeeperBackground background);


    /**
     * 在默认实例创建一个节点
     *
     * @param path 节点路径
     * @param data 节点数据
     * @return
     */
    default String create(String path, Object data) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data) {
        return createForInstance(instanceName, path, data,
                null, EphemeralType.TTL.maxValue(), null);
    }


    /**
     * 在默认实例创建一个节点
     *
     * @param path            节点路径
     * @param data            节点数据
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @return
     */
    default String create(String path, Object data, YesOrNoEnum creatingParents) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, creatingParents);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param data            节点数据
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data, YesOrNoEnum creatingParents) {
        return createForInstance(instanceName, path, data, creatingParents,
                null, EphemeralType.TTL.maxValue(), null);
    }


    /**
     * 在默认实例创建一个节点
     *
     * @param path    节点路径
     * @param data    节点数据
     * @param aclList 权限列表
     * @return
     */
    default String create(String path, Object data, List<ACL> aclList) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, aclList);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @param aclList      权限列表
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data, List<ACL> aclList) {
        return createForInstance(instanceName, path, data, aclList,
                null, EphemeralType.TTL.maxValue(), null);
    }


    /**
     * 在默认实例创建一个节点
     *
     * @param path            节点路径
     * @param data            节点数据
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @return
     */
    default String create(String path, Object data, List<ACL> aclList, YesOrNoEnum creatingParents) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, aclList, creatingParents);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param data            节点数据
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data, List<ACL> aclList,
                                     YesOrNoEnum creatingParents) {
        return createForInstance(instanceName, path, data, aclList, creatingParents,
                null, EphemeralType.TTL.maxValue(), null);
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path       节点路径
     * @param data       节点数据
     * @param createMode 节点类型
     * @return
     */
    default String create(String path, Object data, CreateMode createMode) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, createMode);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @param createMode   节点类型
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data, CreateMode createMode) {
        return createForInstance(instanceName, path, data, createMode, EphemeralType.TTL.maxValue(), null);
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path            节点路径
     * @param data            节点数据
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @return
     */
    default String create(String path, Object data, YesOrNoEnum creatingParents, CreateMode createMode) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, creatingParents, createMode);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param data            节点数据
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data, YesOrNoEnum creatingParents,
                                     CreateMode createMode) {
        return createForInstance(instanceName, path, data, creatingParents,
                createMode, EphemeralType.TTL.maxValue(), null);
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path       节点路径
     * @param data       节点数据
     * @param aclList    权限列表
     * @param createMode 节点类型
     * @return
     */
    default String create(String path, Object data, List<ACL> aclList, CreateMode createMode) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, aclList, createMode);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @param aclList      权限列表
     * @param createMode   节点类型
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data,
                                     List<ACL> aclList, CreateMode createMode) {
        return createForInstance(instanceName, path, data, aclList,
                createMode, EphemeralType.TTL.maxValue(), null);
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path            节点路径
     * @param data            节点数据
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @return
     */
    default String create(String path, Object data, List<ACL> aclList,
                          YesOrNoEnum creatingParents, CreateMode createMode) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, aclList,
                creatingParents, createMode);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param data            节点数据
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data,
                                     List<ACL> aclList, YesOrNoEnum creatingParents, CreateMode createMode) {
        return createForInstance(instanceName, path, data, aclList, creatingParents,
                createMode, EphemeralType.TTL.maxValue(), null);
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path       节点路径
     * @param data       节点数据
     * @param createMode 节点类型
     * @param ttl        过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                   or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String create(String path, Object data, CreateMode createMode, long ttl) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, createMode, ttl);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @param createMode   节点类型
     * @param ttl          过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                     or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data, CreateMode createMode, long ttl) {
        return createForInstance(instanceName, path, data, createMode, ttl, null);
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path            节点路径
     * @param data            节点数据
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String create(String path, Object data, YesOrNoEnum creatingParents, CreateMode createMode, long ttl) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, creatingParents, createMode, ttl);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param data            节点数据
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data,
                                     YesOrNoEnum creatingParents, CreateMode createMode, long ttl) {
        return createForInstance(instanceName, path, data, creatingParents, createMode, ttl, null);
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path       节点路径
     * @param data       节点数据
     * @param aclList    权限列表
     * @param createMode 节点类型
     * @param ttl        过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                   or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String create(String path, Object data, List<ACL> aclList, CreateMode createMode, long ttl) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, aclList, createMode, ttl);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @param aclList      权限列表
     * @param createMode   节点类型
     * @param ttl          过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                     or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data,
                                     List<ACL> aclList, CreateMode createMode, long ttl) {
        return createForInstance(instanceName, path, data, aclList, createMode, ttl, null);
    }

    /**
     * 在默认实例创建一个指定类型的节点
     *
     * @param path            节点路径
     * @param data            节点数据
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String create(String path, Object data, List<ACL> aclList,
                          YesOrNoEnum creatingParents, CreateMode createMode, long ttl) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, aclList,
                creatingParents, createMode, ttl);
    }

    /**
     * 在指定实例创建一个指定类型的节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param data            节点数据
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data,
                                     List<ACL> aclList, YesOrNoEnum creatingParents, CreateMode createMode, long ttl) {
        return createForInstance(instanceName, path, data, aclList, creatingParents, createMode, ttl, null);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path       节点路径
     * @param data       节点数据
     * @param background 后台执行参数
     * @return
     */
    default String create(String path, Object data, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, background);
    }


    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @param background   后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data, ZookeeperBackground background) {
        return createForInstance(instanceName, path, data, null, EphemeralType.TTL.maxValue(), background);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path            节点路径
     * @param data            节点数据
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param background      后台执行参数
     * @return
     */
    default String create(String path, Object data, YesOrNoEnum creatingParents, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, creatingParents, background);
    }


    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param data            节点数据
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param background      后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data,
                                     YesOrNoEnum creatingParents, ZookeeperBackground background) {
        return createForInstance(instanceName, path, data,
                creatingParents, null, EphemeralType.TTL.maxValue(), background);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path       节点路径
     * @param data       节点数据
     * @param aclList    权限列表
     * @param background 后台执行参数
     * @return
     */
    default String create(String path, Object data, List<ACL> aclList, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, aclList, background);
    }


    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @param aclList      权限列表
     * @param background   后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data, List<ACL> aclList,
                                     ZookeeperBackground background) {
        return createForInstance(instanceName, path, data, aclList,
                null, EphemeralType.TTL.maxValue(), background);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path            节点路径
     * @param data            节点数据
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param background      后台执行参数
     * @return
     */
    default String create(String path, Object data, List<ACL> aclList, YesOrNoEnum creatingParents,
                          ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, aclList, creatingParents, background);
    }


    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param data            节点数据
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param background      后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data,
                                     List<ACL> aclList, YesOrNoEnum creatingParents, ZookeeperBackground background) {
        return createForInstance(instanceName, path, data, aclList, creatingParents,
                null, EphemeralType.TTL.maxValue(), background);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path       节点路径
     * @param data       节点数据
     * @param createMode 节点类型
     * @param ttl        过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                   or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background 后台执行参数
     * @return
     */
    default String create(String path, Object data,
                          CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, createMode, ttl, background);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @param createMode   节点类型
     * @param ttl          过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                     or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background   后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data,
                                     CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(instanceName, path, data, List.of(), createMode, ttl, background);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path            节点路径
     * @param data            节点数据
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background      后台执行参数
     * @return
     */
    default String create(String path, Object data, YesOrNoEnum creatingParents,
                          CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data,
                creatingParents, createMode, ttl, background);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param data            节点数据
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background      后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data, YesOrNoEnum creatingParents,
                                     CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(instanceName, path, data, List.of(), creatingParents, createMode, ttl, background);
    }

    /**
     * 在默认实例创建一个节点
     *
     * @param path       节点路径
     * @param data       节点数据
     * @param aclList    权限列表
     * @param createMode 节点类型
     * @param ttl        过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                   or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background 后台执行参数
     * @return
     */
    default String create(String path, Object data, List<ACL> aclList,
                          CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data,
                aclList, createMode, ttl, background);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param data         节点数据
     * @param aclList      权限列表
     * @param createMode   节点类型
     * @param ttl          过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                     or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background   后台执行参数
     * @return
     */
    default String createForInstance(String instanceName, String path, Object data, List<ACL> aclList,
                                     CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(instanceName, path, data, aclList, null, createMode, ttl, background);
    }


    /**
     * 在默认实例创建一个节点
     *
     * @param path            节点路径
     * @param data            节点数据
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     * @param createMode      节点类型
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background      后台执行参数
     * @return
     */
    default String create(String path, Object data, List<ACL> aclList, YesOrNoEnum creatingParents,
                          CreateMode createMode, long ttl, ZookeeperBackground background) {
        return createForInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, path, data, aclList,
                creatingParents, createMode, ttl, background);
    }

    /**
     * 在指定实例创建一个节点
     *
     * @param instanceName    实例名称
     * @param path            节点路径
     * @param data            节点数据
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link org.apache.zookeeper.CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link org.apache.zookeeper.CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background      后台执行参数
     * @return
     */
    String createForInstance(String instanceName, String path, Object data,
                             List<ACL> aclList, YesOrNoEnum creatingParents,
                             CreateMode createMode, long ttl, ZookeeperBackground background);
}
