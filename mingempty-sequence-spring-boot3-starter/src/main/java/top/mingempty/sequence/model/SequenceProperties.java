package top.mingempty.sequence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import top.mingempty.sequence.enums.SeqRealizeEnum;

import java.util.Map;
import java.util.Set;

/**
 * 序列配置
 *
 * @author zzhao
 */
@Data
@EqualsAndHashCode
@ConfigurationProperties("me.sequence")
public class SequenceProperties {

    /**
     * 是否自动注入默认的配置
     */
    private boolean enabled = true;

    /**
     * 雪花算法注入的实例
     * <p>
     * key: 实例名称
     * value: 序号实现机制
     */
    private Map<SeqRealizeEnum, Set<String>> snowflakeIdWorkerInstances;


    /**
     * 基于缓存配置的序号生成器
     * <p>
     * 仅用提示，配置项无用
     */
    @Deprecated
    @NestedConfigurationProperty
    private CacheSequenceProperties cacheSequence;


    /**
     * 基于缓存配置的序号生成器
     */
    private Set<CacheSequenceProperties> cacheSequences;

}
