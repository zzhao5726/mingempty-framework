package top.mingempty.stream.record.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mingempty.domain.base.MeReq;
import top.mingempty.domain.base.MeRsp;
import top.mingempty.stream.record.service.MqRecordService;

/**
 * MQ记录控制器
 *
 * @author zzhao
 */
@Tag(name = "MQ记录控制器")
@RequestMapping("/stream")
@RestController
public class StreamController {

    @Autowired
    private MqRecordService mqRecordService;

    /**
     * Mq消息重试
     *
     * @param meReq
     * @return
     */
    @PostMapping("/retry")
    @Operation(summary = "Mq消息重试")
    public MeRsp<String> retry(@RequestBody MeReq<String> meReq) {
        mqRecordService.retry(meReq.getData());
        return MeRsp.success();
    }
}
