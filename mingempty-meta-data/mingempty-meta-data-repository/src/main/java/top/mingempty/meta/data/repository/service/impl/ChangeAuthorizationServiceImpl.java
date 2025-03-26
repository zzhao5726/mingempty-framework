package top.mingempty.meta.data.repository.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.AuthorizationQuery;
import top.mingempty.meta.data.repository.mapper.ChangeAuthorizationMapper;
import top.mingempty.meta.data.repository.model.po.ChangeAuthorizationPo;
import top.mingempty.meta.data.repository.service.ChangeAuthorizationService;

import java.util.List;
import java.util.Optional;

/**
 * 条目授权变化流水表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class ChangeAuthorizationServiceImpl extends ServiceImpl<ChangeAuthorizationMapper, ChangeAuthorizationPo>  implements ChangeAuthorizationService{

    @Override
    public List<ChangeAuthorizationPo> query(AuthorizationQuery authorizationQuery, MePage mePage) {
        if (ObjUtil.isNotEmpty(mePage)) {
            if (mePage.isSearchCount()) {
                long total = mapper.queryCount(authorizationQuery);
                mePage.setTotal(total);
                if (total == 0) {
                    return List.of();
                }
            }
        }
        return mapper.query(authorizationQuery, Optional.ofNullable(mePage)
                        .map(MePage::getStartIndex)
                        .orElse(null),
                Optional.ofNullable(mePage)
                        .map(MePage::getPageSize)
                        .orElse(null));
    }
}
