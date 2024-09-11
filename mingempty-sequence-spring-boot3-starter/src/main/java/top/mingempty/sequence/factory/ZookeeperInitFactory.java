package top.mingempty.sequence.factory;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import top.mingempty.sequence.api.impl.base.SnowflakeIdWorkerSequence;
import top.mingempty.sequence.api.impl.base.ZookeeperSequence;
import top.mingempty.sequence.api.impl.wrapper.ZookeeperSequenceWrapper;
import top.mingempty.sequence.enums.SeqRealizeEnum;
import top.mingempty.sequence.exception.SequenceException;
import top.mingempty.sequence.model.CacheSequenceProperties;
import top.mingempty.util.SpringContextUtil;
import top.mingempty.zookeeper.entity.wapper.CuratorFrameworkWrapper;

import java.util.List;
import java.util.Set;

/**
 * 执行序号对Zookeeper相关的初始化
 */
@Slf4j
public class ZookeeperInitFactory implements InitFactory {
    private final CuratorFrameworkWrapper curatorFrameworkWrapper;

    public ZookeeperInitFactory() {
        this.curatorFrameworkWrapper = SpringContextUtil.gainBean("curatorFrameworkWrapper", CuratorFrameworkWrapper.class);
    }

    /**
     * 初始化类型
     */
    @Override
    public SeqRealizeEnum setRealize() {
        return SeqRealizeEnum.Zookeeper;
    }

    /**
     * 初始化雪花算法
     *
     * @param instanceNames 实例名称集合
     */
    @Override
    public void snowflakeIdWorker(Set<String> instanceNames) {
        if (CollUtil.isEmpty(instanceNames)) {
            return;
        }
        instanceNames.forEach(instanceName -> {
            CuratorFramework curatorFramework = curatorFrameworkWrapper.getResolvedRouter(instanceName);
            Assert.notNull(curatorFramework, "CuratorFramework is null");
            Integer baseId = createBaseId(curatorFramework);
            if (baseId == -1) {
                throw new SequenceException("seq00000002", SeqRealizeEnum.Zookeeper, instanceName);
            }
            createSnowflakeIdWorker(instanceName, baseId, false);
            registerRefreshListener(instanceName, curatorFramework);
        });

    }

    /**
     * 注册雪花算法
     *
     * @param instanceName 实例名称
     * @param baseId       初始化时使用的baseId
     * @param restart      是否重置
     */
    private void createSnowflakeIdWorker(String instanceName, int baseId, boolean restart) {
        if (!restart) {
            SnowflakeIdWorkerSequence.SnowflakeIdWorkerHolder.init(SeqRealizeEnum.Zookeeper, instanceName, baseId);
        } else {
            SnowflakeIdWorkerSequence.SnowflakeIdWorkerHolder.reInit(SeqRealizeEnum.Zookeeper, instanceName, baseId);
        }
    }

