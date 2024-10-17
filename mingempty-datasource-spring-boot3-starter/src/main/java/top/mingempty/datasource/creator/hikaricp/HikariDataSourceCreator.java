package top.mingempty.datasource.creator.hikaricp;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import top.mingempty.datasource.creator.AbstractDataSourceCreator;
import top.mingempty.datasource.enums.DatasourceConstants;
import top.mingempty.datasource.mapstruct.HikariCpConfigMergeMapstruct;
import top.mingempty.datasource.model.DataSourceConfig;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Hikari数据源创建器
 *
 * @author zzhao
 */
public class HikariDataSourceCreator extends AbstractDataSourceCreator<HikariCpConfig, HikariCpConfigMergeMapstruct> {
    private static Method configCopyMethod = null;

    static {
        fetchMethod();
    }

    /**
     * to support springboot 1.5 and 2.x
     * HikariConfig 2.x use 'copyState' to copy config
     * HikariConfig 3.x use 'copyStateTo' to copy config
     */
    @SuppressWarnings("JavaReflectionMemberAccess")
    private static void fetchMethod() {
        Class<HikariConfig> hikariConfigClass = HikariConfig.class;
        try {
            configCopyMethod = hikariConfigClass.getMethod("copyState", hikariConfigClass);
            return;
        } catch (NoSuchMethodException ignored) {
        }

        try {
            configCopyMethod = hikariConfigClass.getMethod("copyStateTo", hikariConfigClass);
            return;
        } catch (NoSuchMethodException ignored) {
        }
        throw new RuntimeException("HikariConfig does not has 'copyState' or 'copyStateTo' method!");
    }

    public HikariDataSourceCreator(HikariCpConfig gloableConfig) {
        super(gloableConfig, HikariCpConfigMergeMapstruct.INSTANCE);
    }

    /**
     * 获取属于当前工具的配置
     *
     * @param dataSourceConfig 数据源属性
     * @return 当前私有配置
     */
    @Override
    protected HikariCpConfig gainConfig(DataSourceConfig dataSourceConfig) {
        return dataSourceConfig.getHikari();
    }

    /**
     * 创建数据源
     *
     * @param dataSourceConfig 数据源属性
     * @param config             当前私有配置
     * @return
     */
    @Override
    protected DataSource doCreateDataSource(DataSourceConfig dataSourceConfig, HikariCpConfig config) {
        HikariConfig hikariConfig = mapstruct.toDataSource(dataSourceConfig, config);
        if (Boolean.FALSE.equals(dataSourceConfig.isLazy())) {
            return new HikariDataSource(hikariConfig);
        }
        hikariConfig.validate();
        HikariDataSource dataSource = new HikariDataSource();
        try {
            configCopyMethod.invoke(hikariConfig, dataSource);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("HikariConfig failed to copy to HikariDataSource", e);
        }
        return dataSource;
    }

    @Override
    public boolean support(DataSourceConfig dataSourceConfig) {
        Class<? extends DataSource> type = dataSourceConfig.getType();
        return type == null || DatasourceConstants.HIKARI_DATASOURCE.equals(type.getName());
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
        return DatasourceConstants.HIKARI_ORDER;
    }
}