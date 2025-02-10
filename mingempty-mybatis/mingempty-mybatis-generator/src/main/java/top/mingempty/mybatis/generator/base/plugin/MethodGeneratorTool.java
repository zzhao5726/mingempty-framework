package top.mingempty.mybatis.generator.base.plugin;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.config.Context;

import java.util.Set;
import java.util.TreeSet;

/**
 * 方法生成工具
 *
 * @author zzhao
 */
public class MethodGeneratorTool {
    private final static String BATCH_INSERT = "batchInsert";

    private final static String PARAMETER_NAME = "recordList";

    private final static String DELETE_PARAMETER_NAME = "ids";

    private final static String BATCH_UPDATE = "batchUpdate";

    private final static String BATCH_DELETE = "batchDelete";

    public final static Integer INSERT = 0;

    public final static Integer UPDATE = 1;

    /**
     * java方法生成构造器.
     *
     * @param methodName     the method name
     * @param visibility     the visibility
     * @param returnJavaType the return java type
     * @param parameters     the parameters
     * @author HuWeihui
     * @since hui_project v1
     */
    public static Method methodGenerator(String methodName,
                                         JavaVisibility visibility,
                                         FullyQualifiedJavaType returnJavaType,
                                         Parameter... parameters) {
        Method method = new Method(methodName);
        method.setVisibility(visibility);
        method.setReturnType(returnJavaType);
        for (Parameter parameter : parameters) {
            method.addParameter(parameter);
        }
        return method;
    }

    /**
     * 导入基础的java类型
     *
     * @param introspectedTable the introspected table
     * @return the set
     * @author HuWeihui
     * @since hui_project v1
     */
    public static Set<FullyQualifiedJavaType> importedBaseTypesGenerator(IntrospectedTable introspectedTable) {
        //获取实体类类型
        FullyQualifiedJavaType parameterType = introspectedTable.getRules().calculateAllFieldsClass();
        //@Param需要导入的类型
        FullyQualifiedJavaType paramType = new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param");
        //Integer类型
        FullyQualifiedJavaType intInstance = FullyQualifiedJavaType.getIntInstance();
        //List<Entity>
        FullyQualifiedJavaType listParameterType = FullyQualifiedJavaType.getNewListInstance();

        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        importedTypes.add(parameterType);
        importedTypes.add(intInstance);
        importedTypes.add(paramType);
        importedTypes.add(listParameterType);
        return importedTypes;
    }

    /**
     * 默认的批量新增/更新方法构造器.
     *
     * @param interfaze         the interfaze
     * @param introspectedTable the introspected table
     * @param context           the context
     * @author HuWeihui
     * @since hui_project v1
     */
    public static void defaultBatchInsertOrUpdateMethodGen(Integer type, Interface interfaze, IntrospectedTable introspectedTable, Context context) {
        Set<FullyQualifiedJavaType> importedTypes = MethodGeneratorTool.importedBaseTypesGenerator(introspectedTable);

        //List<Entity>
        FullyQualifiedJavaType listParameterType = FullyQualifiedJavaType.getNewListInstance();
        listParameterType.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());

        String methodName = BATCH_INSERT;
        //1.batchInsert
        if (UPDATE.equals(type)) {
            methodName = BATCH_UPDATE;
        }
        Method insertMethod = MethodGeneratorTool.methodGenerator(methodName,
                JavaVisibility.PUBLIC,
                FullyQualifiedJavaType.getIntInstance(),
                new Parameter(listParameterType, PARAMETER_NAME, "@Param(\"" + PARAMETER_NAME + "\")"));
        insertMethod.setAbstract(true);
        CommentGenerator commentGenerator = context.getCommentGenerator();
        commentGenerator.addGeneralMethodComment(insertMethod, introspectedTable);

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(insertMethod);
    }

    /**
     * 默认的批量删除方法构造器.
     *
     * @param interfaze         the interfaze
     * @param introspectedTable the introspected table
     * @param context           the context
     * @author HuWeihui
     * @since hui_project v1
     */
    public static void defaultBatchDeleteMethodGen(Interface interfaze, IntrospectedTable introspectedTable, Context context) {
        Set<FullyQualifiedJavaType> importedTypes = MethodGeneratorTool.importedBaseTypesGenerator(introspectedTable);
        IntrospectedColumn introspectedColumn = introspectedTable.getPrimaryKeyColumns().get(0);
        FullyQualifiedJavaType paramType = introspectedColumn.getFullyQualifiedJavaType();
        Method batchDeleteMethod = MethodGeneratorTool.methodGenerator(BATCH_DELETE,
                JavaVisibility.PUBLIC,
                FullyQualifiedJavaType.getIntInstance(),
                new Parameter(new FullyQualifiedJavaType("java.util.List<" + paramType.getFullyQualifiedName() + ">"), introspectedColumn.getJavaProperty() + "S", "@Param(\"" + introspectedColumn.getJavaProperty() + "S\")"));
        batchDeleteMethod.setAbstract(true);
        context.getCommentGenerator().addGeneralMethodComment(batchDeleteMethod, introspectedTable);
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(batchDeleteMethod);
    }
}
