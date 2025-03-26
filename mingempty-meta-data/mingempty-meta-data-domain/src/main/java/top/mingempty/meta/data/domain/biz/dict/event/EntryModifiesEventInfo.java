package top.mingempty.meta.data.domain.biz.dict.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;

import java.math.BigDecimal;

/**
 * 字典条目修改信息事件通知内部传输对象
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EntryModifiesEventInfo {

    /**
     * 条目编号
     */
    private String entryCode;


    /**
     * 条目名称
     */
    private String entryName;

    /**
     * 条目排序（默认0）
     */
    private BigDecimal sort;

    /**
     * 是否已逻辑删除
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目：zero_or_one)
     * </pre>
     */
    private ZeroOrOneEnum deleteStatus;
}
