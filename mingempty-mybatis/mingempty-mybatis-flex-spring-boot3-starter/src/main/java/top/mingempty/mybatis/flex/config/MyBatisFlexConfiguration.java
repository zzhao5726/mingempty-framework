package top.mingempty.mybatis.flex.config;

import com.mybatisflex.core.table.DynamicTableProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.mingempty.mybatis.config.MyBatisConfiguration;
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

}
