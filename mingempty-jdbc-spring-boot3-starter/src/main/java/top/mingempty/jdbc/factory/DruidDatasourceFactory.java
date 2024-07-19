package top.mingempty.jdbc.factory;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.SneakyThrows;

import javax.sql.DataSource;

/**
 * druid数据源工厂
 *
 * @author zzhao
 */
public class DruidDatasourceFactory implements DatasourceFactory<DruidDataSource> {

    /**
     * 是否支持
     *
     * @param clazz 数据源配置类
     * @return true/false
     */
    @Override
    public boolean support(Class<?> clazz) {
        return DruidDataSource.class.equals(clazz);
    }

    /**
     * 将配置转换为数据源
     *
     * @param properties 数据源配置
     * @return 数据源
     */
    @Override
    @SneakyThrows
    public DataSource createDataSource(DruidDataSource properties) {
        if (!properties.isInited()) {
            properties.init();
        }
        return properties;
    }
}
