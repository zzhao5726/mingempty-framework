package top.mingempty.meta.data.repository.impl.dict;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.model.AuthorizationModel;
import top.mingempty.meta.data.domain.biz.dict.query.AuthorizationQuery;
import top.mingempty.meta.data.domain.biz.dict.repository.AuthorizationRepository;
import top.mingempty.meta.data.repository.impl.dict.converter.AuthorizationRepositoryConverter;
import top.mingempty.meta.data.repository.model.po.AuthorizationPo;
import top.mingempty.meta.data.repository.model.po.ChangeAuthorizationPo;
import top.mingempty.meta.data.repository.service.AuthorizationService;
import top.mingempty.meta.data.repository.service.ChangeAuthorizationService;

import java.util.List;
import java.util.Optional;

/**
 * 字典条目权限仓储接口实现
 *
 * @author zzhao
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthorizationRepositoryImpl implements AuthorizationRepository {

    private final AuthorizationService authorizationService;

    private final ChangeAuthorizationService changeAuthorizationService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void modifies(AuthorizationModel authorization) {
        if (ObjUtil.isEmpty(authorization)
                || StrUtil.isEmpty(authorization.getEntryCode())
                || CollUtil.isEmpty(authorization.getAuthorizationsByChange())) {
            return;
        }
        List<ChangeAuthorizationPo> changeAuthorizationPos = AuthorizationRepositoryConverter.modelToChangePos(authorization);
        changeAuthorizationService.saveBatch(changeAuthorizationPos);
        authorizationService.transferChange(authorization.getEntryCode());


    }


    @Override
    public List<AuthorizationModel> query(AuthorizationQuery authorizationQuery, final MePage mePage) {
        if (ObjUtil.isEmpty(authorizationQuery)) {
            return List.of();
        }
        return Optional.ofNullable(authorizationQuery.getEntryVersion())
                .map(query -> changeAuthorizationService.query(authorizationQuery, mePage))
                .map(AuthorizationRepositoryConverter::changePoToModels)
                .orElseGet(() -> {
                    List<AuthorizationPo> authorizationPos = authorizationService.query(authorizationQuery, mePage);
                    return AuthorizationRepositoryConverter.poToModels(authorizationPos);
                });
    }
}
