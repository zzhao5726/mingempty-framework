package top.mingempty.logging.logback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * LogBackUtil
 *
 * @author zzhao
 */
@Slf4j
public class LogBackUtil {

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
                LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
                JoranConfigurator configurator = new JoranConfigurator();
                configurator.setContext(loggerContext);
                loggerContext.reset();
                configurator.doConfigure(inputStream);
                log.info("Logback configuration loaded successfully.");
            } catch (JoranException | IOException e) {
                log.error("Failed to load Logback configuration.", e);
            }

        }
    }

}
