package top.mingempty.meta.data.domain.biz.dict.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.commons.exception.DictNoAuthorizationModifiesException;
import top.mingempty.meta.data.commons.exception.MetaDataException;
import top.mingempty.meta.data.domain.MetaDataConstant;
import top.mingempty.meta.data.domain.biz.dict.model.AuthorizationModel;
import top.mingempty.meta.data.domain.biz.dict.query.AuthorizationQuery;
import top.mingempty.meta.data.domain.biz.dict.repository.AuthorizationRepository;
import top.mingempty.meta.data.domain.biz.dict.service.converter.AuthorizationDomainConverter;
import top.mingempty.meta.data.domain.biz.dict.service.info.AuthorizationInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.AuthorizationModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.value.AuthorizationVo;
import top.mingempty.meta.data.domain.rpc.auth.AuthClient;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 字典权限领域服务
 */
@Slf4j
@Service
@AllArgsConstructor
public class AuthorizationDomainService implements BaseDomainService<AuthorizationInfo, AuthorizationQuery> {

    private final AuthorizationRepository authorizationRepository;

    @Resource
    private AuthClient authClient;

    /**
     * 字典权限创建或修改领域服务
     *
     * @param entryCode          条目编码
     * @param entryChangeVertion 当前修改版本
     * @param authorizations     字典权限信息变更改内部传输对象集合
     */
    public void modifies(String entryCode, Long entryChangeVertion,
                         Collection<AuthorizationModifiesInfo> authorizations) {
        if (StrUtil.isEmpty(entryCode)
                || ObjUtil.isEmpty(entryChangeVertion)) {
            throw new MetaDataException("meta-data-domain-0000000002");
        }
        AuthorizationModel authorizationModel = Optional.ofNullable(authorizationRepository.query(entryCode))
                .orElse(new AuthorizationModel(entryCode, List.of()));
        if (CollUtil.isEmpty(authorizations)) {
            return;
        }
        AuthorizationModel authorizationModelByClone = AuthorizationModel.cloneOne(entryCode, authorizationModel);
        authorizationModelByClone.setEntryVersion(entryChangeVertion);
        authorizations
                .parallelStream()
                .forEach(authorizationModifiesInfo -> {
                    switch (authorizationModifiesInfo.getDeleteStatus()) {
                        case ZERO ->
                                authorizationModelByClone.add(authorizationModifiesInfo.getAuthorizationType(), authorizationModifiesInfo.getAuthorizationCode());
                        case ONE ->
                                authorizationModelByClone.delete(authorizationModifiesInfo.getAuthorizationType(), authorizationModifiesInfo.getAuthorizationCode());
                    }
                });
        authorizationRepository.modifies(authorizationModelByClone);
    }

    /**
     * 字典权限全量创建或修改领域服务
     *
     * @param entryCode          条目编码
     * @param entryChangeVertion 当前修改版本
     * @param authorizations     字典权限信息变更改内部传输对象集合
     */
    public void modifiesAll(String entryCode, Long entryChangeVertion,
                            Collection<AuthorizationModifiesInfo> authorizations) {
        if (StrUtil.isEmpty(entryCode)
                || ObjUtil.isEmpty(entryChangeVertion)) {
            throw new MetaDataException("meta-data-domain-0000000003");
        }
        AuthorizationModel authorizationModelByClone = Optional.ofNullable(authorizationRepository.query(entryCode))
                .map(AuthorizationModel::cloneOne)
                .orElse(new AuthorizationModel(entryCode, List.of()));
        authorizationModelByClone.setEntryVersion(entryChangeVertion);
        List<AuthorizationVo> authorizationVos = AuthorizationDomainConverter.modifiesInfoToVos(authorizations);
        authorizationModelByClone.modifies(authorizationVos);
        authorizationRepository.modifies(authorizationModelByClone);
    }


    @Override
    public List<AuthorizationInfo> query(AuthorizationQuery query, final MePage page) {
        List<AuthorizationModel> authorizationModels = authorizationRepository.query(query, page);
        return AuthorizationDomainConverter.modelToInfos(authorizationModels);
    }

    @Override
    public AuthorizationQuery gainQuery(String entryCode) {
        return AuthorizationQuery.builder().entryCode(entryCode).build();
    }

    @Override
    public AuthorizationQuery gainQuery(Collection<String> entryCodes) {
        return AuthorizationQuery.builder().entryCodes(entryCodes).build();
    }


    /**
     * 检查权限
     *
     * @param entryCode
     * @return
     */
    public void checkAuthorization(String entryCode) throws DictNoAuthorizationModifiesException {
        if (!AuthorizationModel.isCheckAuthorization()) {
            //不需要检查权限，如内部的一些导入等
            return;
        }

        // 获取当前用户的角色
        Set<String> roleCodes = authClient.gainRoleCode();
        //判断管理员权限
        if (CollUtil.isNotEmpty(CollUtil.intersection(roleCodes, MetaDataConstant.DEFAUT_AUTHORIZATION_ROLE))) {
            return;
        }
        String userCode = authClient.gainUserCode();
        AuthorizationModel authorizationModel = authorizationRepository.query(entryCode);
        authorizationModel.checkAuthorization(userCode, roleCodes);
    }
}
