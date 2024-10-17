package top.mingempty.datasource.creator.wrap;


import org.springframework.core.Ordered;
import top.mingempty.datasource.model.DataSourceConfig;

import javax.sql.DataSource;

/**
 * 数据源包装接口
 *
 * @author zzhao
 */
public interface DataSourceCreatorWrapper extends Ordered {

    /**
     * 对数据源进行包装
     *
     * @param dataSource       数据源
     * @param dataSourceConfig 数据源属性
     * @return 被创建的数据源
     */
    DataSource createDataSourceWrapper(DataSource dataSource, DataSourceConfig dataSourceConfig);


    /**
     * 当前包装类是否支持根据此属性包装
     *
     * @param dataSourceConfig 数据源属性
     * @return 是否支持
     */
    boolean support(DataSourceConfig dataSourceConfig);

}