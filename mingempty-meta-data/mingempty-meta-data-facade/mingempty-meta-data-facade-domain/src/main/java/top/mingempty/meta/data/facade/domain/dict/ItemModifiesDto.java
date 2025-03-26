package top.mingempty.meta.data.facade.domain.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 创建或修改字典项的DTO
 */
@Data
@Schema(title = "创建或修改字典项的DTO")
public class ItemModifiesDto {

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
    private String deleteStatus;

    /**
     * 扩展字段信息
     */
    private Map<String, Object> extraFields;
}
