package top.mingempty.datasource.creator.atomikos;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import top.mingempty.datasource.creator.AbstractDataSourceCreator;
import top.mingempty.datasource.enums.DatasourceConstants;
import top.mingempty.datasource.enums.XADataSourceEnum;
import top.mingempty.datasource.mapstruct.AtomikosConfigMergeMapstruct;
import top.mingempty.datasource.model.DataSourceConfig;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * Atomikos数据源配置
 *
 * @author <a href="mailto:945514650@qq.com">zzhao</a>
 */

public class AtomikosDataSourceCreator extends AbstractDataSourceCreator<AtomikosConfig, AtomikosConfigMergeMapstruct> {

    public AtomikosDataSourceCreator(AtomikosConfig gloableConfig) {
        super(gloableConfig, AtomikosConfigMergeMapstruct.INSTANCE);
    }

    /**
     * 获取属于当前工具的配置
     *
     * @param dataSourceConfig 数据源属性
     * @return 当前私有配置
     */
    @Override
    protected AtomikosConfig gainConfig(DataSourceConfig dataSourceConfig) {
        return dataSourceConfig.getAtomikos();
    }

    /**
     * 创建数据源
     *
     * @param dataSourceConfig 数据源属性
     * @param config             当前私有配置
     * @return
     */
    @Override
    protected DataSource doCreateDataSource(DataSourceConfig dataSourceConfig, AtomikosConfig config) {
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSourceClassName(dataSourceConfig.getDriverClassName());
        Properties xaProperties = xaDataSource.getXaProperties();
        xaProperties.setProperty("url", dataSourceConfig.getUrl());
        xaProperties.setProperty("user", dataSourceConfig.getUsername());
        xaProperties.setProperty("password", dataSourceConfig.getPassword());
        xaDataSource.setUniqueResourceName(dataSourceConfig.getName());
        xaDataSource.setMinPoolSize(config.getMinPoolSize());
        xaDataSource.setMaxPoolSize(config.getMaxPoolSize());
        xaDataSource.setBorrowConnectionTimeout(config.getBorrowConnectionTimeout());
        xaDataSource.setMaxIdleTime(config.getMaxIdleTime());
        xaDataSource.setTestQuery(config.getTestQuery());
        xaDataSource.setMaintenanceInterval(config.getMaintenanceInterval());
        xaDataSource.setDefaultIsolationLevel(config.getDefaultIsolationLevel());
        xaDataSource.setMaxLifetime(config.getMaxLifetime());
        xaDataSource.setConcurrentConnectionValidation(config.isEnableConcurrentConnectionValidation());
        return xaDataSource;
    }

    @Override
    public boolean support(DataSourceConfig dataSourceConfig) {
        Class<? extends DataSource> type = dataSourceConfig.getType();
        return (type == null || DatasourceConstants.ATOMIKOS_DATASOURCE.equals(type.getName())) && XADataSourceEnum.contains(dataSourceConfig.getDriverClassName());
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
        return DatasourceConstants.ATOMIKOS_ORDER;
    }
}