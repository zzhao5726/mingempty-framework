package top.mingempty.cloud.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import top.mingempty.cloud.util.BeanFactoryUtil;
import top.mingempty.cloud.util.EnvironmentUtil;
import top.mingempty.cloud.util.MessageSourceUtil;
import top.mingempty.cloud.util.SpringContextUtil;

/**
 * mingempty cloud 基础工具配置类
 *
 * @author zzhao
 */
@Configuration
public class MeCloudConfiguration {

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
