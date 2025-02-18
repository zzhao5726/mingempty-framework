package top.mingempty.event.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import top.mingempty.event.model.MeTxApplicationEvent;
import top.mingempty.event.publisher.MeTxEventPublisher;

/**
 * 基于事务的事件自动配置
 * <br>
 * 请使用 {@link MeTxEventPublisher#publishEvent(MeTxApplicationEvent)} 方法进行发送。
 * 监听请继承 {@link top.mingempty.event.listener.MeTxApplicationListener}这个抽象类，然后将该类注册为spring管理的bean，
 * 不要使用{@link org.springframework.context.event.EventListener}以及不可使用匿名内部类
 *
 * @author zzhao
 */
@ConditionalOnClass(name = {"org.apache.ibatis.annotations.Mapper"})
@ConditionalOnProperty(prefix = "me.event", name = "enabled-tx", havingValue = "true", matchIfMissing = true)
@MapperScan(basePackages = {"top.mingempty.event.tx.mapper"})
@ComponentScan(basePackages = {"top.mingempty.event.tx"})
public class MeTxEventAutoConfiguration {

    @Bean
    public MeTxEventPublisher meTxEventPublisher() {
        return new MeTxEventPublisher();
    }
}