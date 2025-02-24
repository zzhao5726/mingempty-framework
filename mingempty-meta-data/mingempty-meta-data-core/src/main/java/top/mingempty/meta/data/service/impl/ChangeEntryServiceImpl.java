package top.mingempty.meta.data.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.mapper.ChangeEntryMapper;
import top.mingempty.meta.data.mapstruct.EntryMapstruct;
import top.mingempty.meta.data.model.bo.EntryBo;
import top.mingempty.meta.data.model.bo.EntryQueryBo;
import top.mingempty.meta.data.model.po.ChangeEntryPo;
import top.mingempty.meta.data.service.ChangeEntryService;

import java.util.List;

/**
 * 字典条目变化流水表 服务层实现。
 *
 * @author zzhao
 * @since 2025-02-10 23:12:12
 */
@Service
public class ChangeEntryServiceImpl extends ServiceImpl<ChangeEntryMapper, ChangeEntryPo> implements ChangeEntryService {

    @Override
    public List<EntryBo> query(EntryQueryBo entryQueryBo, final MePage mePage) {
        if (ObjUtil.isNotEmpty(mePage)) {
            if (mePage.isSearchCount()) {
                //查询数量
                mePage.setTotal(mapper.queryCount(entryQueryBo));
            }
            return EntryMapstruct.INSTANCE.changePoToBo(mapper.queryLimit(entryQueryBo, mePage.getStartIndex(), mePage.getPageSize()));
        }
        return EntryMapstruct.INSTANCE.changePoToBo(mapper.query(entryQueryBo));
    }
}
