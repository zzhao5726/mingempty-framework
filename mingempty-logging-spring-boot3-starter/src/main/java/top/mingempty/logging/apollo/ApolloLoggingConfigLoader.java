package top.mingempty.logging.apollo;

import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.enums.ConfigFileFormat;
import lombok.AllArgsConstructor;
import org.springframework.core.env.ConfigurableEnvironment;
import top.mingempty.logging.domain.LogginConstant;
import top.mingempty.logging.loader.LoaderUtil;
import top.mingempty.logging.loader.LogConfigLoader;

/**
 * 基于apollo的logging配置加载器
 *
 * @author zzhao
 */
@AllArgsConstructor
public class ApolloLoggingConfigLoader implements LogConfigLoader {

    private final ConfigurableEnvironment environment;


    @Override
    public void init() {
        String configName = environment.getProperty("me.logging.config-name", LogginConstant.DEFAULT_CONFIG_NAME);
        LoaderUtil.reLoad(ConfigService.getConfigFile(configName, ConfigFileFormat.XML).getContent());
        ConfigService.getConfig(configName)
                .addChangeListener(changeEvent -> {
                    LoaderUtil.reLoad(ConfigService.getConfigFile(configName, ConfigFileFormat.XML).getContent());
                });

    }
}
