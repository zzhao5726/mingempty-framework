package top.mingempty.meta.data.domain.biz.dict.service.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import top.mingempty.meta.data.domain.biz.dict.model.OperationHistoryModel;
import top.mingempty.meta.data.domain.biz.dict.service.info.OperationHistoryInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.OperationRecordInfo;
import top.mingempty.meta.data.domain.biz.dict.value.OperationHistoryVo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 字典操作历史信息领域服务层转换器
 *
 * @author zzhao
 */
public class OperationHistoryDomainConverter {

    /**
     * 字典操作历史信息转换
     *
     * @param operationHistoryVo 字典操作历史信息
     * @return 字典操作历史信息
     */
    public static OperationHistoryInfo voToInfo(OperationHistoryVo operationHistoryVo) {
        if (ObjUtil.isEmpty(operationHistoryVo)) {
            return null;
        }
        return OperationHistoryInfo.builder()
                .operationType(operationHistoryVo.getOperationType())
                .entryVersion(operationHistoryVo.getEntryVersion())
                .restoreEntryVersion(operationHistoryVo.getRestoreEntryVersion())
                .operatorCode(operationHistoryVo.getOperatorCode())
                .operationTime(operationHistoryVo.getOperationTime())
                .batchId(operationHistoryVo.getBatchId())
                .build();
    }


    /**
     * 领域模型集合转换
     *
     * @param operationHistoryModels 领域模型集合
     * @return 条目信息领域模型
     */
    public static List<OperationHistoryInfo> modelToInfos(Collection<OperationHistoryModel> operationHistoryModels) {
        if (CollUtil.isEmpty(operationHistoryModels)) {
            return new CopyOnWriteArrayList<>();
        }
        return operationHistoryModels
                .parallelStream()
                .map(OperationHistoryDomainConverter::modelToInfos)
                .filter(Objects::nonNull)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
    }

    /**
     * 领域模型转换
     *
     * @param operationHistoryModel 领域模型
     * @return 条目信息领域模型
     */
    public static List<OperationHistoryInfo> modelToInfos(OperationHistoryModel operationHistoryModel) {
        if (ObjUtil.isEmpty(operationHistoryModel)
                || CollUtil.isEmpty(operationHistoryModel.getOperationHistorys())) {
            return new CopyOnWriteArrayList<>();
        }
        return operationHistoryModel.getOperationHistorys()
                .parallelStream()
                .map(OperationHistoryDomainConverter::voToInfo)
                .filter(Objects::nonNull)
                .peek(info -> info.setEntryCode(operationHistoryModel.getEntryCode()))
                .collect(Collectors.toList());
    }

    public static OperationHistoryVo recordInfoToVo(OperationRecordInfo operationRecordInfo, String userCode) {
        if (ObjUtil.isEmpty(operationRecordInfo)) {
            return null;
        }
        return OperationHistoryVo.builder()
                .operationType(operationRecordInfo.getOperationType())
                .operatorCode(userCode)
                .entryVersion(operationRecordInfo.getEntryVersion())
                .restoreEntryVersion(operationRecordInfo.getRestoreEntryVersion())
                .operationTime(LocalDateTime.now())
                .batchId(operationRecordInfo.getBatchId())
                .build();
    }

}
