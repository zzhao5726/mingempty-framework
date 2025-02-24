package top.mingempty.meta.data.model.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 条目授权变化流水表 表定义层。
 *
 * @author zzhao
 * @since 2025-03-20 22:58:54
 */
public class ChangeAuthorizationTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 条目授权变化流水表
     */
    public static final ChangeAuthorizationTableDef CHANGE_AUTHORIZATION_PO = new ChangeAuthorizationTableDef();

    /**
     * 条目编号
     */
    public final QueryColumn ENTRY_CODE = new QueryColumn(this, "entry_code");

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
     * 条目授权ID
     */
    public final QueryColumn AUTHORIZATION_ID = new QueryColumn(this, "authorization_id");

    /**
     * 授权编码
     */
    public final QueryColumn AUTHORIZATION_CODE = new QueryColumn(this, "authorization_code");

    /**
     * 授权类型
     * <pre class="code">
     * 1：角色编码
     * 2：用户编码
     * (含义同条目：entry_authorization_type)
     * </pre>
     */
    public final QueryColumn AUTHORIZATION_TYPE = new QueryColumn(this, "authorization_type");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{AUTHORIZATION_ID, ENTRY_CODE, ENTRY_VERSION, AUTHORIZATION_TYPE, AUTHORIZATION_CODE, DELETE_TIME, DELETE_OPERATOR, CREATE_TIME, UPDATE_TIME, CREATE_OPERATOR, UPDATE_OPERATOR};

    public ChangeAuthorizationTableDef() {
        super("", "t_meta_data_change_authorization");
    }

    private ChangeAuthorizationTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ChangeAuthorizationTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ChangeAuthorizationTableDef("", "t_meta_data_change_authorization", alias));
    }

}
