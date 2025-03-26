package top.mingempty.meta.data.domain.biz.dict.service.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.biz.dict.model.AuthorizationModel;
import top.mingempty.meta.data.domain.biz.dict.service.info.AuthorizationInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.AuthorizationModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.value.AuthorizationVo;
import top.mingempty.meta.data.domain.biz.dict.value.DeleteVo;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 条目领域服务层转换器
 *
 * @author zzhao
 */
public class AuthorizationDomainConverter {

    public static AuthorizationInfo voToInfo(AuthorizationVo authorizationVo) {
        if (ObjUtil.isEmpty(authorizationVo)) {
            return null;
        }
        return AuthorizationInfo.builder()
                .authorizationCode(authorizationVo.getAuthorizationCode())
                .authorizationType(authorizationVo.getAuthorizationType())
                .deleteStatus(Optional.ofNullable(authorizationVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteStatus)
                        .orElse(ZeroOrOneEnum.ZERO))
                .deleteOperator(Optional.ofNullable(authorizationVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteOperator)
                        .orElse(null))
                .deleteTime(Optional.ofNullable(authorizationVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteTime)
                        .orElse(null))
                .build();

    }

    /**
     * 将领域模型转换为内部传输对象
     *
     * @param authorizationModel
     * @return
     */
    public static List<AuthorizationInfo> modelToInfos(AuthorizationModel authorizationModel) {
        if (ObjUtil.isEmpty(authorizationModel)
                || CollUtil.isEmpty(authorizationModel.getAuthorizations())) {
            return new CopyOnWriteArrayList<>();
        }
        return authorizationModel.getAuthorizations()
                .parallelStream()
                .map(AuthorizationDomainConverter::voToInfo)
                .filter(Objects::nonNull)
                .peek(authorizationInfo -> authorizationInfo.setEntryCode(authorizationModel.getEntryCode())
                        .setEntryVersion(authorizationModel.getEntryVersion()))
                .collect(Collectors.toList());
    }

    /**
     * 将领域模型集合转换为内部传输对象集合
     *
     * @param authorizationModels
     * @return
     */
    public static List<AuthorizationInfo> modelToInfos(Collection<AuthorizationModel> authorizationModels) {
        if (CollUtil.isEmpty(authorizationModels)) {
            return new CopyOnWriteArrayList<>();
        }

        return authorizationModels
                .parallelStream()
                .map(AuthorizationDomainConverter::modelToInfos)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());

    }

    /**
     * 条目权限修改信息转换
     *
     * @param authorizationModifiesInfo 条目权限修改信息
     * @return
     */
    public static AuthorizationVo modifiesInfoToVo(AuthorizationModifiesInfo authorizationModifiesInfo) {
        if (ObjUtil.isEmpty(authorizationModifiesInfo)) {
            return null;
        }
        return AuthorizationVo.builder()
                .authorizationType(authorizationModifiesInfo.getAuthorizationType())
                .authorizationCode(authorizationModifiesInfo.getAuthorizationCode())
                .deleteStatus(DeleteVo.builder()
                        .deleteStatus(Optional
                                .ofNullable(authorizationModifiesInfo.getDeleteStatus())
                                .orElse(ZeroOrOneEnum.ZERO))
                        .build())
                .build();
    }

    /**
     * 条目权限修改信息集合转换
     *
     * @param authorizationModifiesInfos 条目权限修改信息集合
     * @return
     */
    public static List<AuthorizationVo> modifiesInfoToVos(Collection<AuthorizationModifiesInfo> authorizationModifiesInfos) {
        if (CollUtil.isEmpty(authorizationModifiesInfos)) {
            return new CopyOnWriteArrayList<>();
        }
        return authorizationModifiesInfos.parallelStream()
                .map(AuthorizationDomainConverter::modifiesInfoToVo)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
