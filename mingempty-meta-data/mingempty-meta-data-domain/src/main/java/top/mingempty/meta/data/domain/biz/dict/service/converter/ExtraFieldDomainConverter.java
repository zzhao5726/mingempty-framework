package top.mingempty.meta.data.domain.biz.dict.service.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import top.mingempty.meta.data.domain.biz.dict.model.ExtraFieldModel;
import top.mingempty.meta.data.domain.biz.dict.service.info.ExtraFieldInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.ExtraFieldModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.value.ExtraFieldVo;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 字典扩展字段信息领域服务层转换器
 *
 * @author zzhao
 */
public class ExtraFieldDomainConverter {

    public static ExtraFieldInfo voToInfo(ExtraFieldVo extraFieldVo) {
        if (ObjUtil.isEmpty(extraFieldVo)) {
            return null;
        }
        return ExtraFieldInfo.builder()
                .extraFieldCode(extraFieldVo.getExtraFieldCode())
                .extraFieldName(extraFieldVo.getExtraFieldName())
                .typeIsNumber(extraFieldVo.getTypeIsNumber())
                .otherDictFlag(extraFieldVo.getOtherDictFlag())
                .otherEntryCode(extraFieldVo.getOtherEntryCode())
                .extraFieldSort(extraFieldVo.getExtraFieldSort())
                .build();
    }

    /**
     * 领域模型集合转换
     *
     * @param extraFieldModels 领域模型集合
     * @return 条目信息领域模型
     */
    public static List<ExtraFieldInfo> modelToInfos(Collection<ExtraFieldModel> extraFieldModels) {
        if (CollUtil.isEmpty(extraFieldModels)) {
            return new CopyOnWriteArrayList<>();
        }
        return extraFieldModels
                .parallelStream()
                .map(ExtraFieldDomainConverter::modelToInfos)
                .filter(Objects::nonNull)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());
    }

    /**
     * 领域模型转换
     *
     * @param extraFieldModel 领域模型
     * @return 条目信息领域模型
     */
    public static List<ExtraFieldInfo> modelToInfos(ExtraFieldModel extraFieldModel) {
        if (ObjUtil.isEmpty(extraFieldModel)
                || CollUtil.isEmpty(extraFieldModel.getExtraFields())) {
            return new CopyOnWriteArrayList<>();
        }
        return extraFieldModel.getExtraFields()
                .parallelStream()
                .map(ExtraFieldDomainConverter::voToInfo)
                .filter(Objects::nonNull)
                .peek(extraFieldInfo
                        -> extraFieldInfo.setEntryCode(extraFieldModel.getEntryCode())
                        .setEntryVersion(extraFieldModel.getEntryVersion()))
                .collect(Collectors.toList());
    }

    /**
     * 修改信息转换
     *
     * @param extraFieldModifiesInfo 条目信息修改
     * @return 条目信息修改
     */
    public static ExtraFieldVo modifiesInfoToVo(ExtraFieldModifiesInfo extraFieldModifiesInfo) {
        if (ObjUtil.isEmpty(extraFieldModifiesInfo)) {
            return null;
        }
        return ExtraFieldVo.builder()
                .extraFieldCode(extraFieldModifiesInfo.getExtraFieldCode())
                .extraFieldName(extraFieldModifiesInfo.getExtraFieldName())
                .typeIsNumber(extraFieldModifiesInfo.getTypeIsNumber())
                .otherDictFlag(extraFieldModifiesInfo.getOtherDictFlag())
                .otherEntryCode(extraFieldModifiesInfo.getOtherEntryCode())
                .extraFieldSort(extraFieldModifiesInfo.getExtraFieldSort())
                .build();
    }

    /**
     * 修改信息集合转换
     *
     * @param extraFieldModifiesInfos 条目信息修改集合
     * @return 条目信息修改集合
     */
    public static List<ExtraFieldVo> modifiesInfoToVos(Collection<ExtraFieldModifiesInfo> extraFieldModifiesInfos) {
        if (CollUtil.isEmpty(extraFieldModifiesInfos)) {
            return new CopyOnWriteArrayList<>();
        }

        return extraFieldModifiesInfos
                .parallelStream()
                .map(ExtraFieldDomainConverter::modifiesInfoToVo)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
