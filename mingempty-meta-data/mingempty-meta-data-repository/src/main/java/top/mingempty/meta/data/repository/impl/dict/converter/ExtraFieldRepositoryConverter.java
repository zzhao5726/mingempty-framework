package top.mingempty.meta.data.repository.impl.dict.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import top.mingempty.meta.data.domain.biz.dict.model.ExtraFieldModel;
import top.mingempty.meta.data.domain.biz.dict.value.ExtraFieldVo;
import top.mingempty.meta.data.repository.model.po.ChangeExtraFieldPo;
import top.mingempty.meta.data.repository.model.po.ExtraFieldPo;
import top.mingempty.sequence.enums.SeqRealizeEnum;
import top.mingempty.sequence.factory.SequenceFactory;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 字典条目扩展字段信息仓储层转换器
 *
 * @author zzhao
 */
public class ExtraFieldRepositoryConverter {

    /**
     * po数据库实体转model领域模型
     *
     * @param extraFieldPo po数据库实体
     * @return model领域模型
     */
    public static ExtraFieldVo poToVo(ExtraFieldPo extraFieldPo) {
        if (ObjUtil.isEmpty(extraFieldPo)) {
            return null;
        }
        return ExtraFieldVo.builder()
                .extraFieldCode(extraFieldPo.getExtraFieldCode())
                .extraFieldName(extraFieldPo.getExtraFieldName())
                .typeIsNumber(extraFieldPo.getTypeIsNumber())
                .otherDictFlag(extraFieldPo.getOtherDictFlag())
                .otherEntryCode(extraFieldPo.getOtherEntryCode())
                .extraFieldSort(extraFieldPo.getExtraFieldSort())
                .build();
    }


    /**
     * po数据库实体转model领域模型
     *
     * @param changeExtraFieldPo po数据库实体
     * @return model领域模型
     */
    public static ExtraFieldVo changePoToToVo(ChangeExtraFieldPo changeExtraFieldPo) {
        if (ObjUtil.isEmpty(changeExtraFieldPo)) {
            return null;
        }
        return ExtraFieldVo.builder()
                .extraFieldCode(changeExtraFieldPo.getExtraFieldCode())
                .extraFieldName(changeExtraFieldPo.getExtraFieldName())
                .typeIsNumber(changeExtraFieldPo.getTypeIsNumber())
                .otherDictFlag(changeExtraFieldPo.getOtherDictFlag())
                .otherEntryCode(changeExtraFieldPo.getOtherEntryCode())
                .extraFieldSort(changeExtraFieldPo.getExtraFieldSort())
                .build();
    }


    /**
     * model领域值对象转po数据库实体
     *
     * @param extraFieldVo model领域值对象
     * @return po数据库实体
     */
    public static ExtraFieldPo voToPo(String entryCode, ExtraFieldVo extraFieldVo) {
        if (StrUtil.isEmpty(entryCode)
                || ObjUtil.isEmpty(extraFieldVo)) {
            return null;
        }

        return ExtraFieldPo.builder()
                .extraFieldId(SequenceFactory.seqRealize(SeqRealizeEnum.Zookeeper).snowflakeIdWorker().next())
                .entryCode(entryCode)
                .extraFieldCode(extraFieldVo.getExtraFieldCode())
                .extraFieldName(extraFieldVo.getExtraFieldName())
                .typeIsNumber(extraFieldVo.getTypeIsNumber())
                .otherDictFlag(extraFieldVo.getOtherDictFlag())
                .otherEntryCode(extraFieldVo.getOtherEntryCode())
                .extraFieldSort(extraFieldVo.getExtraFieldSort())
                .build();
    }


    /**
     * model领域值对象转po数据库实体
     *
     * @param extraFieldVo model领域值对象
     * @return po数据库实体
     */
    public static ChangeExtraFieldPo voToChangePoTo(String entryCode, Long entryVersion, ExtraFieldVo extraFieldVo) {
        if (StrUtil.isEmpty(entryCode)
                || ObjUtil.isEmpty(extraFieldVo)) {
            return null;
        }
        return ChangeExtraFieldPo.builder()
                .extraFieldId(SequenceFactory.seqRealize(SeqRealizeEnum.Zookeeper).snowflakeIdWorker().next())
                .entryCode(entryCode)
                .entryVersion(entryVersion)
                .extraFieldCode(extraFieldVo.getExtraFieldCode())
                .extraFieldName(extraFieldVo.getExtraFieldName())
                .typeIsNumber(extraFieldVo.getTypeIsNumber())
                .otherDictFlag(extraFieldVo.getOtherDictFlag())
                .otherEntryCode(extraFieldVo.getOtherEntryCode())
                .extraFieldSort(extraFieldVo.getExtraFieldSort())
                .build();
    }


