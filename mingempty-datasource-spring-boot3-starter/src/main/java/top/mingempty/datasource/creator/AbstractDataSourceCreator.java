package top.mingempty.datasource.creator;


import top.mingempty.datasource.mapstruct.ConfigMergeMapstruct;
import top.mingempty.datasource.model.DataSourceConfig;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * 数据源创建工具抽象实现
 *
 * @author zzhao
 */
public abstract class AbstractDataSourceCreator<C, M extends ConfigMergeMapstruct<C>> implements DataSourceCreator {

    protected final C gloableConfig;
    protected final M mapstruct;

    protected AbstractDataSourceCreator(C gloableConfig, M mapstruct) {
        this.gloableConfig = gloableConfig;
        this.mapstruct = mapstruct;
    }


    /**
     * 通过属性创建数据源
     *
     * @param dataSourceConfig 数据源属性
     * @return 被创建的数据源
     */
    @Override
    public DataSource createDataSource(DataSourceConfig dataSourceConfig) {
        C config = gainConfig(dataSourceConfig);
        if (!Objects.equals(config, gloableConfig)) {
            // 属性拷贝
            config = mapstruct.newT(gainConfig(dataSourceConfig), gloableConfig);
        }
        return doCreateDataSource(dataSourceConfig, config);
    }

    /**
     * 获取属于当前工具的配置
     *
     * @param dataSourceConfig 数据源属性
     * @return 当前私有配置
     */
    protected abstract C gainConfig(DataSourceConfig dataSourceConfig);

    /**
     * 创建数据源
     *
     * @param dataSourceConfig 数据源属性
     * @param config           当前私有配置
     * @return
     */
    protected abstract DataSource doCreateDataSource(DataSourceConfig dataSourceConfig, C config);

}