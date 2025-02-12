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
import top.mingempty.domain.MeGloableProperty;
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
public class MeSpringConfiguration implements Ordered{

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


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
