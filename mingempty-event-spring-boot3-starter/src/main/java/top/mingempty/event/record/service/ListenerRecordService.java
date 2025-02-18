package top.mingempty.event.record.service;

import top.mingempty.event.record.model.ListenerRecordPo;
import top.mingempty.event.record.model.MeRecordApplicationEvent;

/**
 * 事件监听记录服务接口
 *
 * @author zzhao
 */
public interface ListenerRecordService {
    /**
     * 撤销监听事件
     *
     * @param eventId
     */
    void cancel(String eventId);

    /**
     * 撤销某一个监听事件
     *
     * @param eventListenerId
     */
    void cancelWithId(String eventListenerId);

    /**
     * 撤销某一个监听事件
     *
     * @param eventId
     * @param eventListenerClass
     */
    void cancel(String eventId, String eventListenerClass);

    /**
     * 通过事件id和监听器类名查询监听记录
     *
     * @param eventId
     * @param eventListenerClass
     */
    ListenerRecordPo selectByUnique(String eventId, String eventListenerClass);

    /**
     * 记录监听事件
     *
     * @param applicationEvent
     * @param eventListenerClass
     */
    String record(MeRecordApplicationEvent applicationEvent, String eventListenerClass);

    /**
     * 监听事件处理成功修改状态
     *
     * @param eventListenerId
     */
    void complete(String eventListenerId);

    /**
     * 监听事件处理成功修改状态
     *
     * @param eventId
     * @param eventListenerClass
     */
    void complete(String eventId, String eventListenerClass);

    /**
     * 重试监听事件
     *
     * @param eventListenerId
     */
    void retry(String eventListenerId);
}
