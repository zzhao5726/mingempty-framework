package top.mingempty.discovery.nacos.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.discovery.NacosDiscoveryAutoConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import top.mingempty.commons.util.DateTimeUtil;
import top.mingempty.domain.MeGloableProperty;

import java.time.LocalDateTime;
import java.util.Map;


/**
 * nacos自定义配置文件
 */
@Slf4j
@Configuration
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureBefore(NacosDiscoveryAutoConfiguration.class)
public class NacosConfig {

    private final MeGloableProperty meGloableProperty;

    @Bean
    public NacosDiscoveryProperties nacosProperties() {
        NacosDiscoveryProperties nacosDiscoveryProperties = new NacosDiscoveryProperties();
        Map<String, String> metadata = nacosDiscoveryProperties.getMetadata();
        metadata.put("startup.time", DateTimeUtil.formatLocalDateTime(LocalDateTime.now()));
        metadata.put("me.group", meGloableProperty.getGroup());
        metadata.put("me.version", meGloableProperty.getVersion());
        if (meGloableProperty.isUsingBasePath()) {
            metadata.put("me.base-path", meGloableProperty.getBasePath());
        } else {
            metadata.put("me.base-path", "");
        }
        return nacosDiscoveryProperties;
    }


}
