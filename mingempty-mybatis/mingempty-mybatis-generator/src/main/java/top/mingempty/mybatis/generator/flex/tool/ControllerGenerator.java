package top.mingempty.mybatis.generator.flex.tool;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.codegen.config.ControllerConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.PackageConfig;
import com.mybatisflex.codegen.constant.TemplateConst;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.IGenerator;
import com.mybatisflex.core.util.StringUtil;
import top.mingempty.mybatis.generator.base.tool.JavaDocTool;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Controller 生成器。
 *
 * @author 王帅
 * @since 2023-05-14
 */
public class ControllerGenerator implements IGenerator {

    private String templatePath;

    public ControllerGenerator() {
        this(TemplateConst.CONTROLLER);
    }

    public ControllerGenerator(String templatePath) {
        this.templatePath = templatePath;
    }

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {

        if (!globalConfig.isControllerGenerateEnable()) {
            return;
        }

        PackageConfig packageConfig = globalConfig.getPackageConfig();
        ControllerConfig controllerConfig = globalConfig.getControllerConfig();

        String sourceDir = StringUtil.hasText(controllerConfig.getSourceDir()) ? controllerConfig.getSourceDir() : packageConfig.getSourceDir();

        String controllerPackagePath = packageConfig.getControllerPackage().replace(".", "/");
        File controllerJavaFile = new File(sourceDir, controllerPackagePath + "/" +
                table.buildControllerClassName() + globalConfig.getFileType());

        if (controllerJavaFile.exists() && !controllerConfig.isOverwriteEnable()) {
            return;
        }

        Map<String, Object> params = new HashMap<>(4);
        params.put("table", table);
        params.put("packageConfig", packageConfig);
        params.put("controllerConfig", controllerConfig);
        params.put("javadocConfig", globalConfig.getJavadocConfig());
        params.put("withSwagger", globalConfig.isEntityWithSwagger());
        params.put("swaggerVersion", globalConfig.getSwaggerVersion());
        String swaggerWithControllerClass = Optional.ofNullable(table.getGlobalConfig().getCustomConfig(table.getName()))
                .map(comment -> JavaDocTool.generatorSpringDocTag((String) comment))
                .orElse(JavaDocTool.generatorSpringDocTag(table.getComment()));
        params.put("swaggerWithControllerClass", swaggerWithControllerClass);
        params.put("controllerMappingHyphen", StrUtil.toSymbolCase(table.getEntityJavaFileName(), '-').toLowerCase(Locale.ROOT));
        params.putAll(globalConfig.getCustomConfig());
        globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, controllerJavaFile);

        System.out.println("Controller ---> " + controllerJavaFile);
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
