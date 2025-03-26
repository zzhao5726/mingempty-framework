package top.mingempty.meta.data.repository.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.ExtraFieldQuery;
import top.mingempty.meta.data.repository.mapper.ChangeExtraFieldMapper;
import top.mingempty.meta.data.repository.model.po.ChangeExtraFieldPo;
import top.mingempty.meta.data.repository.service.ChangeExtraFieldService;

import java.util.List;
import java.util.Optional;

/**
 * 字典扩展字段信息变化流水表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class ChangeExtraFieldServiceImpl extends ServiceImpl<ChangeExtraFieldMapper, ChangeExtraFieldPo>  implements ChangeExtraFieldService{

    @Override
    public List<ChangeExtraFieldPo> query(ExtraFieldQuery extraFieldQuery, MePage mePage) {
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
