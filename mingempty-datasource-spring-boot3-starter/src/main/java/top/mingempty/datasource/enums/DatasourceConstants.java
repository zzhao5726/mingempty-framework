package top.mingempty.datasource.enums;

/**
 * 数据源常量
 *
 * @author zzhao
 */
public interface DatasourceConstants {

    /**
     * 数据源：主库
     */
    String MASTER = "master";
    /**
     * 数据源：从库
     */
    String SLAVE = "slave";

    /**
     * DRUID数据源类
     */
    String DRUID_DATASOURCE = "com.alibaba.druid.pool.DruidDataSource";

    /**
     * HikariCp数据源
     */
    String HIKARI_DATASOURCE = "com.zaxxer.hikari.HikariDataSource";

    /**
     * BeeCp数据源
     */
    String BEECP_DATASOURCE = "cn.beecp.BeeDataSource";

    /**
     * DBCP2数据源
     */
    String DBCP2_DATASOURCE = "org.apache.commons.dbcp2.BasicDataSource";

    /**
     * Atomikos数据源
     */
    String ATOMIKOS_DATASOURCE = "com.atomikos.jdbc.AtomikosDataSourceBean";

    /**
     * C3P0数据源
     */
    String C3P0_DATASOURCE = "com.mchange.v2.c3p0.ComboPooledDataSource";



    /**
     * shardingsphere数据源优先级
     */
    int SHARDINGSPHERE_ORDER = 0000;

    /**
     * JNDI数据源优先级
     */
    int JNDI_ORDER = 1000;

    /**
     * HIKARI数据源优先级
     */
    int HIKARI_ORDER = 2000;

    /**
     * DRUID数据源优先级
     */
    int DRUID_ORDER = 3000;

    /**
     * BEECP数据源优先级
     */
    int BEECP_ORDER = 4000;

    /**
     * C3P0数据源优先级
     */
    int C3P0_ORDER = 5000;

    /**
     * DBCP2数据源优先级
     */
    int DBCP2_ORDER = 6000;

    /**
     * ATOMIKOS数据源优先级
     */
    int ATOMIKOS_ORDER = 7000;

    /**
     * BASIC数据源优先级
     */
    int BASIC_ORDER = Integer.MAX_VALUE;
}