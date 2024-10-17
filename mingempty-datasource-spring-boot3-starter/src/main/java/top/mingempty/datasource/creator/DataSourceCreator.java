package top.mingempty.datasource.creator;


import org.springframework.core.Ordered;
import top.mingempty.datasource.model.DataSourceConfig;

import javax.sql.DataSource;

/**
 * 数据创建工具接口
 *
 * @author zzhao
 */
public interface DataSourceCreator extends Ordered {

    /**
     * 通过属性创建数据源
     *
     * @param dataSourceConfig 数据源属性
     * @return 被创建的数据源
     */
    DataSource createDataSource(DataSourceConfig dataSourceConfig);


    /**
     * 当前创建器是否支持根据此属性创建
     *
     * @param dataSourceConfig 数据源属性
     * @return 是否支持
     */
    boolean support(DataSourceConfig dataSourceConfig);

}