package top.mingempty.jdbc.config;

import cn.hutool.core.map.MapUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.jdbc.domain.HikariDataSourceProperties;
import top.mingempty.jdbc.domain.MeDatasourceWrapper;
import top.mingempty.jdbc.factory.DatasourceFactory;
import top.mingempty.jdbc.factory.HikariDatasourceFactory;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zzhao
 */
@Order(1)
@Configuration
@EnableConfigurationProperties({HikariDataSourceProperties.class})
@ConditionalOnClass(name = {"com.zaxxer.hikari.HikariDataSource"})
@ConditionalOnProperty(prefix = "me.datasource.hikari", name = "enabled", havingValue = "true")
@ConditionalOnMissingBean(value = {DruidDatasourceAutoConfigure.class, C3p0DatasourceAutoConfigure.class})
public class HikariDatasourceAutoConfigure {

    /**
     * 创建hikari数据源工厂
     *
     * @return hikariDatasourceFactory
     */
    @Bean
    public DatasourceFactory hikariDatasourceFactory() {
        return new HikariDatasourceFactory();
    }


    @Bean
    @Primary
    public DataSource dataSource(DatasourceFactory hikariDatasourceFactory, HikariDataSourceProperties hikariDataSourceProperties) {
        ConcurrentHashMap<String, DataSource> targetRouter = new ConcurrentHashMap<>(2);
        targetRouter.put(GlobalConstant.DEFAULT_INSTANCE_NAME, hikariDatasourceFactory.createDataSource(hikariDataSourceProperties));
        if (MapUtil.isNotEmpty(hikariDataSourceProperties.getMore())) {
            hikariDataSourceProperties
                    .getMore()
                    .forEach((key, value)
                            -> targetRouter.put(key, hikariDatasourceFactory.createDataSource(value)));
        }
        return new MeDatasourceWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, targetRouter);
    }
}
