package top.mingempty.meta.data.repository.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.EntryQuery;
import top.mingempty.meta.data.repository.mapper.EntryMapper;
import top.mingempty.meta.data.repository.model.po.EntryPo;
import top.mingempty.meta.data.repository.service.EntryService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 字典条目表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class EntryServiceImpl extends ServiceImpl<EntryMapper, EntryPo> implements EntryService {

    @Override
    public void transferChange(Collection<String> entryCodes) {
        EntryQuery entryQuery = EntryQuery.builder()
                .entryCodes(entryCodes)
                .build();
        mapper.transferChange(entryQuery);
    }

    @Override
    public List<EntryPo> query(EntryQuery entryQuery, final MePage mePage) {
        if (ObjUtil.isNotEmpty(mePage)) {
            if (mePage.isSearchCount()) {
                long total = mapper.queryCount(entryQuery);
                mePage.setTotal(total);
                if (total == 0) {
                    return List.of();
                }
            }
        }
        return mapper.query(entryQuery, Optional.ofNullable(mePage)
                        .map(MePage::getStartIndex)
                        .orElse(null),
                Optional.ofNullable(mePage)
                        .map(MePage::getPageSize)
                        .orElse(null));
    }
}
