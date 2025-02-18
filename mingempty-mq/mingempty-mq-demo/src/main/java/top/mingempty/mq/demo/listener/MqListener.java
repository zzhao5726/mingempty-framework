package top.mingempty.mq.demo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import top.mingempty.mq.demo.model.Demo;
import top.mingempty.stream.listener.MeAbstractListener;

import java.util.function.Consumer;

@Slf4j
@Component
public class MqListener {


    // 消费者1
    @Bean
    public Consumer<Demo> myInput1() {
        return message -> {
            log.info("Consumer1 received: " + message);
        };
    }

    // 消费者2
    @Bean
    public Consumer<Demo> myInput2() {
        return message -> {
            log.info("Consumer2 received: " + message);
        };
    }

    // 消费者3
    @Bean
    public Consumer<Message<Demo>> myInput3() {
        return new MeAbstractListener<Demo>() {
            @Override
            public void onListener(Message<Demo> message, Demo data) {
                log.info("Consumer3 received: " + data);
            }
        };
    }

}
