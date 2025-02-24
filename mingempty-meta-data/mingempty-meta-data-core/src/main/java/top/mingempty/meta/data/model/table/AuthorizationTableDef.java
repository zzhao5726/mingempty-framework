package top.mingempty.meta.data.model.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

import java.io.Serial;

/**
 * 条目授权表 表定义层。
 *
 * @author zzhao
 * @since 2025-03-20 22:58:54
 */
public class AuthorizationTableDef extends TableDef {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 条目授权表
     */
    public static final AuthorizationTableDef AUTHORIZATION_PO = new AuthorizationTableDef();

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
     * 创建人
     */
    public final QueryColumn CREATE_OPERATOR = new QueryColumn(this, "create_operator");

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
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{AUTHORIZATION_ID, ENTRY_CODE, AUTHORIZATION_TYPE, AUTHORIZATION_CODE, CREATE_TIME, UPDATE_TIME, CREATE_OPERATOR, UPDATE_OPERATOR};

    public AuthorizationTableDef() {
        super("", "t_meta_data_authorization");
    }

    private AuthorizationTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public AuthorizationTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new AuthorizationTableDef("", "t_meta_data_authorization", alias));
    }

}
