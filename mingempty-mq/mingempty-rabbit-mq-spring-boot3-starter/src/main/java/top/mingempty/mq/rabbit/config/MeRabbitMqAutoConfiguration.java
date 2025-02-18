package top.mingempty.mq.rabbit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;

/**
 * 对rabbitMQ的配置
 *
 * @author zzhao
 */
@Slf4j
@ComponentScan(basePackages = {"top.mingempty.mq.rabbit"})
public class MeRabbitMqAutoConfiguration {

}
