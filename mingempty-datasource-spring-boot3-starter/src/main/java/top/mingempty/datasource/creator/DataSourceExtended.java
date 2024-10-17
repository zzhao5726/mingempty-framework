package top.mingempty.datasource.creator;

import top.mingempty.datasource.model.DataSourceProperty;
import top.mingempty.datasource.model.DynamicDatasource;

/**
 * 数据源扩展接口
 */
public interface DataSourceExtended {

    /**
     * 扩展数据源
     *
     * @param dataSourceProperty 配置文件
     * @param dynamicDatasource  基于配置初始化后的数据源
     */
    void dataSourceExtended(DataSourceProperty dataSourceProperty,
                            final DynamicDatasource dynamicDatasource);
}
