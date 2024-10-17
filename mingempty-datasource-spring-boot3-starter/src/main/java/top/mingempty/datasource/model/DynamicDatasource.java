package top.mingempty.datasource.model;

import cn.hutool.core.util.StrUtil;
import top.mingempty.datasource.aspect.DsAspect;
import top.mingempty.datasource.transaction.ConnectionFactory;
import top.mingempty.datasource.transaction.ConnectionProxy;
import top.mingempty.datasource.transaction.TransactionContext;
import top.mingempty.domain.dynamic.AbstractDynamicInstance;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * 动态数据源
 *
 * @author zzhao
 */
public class DynamicDatasource extends AbstractDynamicInstance<DataSource, DynamicDatasource, DataSourceProperty, DataSourceConfig> implements DataSource {


    public DynamicDatasource(DataSourceProperty property, DataSource instance) {
        super(property, instance);
    }

    public DynamicDatasource(DataSourceProperty property, DataSource instance, boolean lenientFallback) {
        super(property, instance, lenientFallback);
    }

    /**
     * 检索查找实例的方式
     */
    @Override
    protected String determineCurrentLookupKey() {
        DsAspect.DsInfo dsInfo = DsAspect.acquireDs();
        return Optional.ofNullable(dsInfo)
                .flatMap(d -> Optional.ofNullable(d.dsName()))
                .orElse(null);
    }

    /**
     * 当前实例集类型
     *
     * @return
     */
    @Override
    protected Class<DataSource> type() {
        return DataSource.class;
    }

    /*=================DataSource==========================*/


    @Override
    public Connection getConnection() throws SQLException {
        String xid = TransactionContext.getXID();
        if (StrUtil.isEmpty(xid)) {
            return this.determineInstance().getConnection();
        } else {
            String ds = determineCurrentLookupKey();
            ds = StrUtil.isEmpty(ds) ? getProperty().getPrimary() : ds;
            ConnectionProxy connection = ConnectionFactory.getConnection(xid, ds);
            return connection == null ? getConnectionProxy(xid, ds, this.gainInstance(ds).getConnection()) : connection;
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        String xid = TransactionContext.getXID();
        if (StrUtil.isEmpty(xid)) {
            return this.determineInstance().getConnection();
        } else {
            String ds = determineCurrentLookupKey();
            ds = StrUtil.isEmpty(ds) ? getProperty().getPrimary() : ds;
            ConnectionProxy connection = ConnectionFactory.getConnection(xid, ds);
            return connection == null ? getConnectionProxy(xid, ds, this.gainInstance(ds).getConnection(username, password)) : connection;
        }
    }

    private Connection getConnectionProxy(String xid, String ds, Connection connection) {
        ConnectionProxy connectionProxy = new ConnectionProxy(connection, ds);
        ConnectionFactory.putConnection(xid, ds, connectionProxy);
        return connectionProxy;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return this.determineInstance().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.determineInstance().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        this.determineInstance().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return this.determineInstance().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.determineInstance().getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.determineInstance().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.determineInstance().isWrapperFor(iface);
    }
}
