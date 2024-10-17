package top.mingempty.datasource.creator.dbcp;

import lombok.SneakyThrows;
import org.apache.commons.dbcp2.BasicDataSource;
import top.mingempty.datasource.creator.AbstractDataSourceCreator;
import top.mingempty.datasource.enums.DatasourceConstants;
import top.mingempty.datasource.mapstruct.Dbcp2ConfigMergeMapstruct;
import top.mingempty.datasource.model.DataSourceConfig;

import javax.sql.DataSource;

/**
 * DBCP数据源创建器
 *
 * @author zzhao
 */
public class Dbcp2DataSourceCreator extends AbstractDataSourceCreator<Dbcp2Config, Dbcp2ConfigMergeMapstruct> {

    public Dbcp2DataSourceCreator(Dbcp2Config gloableConfig) {
        super(gloableConfig, Dbcp2ConfigMergeMapstruct.INSTANCE);
    }

    /**
     * 获取属于当前工具的配置
     *
     * @param dataSourceConfig 数据源属性
     * @return 当前私有配置
     */
    @Override
    protected Dbcp2Config gainConfig(DataSourceConfig dataSourceConfig) {
        return dataSourceConfig.getDbcp2();
    }

    /**
     * 创建数据源
     *
     * @param dataSourceConfig 数据源属性
     * @param config             当前私有配置
     * @return
     */
    @Override
    @SneakyThrows
    protected DataSource doCreateDataSource(DataSourceConfig dataSourceConfig, Dbcp2Config config) {
        BasicDataSource dataSource = mapstruct.toDataSource(dataSourceConfig, config);
        if (!dataSourceConfig.isLazy()) {
            dataSource.start();
        }
        return dataSource;
    }

    @Override
    public boolean support(DataSourceConfig dataSourceConfig) {
        Class<? extends DataSource> type = dataSourceConfig.getType();
        return type == null || DatasourceConstants.DBCP2_DATASOURCE.equals(type.getName());
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
        return DatasourceConstants.DBCP2_ORDER;
    }
}