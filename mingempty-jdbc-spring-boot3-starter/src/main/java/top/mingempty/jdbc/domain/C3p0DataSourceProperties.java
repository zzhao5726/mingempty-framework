package top.mingempty.jdbc.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

/**
 * C3P0数据库连接池配置文件
 * 最终配置为{@link com.mchange.v2.c3p0.ComboPooledDataSource}
 *
 * @author zzhao
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties("me.datasource.c3p0")
public class C3p0DataSourceProperties extends C3p0Config {

    /**
     * 是否启用c3p0数据库连接池
     */
    private boolean enabled;

    /**
     * 多数据配置
     */
    @NestedConfigurationProperty
    private Map<String, C3p0Config> more;
}
