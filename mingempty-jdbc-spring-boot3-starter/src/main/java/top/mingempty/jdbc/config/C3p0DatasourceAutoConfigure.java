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
import top.mingempty.jdbc.domain.C3p0DataSourceProperties;
import top.mingempty.jdbc.domain.MeDatasourceWrapper;
import top.mingempty.jdbc.factory.C3p0DatasourceFactory;
import top.mingempty.jdbc.factory.DatasourceFactory;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zzhao
 */
@Order(3)
@Configuration
@EnableConfigurationProperties({C3p0DataSourceProperties.class})
@ConditionalOnClass(name = {"com.mchange.v2.c3p0.ComboPooledDataSource"})
@ConditionalOnProperty(prefix = "me.datasource.c3p0", name = "enabled", havingValue = "true")
@ConditionalOnMissingBean(value = {HikariDatasourceAutoConfigure.class, DruidDatasourceAutoConfigure.class})
public class C3p0DatasourceAutoConfigure {

    /**
     * 创建c3p0数据源工厂
     *
     * @return c3p0DatasourceFactory
     */
    @Bean
    public DatasourceFactory c3p0DatasourceFactory() {
        return new C3p0DatasourceFactory();
    }


    @Bean
    @Primary
    public DataSource dataSource(DatasourceFactory c3p0DatasourceFactory, C3p0DataSourceProperties c3p0DataSourceProperties) {
        ConcurrentHashMap<String, DataSource> targetRouter = new ConcurrentHashMap<>(2);
        targetRouter.put(GlobalConstant.DEFAULT_INSTANCE_NAME, c3p0DatasourceFactory.createDataSource(c3p0DataSourceProperties));
        if (MapUtil.isNotEmpty(c3p0DataSourceProperties.getMore())) {
            c3p0DataSourceProperties
                    .getMore()
                    .forEach((key, value)
                            -> targetRouter.put(key, c3p0DatasourceFactory.createDataSource(value)));
        }
        return new MeDatasourceWrapper(GlobalConstant.DEFAULT_INSTANCE_NAME, targetRouter);
    }


}
