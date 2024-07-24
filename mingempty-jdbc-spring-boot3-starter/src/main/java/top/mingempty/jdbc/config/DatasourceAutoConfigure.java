package top.mingempty.jdbc.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.mingempty.jdbc.aspect.DatasourceAspect;
import top.mingempty.jdbc.aspect.SeataAtAspect;
import top.mingempty.jdbc.aspect.SeataXaAspect;
import top.mingempty.jdbc.domain.DataSourceProperties;

/**
 * 默认的数据源配置
 *
 * @author zzhao
 */
@Configuration
@EnableConfigurationProperties({DataSourceProperties.class})
@EnableAutoConfiguration(excludeName = {
        "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
        "io.seata.spring.boot.autoconfigure.SeataDataSourceAutoConfiguration"})
public class DatasourceAutoConfigure {


    /**
     * 对数据源切面的注入
     *
     * @return
     */
    @Bean
    public DatasourceAspect datasourceAspect() {
        return new DatasourceAspect();
    }


    /**
     * 对数据源Seater At切面的注入
     *
     * @return
     */
    @Bean
    @ConditionalOnClass(name = "io.seata.rm.datasource.DataSourceProxy")
    @ConditionalOnExpression("#{'${me.datasource.enabled-seata}'.equals('true') " +
            " and '${me.datasource.enabled-auto-seata-data-source-proxy}'.equals('true')"+
            " and '${me.datasource.seata-data-source-proxy-mode}'.equals('AT')}")
    public SeataAtAspect seataAtAspect() {
        return new SeataAtAspect();
    }


    /**
     * 对数据源Seater At切面的注入
     *
     * @return
     */
    @Bean
    @ConditionalOnClass(name = "io.seata.rm.datasource.DataSourceProxy")
    @ConditionalOnExpression("#{'${me.datasource.enabled-seata}'.equals('true') " +
            " and '${me.datasource.enabled-auto-seata-data-source-proxy}'.equals('true')"+
            " and '${me.datasource.seata-data-source-proxy-mode}'.equals('XA')}")
    public SeataXaAspect seataXaAspect() {
        return new SeataXaAspect();
    }


}
