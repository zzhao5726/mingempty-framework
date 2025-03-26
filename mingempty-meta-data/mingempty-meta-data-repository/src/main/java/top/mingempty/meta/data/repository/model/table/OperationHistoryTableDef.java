package top.mingempty.meta.data.repository.model.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 字典操作历史表 表定义层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
public class OperationHistoryTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典操作历史表
     */
    public static final OperationHistoryTableDef OPERATION_HISTORY_PO = new OperationHistoryTableDef();

    /**
     * 批次ID
     * <pre class="code">
     * (使用Excel或Zip导入时有值)
     * </pre>
     */
    public final QueryColumn BATCH_ID = new QueryColumn(this, "batch_id");

    /**
     * 条目编号
     */
    public final QueryColumn ENTRY_CODE = new QueryColumn(this, "entry_code");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 条目版本（默认1）
     */
    public final QueryColumn ENTRY_VERSION = new QueryColumn(this, "entry_version");

    /**
     * 操作人
     */
    public final QueryColumn OPERATOR_CODE = new QueryColumn(this, "operator_code");

    /**
     * 操作时间
     */
    public final QueryColumn OPERATION_TIME = new QueryColumn(this, "operation_time");

    /**
     * 操作类型
     * <pre class="code">
     * 01.条目-新增
     * 02.条目-修改
     * 03.条目-逻辑删除
     * 04.条目权限-新增
     * 05.条目权限-逻辑删除
     * 06.扩展字段-新增
     * 07.扩展字段-修改
     * 08.字典项-新增
     * 09.字典项-修改
     * 10.字典项-逻辑删除
     * 11.导入-excel
     * 12.导入-zip
     * (同字典条目编码：dict_operation_type)
     * </pre>
     */
    public final QueryColumn OPERATION_TYPE = new QueryColumn(this, "operation_type");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_OPERATOR = new QueryColumn(this, "create_operator");

    /**
     * 更新人
     */
    public final QueryColumn UPDATE_OPERATOR = new QueryColumn(this, "update_operator");

    /**
     * 操作历史ID
     */
    public final QueryColumn OPERATION_HISTORY_ID = new QueryColumn(this, "operation_history_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{OPERATION_HISTORY_ID, ENTRY_CODE, OPERATION_TYPE, ENTRY_VERSION, OPERATOR_CODE, OPERATION_TIME, BATCH_ID, CREATE_TIME, UPDATE_TIME, CREATE_OPERATOR, UPDATE_OPERATOR};

    public OperationHistoryTableDef() {
        super("", "t_meta_data_operation_history");
    }

    private OperationHistoryTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public OperationHistoryTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new OperationHistoryTableDef("", "t_meta_data_operation_history", alias));
    }

}
