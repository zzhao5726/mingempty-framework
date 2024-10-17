package top.mingempty.datasource.creator.jndi;

import cn.hutool.core.util.StrUtil;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import top.mingempty.datasource.creator.DataSourceCreator;
import top.mingempty.datasource.enums.DatasourceConstants;
import top.mingempty.datasource.model.DataSourceConfig;

import javax.sql.DataSource;

/**
 * JNDI数据源创建器
 *
 * @author zzhao
 */
public class JndiDataSourceCreator implements DataSourceCreator {

    private static final JndiDataSourceLookup LOOKUP = new JndiDataSourceLookup();


    @Override
    public DataSource createDataSource(DataSourceConfig dataSourceConfig) {
        return LOOKUP.getDataSource(dataSourceConfig.getJndiName());
    }

    @Override
    public boolean support(DataSourceConfig dataSourceConfig) {
        return StrUtil.isNotEmpty(dataSourceConfig.getJndiName());
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
        return DatasourceConstants.JNDI_ORDER;
    }
}