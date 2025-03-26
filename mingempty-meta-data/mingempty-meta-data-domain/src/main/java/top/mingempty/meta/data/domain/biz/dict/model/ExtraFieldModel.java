package top.mingempty.meta.data.domain.biz.dict.model;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.commons.exception.MetaDataException;
import top.mingempty.meta.data.domain.biz.dict.value.ExtraFieldVo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 字典扩展字段信息领域模型
 */
public class ExtraFieldModel {

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
     * 条目扩展字段信息值对象
     */
    private final List<ExtraFieldVo> extraFieldVos = new CopyOnWriteArrayList<>();

    /**
     * 条目扩展字段信息值对象备份数据
     */
    private final List<ExtraFieldVo> extraFieldVosByBack = new CopyOnWriteArrayList<>();

    /**
     * 条目扩展字段信息值对象修改数据
     */
    private final Collection<ExtraFieldVo> extraFieldsByChange = new CopyOnWriteArraySet<>();

    /**
     * 条目扩展字段信息值对象
     */
    private final Map<String, ExtraFieldVo> extraFields = new ConcurrentHashMap<>();

    /**
     * 条目扩展字段信息值对象备份数据
     */
    private final Map<String, ExtraFieldVo> extraFieldsByBack = new ConcurrentHashMap<>();

