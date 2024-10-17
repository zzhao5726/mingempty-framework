package top.mingempty.datasource.creator.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.SneakyThrows;
import top.mingempty.datasource.creator.AbstractDataSourceCreator;
import top.mingempty.datasource.enums.DatasourceConstants;
import top.mingempty.datasource.mapstruct.C3p0ConfigMergeMapstruct;
import top.mingempty.datasource.model.DataSourceConfig;

import javax.sql.DataSource;

/**
 * C3p0数据源创建器
 *
 * @author zzhao
 */
public class C3p0DataSourceCreator extends AbstractDataSourceCreator<C3p0Config, C3p0ConfigMergeMapstruct> {

    public C3p0DataSourceCreator(C3p0Config gloableConfig) {
        super(gloableConfig, C3p0ConfigMergeMapstruct.INSTANCE);
    }


    /**
     * 获取属于当前工具的配置
     *
     * @param dataSourceConfig 数据源属性
     * @return 当前私有配置
     */
    @Override
    protected C3p0Config gainConfig(DataSourceConfig dataSourceConfig) {
        return dataSourceConfig.getC3p0();
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
    protected DataSource doCreateDataSource(DataSourceConfig dataSourceConfig, C3p0Config config) {
        ComboPooledDataSource dataSource = mapstruct.toDataSource(dataSourceConfig, config);
        if (!dataSourceConfig.isLazy()) {
            dataSource.getNumUserPools();
            return dataSource;
        }
        return dataSource;
    }

    /**
     * 当前创建器是否支持根据此属性创建
     *
     * @param dataSourceConfig 数据源属性
     * @return 是否支持
     */
    @Override
    public boolean support(DataSourceConfig dataSourceConfig) {
        Class<? extends DataSource> type = dataSourceConfig.getType();
        return type == null || DatasourceConstants.C3P0_DATASOURCE.equals(type.getName());
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
        return DatasourceConstants.C3P0_ORDER;
    }
}
