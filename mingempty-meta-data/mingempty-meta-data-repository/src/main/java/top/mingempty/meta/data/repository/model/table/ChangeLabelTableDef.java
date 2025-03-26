package top.mingempty.meta.data.repository.model.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 字典项标签变化流水表 表定义层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
public class ChangeLabelTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典项标签变化流水表
     */
    public static final ChangeLabelTableDef CHANGE_LABEL_PO = new ChangeLabelTableDef();

    /**
     * 字典项标签ID
     */
    public final QueryColumn LABEL_ID = new QueryColumn(this, "label_id");

    /**
     * 字典项编号
     */
    public final QueryColumn ITEM_CODE = new QueryColumn(this, "item_code");

    /**
     * 条目编号
     */
    public final QueryColumn ENTRY_CODE = new QueryColumn(this, "entry_code");

    /**
     * 标签编号
     * <pre class="code">
     * (含义同条目：dict_label)
     * </pre>
     */
    public final QueryColumn LABEL_CODE = new QueryColumn(this, "label_code");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 删除时间
     */
    public final QueryColumn DELETE_TIME = new QueryColumn(this, "delete_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 是否已逻辑删除
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目：zero_or_one)
     * </pre>
     */
    public final QueryColumn DELETE_STATUS = new QueryColumn(this, "delete_status");

    /**
     * 条目版本（默认1）
     */
    public final QueryColumn ENTRY_VERSION = new QueryColumn(this, "entry_version");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_OPERATOR = new QueryColumn(this, "create_operator");

    /**
     * 删除人
     */
    public final QueryColumn DELETE_OPERATOR = new QueryColumn(this, "delete_operator");

    /**
     * 更新人
     */
    public final QueryColumn UPDATE_OPERATOR = new QueryColumn(this, "update_operator");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{LABEL_ID, ENTRY_CODE, ENTRY_VERSION, LABEL_CODE, ITEM_CODE, DELETE_TIME, DELETE_OPERATOR, CREATE_TIME, UPDATE_TIME, CREATE_OPERATOR, UPDATE_OPERATOR};

    public ChangeLabelTableDef() {
        super("", "t_meta_data_change_label");
    }

    private ChangeLabelTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ChangeLabelTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ChangeLabelTableDef("", "t_meta_data_change_label", alias));
    }

}
