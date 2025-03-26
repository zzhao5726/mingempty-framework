package top.mingempty.meta.data.repository.impl.dict.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import top.mingempty.domain.enums.BaseMetaData;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.biz.dict.model.EntryModel;
import top.mingempty.meta.data.domain.biz.dict.value.DeleteVo;
import top.mingempty.meta.data.repository.model.po.ChangeEntryPo;
import top.mingempty.meta.data.repository.model.po.EntryPo;
import top.mingempty.sequence.enums.SeqRealizeEnum;
import top.mingempty.sequence.factory.SequenceFactory;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 条目仓储层转换器
 *
 * @author zzhao
 */
public class EntryRepositoryConverter {


    /**
     * model领域模型集合转po数据库实体集合
     *
     * @param entryModel model领域模型集合
     * @return po数据库集合
     */
    public static EntryPo modelToPo(EntryModel entryModel) {
        if (ObjUtil.isEmpty(entryModel)) {
            return null;
        }
        return EntryPo.builder()
                .entryId(SequenceFactory.seqRealize(SeqRealizeEnum.Zookeeper).snowflakeIdWorker().next())
                .entryCode(entryModel.getEntryCode())
                .entryName(entryModel.getEntryName())
                .entryType(entryModel.getEntryType())
                .entrySharding(entryModel.getEntrySharding())
                .sort(entryModel.getSort())
                .deleteStatus(Optional.ofNullable(entryModel.getDeleteStatus())
                        .map(DeleteVo::getDeleteStatus)
                        .map(ZeroOrOneEnum::getItemCode)
                        .orElse(ZeroOrOneEnum.ZERO.getItemCode()))
                .deleteOperator(Optional.ofNullable(entryModel.getDeleteStatus())
                        .map(DeleteVo::getDeleteOperator)
                        .orElse(null))
                .deleteTime(Optional.ofNullable(entryModel.getDeleteStatus())
                        .map(DeleteVo::getDeleteTime)
                        .orElse(null))
                .build();
    }


    /**
     * model领域模型集合转po数据库实体集合
     *
     * @param entryModels model领域模型集合
     * @return po数据库实体集合
     */
    public static List<EntryPo> modelToPos(Collection<EntryModel> entryModels) {
        if (CollUtil.isEmpty(entryModels)) {
            return List.of();
        }
        return entryModels
                .parallelStream()
                .map(EntryRepositoryConverter::modelToPo)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    /**
     * model领域模型转changePo数据库实体
     *
     * @param entryModel model领域模型
     * @return changePo数据库实体
     */
    public static ChangeEntryPo modelToChangePo(EntryModel entryModel) {
        if (ObjUtil.isEmpty(entryModel)) {
            return null;
        }
        return ChangeEntryPo.builder()
                .entryId(SequenceFactory.seqRealize(SeqRealizeEnum.Zookeeper).snowflakeIdWorker().next())
                .entryCode(entryModel.getEntryCode())
                .entryVersion(entryModel.getEntryVersion())
                .entryName(entryModel.getEntryName())
                .entryType(entryModel.getEntryType())
                .entrySharding(entryModel.getEntrySharding())
                .sort(entryModel.getSort())
                .deleteStatus(Optional.of(entryModel)
                        .map(EntryModel::getDeleteStatus)
                        .map(DeleteVo::getDeleteStatus)
                        .orElse(ZeroOrOneEnum.ZERO)
                        .getItemCode())
                .deleteOperator(Optional.of(entryModel)
                        .map(EntryModel::getDeleteStatus)
                        .map(DeleteVo::getDeleteOperator)
                        .orElse(null))
                .deleteTime(Optional.of(entryModel)
                        .map(EntryModel::getDeleteStatus)
                        .map(DeleteVo::getDeleteTime)
                        .orElse(null))
                .build();
    }


    /**
     * model领域模型集合转changePo数据库实体集合
     *
     * @param entryModels model领域模型集合
     * @return changePo数据库实体集合
     */
    public static List<ChangeEntryPo> modelToChangePos(Collection<EntryModel> entryModels) {
        if (CollUtil.isEmpty(entryModels)) {
            return List.of();
        }
        return entryModels
                .parallelStream()
                .map(EntryRepositoryConverter::modelToChangePo)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    /**
     * po数据库实体转model领域模型
     *
     * @param entryPo po数据库实体
     * @return model领域模型
     */
    public static EntryModel poToModel(EntryPo entryPo) {
        if (ObjUtil.isEmpty(entryPo)) {
            return null;
        }
        DeleteVo deleteValue = DeleteVo.builder()
                .deleteStatus(BaseMetaData.EnumHelper.INSTANCE
                        .findOptional(ZeroOrOneEnum.class, entryPo.getDeleteStatus()).orElse(ZeroOrOneEnum.ZERO))
                .deleteTime(entryPo.getDeleteTime())
                .deleteOperator(entryPo.getDeleteOperator())
                .build();
        return EntryModel.builder()
                .entryCode(entryPo.getEntryCode())
                .entryName(entryPo.getEntryName())
                .entryType(entryPo.getEntryType())
                .entrySharding(entryPo.getEntrySharding())
                .sort(entryPo.getSort())
                .deleteStatus(deleteValue)
                .build();
    }

    /**
     * po数据库实体集合转model领域模型集合
     *
     * @param entryPos po数据库实体集合
     * @return model领域模型集合
     */
    public static List<EntryModel> poToModels(Collection<EntryPo> entryPos) {
        if (CollUtil.isEmpty(entryPos)) {
            return List.of();
        }
        return entryPos
                .parallelStream()
                .map(EntryRepositoryConverter::poToModel)
                .collect(Collectors.toList());
    }


    /**
     * changePo数据库实体转model领域模型
     *
     * @param changeEntryPo changePo数据库实体
     * @return model领域模型
     */
    public static EntryModel changePoToModel(ChangeEntryPo changeEntryPo) {
        if (ObjUtil.isEmpty(changeEntryPo)) {
            return null;
        }
        DeleteVo deleteValue = DeleteVo.builder()
                .deleteStatus(BaseMetaData.EnumHelper.INSTANCE
                        .findOptional(ZeroOrOneEnum.class, changeEntryPo.getDeleteStatus())
                        .orElse(ZeroOrOneEnum.ZERO))
                .deleteTime(changeEntryPo.getDeleteTime())
                .deleteOperator(changeEntryPo.getDeleteOperator())
                .build();

        return EntryModel.builder()
                .entryCode(changeEntryPo.getEntryCode())
                .entryVersion(changeEntryPo.getEntryVersion())
                .entryName(changeEntryPo.getEntryName())
                .entryType(changeEntryPo.getEntryType())
                .entrySharding(changeEntryPo.getEntrySharding())
                .sort(changeEntryPo.getSort())
                .deleteStatus(deleteValue)
                .build();
    }

    /**
     * changePo数据库实体集合转model领域模型集合
     *
     * @param changeEntryPos changePo数据库实体集合
     * @return model领域模型集合
     */
    public static List<EntryModel> changePoToModels(Collection<ChangeEntryPo> changeEntryPos) {
        if (CollUtil.isEmpty(changeEntryPos)) {
            return List.of();
        }
        return changeEntryPos
                .parallelStream()
                .map(EntryRepositoryConverter::changePoToModel)
                .collect(Collectors.toList());
    }

}
