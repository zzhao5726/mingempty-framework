package top.mingempty.jdbc.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import top.mingempty.jdbc.domain.enums.SeataDatasourceProxyModeEnum;


/**
 * hikari数据库连接池配置文件
 * 最终配置为{@link  com.zaxxer.hikari.HikariDataSource}
 *
 * @author zzhao
 */
@Data
@ConfigurationProperties("me.datasource")
public class DataSourceProperties {

    /**
     * 是否启用seata自动配置
     */
    private boolean enabledSeata = true;

    /**
     * 是否启用数据源 Bean 的自动代理
     */
    private boolean enabledAutoSeataDataSourceProxy = true;

    /**
     * 数据源代理模式 AT | XA
     */
    private SeataDatasourceProxyModeEnum seataDataSourceProxyMode = SeataDatasourceProxyModeEnum.AT;

    /**
     * 指定哪些数据源 Bean 不符合自动代理的条件
     */
    private String[] excludesForAutoProxying = {};

}
