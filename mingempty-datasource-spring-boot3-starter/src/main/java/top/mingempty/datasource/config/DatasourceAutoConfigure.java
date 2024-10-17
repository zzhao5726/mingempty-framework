package top.mingempty.datasource.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import top.mingempty.datasource.aspect.DsAspect;
import top.mingempty.datasource.aspect.DsTransactionalAspect;
import top.mingempty.datasource.creator.DataSourceCreator;
import top.mingempty.datasource.creator.DataSourceExtended;
import top.mingempty.datasource.creator.DefaultDataSourceCreator;
import top.mingempty.datasource.creator.atomikos.AtomikosConfig;
import top.mingempty.datasource.creator.atomikos.AtomikosDataSourceCreator;
import top.mingempty.datasource.creator.basic.BasicDataSourceCreator;
import top.mingempty.datasource.creator.beecp.BeeCpConfig;
import top.mingempty.datasource.creator.beecp.BeeCpDataSourceCreator;
import top.mingempty.datasource.creator.c3p0.C3p0Config;
import top.mingempty.datasource.creator.c3p0.C3p0DataSourceCreator;
import top.mingempty.datasource.creator.dbcp.Dbcp2Config;
import top.mingempty.datasource.creator.dbcp.Dbcp2DataSourceCreator;
import top.mingempty.datasource.creator.druid.DruidConfig;
import top.mingempty.datasource.creator.druid.DruidDataSourceCreator;
import top.mingempty.datasource.creator.hikaricp.HikariCpConfig;
import top.mingempty.datasource.creator.hikaricp.HikariDataSourceCreator;
import top.mingempty.datasource.creator.jndi.JndiDataSourceCreator;
import top.mingempty.datasource.creator.shardingsphere.ShardingsphereDataSourceCreator;
import top.mingempty.datasource.creator.wrap.DataSourceCreatorWrapper;
import top.mingempty.datasource.creator.wrap.SeataDataSourceCreatorWrapper;
import top.mingempty.datasource.model.DataSourceProperty;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({DataSourceProperty.class})
@EnableAutoConfiguration(excludeName = {
        "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
        "org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration"})
@ConditionalOnProperty(prefix = "me.datasource", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DatasourceAutoConfigure {

    private final DataSourceProperty dataSourceProperty;

    @Bean
    @ConditionalOnClass(name = {"io.seata.rm.datasource.xa.DataSourceProxyXA"})
//    @ConditionalOnProperty(prefix = "me.datasource", name = {"seata-enabled"}, havingValue = "true")
    @ConditionalOnExpression("${me.datasource.seata-enabled:true} || ${me.datasource.seataEnabled:true}")
    public DataSourceCreatorWrapper seataDataSourceCreatorWrapper() {
        return new SeataDataSourceCreatorWrapper();
    }

    @Bean
    @ConditionalOnClass(name = {"org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup"})
    public DataSourceCreator jndiDataSourceCreator() {
        return new JndiDataSourceCreator();
    }

    @Bean
    @ConditionalOnClass(name = {"org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource"})
    public DataSourceCreator shardingsphereDataSourceCreator() {
        return new ShardingsphereDataSourceCreator();
    }

    @Bean
    @ConditionalOnClass(name = {"com.zaxxer.hikari.HikariDataSource"})
    public DataSourceCreator hikariDataSourceCreator() {
        HikariCpConfig config = Optional.ofNullable(dataSourceProperty.def())
                .flatMap(dataSourceConfig -> Optional.ofNullable(dataSourceConfig.getHikari()))
                .orElse(new HikariCpConfig());
        return new HikariDataSourceCreator(config);
    }

    @Bean
    @ConditionalOnClass(name = {"com.alibaba.druid.pool.DruidDataSource"})
    public DataSourceCreator druidDataSourceCreator() {
        DruidConfig config = Optional.ofNullable(dataSourceProperty.def())
                .flatMap(dataSourceConfig -> Optional.ofNullable(dataSourceConfig.getDruid()))
                .orElse(new DruidConfig());
        return new DruidDataSourceCreator(config);
    }

    @Bean
    @ConditionalOnClass(name = {"cn.beecp.BeeDataSource"})
    public DataSourceCreator beeCpDataSourceCreator() {
        BeeCpConfig config = Optional.ofNullable(dataSourceProperty.def())
                .flatMap(dataSourceConfig -> Optional.ofNullable(dataSourceConfig.getBeecp()))
                .orElse(new BeeCpConfig());
        return new BeeCpDataSourceCreator(config);
    }

    @Bean
    @ConditionalOnClass(name = {"com.mchange.v2.c3p0.ComboPooledDataSource"})
    public DataSourceCreator c3p0DataSourceCreator() {
        C3p0Config config = Optional.ofNullable(dataSourceProperty.def())
                .flatMap(dataSourceConfig -> Optional.ofNullable(dataSourceConfig.getC3p0()))
                .orElse(new C3p0Config());
        return new C3p0DataSourceCreator(config);
    }

    @Bean
    @ConditionalOnClass(name = {"org.apache.commons.dbcp2.BasicDataSource"})
    public DataSourceCreator dbcp2DataSourceCreator() {
        Dbcp2Config config = Optional.ofNullable(dataSourceProperty.def())
                .flatMap(dataSourceConfig -> Optional.ofNullable(dataSourceConfig.getDbcp2()))
                .orElse(new Dbcp2Config());
        return new Dbcp2DataSourceCreator(config);
    }

    @Bean
    @ConditionalOnClass(name = {"com.atomikos.jdbc.AtomikosDataSourceBean"})
    public DataSourceCreator atomikosDataSourceCreator() {
        AtomikosConfig config = Optional.ofNullable(dataSourceProperty.def())
                .flatMap(dataSourceConfig -> Optional.ofNullable(dataSourceConfig.getAtomikos()))
                .orElse(new AtomikosConfig());
        return new AtomikosDataSourceCreator(config);
    }

    @Bean
    @ConditionalOnClass(name = {"org.springframework.boot.jdbc.DataSourceBuilder"})
    public DataSourceCreator basicDataSourceCreator() {
        return new BasicDataSourceCreator();
    }

    @Bean
    public DefaultDataSourceCreator defaultDataSourceCreator(
            @Autowired(required = false) List<DataSourceCreator> dataSourceCreators,
            @Autowired(required = false) List<DataSourceCreatorWrapper> dataSourceCreatorsWrappers,
            @Autowired(required = false) List<DataSourceExtended> dataSourceExtendeds) {
        return new DefaultDataSourceCreator(dataSourceCreators, dataSourceCreatorsWrappers, dataSourceExtendeds);
    }

    @Bean
    public DataSource dataSource(DefaultDataSourceCreator defaultDataSourceCreator) {
        return defaultDataSourceCreator.createDataSource(this.dataSourceProperty);
    }


    @Bean
    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    public DsAspect dsAspect() {
        return new DsAspect();
    }


    @Bean
    @Order(value = Ordered.HIGHEST_PRECEDENCE + 1)
    public DsTransactionalAspect dsTransactionalAspect() {
        return new DsTransactionalAspect();
    }

}
