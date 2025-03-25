package top.mingempty.mybatis.flex.config;

import com.mybatisflex.core.handler.CompositeEnumTypeHandler;
import com.mybatisflex.core.table.DynamicTableProcessor;
import com.mybatisflex.spring.boot.ConfigurationCustomizer;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.mingempty.commons.util.ReflectionUtil;
import top.mingempty.mybatis.config.MyBatisConfiguration;
import top.mingempty.mybatis.flex.handler.MeMFCompositeEnumTypeHandler;
import top.mingempty.mybatis.flex.model.MybatisFlexProperties;
import top.mingempty.mybatis.tool.ReplacedTableNameTool;

import java.util.Optional;

@Configuration
@AutoConfigureAfter({MyBatisConfiguration.class})
@EnableConfigurationProperties(MybatisFlexProperties.class)
@ConditionalOnProperty(prefix = "me.mybatis-flex", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MyBatisFlexConfiguration {

    /**
     * mybatis flex表名称自动自动拦截替换配置
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "me.mybatis-flex", name = "replaced-table-name", havingValue = "true")
    public DynamicTableProcessor dynamicTableProcessor() {
        return tableName -> Optional.ofNullable(ReplacedTableNameTool.gainReplacedTableName())
                .map(replacedTableName -> replacedTableName.getOrDefault(tableName, tableName))
                .orElse(tableName);
    }

    @Configuration
    @ConditionalOnClass(name = {"com.mybatisflex.spring.boot.ConfigurationCustomizer"})
    public static class MeConfigurationCustomizer {
        /**
         * mybatis后置配置
         *
         * @return
         */
        @Bean
        public ConfigurationCustomizer mybatisConfigurationCustomizer() {
            return mybatisConfiguration -> {
                TypeHandlerRegistry typeHandlerRegistry = mybatisConfiguration.getTypeHandlerRegistry();
                Class<? extends TypeHandler> defaultEnumTypeHandler
                        = ReflectionUtil.getValue("defaultEnumTypeHandler", typeHandlerRegistry);
                if (CompositeEnumTypeHandler.class.equals(defaultEnumTypeHandler)) {
                    // 设置默认枚举处理器（替换为你实际的类）
                    mybatisConfiguration.setDefaultEnumTypeHandler(MeMFCompositeEnumTypeHandler.class);
                }
            };
        }
    }

}
