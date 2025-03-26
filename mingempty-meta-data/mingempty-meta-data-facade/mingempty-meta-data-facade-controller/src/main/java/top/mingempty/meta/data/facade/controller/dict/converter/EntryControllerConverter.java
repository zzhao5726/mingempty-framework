package top.mingempty.meta.data.facade.controller.dict.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.biz.dict.query.EntryQuery;
import top.mingempty.meta.data.domain.biz.dict.service.info.AuthorizationInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.EntryBaseInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.EntryInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.EntryBaseModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.EntryModifiesInfo;
import top.mingempty.meta.data.domain.enums.AuthorizationTypeEnum;
import top.mingempty.meta.data.domain.enums.EntryTypeEnum;
import top.mingempty.meta.data.facade.domain.dict.EntryBaseModifiesDto;
import top.mingempty.meta.data.facade.domain.dict.EntryDetailsDto;
import top.mingempty.meta.data.facade.domain.dict.EntryDetailsQueryDto;
import top.mingempty.meta.data.facade.domain.dict.EntryListDto;
import top.mingempty.meta.data.facade.domain.dict.EntryListQueryDto;
import top.mingempty.meta.data.facade.domain.dict.EntryModifiesDto;
import top.mingempty.meta.data.facade.domain.dict.ExtraFieldDetailsDto;
import top.mingempty.meta.data.facade.domain.dict.OperationHistoryDetailsDto;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 条目相关控制器转换器
 *
 * @author zzhao
 */
public class EntryControllerConverter {


    public static EntryBaseModifiesInfo modifiesDtoToInfo(EntryBaseModifiesDto entryBaseModifiesDto) {
        if (ObjUtil.isEmpty(entryBaseModifiesDto)) {
            return null;
        }
        return EntryBaseModifiesInfo.builder()
                .entryCode(entryBaseModifiesDto.getEntryCode())
                .entry(modifiesDtoToInfo(entryBaseModifiesDto.getEntry()))
                .authorizations(AuthorizationControllerConverter.modifiesDtoToInfo(entryBaseModifiesDto.getAuthorizations()))
                .extraFields(ExtraFieldControllerConverter.modifiesDtoToInfo(entryBaseModifiesDto.getExtraFields()))
                .build();
    }


    public static EntryModifiesInfo modifiesDtoToInfo(EntryModifiesDto entryModifiesDto) {
        return EntryModifiesInfo.builder()
                .entryName(entryModifiesDto.getEntryName())
                .entryType(BaseControllerConverter.entryTypeWithDefault(entryModifiesDto.getEntryType()))
                .entrySharding(BaseControllerConverter.zeroOrOneEnumWithDefault(entryModifiesDto.getEntrySharding()))
                .sort(entryModifiesDto.getSort())
                .deleteStatus(BaseControllerConverter.zeroOrOneEnumWithDefault(entryModifiesDto.getDeleteStatus()))
                .build();
    }


    public static List<EntryModifiesInfo> modifiesDtoToInfos(Collection<EntryModifiesDto> entryModifiesDtos) {
        if (CollUtil.isEmpty(entryModifiesDtos)) {
            return List.of();
        }

        return entryModifiesDtos.parallelStream()
                .map(EntryControllerConverter::modifiesDtoToInfo)
                .collect(Collectors.toList());
    }

    public static EntryQuery listQueryDtoToQueryInfo(EntryListQueryDto entryListQueryDto) {
        if (ObjUtil.isEmpty(entryListQueryDto)) {
            return EntryQuery.builder().build();
        }
        return EntryQuery.builder()
                .entryCode(entryListQueryDto.getEntryCode())
                .entryCodeLike(entryListQueryDto.getEntryCodeLike())
                .entryCodes(entryListQueryDto.getEntryCodes())
                .entryName(entryListQueryDto.getEntryName())
                .entryNameLike(entryListQueryDto.getEntryNameLike())
                .entryType(BaseControllerConverter.entryType(entryListQueryDto.getEntryType()))
                .entrySharding(BaseControllerConverter.zeroOrOneEnum(entryListQueryDto.getEntrySharding()))
                .deleteStatus(BaseControllerConverter.zeroOrOneEnum(entryListQueryDto.getDeleteStatus()))
                .deleteOperator(entryListQueryDto.getDeleteOperator())
                .deleteTime(entryListQueryDto.getDeleteTime())
                .build();
    }

    public static List<EntryListDto> infoToListDtos(Collection<EntryInfo> entryInfoList) {
        if (CollUtil.isEmpty(entryInfoList)) {
            return List.of();
        }
        return entryInfoList.parallelStream()
                .map(EntryControllerConverter::infoToListDto)
                .filter(ObjUtil::isNotNull)
                .collect(Collectors.toList());
    }

