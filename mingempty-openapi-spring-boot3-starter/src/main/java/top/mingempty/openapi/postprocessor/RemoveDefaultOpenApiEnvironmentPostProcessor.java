package top.mingempty.openapi.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 系统基础信息增强
 *
 * @author zzhao
 */
@Slf4j
public class RemoveDefaultOpenApiEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    /**
     * Post-process the given {@code environment}.
     *
     * @param environment the environment to post-process
     * @param application the application to which the environment belongs
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        List<String> removeKeys = new CopyOnWriteArrayList<>();
        environment.getSystemEnvironment()
                .entrySet()
                .parallelStream()
                .forEach(entry -> {
                    if (entry.getKey().startsWith("springdoc")) {
                        if (!environment.getSystemProperties().containsKey(entry.getKey())) {
                            environment.getSystemProperties().put(entry.getKey().replace("springdoc", "me.openapi.spring"), entry.getValue());
                        }
                        removeKeys.add(entry.getKey());
                    }
                });
        removeKeys.parallelStream().forEach(key -> environment.getSystemEnvironment().remove(key));

        removeKeys.clear();

        environment.getSystemProperties()
                .entrySet()
                .parallelStream()
                .forEach(entry -> {
                    if (entry.getKey().startsWith("springdoc")) {
                        if (!environment.getSystemProperties().containsKey(entry.getKey())) {
                            environment.getSystemProperties().put(entry.getKey().replace("springdoc", "me.openapi.spring"), entry.getValue());
                        }
                        removeKeys.add(entry.getKey());
                    }
                });
        removeKeys.parallelStream().forEach(key -> environment.getSystemEnvironment().remove(key));
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
