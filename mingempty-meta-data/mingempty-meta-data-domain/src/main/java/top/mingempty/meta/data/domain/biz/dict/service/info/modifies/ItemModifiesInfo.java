package top.mingempty.meta.data.domain.biz.dict.service.info.modifies;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 字典项内部传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ItemModifiesInfo {

    /**
     * 字典父编号
     */
    private String itemParentCode;

    /**
     * 字典项编号
     */
    private String itemCode;

    /**
     * 字典项名称
     */
    private String itemName;

    /**
     * 字典排序（默认0）
     */
    private BigDecimal itemSort;

    /**
     * 字典层级（默认1）
     */
    private Long itemLevel;

    /**
     * 是否已逻辑删除
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目：zero_or_one)
     * </pre>
     */
    private ZeroOrOneEnum deleteStatus;

    /**
     * 扩展字段信息
     */
    private Map<String, Object> extraFields;


}
