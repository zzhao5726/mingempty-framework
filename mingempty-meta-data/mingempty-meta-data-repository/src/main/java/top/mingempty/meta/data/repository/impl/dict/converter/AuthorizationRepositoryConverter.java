package top.mingempty.meta.data.repository.impl.dict.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import top.mingempty.domain.enums.BaseMetaData;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.biz.dict.model.AuthorizationModel;
import top.mingempty.meta.data.domain.biz.dict.value.AuthorizationVo;
import top.mingempty.meta.data.domain.biz.dict.value.DeleteVo;
import top.mingempty.meta.data.repository.model.po.AuthorizationPo;
import top.mingempty.meta.data.repository.model.po.ChangeAuthorizationPo;
import top.mingempty.sequence.enums.SeqRealizeEnum;
import top.mingempty.sequence.factory.SequenceFactory;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 字典条目权限仓储层转换器
 *
 * @author zzhao
 */
public class AuthorizationRepositoryConverter {

    /**
     * po数据库实体转model领域模型
     *
     * @param authorizationPo po数据库实体
     * @return model领域模型
     */
    public static AuthorizationVo poToVo(AuthorizationPo authorizationPo) {
        if (ObjUtil.isEmpty(authorizationPo)) {
            return null;
        }
        return AuthorizationVo.builder()
                .authorizationType(authorizationPo.getAuthorizationType())
                .authorizationCode(authorizationPo.getAuthorizationCode())
                .deleteStatus(DeleteVo.builder()
                        .deleteStatus(ZeroOrOneEnum.ZERO)
                        .build())
                .build();
    }


    /**
     * po数据库实体转model领域模型
     *
     * @param changeAuthorizationPo po数据库实体
     * @return model领域模型
     */
    public static AuthorizationVo changePoToToVo(ChangeAuthorizationPo changeAuthorizationPo) {
        if (ObjUtil.isEmpty(changeAuthorizationPo)) {
            return null;
        }
        DeleteVo deleteVo = DeleteVo.builder()
                .deleteStatus(BaseMetaData.EnumHelper.INSTANCE.findOptional(ZeroOrOneEnum.class, changeAuthorizationPo.getDeleteStatus())
                        .orElse(ZeroOrOneEnum.ZERO))
                .deleteOperator(changeAuthorizationPo.getDeleteOperator())
                .deleteTime(changeAuthorizationPo.getDeleteTime())
                .build();
        return AuthorizationVo.builder()
                .authorizationType(changeAuthorizationPo.getAuthorizationType())
                .authorizationCode(changeAuthorizationPo.getAuthorizationCode())
                .deleteStatus(deleteVo)
                .build();
    }


    /**
     * model领域值对象转po数据库实体
     *
     * @param authorizationVo model领域值对象
     * @return po数据库实体
     */
    public static AuthorizationPo voToPo(String entryCode, AuthorizationVo authorizationVo) {
        if (StrUtil.isEmpty(entryCode)
                || ObjUtil.isEmpty(authorizationVo)) {
            return null;
        }

        return AuthorizationPo.builder()
                .authorizationId(SequenceFactory.seqRealize(SeqRealizeEnum.Zookeeper).snowflakeIdWorker().next())
                .entryCode(entryCode)
                .authorizationType(authorizationVo.getAuthorizationType())
                .authorizationCode(authorizationVo.getAuthorizationCode())
                .build();
    }


    /**
     * model领域值对象转po数据库实体
     *
     * @param authorizationVo model领域值对象
     * @return po数据库实体
     */
    public static ChangeAuthorizationPo voToChangePoTo(String entryCode, Long entryVersion, AuthorizationVo authorizationVo) {
        if (StrUtil.isEmpty(entryCode)
                || ObjUtil.isEmpty(authorizationVo)) {
            return null;
        }
        return ChangeAuthorizationPo.builder()
                .authorizationId(SequenceFactory.seqRealize(SeqRealizeEnum.Zookeeper).snowflakeIdWorker().next())
                .entryCode(entryCode)
                .entryVersion(entryVersion)
                .authorizationType(authorizationVo.getAuthorizationType())
                .authorizationCode(authorizationVo.getAuthorizationCode())
                .deleteStatus(Optional.ofNullable(authorizationVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteStatus)
                        .map(ZeroOrOneEnum::getItemCode)
                        .orElse(ZeroOrOneEnum.ZERO.getItemCode()))
                .deleteOperator(Optional.ofNullable(authorizationVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteOperator)
                        .orElse(null))
                .deleteTime(Optional.ofNullable(authorizationVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteTime)
                        .orElse(null))
                .build();
    }


