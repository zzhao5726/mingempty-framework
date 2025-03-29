package top.mingempty.dubbo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import top.mingempty.dubbo.model.DubboProperties;

@EnableConfigurationProperties(DubboProperties.class)
@ConditionalOnProperty(prefix = "me.dubbo", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DubboAutoConfiguration {
}
