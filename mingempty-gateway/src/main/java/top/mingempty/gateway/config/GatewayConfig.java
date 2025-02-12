package top.mingempty.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.mingempty.gateway.domain.GatewayProperties;
import top.mingempty.gateway.filter.MeGlobakFilter;

/**
 * 网关配置
 *
 * @author zzhao
 * @date 2023/2/27 20:13
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(GatewayProperties.class)
public class GatewayConfig {


    /**
     * 全局拦截器，微服务内部清洗
     *
     * @return 全局拦截器
     */
    @Bean
    public MeGlobakFilter meGlobakFilter(GatewayProperties gatewayProperties) {
        return new MeGlobakFilter(gatewayProperties);
    }

}