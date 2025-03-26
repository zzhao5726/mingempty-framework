package top.mingempty.meta.data.domain.biz.dict.model;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.commons.exception.MetaDataException;
import top.mingempty.meta.data.domain.biz.dict.value.DeleteVo;
import top.mingempty.meta.data.domain.biz.dict.value.LabelVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * 字典项标签领域模型
 */
public class LabelModel {

    /**
     * 条目编号
     */
    @Getter
    private final String entryCode;

    /**
     * 条目版本（默认1）
     */
    @Setter
    @Getter
    private Long entryVersion;


    /**
     * 条目字典项标签信息值对象
     */
    private final Map<LabelVo, LabelVo> labels = new ConcurrentHashMap<>();

    /**
     * 条目字典项标签信息值对象备份数据
     */
    private final Map<LabelVo, LabelVo> labelsByBack = new ConcurrentHashMap<>();

    /**
     * 条目字典项标签信息值对象修改数据
     */
    private final Collection<LabelVo> labelsByChange = new CopyOnWriteArraySet<>();

    public LabelModel(String entryCode, Collection<LabelVo> labelVos) {
        this.entryCode = entryCode;
        if (CollUtil.isEmpty(labelVos)) {
            return;
        }
        labelVos.parallelStream()
                .forEach(label -> {
                    labels.put(label, label);
                    labelsByBack.put(label, label);
                });
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LabelModel that)) return false;
        return Objects.equals(getEntryCode(), that.getEntryCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEntryCode());
    }

    public List<LabelVo> getLabels() {
        return List.copyOf(labels.values());
    }

    public List<LabelVo> getLabelsByBack() {
        return List.copyOf(labelsByBack.values());
    }

    public List<LabelVo> getLabelsByChange() {
        return List.copyOf(labelsByChange);
    }


    /**
     * 获取某一个条目的权限信息
     * <p>
     * 当未找到数据时，返回空
     *
     * @param labelCode 条目字典项标签编码
     * @param itemCode  条目字典项编码
     * @return
     */
    public LabelVo gainOne(String labelCode, String itemCode) {
        if (!exists(labelCode, itemCode)) {
            return null;
        }

        return gainOne(LabelVo.builder()
                .labelCode(labelCode)
                .itemCode(itemCode)
                .build());
    }


    /**
     * 获取某一个条目的权限信息
     * <p>
     * 当未找到数据时，返回空
     *
     * @param labelVo 条目权限
     * @return
     */
    public LabelVo gainOne(LabelVo labelVo) {
        if (ObjUtil.isEmpty(labelVo)) {
            return null;
        }
        return labels.get(labelVo);
    }


    /**
     * 基于备份数据获取某一个条目的权限信息
     * <p>
     * 当未找到数据时，返回空
     *
     * @param labelCode 条目字典项标签编码
     * @param itemCode  条目字典项编码
     * @return
     */
    public LabelVo gainBackOne(String labelCode, String itemCode) {
        if (!existsBack(labelCode, itemCode)) {
            return null;
        }

        return gainBackOne(LabelVo.builder()
                .labelCode(labelCode)
                .itemCode(itemCode)
                .build());
    }


    /**
     * 基于备份数据获取某一个条目的权限信息
     * <p>
     * 当未找到数据时，返回空
     *
     * @param labelVo 条目权限
     * @return
     */
    public LabelVo gainBackOne(LabelVo labelVo) {
        if (ObjUtil.isEmpty(labelVo)) {
            return null;
        }
        return labelsByBack.get(labelVo);
    }


    /**
     * 添加条目权限
     *
     * @param labelCode 条目字典项标签编码
     * @param itemCode  条目字典项编码
     */
    public void add(String labelCode, String itemCode) {
        modifies(labelCode, itemCode, ZeroOrOneEnum.ZERO);
    }


    /**
     * 删除条目权限
     *
     * @param labelCode 条目字典项标签编码
     * @param itemCode  条目字典项编码
     */
    public void delete(String labelCode, String itemCode) {
        modifies(labelCode, itemCode, ZeroOrOneEnum.ONE);
    }

    /**
     * 修改条目权限
     *
     * @param labels 条目权限集合
     */
    public void modifies(Collection<LabelVo> labels) {
        if (labels == null) {
            labels = new CopyOnWriteArraySet<>();
        }
        // 处理删除逻辑
        Collection<LabelVo> toDelete = CollUtil.subtract(this.labelsByBack.values(), labels);
        toDelete.parallelStream()
                .filter(this::isDelete)
                .map(LabelVo::delete)
                .forEach(labels::add);
        labels.parallelStream()
                .forEach(labelVo
                        -> modifies(labelVo.getLabelCode(),
                        labelVo.getItemCode(),
                        labelVo.deleteStatus())
                );
    }

    /**
     * 修改条目权限
     *
     * @param labelCode    条目字典项标签编码
     * @param itemCode     条目字典项编码
     * @param deleteStatus 条目权限逻辑删除状态
     */
    private void modifies(String labelCode, String itemCode, ZeroOrOneEnum deleteStatus) {
        checkModifies(labelCode, itemCode);
        LabelVo labelVo = LabelVo.builder()
                .labelCode(labelCode)
                .itemCode(itemCode)
                .deleteStatus(DeleteVo.builder()
                        .deleteStatus(Optional.ofNullable(deleteStatus).orElse(ZeroOrOneEnum.ZERO))
                        .build())
                .build();
        if (checkSame(labelVo)) {
            return;
        }
        labels.put(labelVo, labelVo);
        labelsByChange.add(labelVo);
    }


    public void checkModifies(String labelCode, String itemCode) {
        if (ObjUtil.isEmpty(labelCode)
                || StrUtil.isEmpty(itemCode)) {
            throw new MetaDataException("meta-data-domain-0000000006");
        }
    }

    /**
     * 判断条目权限是否存在
     *
     * @param labelCode 条目字典项标签编码
     * @param itemCode  条目字典项编码
     * @return
     */
    public boolean exists(String labelCode, String itemCode) {
        if (StrUtil.isEmpty(labelCode)
                || StrUtil.isEmpty(itemCode)) {
            return false;
        }
        return exists(LabelVo.builder()
                .labelCode(labelCode)
                .itemCode(itemCode)
                .build());
    }

    /**
     * 判断条目权限是否存在
     *
     * @param labelVo 条目权限
     * @return
     */
    public boolean exists(LabelVo labelVo) {
        if (ObjUtil.isEmpty(labelVo)) {
            return false;
        }
        return labels.containsKey(labelVo);
    }

    /**
     * 基于备份数据判断条目权限是否存在
     *
     * @param labelCode 条目字典项标签编码
     * @param itemCode  条目字典项编码
     * @return
     */
    public boolean existsBack(String labelCode, String itemCode) {
        if (StrUtil.isEmpty(labelCode)
                || StrUtil.isEmpty(itemCode)) {
            return false;
        }
        return existsBack(LabelVo.builder()
                .labelCode(labelCode)
                .itemCode(itemCode)
                .build());
    }

    /**
     * 基于备份数据判断条目权限是否存在
     *
     * @param labelVo 条目权限
     * @return
     */
    public boolean existsBack(LabelVo labelVo) {
        if (ObjUtil.isEmpty(labelVo)) {
            return false;
        }
        return labelsByBack.containsKey(labelVo);
    }

    /**
     * 条目权限是否被删除
     * <p>
     * 当未找到当前条目权限数据时，默认为被删除
     *
     * @param labelCode 条目字典项标签编码
     * @param itemCode  条目字典项编码
     * @return
     */
    public boolean isDelete(String labelCode, String itemCode) {
        if (StrUtil.isEmpty(labelCode)
                || StrUtil.isEmpty(itemCode)) {
            return true;
        }

        return isDelete(LabelVo.builder()
                .labelCode(labelCode)
                .itemCode(itemCode)
                .build());
    }

    /**
     * 条目权限是否被删除
     * <p>
     * 当未找到当前条目权限数据时，默认为被删除
     *
     * @param labelVo 条目权限
     * @return
     */
    public boolean isDelete(LabelVo labelVo) {
        if (ObjUtil.isEmpty(labelVo)) {
            return true;
        }
        return Optional.ofNullable(labels.get(labelVo))
                .map(LabelVo::deleteStatus)
                .orElse(ZeroOrOneEnum.ONE)
                .equals(ZeroOrOneEnum.ONE);
    }

    /**
     * 检查数据是否完全一致
     *
     * @param labelVo
     * @return
     */
    public boolean checkSame(LabelVo labelVo) {
        if (ObjUtil.isEmpty(labelVo)) {
            return false;
        }
        LabelVo labelVoByExists = gainOne(labelVo);
        if (ObjUtil.isEmpty(labelVoByExists)) {
            return false;
        }
        return checkSame(labelVo, labelVoByExists);
    }


    /**
     * 检查数据是否完全一致
     *
     * @param labelVo
     * @param labelVo2
     * @return
     */
    public boolean checkSame(LabelVo labelVo, LabelVo labelVo2) {
        return Objects.equals(labelVo, labelVo2)
                && Objects.equals(labelVo.getDeleteStatus(), labelVo2.getDeleteStatus());
    }


    /**
     * 克隆一个对象
     *
     * @return
     */
    public LabelModel cloneOne() {
        LabelModel labelModel = new LabelModel(this.getEntryCode(), this.getLabels());
        labelModel.setEntryVersion(this.getEntryVersion());
        return labelModel;
    }


    /**
     * 克隆一个对象
     *
     * @return
     */
    public static LabelModel cloneOne(String entryCode, LabelModel labelModel) {
        if (ObjUtil.isEmpty(labelModel)) {
            return new LabelModel(entryCode, List.of());
        }
        return labelModel.cloneOne();
    }


    /**
     * 根据条目字典项标签编码获取条目字典项编码集合
     *
     * @param labelCode
     * @return
     */
    public Set<String> getItemCodesByLabelCode(String labelCode) {
        if (StrUtil.isEmpty(labelCode)) {
            return Set.of();
        }

        return this.getLabels()
                .parallelStream()
                .filter(labelVo -> labelCode.equals(labelVo.getLabelCode()))
                .map(LabelVo::getItemCode)
                .collect(Collectors.toSet());
    }

}
