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
import top.mingempty.jdbc.domain.DruidDataSourceProperties;
import top.mingempty.jdbc.domain.MeDatasourceWrapper;
import top.mingempty.jdbc.factory.DatasourceFactory;
import top.mingempty.jdbc.factory.DruidDatasourceFactory;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zzhao
 */
@Order(2)
@Configuration
@EnableConfigurationProperties({DruidDataSourceProperties.class})
@ConditionalOnClass(name = {"com.alibaba.druid.pool.DruidDataSource"})
@ConditionalOnProperty(prefix = "me.datasource.druid", name = "enabled", havingValue = "true")
@ConditionalOnMissingBean(value = {HikariDatasourceAutoConfigure.class, C3p0DatasourceAutoConfigure.class})
public class DruidDatasourceAutoConfigure {

    /**
     * 创建druid数据源工厂
     *
     * @return druidDatasourceFactory
     */
    @Bean
    public DatasourceFactory druidDatasourceFactory() {
        return new DruidDatasourceFactory();
    }


    @Bean
    @Primary
    public DataSource dataSource(DatasourceFactory druidDatasourceFactory, DruidDataSourceProperties druidDataSourceProperties) {
        ConcurrentHashMap<String, DataSource> targetRouter = new ConcurrentHashMap<>(2);
        targetRouter.put(GlobalConstant.DEFAULT_INSTANCE_NAME, druidDatasourceFactory.createDataSource(druidDataSourceProperties));
        if (MapUtil.isNotEmpty(druidDataSourceProperties.getMore())) {
            druidDataSourceProperties
                    .getMore()
                    .forEach((key, value)
                            -> targetRouter.put(key, druidDatasourceFactory.createDataSource(value)));
        }
        return new MeDatasourceWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, targetRouter);
    }
}
