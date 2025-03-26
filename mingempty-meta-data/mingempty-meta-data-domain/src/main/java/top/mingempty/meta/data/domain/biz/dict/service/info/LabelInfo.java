package top.mingempty.meta.data.domain.biz.dict.service.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;

import java.time.LocalDateTime;

/**
 * 字典项标签内部传输对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LabelInfo {

    /**
     * 条目编号
     */
    private String entryCode;

    /**
     * 条目版本（默认1）
     */
    private Long entryVersion;

    /**
     * 标签编号
     * <pre class="code">
     * (含义同条目：dict_label)
     * </pre>
     */
    private String labelCode;

    /**
     * 标签名称
     */
    private String labelName;

    /**
     * 字典项编号
     */
    private String itemCode;

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
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 删除用户
     */
    private String deleteOperator;

}