    public static EntryListDto infoToListDto(EntryInfo entryInfo) {
        if (ObjUtil.isEmpty(entryInfo)) {
            return null;
        }
        return EntryListDto.builder()
                .entryCode(entryInfo.getEntryCode())
                .entryName(entryInfo.getEntryName())
                .entryType(Optional.ofNullable(entryInfo.getEntryType())
                        .map(EntryTypeEnum::getItemCode)
                        .orElse(EntryTypeEnum.ONE.getItemCode()))
                .entrySharding(Optional.ofNullable(entryInfo.getEntrySharding())
                        .map(ZeroOrOneEnum::getItemCode)
                        .orElse(ZeroOrOneEnum.ZERO.getItemCode()))
                .sort(entryInfo.getSort())
                .deleteStatus(Optional.ofNullable(entryInfo.getDeleteStatus())
                        .map(ZeroOrOneEnum::getItemCode)
                        .orElse(ZeroOrOneEnum.ZERO.getItemCode()))
                .deleteTime(entryInfo.getDeleteTime())
                .deleteOperator(entryInfo.getDeleteOperator())
                .build();
    }

    public static EntryQuery detailsQueryDtoToQueryInfo(EntryDetailsQueryDto entryDetailsQueryDto) {
        if (ObjUtil.isEmpty(entryDetailsQueryDto)
                || StrUtil.isEmpty(entryDetailsQueryDto.getEntryCode())) {
            return null;
        }
        return EntryQuery.builder()
                .entryCode(entryDetailsQueryDto.getEntryCode())
                .entryVersion(entryDetailsQueryDto.getEntryVersion())
                .build();
    }

    public static EntryDetailsDto baseInfoToDetailsDto(EntryBaseInfo entryBaseInfo) {
        if (ObjUtil.isEmpty(entryBaseInfo)) {
            return null;
        }
        Map<String, Collection<String>> authorizations = Optional.ofNullable(entryBaseInfo.getAuthorizations())
                .orElse(List.of())
                .parallelStream()
                .collect(Collectors.groupingBy(authorizationInfo -> Optional.ofNullable(authorizationInfo.getAuthorizationType())
                                .map(AuthorizationTypeEnum::getItemCode)
                                .orElse(AuthorizationTypeEnum.ONE.getItemCode()),
                        Collectors.mapping(AuthorizationInfo::getAuthorizationCode, Collectors.toCollection(CopyOnWriteArrayList::new))));

        Collection<ExtraFieldDetailsDto> extraFields = ExtraFieldControllerConverter.infoToDtos(entryBaseInfo.getExtraFields());

        Collection<OperationHistoryDetailsDto> operationHistorys = OperationHistoryControllerConverter.infoToDtos(entryBaseInfo.getOperationHistorys());

        return toDetailsDto(entryBaseInfo, authorizations, extraFields, operationHistorys);
    }

    public static EntryDetailsDto toDetailsDto(EntryBaseInfo entryBaseInfo,
                                                Map<String, Collection<String>> authorizations,
                                                Collection<ExtraFieldDetailsDto> extraFields,
                                                Collection<OperationHistoryDetailsDto> operationHistorys) {
        return EntryDetailsDto.builder()
                .entryCode(entryBaseInfo.getEntryCode())
                .entryName(Optional.ofNullable(entryBaseInfo.getEntry())
                        .map(EntryInfo::getEntryName)
                        .orElse(null))
                .entryType(Optional.ofNullable(entryBaseInfo.getEntry())
                        .map(EntryInfo::getEntryType)
                        .map(EntryTypeEnum::getItemCode)
                        .orElse(EntryTypeEnum.ONE.getItemCode()))
                .entrySharding(Optional.ofNullable(entryBaseInfo.getEntry())
                        .map(EntryInfo::getEntrySharding)
                        .map(ZeroOrOneEnum::getItemCode)
                        .orElse(ZeroOrOneEnum.ZERO.getItemCode()))
                .sort(Optional.ofNullable(entryBaseInfo.getEntry())
                        .map(EntryInfo::getSort)
                        .orElse(null))
                .deleteStatus(Optional.ofNullable(entryBaseInfo.getEntry())
                        .map(EntryInfo::getEntrySharding)
                        .map(ZeroOrOneEnum::getItemCode)
                        .orElse(ZeroOrOneEnum.ZERO.getItemCode()))
                .deleteOperator(Optional.ofNullable(entryBaseInfo.getEntry())
                        .map(EntryInfo::getDeleteOperator)
                        .orElse(null))
                .deleteTime(Optional.ofNullable(entryBaseInfo.getEntry())
                        .map(EntryInfo::getDeleteTime)
                        .orElse(null))
                .authorizations(authorizations)
                .extraFields(extraFields)
                .operationHistorys(operationHistorys)
                .build();
    }
}