    /**
     * 从zk中注册baseId
     *
     * @param curatorFramework zk客户端
     */
    @SneakyThrows
    private int createBaseId(CuratorFramework curatorFramework) {
        String keyPrefix = SeqRealizeEnum.Zookeeper.getKeyPrefix().concat("SnowflakeId").concat(SeqRealizeEnum.Zookeeper.getSeparator());
        String allWorkFolder = keyPrefix.concat("all");
        String nowWorkFolder = keyPrefix.concat("now");
        // 获取baseId的最大值
        int maxId = 1 << (SnowflakeIdWorkerSequence.DATA_CENTER_ID_BITS + SnowflakeIdWorkerSequence.WORKER_ID_BITS);
        // 检测是否有所需的节点，无则建立
        curatorFramework.checkExists().creatingParentContainersIfNeeded().forPath(allWorkFolder + "/0");
        curatorFramework.checkExists().creatingParentContainersIfNeeded().forPath(nowWorkFolder + "/0");
        // 获取已经使用过的baseId集合
        List<String> allWork = curatorFramework.getChildren().forPath(allWorkFolder);
        // 获取当前正在使用的baseId集合
        List<String> nowWork = curatorFramework.getChildren().forPath(nowWorkFolder);
        int baseId = -1;
        int nowMaxId = -1;

        if (allWork != null && !allWork.isEmpty()) {
            // 将已经使用过的baseId从小到大排序
            allWork.sort((o1, o2) -> {
                if (o1.length() > o2.length()) {
                    return 1;
                } else if (o1.length() < o2.length()) {
                    return -1;
                }
                return o1.compareTo(o2);
            });
            // 获取当前已经使用的最大的baseId
            nowMaxId = Integer.parseInt(allWork.getLast());
            // 从已使用过的baseId集合中剔除当前正在使用的baseId集合
            if (nowWork != null && !nowWork.isEmpty()) {
                allWork.removeIf(nowWork::contains);
            }
            // 如果当前还有baseId未被使用，则利用临时节点抢占选取的baseId
            if (!allWork.isEmpty()) {
                for (String id : allWork) {
                    String path = nowWorkFolder + "/" + id;
                    try {
                        curatorFramework.create().withMode(CreateMode.EPHEMERAL)
                                .forPath(path);
                        baseId = Integer.parseInt(id);
                        break;
                    } catch (Exception e) {
                        log.error("zk lock path fail, path is {}.", path, e);
                    }
                }
            }
        }
        // 如果没有未使用的baseId或者抢占失败，则尝试申请新的baseId
        if (baseId == -1) {
            nowMaxId++;
            // 当当前的最大baseId小于id生成器所支持的最大baseId时则尝试申请
            while (nowMaxId < maxId) {
                baseId = nowMaxId;
                String path = nowWorkFolder + "/" + baseId;
                String allPath = allWorkFolder + "/" + baseId;
                try {
                    // 申请并抢占新的baseId
                    curatorFramework.create().withMode(CreateMode.PERSISTENT)
                            .forPath(allPath);
                    curatorFramework.create().withMode(CreateMode.EPHEMERAL)
                            .forPath(path);
                    break;
                } catch (Exception e) {
                    log.error("zk create path fail, path is {}.", path, e);
                }
                nowMaxId++;
            }
        }
        if (baseId >= maxId || nowMaxId >= maxId) {
            log.error("create snowflakeId fail, baseId is {}, nowMaxId is {}, maxId is {}", baseId, nowMaxId, maxId);
            return -1;
        } else {
            log.info("create snowflakeId success, baseId is {}, nowMaxId is {}, maxId is {}", baseId, nowMaxId, maxId);
            return baseId;
        }
    }

    /**
     * 监听zk重连
     *
     * @param curatorFramework zk客户端
     */
    private void registerRefreshListener(String instanceName, CuratorFramework curatorFramework) {
        curatorFramework.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            private int nowState = 1;

            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                switch (newState) {
                    case RECONNECTED:
                        if (nowState == 0) {
                            try {
                                createSnowflakeIdWorker(instanceName, createBaseId(client), true);
                            } catch (Exception e) {
                                log.error("recreate snowflakeId fail,because ", e);
                            }
                        }
                        nowState = 1;
                        break;
                    case LOST:
                        nowState = 0;
                        break;
                    default: {
                    }
                }
            }
        });
    }


    /**
     * 初始化缓存序号
     *
     * @param cacheSequenceProperties 缓存序号配置
     */
    @Override
    public void cacheSequence(CacheSequenceProperties cacheSequenceProperties) {
        if (cacheSequenceProperties.isExpirationStrategyInit()) {
            expirationStrategySequence(cacheSequenceProperties);
            return;
        }
        CuratorFramework curatorFramework = curatorFrameworkWrapper.getResolvedRouter(cacheSequenceProperties.getInstanceName());
        ZookeeperSequence.ZookeeperSequenceHolder.init(cacheSequenceProperties.getInstanceName(),
                cacheSequenceProperties.getSeqName(),
                cacheSequenceProperties.getStep(), curatorFramework);
    }

    /**
     * 初始化包装序号
     *
     * @param cacheSequenceProperties 缓存序号配置
     */
    @Override
    public void expirationStrategySequence(CacheSequenceProperties cacheSequenceProperties) {
        CuratorFramework curatorFramework = curatorFrameworkWrapper.getResolvedRouter(cacheSequenceProperties.getInstanceName());
        ZookeeperSequenceWrapper.ZookeeperSequenceHolder.init(cacheSequenceProperties.getInstanceName(),
                cacheSequenceProperties.getSeqName(), cacheSequenceProperties.getStep(),
                cacheSequenceProperties.getExpirationStrategy(), curatorFramework);
    }
}
