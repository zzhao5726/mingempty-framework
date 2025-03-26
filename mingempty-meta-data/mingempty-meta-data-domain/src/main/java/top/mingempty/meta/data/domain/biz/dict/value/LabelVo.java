package top.mingempty.meta.data.domain.biz.dict.value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;

import java.util.Objects;
import java.util.Optional;

/**
 * 字典项标签值对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LabelVo {

    /**
     * 标签编号
     * <pre class="code">
     * (含义同条目：dict_label)
     * </pre>
     */
    private String labelCode;

    /**
     * 字典项编号
     */
    private String itemCode;

    /**
     * 条目字典项标签数据逻辑删除状态
     */
    private DeleteVo deleteStatus;


    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LabelVo labelVo)) return false;
        return Objects.equals(getLabelCode(), labelVo.getLabelCode()) && Objects.equals(getItemCode(), labelVo.getItemCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLabelCode(), getItemCode());
    }

    /**
     * 获取条目标签数据值对象的删除状态
     *
     * @return 删除状态
     */
    public ZeroOrOneEnum deleteStatus() {
        return Optional.ofNullable(deleteStatus)
                .map(DeleteVo::getDeleteStatus)
                .orElse(ZeroOrOneEnum.ZERO);
    }

    public LabelVo cloneOne() {
        return LabelVo.builder()
                .labelCode(labelCode)
                .itemCode(itemCode)
                .deleteStatus(Optional.ofNullable(deleteStatus)
                        .map(DeleteVo::cloneOne)
                        .orElse(DeleteVo.builder().deleteStatus(ZeroOrOneEnum.ZERO)
                                .build()))
                .build();
    }

    /**
     * 构件一个基于当前标签的删除副本
     *
     * @return 条目标签数据值对象
     */
    public LabelVo delete() {
        return cloneOne().setDeleteStatus(DeleteVo.builder().deleteStatus(ZeroOrOneEnum.ONE).build());
    }
}
