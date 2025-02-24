package top.mingempty.meta.data.service;

import com.mybatisflex.core.service.IService;
import top.mingempty.meta.data.model.enums.DictOperationEnum;
import top.mingempty.meta.data.model.po.OperationHistoryPo;

import java.util.Map;

/**
 * 字典操作历史表 服务层。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
public interface OperationHistoryService extends IService<OperationHistoryPo> {


    /**
     * 获取最新版本
     *
     * @param entryCode 条目编码
     * @return
     */
    Long gainVersion(String entryCode);


    /**
     * 记录操作历史
     *
     * @param entryVersionMap   条目版本信息
     * @param dictOperationType
     */
    void record(Map<String, Long> entryVersionMap, DictOperationEnum dictOperationType);
}
