package top.mingempty.mybatis.generator.base.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * 实体类实现某个接口
 *
 * @author zzhao
 */
public class ImplementsBoBasePlugin extends PluginAdapter {

    private FullyQualifiedJavaType poBase;
    private FullyQualifiedJavaType poDeleteBase;

    public ImplementsBoBasePlugin() {
        super();
        poBase = new FullyQualifiedJavaType("top.mingempty.domain.base.BasePoModel" );
        poDeleteBase = new FullyQualifiedJavaType("top.mingempty.domain.base.BaseDeletePoModel" );
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (introspectedTable.getColumn("create_time" ).isEmpty()
                || introspectedTable.getColumn("update_time" ).isEmpty()
                || introspectedTable.getColumn("create_operator" ).isEmpty()
                || introspectedTable.getColumn("update_operator" ).isEmpty()) {
            return true;
        }
        if (introspectedTable.getColumn("delete_status" ).isPresent()
                && introspectedTable.getColumn("delete_time" ).isPresent()
                && introspectedTable.getColumn("delete_operator" ).isPresent()) {
            //说明有逻辑删除字段
            topLevelClass.addImportedType(poDeleteBase);
            topLevelClass.addSuperInterface(poDeleteBase);
        } else {
//            topLevelClass.setSuperClass(poBase);

            topLevelClass.addImportedType(poBase);
            topLevelClass.addSuperInterface(poBase);
        }
        return true;
    }

}
