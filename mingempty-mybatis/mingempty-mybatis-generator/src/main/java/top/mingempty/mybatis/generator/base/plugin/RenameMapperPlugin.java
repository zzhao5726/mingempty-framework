package top.mingempty.mybatis.generator.base.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

/**
 * 重命名DOMapper.xml 改为 Mapper.xml; DOMapper.java 改为 DAO.java
 *
 * @author zzhao
 */
public class RenameMapperPlugin extends PluginAdapter {
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String oldXmlMapperFile = introspectedTable.getMyBatis3XmlMapperFileName();
        String newXmlMapperFile = oldXmlMapperFile.replace("PoMapper", "Mapper");
        introspectedTable.setMyBatis3XmlMapperFileName(newXmlMapperFile);

        String  oldExampleType = introspectedTable.getExampleType();
        String newExampleType = oldExampleType.replace("PoExample", "Example");
        introspectedTable.setExampleType(newExampleType);

        String oldJavaMapperType = introspectedTable.getMyBatis3JavaMapperType();
        String newJavaMapperType = oldJavaMapperType.substring(0, oldJavaMapperType.lastIndexOf("PoMapper")) + "Mapper";
        introspectedTable.setMyBatis3JavaMapperType(newJavaMapperType);
    }
}