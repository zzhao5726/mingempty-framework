package top.mingempty.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;
import top.mingempty.gateway.domain.GatewayConstant;
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


    /**
     * 跨域问题处理
     *
     * @return 过滤器
     */
    @Bean
    public CorsWebFilter corsWebFilter(GatewayProperties gatewayProperties) {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", corsConfiguration(gatewayProperties));
        return new CorsWebFilter(source);
    }


    private CorsConfiguration corsConfiguration(GatewayProperties gatewayProperties) {
        CorsConfiguration corsConfiguration = gatewayProperties.getCorsConfiguration();
        if (corsConfiguration != null) {
            return corsConfiguration;
        }
        corsConfiguration = new CorsConfiguration();
        // 允许cookies跨域
        corsConfiguration.setAllowCredentials(true);
        // #允许向该服务器提交请求的URI，*表示全部允许
        corsConfiguration.addAllowedOriginPattern(GatewayConstant.ALL);
        // #允许访问的头信息,*表示全部
        corsConfiguration.addAllowedHeader(GatewayConstant.ALL);
        // 允许提交请求的方法类型，*表示全部允许
        corsConfiguration.addAllowedMethod(GatewayConstant.ALL);
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        corsConfiguration.setMaxAge(GatewayConstant.MAX_AGE);

        return corsConfiguration;
    }
}