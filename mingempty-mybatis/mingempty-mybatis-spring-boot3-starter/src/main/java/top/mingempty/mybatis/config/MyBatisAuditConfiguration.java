package top.mingempty.mybatis.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import top.mingempty.mybatis.interceptor.MybatisAuditDataInterceptor;
import top.mingempty.mybatis.model.MybatisProperties;
import top.mingempty.mybatis.tool.AuditOperatorTool;

/**
 * 处理审计字段配置类
 *
 * @author zzhao
 */
@AllArgsConstructor
@ConditionalOnProperty(prefix = "me.mybatis", name = "audit-enabled", havingValue = "true", matchIfMissing = true)
public class MyBatisAuditConfiguration {


    private final MybatisProperties mybatisProperties;

    /**
     * Mybatis处理审计字段拦截器
     *
     * @return
     */
    @Bean
    public MybatisAuditDataInterceptor mybatisAuditDataInterceptor() {
        AuditOperatorTool.init(mybatisProperties.getAuditOperator());
        return new MybatisAuditDataInterceptor();
    }


}