    public ExtraFieldModel(String entryCode, Collection<ExtraFieldVo> extraFieldVos) {
        this.entryCode = entryCode;
        if (CollUtil.isEmpty(extraFieldVos)) {
            return;
        }
        extraFieldVos.parallelStream()
                .forEach(extraFieldVo -> {
                    this.extraFieldVos.add(extraFieldVo);
                    this.extraFieldVosByBack.add(extraFieldVo);
                    this.extraFields.put(extraFieldVo.getExtraFieldCode(), extraFieldVo);
                    this.extraFieldsByBack.put(extraFieldVo.getExtraFieldCode(), extraFieldVo);
                });
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ExtraFieldModel that)) return false;
        return Objects.equals(getEntryCode(), that.getEntryCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEntryCode());
    }


    /**
     * 获取条目扩展字段信息值对象
     *
     * @return
     */
    public List<ExtraFieldVo> getExtraFields() {
        return List.copyOf(this.extraFieldVos);
    }

    /**
     * 获取条目扩展字段信息值对象备份数据
     *
     * @return
     */
    public List<ExtraFieldVo> getExtraFieldsByBack() {
        return List.copyOf(this.extraFieldVosByBack);
    }

    /**
     * 获取条目扩展字段信息值对象修改数据
     *
     * @return
     */
    public List<ExtraFieldVo> getExtraFieldsByChange() {
        return List.copyOf(this.extraFieldsByChange);
    }

    /**
     * 获取某一个条目的扩展字段信息
     * <p>
     * 当未找到数据时，返回空
     *
     * @param extraFieldCode 扩展字段编码
     * @return
     */
    public ExtraFieldVo gainOne(String extraFieldCode) {
        if (!exists(extraFieldCode)) {
            return null;
        }
        return extraFields.get(extraFieldCode);
    }

    /**
     * 基于备份数据获取某一个条目的扩展字段信息
     * <p>
     * 当未找到数据时，返回空
     *
     * @param extraFieldCode 扩展字段编码
     * @return
     */
    public ExtraFieldVo gainBackOne(String extraFieldCode) {
        if (!existsBack(extraFieldCode)) {
            return null;
        }
        return extraFieldsByBack.get(extraFieldCode);
    }

    public void modifies(ExtraFieldVo extraField) {
        checkModifies(extraField);
        if (this.checkSame(extraField)) {
            //说明相同
            return;
        }
        this.extraFields.put(extraField.getExtraFieldCode(), extraField);
        this.extraFieldsByChange.add(extraField);
        this.extraFieldVos.add(extraField);
        this.extraFieldVosByBack.add(extraField);
    }

    public void modifies(Collection<ExtraFieldVo> extraFields) {
        if (CollUtil.isEmpty(extraFields)) {
            return;
        }
        //先做校验,防止污染原始数据
        extraFields.parallelStream()
                .forEach(this::checkModifies);

        extraFields.parallelStream()
                .forEach(this::modifies);
    }


    public void checkModifies(ExtraFieldVo extraField) {
        if (ObjUtil.isEmpty(extraField)
                || StrUtil.isEmpty(extraField.getExtraFieldCode())
                || StrUtil.isEmpty(extraField.getExtraFieldName())) {
            throw new MetaDataException("meta-data-domain-0000000005");
        }
        if (ObjUtil.isEmpty(extraField.getOtherDictFlag())) {
            extraField.setOtherDictFlag(ZeroOrOneEnum.ZERO);
        } else if (ZeroOrOneEnum.ONE.equals(extraField.getOtherDictFlag())
                && StrUtil.isEmpty(extraField.getOtherEntryCode())) {
            throw new MetaDataException("meta-data-domain-0000000005");
        }
        if (ObjUtil.isEmpty(extraField.getExtraFieldSort())) {
            extraField.setExtraFieldSort(BigDecimal.ZERO);
        }

    }


    /**
     * 判断条目扩展字段信息是否存在
     *
     * @param extraFieldCode 扩展字段编码
     * @return
     */
    public boolean exists(String extraFieldCode) {
        if (StrUtil.isEmpty(extraFieldCode)) {
            return false;
        }
        return extraFields.containsKey(extraFieldCode);
    }


    /**
     * 基于备份数据判断条目扩展字段信息是否存在
     *
     * @param extraFieldCode 扩展字段编码
     * @return
     */
    public boolean existsBack(String extraFieldCode) {
        if (StrUtil.isEmpty(extraFieldCode)) {
            return false;
        }
        return extraFieldsByBack.containsKey(extraFieldCode);
    }

    /**
     * 获取某一个条目扩展字段是否为其它字典项
     *
     * @param extraFieldCode 扩展字段编码
     * @return
     */
    public String gainExtraFieldName(String extraFieldCode) {
        return Optional.ofNullable(gainOne(extraFieldCode))
                .map(ExtraFieldVo::getExtraFieldName)
                .orElse(null);
    }

    /**
     * 获取某一个条目扩展字段是否为其它字典项
     *
     * @param extraFieldCode 扩展字段编码
     * @return
     */
    public ZeroOrOneEnum gainOtherDictFlag(String extraFieldCode) {
        return Optional.ofNullable(gainOne(extraFieldCode))
                .map(ExtraFieldVo::getOtherDictFlag)
                .orElse(ZeroOrOneEnum.ZERO);
    }

    /**
     * 获取某一个条目扩展字段其它字典项的编码
     *
     * @param extraFieldCode 扩展字段编码
     * @return
     */
    public String gainOtherEntryCode(String extraFieldCode) {
        return Optional.ofNullable(gainOne(extraFieldCode))
                .map(ExtraFieldVo::getExtraFieldCode)
                .orElse(null);
    }


    /**
     * 检查数据是否完全一致
     *
     * @param extraField
     * @return
     */
    public boolean checkSame(ExtraFieldVo extraField) {
        if (ObjUtil.isEmpty(extraField)
                || StrUtil.isEmpty(extraField.getExtraFieldCode())) {
            return false;
        }
        ExtraFieldVo extraFieldVo = gainOne(extraField.getExtraFieldCode());
        if (ObjUtil.isEmpty(extraFieldVo)) {
            return false;
        }
        return checkSame(extraField, extraFieldVo);
    }


    /**
     * 检查数据是否完全一致
     *
     * @param extraField
     * @return
     */
    public boolean checkSame(ExtraFieldVo extraField, ExtraFieldVo extraField2) {
        return Objects.equals(extraField, extraField2)
                && Objects.equals(extraField.getExtraFieldName(), extraField2.getExtraFieldName())
                && Objects.equals(extraField.getOtherDictFlag(), extraField2.getOtherDictFlag())
                && Objects.equals(extraField.getOtherEntryCode(), extraField2.getOtherEntryCode())
                && ObjUtil.compare(extraField.getExtraFieldSort(), extraField2.getExtraFieldSort()) == 0
                ;

    }

    /**
     * 克隆一个对象
     *
     * @return
     */
    public ExtraFieldModel cloneOne() {
        ExtraFieldModel extraFieldModel = new ExtraFieldModel(this.entryCode, this.getExtraFields());
        extraFieldModel.setEntryVersion(this.getEntryVersion());
        return extraFieldModel;
    }


    /**
     * 克隆一个对象
     *
     * @return
     */
    public static ExtraFieldModel cloneOne(String entryCode, ExtraFieldModel extraFieldModel) {
        if (ObjUtil.isEmpty(extraFieldModel)) {
            return new ExtraFieldModel(entryCode, List.of());
        }
        return extraFieldModel.cloneOne();
    }

}
