package top.mingempty.datasource.creator.wrap;

import cn.hutool.core.util.ObjUtil;
import io.seata.rm.datasource.DataSourceProxy;
import io.seata.rm.datasource.xa.DataSourceProxyXA;
import org.springframework.core.Ordered;
import top.mingempty.datasource.model.DataSourceConfig;

import javax.sql.DataSource;

/**
 * seata数据源创建器
 *
 * @author zzhao
 */
public class SeataDataSourceCreatorWrapper implements DataSourceCreatorWrapper {

    /**
     * 对数据源进行包装
     *
     * @param dataSource       数据源
     * @param dataSourceConfig 数据源属性
     * @return 被创建的数据源
     */
    @Override
    public DataSource createDataSourceWrapper(DataSource dataSource, DataSourceConfig dataSourceConfig) {
        if (ObjUtil.isEmpty(dataSource)) {
            return null;
        }

        return switch (dataSourceConfig.getSeataMode()) {
            case XA -> new DataSourceProxyXA(dataSource);
            case AT -> new DataSourceProxy(dataSource);
        };
    }

    /**
     * 当前包装类是否支持根据此属性包装
     *
     * @param dataSourceConfig 数据源属性
     * @return 是否支持
     */
    @Override
    public boolean support(DataSourceConfig dataSourceConfig) {
        return dataSourceConfig.isSeata();
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
        return Ordered.LOWEST_PRECEDENCE;
    }
}
