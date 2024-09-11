package top.mingempty.sequence.factory;

import cn.hutool.core.collection.CollUtil;
import top.mingempty.jdbc.domain.MeDatasourceWrapper;
import top.mingempty.sequence.api.impl.base.DatabaseSequence;
import top.mingempty.sequence.api.impl.wrapper.DatabaseSequenceWrapper;
import top.mingempty.sequence.enums.SeqRealizeEnum;
import top.mingempty.sequence.model.CacheSequenceProperties;
import top.mingempty.util.SpringContextUtil;

import javax.sql.DataSource;
import java.util.Set;

/**
 * 执行序号对数据库相关的初始化
 */
public class DatabaseInitFactory implements InitFactory {
    private final MeDatasourceWrapper meDatasourceWrapper;

    public DatabaseInitFactory() {
        this.meDatasourceWrapper = SpringContextUtil.gainBean("dataSource", MeDatasourceWrapper.class);
    }

    /**
     * 初始化类型
     */
    @Override
    public SeqRealizeEnum setRealize() {
        return SeqRealizeEnum.Database;
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
        throw new UnsupportedOperationException();
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
        DataSource dataSource = meDatasourceWrapper.getResolvedRouter(cacheSequenceProperties.getInstanceName());
        DatabaseSequence.DatabaseSequenceHolder.init(cacheSequenceProperties.getInstanceName(),
                cacheSequenceProperties.getSeqName(), cacheSequenceProperties.getStep(), dataSource);

    }

    /**
     * 初始化包装序号
     *
     * @param cacheSequenceProperties 缓存序号配置
     */
    @Override
    public void expirationStrategySequence(CacheSequenceProperties cacheSequenceProperties) {
        DataSource dataSource = meDatasourceWrapper.getResolvedRouter(cacheSequenceProperties.getInstanceName());
        DatabaseSequenceWrapper.DatabaseSequenceHolder.init(cacheSequenceProperties.getInstanceName(),
                cacheSequenceProperties.getSeqName(), cacheSequenceProperties.getStep(),
                cacheSequenceProperties.getExpirationStrategy(), dataSource);
    }
}
