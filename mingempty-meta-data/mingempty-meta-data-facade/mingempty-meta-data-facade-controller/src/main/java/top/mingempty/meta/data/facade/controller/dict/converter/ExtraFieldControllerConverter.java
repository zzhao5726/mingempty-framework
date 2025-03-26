package top.mingempty.meta.data.facade.controller.dict.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.biz.dict.service.info.ExtraFieldInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ExtraFieldModifiesInfo;
import top.mingempty.meta.data.facade.domain.dict.ExtraFieldDetailsDto;
import top.mingempty.meta.data.facade.domain.dict.ExtraFieldModifiesDto;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 条目扩展字段相关控制器转换器
 *
 * @author zzhao
 */
public class ExtraFieldControllerConverter {


    public static ExtraFieldModifiesInfo modifiesDtoToInfo(ExtraFieldModifiesDto extraFieldModifiesDto) {
        if (ObjUtil.isEmpty(extraFieldModifiesDto)) {
            return null;
        }
        return ExtraFieldModifiesInfo.builder()
                .extraFieldCode(extraFieldModifiesDto.getExtraFieldCode())
                .extraFieldName(extraFieldModifiesDto.getExtraFieldName())
                .typeIsNumber(extraFieldModifiesDto.getTypeIsNumber())
                .otherDictFlag(BaseControllerConverter.zeroOrOneEnumWithDefault(extraFieldModifiesDto.getOtherDictFlag()))
                .otherEntryCode(extraFieldModifiesDto.getOtherEntryCode())
                .extraFieldSort(extraFieldModifiesDto.getExtraFieldSort())
                .build();
    }


    public static List<ExtraFieldModifiesInfo> modifiesDtoToInfo(Collection<ExtraFieldModifiesDto> extraFieldModifiesDtos) {
        if (CollUtil.isEmpty(extraFieldModifiesDtos)) {
            return List.of();
        }

        return extraFieldModifiesDtos.parallelStream()
                .map(ExtraFieldControllerConverter::modifiesDtoToInfo)
                .filter(ObjUtil::isNotNull)
                .collect(Collectors.toList());
    }


    public static List<ExtraFieldDetailsDto> infoToDtos(Collection<ExtraFieldInfo> extraFieldInfos) {
        if (CollUtil.isEmpty(extraFieldInfos)) {
            return List.of();
        }
        return extraFieldInfos.parallelStream()
                .map(ExtraFieldControllerConverter::infoToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static ExtraFieldDetailsDto infoToDto(ExtraFieldInfo extraFieldInfo) {
        if (ObjUtil.isEmpty(extraFieldInfo)) {
            return null;
        }
        return ExtraFieldDetailsDto.builder()
                .extraFieldCode(extraFieldInfo.getExtraFieldCode())
                .extraFieldName(extraFieldInfo.getExtraFieldName())
                .typeIsNumber(Optional.ofNullable(extraFieldInfo.getTypeIsNumber())
                        .map(ZeroOrOneEnum::getItemCode)
                        .orElse(ZeroOrOneEnum.ZERO.getItemCode()))
                .otherDictFlag(Optional.ofNullable(extraFieldInfo.getOtherDictFlag())
                        .map(ZeroOrOneEnum::getItemCode)
                        .orElse(ZeroOrOneEnum.ZERO.getItemCode()))
                .otherEntryCode(extraFieldInfo.getOtherEntryCode())
                .extraFieldSort(extraFieldInfo.getExtraFieldSort())
                .build();
    }
}
