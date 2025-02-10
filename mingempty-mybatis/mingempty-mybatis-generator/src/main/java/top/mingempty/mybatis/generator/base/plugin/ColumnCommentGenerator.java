package top.mingempty.mybatis.generator.base.plugin;

import cn.hutool.core.util.StrUtil;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import top.mingempty.commons.util.DateTimeUtil;
import top.mingempty.mybatis.generator.base.tool.JavaDocTool;

import java.time.LocalDateTime;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * 自定义注释生成器
 */
public class ColumnCommentGenerator extends DefaultCommentGenerator {
    /**
     * 隐藏所有注释
     */
    private boolean suppressAllComments = false;
    /**
     * 是否添加数据库备注信息
     */
    private boolean addRemarkComments = true;

    /**
     * 是否添加swagger注解
     */
    private boolean addSwaggerComments = false;


    private String author = "zzhao";
    private static final String EXAMPLE_SUFFIX = "Example";
    private static final String API_PROPERTY_FULL_CLASS_NAME = "io.swagger.v3.oas.annotations.media.Schema";

    /**
     * 设置用户配置的参数
     */
    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        addRemarkComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
        addSwaggerComments = isTrue(properties.getProperty("addSwaggerComments" ));
        this.author = properties.getProperty("author" );
    }

    /**
     * 为模型类添加注释
     *
     * @param topLevelClass     the top level class
     * @param introspectedTable the introspected table
     */
    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }

        topLevelClass.addJavaDocLine("/**" );

        String remarks = introspectedTable.getRemarks();
        if (addRemarkComments && StrUtil.isNotEmpty(remarks)) {
            String comment = JavaDocTool.generatorClassComment(remarks);
            topLevelClass.addJavaDocLine(" * " + comment);
        }
        topLevelClass.addJavaDocLine(" *" );
        topLevelClass.addJavaDocLine(" * @author " + author);
        topLevelClass.addJavaDocLine(" * @date " + DateTimeUtil.formatLocalDateTime(LocalDateTime.now()));
        topLevelClass.addJavaDocLine(" */" );
        //根据参数和备注信息判断是否添加备注信息
        if (addSwaggerComments && StrUtil.isNotEmpty(remarks)) {
            String springDoc = JavaDocTool.generatorSpringDocSchema(remarks);
            topLevelClass.addJavaDocLine(springDoc);
        }
    }

    /**
     * 给model的字段添加注释
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable,
                                IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        if (!addRemarkComments && StrUtil.isEmpty(remarks)) {
            return;
        }
        //文档注释开始
        field.addJavaDocLine("/**" );
        //获取数据库字段的备注信息
        String comment = JavaDocTool.generatorFieldComment(remarks);
        field.addJavaDocLine(" * "+comment);
        field.addJavaDocLine(" */" );
        //数据库中特殊字符需要转义
        remarks = remarks.replaceAll("\"", "'" );
        //根据参数和备注信息判断是否添加备注信息
        if (addSwaggerComments) {
            String springDoc = JavaDocTool.generatorSpringDocSchema(remarks);
            field.addJavaDocLine(springDoc);
        }
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
        //只在model中添加swagger注解类的导入
        if (addSwaggerComments
                && !Interface.class.equals(compilationUnit.getClass())
                && !compilationUnit.getType().getFullyQualifiedName().contains(EXAMPLE_SUFFIX)) {
            compilationUnit.addImportedType(new FullyQualifiedJavaType(API_PROPERTY_FULL_CLASS_NAME));
        }
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }


    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addComment(XmlElement xmlElement) {
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    }
}
