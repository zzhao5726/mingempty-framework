package top.mingempty.mybatis.generator.base.tool;

import org.mybatis.generator.api.IntrospectedTable;

/**
 * 生成工具类
 */
public class BaseGenTool {
    /**
     * 判断是不是Mybatis3运行生成的.
     */
    public static boolean isMybatisMode(IntrospectedTable introspectedTable) {
        return IntrospectedTable.TargetRuntime.MYBATIS3.equals(introspectedTable.getTargetRuntime());
    }
}
