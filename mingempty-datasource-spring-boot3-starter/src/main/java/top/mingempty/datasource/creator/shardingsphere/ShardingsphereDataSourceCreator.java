package top.mingempty.datasource.creator.shardingsphere;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import top.mingempty.datasource.creator.DataSourceCreator;
import top.mingempty.datasource.enums.DatasourceConstants;
import top.mingempty.datasource.exception.DataSourceException;
import top.mingempty.datasource.model.DataSourceConfig;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Supplier;

/**
 * shardingsphere数据源创建器
 *
 * @author zzhao
 */
@Slf4j
public class ShardingsphereDataSourceCreator implements DataSourceCreator {

    /**
     * 创建基础数据源
     *
     * @param dataSourceConfig 数据源参数
     * @return 数据源
     */
    @Override
    @SneakyThrows
    public DataSource createDataSource(DataSourceConfig dataSourceConfig) {
        return ServiceLoader.load(ShardingspherePropertiesGain.class).stream()
                .map(ServiceLoader.Provider::get)
                .filter(shardingspherePropertiesGain
                        -> shardingspherePropertiesGain.support(dataSourceConfig.getShardingspherePath()))
                .min(Comparator.comparingInt(ShardingspherePropertiesGain::getOrder))
                .flatMap(shardingspherePropertiesGain
                        -> Optional.ofNullable(shardingspherePropertiesGain.gain(dataSourceConfig.getShardingspherePath())))
                .filter(bytes -> bytes.length > 0)
                .flatMap(bytes -> {
                    try {
                        return Optional.ofNullable(YamlShardingSphereDataSourceFactory.createDataSource(bytes));
                    } catch (SQLException | IOException e) {
                        throw new DataSourceException("ds-0000000002", e);
                    }
                })
                .orElseThrow((Supplier<Throwable>) () -> new DataSourceException("ds-0000000001"));
    }

    @Override
    public boolean support(DataSourceConfig dataSourceConfig) {
        return StrUtil.isNotEmpty(dataSourceConfig.getShardingspherePath());
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
        return DatasourceConstants.SHARDINGSPHERE_ORDER;
    }
}