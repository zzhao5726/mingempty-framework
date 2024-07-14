package top.mingempty.trace.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import top.mingempty.trace.adapter.DefaultTraceRecordPushAdapter;
import top.mingempty.trace.adapter.TraceRecordPushAdapter;
import top.mingempty.trace.domain.MeTraceProperties;
import top.mingempty.trace.domain.TracePushBlockingQueue;
import top.mingempty.trace.filter.TraceFluxFilter;
import top.mingempty.trace.filter.TraceMvcFilter;

/**
 * mingempty 链路工具配置类
 *
 * @author zzhao
 */
@EnableConfigurationProperties(MeTraceProperties.class)
public class TraceConfiguration {


    @Bean
    @Primary
    @ConditionalOnMissingBean(value = {TraceRecordPushAdapter.class})
    @ConditionalOnProperty(prefix = "me.trace", name = "enabled", havingValue = "false", matchIfMissing = true)
    public TraceRecordPushAdapter defaultTraceRecordPushAdapter() {
        return new DefaultTraceRecordPushAdapter();
    }

    @Bean
    public TracePushBlockingQueue tracePushBlockingQueue(TraceRecordPushAdapter traceRecordPushAdapter,
                                                         @Value("${me.name}") String appName,
                                                         @Value("${me.group}") String appGroup,
                                                         @Value("${me.version}") String appVersion) {
        return new TracePushBlockingQueue(traceRecordPushAdapter, appName, appGroup, appVersion);
    }


    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public TraceMvcFilter meTraceFilter() {
        return new TraceMvcFilter();
    }


    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    public TraceFluxFilter meTraceFluxFilter() {
        return new TraceFluxFilter();
    }

}
