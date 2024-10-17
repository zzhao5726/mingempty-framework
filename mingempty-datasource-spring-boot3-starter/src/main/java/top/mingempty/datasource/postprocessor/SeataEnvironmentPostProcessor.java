package top.mingempty.datasource.postprocessor;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import top.mingempty.commons.util.StringUtil;
import top.mingempty.datasource.enums.SeataMode;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * seata配置特殊处理
 *
 * @author zzhao
 */
@Slf4j
public class SeataEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private final static String ORIGIN_TRACKED_MAP_PROPERTY_SOURCE_CLASS_NAME = OriginTrackedMapPropertySource.class.getName();

    /**
     * Post-process the given {@code environment}.
     *
     * @param environment the environment to post-process
     * @param application the application to which the environment belongs
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (StrUtil.isNotEmpty(environment.getProperty("spring.datasource.url"))) {
            //说明有通过spring.datasource.url配置数据源，给提示无效
            log.warn("spring boot默认数据源配置已禁用，请勿配置");
        }


        String enabledSeata = StringUtil.null2Str(environment.getProperty("me.datasource.seata-enabled"),
                StringUtil.null2Str(environment.getProperty("me.datasource.seataEnabled"),
                        StringUtil.null2Str(environment.getProperty("seata.enabled"), Boolean.FALSE.toString())));
        environment.getSystemProperties().put("me.datasource.seata-enabled", enabledSeata);
        environment.getSystemProperties().put("seata.enabled", enabledSeata);
        if (Boolean.FALSE.toString().equals(enabledSeata)) {
            return;
        }


        String enabledAutoSeataDataSourceProxy = StringUtil.null2Str(environment.getProperty("me.datasource.auto-seata-data-source-proxy-enabled"),
                StringUtil.null2Str(environment.getProperty("me.datasource.autoSeataDataSourceProxyEnabled"),
                        StringUtil.null2Str(environment.getProperty("seata.enable-auto-data-source-proxy"),
                                StringUtil.null2Str(environment.getProperty("seata.enableAutoDataSourceProxy"),
                                        Boolean.FALSE.toString()))));

        environment.getSystemProperties().put("me.datasource.auto-seata-data-source-proxy-enabled", enabledAutoSeataDataSourceProxy);
        environment.getSystemProperties().put("seata.enable-auto-data-source-proxy", enabledAutoSeataDataSourceProxy);
        if (Boolean.FALSE.toString().equals(enabledAutoSeataDataSourceProxy)) {
            return;
        }
        //说明启用了全局代理

        //配置全局代理模式
        String dataSourceProxyMode = StringUtil.null2Str(environment.getProperty("me.datasource.seata-data-source-proxy-mode"),
                StringUtil.null2Str(environment.getProperty("me.datasource.seataDataSourceProxyMode"),
                        StringUtil.null2Str(environment.getProperty("seata.data-source-proxy-mode"),
                                StringUtil.null2Str(environment.getProperty("seata.dataSourceProxyMode"),
                                        SeataMode.AT.name()))));
        environment.getSystemProperties().put("me.datasource.seata-data-source-proxy-mode", dataSourceProxyMode);
        environment.getSystemProperties().put("seata.data-source-proxy-mode", dataSourceProxyMode);


        //当前配置内数据源 Bean 不符合自动代理条件的数量
        AtomicInteger excludesForAutoProxyingSize = new AtomicInteger(0);
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        environment.getPropertySources().stream()
                .filter(item -> ORIGIN_TRACKED_MAP_PROPERTY_SOURCE_CLASS_NAME.equals(item.getClass().getName()))
                .map(item -> (OriginTrackedMapPropertySource) item)
                .map(PropertySource::getSource)
                .flatMap(map -> map.entrySet().stream())
                .forEach(entry -> {
                    if (entry.getKey().startsWith("seata.excludes-for-auto-proxying")
                            || entry.getKey().startsWith("seata.excludesForAutoProxying")) {
                        excludesForAutoProxyingSize.incrementAndGet();
                        if ((entry.getValue() instanceof OriginTrackedValue originTrackedValue
                                && originTrackedValue.getValue() instanceof String str)) {
                            if ("top.mingempty.datasource.model.DynamicDatasource".equals(str)) {
                                atomicBoolean.set(false);
                            }
                        }
                    }
                });

        if (atomicBoolean.get()) {
            environment.getSystemProperties().put("seata.excludes-for-auto-proxying[" + excludesForAutoProxyingSize.get() + "]",
                    "top.mingempty.datasource.model.DynamicDatasource");
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
