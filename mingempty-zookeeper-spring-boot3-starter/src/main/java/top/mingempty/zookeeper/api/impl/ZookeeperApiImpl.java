package top.mingempty.zookeeper.api.impl;

import cn.hutool.core.collection.CollUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.ACLBackgroundPathAndBytesable;
import org.apache.curator.framework.api.AddWatchBuilder;
import org.apache.curator.framework.api.AddWatchBuilder2;
import org.apache.curator.framework.api.BackgroundPathAndBytesable;
import org.apache.curator.framework.api.BackgroundPathable;
import org.apache.curator.framework.api.BackgroundPathableQuietlyable;
import org.apache.curator.framework.api.BackgroundVersionable;
import org.apache.curator.framework.api.ChildrenDeletable;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.DeleteBuilder;
import org.apache.curator.framework.api.DeleteBuilderMain;
import org.apache.curator.framework.api.ExistsBuilder;
import org.apache.curator.framework.api.ExistsBuilderMain;
import org.apache.curator.framework.api.GetACLBuilder;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.api.GetDataWatchBackgroundStatable;
import org.apache.curator.framework.api.PathAndBytesable;
import org.apache.curator.framework.api.Pathable;
import org.apache.curator.framework.api.ProtectACLCreateModeStatPathAndBytesable;
import org.apache.curator.framework.api.RemoveWatchesLocal;
import org.apache.curator.framework.api.RemoveWatchesType;
import org.apache.curator.framework.api.SetACLBuilder;
import org.apache.curator.framework.api.SetDataBuilder;
import org.apache.curator.framework.api.WatchesBuilder;
import org.apache.zookeeper.AddWatchMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.Assert;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.domain.enums.YesOrNoEnum;
import top.mingempty.zookeeper.api.ZookeeperApi;
import top.mingempty.zookeeper.entity.ZookeeperBackground;
import top.mingempty.zookeeper.entity.ZookeeperProperties;
import top.mingempty.zookeeper.entity.ZookeeperWatchable;
import top.mingempty.zookeeper.entity.wapper.CuratorFrameworkWrapper;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * zookeeper常用方法实现
 *
 * @author zzhao
 */
@AllArgsConstructor
public class ZookeeperApiImpl implements ZookeeperApi {

    private final ZookeeperProperties zookeeperProperties;
    private final CuratorFrameworkWrapper curatorFrameworkWrapper;

    /**
     * 将指定实例的CuratorFramework客户端
     *
     * @param instanceName
     * @return
     */
    @Override
    public CuratorFramework gainCuratorFrameworkForInstance(String instanceName) {
        return this.curatorFrameworkWrapper.getResolvedRouter(instanceName);
    }

    /**
     * 通过PathAndBytesable处理一个节点
     *
     * @param pathAndBytesable 节点管理器
     * @param path             节点路径
     * @return
     */
    @Override
    @SneakyThrows
    public <T> T pathAndBytesable(PathAndBytesable<T> pathAndBytesable, String path) {
        return pathAndBytesable.forPath(path);
    }

    /**
     * 通过PathAndBytesable处理一个节点
     *
     * @param pathAndBytesable 节点管理器
     * @param path             节点路径
     * @param data             节点数据
     * @return
     */
    @Override
    @SneakyThrows
    public <T> T pathAndBytesable(PathAndBytesable<T> pathAndBytesable, String path, Object data) {
        byte[] bytes = Optional.ofNullable(data)
                .flatMap(data1 -> Optional.of(JsonUtil.toStr(data1)))
                .flatMap(data2 -> Optional.of(data2.getBytes(StandardCharsets.UTF_8)))
                .orElse(new byte[]{});
        return pathAndBytesable.forPath(path, bytes);
    }

    /**
     * 通过Pathable处理一个节点
     *
     * @param pathable 节点管理器
     * @param path     节点路径
     */
    @Override
    @SneakyThrows
    public <T> T pathable(Pathable<T> pathable, String path) {
        return pathable.forPath(path);
    }

    /**
     * 将指定实例内的指定节点设置为容器节点
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     */
    @Override
    @SneakyThrows
    public void createContainersForInstance(String instanceName, String path) {
        this.curatorFrameworkWrapper.getResolvedRouter(instanceName).createContainers(path);
    }

