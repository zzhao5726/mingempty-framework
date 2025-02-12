package top.mingempty.openapi.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

/**
 * 系统基础信息增强
 *
 * @author zzhao
 */
@Slf4j
public class RemoveDefaultOpenApiEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private final static String ORIGIN_TRACKED_MAP_PROPERTY_SOURCE_CLASS_NAME = OriginTrackedMapPropertySource.class.getName();

    /**
     * Post-process the given {@code environment}.
     *
     * @param environment the environment to post-process
     * @param application the application to which the environment belongs
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (environment.getProperty("me.openapi.enabled", Boolean.class, Boolean.FALSE)) {
            environment.getPropertySources().stream()
                    .filter(item -> ORIGIN_TRACKED_MAP_PROPERTY_SOURCE_CLASS_NAME.equals(item.getClass().getName()))
                    .map(item -> (OriginTrackedMapPropertySource) item)
                    .map(PropertySource::getSource)
                    .flatMap(map -> map.entrySet().stream())
                    .forEach(entry -> {
                        String newKey = "";
                        if (entry.getKey().startsWith("me.openapi.springdoc")) {
                            newKey = entry.getKey().replace("me.openapi.springdoc", "springdoc");
                        } else if (entry.getKey().startsWith("me.openapi.swagger-ui")) {
                            newKey = entry.getKey().replace("me.openapi.swagger-ui", "springdoc.swagger-ui");
                        }
                        if (!newKey.isEmpty()) {
                            if (environment.containsProperty(newKey)) {
                                environment.getSystemProperties().put(newKey, environment.getProperty(newKey));
                            } else if ((entry.getValue() instanceof OriginTrackedValue originTrackedValue)) {
                                environment.getSystemProperties().put(newKey, originTrackedValue.getValue());
                            }
                        }
                    });
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
        return Ordered.HIGHEST_PRECEDENCE + 13;
    }
}
