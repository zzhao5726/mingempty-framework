package top.mingempty.event.record.service;

import top.mingempty.event.record.model.MeRecordApplicationEvent;
import top.mingempty.event.record.model.PublisherRecordPo;

/**
 * 事件发布记录服务接口
 *
 * @author zzhao
 */
public interface PublisherRecordService {
    /**
     * 事件发布记录
     *
     * @param applicationEvent
     */
    void record(MeRecordApplicationEvent applicationEvent);

    /**
     * 事件成功发送修改状态
     *
     * @param applicationEvent
     */
    void complete(MeRecordApplicationEvent applicationEvent);

    /**
     * 撤销事件
     *
     * @param eventId
     */
    void cancel(String eventId);

    /**
     * 发送重试
     *
     * @param eventId
     */
    void retry(String eventId);

    /**
     * 基于ID查询
     *
     * @param eventId
     * @return
     */
    PublisherRecordPo selectById(String eventId);
}
