package top.mingempty.sequence.api.impl.wrapper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.sequence.api.impl.AbstractExpirationStrategySequence;
import top.mingempty.sequence.api.impl.base.ZookeeperSequence;
import top.mingempty.sequence.enums.ExpirationStrategyEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Zookeeper序号生成器包装
 *
 * @author zzhao
 */
@Slf4j
public class ZookeeperSequenceWrapper extends AbstractExpirationStrategySequence<ZookeeperSequence> {


    private ZookeeperSequenceWrapper(ZookeeperSequence zookeeperSequence, ExpirationStrategyEnum expirationStrategy) {
        super(zookeeperSequence, expirationStrategy);
    }

    public static ZookeeperSequenceWrapper gainInstance(String seqName) {
        return gainInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName);
    }

    public static ZookeeperSequenceWrapper gainInstance(String instanceName, String seqName) {
        return ZookeeperSequenceWrapper.ZookeeperSequenceHolder.ZOOKEEPER_SEQUENCE_WRAPPER_MAP.computeIfAbsent(instanceName, k -> new ConcurrentHashMap<>(0)).get(seqName);
    }

    public static class ZookeeperSequenceHolder {

        private static final Map<String, Map<String, ZookeeperSequenceWrapper>> ZOOKEEPER_SEQUENCE_WRAPPER_MAP = new ConcurrentHashMap<>();

        public static void init(String seqName, CuratorFramework curatorFramework) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, curatorFramework);
        }

        public static void init(String seqName, int step, CuratorFramework curatorFramework) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, step, curatorFramework);
        }

        public static void init(String seqName, int step, ExpirationStrategyEnum expirationStrategy, CuratorFramework curatorFramework) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, step, expirationStrategy, curatorFramework);
        }

        public static void init(String instanceName, String seqName, CuratorFramework curatorFramework) {
            init(instanceName, seqName, 50, curatorFramework);
        }

        public static void init(String instanceName, String seqName, int step, CuratorFramework curatorFramework) {
            init(instanceName, seqName, step, ExpirationStrategyEnum.NONE, curatorFramework);
        }

        public static void init(String instanceName, String seqName, int step, ExpirationStrategyEnum expirationStrategy, CuratorFramework curatorFramework) {
            if (ZOOKEEPER_SEQUENCE_WRAPPER_MAP.containsKey(instanceName)) {
                log.error("ZookeeperSequenceWrapper has init!!!!!!!");
                return;
            }

            ZookeeperSequence.ZookeeperSequenceHolder.init(instanceName, seqName, step, curatorFramework);
            ZookeeperSequence zookeeperSequence = ZookeeperSequence.gainInstance(instanceName, seqName);
            ZOOKEEPER_SEQUENCE_WRAPPER_MAP.computeIfAbsent(instanceName, k -> new ConcurrentHashMap<>(0))
                    .computeIfAbsent(seqName, k -> new ZookeeperSequenceWrapper(zookeeperSequence, expirationStrategy));
        }
    }
}
