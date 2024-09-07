package top.mingempty.zookeeper.factory;

import lombok.AllArgsConstructor;
import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.utils.NonAdminZookeeperFactory;
import top.mingempty.builder.WrapperBuilder;
import top.mingempty.commons.thread.ThreadFactoryBuilder;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.zookeeper.entity.MeAuthInfo;
import top.mingempty.zookeeper.entity.Zookeeper;
import top.mingempty.zookeeper.entity.ZookeeperConstant;
import top.mingempty.zookeeper.entity.ZookeeperProperties;
import top.mingempty.zookeeper.entity.enums.ZookeeperFactoryEnum;
import top.mingempty.zookeeper.entity.wapper.CuratorFrameworkWrapper;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;

/**
 * CuratorFramework 工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class MeCuratorFrameworkFactory implements WrapperBuilder<CuratorFrameworkWrapper, CuratorFramework, Zookeeper> {

    private final ZookeeperProperties zookeeperProperties;

    private static final NonAdminZookeeperFactory NON_ADMIN_ZOOKEEPER_FACTORY = new NonAdminZookeeperFactory();

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    @Override
    public CuratorFrameworkWrapper build() {
        Map<String, CuratorFramework> map = new ConcurrentHashMap<>();
        zookeeperProperties.gainAll()
                .entrySet()
                .parallelStream()
                .forEach(entry
                        -> map.put(entry.getKey(),
                        curatorFramework(entry.getKey(), entry.getValue())));
        return new CuratorFrameworkWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, map);
    }

    public CuratorFramework curatorFramework(String instanceName, Zookeeper zookeeper) {
        zookeeper.setThreadFactoryName(Optional.ofNullable(zookeeper.getThreadFactoryName())
                .orElse(instanceName + "-" + ZookeeperConstant.THREAD_FACTORY_NAME));
        return build(instanceName, zookeeper);
    }

    /**
     * 构建
     *
     * @param instanceName
     * @param zookeeper
     * @return 被构建的对象
     */
    @Override
    public CuratorFramework buildToSub(String instanceName, Zookeeper zookeeper) {
        List<AuthInfo> authInfoList = zookeeper.getAuthInfos()
                .parallelStream()
                .map(MeAuthInfo::generateAuthInfo)
                .toList();

        ThreadFactory threadFactory = ThreadFactoryBuilder.create().setNamePrefix(zookeeper.getThreadFactoryName()).build();

        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .authorization(authInfoList)
                .threadFactory(threadFactory)
                .connectString(zookeeper.getConnectString())
                .namespace(zookeeper.getNamespace())
                .retryPolicy(zookeeper.generateRetryPolicy())
                .sessionTimeoutMs(zookeeper.getSessionTimeoutMs())
                .connectionTimeoutMs(zookeeper.getConnectionTimeoutMs())
                .maxCloseWaitMs(zookeeper.getMaxCloseWaitMs())
                .simulatedSessionExpirationPercent(zookeeper.getSimulatedSessionExpirationPercent())
                .canBeReadOnly(zookeeper.isCanBeReadOnly())
                .waitForShutdownTimeoutMs(zookeeper.getWaitForShutdownTimeoutMs())
                .simulatedSessionExpirationPercent(zookeeper.getSimulatedSessionExpirationPercent());

        Optional.ofNullable(zookeeper.getDefaultData())
                .ifPresent(defaultData -> builder.defaultData(defaultData.getBytes(StandardCharsets.UTF_8)));

        if (!zookeeper.isUseContainerParentsIfAvailable()) {
            builder.dontUseContainerParents();
        }

        if (ZookeeperFactoryEnum.NO_ADMIN.equals(zookeeper.getZookeeperFactory())) {
            builder.zookeeperFactory(NON_ADMIN_ZOOKEEPER_FACTORY);
        }

        CuratorFramework curatorFramework = builder.build();
        curatorFramework.start();
        return curatorFramework;
    }
}
