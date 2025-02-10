package top.mingempty.mybatis.generator.plus.tool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.model.AnnotationAttributes;
import com.baomidou.mybatisplus.generator.model.ClassAnnotationAttributes;
import org.apache.ibatis.annotations.Mapper;
import top.mingempty.domain.other.DatePattern;
import top.mingempty.mybatis.generator.base.tool.JavaDocTool;
import top.mingempty.mybatis.generator.plus.entity.MpGeneratorVo;
import top.mingempty.mybatis.plus.extension.controller.EasyBaseController;
import top.mingempty.mybatis.plus.extension.mapper.EasyBaseMapper;
import top.mingempty.mybatis.plus.extension.service.EasyBaseService;
import top.mingempty.mybatis.plus.extension.service.impl.EasyBaseServiceImpl;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MybatisPlusGenerator {

    private static final String TMP_PATH = "/tmp/generator/mybatisplus/";

    public static final String SUB_PATH = "MybatisPlusGenerator";


    public static final String SUB_RESOURCES_PATH = "src/main/resources/";

    public static final String SUB_JAVA_PATH = "src/main/java/";

    public String generator(MpGeneratorVo mpGeneratorVo) {
        String path = TMP_PATH + UUID.fastUUID() + File.separator;
        String path2 = path + SUB_PATH;
        if (StrUtil.isNotEmpty(mpGeneratorVo.getModulePath())) {
            if (!mpGeneratorVo.getModulePath().startsWith(File.separator)) {
                path2 += File.separator + mpGeneratorVo.getModulePath();
            } else {
                path2 += mpGeneratorVo.getModulePath();
            }

        }
        if (!path2.endsWith(File.separator)) {
            path2 += File.separator;
        }
        FileUtil.mkdir(path2);
        DataSourceConfig.Builder builderDataSourceConfig
                = new DataSourceConfig.Builder(mpGeneratorVo.getUrl(),
                mpGeneratorVo.getUsername(),
                mpGeneratorVo.getPassword())
                .typeConvertHandler(new CustomTypeConvertHandler());
        String finalPath = path2;
        FastAutoGenerator.create(builderDataSourceConfig)
                .globalConfig(builder -> builder
                        // 设置作者
                        .author(mpGeneratorVo.getAuthor())
                        // 注释日期格式
                        .commentDate(DatePattern.NORM_DATETIME_PATTERN)
                        // 指定输出目录
                        .outputDir(finalPath + SUB_JAVA_PATH)
                        // 禁止打开输出目录
                        .disableOpenDir()
                )
                .injectionConfig(builder -> builder.beforeOutputFile((config, objectMap) -> {
                    //设置实体类的父类
                    supperClass(config, objectMap);
                    if (mpGeneratorVo.isEnableSpringdoc()) {
                        enableSpringdoc(config, objectMap);
                    }
                    //设置描述
                    commentSetting(config, objectMap);
                }))
                .packageConfig(builder -> builder
                        // 指定父包名
                        .parent(mpGeneratorVo.getParentPackage())
                        // 指定实体包名
                        .entity(mpGeneratorVo.getEntityPackage())
                        // 指定mapper接口包名
                        .mapper(mpGeneratorVo.getMapperPackage())
                        // 指定service接口包名
                        .service(mpGeneratorVo.getServicePackage())
                        // service实现类包名
                        .serviceImpl(mpGeneratorVo.getServiceImplPackage())
                        // 指定控制器包名
                        .controller(mpGeneratorVo.getControllerPackage())
                        // 路径配置信息
                        .pathInfo(Collections.singletonMap(OutputFile.xml, finalPath + SUB_RESOURCES_PATH + mpGeneratorVo.getXmlPath()))
                )
                .strategyConfig(builder -> {
                    //启用 schema
                    builder.enableSchema();
                    // 增加包含的表名
                    Optional.ofNullable(mpGeneratorVo.getTableNames()).ifPresent(builder::addInclude);
                    // 增加过滤表前缀
                    Optional.ofNullable(mpGeneratorVo.getTablePrefixList()).ifPresent(builder::addTablePrefix);
                    // 增加过滤表后缀
                    Optional.ofNullable(mpGeneratorVo.getTableSuffixList()).ifPresent(builder::addTableSuffix);
                    // 增加排除表
                    Optional.ofNullable(mpGeneratorVo.getExcludeList()).ifPresent(builder::addExclude);
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder()
                            //格式化实体文件名称
                            .formatFileName(mpGeneratorVo.getFormatModelFileName())
                            // 开启生成实体时生成字段注解
                            .enableTableFieldAnnotation()
                            // 数据库表映射到实体的命名策略
                            .naming(NamingStrategy.underline_to_camel)
                            // 数据库表字段映射到实体的命名策略
                            .columnNaming(NamingStrategy.underline_to_camel)
                            //覆盖已生成文件
                            .enableFileOverride();

                    if (mpGeneratorVo.isEnableLombok()) {
                        //开启lombok模型
                        builder.entityBuilder().enableLombok();
                    }

                    if (mpGeneratorVo.isEnableChainModel()) {
                        // 开启链式模型
                        builder.entityBuilder().enableChainModel();
                    }
                })
                .strategyConfig(builder -> builder.controllerBuilder()
                        // 开启生成 @RestController 控制器
                        .enableRestStyle()
                        .superClass(EasyBaseController.class)
                        // 格式化controller文件名称
                        .formatFileName(mpGeneratorVo.getFormatControllerFileName())
                        // 开启驼峰转连字符
                        .enableHyphenStyle()
                        //覆盖已生成文件
                        .enableFileOverride()
                )
                .strategyConfig(builder -> builder.serviceBuilder()
                        .superServiceClass(EasyBaseService.class)
                        .superServiceImplClass(EasyBaseServiceImpl.class)
                        // 格式化service接口文件名称
                        .formatServiceFileName(mpGeneratorVo.getFormatServiceFileName())
                        // 格式化service实现类文件名称
                        .formatServiceImplFileName(mpGeneratorVo.getFormatServiceImplFileName())
                        //覆盖已生成文件
                        .enableFileOverride())
                .strategyConfig(builder -> builder.mapperBuilder()
                        .superClass(EasyBaseMapper.class)
                        .mapperAnnotation(Mapper.class)
                        //启用 BaseResultMap 生成
                        .enableBaseResultMap()
                        //启用 BaseColumnList 生成
                        .enableBaseColumnList()
                        // 格式化Mapper文件名称
                        .formatMapperFileName(mpGeneratorVo.getFormatMapperFileName())
                        // 格式化Xml文件名称
                        .formatXmlFileName(mpGeneratorVo.getFormatXmlFileName())
                        //覆盖已生成文件
                        .enableFileOverride())
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute()
        ;
        return path;
    }

    /**
     * 修改 注释
     *
     * @param config
     * @param objectMap
     */
    private void commentSetting(TableInfo config, Map<String, Object> objectMap) {
        if (StrUtil.isNotEmpty(config.getComment())) {
            String comment = JavaDocTool.generatorClassComment(config.getComment());
            config.setComment(comment);
        }

        config.getFields().forEach(fieldInfo -> {
            if (StrUtil.isEmpty(fieldInfo.getComment())) {
                return;
            }
            String comment = JavaDocTool.generatorFieldComment(fieldInfo.getComment());
            fieldInfo.setComment(comment);
        });

    }

    /**
     * 开启 springdoc 模式
     *
     * @param config
     * @param objectMap
     */
    private void enableSpringdoc(TableInfo config, Map<String, Object> objectMap) {
        List<String> importEntityPackages = (List<String>) objectMap.get("importEntityPackages");
        //设置类的注解
        if (StrUtil.isNotEmpty(config.getComment())) {
            String springDoc = JavaDocTool.generatorSpringDocSchema(config.getComment());
            ClassAnnotationAttributes classAnnotationAttributes = new ClassAnnotationAttributes();
            classAnnotationAttributes.addImportPackage("io.swagger.v3.oas.annotations.media.Schema");
            classAnnotationAttributes.setDisplayName(springDoc);
            List<ClassAnnotationAttributes> entityClassAnnotations = (List<ClassAnnotationAttributes>) objectMap.get("entityClassAnnotations");
            entityClassAnnotations.add(classAnnotationAttributes);
            importEntityPackages.add("io.swagger.v3.oas.annotations.media.Schema");
        }

        config.getFields().forEach(fieldInfo -> {
            if (StrUtil.isEmpty(fieldInfo.getComment())) {
                return;
            }
            String springDoc = JavaDocTool.generatorSpringDocSchema(fieldInfo.getComment());
            AnnotationAttributes annotationAttributes = new AnnotationAttributes();
            annotationAttributes.addImportPackage("io.swagger.v3.oas.annotations.media.Schema");
            annotationAttributes.setDisplayName(springDoc);
            fieldInfo.addAnnotationAttributesList(annotationAttributes);
            if (!importEntityPackages.contains("io.swagger.v3.oas.annotations.media.Schema")) {
                importEntityPackages.add("io.swagger.v3.oas.annotations.media.Schema");
            }
        });

        // 添加控制器注解
        objectMap.put("importColtrollerPackages", List.of("io.swagger.v3.oas.annotations.tags.Tag"));
        String springDocTag = JavaDocTool.generatorSpringDocTag(config.getComment());
        objectMap.put("coltrollerClassAnnotations", List.of(springDocTag));


    }

    /**
     * 设置实体类的父类
     *
     * @param config
     * @param objectMap
     */
    private void supperClass(TableInfo config, Map<String, Object> objectMap) {
        List<String> importEntityPackages = (List<String>) objectMap.get("importEntityPackages");
        if (config.getTableFieldMap().containsKey("create_time")
                && config.getTableFieldMap().containsKey("update_time")
                && config.getTableFieldMap().containsKey("create_operator")
                && config.getTableFieldMap().containsKey("update_operator")) {
            config.getFields().removeIf(fieldInfo -> fieldInfo.getName().equals("create_time")
                    || fieldInfo.getName().equals("update_time")
                    || fieldInfo.getName().equals("create_operator")
                    || fieldInfo.getName().equals("update_operator"));
            config.getCommonFields().add(config.getTableFieldMap().get("create_time"));
            config.getCommonFields().add(config.getTableFieldMap().get("update_time"));
            config.getCommonFields().add(config.getTableFieldMap().get("create_operator"));
            config.getCommonFields().add(config.getTableFieldMap().get("update_operator"));
            if (config.getTableFieldMap().containsKey("delete_status")
                    && config.getTableFieldMap().containsKey("delete_time")
                    && config.getTableFieldMap().containsKey("delete_operator")) {
                objectMap.put("superEntityClass", "MpDeleteBasePoModel");
                importEntityPackages.add("top.mingempty.mybatis.plus.extension.entity.MpDeleteBasePoModel");
                config.getFields().removeIf(fieldInfo -> fieldInfo.getName().equals("delete_status")
                        || fieldInfo.getName().equals("delete_time")
                        || fieldInfo.getName().equals("delete_operator"));
                config.getCommonFields().add(config.getTableFieldMap().get("delete_status"));
                config.getCommonFields().add(config.getTableFieldMap().get("delete_time"));
                config.getCommonFields().add(config.getTableFieldMap().get("delete_operator"));
            } else {
                objectMap.put("superEntityClass", "MpBasePoModel");
                importEntityPackages.add("top.mingempty.mybatis.plus.extension.entity.MpBasePoModel");
            }

        }
    }

//    public static void main(String[] args) {
//        MpGeneratorVo mpGeneratorVo = new MpGeneratorVo();
//        mpGeneratorVo.setAuthor("zzhao" );
//        mpGeneratorVo.setModulePath("" );
//        mpGeneratorVo.setUrl("jdbc:mysql://mysql.mingempty.top:3306/user_management?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true" );
//        mpGeneratorVo.setUsername("zzhao" );
//        mpGeneratorVo.setPassword("MySQL5726_zzhao" );
//        mpGeneratorVo.setEnableSpringdoc(true);
//        mpGeneratorVo.setEnableLombok(true);
//        mpGeneratorVo.setEnableChainModel(true);
//        mpGeneratorVo.setParentPackage("com.example" );
//        mpGeneratorVo.setEntityPackage("model" );
//        mpGeneratorVo.setMapperPackage("mapper" );
//        mpGeneratorVo.setServicePackage("service" );
//        mpGeneratorVo.setServiceImplPackage("service.impl" );
//        mpGeneratorVo.setControllerPackage("controller" );
//        mpGeneratorVo.setXmlPath("mybatis/mapper" );
//        mpGeneratorVo.setFormatModelFileName("%sPo" );
//        mpGeneratorVo.setFormatControllerFileName("%sController" );
//        mpGeneratorVo.setFormatServiceFileName("%sService" );
//        mpGeneratorVo.setFormatServiceImplFileName("%sServiceImpl" );
//        mpGeneratorVo.setFormatMapperFileName("%sMapper" );
//        mpGeneratorVo.setFormatXmlFileName("%sMapper" );
//        mpGeneratorVo.setTableNames(Lists.newArrayList());
//        mpGeneratorVo.setTablePrefixList(Lists.newArrayList());
//        mpGeneratorVo.setTableSuffixList(Lists.newArrayList());
//        mpGeneratorVo.setExcludeList(Lists.newArrayList());
//
//        MybatisPlusGenerator mybatisPlusGenerator = new MybatisPlusGenerator();
//        String generator = mybatisPlusGenerator.generator(mpGeneratorVo);
//        System.out.println(generator);
//    }
}