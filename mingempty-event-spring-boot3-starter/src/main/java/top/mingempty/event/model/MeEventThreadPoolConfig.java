package top.mingempty.event.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import top.mingempty.concurrent.model.ThreadPoolConfig;

/**
 * 事件配置类
 */
@Data
@ConfigurationProperties(prefix = "me.event")
public class MeEventThreadPoolConfig {
    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * 是否启用事件记录机制
     */
    private boolean enabledRecord;

    /**
     * 线程池配置
     */
    @NestedConfigurationProperty
    private ThreadPoolConfig pool = new ThreadPoolConfig() {{
        this.setName("event");
        this.setThreadName("Event-Delegating-ThreadPool-%d");
    }};


}