    /**
     * model领域模型集合转po数据库实体集合
     *
     * @param extraFieldModel model领域模型集合
     * @return po数据库集合
     */
    public static List<ExtraFieldPo> modelToPos(ExtraFieldModel extraFieldModel) {
        if (ObjUtil.isEmpty(extraFieldModel)) {
            return List.of();
        }
        return Optional.ofNullable(extraFieldModel.getExtraFieldsByChange())
                .orElse(List.of())
                .parallelStream()
                .map(extraFieldVo
                        -> voToPo(extraFieldModel.getEntryCode(),
                        extraFieldVo))
                .collect(Collectors.toList());
    }


    /**
     * model领域模型集合转po数据库实体集合
     *
     * @param extraFieldModels model领域模型集合
     * @return po数据库实体集合
     */
    public static List<ExtraFieldPo> modelToPos(Collection<ExtraFieldModel> extraFieldModels) {
        if (CollUtil.isEmpty(extraFieldModels)) {
            return List.of();
        }
        return extraFieldModels
                .parallelStream()
                .map(ExtraFieldRepositoryConverter::modelToPos)
                .filter(Objects::nonNull)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
    }


    /**
     * model领域模型转changePo数据库实体
     *
     * @param extraFieldModel model领域模型
     * @return changePo数据库实体集合
     */
    public static List<ChangeExtraFieldPo> modelToChangePos(ExtraFieldModel extraFieldModel) {
        if (ObjUtil.isEmpty(extraFieldModel)) {
            return null;
        }
        return Optional.ofNullable(extraFieldModel.getExtraFieldsByChange())
                .orElse(List.of())
                .parallelStream()
                .map(extraFieldVo
                        -> voToChangePoTo(extraFieldModel.getEntryCode(),
                        extraFieldModel.getEntryVersion(),
                        extraFieldVo))
                .collect(Collectors.toList());
    }


    /**
     * model领域模型集合转changePo数据库实体集合
     *
     * @param extraFieldModels model领域模型集合
     * @return changePo数据库实体集合
     */
    public static List<ChangeExtraFieldPo> modelToChangePos(Collection<ExtraFieldModel> extraFieldModels) {
        if (CollUtil.isEmpty(extraFieldModels)) {
            return List.of();
        }
        return extraFieldModels
                .parallelStream()
                .map(ExtraFieldRepositoryConverter::modelToChangePos)
                .filter(Objects::nonNull)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
    }

    /**
     * po数据库实体集合转model领域模型集合
     *
     * @param extraFieldPos po数据库实体集合
     * @return model领域模型集合
     */
    public static List<ExtraFieldModel> poToModels(Collection<ExtraFieldPo> extraFieldPos) {
        if (CollUtil.isEmpty(extraFieldPos)) {
            return List.of();
        }
        return extraFieldPos
                .parallelStream()
                .collect(Collectors.groupingBy(ExtraFieldPo::getEntryCode,
                        Collectors.mapping(ExtraFieldRepositoryConverter::poToVo, Collectors.toSet())))
                .entrySet()
                .parallelStream()
                .map(entry -> new ExtraFieldModel(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * changePo数据库实体集合转model领域模型集合
     *
     * @param changeExtraFieldPos changePo数据库实体集合
     * @return model领域模型集合
     */
    public static List<ExtraFieldModel> changePoToModels(Collection<ChangeExtraFieldPo> changeExtraFieldPos) {
        if (CollUtil.isEmpty(changeExtraFieldPos)) {
            return List.of();
        }
        return changeExtraFieldPos
                .parallelStream()
                .collect(Collectors.groupingBy(ChangeExtraFieldPo::getEntryCode,
                        Collectors.mapping(ExtraFieldRepositoryConverter::changePoToToVo, Collectors.toSet())))
                .entrySet()
                .parallelStream()
                .map(entry -> new ExtraFieldModel(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
