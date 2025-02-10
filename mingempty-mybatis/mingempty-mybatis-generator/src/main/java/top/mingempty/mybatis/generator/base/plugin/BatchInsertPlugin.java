package top.mingempty.mybatis.generator.base.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.GeneratedKey;
import top.mingempty.mybatis.generator.base.tool.BaseGenTool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 批量插入插件
 *
 * @author zzhao
 */
public class BatchInsertPlugin extends PluginAdapter {

    private final static String METHOD_NAME = "batchInsert";

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }


    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        if (BaseGenTool.isMybatisMode(introspectedTable)) {
            MethodGeneratorTool.defaultBatchInsertOrUpdateMethodGen(MethodGeneratorTool.INSERT, interfaze, introspectedTable, context);
        }
        return super.clientGenerated(interfaze, introspectedTable);
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        if (BaseGenTool.isMybatisMode(introspectedTable)) {
            addElements(document.getRootElement(), introspectedTable);
        }
        return super.sqlMapDocumentGenerated(document, introspectedTable);
    }

    /**
     * SQL-Mapping 文件中增加的 id为 insertBatch的批量写入
     * <p>
     * <insert id="addTrainRecordBatch" useGeneratedKeys="true" parameterType=
     * "java.util.List"> <selectKey resultType="long" keyProperty="id" order=
     * "AFTER"> SELECT LAST_INSERT_ID() </selectKey> insert into t_train_record
     * (add_time,emp_id,activity_id,flag) values
     * <foreach collection="list" item="item" index="index" separator="," > (#{
     * item.addTime},#{item.empId},#{item.activityId},#{item.flag}) </foreach>
     * </insert>
     *
     * @param parentElement
     * @param introspectedTable
     */
    private void addElements(XmlElement parentElement, IntrospectedTable introspectedTable) {
        XmlElement answer = new XmlElement("insert");
        answer.addAttribute(new Attribute("id", METHOD_NAME));
        answer.addAttribute(new Attribute("parameterType", "java.util.List"));

        context.getCommentGenerator().addComment(answer);

        introspectedTable.getGeneratedKey()
                .ifPresent(gk -> {
                    introspectedTable.getColumn(gk.getColumn()).ifPresent(introspectedColumn -> {
                        if (gk.isJdbcStandard()) {
                            answer.addAttribute(new Attribute("useGeneratedKeys", "true"));  //$NON-NLS-2$
                            answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));
                        } else {
                            answer.addElement(getSelectKey(introspectedColumn, gk));
                        }
                    });
                });


        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();

        insertClause.append("insert into ");
        insertClause.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        insertClause.append(" (");

        valuesClause.append("values <foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" >\n\t  (");

        // (#{item.addTime},#{item.empId},#{item.activityId},#{item.flag})

        List<String> valuesClauses = new ArrayList<String>();
        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();
            if (introspectedColumn.isIdentity()) {
                // cannot set values on identity fields
                continue;
            }

            insertClause.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));

            // 批量插入,如果是sequence字段,则插入不需要item.前缀
            if (introspectedColumn.isSequenceColumn()) {
                valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            } else {
                valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn, "item."));
            }
            if (iter.hasNext()) {
                insertClause.append(", ");
                valuesClause.append(", ");
            }

            if (valuesClause.length() > 80) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);

                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
        }

        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));

        valuesClause.append(")\n\t  </foreach>");
        valuesClauses.add(valuesClause.toString());

        for (String clause : valuesClauses) {
            answer.addElement(new TextElement(clause));
        }

        parentElement.addElement(answer);
    }

    /**
     * This method should return an XmlElement for the select key used to
     * automatically generate keys.
     *
     * @param introspectedColumn the column related to the select key statement
     * @param generatedKey       the generated key for the current table
     * @return the selectKey element
     */
    protected XmlElement getSelectKey(IntrospectedColumn introspectedColumn, GeneratedKey generatedKey) {
        String identityColumnType = introspectedColumn.getFullyQualifiedJavaType().getFullyQualifiedName();

        XmlElement answer = new XmlElement("selectKey");
        answer.addAttribute(new Attribute("resultType", identityColumnType));
        answer.addAttribute(new Attribute("keyProperty", introspectedColumn.getJavaProperty()));
        answer.addAttribute(new Attribute("order", generatedKey.getMyBatis3Order()));
        answer.addElement(new TextElement(generatedKey.getRuntimeSqlStatement()));

        return answer;
    }
}
