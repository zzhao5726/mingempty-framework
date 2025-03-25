package top.mingempty.mybatis.config;

import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import top.mingempty.commons.util.ReflectionUtil;
import top.mingempty.mybatis.interceptor.MybatisReplacedTableNameInterceptor;
import top.mingempty.mybatis.model.MybatisProperties;
import top.mingempty.mybatis.type.MeEnumTypeHandler;

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

    @Configuration
    @ConditionalOnClass(name = {"org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer"})
    public static class MeConfigurationCustomizer {
        /**
         * mybatis后置配置
         *
         * @return
         */
        @Bean
        public ConfigurationCustomizer mybatisConfigurationCustomizer() {
            return configuration -> {
                TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
                Class<? extends TypeHandler> defaultEnumTypeHandler
                        = ReflectionUtil.getValue("defaultEnumTypeHandler", typeHandlerRegistry);
                if (EnumTypeHandler.class.equals(defaultEnumTypeHandler)) {
                    // 设置默认枚举处理器（替换为你实际的类）
                    configuration.setDefaultEnumTypeHandler(MeEnumTypeHandler.class);
                }
            };
        }
    }
}