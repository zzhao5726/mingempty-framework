package top.mingempty.concurrent.record.service;

import top.mingempty.concurrent.thread.AbstractDelegatingContext;

/**
 * 线程任务服务接口
 *
 * @author zzhao
 */
public interface TaskService {

    /**
     * 记录提交的任务
     *
     * @param contex
     * @param poolName
     * @param <T>
     */
    <T extends AbstractDelegatingContext<V>, V> void record(T contex, String poolName);


    /**
     * 任务完成修改记录
     *
     * @param taskId
     */
    void complete(String taskId);

    /**
     * 重试提交的任务
     *
     * @param taskId
     */
    void retry(String taskId);

}
