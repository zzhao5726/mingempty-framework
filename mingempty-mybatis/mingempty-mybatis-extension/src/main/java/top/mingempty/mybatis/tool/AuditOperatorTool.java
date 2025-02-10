package top.mingempty.mybatis.tool;

import lombok.Data;
import lombok.Getter;
import top.mingempty.mybatis.exception.MybatisException;

/**
 * 审计人员工具类
 *
 * @author zzhao
 */
@Getter
@Data
public class AuditOperatorTool {

    private static AuditOperatorTool AUDIT_OPERATOR_TOOL;

    private final String defaultOperator;

    private AuditOperatorTool(String defaultOperator) {
        this.defaultOperator = defaultOperator;
    }

    public static void init(String defaultOperator) {
        AUDIT_OPERATOR_TOOL = new AuditOperatorTool(defaultOperator);
    }

    public static String gainAuditOperator() {
        if (AUDIT_OPERATOR_TOOL == null) {
            throw new MybatisException("mybatis-0000000001");
        }
        // TODO 获取操作用户
        return AUDIT_OPERATOR_TOOL.getDefaultOperator();
    }
}
