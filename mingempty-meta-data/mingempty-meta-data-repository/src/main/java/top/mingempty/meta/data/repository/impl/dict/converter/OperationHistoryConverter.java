package top.mingempty.meta.data.repository.impl.dict.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import top.mingempty.meta.data.domain.biz.dict.model.OperationHistoryModel;
import top.mingempty.meta.data.domain.biz.dict.value.OperationHistoryVo;
import top.mingempty.meta.data.repository.model.po.OperationHistoryPo;
import top.mingempty.sequence.enums.SeqRealizeEnum;
import top.mingempty.sequence.factory.SequenceFactory;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 操作历史转换器
 *
 * @author zzhao
 */
public class OperationHistoryConverter {

    /**
     * po数据库实体转model领域模型
     *
     * @param operationHistoryPo po数据库实体
     * @return model领域模型
     */
    public static OperationHistoryVo poToVo(OperationHistoryPo operationHistoryPo) {
        if (ObjUtil.isEmpty(operationHistoryPo)) {
            return null;
        }
        return OperationHistoryVo.builder()
                .entryVersion(operationHistoryPo.getEntryVersion())
                .operationType(operationHistoryPo.getOperationType())
                .restoreEntryVersion(operationHistoryPo.getRestoreEntryVersion())
                .operatorCode(operationHistoryPo.getOperatorCode())
                .operationTime(operationHistoryPo.getOperationTime())
                .batchId(operationHistoryPo.getBatchId())
                .build();
    }

    /**
     * model领域值对象转po数据库实体
     *
     * @param entryCode          条目编码
     * @param operationHistoryVo model领域值对象
     * @return po数据库实体
     */
    public static OperationHistoryPo voToPo(String entryCode,  OperationHistoryVo operationHistoryVo) {
        if (StrUtil.isEmpty(entryCode)
                || ObjUtil.isEmpty(operationHistoryVo)) {
            return null;
        }

        return OperationHistoryPo.builder()
                .operationHistoryId(SequenceFactory.seqRealize(SeqRealizeEnum.Zookeeper).snowflakeIdWorker().next())
                .entryCode(entryCode)
                .entryVersion(operationHistoryVo.getEntryVersion())
                .operationType(operationHistoryVo.getOperationType())
                .restoreEntryVersion(operationHistoryVo.getRestoreEntryVersion())
                .operatorCode(operationHistoryVo.getOperatorCode())
                .operationTime(operationHistoryVo.getOperationTime())
                .batchId(operationHistoryVo.getBatchId())
                .build();
    }


    /**
     * model领域模型转po数据库实体
     *
     * @param operationHistoryModel model领域模型
     * @return po po数据库实体
     */
    public static List<OperationHistoryPo> modelToPos(OperationHistoryModel operationHistoryModel) {
        if (ObjUtil.isEmpty(operationHistoryModel)
                || CollUtil.isEmpty(operationHistoryModel.getOperationHistorysByChange())) {
            return new CopyOnWriteArrayList<>();
        }
        return operationHistoryModel.getOperationHistorysByChange()
                .parallelStream()
                .map(vo -> voToPo(operationHistoryModel.getEntryCode(), vo))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    /**
     * model领域模型集合转po数据库实体集合
     *
     * @param operationHistoryModels model领域模型集合
     * @return po po数据库实体
     */
    public static List<OperationHistoryPo> modelToPos(Collection<OperationHistoryModel> operationHistoryModels) {
        if (CollUtil.isEmpty(operationHistoryModels)) {
            return new CopyOnWriteArrayList<>();
        }
        return operationHistoryModels
                .parallelStream()
                .map(operationHistoryModel -> modelToPos(operationHistoryModel))
                .filter(Objects::nonNull)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
    }


    /**
     * po数据库实体集合转model领域模型集合
     *
     * @param operationHistoryPos po数据库实体集合
     * @return model领域模型集合
     */
    public static List<OperationHistoryModel> poToModels(Collection<OperationHistoryPo> operationHistoryPos) {
        if (CollUtil.isEmpty(operationHistoryPos)) {
            return List.of();
        }
        return operationHistoryPos
                .parallelStream()
                .collect(Collectors.groupingBy(OperationHistoryPo::getEntryCode,
                        Collectors.mapping(OperationHistoryConverter::poToVo, Collectors.toSet())))
                .entrySet()
                .parallelStream()
                .map(entry -> new OperationHistoryModel(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

}
