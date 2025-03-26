package top.mingempty.meta.data.domain.biz.dict.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.mingempty.domain.enums.ZeroOrOneEnum;
import top.mingempty.meta.data.domain.enums.DictOperationEnum;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 操作历史查询参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OperationHistoryQuery implements BaseQuery<OperationHistoryQuery> {

    /**
     * 查询字段
     */
    private Collection<String> selectColumns;

    /**
     * 条目编号
     */
    private String entryCode;

    /**
     * 模糊条目编号
     */
    private String entryCodeLike;

    /**
     * 条目编号集合
     */
    private Collection<String> entryCodes;

    /**
     * 条目版本（默认1）
     */
    private Long entryVersion;

    /**
     * 还原条目版本
     * <pre class="code">
     * (仅当操作类型为5时才有值)
     * </pre>
     */
    private Long restoreEntryVersion;

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
     * 操作人
     */
    private String operatorCode;

    /**
     * 操作时间起始
     */
    private LocalDateTime operationTimeByStart;

    /**
     * 操作时间截止
     */
    private LocalDateTime operationTimeByEnd;

    /**
     * 批次ID
     * <pre class="code">
     * (仅当操作类型为3和4时才有值)
     * </pre>
     */
    private Long batchId;

    @Override
    public OperationHistoryQuery setDeleteStatus(ZeroOrOneEnum deleteStatus) {
        throw new UnsupportedOperationException("不支持该方法");
    }

    @Override
    public OperationHistoryQuery setDeleteTime(LocalDateTime deleteTime) {
        throw new UnsupportedOperationException("不支持该方法");
    }

    @Override
    public OperationHistoryQuery setDeleteOperator(String deleteOperator) {
        throw new UnsupportedOperationException("不支持该方法");
    }
}
