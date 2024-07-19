package top.mingempty.jdbc.postprocessor;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import top.mingempty.commons.util.StringUtil;
import top.mingempty.jdbc.domain.enums.SeataDatasourceProxyModeEnum;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * jdbc系统基础信息增强
 *
 * @author zzhao
 */
@Slf4j
public class RemoveDefaultDatasourceEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

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

        List<String> removeKeys = new CopyOnWriteArrayList<>();
        environment.getSystemProperties()
                .entrySet()
                .parallelStream()
                .forEach(entry -> {
                    if (entry.getKey().startsWith("spring.datasource.hikari")) {
                        if (!environment.getSystemProperties().containsKey(entry.getKey())) {
                            environment.getSystemProperties().put(entry.getKey().replace("spring.datasource.hikari",
                                    "me.datasource.hikari"), entry.getValue());
                        }
                        removeKeys.add(entry.getKey());
                    }
                });
        removeKeys.parallelStream().forEach(key -> environment.getSystemProperties().remove(key));

        removeKeys.clear();


        String enabledSeata = StringUtil.null2Str(environment.getProperty("me.datasource.enabled-seata"),
                StringUtil.null2Str(environment.getProperty("seata.enabled"), Boolean.FALSE.toString()));
        environment.getSystemProperties().put("me.datasource.enabled-seata", enabledSeata);
        environment.getSystemProperties().put("seata.enabled", enabledSeata);


        String enabledAutoSeataDataSourceProxy = StringUtil.null2Str(environment.getProperty("me.datasource.enabled-auto-seata-data-source-proxy"),
                StringUtil.null2Str(environment.getProperty("seata.enable-auto-data-source-proxy"), Boolean.TRUE.toString()));
        environment.getSystemProperties().put("me.datasource.enabled-auto-seata-data-source-proxy", enabledAutoSeataDataSourceProxy);
        environment.getSystemProperties().put("seata.enable-auto-data-source-proxy", Boolean.FALSE.toString());

        String dataSourceProxyMode = StringUtil.null2Str(environment.getProperty("me.datasource.seata-data-source-proxy-mode"),
                StringUtil.null2Str(environment.getProperty("seata.data-source-proxy-mode"),
                        SeataDatasourceProxyModeEnum.AT.name()));
        environment.getSystemProperties().put("me.datasource.seata-data-source-proxy-mode", dataSourceProxyMode);
        environment.getSystemProperties().put("seata.data-source-proxy-mode", dataSourceProxyMode);
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
