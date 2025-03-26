package top.mingempty.meta.data.repository.model.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 字典项标签表 表定义层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
public class LabelTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典项标签表
     */
    public static final LabelTableDef LABEL_PO = new LabelTableDef();

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
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_OPERATOR = new QueryColumn(this, "create_operator");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{LABEL_ID, ENTRY_CODE, LABEL_CODE, ITEM_CODE, CREATE_TIME, UPDATE_TIME, CREATE_OPERATOR, UPDATE_OPERATOR};

    public LabelTableDef() {
        super("", "t_meta_data_label");
    }

    private LabelTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public LabelTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new LabelTableDef("", "t_meta_data_label", alias));
    }

}
