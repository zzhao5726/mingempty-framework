package top.mingempty.concurrent.record.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mingempty.concurrent.record.service.TaskService;
import top.mingempty.domain.base.MeReq;
import top.mingempty.domain.base.MeRsp;

/**
 * 线程池任务控制器
 *
 * @author zzhao
 */
@Tag(name = "线程池任务控制器")
@RequestMapping("/concurrent/task")
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 线程池任务重试
     *
     * @param meReq
     * @return
     */
    @PostMapping("/retry")
    @Operation(summary = "线程池任务重试")
    public MeRsp<String> retry(@RequestBody MeReq<String> meReq) {
        taskService.retry(meReq.getData());
        return MeRsp.success();
    }
}
