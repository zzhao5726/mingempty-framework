package top.mingempty.sequence.factory;

import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.sequence.api.Sequence;

/**
 * 序号实例和名称建造者
 *
 * @author zzhao
 */
public interface SeqInstanceBuilder {


    default Sequence<Long> seqInstance(String seqName) {
        return seqInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName);
    }


    Sequence<Long>  seqInstance(String instanceName, String seqName);


}
