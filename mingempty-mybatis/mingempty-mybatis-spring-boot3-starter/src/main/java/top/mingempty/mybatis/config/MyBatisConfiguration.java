package top.mingempty.mybatis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import top.mingempty.mybatis.interceptor.MybatisReplacedTableNameInterceptor;
import top.mingempty.mybatis.model.MybatisProperties;

@ComponentScan(basePackages = {"top.mingempty.mybatis"})
@EnableConfigurationProperties(MybatisProperties.class)
@ConditionalOnProperty(prefix = "me.mybatis", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MyBatisConfiguration {


    /**
     * mybatis表名称自动自动拦截替换配置
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "me.mybatis", name = "replaced-table-name", havingValue = "true")
    public MybatisReplacedTableNameInterceptor mybatisReplacedTableNameInterceptor() {
        return new MybatisReplacedTableNameInterceptor();
    }
}