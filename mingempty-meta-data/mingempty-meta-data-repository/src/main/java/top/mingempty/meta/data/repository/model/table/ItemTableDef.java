package top.mingempty.meta.data.repository.model.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 字典项表 表定义层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
public class ItemTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典项表
     */
    public static final ItemTableDef ITEM_PO = new ItemTableDef();

    /**
     * 字典ID
     */
    public final QueryColumn ITEM_ID = new QueryColumn(this, "item_id");

    /**
     * 字典项编号
     */
    public final QueryColumn ITEM_CODE = new QueryColumn(this, "item_code");

    /**
     * 字典项名称
     */
    public final QueryColumn ITEM_NAME = new QueryColumn(this, "item_name");

    /**
     * 字典排序（默认0）
     */
    public final QueryColumn ITEM_SORT = new QueryColumn(this, "item_sort");

    /**
     * 条目编号
     */
    public final QueryColumn ENTRY_CODE = new QueryColumn(this, "entry_code");

    /**
     * 字典层级（默认1）
     */
    public final QueryColumn ITEM_LEVEL = new QueryColumn(this, "item_level");

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
     * 创建人
     */
    public final QueryColumn CREATE_OPERATOR = new QueryColumn(this, "create_operator");

    /**
     * 删除人
     */
    public final QueryColumn DELETE_OPERATOR = new QueryColumn(this, "delete_operator");

    /**
     * 扩展字段
     * <pre class="code">
     * (以json格式进行存储)
     * </pre>
     */
    public final QueryColumn ITEM_EXTRA_FIELD = new QueryColumn(this, "item_extra_field");

    /**
     * 字典父编号
     */
    public final QueryColumn ITEM_PARENT_CODE = new QueryColumn(this, "item_parent_code");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ITEM_ID, ENTRY_CODE, ITEM_PARENT_CODE, ITEM_CODE, ITEM_NAME, ITEM_SORT, ITEM_LEVEL, ITEM_EXTRA_FIELD, DELETE_TIME, DELETE_OPERATOR, CREATE_TIME, UPDATE_TIME, CREATE_OPERATOR, UPDATE_OPERATOR};

    public ItemTableDef() {
        super("", "t_meta_data_item");
    }

    private ItemTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ItemTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ItemTableDef("", "t_meta_data_item", alias));
    }

}
