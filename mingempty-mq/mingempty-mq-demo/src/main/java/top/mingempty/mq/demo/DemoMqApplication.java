package top.mingempty.mq.demo;

import org.springframework.boot.SpringApplication;
import top.mingempty.boot.MeCloudApplication;

/**
 * MqDemo启动类
 *
 * @author zzhao
 * @date 2023/2/27 20:03
 */
@MeCloudApplication
public class DemoMqApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoMqApplication.class, args);
    }
}