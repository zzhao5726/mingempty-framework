package top.mingempty.datasource.enums;

import lombok.Getter;

/**
 * 目前支持的XA数据源
 *
 * @author <a href="mailto:945514650@qq.com">zzhao</a>
 */
@Getter
public enum XADataSourceEnum {
    /**
     * <a href="https://dev.mysql.com/doc/connector-j/en/connector-j-api-changes.html">MySQL Connector/J 5.1 使用老的 MysqlXADataSource</a>
     */
    MYSQL_CONNECTOR_J_5("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource"),
    /**
     * mysql
     */
    MYSQL("com.mysql.cj.jdbc.MysqlXADataSource"),
    /**
     * oracle
     */
    ORACLE("oracle.jdbc.xa.client.OracleXADataSource"),
    /**
     * postgresql
     */
    POSTGRE_SQL("org.postgresql.xa.PGXADataSource"),
    /**
     * h2
     */
    H2("org.h2.jdbcx.JdbcDataSource");

    /**
     * xa数据源类名
     */
    private final String xaDriverClassName;

    /**
     * 构造方法
     *
     * @param xaDriverClassName
     */
    XADataSourceEnum(String xaDriverClassName) {
        this.xaDriverClassName = xaDriverClassName;
    }

    /**
     * 是否包含
     *
     * @param xaDataSourceClassName xa数据源类名
     * @return boolean 包含
     */
    public static boolean contains(String xaDataSourceClassName) {
        for (XADataSourceEnum item : values()) {
            if (item.getXaDriverClassName().equals(xaDataSourceClassName)) {
                return true;
            }
        }
        return false;
    }
}