    /**
     * 获取指定实例节点创建构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    @Override
    public CreateBuilder gainCreateBuilderForInstance(String instanceName) {
        return gainCuratorFrameworkForInstance(instanceName).create();
    }

    /**
     * 获取指定实例节点创建构建器
     *
     * @param instanceName    实例名称
     * @param aclList         权限列表
     * @param creatingParents 父级存在时，是否创。
     *                        值空时不创建,值为{@link YesOrNoEnum#NO}时创建为普通节点,值为{@link YesOrNoEnum#YES}时创建为容器节点。
     * @param createMode      节点类型
     * @param ttl             过期时间，当 {@code  createMode} 为{@link CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background      后台执行参数
     * @return
     */
    @Override
    public PathAndBytesable<String> gainPathAndBytesableForInstance(String instanceName,
                                                                    List<ACL> aclList, YesOrNoEnum creatingParents,
                                                                    CreateMode createMode, long ttl,
                                                                    ZookeeperBackground background) {
        CreateBuilder createBuilder = gainCreateBuilderForInstance(instanceName);
        ACLBackgroundPathAndBytesable<String> aclBackgroundPathAndBytesable = createBuilder;
        if (creatingParents != null) {
            ProtectACLCreateModeStatPathAndBytesable<String> pathAndBytesable
                    = YesOrNoEnum.YES.equals(creatingParents)
                    ? createBuilder.creatingParentsIfNeeded() : createBuilder.creatingParentContainersIfNeeded();
            if (CreateMode.EPHEMERAL_SEQUENTIAL.equals(createMode)) {
                aclBackgroundPathAndBytesable = pathAndBytesable.withProtection().withMode(createMode);
            } else if (CreateMode.PERSISTENT_WITH_TTL.equals(createMode)
                    || CreateMode.PERSISTENT_SEQUENTIAL_WITH_TTL.equals(createMode)) {
                aclBackgroundPathAndBytesable = createBuilder.withTtl(ttl).withMode(createMode);
            } else if (createMode != null) {
                aclBackgroundPathAndBytesable = pathAndBytesable.withMode(createMode);
            }
        } else {
            if (CreateMode.EPHEMERAL_SEQUENTIAL.equals(createMode)) {
                aclBackgroundPathAndBytesable = createBuilder.withProtection().withMode(createMode);
            } else if (CreateMode.PERSISTENT_WITH_TTL.equals(createMode)
                    || CreateMode.PERSISTENT_SEQUENTIAL_WITH_TTL.equals(createMode)) {
                aclBackgroundPathAndBytesable = createBuilder.withTtl(ttl).withMode(createMode);
            } else if (createMode != null) {
                aclBackgroundPathAndBytesable = createBuilder.withMode(createMode);
            }
        }

        BackgroundPathAndBytesable<String> backgroundPathAndBytesable
                = CollUtil.isEmpty(aclList)
                ? aclBackgroundPathAndBytesable : aclBackgroundPathAndBytesable.withACL(aclList);

        return Optional.ofNullable(background)
                .flatMap(bg -> Optional.of(bg.inBackgroundWithPathAndBytesable(backgroundPathAndBytesable)))
                .orElse(backgroundPathAndBytesable);
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
     * @param ttl             过期时间，当 {@code  createMode} 为{@link CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background      后台执行参数
     * @return
     */
    @Override
    public String createForInstance(String instanceName, String path,
                                    List<ACL> aclList, YesOrNoEnum creatingParents,
                                    CreateMode createMode, long ttl, ZookeeperBackground background) {
        return pathAndBytesable(gainPathAndBytesableForInstance(instanceName, aclList, creatingParents,
                createMode, ttl, background), path);
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
     * @param ttl             过期时间，当 {@code  createMode} 为{@link CreateMode#PERSISTENT_WITH_TTL}
     *                        or {@link CreateMode#PERSISTENT_SEQUENTIAL_WITH_TTL}时有效
     * @param background      后台执行参数
     * @return
     */
    @Override
    public String createForInstance(String instanceName, String path, Object data,
                                    List<ACL> aclList, YesOrNoEnum creatingParents,
                                    CreateMode createMode, long ttl, ZookeeperBackground background) {
        return pathAndBytesable(gainPathAndBytesableForInstance(instanceName, aclList, creatingParents,
                createMode, ttl, background), path, data);
    }

    /**
     * 获取指定实例节点删除构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    @Override
    public DeleteBuilder gainDeleteBuilderForInstance(String instanceName) {
        return gainCuratorFrameworkForInstance(instanceName).delete();
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
    @Override
    public Pathable<Void> gainDeletePathableForInstance(String instanceName, boolean quietly,
                                                        boolean guaranteed, boolean deletingChildrenIfNeeded,
                                                        int version, ZookeeperBackground background) {
        BackgroundVersionable backgroundVersionable = gainDeleteBuilderForInstance(instanceName);
        if (quietly
                && backgroundVersionable instanceof DeleteBuilder deleteBuilder) {
            backgroundVersionable = deleteBuilder.quietly();
        }

        if (guaranteed
                && backgroundVersionable instanceof DeleteBuilderMain deleteBuilderMain) {
            backgroundVersionable = deleteBuilderMain.guaranteed();

        }

        if (deletingChildrenIfNeeded
                && backgroundVersionable instanceof ChildrenDeletable childrenDeletable) {
            backgroundVersionable = childrenDeletable.deletingChildrenIfNeeded();
        }
        BackgroundPathable<Void> backgroundPathable;
        if (version > -1) {
            backgroundPathable = backgroundVersionable.withVersion(version);
        } else {
            backgroundPathable = backgroundVersionable;
        }
        return Optional.ofNullable(background)
                .flatMap(bg -> Optional.of(background.inBackgroundWithPathable(backgroundPathable)))
                .orElse(backgroundPathable);
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
    @Override
    public void deleteForInstance(String instanceName, String path, boolean quietly,
                                  boolean guaranteed, boolean deletingChildrenIfNeeded,
                                  int version, ZookeeperBackground background) {
        pathable(gainDeletePathableForInstance(instanceName, quietly, guaranteed, deletingChildrenIfNeeded,
                version, background), path);
    }

    /**
     * 获取指定实例指定节点的子节点构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    @Override
    public GetChildrenBuilder gainChildrenBuilderForInstance(String instanceName) {
        return gainCuratorFrameworkForInstance(instanceName).getChildren();
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
    @Override
    public Pathable<List<String>> gainChildrenPathableForInstance(String instanceName, Stat stat,
                                                                  ZookeeperBackground background,
                                                                  ZookeeperWatchable watchable) {

        GetChildrenBuilder getChildrenBuilder = gainChildrenBuilderForInstance(instanceName);
        Optional<ZookeeperWatchable> optionalWatcher = Optional.ofNullable(watchable);

        if (stat != null) {
            return optionalWatcher
                    .flatMap(watcher -> Optional.of(watcher.getWatcher(getChildrenBuilder.storingStatIn(stat))))
                    .orElse(getChildrenBuilder.storingStatIn(stat));

        } else if (background != null) {
            return optionalWatcher
                    .flatMap(watcher -> Optional.of(watcher.getWatcher(getChildrenBuilder)))
                    .flatMap(bgp -> Optional.of(background.inBackgroundWithPathable(bgp)))
                    .orElse(getChildrenBuilder);
        }
        return optionalWatcher
                .flatMap(watcher -> Optional.of(watcher.getWatcher(getChildrenBuilder)))
                .orElse(getChildrenBuilder);
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
    @Override
    @SneakyThrows
    public List<String> childrenForInstance(String instanceName, String path, Stat stat,
                                            ZookeeperBackground background, ZookeeperWatchable watchable) {
        return pathable(gainChildrenPathableForInstance(instanceName, stat, background, watchable), path);
    }


    /**
     * 获取指定实例指定节点的ACL构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    @Override
    public GetACLBuilder gainGetACLBuilderForInstance(String instanceName) {
        return gainCuratorFrameworkForInstance(instanceName).getACL();
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
    @Override
    public Pathable<List<ACL>> gainACLPathableForInstance(String instanceName, Stat stat, ZookeeperBackground background) {
        GetACLBuilder getACLBuilder = gainGetACLBuilderForInstance(instanceName);
        return Optional.ofNullable(stat)
                .flatMap(s -> Optional.of(getACLBuilder.storingStatIn(s)))
                .or(() -> Optional.ofNullable(background)
                        .flatMap(bgp -> Optional.of(bgp.inBackgroundWithPathable(getACLBuilder))))
                .orElse(getACLBuilder);
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
    @Override
    public List<ACL> aclForInstance(String instanceName, String path, Stat stat, ZookeeperBackground background) {
        return pathable(gainACLPathableForInstance(instanceName, stat, background), path);
    }

    /**
     * 获取指定实例指定节点的设置ACL构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    @Override
    public SetACLBuilder gainSetACLBuilderForInstance(String instanceName) {
        return gainCuratorFrameworkForInstance(instanceName).setACL();
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
    @Override
    public Pathable<Stat> gainACLPathableForInstance(String instanceName, List<ACL> aclList,
                                                     int version, ZookeeperBackground background) {
        SetACLBuilder setACLBuilder = gainSetACLBuilderForInstance(instanceName);
        BackgroundPathable<Stat> statBackgroundPathable = setACLBuilder.withVersion(version).withACL(aclList);
        return Optional.ofNullable(background)
                .flatMap(bgp -> Optional.of(bgp.inBackgroundWithPathable(statBackgroundPathable)))
                .orElse(statBackgroundPathable);
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
    @Override
    public Stat aclForInstance(String instanceName, String path, List<ACL> aclList,
                               int version, ZookeeperBackground background) {
        return pathable(gainACLPathableForInstance(instanceName, aclList, version, background), path);
    }

    /**
     * 获取指定实例指定节点的数据构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    @Override
    public GetDataBuilder gainGetDataBuilderForInstance(String instanceName) {
        return gainCuratorFrameworkForInstance(instanceName).getData();
    }

    /**
     * 获取指定实例的指定节点的数据
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param instanceName 实例名称
     * @param decompressed 使用配置的压缩提供程序对数据进行解压缩
     * @param stat         获取当前节点状态
     * @param background   后台执行参数
     * @param watchable    zookeeper监听器
     * @return
     */
    @Override
    public Pathable<byte[]> gainDataPathableForInstance(String instanceName, boolean decompressed, Stat stat,
                                                        ZookeeperBackground background,
                                                        ZookeeperWatchable watchable) {
        GetDataBuilder getDataBuilder = gainGetDataBuilderForInstance(instanceName);
        Optional<ZookeeperWatchable> optionalWatcher = Optional.ofNullable(watchable);
        if (decompressed) {
            GetDataWatchBackgroundStatable getDataWatchBackgroundStatable = getDataBuilder.decompressed();
            if (stat != null) {
                return optionalWatcher
                        .flatMap(watcher -> Optional.of(watcher.getWatcher(getDataWatchBackgroundStatable.storingStatIn(stat))))
                        .orElse(getDataWatchBackgroundStatable.storingStatIn(stat));

            } else if (background != null) {
                return optionalWatcher
                        .flatMap(watcher -> Optional.of(watcher.getWatcher(getDataWatchBackgroundStatable)))
                        .flatMap(bgp -> Optional.of(background.inBackgroundWithPathable(bgp)))
                        .orElse(getDataWatchBackgroundStatable);
            }
            return optionalWatcher
                    .flatMap(watcher -> Optional.of(watcher.getWatcher(getDataWatchBackgroundStatable)))
                    .orElse(getDataWatchBackgroundStatable);
        } else {
            if (stat != null) {
                return optionalWatcher
                        .flatMap(watcher -> Optional.of(watcher.getWatcher(getDataBuilder.storingStatIn(stat))))
                        .orElse(getDataBuilder.storingStatIn(stat));

            } else if (background != null) {
                return optionalWatcher
                        .flatMap(watcher -> Optional.of(watcher.getWatcher(getDataBuilder)))
                        .flatMap(bgp -> Optional.of(background.inBackgroundWithPathable(bgp)))
                        .orElse(getDataBuilder);
            }
            return optionalWatcher
                    .flatMap(watcher -> Optional.of(watcher.getWatcher(getDataBuilder)))
                    .orElse(getDataBuilder);
        }
    }

    /**
     * 获取指定实例的指定节点的数据
     * 参数{@code stat}和参数{@code background}两者不能共存，{@code stat}优先级最高
     *
     * @param instanceName 实例名称
     * @param path         节点路径
     * @param decompressed
     * @param stat         获取当前节点状态
     * @param background   后台执行参数
     * @param watchable    zookeeper监听器
     * @return
     */
    @Override
    public String dataForInstance(String instanceName, String path, boolean decompressed, Stat stat,
                                  ZookeeperBackground background, ZookeeperWatchable watchable) {
        return Optional
                .ofNullable(pathable(gainDataPathableForInstance(instanceName, decompressed,
                        stat, background, watchable), path))
                .flatMap(bytes -> Optional.of(new String(bytes)))
                .orElse(null);
    }

    /**
     * 获取指定实例指定节点的设置数据构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    @Override
    public SetDataBuilder gainSetDataBuilderForInstance(String instanceName) {
        return gainCuratorFrameworkForInstance(instanceName).setData();
    }

    /**
     * 获取设置指定实例的指定节点的数据 Pathable
     *
     * @param instanceName 实例名称
     * @param version      指定版本
     * @param background   后台执行参数
     * @return
     */
    @Override
    public PathAndBytesable<Stat> gainSetDataPathableForInstance(String instanceName, int version, ZookeeperBackground background) {
        BackgroundPathAndBytesable<Stat> statBackgroundPathAndBytesable = gainSetDataBuilderForInstance(instanceName).withVersion(version);
        return Optional.ofNullable(background)
                .flatMap(bgp -> Optional.of(bgp.inBackgroundWithPathAndBytesable(statBackgroundPathAndBytesable)))
                .orElse(statBackgroundPathAndBytesable);

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
    @Override
    public Stat setDataForInstance(String instanceName, String path, int version, ZookeeperBackground background) {
        return pathAndBytesable(gainSetDataPathableForInstance(instanceName, version, background), path);
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
    @Override
    public Stat setDataForInstance(String instanceName, String path, Object data, int version, ZookeeperBackground background) {
        return pathAndBytesable(gainSetDataPathableForInstance(instanceName, version, background), path, data);
    }

    /**
     * 获取指定实例指定节点的存在构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    @Override
    public ExistsBuilder gainExistsBuilderForInstance(String instanceName) {
        return gainCuratorFrameworkForInstance(instanceName).checkExists();
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
    @Override
    public Pathable<Stat> gainExistsPathableForInstance(String instanceName,
                                                        YesOrNoEnum creatingParents, List<ACL> aclList,
                                                        ZookeeperBackground background, ZookeeperWatchable watchable) {
        ExistsBuilder existsBuilder = gainExistsBuilderForInstance(instanceName);
        ExistsBuilderMain existsBuilderMain = Optional.ofNullable(creatingParents)
                .flatMap(yesOrNoEnum
                        -> Optional.of(YesOrNoEnum.YES.equals(yesOrNoEnum)
                        ? existsBuilder.creatingParentContainersIfNeeded() : existsBuilder.creatingParentsIfNeeded()))
                .flatMap(acLableExistBuilderMain
                        -> Optional.of(acLableExistBuilderMain.withACL(CollUtil.isEmpty(aclList)
                        ? ZooDefs.Ids.OPEN_ACL_UNSAFE : aclList)))
                .orElse(existsBuilder);

        BackgroundPathable<Stat> backgroundPathable = Optional.ofNullable(watchable)
                .flatMap(watcher -> Optional.of(watcher.getWatcher(existsBuilderMain)))
                .orElse(existsBuilderMain);

        return Optional.ofNullable(background)
                .flatMap(bgp -> Optional.of(bgp.inBackgroundWithPathable(backgroundPathable)))
                .orElse(backgroundPathable);
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
    @Override
    public Stat existsForInstance(String instanceName, String path,
                                  YesOrNoEnum creatingParents, List<ACL> aclList,
                                  ZookeeperBackground background, ZookeeperWatchable watchable) {
        return pathable(gainExistsPathableForInstance(instanceName, creatingParents, aclList, background, watchable),
                path);
    }

    /**
     * 获取指定实例节点监视构建器
     *
     * @param instanceName 实例名称
     * @return
     */
    @Override
    public WatchesBuilder gainWatchesBuilderForInstance(String instanceName) {
        return gainCuratorFrameworkForInstance(instanceName).watchers();
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
    @Override
    public Pathable<Void> gainRemoveWatchesPathableForInstance(String instanceName, ZookeeperWatchable watchable,
                                                               Watcher.WatcherType watcherType,
                                                               boolean locally, boolean quietly,
                                                               boolean guaranteed, ZookeeperBackground background) {
        Assert.notNull(watchable, "watchable must not be null");
        RemoveWatchesType removeWatchesType = watchable.removeWatcher(gainWatchesBuilderForInstance(instanceName));
        if (guaranteed) {
            BackgroundPathableQuietlyable<Void> backgroundPathableQuietlyable = removeWatchesType.guaranteed();
            BackgroundPathable<Void> backgroundPathable;
            if (quietly) {
                backgroundPathable = backgroundPathableQuietlyable.quietly();
            } else {
                backgroundPathable = backgroundPathableQuietlyable;
            }

            return Optional.ofNullable(background)
                    .flatMap(bgp -> Optional.of(bgp.inBackgroundWithPathable(backgroundPathable)))
                    .orElse(backgroundPathable);
        }
        RemoveWatchesLocal removeWatchesLocal = Optional.ofNullable(watcherType)
                .flatMap(type -> Optional.ofNullable(removeWatchesType.ofType(watcherType)))
                .orElse(removeWatchesType);

        BackgroundPathableQuietlyable<Void> backgroundPathableQuietlyable;
        if (locally) {
            backgroundPathableQuietlyable = removeWatchesLocal.locally();
        } else {
            backgroundPathableQuietlyable = removeWatchesLocal;
        }
        BackgroundPathable<Void> backgroundPathable;
        if (quietly) {
            backgroundPathable = backgroundPathableQuietlyable.quietly();
        } else {
            backgroundPathable = backgroundPathableQuietlyable;
        }
        return Optional.ofNullable(background)
                .flatMap(bgp -> Optional.of(bgp.inBackgroundWithPathable(backgroundPathable)))
                .orElse(backgroundPathable);
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
    @Override
    public void watchesRemoveForInstance(String instanceName, String path, ZookeeperWatchable watchable,
                                         Watcher.WatcherType watcherType, boolean locally, boolean quietly,
                                         boolean guaranteed, ZookeeperBackground background) {
        pathable(gainRemoveWatchesPathableForInstance(instanceName, watchable, watcherType, locally, quietly,
                guaranteed, background), path);
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
    @Override
    public Pathable<Void> gainAddWatchesForInstance(String instanceName, AddWatchMode addWatchMode,
                                                    ZookeeperWatchable watchable,
                                                    ZookeeperBackground background) {
        AddWatchBuilder addWatchBuilder = gainWatchesBuilderForInstance(instanceName).add();
        AddWatchBuilder2 addWatchBuilder2 = Optional.ofNullable(addWatchMode)
                .flatMap(mode -> Optional.ofNullable(addWatchBuilder.withMode(mode)))
                .orElse(addWatchBuilder);
        return Optional.ofNullable(watchable)
                .flatMap(watchable1 -> Optional.ofNullable(watchable1.getWatcher(
                        Optional.ofNullable(background)
                                .flatMap(bg -> Optional.ofNullable(bg.inBackground(addWatchBuilder2)))
                                .orElse(addWatchBuilder2)
                )))
                .orElse(addWatchBuilder2);
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
    @Override
    public void watchersForInstance(String instanceName, String path, AddWatchMode addWatchMode,
                                    ZookeeperWatchable watchable, ZookeeperBackground backgrounde) {
        pathable(gainAddWatchesForInstance(instanceName, addWatchMode, watchable, backgrounde), path);
    }
}
