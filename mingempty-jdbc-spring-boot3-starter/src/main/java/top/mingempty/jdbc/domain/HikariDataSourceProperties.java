package top.mingempty.jdbc.domain;

import com.zaxxer.hikari.HikariConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;


/**
 * hikari数据库连接池配置文件
 * 最终配置为{@link  com.zaxxer.hikari.HikariDataSource}
 *
 * @author zzhao
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties("me.datasource.hikari")
public class HikariDataSourceProperties extends HikariConfig {

    /**
     * 是否启用hikari数据库连接池
     */
    private boolean enabled;

    /**
     * 多数据配置
     */
    @NestedConfigurationProperty
    private Map<String, HikariConfig> more;
}
