package top.mingempty.mq.demo.product;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mingempty.mq.demo.model.Demo;

@Slf4j
@AllArgsConstructor
@RestController
public class DemoController {
    private final StreamBridge streamBridge;

    @GetMapping("/aaa")
    public void aaa() {
        log.info("发送前1");
        streamBridge.send("myOutput-out-0", new Demo());
        log.info("发送前2");
        streamBridge.send("myOutput2-out-0", new Demo());

    }
}
