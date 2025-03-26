package top.mingempty.meta.data.repository.service;

import com.mybatisflex.core.service.IService;
import top.mingempty.meta.data.repository.model.po.OperationHistoryPo;

/**
 * 字典操作历史表 服务层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
public interface OperationHistoryService extends IService<OperationHistoryPo> {

    /**
     * 获取最大版本号
     *
     * @param entryCode 条目编码
     * @return 最大版本号
     */
    Long gainMaxVersion(String entryCode);
}
