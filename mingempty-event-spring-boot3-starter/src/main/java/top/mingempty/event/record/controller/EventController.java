package top.mingempty.event.record.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mingempty.domain.base.MeReq;
import top.mingempty.domain.base.MeRsp;
import top.mingempty.event.record.service.ListenerRecordService;
import top.mingempty.event.record.service.PublisherRecordService;

/**
 * 事件控制器
 */
@RestController
@Tag(name = "事件控制器")
@AllArgsConstructor
@RequestMapping("/event/retry")
public class EventController {

    private final PublisherRecordService publisherRecordService;
    private final ListenerRecordService listenerRecordService;

    /**
     * 发送重试
     *
     * @param meReq
     * @return
     */
    @PostMapping("/publisher")
    @Operation(summary = "发送重试")
    public MeRsp<Void> retryPublisher(@RequestBody MeReq<String> meReq) {
        publisherRecordService.retry(meReq.getData());
        return MeRsp.success();
    }

    /**
     * 监听重试
     *
     * @param meReq
     * @return
     */
    @PostMapping("/listener")
    @Operation(summary = "监听重试")
    public MeRsp<Void> retryListener(@RequestBody MeReq<String> meReq) {
        listenerRecordService.retry(meReq.getData());
        return MeRsp.success();
    }

}
