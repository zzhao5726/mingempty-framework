package top.mingempty.meta.data.repository.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.ItemQuery;
import top.mingempty.meta.data.repository.mapper.ChangeItemMapper;
import top.mingempty.meta.data.repository.model.po.ChangeItemPo;
import top.mingempty.meta.data.repository.service.ChangeItemService;

import java.util.List;
import java.util.Optional;

/**
 * 字典项变化流水表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class ChangeItemServiceImpl extends ServiceImpl<ChangeItemMapper, ChangeItemPo> implements ChangeItemService {

    @Override
    public List<ChangeItemPo> query(ItemQuery itemQuery, MePage mePage) {
        if (ObjUtil.isNotEmpty(mePage)) {
            if (mePage.isSearchCount()) {
                long total = mapper.queryCount(itemQuery);
                mePage.setTotal(total);
                if (total == 0) {
                    return List.of();
                }
            }
        }
        return mapper.query(itemQuery, Optional.ofNullable(mePage)
                        .map(MePage::getStartIndex)
                        .orElse(null),
                Optional.ofNullable(mePage)
                        .map(MePage::getPageSize)
                        .orElse(null));
    }
}
