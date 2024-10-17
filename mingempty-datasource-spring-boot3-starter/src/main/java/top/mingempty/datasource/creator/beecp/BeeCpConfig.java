package top.mingempty.datasource.creator.beecp;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

/**
 * BeeCp参数配置
 *
 * @author zzhao
 */
@Getter
@Setter
public class BeeCpConfig {

    private String defaultCatalog;
    private String defaultSchema;
    private Boolean defaultReadOnly;
    private Boolean defaultAutoCommit;
    private Integer defaultTransactionIsolationCode;
    private String defaultTransactionIsolationName;

    private Boolean fairMode;
    private Integer initialSize;
    private Integer maxActive;
    private Integer borrowSemaphoreSize;
    private Long maxWait;
    private Long idleTimeout;
    private Long holdTimeout;
    private String connectionTestSql;
    private Integer connectionTestTimeout;
    private Long connectionTestIntegererval;
    private Long idleCheckTimeIntegererval;
    private Boolean forceCloseUsingOnClear;
    private Long delayTimeForNextClear;

    private String connectionFactoryClassName;
    private String xaConnectionFactoryClassName;
    private Properties connectProperties;
    private String poolImplementClassName;
    private Boolean enableJmx;
}