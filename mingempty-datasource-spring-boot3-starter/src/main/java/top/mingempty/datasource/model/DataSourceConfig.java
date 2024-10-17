package top.mingempty.datasource.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import top.mingempty.datasource.creator.atomikos.AtomikosConfig;
import top.mingempty.datasource.creator.beecp.BeeCpConfig;
import top.mingempty.datasource.creator.c3p0.C3p0Config;
import top.mingempty.datasource.creator.dbcp.Dbcp2Config;
import top.mingempty.datasource.creator.druid.DruidConfig;
import top.mingempty.datasource.creator.hikaricp.HikariCpConfig;
import top.mingempty.datasource.creator.shardingsphere.DruidStandalonePersistRepository;
import top.mingempty.datasource.creator.shardingsphere.NacosShardingspherePropertiesGain;
import top.mingempty.datasource.creator.shardingsphere.ShardingspherePropertiesGain;
import top.mingempty.datasource.enums.ClusterMode;
import top.mingempty.datasource.enums.SeataMode;
import top.mingempty.domain.dynamic.DynamicConfig;

import javax.sql.DataSource;

/**
 * @author zzhao
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class DataSourceConfig extends DynamicConfig {

    /**
     * 连接池类型
     * <p>
     * 如果不设置自动查找 Druid > HikariCp
     */
    @Schema(title = "连接池类型", description = "如果不设置自动查找 Druid > HikariCp")
    private Class<? extends DataSource> type;

    /**
     * JDBC driver
     */
    @Schema(title = "JDBC driver")
    private String driverClassName;

    /**
     * JDBC url 地址
     */
    @Schema(title = "JDBC url 地址")
    private String url;

    /**
     * JDBC 用户名
     */
    @Schema(title = "JDBC 用户名")
    private String username;

    /**
     * JDBC 密码
     */
    @Schema(title = "JDBC 密码")
    private String password;

    /**
     * jndi数据源名称
     * <p>
     * 设置即表示启用
     */
    @Schema(title = "jndi数据源名称", description = "设置即表示启用")
    private String jndiName;

    /**
     * shardingsphere路径.
     * <p>
     * 设置即表示启用。
     * <p>
     * 采用spi机制，本框架已实现基于classpath、本地路径或者nacos注册中心读取配置文件。</br>
     * 使用nacos时，请注入{@link NacosShardingspherePropertiesGain}实现类。采用项目配置的nacos配置中心地址以及命名空间，获取指定分组的指定文件内容。</br>
     * 自定义时，请实现{@link ShardingspherePropertiesGain}接口</br>
     *
     * <p>
     * 使用classpath或本地路径</br>
     * 示例：</br>
     * classpath:shardingsphere.yaml</br>
     * file:/etc/shardingsphere/shardingsphere.yaml</br>
     * <p>
     * 使用nacos注册中心</br>
     * 示例：</br>
     * nacos:shardingsphere.yaml（此处为使用默认分组的配置）</br>
     * nacos:group:shardingsphere.yaml（此处为使用指定分组的配置）</br>
     *
     * <p>
     * 数据库连接池：
     * 默认采用 HikariCp连接池，增加对druid的支持。</br>
     * 用法：</br>
     * 1.对配置项｛@code mode.repository.type｝设置为druid，
     * 同时利用spi机制注入{@link DruidStandalonePersistRepository}
     * 2.对配置项{@code dataSources.{数据源名称}.dataSourceClassName}设置为com.alibaba.druid.pool.DruidDataSource
     * 3.针对其他数据库连接池，可参考{@link DruidStandalonePersistRepository}进行自定义实现
     */
    @Schema(title = "shardingsphere路径", description = "设置即表示启用")
    private String shardingspherePath;

    /**
     * 集群模式
     * <p>
     * 名称{@link  DynamicConfig#name}相同代表为同数据源，对组内不同模式数据做负载均衡
     */
    @Schema(title = "集群模式")
    private ClusterMode clusterMode = ClusterMode.MASTER;

    /**
     * 是否启用seata
     */
    @Schema(title = "是否启用seata")
    private boolean seata = false;

    /**
     * seata模式
     */
    @Schema(title = "seata模式")
    private SeataMode seataMode;

    /**
     * lazy init datasource
     */
    @Schema(title = "lazy init datasource")
    private boolean lazy = false;

    /**
     * 初始化
     */
    @Schema(title = "初始化")
    @NestedConfigurationProperty
    private DatasourceInitProperty init = new DatasourceInitProperty();

    /**
     * Druid参数配置
     */
    @Schema(title = "Druid参数配置")
    @NestedConfigurationProperty
    private DruidConfig druid = new DruidConfig();

    /**
     * HikariCp参数配置
     */
    @Schema(title = "HikariCp参数配置")
    @NestedConfigurationProperty
    private HikariCpConfig hikari = new HikariCpConfig();

    /**
     * BeeCp参数配置
     */
    @Schema(title = "BeeCp参数配置")
    @NestedConfigurationProperty
    private BeeCpConfig beecp = new BeeCpConfig();

    /**
     * DBCP2参数配置
     */
    @Schema(title = "DBCP2参数配置")
    @NestedConfigurationProperty
    private Dbcp2Config dbcp2 = new Dbcp2Config();

    /**
     * atomikos参数配置
     */
    @Schema(title = "atomikos参数配置")
    @NestedConfigurationProperty
    private AtomikosConfig atomikos = new AtomikosConfig();

    /**
     * c3p0参数配置
     */
    @Schema(title = "c3p0参数配置")
    @NestedConfigurationProperty
    private C3p0Config c3p0 = new C3p0Config();
}