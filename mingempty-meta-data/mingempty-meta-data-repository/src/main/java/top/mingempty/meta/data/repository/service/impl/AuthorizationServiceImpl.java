package top.mingempty.meta.data.repository.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.AuthorizationQuery;
import top.mingempty.meta.data.repository.mapper.AuthorizationMapper;
import top.mingempty.meta.data.repository.model.po.AuthorizationPo;
import top.mingempty.meta.data.repository.service.AuthorizationService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 条目授权表 服务层实现。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
@Service
public class AuthorizationServiceImpl extends ServiceImpl<AuthorizationMapper, AuthorizationPo> implements AuthorizationService {

    @Override
    public void transferChange(Collection<String> entryCodes) {
        AuthorizationQuery authorizationQuery = AuthorizationQuery.builder()
                .entryCodes(entryCodes)
                .build();
        mapper.deleteChange(authorizationQuery);
        mapper.transferChange(authorizationQuery);
    }

    @Override
    public List<AuthorizationPo> query(AuthorizationQuery authorizationQuery, MePage mePage) {
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
