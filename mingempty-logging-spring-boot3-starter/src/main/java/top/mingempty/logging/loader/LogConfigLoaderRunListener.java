package top.mingempty.logging.loader;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import top.mingempty.logging.apollo.ApolloLoggingConfigLoader;
import top.mingempty.logging.domain.LoggingProperties;
import top.mingempty.logging.nacos.NacosLoggingConfigLoader;


/**
 * 配置初始化
 *
 * @author zzhao
 */
@Slf4j
@Order(value = 2)
@AllArgsConstructor
@EnableConfigurationProperties(LoggingProperties.class)
public class LogConfigLoaderRunListener implements SpringApplicationRunListener {


    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        try {
            Class.forName("com.alibaba.nacos.api.config.ConfigService");
            Class<?> nacosConfigManagerClass = Class.forName("com.alibaba.cloud.nacos.NacosConfigManager");
            Object object = bootstrapContext.get(nacosConfigManagerClass);
            LogConfigLoader logConfigLoader = new NacosLoggingConfigLoader(object, environment);
            logConfigLoader.init();
        } catch (ClassNotFoundException e) {
            log.warn("com.alibaba.nacos.api.config.ConfigService not presence");
        }

        try {
            Class.forName("com.ctrip.framework.apollo.ConfigService");
            LogConfigLoader logConfigLoader = new ApolloLoggingConfigLoader(environment);
            logConfigLoader.init();
        } catch (ClassNotFoundException e) {
            log.warn("com.ctrip.framework.apollo.ConfigService not presence");
        }

        SpringApplicationRunListener.super.environmentPrepared(bootstrapContext, environment);
    }
}
