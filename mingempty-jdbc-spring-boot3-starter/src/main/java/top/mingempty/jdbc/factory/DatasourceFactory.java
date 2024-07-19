package top.mingempty.jdbc.factory;

import javax.sql.DataSource;

/**
 * 数据源工厂类
 *
 * @author zzhao
 */
public interface DatasourceFactory<T> {

    /**
     * 是否支持
     *
     * @param clazz 数据源配置类
     * @return true/false
     */
    boolean support(Class<?> clazz);

    /**
     * 将配置转换为数据源
     *
     * @param properties 数据源配置
     * @return 数据源
     */
    DataSource createDataSource(T properties);

}
