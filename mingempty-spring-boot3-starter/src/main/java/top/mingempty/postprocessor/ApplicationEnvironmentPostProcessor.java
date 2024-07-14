package top.mingempty.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import top.mingempty.commons.util.StringUtil;

/**
 * 系统基础信息配置增强
 *
 * @author zzhao
 */
@Slf4j
public class ApplicationEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    /**
     * Post-process the given {@code environment}.
     *
     * @param environment the environment to post-process
     * @param application the application to which the environment belongs
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String appName = StringUtil.null2Str(environment.getProperty("me.name"),
                StringUtil.null2Str(environment.getProperty("spring.application.name"),
                        "mingempty"));

        String appGroup = StringUtil.null2Str(environment.getProperty("me.group"),
                StringUtil.null2Str(environment.getProperty("spring.application.group"),
                        "g"));

        String appVersion = StringUtil.null2Str(environment.getProperty("me.version"),
                StringUtil.null2Str(environment.getProperty("spring.application.version"),
                        "v"));


        environment.getSystemProperties().put("me.name", appName);
        environment.getSystemProperties().put("spring.application.name", appName);
        environment.getSystemProperties().put("me.group", appGroup);
        environment.getSystemProperties().put("spring.application.group", appGroup);
        environment.getSystemProperties().put("me.version", appVersion);
        environment.getSystemProperties().put("spring.application.version", appVersion);
        if (Boolean.parseBoolean(StringUtil.null2Str(environment.getProperty("me.using-base-path"), "true"))) {
            String basePath = StringUtil.null2Str(environment.getProperty("me.base-path"),
                    StringUtil.null2Str(environment.getProperty("server.servlet.context-path"),
                            StringUtil.null2Str(environment.getProperty("spring.webflux.base-path"),
                                    "/mingempty")));
            environment.getSystemProperties().put("me.base-path", basePath);
            environment.getSystemProperties().put("server.servlet.context-path", basePath);
            environment.getSystemProperties().put("spring.webflux.base-path", basePath);
            environment.getSystemProperties().put("me.using-base-path", true);
            log.debug("系统基础信息增强。appName:[{}],appGroup:[{}],appVersion:[{}],basePath:[{}]",
                    appName, appGroup, appVersion, basePath);
        } else {
            environment.getSystemProperties().remove("server.servlet.context-path");
            environment.getSystemProperties().remove("spring.webflux.base-path");
            log.debug("系统基础信息增强。appName:[{}],appGroup:[{}],appVersion:[{}]",
                    appName, appGroup, appVersion);
        }


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
        return Ordered.HIGHEST_PRECEDENCE + 12;
    }
}
