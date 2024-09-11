package top.mingempty.sequence.factory;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import top.mingempty.sequence.enums.SeqRealizeEnum;
import top.mingempty.sequence.model.CacheSequenceProperties;
import top.mingempty.sequence.model.SequenceProperties;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 序号初始化工厂
 *
 * @author zzhao
 */
@AllArgsConstructor
public class SequenceInitFactory implements InitializingBean {
    private final SequenceProperties sequenceProperties;

    private final List<InitFactory> initFactories;


    /**
     * 初始化
     */
    public void init() {
        //初始雪花算法
        Map<SeqRealizeEnum, Set<String>> snowflakeIdWorkerInstances = sequenceProperties.getSnowflakeIdWorkerInstances();
        if (MapUtil.isNotEmpty(snowflakeIdWorkerInstances)) {
            initFactories.forEach(initFactory
                    -> initFactory.snowflakeIdWorker(snowflakeIdWorkerInstances.get(initFactory.setRealize())));
        }

        //初始化其他
        Set<CacheSequenceProperties> cacheSequences = sequenceProperties.getCacheSequences();
        if (CollUtil.isNotEmpty(cacheSequences)) {
            cacheSequences.forEach(cacheSequenceProperties
                    -> initFactories.stream()
                    .filter(initFactory
                            -> initFactory.setRealize().equals(cacheSequenceProperties.getSeqRealize()))
                    .forEach(initFactory -> initFactory.cacheSequence(cacheSequenceProperties)));
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
