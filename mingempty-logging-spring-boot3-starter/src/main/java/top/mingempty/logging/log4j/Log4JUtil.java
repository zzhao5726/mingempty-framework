package top.mingempty.logging.log4j;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Log4JUtil
 *
 * @author zzhao
 */
@Slf4j
public class Log4JUtil {

    private static final AtomicReference<String> LOG_CONFIG = new AtomicReference<>();

    /**
     * 初始化LogBack
     *
     * @param configInfo
     */
    public static void init(String configInfo) {
        if (StrUtil.isEmpty(configInfo)) {
            return;
        }
        String oldLogConfig = LOG_CONFIG.get();
        if (StrUtil.equals(oldLogConfig, configInfo)) {
            return;
        }
        synchronized (LOG_CONFIG) {
            oldLogConfig = LOG_CONFIG.get();
            if (StrUtil.equals(oldLogConfig, configInfo)) {
                return;
            }
            LOG_CONFIG.set(configInfo);
            try (InputStream inputStream = new ByteArrayInputStream(configInfo.getBytes(StandardCharsets.UTF_8))) {
                ConfigurationSource source = new ConfigurationSource(inputStream);
                LoggerContext loggerContext = Configurator.initialize(Log4JUtil.class.getClassLoader(), source);
                loggerContext.reconfigure();
                log.info("Log4j configuration reloaded successfully.");
            } catch (IOException e) {
                log.error("Failed to reload Log4j configuration.", e);
            }

        }
    }
}
