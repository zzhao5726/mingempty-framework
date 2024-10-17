package top.mingempty.datasource.model;

import cn.hutool.core.lang.Assert;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * 轮训动态数据源
 *
 * @author zzhao
 */
public class RoundRobinDatasource implements DataSource {

    private final DataSource DATA_SOURCE;
    private final List<DataSource> DATA_SOURCES;
    private final AtomicInteger ATOMIC_INTEGER;

    public RoundRobinDatasource(DataSource dataSource) {
        Assert.notNull(dataSource, "dataSource can not be null");
        this.DATA_SOURCE = dataSource;
        this.DATA_SOURCES = null;
        this.ATOMIC_INTEGER = null;
    }

    public RoundRobinDatasource(List<DataSource> dataSources) {
        Assert.notEmpty(dataSources, "dataSources can not be empty");
        if (dataSources.size() == 1) {
            Assert.notNull(dataSources.getFirst(), "dataSource can not be null");
            DATA_SOURCE = dataSources.getFirst();
            DATA_SOURCES = null;
            ATOMIC_INTEGER = null;
        } else {
            DATA_SOURCE = null;
            DATA_SOURCES = new CopyOnWriteArrayList<>(dataSources);
            ATOMIC_INTEGER = new AtomicInteger(0);
        }
    }

    private DataSource roundRobin() {
        if (DATA_SOURCE != null) {
            return DATA_SOURCE;
        }
        //获取当前值，同时，如果当前值大于等于数据源数据量时，设置值为0
        int andAccumulate = ATOMIC_INTEGER.getAndAccumulate(1, (left, right) -> {
            if (left >= DATA_SOURCES.size()) {
                return 0;
            }
            return left + right;
        });
        return DATA_SOURCES.get(andAccumulate % DATA_SOURCES.size());
    }

    /*=================DataSource==========================*/


    @Override
    public Connection getConnection() throws SQLException {
        return this.roundRobin().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return this.roundRobin().getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return this.roundRobin().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.roundRobin().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        this.roundRobin().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return this.roundRobin().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.roundRobin().getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.roundRobin().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.roundRobin().isWrapperFor(iface);
    }
}