    /**
     * model领域模型集合转po数据库实体集合
     *
     * @param authorizationModel model领域模型集合
     * @return po数据库集合
     */
    public static List<AuthorizationPo> modelToPos(AuthorizationModel authorizationModel) {
        if (ObjUtil.isEmpty(authorizationModel)) {
            return List.of();
        }
        return Optional.ofNullable(authorizationModel.getAuthorizationsByChange())
                .orElse(List.of())
                .parallelStream()
                .map(authorizationVo
                        -> voToPo(authorizationModel.getEntryCode(),
                        authorizationVo))
                .collect(Collectors.toList());
    }


    /**
     * model领域模型集合转po数据库实体集合
     *
     * @param authorizationModels model领域模型集合
     * @return po数据库实体集合
     */
    public static List<AuthorizationPo> modelToPos(Collection<AuthorizationModel> authorizationModels) {
        if (CollUtil.isEmpty(authorizationModels)) {
            return List.of();
        }
        return authorizationModels
                .parallelStream()
                .map(AuthorizationRepositoryConverter::modelToPos)
                .filter(Objects::nonNull)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
    }


    /**
     * model领域模型转changePo数据库实体
     *
     * @param authorizationModel model领域模型
     * @return changePo数据库实体集合
     */
    public static List<ChangeAuthorizationPo> modelToChangePos(AuthorizationModel authorizationModel) {
        if (ObjUtil.isEmpty(authorizationModel)) {
            return null;
        }
        return Optional.ofNullable(authorizationModel.getAuthorizationsByChange())
                .orElse(List.of())
                .parallelStream()
                .map(authorizationVo
                        -> voToChangePoTo(authorizationModel.getEntryCode(),
                        authorizationModel.getEntryVersion(),
                        authorizationVo))
                .collect(Collectors.toList());
    }


    /**
     * model领域模型集合转changePo数据库实体集合
     *
     * @param authorizationModels model领域模型集合
     * @return changePo数据库实体集合
     */
    public static List<ChangeAuthorizationPo> modelToChangePos(Collection<AuthorizationModel> authorizationModels) {
        if (CollUtil.isEmpty(authorizationModels)) {
            return List.of();
        }
        return authorizationModels
                .parallelStream()
                .map(AuthorizationRepositoryConverter::modelToChangePos)
                .filter(Objects::nonNull)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
    }

    /**
     * po数据库实体集合转model领域模型集合
     *
     * @param authorizationPos po数据库实体集合
     * @return model领域模型集合
     */
    public static List<AuthorizationModel> poToModels(Collection<AuthorizationPo> authorizationPos) {
        if (CollUtil.isEmpty(authorizationPos)) {
            return List.of();
        }
        return authorizationPos
                .parallelStream()
                .collect(Collectors.groupingBy(AuthorizationPo::getEntryCode,
                        Collectors.mapping(AuthorizationRepositoryConverter::poToVo, Collectors.toSet())))
                .entrySet()
                .parallelStream()
                .map(entry -> new AuthorizationModel(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * changePo数据库实体集合转model领域模型集合
     *
     * @param changeAuthorizationPos changePo数据库实体集合
     * @return model领域模型集合
     */
    public static List<AuthorizationModel> changePoToModels(Collection<ChangeAuthorizationPo> changeAuthorizationPos) {
        if (CollUtil.isEmpty(changeAuthorizationPos)) {
            return List.of();
        }
        return changeAuthorizationPos
                .parallelStream()
                .collect(Collectors.groupingBy(ChangeAuthorizationPo::getEntryCode,
                        Collectors.mapping(AuthorizationRepositoryConverter::changePoToToVo, Collectors.toSet())))
                .entrySet()
                .parallelStream()
                .map(entry -> new AuthorizationModel(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
