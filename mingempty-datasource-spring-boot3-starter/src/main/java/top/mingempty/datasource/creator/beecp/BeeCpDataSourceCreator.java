package top.mingempty.datasource.creator.beecp;

import cn.beecp.BeeDataSource;
import cn.beecp.BeeDataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.datasource.creator.AbstractDataSourceCreator;
import top.mingempty.datasource.enums.DatasourceConstants;
import top.mingempty.datasource.mapstruct.BeeCpConfigMergeMapstruct;
import top.mingempty.datasource.model.DataSourceConfig;

import javax.sql.DataSource;
import java.lang.reflect.Method;


/**
 * BeeCp数据源创建器
 *
 * @author zzhao
 */
@Slf4j
public class BeeCpDataSourceCreator extends AbstractDataSourceCreator<BeeCpConfig, BeeCpConfigMergeMapstruct> {

    private static Method copyToMethod = null;

    static {
        try {
            copyToMethod = BeeDataSourceConfig.class.getDeclaredMethod("copyTo", BeeDataSourceConfig.class);
            copyToMethod.setAccessible(true);
        } catch (NoSuchMethodException ignored) {
        }
    }

    public BeeCpDataSourceCreator(BeeCpConfig gloableConfig) {
        super(gloableConfig, BeeCpConfigMergeMapstruct.INSTANCE);
    }

    /**
     * 获取属于当前工具的配置
     *
     * @param dataSourceConfig 数据源属性
     * @return 当前私有配置
     */
    @Override
    protected BeeCpConfig gainConfig(DataSourceConfig dataSourceConfig) {
        return dataSourceConfig.getBeecp();
    }

    /**
     * 创建数据源
     *
     * @param dataSourceConfig 数据源属性
     * @param config           当前私有配置
     * @return
     */
    @Override
    protected DataSource doCreateDataSource(DataSourceConfig dataSourceConfig, BeeCpConfig config) {
        BeeDataSourceConfig beeDataSourceConfig = mapstruct.toDataSourceConfig(dataSourceConfig, config);
        if (!dataSourceConfig.isLazy()) {
            return new BeeDataSource(beeDataSourceConfig);
        }
        return mapstruct.toDataSource(dataSourceConfig, config);
    }

    @Override
    public boolean support(DataSourceConfig dataSourceConfig) {
        Class<? extends DataSource> type = dataSourceConfig.getType();
        return type == null || DatasourceConstants.BEECP_DATASOURCE.equals(type.getName());
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
        return DatasourceConstants.BEECP_ORDER;
    }
}