package top.mingempty.datasource.model;

import cn.hutool.core.collection.CollUtil;
import com.google.common.base.Preconditions;
import top.mingempty.datasource.aspect.DsAspect;
import top.mingempty.datasource.enums.ClusterMode;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.logging.Logger;

/**
 * 动态主从数据源
 *
 * @author zzhao
 */
public class MasterSlaveDynamicDatasource implements DataSource {

    private final RoundRobinDatasource MASTER_ROUND_ROBIN_DATASOURCE;
    private final RoundRobinDatasource SLAVE_ROUND_ROBIN_DATASOURCE;


    public MasterSlaveDynamicDatasource(ClusterMode clusterMode, List<DataSource> masterDataSources) {
        this(clusterMode, CollUtil.isEmpty(masterDataSources) ? null : new RoundRobinDatasource(masterDataSources));
    }

    public MasterSlaveDynamicDatasource(ClusterMode clusterMode, RoundRobinDatasource roundRobinDatasource) {
        this(ClusterMode.MASTER.equals(clusterMode) ? roundRobinDatasource : null,
                ClusterMode.SLAVE.equals(clusterMode) ? null : roundRobinDatasource);
    }

    public MasterSlaveDynamicDatasource(List<DataSource> masterDataSources, List<DataSource> slaveDataSources) {
        this(CollUtil.isEmpty(masterDataSources) ? null : new RoundRobinDatasource(masterDataSources),
                CollUtil.isEmpty(slaveDataSources) ? null : new RoundRobinDatasource(slaveDataSources));
    }

    public MasterSlaveDynamicDatasource(RoundRobinDatasource masterRoundRobinDatasource, RoundRobinDatasource slaveRoundRobinDatasource) {
        Preconditions.checkArgument(masterRoundRobinDatasource != null || slaveRoundRobinDatasource != null,
                "master or slave must be not null");
        this.MASTER_ROUND_ROBIN_DATASOURCE = masterRoundRobinDatasource;
        this.SLAVE_ROUND_ROBIN_DATASOURCE = slaveRoundRobinDatasource;
    }


    /**
     * 读写数据源切换
     *
     * @return
     */
    public DataSource masterSlave() {
        //只要有一个为空，就返回不为空的那个
        if (MASTER_ROUND_ROBIN_DATASOURCE != null
                && SLAVE_ROUND_ROBIN_DATASOURCE == null) {
            return MASTER_ROUND_ROBIN_DATASOURCE;
        }

        if (SLAVE_ROUND_ROBIN_DATASOURCE != null
                && MASTER_ROUND_ROBIN_DATASOURCE == null) {
            return SLAVE_ROUND_ROBIN_DATASOURCE;
        }

        return switch (DsAspect.acquireDs().type()) {
            case MASTER -> MASTER_ROUND_ROBIN_DATASOURCE;
            case SLAVE -> SLAVE_ROUND_ROBIN_DATASOURCE;
        };
    }

    /*=================DataSource==========================*/


    @Override
    public Connection getConnection() throws SQLException {
        return this.masterSlave().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return this.masterSlave().getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return this.masterSlave().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.masterSlave().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        this.masterSlave().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return this.masterSlave().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return this.masterSlave().getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return this.masterSlave().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return this.masterSlave().isWrapperFor(iface);
    }
}
