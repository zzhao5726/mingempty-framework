package top.mingempty.meta.data.domain.biz.dict.service.converter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.biz.dict.event.EntryModifiesEventInfo;
import top.mingempty.meta.data.domain.biz.dict.model.EntryModel;
import top.mingempty.meta.data.domain.biz.dict.service.info.EntryInfo;
import top.mingempty.meta.data.domain.biz.dict.service.info.modifies.EntryModifiesInfo;
import top.mingempty.meta.data.domain.biz.dict.value.DeleteVo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 条目领域服务层转换器
 *
 * @author zzhao
 */
public class EntryDomainConverter {

    /**
     * 将内部传输对象转换为领域模型
     */
    public static EntryModel infoToModel(String entryCode, Long entryChangeVertion, EntryModifiesInfo entryModifiesInfo) {
        if (ObjUtil.isEmpty(entryModifiesInfo)) {
            return null;
        }

        return EntryModel.builder()
                .entryCode(entryCode)
                .entryVersion(entryChangeVertion)
                .entryName(entryModifiesInfo.getEntryName())
                .entryType(entryModifiesInfo.getEntryType())
                .entrySharding(entryModifiesInfo.getEntrySharding())
                .sort(entryModifiesInfo.getSort())
                .deleteStatus(DeleteVo.builder()
                        .deleteStatus(Optional.ofNullable(entryModifiesInfo.getDeleteStatus())
                                .orElse(ZeroOrOneEnum.ZERO))
                        .build())
                .build();
    }

    /**
     * 领域模型拷贝
     *
     * @param entryModelByOld 旧领域模型
     * @param entryModelByNew 新领域模型
     * @return 修改后的领域模型
     */
    public static EntryModel modifies(EntryModel entryModelByOld, EntryModel entryModelByNew) {
        if (ObjUtil.isEmpty(entryModelByOld)
                || ObjUtil.isEmpty(entryModelByNew)) {
            return entryModelByNew;
        }
        DeleteVo deleteStatus = Optional.ofNullable(entryModelByNew.getDeleteStatus())
                .map(DeleteVo::cloneOne)
                .orElse(entryModelByOld.getDeleteStatus());
        entryModelByNew.setEntryCode(ObjUtil.defaultIfNull(entryModelByNew.getEntryCode(), entryModelByOld.getEntryCode()));
        entryModelByNew.setEntryName(ObjUtil.defaultIfNull(entryModelByNew.getEntryName(), entryModelByOld.getEntryName()));
        entryModelByNew.setEntryType(ObjUtil.defaultIfNull(entryModelByNew.getEntryType(), entryModelByOld.getEntryType()));
        entryModelByNew.setEntrySharding(ObjUtil.defaultIfNull(entryModelByNew.getEntrySharding(), entryModelByOld.getEntrySharding()));
        entryModelByNew.setSort(ObjUtil.defaultIfNull(entryModelByNew.getSort(), entryModelByOld.getSort()));
        entryModelByNew.setDeleteStatus(deleteStatus);
        return entryModelByNew;
    }

    /**
     * 领域模型集合转换
     *
     * @param entryModels 领域模型集合
     * @return 条目信息领域模型
     */
    public static List<EntryInfo> modelToInfos(Collection<EntryModel> entryModels) {
        if (CollUtil.isEmpty(entryModels)) {
            return List.of();
        }
        return entryModels
                .parallelStream()
                .map(EntryDomainConverter::modelToInfo)
                .collect(Collectors.toList());
    }

    /**
     * 领域模型转换
     *
     * @param entryModel 领域模型
     * @return 条目信息领域模型
     */
    public static EntryInfo modelToInfo(EntryModel entryModel) {
        if (ObjUtil.isEmpty(entryModel)) {
            return null;
        }
        return EntryInfo.builder()
                .entryCode(entryModel.getEntryCode())
                .entryVersion(entryModel.getEntryVersion())
                .entryName(entryModel.getEntryName())
                .entryType(entryModel.getEntryType())
                .entrySharding(entryModel.getEntrySharding())
                .sort(entryModel.getSort())
                .deleteStatus(Optional.ofNullable(entryModel.getDeleteStatus())
                        .map(DeleteVo::getDeleteStatus)
                        .orElse(ZeroOrOneEnum.ZERO))
                .deleteOperator(Optional.ofNullable(entryModel.getDeleteStatus())
                        .map(DeleteVo::getDeleteOperator)
                        .orElse(null))
                .deleteTime(Optional.ofNullable(entryModel.getDeleteStatus())
                        .map(DeleteVo::getDeleteTime)
                        .orElse(null))
                .build();
    }

    /**
     * 领域模型转换为字典条目修改信息事件通知内部传输对象
     *
     * @param entryModel 领域模型
     * @return 字典条目修改信息事件通知内部传输对象
     */
    public static EntryModifiesEventInfo modelToEventInfo(EntryModel entryModel) {
        if (ObjUtil.isEmpty(entryModel)) {
            return null;
        }
        return EntryModifiesEventInfo.builder()
                .entryCode(entryModel.getEntryCode())
                .entryName(entryModel.getEntryName())
                .sort(entryModel.getSort())
                .deleteStatus(Optional.ofNullable(entryModel.getDeleteStatus())
                        .map(DeleteVo::getDeleteStatus)
                        .orElse(ZeroOrOneEnum.ZERO))
                .build();
    }
}
