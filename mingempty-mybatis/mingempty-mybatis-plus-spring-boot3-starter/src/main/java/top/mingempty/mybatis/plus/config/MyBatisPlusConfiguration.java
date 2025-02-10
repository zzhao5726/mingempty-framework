package top.mingempty.mybatis.plus.config;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.mingempty.mybatis.config.MyBatisConfiguration;
import top.mingempty.mybatis.plus.model.MybatisPlusProperties;
import top.mingempty.mybatis.tool.ReplacedTableNameTool;

import java.util.List;
import java.util.Optional;

@Configuration
@AutoConfigureAfter({MyBatisConfiguration.class})
@EnableConfigurationProperties(MybatisPlusProperties.class)
@ConditionalOnProperty(prefix = "me.mybatis-plus", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MyBatisPlusConfiguration {


    /**
     * 自定义的注入
     * 后续有别的增加建议获取到这个bean，直接进行添加，而不是新创建一个bean
     *
     * @return
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig conf = new GlobalConfig();
        // 自定义的注入需要在这里进行配置
        conf.setSqlInjector(new DefaultSqlInjector() {
            @Override
            public List<AbstractMethod> getMethodList(org.apache.ibatis.session.Configuration configuration, Class<?> mapperClass, TableInfo tableInfo) {
                List<AbstractMethod> methodList = super.getMethodList(configuration, mapperClass, tableInfo);
                methodList.add(new InsertBatchSomeColumn());
                return methodList;
            }
        });
        return conf;
    }

    /**
     * 添加增强插件
     * 后续有别的增加建议获取到这个bean，直接进行添加，而不是新创建一个bean
     **/
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(@Autowired(required = false)
                                                             DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        if (ObjUtil.isNotEmpty(dynamicTableNameInnerInterceptor)) {
            interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);
        }

        return interceptor;
    }

    /**
     * mybatis plus表名称自动自动拦截替换配置
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "me.mybatis-plus", name = "replaced-table-name", havingValue = "true")
    public DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor() {
        DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler((sql, tableName)
                -> Optional.ofNullable(ReplacedTableNameTool.gainReplacedTableName())
                .map(replacedTableName -> replacedTableName.getOrDefault(tableName, tableName))
                .orElse(tableName));
        return dynamicTableNameInnerInterceptor;
    }

}
