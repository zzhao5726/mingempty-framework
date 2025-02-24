package top.mingempty.meta.data.model.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 字典条目表 表定义层。
 *
 * @author zzhao
 * @since 2025-03-20 22:58:54
 */
public class EntryTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典条目表
     */
    public static final EntryTableDef ENTRY_PO = new EntryTableDef();

    /**
     * 条目排序（默认0）
     */
    public final QueryColumn SORT = new QueryColumn(this, "sort");

    /**
     * 条目ID
     */
    public final QueryColumn ENTRY_ID = new QueryColumn(this, "entry_id");

    /**
     * 条目编号
     */
    public final QueryColumn ENTRY_CODE = new QueryColumn(this, "entry_code");

    /**
     * 条目名称
     */
    public final QueryColumn ENTRY_NAME = new QueryColumn(this, "entry_name");

    /**
     * 条目类型
     * <pre class="code">
     * 1：普通字典
     * 2：树形字典
     * (同字典条目编码：dict_entry_type)
     * </pre>
     */
    public final QueryColumn ENTRY_TYPE = new QueryColumn(this, "entry_type");

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
     * 是否分表
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目编码：zero_or_one)
     * </pre>
     */
    public final QueryColumn ENTRY_SHARDING = new QueryColumn(this, "entry_sharding");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ENTRY_ID, ENTRY_CODE, ENTRY_NAME, ENTRY_TYPE, ENTRY_SHARDING, SORT, DELETE_TIME, DELETE_OPERATOR, CREATE_TIME, UPDATE_TIME, CREATE_OPERATOR, UPDATE_OPERATOR};

    public EntryTableDef() {
        super("", "t_meta_data_entry");
    }

    private EntryTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public EntryTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new EntryTableDef("", "t_meta_data_entry", alias));
    }

}
