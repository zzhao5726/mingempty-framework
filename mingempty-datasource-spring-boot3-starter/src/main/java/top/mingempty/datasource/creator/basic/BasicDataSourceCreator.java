package top.mingempty.datasource.creator.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import top.mingempty.datasource.creator.DataSourceCreator;
import top.mingempty.datasource.enums.DatasourceConstants;
import top.mingempty.datasource.model.DataSourceConfig;

import javax.sql.DataSource;

/**
 * 基础数据源创建器
 *
 * @author zzhao
 */
@Slf4j
public class BasicDataSourceCreator implements DataSourceCreator {

    /**
     * 创建基础数据源
     *
     * @param dataSourceConfig 数据源参数
     * @return 数据源
     */
    @Override
    public DataSource createDataSource(DataSourceConfig dataSourceConfig) {
        return DataSourceBuilder.create()
                .type(dataSourceConfig.getType())
                .url(dataSourceConfig.getUrl())
                .driverClassName(dataSourceConfig.getDriverClassName())
                .username(dataSourceConfig.getUsername())
                .password(dataSourceConfig.getPassword())
                .build();
    }

    @Override
    public boolean support(DataSourceConfig dataSourceConfig) {
        return true;
    }

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return DatasourceConstants.BASIC_ORDER;
    }
}