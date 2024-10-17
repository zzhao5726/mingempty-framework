package top.mingempty.datasource.creator.druid;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.datasource.creator.AbstractDataSourceCreator;
import top.mingempty.datasource.enums.DatasourceConstants;
import top.mingempty.datasource.mapstruct.DruidConfigMergeMapstruct;
import top.mingempty.datasource.model.DataSourceConfig;

import javax.sql.DataSource;

/**
 * Druid数据源创建器
 *
 * @author zzhao
 */
@Slf4j
public class DruidDataSourceCreator extends AbstractDataSourceCreator<DruidConfig, DruidConfigMergeMapstruct> {

    public DruidDataSourceCreator(DruidConfig gloableConfig) {
        super(gloableConfig, DruidConfigMergeMapstruct.INSTANCE);
    }

    /**
     * 获取属于当前工具的配置
     *
     * @param dataSourceConfig 数据源属性
     * @return 当前私有配置
     */
    @Override
    protected DruidConfig gainConfig(DataSourceConfig dataSourceConfig) {
        return dataSourceConfig.getDruid();
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
    protected DataSource doCreateDataSource(DataSourceConfig dataSourceConfig, DruidConfig config) {
        DruidDataSource dataSource = mapstruct.toDataSource(dataSourceConfig, config);
        if (!dataSourceConfig.isLazy()) {
            dataSource.init();
        }
        return dataSource;
    }

    @Override
    public boolean support(DataSourceConfig dataSourceConfig) {
        Class<? extends DataSource> type = dataSourceConfig.getType();
        return type == null || DatasourceConstants.DRUID_DATASOURCE.equals(type.getName());
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
        return DatasourceConstants.DRUID_ORDER;
    }
}