package top.mingempty.meta.data.util;

import top.mingempty.sequence.enums.SeqRealizeEnum;
import top.mingempty.sequence.factory.SequenceFactory;

/**
 * ID工具类
 */
public class IDUtil {

    /**
     * 获取默认的雪花ID
     *
     * @return
     */
    public static Long gainId() {
        return SequenceFactory.seqRealize(SeqRealizeEnum.Zookeeper).snowflakeIdWorker().next();
    }

    /**
     * 获取指定实例下的雪花ID
     *
     * @return
     */
    public static Long gainId(String instanceName) {
        return SequenceFactory.seqRealize(SeqRealizeEnum.Zookeeper).snowflakeIdWorker(instanceName).next();
    }
}
