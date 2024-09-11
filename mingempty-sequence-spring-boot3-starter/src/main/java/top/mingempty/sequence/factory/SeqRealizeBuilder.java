package top.mingempty.sequence.factory;

import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.sequence.api.impl.base.SnowflakeIdWorkerSequence;

/**
 * 序号类型建造者
 *
 * @author zzhao
 */
public interface SeqRealizeBuilder extends  SeqInstanceBuilder{



     ExpirationStrategyBuilder cacheSequence();


     default SnowflakeIdWorkerSequence snowflakeIdWorker() {
          return snowflakeIdWorker(GlobalConstant.DEFAULT_INSTANCE_NAME);
     }

     SnowflakeIdWorkerSequence snowflakeIdWorker(String instanceName);

}
