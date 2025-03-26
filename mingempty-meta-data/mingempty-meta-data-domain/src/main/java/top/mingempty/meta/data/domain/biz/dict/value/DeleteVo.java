package top.mingempty.meta.data.domain.biz.dict.value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.mingempty.domain.enums.ZeroOrOneEnum;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 逻辑删除值对象
 *
 * @author zzhao
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteVo {

    /**
     * 是否已逻辑删除
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目：zero_or_one)
     * </pre>
     */
    private ZeroOrOneEnum deleteStatus = ZeroOrOneEnum.ZERO;

    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 删除用户
     */
    private String deleteOperator;

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        DeleteVo that = (DeleteVo) object;
        return getDeleteStatus() == that.getDeleteStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getDeleteStatus());
    }

    public DeleteVo cloneOne() {
        return DeleteVo.builder()
                .deleteStatus(this.deleteStatus)
                .deleteTime(this.deleteTime)
                .deleteOperator(this.deleteOperator)
                .build();
    }
}
