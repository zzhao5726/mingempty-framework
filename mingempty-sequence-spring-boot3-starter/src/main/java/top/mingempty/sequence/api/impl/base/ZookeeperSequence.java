package top.mingempty.sequence.api.impl.base;

import cn.hutool.core.lang.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.RetryForever;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.sequence.api.impl.AbstractCacheSequence;
import top.mingempty.sequence.enums.SeqRealizeEnum;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于Zookeeper实现带缓存的序号生成
 *
 * @author zzhao
 */
@Slf4j
public class ZookeeperSequence extends AbstractCacheSequence {

    private final static RetryForever RETRY_FOREVER = new RetryForever(1);

    private final CuratorFramework curatorFramework;
    private volatile DistributedAtomicLong distributedAtomicLong;


    private ZookeeperSequence(String seqName, CuratorFramework curatorFramework) {
        this(seqName, 50, curatorFramework);
    }

    private ZookeeperSequence(String seqName, int step, CuratorFramework curatorFramework) {
        super(SeqRealizeEnum.Zookeeper, seqName, step);
        this.curatorFramework = curatorFramework;
    }

    /**
     * 获取新的最大值和步长
     * <p>
     * key：获取到的最大值
     * value：步长
     */
    @Override
    protected final Pair<Long, Integer> max() {
        try {
            if (distributedAtomicLong == null) {
                synchronized (this) {
                    if (distributedAtomicLong == null) {
                        this.distributedAtomicLong = new DistributedAtomicLong(curatorFramework, key(), RETRY_FOREVER);
                    }
                }
            }
            AtomicValue<Long> atomicValue = distributedAtomicLong.add((long) step());
            if (atomicValue.succeeded()) {
                return Pair.of(atomicValue.postValue(), step());
            }
            log.warn("序号[{}]获取最大值失败", key());
        } catch (Exception e) {
            log.error("序号[{}]获取最大值异常", key(), e);
        }
        //说明出险了异常
        return Pair.of(-1L, -1);
    }

    /**
     * 序号实现机制
     */
    @Override
    public SeqRealizeEnum seqRealize() {
        return SeqRealizeEnum.Zookeeper;
    }

    public static ZookeeperSequence gainInstance(String seqName) {
        return gainInstance(GlobalConstant.DEFAULT_INSTANCE_NAME);
    }

    public static ZookeeperSequence gainInstance(String instanceName, String seqName) {
        return ZookeeperSequence.ZookeeperSequenceHolder.ZOOKEEPER_SEQUENCE_MAP.computeIfAbsent(instanceName, k -> new ConcurrentHashMap<>(0)).get(seqName);
    }

    public static class ZookeeperSequenceHolder {

        private static final Map<String, Map<String, ZookeeperSequence>> ZOOKEEPER_SEQUENCE_MAP = new ConcurrentHashMap<>();

        public static void init(String seqName, CuratorFramework curatorFramework) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, curatorFramework);
        }

        public static void init(String seqName, int step, CuratorFramework curatorFramework) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, step, curatorFramework);
        }

        public static void init(String instanceName, String seqName, CuratorFramework curatorFramework) {
            init(instanceName, seqName, 50, curatorFramework);
        }

        public static void init(String instanceName, String seqName, int step, CuratorFramework curatorFramework) {
            if (ZOOKEEPER_SEQUENCE_MAP.containsKey(instanceName)
                    && ZOOKEEPER_SEQUENCE_MAP.get(instanceName).containsKey(seqName)) {
                log.error("ZookeeperSequence has init!!!!!!!");
                return;
            }
            ZOOKEEPER_SEQUENCE_MAP.computeIfAbsent(instanceName, k -> new ConcurrentHashMap<>(0))
                    .computeIfAbsent(seqName, k -> new ZookeeperSequence(seqName, step, curatorFramework));
        }
    }
}
