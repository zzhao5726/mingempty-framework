package top.mingempty.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.domain.MeProperties;
import top.mingempty.util.BeanFactoryUtil;
import top.mingempty.util.EnvironmentUtil;
import top.mingempty.util.MessageSourceUtil;
import top.mingempty.util.SpringContextUtil;

/**
 * mingempty cloud 基础工具配置类
 *
 * @author zzhao
 */
@EnableConfigurationProperties(MeProperties.class)
@ComponentScan(basePackages = "top.mingempty.advice")
public class MeSpringConfiguration {

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public static ObjectMapper meObjectMapper() {
        return JsonUtil.DEFAULT_OBJECT_MAPPER;
    }


    @Bean
    @ConditionalOnMissingBean(name = "springContextUtil")
    public static SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }

    @Bean
    @ConditionalOnMissingBean(name = "environmentUtil")
    public static EnvironmentUtil environmentUtil() {
        return new EnvironmentUtil();
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ConditionalOnMissingBean(name = "messageSourceUtil")
    public static MessageSourceUtil messageSourceUtil() {
        return new MessageSourceUtil();
    }

    @Bean
    @ConditionalOnMissingBean(name = "beanFactoryUtil")
    public static BeanFactoryUtil beanFactoryUtil() {
        return new BeanFactoryUtil();
    }


}
