package top.mingempty.concurrent.record.service.impl;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.commons.trace.TraceContext;
import top.mingempty.commons.trace.enums.ProtocolEnum;
import top.mingempty.commons.trace.enums.SpanTypeEnum;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.concurrent.exception.ConcurrentException;
import top.mingempty.concurrent.model.enums.TaskStatusEnum;
import top.mingempty.concurrent.record.mapper.TaskMapper;
import top.mingempty.concurrent.record.model.TaskPo;
import top.mingempty.concurrent.record.service.TaskService;
import top.mingempty.concurrent.record.util.TaskUtil;
import top.mingempty.concurrent.thread.AbstractDelegatingContext;
import top.mingempty.trace.util.TraceAdapterUtil;

import java.time.LocalDateTime;

/**
 * 线程任务服务实现类
 *
 * @author zzhao
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T extends AbstractDelegatingContext<V>, V> void record(T contex, String poolName) {
        TaskPo taskPo = taskMapper.selectByPrimaryKey(contex.getRecordId());
        if (taskPo != null) {
            return;
        }
        taskPo = new TaskPo();
        taskPo.setTaskId(contex.getRecordId());
        taskPo.setPoolName(poolName);
        taskPo.setTaskClass(contex.getClass().getName());
        taskPo.setTaskStatus(TaskStatusEnum.INITIALIZED.getCode());
        taskPo.setThreadData(JsonUtil.toStr(contex));
        taskPo.setRecordTime(LocalDateTime.now());
        TraceContext traceContext = TraceAdapterUtil.gainTraceContext();
        if (traceContext != null) {
            taskPo.setTraceId(traceContext.getTraceId());
            taskPo.setSpanId(traceContext.getSpanId());
        }
        taskPo.setCreateTime(LocalDateTime.now());
        taskPo.setUpdateTime(LocalDateTime.now());
        taskMapper.insert(taskPo);
    }

    @Override
    public void complete(String taskId) {
        TaskPo taskPo = new TaskPo();
        taskPo.setTaskId(taskId);
        taskPo.setTaskStatus(TaskStatusEnum.PROCESSED.getCode());
        taskPo.setCompletionTime(LocalDateTime.now());
        taskPo.setUpdateTime(LocalDateTime.now());
        taskMapper.updateByPrimaryKeySelective(taskPo);
    }

    @SneakyThrows
    @Override
    public void retry(String taskId) {
        TaskPo taskPo = taskMapper.selectByPrimaryKey(taskId);
        if (taskPo == null) {
            throw new ConcurrentException("concurrent-0000000001");
        }

        if (TaskStatusEnum.PROCESSED.getCode().equals(taskPo.getTaskStatus())) {
            throw new ConcurrentException("concurrent-0000000002");
        }
        if (StrUtil.isNotEmpty(taskPo.getTraceId())
                && StrUtil.isNotEmpty(taskPo.getSpanId())) {
            TraceContext traceContext = new TraceContext("concurrent-retry",
                    taskPo.getTraceId(), taskPo.getSpanId(),
                    ProtocolEnum.OTHER, SpanTypeEnum.THREAD_ASYNC);
            traceContext.start();
        }
        Object object = JsonUtil.toObj(taskPo.getThreadData(), Class.forName(taskPo.getTaskClass()));
        if (object instanceof AbstractDelegatingContext abstractDelegatingContext) {
            abstractDelegatingContext.setRecordId(taskPo.getTaskId());
        }

        TaskUtil.submit(object, taskPo.getPoolName());
    }
}
