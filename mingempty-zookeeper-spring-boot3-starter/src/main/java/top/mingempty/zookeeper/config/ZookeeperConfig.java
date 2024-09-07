package top.mingempty.zookeeper.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import top.mingempty.zookeeper.api.ZookeeperApi;
import top.mingempty.zookeeper.api.impl.ZookeeperApiImpl;
import top.mingempty.zookeeper.entity.ZookeeperProperties;
import top.mingempty.zookeeper.entity.wapper.CuratorFrameworkWrapper;
import top.mingempty.zookeeper.factory.MeCuratorFrameworkFactory;

/**
 * zookeeper配置类
 *
 * @author zzhao
 */
@Slf4j
@AllArgsConstructor
@EnableConfigurationProperties(value = {ZookeeperProperties.class})
@ConditionalOnProperty(prefix = "me.zookeeper", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ZookeeperConfig {

    /**
     * zookeeper配置
     */
    private final ZookeeperProperties zookeeperProperties;

    /**
     * zookeeper 客户端工厂
     *
     * @return
     */
    @Bean
    public MeCuratorFrameworkFactory meCuratorFrameworkFactory() {
        return new MeCuratorFrameworkFactory(zookeeperProperties);
    }

    @Bean
    public CuratorFrameworkWrapper CuratorFrameworkWrapper(MeCuratorFrameworkFactory meCuratorFrameworkFactory) {
        return meCuratorFrameworkFactory.build();
    }

    @Bean
    public ZookeeperApi zookeeperApi(CuratorFrameworkWrapper curatorFrameworkWrapper) {
        return new ZookeeperApiImpl(zookeeperProperties, curatorFrameworkWrapper);
    }

}
