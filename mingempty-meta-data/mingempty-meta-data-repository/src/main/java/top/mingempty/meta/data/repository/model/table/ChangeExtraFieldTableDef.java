package top.mingempty.meta.data.repository.model.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 字典扩展字段信息变化流水表 表定义层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
public class ChangeExtraFieldTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典扩展字段信息变化流水表
     */
    public static final ChangeExtraFieldTableDef CHANGE_EXTRA_FIELD_PO = new ChangeExtraFieldTableDef();

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
     * 条目扩展字段关系ID
     */
    public final QueryColumn EXTRA_FIELD_ID = new QueryColumn(this, "extra_field_id");

    /**
     * 是否为其余字典项
     * <pre class="code">
     * 0：否
     * 1：是
     * (同字典条目编码：zero_or_one)
     * </pre>
     */
    public final QueryColumn OTHER_DICT_FLAG = new QueryColumn(this, "other_dict_flag");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_OPERATOR = new QueryColumn(this, "create_operator");

    /**
     * 扩展字段编码
     */
    public final QueryColumn EXTRA_FIELD_CODE = new QueryColumn(this, "extra_field_code");

    /**
     * 扩展字段名称
     */
    public final QueryColumn EXTRA_FIELD_NAME = new QueryColumn(this, "extra_field_name");

    /**
     * 扩展字段排序（默认0）
     */
    public final QueryColumn EXTRA_FIELD_SORT = new QueryColumn(this, "extra_field_sort");

    /**
     * 其余字典项条目编号
     * <pre class="code">
     * (同字典条目编码：entry_code)
     * </pre>
     */
    public final QueryColumn OTHER_ENTRY_CODE = new QueryColumn(this, "other_entry_code");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{EXTRA_FIELD_ID, ENTRY_CODE, ENTRY_VERSION, EXTRA_FIELD_NAME, EXTRA_FIELD_CODE, OTHER_DICT_FLAG, OTHER_ENTRY_CODE, EXTRA_FIELD_SORT, CREATE_TIME, UPDATE_TIME, CREATE_OPERATOR, UPDATE_OPERATOR};

    public ChangeExtraFieldTableDef() {
        super("", "t_meta_data_change_extra_field");
    }

    private ChangeExtraFieldTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ChangeExtraFieldTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ChangeExtraFieldTableDef("", "t_meta_data_change_extra_field", alias));
    }

}
