package top.mingempty.mybatis.generator.base.tool;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.JavaTypeResolverConfiguration;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.plugins.MapperAnnotationPlugin;
import org.mybatis.generator.plugins.UnmergeableXmlMappersPlugin;
import top.mingempty.mybatis.generator.base.entity.MybatisGeneratorVo;
import top.mingempty.mybatis.generator.base.plugin.BatchDeletePlugin;
import top.mingempty.mybatis.generator.base.plugin.BatchInsertPlugin;
import top.mingempty.mybatis.generator.base.plugin.ColumnCommentGenerator;
import top.mingempty.mybatis.generator.base.plugin.ImplementsBoBasePlugin;
import top.mingempty.mybatis.generator.base.plugin.LombokPlugin;
import top.mingempty.mybatis.generator.base.plugin.MybatisJavaTypeResolver;
import top.mingempty.mybatis.generator.base.plugin.RenameMapperPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
public class MybatisGenerator {

    /**
     * mybatis本地生成路径
     */
//    private static final String TMP_PATH = System.getProperty("java.io.tmpdir") + "generator" + File.separator + "mybatis" + File.separator;
    private static final String TMP_PATH = "/tmp/generator/mybatis/";

    public static final String SUB_PATH = "MybatisGenerator";


    @SneakyThrows
    public String generator(MybatisGeneratorVo mybatisGeneratorVo) {
        String path = TMP_PATH + UUID.fastUUID() + File.separator;

        Configuration config = new Configuration();
        Context context = new Context(mybatisGeneratorVo.getContextModelType());
        context.setId(mybatisGeneratorVo.getContextId());
        context.setTargetRuntime(mybatisGeneratorVo.getContextTargetRuntime().name());

        // 添加自定义插件
        PluginConfiguration implementsBoBasePlugin = new PluginConfiguration();
        implementsBoBasePlugin.setConfigurationType(ImplementsBoBasePlugin.class.getName());
        context.addPluginConfiguration(implementsBoBasePlugin);

        PluginConfiguration mapperAnnotationPlugin = new PluginConfiguration();
        mapperAnnotationPlugin.setConfigurationType(MapperAnnotationPlugin.class.getName());
        context.addPluginConfiguration(mapperAnnotationPlugin);

        PluginConfiguration batchDeletePlugin = new PluginConfiguration();
        batchDeletePlugin.setConfigurationType(BatchDeletePlugin.class.getName());
        context.addPluginConfiguration(batchDeletePlugin);

        PluginConfiguration batchInsertPlugin = new PluginConfiguration();
        batchInsertPlugin.setConfigurationType(BatchInsertPlugin.class.getName());
        context.addPluginConfiguration(batchInsertPlugin);

        PluginConfiguration renameMapperPlugin = new PluginConfiguration();
        renameMapperPlugin.setConfigurationType(RenameMapperPlugin.class.getName());
        context.addPluginConfiguration(renameMapperPlugin);

        PluginConfiguration unmergeableXmlMappersPlugin = new PluginConfiguration();
        unmergeableXmlMappersPlugin.setConfigurationType(UnmergeableXmlMappersPlugin.class.getName());
        context.addPluginConfiguration(unmergeableXmlMappersPlugin);

        if (mybatisGeneratorVo.isEnableLombok()) {
            PluginConfiguration lombokPlugin = new PluginConfiguration();
            lombokPlugin.setConfigurationType(LombokPlugin.class.getName());
            context.addPluginConfiguration(lombokPlugin);
        }
        if (CollUtil.isNotEmpty(mybatisGeneratorVo.getPluginList())) {
            //添加自定义插件
            mybatisGeneratorVo.getPluginList()
                    .forEach(plugin -> {
                        PluginConfiguration pluginConfiguration = new PluginConfiguration();
                        pluginConfiguration.setConfigurationType(plugin);
                        Optional.ofNullable(mybatisGeneratorVo.getPluginProperty())
                                .map(pluginProperty -> pluginProperty.get(plugin))
                                .ifPresent(pluginProperty -> {
                                    pluginProperty.forEach(pluginConfiguration::addProperty);
                                });
                        context.addPluginConfiguration(pluginConfiguration);
                    });
        }

        // 注释
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.setConfigurationType(ColumnCommentGenerator.class.getName());
        commentGeneratorConfiguration.addProperty("suppressAllComments", "false" );
        commentGeneratorConfiguration.addProperty("addRemarkComments", "true" );
        commentGeneratorConfiguration.addProperty("suppressDate", "true" );
        commentGeneratorConfiguration.addProperty("author", mybatisGeneratorVo.getAuthor());
        commentGeneratorConfiguration.addProperty("addSwaggerComments", mybatisGeneratorVo.getAddSwaggerComments());
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);


