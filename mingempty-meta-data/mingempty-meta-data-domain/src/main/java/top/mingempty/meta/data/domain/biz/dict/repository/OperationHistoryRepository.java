package top.mingempty.meta.data.domain.biz.dict.repository;

import top.mingempty.meta.data.domain.biz.dict.model.OperationHistoryModel;
import top.mingempty.meta.data.domain.biz.dict.query.OperationHistoryQuery;

import java.util.Collection;

/**
 * 字典条目操作历史仓储接口
 *
 * @author zzhao
 */
public interface OperationHistoryRepository extends BaseRepository<OperationHistoryModel, OperationHistoryQuery> {

    /**
     * 获取条目当前版本
     *
     * @param entryCode 条目编码
     * @return
     */
    Long gainMaxVersion(String entryCode);

    /**
     * 记录条目操作历史
     *
     * @param operationHistoryModel 条目编码
     */
    void record(OperationHistoryModel operationHistoryModel);

    @Override
    default OperationHistoryQuery gainQuery(String entryCode) {
        return OperationHistoryQuery.builder().entryCode(entryCode).build();
    }

    @Override
    default OperationHistoryQuery gainQuery(Collection<String> entryCodes) {
        return OperationHistoryQuery.builder().entryCodes(entryCodes).build();
    }
}
