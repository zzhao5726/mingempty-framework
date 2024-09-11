package top.mingempty.sequence.factory;

import top.mingempty.sequence.api.Sequence;
import top.mingempty.sequence.api.impl.base.DatabaseSequence;
import top.mingempty.sequence.api.impl.base.RedisSequence;
import top.mingempty.sequence.api.impl.base.SnowflakeIdWorkerSequence;
import top.mingempty.sequence.api.impl.base.UUIDSequence;
import top.mingempty.sequence.api.impl.base.ZookeeperSequence;
import top.mingempty.sequence.api.impl.wrapper.DatabaseSequenceWrapper;
import top.mingempty.sequence.api.impl.wrapper.RedisSequenceWrapper;
import top.mingempty.sequence.api.impl.wrapper.ZookeeperSequenceWrapper;
import top.mingempty.sequence.enums.SeqRealizeEnum;

/**
 * 序号工厂
 *
 * @author zzhao
 */
public class SequenceFactory {


    /**
     * 获取UUID序列
     *
     * @return
     */
    public static Sequence<String> uuid() {
        return UUIDSequence.instance();
    }


    /**
     * 获取指定实例的构造器
     *
     * @param seqRealize
     * @return
     */
    public static SeqRealizeBuilder seqRealize(SeqRealizeEnum seqRealize) {
        return new SeqRealizeBuilder() {
            @Override
            public Sequence<Long> seqInstance(String instanceName, String seqName) {
                return switch (seqRealize) {
                    case Redis -> RedisSequence.gainInstance(instanceName, seqName);
                    case Zookeeper -> ZookeeperSequence.gainInstance(instanceName, seqName);
                    case Database -> DatabaseSequence.gainInstance(instanceName, seqName);
                };
            }

            @Override
            public ExpirationStrategyBuilder cacheSequence() {
                return (instanceName, seqName) -> switch (seqRealize) {
                    case Redis -> RedisSequenceWrapper.gainInstance(instanceName, seqName);
                    case Zookeeper -> ZookeeperSequenceWrapper.gainInstance(instanceName, seqName);
                    case Database -> DatabaseSequenceWrapper.gainInstance(instanceName, seqName);
                };
            }

            @Override
            public SnowflakeIdWorkerSequence snowflakeIdWorker(String instanceName) {
                return SnowflakeIdWorkerSequence.gainInstance(seqRealize, instanceName);
            }
        };
    }

}
