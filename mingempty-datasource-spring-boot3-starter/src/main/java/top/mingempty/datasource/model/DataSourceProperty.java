package top.mingempty.datasource.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import top.mingempty.datasource.enums.SeataMode;
import top.mingempty.domain.dynamic.DynamicProperty;

import java.util.List;


/**
 * 数据库连接池配置文件
 * 最终配置为{@link  com.zaxxer.hikari.HikariDataSource}
 *
 * @author zzhao
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "数据库连接池配置文件")
@ConfigurationProperties("me.datasource")
public class DataSourceProperty extends DynamicProperty<DataSourceProperty, DataSourceConfig, DynamicDatasource> {

    /**
     * 默认数据源配置
     * <p>
     * 当配置其余多项时，此配置未全局配置
     */
    @Schema(title = "全局数据源配置", description = "当其余多项时，此配置未全局配置")
    @NestedConfigurationProperty
    private DataSourceConfig def;

    /**
     * 数据源配置
     */
    @Schema(title = "数据源配置")
    @NestedConfigurationProperty
    private List<DataSourceConfig> other;

    /**
     * 是否启用seata自动配置
     */
    private boolean seataEnabled = false;

    /**
     * 是否启用seata自动配置
     */
    private boolean autoSeataDataSourceProxyEnabled = false;

    /**
     * 是否启用seata自动配置
     */
    private SeataMode seataDataSourceProxyMode = SeataMode.AT;

    /**
     * 类型
     */
    @Override
    protected Class<DynamicDatasource> type() {
        return DynamicDatasource.class;
    }

    /**
     * 获取默认配置
     */
    @Override
    public DataSourceConfig def() {
        return this.def;
    }

    /**
     * 获取其他配置
     */
    @Override
    public List<DataSourceConfig> other() {
        return this.other;
    }
}
