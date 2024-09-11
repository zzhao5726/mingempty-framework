package top.mingempty.sequence.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import top.mingempty.sequence.factory.DatabaseInitFactory;
import top.mingempty.sequence.factory.InitFactory;
import top.mingempty.sequence.factory.RedisInitFactory;
import top.mingempty.sequence.factory.SequenceInitFactory;
import top.mingempty.sequence.factory.ZookeeperInitFactory;
import top.mingempty.sequence.model.SequenceProperties;

import java.util.List;

/**
 * 序号生成器自动装配
 *
 * @author zzhao
 */
@AllArgsConstructor
@EnableConfigurationProperties({SequenceProperties.class})
@ConditionalOnProperty(prefix = "me.sequence", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SequenceConfig {

    private final SequenceProperties sequenceProperties;

    @Bean
    @ConditionalOnBean(name = {"dataSource"})
    @ConditionalOnClass(name = {"top.mingempty.jdbc.domain.MeDatasourceWrapper"})
    public InitFactory databaseInitFactory() {
        return new DatabaseInitFactory();
    }

    @Bean
    @ConditionalOnBean(name = {"redisTemplateWrapper"})
    @ConditionalOnClass(name = {"top.mingempty.cache.redis.entity.wapper.RedisTemplateWrapper"})
    public InitFactory redisInitFactory() {
        return new RedisInitFactory();
    }

    @Bean
    @ConditionalOnBean(name = {"curatorFrameworkWrapper"})
    @ConditionalOnClass(name = {"top.mingempty.zookeeper.entity.wapper.CuratorFrameworkWrapper"})
    public InitFactory zookeeperInitFactory() {
        return new ZookeeperInitFactory();
    }


    @Bean
    public SequenceInitFactory sequenceInitFactory(List<InitFactory> initFactories) {
        return new SequenceInitFactory(sequenceProperties, initFactories);
    }
}
