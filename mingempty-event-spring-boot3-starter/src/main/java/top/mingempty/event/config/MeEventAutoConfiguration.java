package top.mingempty.event.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.mingempty.event.MeEventPublisher;
import top.mingempty.event.MeEventtListener;

/**
 * 事件自动注入判断
 *
 * @author zzhao
 */
@Configuration
public class MeEventAutoConfiguration {


    @Bean
    public MeEventPublisher meEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new MeEventPublisher(applicationEventPublisher);
    }



    @Bean
    public MeEventtListener meEventtListener() {
        return new MeEventtListener();
    }

}
