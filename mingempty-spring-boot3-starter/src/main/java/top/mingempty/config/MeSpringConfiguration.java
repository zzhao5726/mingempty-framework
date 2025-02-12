package top.mingempty.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.domain.MeGloableProperty;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.util.BeanFactoryUtil;
import top.mingempty.util.EnvironmentUtil;
import top.mingempty.util.MessageSourceUtil;
import top.mingempty.util.SpringContextUtil;

/**
 * mingempty cloud 基础工具配置类
 *
 * @author zzhao
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ComponentScan(basePackages = "top.mingempty.advice")
@EnableConfigurationProperties(MeGloableProperty.class)
public class MeSpringConfiguration implements Ordered {

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public ObjectMapper objectMapper() {
        return JsonUtil.DEFAULT_OBJECT_MAPPER;
    }

    @Bean
    @ConditionalOnMissingBean(name = "springContextUtil")
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }

    @Bean
    @ConditionalOnMissingBean(name = "environmentUtil")
    public EnvironmentUtil environmentUtil() {
        return new EnvironmentUtil();
    }

    @Bean
    @ConditionalOnMissingBean(name = "messageSourceUtil")
    public MessageSourceUtil messageSourceUtil() {
        return new MessageSourceUtil();
    }

    @Bean
    @ConditionalOnMissingBean(name = "beanFactoryUtil")
    public BeanFactoryUtil beanFactoryUtil() {
        return new BeanFactoryUtil();
    }


    /**
     * 基于Flex的跨域过滤器
     *
     * @return 过滤器
     */
    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    @ConditionalOnProperty(prefix = "me", name = "enabled-cors-filter", havingValue = "true")
    public CorsWebFilter corsWebFilter(MeGloableProperty meGloableProperty) {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource
                = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration(meGloableProperty));
        return new CorsWebFilter(urlBasedCorsConfigurationSource);
    }

    /**
     * 基于MVC的跨域过滤器
     *
     * @return 过滤器
     */
    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnProperty(prefix = "me", name = "enabled-cors-filter", havingValue = "true")
    public CorsFilter corsFilter(MeGloableProperty meGloableProperty) {
        org.springframework.web.cors.UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource
                = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration(meGloableProperty));
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }


    private CorsConfiguration corsConfiguration(MeGloableProperty meGloableProperty) {
        CorsConfiguration corsConfiguration = meGloableProperty.getCorsConfiguration();
        if (corsConfiguration != null) {
            return corsConfiguration;
        }
        corsConfiguration = new CorsConfiguration();
        // 允许cookies跨域
        corsConfiguration.setAllowCredentials(true);
        // #允许向该服务器提交请求的URI，*表示全部允许
        corsConfiguration.addAllowedOriginPattern(GlobalConstant.ALL);
        // #允许访问的头信息,*表示全部
        corsConfiguration.addAllowedHeader(GlobalConstant.ALL);
        // 允许提交请求的方法类型，*表示全部允许
        corsConfiguration.addAllowedMethod(GlobalConstant.ALL);
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        corsConfiguration.setMaxAge(GlobalConstant.MAX_AGE);

        return corsConfiguration;
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
