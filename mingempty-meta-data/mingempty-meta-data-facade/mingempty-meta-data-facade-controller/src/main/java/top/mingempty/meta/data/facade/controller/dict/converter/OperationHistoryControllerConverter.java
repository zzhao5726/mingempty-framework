package top.mingempty.meta.data.facade.controller.dict.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import top.mingempty.meta.data.domain.biz.dict.service.info.OperationHistoryInfo;
import top.mingempty.meta.data.domain.enums.DictOperationEnum;
import top.mingempty.meta.data.facade.domain.dict.OperationHistoryDetailsDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 条目操作历史相关控制器转换器
 *
 * @author zzhao
 */
public class OperationHistoryControllerConverter {

    public static List<OperationHistoryDetailsDto> infoToDtos(Collection<OperationHistoryInfo> operationHistorys) {
        if (CollUtil.isEmpty(operationHistorys)) {
            return List.of();
        }
        return operationHistorys.parallelStream()
                .map(OperationHistoryControllerConverter::infoToDto)
                .filter(ObjUtil::isNotNull)
                .collect(Collectors.toList());
    }

    private static OperationHistoryDetailsDto infoToDto(OperationHistoryInfo operationHistoryInfo) {
        if (ObjUtil.isEmpty(operationHistoryInfo)) {
            return null;
        }
        return OperationHistoryDetailsDto.builder()
                .operationType(Optional.ofNullable(operationHistoryInfo.getOperationType())
                        .map(DictOperationEnum::getItemCode)
                        .orElse(null))
                .entryVersion(operationHistoryInfo.getEntryVersion())
                .restoreEntryVersion(operationHistoryInfo.getRestoreEntryVersion())
                .operatorCode(operationHistoryInfo.getOperatorCode())
                .operationTime(operationHistoryInfo.getOperationTime())
                .batchId(operationHistoryInfo.getBatchId())
                .build();
    }
}
