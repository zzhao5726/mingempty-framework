package top.mingempty.meta.data.repository.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.meta.data.repository.mapper.OperationHistoryMapper;
import top.mingempty.meta.data.repository.model.po.OperationHistoryPo;
import top.mingempty.meta.data.repository.service.OperationHistoryService;

/**
 * 字典操作历史表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class OperationHistoryServiceImpl extends ServiceImpl<OperationHistoryMapper, OperationHistoryPo>  implements OperationHistoryService{

    @Override
    public Long gainMaxVersion(String entryCode) {
        return this.mapper.gainMaxVersion(entryCode);
    }
}
