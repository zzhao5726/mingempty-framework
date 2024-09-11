package top.mingempty.sequence.factory;

import top.mingempty.sequence.enums.SeqRealizeEnum;
import top.mingempty.sequence.model.CacheSequenceProperties;

import java.util.Set;

/**
 * 接口抽象
 */
public interface InitFactory {

    /**
     * 初始化类型
     */
    SeqRealizeEnum setRealize();

    /**
     * 初始化雪花算法
     *
     * @param instanceNames 实例名称集合
     */
    void snowflakeIdWorker(Set<String> instanceNames);

    /**
     * 初始化缓存序号
     *
     * @param cacheSequenceProperties 缓存序号配置
     */
    void cacheSequence(CacheSequenceProperties cacheSequenceProperties);


    /**
     * 初始化包装序号
     *
     * @param cacheSequenceProperties 缓存序号配置
     */
    void expirationStrategySequence(CacheSequenceProperties cacheSequenceProperties);
}
