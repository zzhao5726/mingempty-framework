package top.mingempty.meta.data.repository.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.ExtraFieldQuery;
import top.mingempty.meta.data.repository.mapper.ExtraFieldMapper;
import top.mingempty.meta.data.repository.model.po.ExtraFieldPo;
import top.mingempty.meta.data.repository.service.ExtraFieldService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 字典扩展字段信息表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class ExtraFieldServiceImpl extends ServiceImpl<ExtraFieldMapper, ExtraFieldPo> implements ExtraFieldService {

    @Override
    public void transferChange(Collection<String> entryCodes) {
        ExtraFieldQuery extraFieldQuery = ExtraFieldQuery.builder()
                .entryCodes(entryCodes)
                .build();
        mapper.transferChange(extraFieldQuery);
    }

    @Override
    public List<ExtraFieldPo> query(ExtraFieldQuery extraFieldQuery, MePage mePage) {
        if (ObjUtil.isNotEmpty(mePage)) {
            if (mePage.isSearchCount()) {
                long total = mapper.queryCount(extraFieldQuery);
                mePage.setTotal(total);
                if (total == 0) {
                    return List.of();
                }
            }
        }
        return mapper.query(extraFieldQuery, Optional.ofNullable(mePage)
                        .map(MePage::getStartIndex)
                        .orElse(null),
                Optional.ofNullable(mePage)
                        .map(MePage::getPageSize)
                        .orElse(null));
    }
}
