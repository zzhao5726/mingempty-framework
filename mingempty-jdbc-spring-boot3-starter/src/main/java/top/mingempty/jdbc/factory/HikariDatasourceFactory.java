package top.mingempty.jdbc.factory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * Hikari数据源工厂
 *
 * @author zzhao
 */
public class HikariDatasourceFactory implements DatasourceFactory<HikariConfig> {
    /**
     * 是否支持
     *
     * @param clazz 数据源配置类
     * @return true/false
     */
    @Override
    public boolean support(Class<?> clazz) {
        return HikariConfig.class.equals(clazz);
    }

    /**
     * 将配置转换为数据源
     *
     * @param properties 数据源配置
     * @return 数据源
     */
    @Override
    public DataSource createDataSource(HikariConfig properties) {
        return new HikariDataSource(properties);
    }
}
