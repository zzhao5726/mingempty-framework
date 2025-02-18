package top.mingempty.mq.demo.model;

import cn.hutool.core.lang.UUID;
import lombok.Data;

@Data
public class Demo {
    private String aaa = UUID.fastUUID().toString(true);
}
