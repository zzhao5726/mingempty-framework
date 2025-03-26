package top.mingempty.meta.data.domain.biz.dict.service.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.biz.dict.model.LabelModel;
import top.mingempty.meta.data.domain.biz.dict.service.info.LabelInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.LabelModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.value.DeleteVo;
import top.mingempty.meta.data.domain.biz.dict.value.LabelVo;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 字典项标签信息领域服务层转换器
 *
 * @author zzhao
 */
public class LabelDomainConverter {


    public static LabelInfo voToInfo(LabelVo labelVo) {
        if (ObjUtil.isEmpty(labelVo)) {
            return null;
        }

        return LabelInfo.builder()
                .labelCode(labelVo.getLabelCode())
                .itemCode(labelVo.getItemCode())
                .deleteStatus(Optional.ofNullable(labelVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteStatus)
                        .orElse(ZeroOrOneEnum.ZERO))
                .deleteOperator(Optional.ofNullable(labelVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteOperator)
                        .orElse(null))
                .deleteTime(Optional.ofNullable(labelVo.getDeleteStatus())
                        .map(DeleteVo::getDeleteTime)
                        .orElse(null))
                .build();

    }

    /**
     * 将领域模型转换为内部传输对象
     *
     * @param labelModel
     * @return
     */
    public static List<LabelInfo> modelToInfos(LabelModel labelModel) {
        if (ObjUtil.isEmpty(labelModel)
                || CollUtil.isEmpty(labelModel.getLabels())) {
            return new CopyOnWriteArrayList<>();
        }
        return labelModel.getLabels()
                .parallelStream()
                .map(LabelDomainConverter::voToInfo)
                .filter(Objects::nonNull)
                .peek(labelInfo -> labelInfo.setEntryCode(labelModel.getEntryCode())
                        .setEntryVersion(labelModel.getEntryVersion()))
                .collect(Collectors.toList());
    }

    /**
     * 将领域模型集合转换为内部传输对象集合
     *
     * @param labelModels
     * @return
     */
    public static List<LabelInfo> modelToInfos(Collection<LabelModel> labelModels) {
        if (CollUtil.isEmpty(labelModels)) {
            return new CopyOnWriteArrayList<>();
        }

        return labelModels
                .parallelStream()
                .map(LabelDomainConverter::modelToInfos)
                .flatMap(Collection::parallelStream)
                .collect(Collectors.toList());

    }

    /**
     * 条目字典项标签修改信息转换
     *
     * @param labelModifiesInfo 条目字典项标签修改信息
     * @return
     */
    public static LabelVo modifiesInfoToVo(LabelModifiesInfo labelModifiesInfo) {
        if (ObjUtil.isEmpty(labelModifiesInfo)) {
            return null;
        }
        return LabelVo.builder()
                .labelCode(labelModifiesInfo.getLabelCode())
                .itemCode(labelModifiesInfo.getItemCode())
                .deleteStatus(DeleteVo.builder()
                        .deleteStatus(Optional
                                .ofNullable(labelModifiesInfo.getDeleteStatus())
                                .orElse(ZeroOrOneEnum.ZERO))
                        .build())
                .build();
    }

    /**
     * 条目字典项标签修改信息集合转换
     *
     * @param labelModifiesInfos 条目字典项标签修改信息集合
     * @return
     */
    public static List<LabelVo> modifiesInfoToVos(Collection<LabelModifiesInfo> labelModifiesInfos) {
        if (CollUtil.isEmpty(labelModifiesInfos)) {
            return new CopyOnWriteArrayList<>();
        }
        return labelModifiesInfos.parallelStream()
                .map(LabelDomainConverter::modifiesInfoToVo)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
