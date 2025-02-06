package top.mingempty.logging.nacos;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.ConfigurableEnvironment;
import top.mingempty.logging.domain.LogginConstant;
import top.mingempty.logging.loader.LoaderUtil;
import top.mingempty.logging.loader.LogConfigLoader;

import java.util.concurrent.Executor;

/**
 * 基于nacos的logging配置加载器
 *
 * @author zzhao
 */
@Slf4j
public class NacosLoggingConfigLoader implements LogConfigLoader {

    private final NacosConfigManager nacosConfigManager;
    private final ConfigurableEnvironment environment;

    public NacosLoggingConfigLoader(Object nacosConfigManager, ConfigurableEnvironment environment) {
        this.nacosConfigManager = (NacosConfigManager) nacosConfigManager;
        this.environment = environment;
    }

    @Override
    public void init() {
        try {
            String configName = environment.getProperty("me.logging.config-name", LogginConstant.DEFAULT_CONFIG_NAME);
            String nacosGroup = environment.getProperty("me.logging.nacos-group", LogginConstant.DEFAULT_NACOS_GROUP);
            String configInfo = nacosConfigManager.getConfigService()
                    .getConfigAndSignListener(configName, nacosGroup, 3000, new Listener() {
                        @Override
                        public Executor getExecutor() {
                            return null;
                        }

                        @Override
                        public void receiveConfigInfo(String configInfo) {
                            LoaderUtil.reLoad(configInfo);
                        }
                    });
            LoaderUtil.reLoad(configInfo);
        } catch (NacosException e) {
            log.error("基于nacos初始化logback配置异常", e);
        }
    }


}
