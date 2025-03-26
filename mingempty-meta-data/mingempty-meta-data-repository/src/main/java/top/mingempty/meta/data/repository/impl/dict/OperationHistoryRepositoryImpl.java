package top.mingempty.meta.data.repository.impl.dict;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.model.OperationHistoryModel;
import top.mingempty.meta.data.domain.biz.dict.query.OperationHistoryQuery;
import top.mingempty.meta.data.domain.biz.dict.repository.OperationHistoryRepository;
import top.mingempty.meta.data.repository.impl.dict.converter.OperationHistoryConverter;
import top.mingempty.meta.data.repository.model.po.OperationHistoryPo;
import top.mingempty.meta.data.repository.service.OperationHistoryService;

import java.util.List;

/**
 * 字典条目操作历史仓储接口实现
 *
 * @author zzhao
 */
@Slf4j
@Component
@AllArgsConstructor
public class OperationHistoryRepositoryImpl implements OperationHistoryRepository {

    private final OperationHistoryService operationHistoryService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Long gainMaxVersion(String entryCode) {
        return operationHistoryService.gainMaxVersion(entryCode);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void record(OperationHistoryModel operationHistoryModel) {
        List<OperationHistoryPo> operationHistoryPos = OperationHistoryConverter.modelToPos(operationHistoryModel);
        if (CollUtil.isEmpty(operationHistoryPos)) {
            return;
        }
        operationHistoryService.saveBatch(operationHistoryPos);
    }

    @Override
    public List<OperationHistoryModel> query(OperationHistoryQuery operationHistoryQuery, final MePage page) {
        if (ObjUtil.isEmpty(operationHistoryQuery)) {
            return List.of();
        }
        List<OperationHistoryPo> operationHistoryPos = operationHistoryService.query(operationHistoryQuery, page);
        return OperationHistoryConverter.poToModels(operationHistoryPos);
    }
}
