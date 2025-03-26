package top.mingempty.meta.data.domain.biz.dict.value;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.meta.data.domain.enums.DictOperationEnum;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 条目操作历史数据值对象
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OperationHistoryVo {

    /**
     * 条目版本（默认1）
     */
    private Long entryVersion;

    /**
     * 操作类型
     * <pre class="code">
     * 1.条目基础信息新增或修改
     * 2.字典项新增或修改
     * 3.excel导入
     * 4.内部自定义格式导入
     * 5.版本还原
     * (同字典条目编码：dict_operation_type)
     * </pre>
     */
    private DictOperationEnum operationType;
    /**
     * 还原条目版本
     * <pre class="code">
     * (仅当操作类型为5时才有值)
     * </pre>
     */
    private Long restoreEntryVersion;

    /**
     * 操作人
     */
    private String operatorCode;

    /**
     * 操作时间
     */
    private LocalDateTime operationTime = LocalDateTime.now();

    /**
     * 批次ID
     * <pre class="code">
     * (仅当操作类型为3和4时才有值)
     * </pre>
     */
    private Long batchId;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof OperationHistoryVo that)) return false;
        return Objects.equals(getEntryVersion(), that.getEntryVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEntryVersion());
    }
}
