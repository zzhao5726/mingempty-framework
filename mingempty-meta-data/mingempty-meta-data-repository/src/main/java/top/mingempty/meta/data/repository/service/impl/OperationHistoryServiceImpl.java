package top.mingempty.meta.data.repository.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.OperationHistoryQuery;
import top.mingempty.meta.data.repository.mapper.OperationHistoryMapper;
import top.mingempty.meta.data.repository.model.po.OperationHistoryPo;
import top.mingempty.meta.data.repository.service.OperationHistoryService;

import java.util.List;
import java.util.Optional;

/**
 * 字典操作历史表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class OperationHistoryServiceImpl extends ServiceImpl<OperationHistoryMapper, OperationHistoryPo> implements OperationHistoryService {

    @Override
    public Long gainMaxVersion(String entryCode) {
        return this.mapper.gainMaxVersion(entryCode);
    }

    @Override
    public List<OperationHistoryPo> query(OperationHistoryQuery operationHistoryQuery, final MePage mePage) {
        if (ObjUtil.isNotEmpty(mePage)) {
            if (mePage.isSearchCount()) {
                long total = mapper.queryCount(operationHistoryQuery);
                mePage.setTotal(total);
            }
        }
        return mapper.query(operationHistoryQuery, Optional.ofNullable(mePage)
                        .map(MePage::getStartIndex)
                        .orElse(null),
                Optional.ofNullable(mePage)
                        .map(MePage::getPageSize)
                        .orElse(null));
    }
}
