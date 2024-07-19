package top.mingempty.jdbc.domain;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;


/**
 * druid数据库连接池配置文件
 * 最终配置为{@link com.alibaba.druid.pool.DruidDataSource}
 *
 * @author zzhao
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties("me.datasource.druid")
public class DruidDataSourceProperties extends DruidDataSource {

    /**
     * 是否启用druid数据库连接池
     */
    private boolean enabled;

    /**
     * 多数据配置
     */
    @NestedConfigurationProperty
    private Map<String, DruidDataSource> more;
}
