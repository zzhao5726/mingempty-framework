package top.mingempty.mybatis.generator.flex.tool;

import com.mybatisflex.codegen.config.EntityConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.PackageConfig;
import com.mybatisflex.codegen.constant.TemplateConst;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.IGenerator;
import com.mybatisflex.core.util.StringUtil;
import top.mingempty.mybatis.generator.base.tool.JavaDocTool;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Entity 生成器。
 *
 * @author Michael Yang
 * @author 王帅
 */
public class EntityGenerator implements IGenerator {

    protected String templatePath;

    protected String entityWithBaseTemplatePath = "/templates/enjoy/entityWithBase.tpl";
    protected String ktEntityWithBaseTemplatePath = "/templates/enjoy/entityWithBase.kotlin.tpl";


    public EntityGenerator() {
        this(TemplateConst.ENTITY);
    }

    public EntityGenerator(String templatePath) {
        this.templatePath = templatePath;
    }

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {

        if (!globalConfig.isEntityGenerateEnable()) {
            return;
        }

        // 生成 entity 类
        genEntityClass(table, globalConfig);

        // 生成 base 类
        genBaseClass(table, globalConfig);
    }


    protected void genEntityClass(Table table, GlobalConfig globalConfig) {
        PackageConfig packageConfig = globalConfig.getPackageConfig();
        EntityConfig entityConfig = globalConfig.getEntityConfig();

        String sourceDir = StringUtil.hasText(entityConfig.getSourceDir()) ? entityConfig.getSourceDir() : packageConfig.getSourceDir();

        String entityPackagePath = packageConfig.getEntityPackage().replace(".", "/");
        String entityClassName = table.buildEntityClassName();

        File entityJavaFile = new File(sourceDir, entityPackagePath + "/" + entityClassName + globalConfig.getFileType());

        if (entityJavaFile.exists() && !entityConfig.isOverwriteEnable()) {
            return;
        }
        // 排除忽略列
        if (globalConfig.getStrategyConfig().getIgnoreColumns() != null) {
            table.getColumns().removeIf(column -> globalConfig.getStrategyConfig().getIgnoreColumns().contains(column.getName().toLowerCase()));
        }

        Map<String, Object> params = new HashMap<>(6);
        params.put("table", table);
        params.put("entityPackageName", packageConfig.getEntityPackage());
        params.put("entityConfig", entityConfig);
        params.put("entityClassName", table.buildEntityClassName());
        params.put("packageConfig", packageConfig);
        params.put("javadocConfig", globalConfig.getJavadocConfig());
        params.put("isBase", false);
        springDoc(table, params);
        javaDoc(table, globalConfig);
        params.putAll(globalConfig.getCustomConfig());

        String templatePath = this.templatePath;

        // 开启生成 baseClass
        if (entityConfig.isWithBaseClassEnable()) {
            if (globalConfig.getFileType() == GlobalConfig.FileType.KOTLIN) {
                templatePath = this.ktEntityWithBaseTemplatePath;
            } else {
                templatePath = this.entityWithBaseTemplatePath;
            }

            String baseClassName = table.buildEntityClassName() + entityConfig.getWithBaseClassSuffix();
            params.put("baseClassName", baseClassName);

            String baseClassPackage = StringUtil.hasText(entityConfig.getWithBasePackage())
                    ? entityConfig.getWithBasePackage() : packageConfig.getEntityPackage() + ".base";
            params.put("baseClassPackage", baseClassPackage);

            params.put("entityClassName", table.buildEntityClassName());
        }


        globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, entityJavaFile);

        System.out.println("Entity ---> " + entityJavaFile);
    }

    protected void genBaseClass(Table table, GlobalConfig globalConfig) {
        EntityConfig entityConfig = globalConfig.getEntityConfig();

        // 不需要生成 baseClass
        if (!entityConfig.isWithBaseClassEnable()) {
            return;
        }

        PackageConfig packageConfig = globalConfig.getPackageConfig();
        String sourceDir = StringUtil.hasText(entityConfig.getSourceDir()) ? entityConfig.getSourceDir() : packageConfig.getSourceDir();

        String baseEntityPackagePath = packageConfig.getEntityPackage().replace(".", "/");
        baseEntityPackagePath = StringUtil.hasText(entityConfig.getWithBasePackage()) ? entityConfig.getWithBasePackage().replace(".", "")
                : baseEntityPackagePath + "/base";

        String baseEntityClassName = table.buildEntityClassName() + entityConfig.getWithBaseClassSuffix();

        File baseEntityJavaFile = new File(sourceDir, baseEntityPackagePath + "/" + baseEntityClassName + globalConfig.getFileType());


        // 排除忽略列
        if (globalConfig.getStrategyConfig().getIgnoreColumns() != null) {
            table.getColumns().removeIf(column -> globalConfig.getStrategyConfig().getIgnoreColumns().contains(column.getName().toLowerCase()));
        }

        Map<String, Object> params = new HashMap<>(6);
        params.put("table", table);
        params.put("entityPackageName", baseEntityPackagePath.replace("/", "."));
        params.put("entityClassName", baseEntityClassName);
        params.put("entityConfig", entityConfig);
        params.put("packageConfig", packageConfig);
        params.put("javadocConfig", globalConfig.getJavadocConfig());
        params.put("isBase", true);
        springDoc(table, params);
        javaDoc(table, globalConfig);
        params.putAll(globalConfig.getCustomConfig());

        globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, baseEntityJavaFile);

        System.out.println("BaseEntity ---> " + baseEntityJavaFile);
    }

    /**
     * 处理java doc
     *
     * @param table
     * @param globalConfig
     */
    private static void javaDoc(Table table, GlobalConfig globalConfig) {
        table.getGlobalConfig().setCustomConfig(table.getName(), table.getComment());
        table.setComment(JavaDocTool.generatorClassComment(table.getComment()));
        table.getColumns()
                .parallelStream()
                .forEach(column
                        -> column.setComment(JavaDocTool.generatorFieldComment(column.getComment())));
    }

    /**
     * springDoc
     *
     * @param table
     * @param params
     */
    private static void springDoc(Table table, Map<String, Object> params) {
        if (!table.getEntityConfig()
                .isWithSwagger()) {
            return;
        }
        if (EntityConfig.SwaggerVersion.DOC.equals(table.getEntityConfig().getSwaggerVersion())) {
            params.put("swaggerWithClass", JavaDocTool.generatorSpringDocSchema(table.getComment()));
            Map<String, String> swaggerWithField = table.getColumns()
                    .stream()
                    .collect(Collectors.toMap(Column::getName,
                            column -> JavaDocTool.generatorSpringDocSchema(column.getComment())));
            params.put("swaggerWithField", swaggerWithField);
        } else {
            // TODO 为FOX swagger预留
        }
    }


    @Override
    public String getTemplatePath() {
        return templatePath;
    }

    @Override
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

}
