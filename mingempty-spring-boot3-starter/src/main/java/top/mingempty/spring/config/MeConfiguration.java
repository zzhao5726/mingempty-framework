package top.mingempty.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import top.mingempty.spring.domain.MeProperties;
import top.mingempty.spring.util.BeanFactoryUtil;
import top.mingempty.spring.util.EnvironmentUtil;
import top.mingempty.spring.util.MessageSourceUtil;
import top.mingempty.spring.util.SpringContextUtil;

/**
 * mingempty cloud 基础工具配置类
 *
 * @author zzhao
 */
@EnableConfigurationProperties(MeProperties.class)
@ComponentScan(basePackages = "top.mingempty.spring.advice")
@ConditionalOnProperty(prefix = "me",name = "enabled", havingValue = "true", matchIfMissing = true)
public class MeConfiguration implements Ordered {

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

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