        // 设置类型
        JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
        javaTypeResolverConfiguration.setConfigurationType(MybatisJavaTypeResolver.class.getName());
        javaTypeResolverConfiguration.addProperty("forceBigDecimals", "false" );
        context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);


        // 设置 JDBC 连接
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setDriverClass(mybatisGeneratorVo.getDriverClass());
        jdbcConnectionConfiguration.setConnectionURL(mybatisGeneratorVo.getUrl());
        jdbcConnectionConfiguration.setUserId(mybatisGeneratorVo.getUsername());
        jdbcConnectionConfiguration.setPassword(mybatisGeneratorVo.getPassword());
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        String path2 = path + SUB_PATH + File.separator;
        // 设置 Java 模型生成器
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        FileUtil.mkdir(path2 + mybatisGeneratorVo.getModelTargetProject());
        javaModelGeneratorConfiguration.setTargetProject(path2 + mybatisGeneratorVo.getModelTargetProject());
        javaModelGeneratorConfiguration.setTargetPackage(mybatisGeneratorVo.getModelTargetPackage());
        javaModelGeneratorConfiguration.addProperty("enableSubPackages", "false" );
        javaModelGeneratorConfiguration.addProperty("trimStrings", mybatisGeneratorVo.getTrimStrings());
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        // 设置 Java 客户端生成器
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        FileUtil.mkdir(path2 + mybatisGeneratorVo.getClientTargetProject());
        javaClientGeneratorConfiguration.setTargetProject(path2 + mybatisGeneratorVo.getClientTargetProject());
        javaClientGeneratorConfiguration.setTargetPackage(mybatisGeneratorVo.getClientTargetPackage());
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER" );
        javaClientGeneratorConfiguration.addProperty("enableSubPackages", "false" );
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        // 设置 SQL 映射生成器
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        FileUtil.mkdir(path2 + mybatisGeneratorVo.getMapperTargetProject());
        sqlMapGeneratorConfiguration.setTargetProject(path2 + mybatisGeneratorVo.getMapperTargetProject());
        sqlMapGeneratorConfiguration.setTargetPackage(mybatisGeneratorVo.getMapperTargetPackage());
        sqlMapGeneratorConfiguration.addProperty("enableSubPackages", "false" );
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        // 设置表配置
        mybatisGeneratorVo.getTableNameMap().forEach((tableName, domainObjectName) -> {
            TableConfiguration tableConfiguration = new TableConfiguration(context);
            tableConfiguration.setTableName(tableName);
            tableConfiguration.setDomainObjectName(domainObjectName);
            Optional.ofNullable(mybatisGeneratorVo.getTableSchemaMap())
                    .map(tableSchemaMap -> tableSchemaMap.get(tableName))
                    .ifPresent(tableConfiguration::setCatalog);
            context.addTableConfiguration(tableConfiguration);
        });

        // 将上下文添加到配置
        config.addContext(context);

        //是否允许覆盖文件
        boolean overwrite = true;
        // 运行 MyBatis Generator
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        List<String> warnings = new ArrayList<>();
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);

        if (CollUtil.isNotEmpty(warnings)) {
            for (int i = 0; i < warnings.size(); i++) {
                log.warn("警告{}：[{}]", i, warnings.get(i));
            }
        }
        return path;
    }

//    public static void main(String[] args) throws Exception {
//        MybatisGeneratorVo mybatisGeneratorVo = new MybatisGeneratorVo();
//        mybatisGeneratorVo.setContextId("mybatisGenerator");
//        mybatisGeneratorVo.setAuthor("zzhao");
//        mybatisGeneratorVo.setContextModelType(ModelType.FLAT);
//        mybatisGeneratorVo.setContextTargetRuntime(IntrospectedTable.TargetRuntime.MYBATIS3);
//        mybatisGeneratorVo.setDriverClass("com.mysql.cj.jdbc.Driver");
//        mybatisGeneratorVo.setUrl("jdbc:mysql://mysql.mingempty.top:3306/user_management?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
//        mybatisGeneratorVo.setUsername("zzhao");
//        mybatisGeneratorVo.setPassword("MySQL5726_zzhao");
//        mybatisGeneratorVo.setModelTargetPackage("top.mingempty.mybatis.test.model");
//        mybatisGeneratorVo.setModelTargetProject("mingempty-mybatis/mingempty-mybatis-spring-boot3-starter/src/test/java");
//        mybatisGeneratorVo.setTrimStrings("false");
//        mybatisGeneratorVo.setAddSwaggerComments("true");
//        mybatisGeneratorVo.setEnableLombok(true);
//        mybatisGeneratorVo.setClientTargetPackage("top.mingempty.mybatis.test.mapper");
//        mybatisGeneratorVo.setClientTargetProject("mingempty-mybatis/mingempty-mybatis-spring-boot3-starter/src/test/java");
//        mybatisGeneratorVo.setMapperTargetPackage("mybatis.mapper");
//        mybatisGeneratorVo.setMapperTargetProject("mingempty-mybatis/mingempty-mybatis-spring-boot3-starter/src/test/resources");
//        Map<String, String> tableNameMap = new HashMap<>(16);
//        tableNameMap.put("aaa_user", "UserPo");
//        mybatisGeneratorVo.setTableNameMap(tableNameMap);
//
//        MybatisGenerator mybatisGenerator = new MybatisGenerator();
//        mybatisGenerator.generator(mybatisGeneratorVo);
//
//    }
}

