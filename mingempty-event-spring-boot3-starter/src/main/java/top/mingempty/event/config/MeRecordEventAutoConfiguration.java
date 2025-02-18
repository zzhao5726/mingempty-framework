package top.mingempty.event.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import top.mingempty.event.record.listener.MeRecordApplicationListener;
import top.mingempty.event.record.model.MeRecordApplicationEvent;
import top.mingempty.event.record.publisher.MeRecordEventPublisher;

/**
 * 事件记录机制自动配置类
 * <br>
 * 请使用 {@link MeRecordEventPublisher#publishEvent(MeRecordApplicationEvent)} 方法进行发送。
 * 监听请继承 {@link MeRecordApplicationListener}这个抽象类，然后将该类注册为spring管理的bean，
 * 不要使用{@link org.springframework.context.event.EventListener}以及不可使用匿名内部类
 *
 * @author zzhao
 */
@ConditionalOnClass(name = {"org.apache.ibatis.annotations.Mapper"})
@ConditionalOnProperty(prefix = "me.event", name = "enabled-record", havingValue = "true", matchIfMissing = true)
@MapperScan(basePackages = {"top.mingempty.event.record.mapper"})
@ComponentScan(basePackages = {"top.mingempty.event.record"})
public class MeRecordEventAutoConfiguration {

    @Bean
    public MeRecordEventPublisher meRecordEventPublisher() {
        return new MeRecordEventPublisher();
    }
}