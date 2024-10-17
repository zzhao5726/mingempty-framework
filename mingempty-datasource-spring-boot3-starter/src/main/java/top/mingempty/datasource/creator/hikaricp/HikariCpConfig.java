package top.mingempty.datasource.creator.hikaricp;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

/**
 * HikariCp参数配置
 *
 * @author zzhao
 */
@Getter
@Setter
public class HikariCpConfig {

    private String catalog;
    private Long connectionTimeout;
    private Long validationTimeout;
    private Long idleTimeout;
    private Long leakDetectionThreshold;
    private Long maxLifetime;
    private Integer maxPoolSize;
    private Integer maximumPoolSize;
    private Integer minIdle;
    private Integer minimumIdle;

    private Long initializationFailTimeout;
    private String connectionInitSql;
    private String connectionTestQuery;
    private String dataSourceClassName;
    private String dataSourceJndiName;
    private String transactionIsolationName;
    private Boolean isAutoCommit;
    private Boolean isReadOnly;
    private Boolean isIsolateInternalQueries;
    private Boolean isRegisterMbeans;
    private Boolean isAllowPoolSuspension;
    private Properties dataSourceProperties;
    private Properties healthCheckProperties;

    /**
     * 高版本才有
     */
    private String schema;
    private String exceptionOverrideClassName;
    private Long keepaliveTime;
    private Boolean sealed;

    /**
     * 设置 最大连接数 maxPoolSize
     *
     * @param maximumPoolSize 最大链接数
     */
    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maxPoolSize = maximumPoolSize;
    }

    /**
     * 设置 最小空闲连接数 minIdle
     *
     * @param minimumIdle 最小空闲连接数
     */
    public void setMinimumIdle(Integer minimumIdle) {
        this.minIdle = minimumIdle;
    }
}