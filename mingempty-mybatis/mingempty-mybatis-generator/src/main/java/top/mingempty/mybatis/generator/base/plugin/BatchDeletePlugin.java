package top.mingempty.mybatis.generator.base.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import top.mingempty.mybatis.generator.base.tool.BaseGenTool;

import java.util.List;

/**
 * 批量删除插件
 *
 * @author zzhao
 */
public class BatchDeletePlugin extends PluginAdapter {

    private final static String BATCH_DELETE = "batchDelete";

    private final static String PARAMETER_NAME = "ids";

    @Override
    public boolean validate(List<String> list) {
        return true;
    }


    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        if (BaseGenTool.isMybatisMode(introspectedTable)) {
            MethodGeneratorTool.defaultBatchDeleteMethodGen(interfaze, introspectedTable, context);
        }
        return super.clientGenerated(interfaze, introspectedTable);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        if (BaseGenTool.isMybatisMode(introspectedTable)) {
            addSqlMapper(document, introspectedTable);
        }
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }


    /**
     * 批量删除的xml方法生成
     *
     * @param document
     * @param introspectedTable
     */
    private void addSqlMapper(Document document, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();

        String key = introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName();

        String baseSql = String.format("delete from %s where %s in (", tableName, key);
        IntrospectedColumn introspectedColumn = introspectedTable.getPrimaryKeyColumns().get(0);

        FullyQualifiedJavaType paramType = introspectedColumn.getFullyQualifiedJavaType();

        XmlElement deleteElement = SqlMapperGeneratorTool.baseElementGenerator(SqlMapperGeneratorTool.DELETE, BATCH_DELETE, paramType);

        XmlElement foreachElement = SqlMapperGeneratorTool.baseForeachElementGenerator(introspectedColumn.getJavaProperty().concat("S"), introspectedColumn.getJavaProperty(), "index", null);

        deleteElement.addElement(new TextElement(baseSql));

        foreachElement.addAttribute(new Attribute("separator", ","));

        foreachElement.addElement(new TextElement("#{" + introspectedColumn.getJavaProperty() + "}"));

        deleteElement.addElement(foreachElement);

        deleteElement.addElement(new TextElement(")"));

        document.getRootElement().addElement(deleteElement);
    }
}
