package top.mingempty.mybatis.generator.flex.tool;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.EntityConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.constant.GenTypeConst;
import com.mybatisflex.codegen.dialect.JdbcTypeMapping;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.GeneratorFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import top.mingempty.commons.util.DateTimeUtil;
import top.mingempty.domain.base.BaseDeletePoModel;
import top.mingempty.domain.base.BasePoModel;
import top.mingempty.mybatis.flex.extension.controller.EasyBaseController;
import top.mingempty.mybatis.generator.flex.entity.MfGeneratorVo;

import javax.sql.DataSource;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MybatisFlexGenerator {

    static {
        /**
         * 类型注册
         */
        JdbcTypeMapping.registerMapping(BigInteger.class, Long.class);
        JdbcTypeMapping.registerMapping(Double.class, BigDecimal.class);
        JdbcTypeMapping.registerMapping(Float.class, BigDecimal.class);

        /**
         * 自定义模板生成
         */
        GeneratorFactory.registerGenerator(GenTypeConst.CONTROLLER, new ControllerGenerator());
        GeneratorFactory.registerGenerator(GenTypeConst.ENTITY, new EntityGenerator());
    }

    private static final String TMP_PATH = "/tmp/generator/mybatisflex/";

    public static final String SUB_PATH = "MybatisFlexGenerator";


    public static final String SUB_RESOURCES_PATH = "src/main/resources/";

    public static final String SUB_JAVA_PATH = "src/main/java/";

    public String generator(MfGeneratorVo mfGeneratorVo) {
        DataSource dataSource = DataSourceBuilder.create()
                .url(mfGeneratorVo.getUrl())
                .username(mfGeneratorVo.getUsername())
                .password(mfGeneratorVo.getPassword())
                .build();

        String path = TMP_PATH + UUID.fastUUID() + File.separator;
        String path2 = path + SUB_PATH;
        if (StrUtil.isNotEmpty(mfGeneratorVo.getModulePath())) {
            if (!mfGeneratorVo.getModulePath().startsWith(File.separator)) {
                path2 += File.separator + mfGeneratorVo.getModulePath();
            } else {
                path2 += mfGeneratorVo.getModulePath();
            }
        }
        if (!path2.endsWith(File.separator)) {
            path2 += File.separator;
        }


        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();
        //设置根包
        globalConfig.getPackageConfig()
                .setSourceDir(path2 + SUB_JAVA_PATH)
                .setBasePackage(mfGeneratorVo.getBasePackage())
                .setEntityPackage(mfGeneratorVo.getEntityPackage())
                .setTableDefPackage(mfGeneratorVo.getTableDefPackage())
                .setMapperPackage(mfGeneratorVo.getMapperPackage())
                .setServicePackage(mfGeneratorVo.getServicePackage())
                .setServiceImplPackage(mfGeneratorVo.getServiceImplPackage())
                .setControllerPackage(mfGeneratorVo.getControllerPackage())
                .setMapperXmlPath(path2 + SUB_RESOURCES_PATH + mfGeneratorVo.getXmlPath())
        ;

        //设置表前缀和只生成哪些表，setGenerateTable 未配置时，生成所有表
        Optional.ofNullable(mfGeneratorVo.getTablePrefixList())
                .ifPresent(tablePrefixList -> {
                    globalConfig.getStrategyConfig()
                            .setTablePrefix(tablePrefixList.toArray(new String[0]));
                });

        Optional.ofNullable(mfGeneratorVo.getTableSuffixList())
                .ifPresent(tableSuffixList -> {
                    globalConfig.getStrategyConfig()
                            .setTableSuffix(tableSuffixList.toArray(new String[0]));
                });

        Optional.ofNullable(mfGeneratorVo.getTableNames())
                .ifPresent(tableNames -> {
                    globalConfig.getStrategyConfig()
                            .setGenerateTable(tableNames.toArray(new String[0]));
                });

        Optional.ofNullable(mfGeneratorVo.getExcludeTables())
                .ifPresent(excludeTables -> {
                    globalConfig.getStrategyConfig()
                            .setUnGenerateTable(excludeTables.toArray(new String[0]));
                })
        ;

        globalConfig.getJavadocConfig()
                .setAuthor(mfGeneratorVo.getAuthor())
                .setSince(DateTimeUtil.formatLocalDateTime(LocalDateTime.now()))
        ;

        //设置生成 entity 并启用 Lombok
        globalConfig.enableEntity()
                .setOverwriteEnable(true)
                .setClassSuffix(mfGeneratorVo.getModelClassSuffix())
                .setJdkVersion(mfGeneratorVo.getJdkVersion())
                .setSuperClassFactory(this::supperClass)
                .setAlwaysGenColumnAnnotation(true)
        ;

        if (mfGeneratorVo.isEnableLombok()) {
            globalConfig.enableEntity().setWithLombok(true);
        }

        if (mfGeneratorVo.isEnableSpringdoc()) {
            globalConfig.enableEntity().setWithSwagger(true)
                    .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);
        }

        globalConfig.enableTableDef()
                .setOverwriteEnable(true)
                .setClassSuffix(mfGeneratorVo.getModelTableDefClassSuffix())
        ;

        //设置生成 mapper
        globalConfig.enableMapper()
                .setOverwriteEnable(true)
                .setMapperAnnotation(true)
                .setClassSuffix(mfGeneratorVo.getMapperClassSuffix())
        ;

        globalConfig.enableMapperXml()
                .setOverwriteEnable(true)
                .setFileSuffix(mfGeneratorVo.getMapperXMLFileSuffix())
        ;

        globalConfig.enableService()
                .setOverwriteEnable(true)
                .setClassSuffix(mfGeneratorVo.getServiceClassSuffix())
        ;
        globalConfig.enableServiceImpl()
                .setOverwriteEnable(true)
                .setClassSuffix(mfGeneratorVo.getServiceImplClassSuffix())
        ;
        globalConfig.enableController()
                .setOverwriteEnable(true)
                .setSuperClass(EasyBaseController.class)
                .setClassSuffix(mfGeneratorVo.getControllerClassSuffix())
        ;

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);
        //生成代码
        generator.generate();

        return path;
    }


    /**
     * 设置实体类的父类
     *
     * @param table
     */
    private Class<?> supperClass(Table table) {
        Set<String> columnNames = table.getColumns()
                .parallelStream()
                .map(Column::getName)
                .collect(Collectors.toSet());
        if (columnNames.contains("create_time")
                && columnNames.contains("update_time")
                && columnNames.contains("create_operator")
                && columnNames.contains("update_operator")) {
            table.getGlobalConfig()
                    .setCustomConfig("baseEntity", true);
            if (columnNames.contains("delete_status")
                    && columnNames.contains("delete_time")
                    && columnNames.contains("delete_operator")) {
                table.getGlobalConfig()
                        .enableEntity()
                        .setImplInterfaces(BaseDeletePoModel.class);
                ColumnConfig columnConfig = new ColumnConfig();
                columnConfig.setColumnName("delete_status");
                columnConfig.setLogicDelete(true);
                table.getColumns()
                        .stream().filter(column -> "delete_status".equals(column.getName()))
                        .findFirst()
                        .ifPresent(column -> column.setColumnConfig(columnConfig));
            } else {
                table.getGlobalConfig()
                        .enableEntity()
                        .setImplInterfaces(BasePoModel.class);
            }
        } else {
            table.getGlobalConfig()
                    .setCustomConfig("baseEntity", false);
            table.getGlobalConfig()
                    .enableEntity()
                    .setImplInterfaces(Serializable.class);
        }
        return null;
    }

//    public static void main(String[] args) {
//        MfGeneratorVo mfGeneratorVo = new MfGeneratorVo();
//        mfGeneratorVo.setAuthor("zzhao");
//        mfGeneratorVo.setModulePath("mingempty-mybatis/mingempty-mybatis-flex-spring-boot3-starter");
//        mfGeneratorVo.setUrl("jdbc:mysql://10.30.44.189:3306/meta_data?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true");
//        mfGeneratorVo.setUsername("zzhao");
//        mfGeneratorVo.setPassword("MySQL5726_zzhao");
//        mfGeneratorVo.setEnableSpringdoc(true);
//        mfGeneratorVo.setEnableLombok(true);
//        mfGeneratorVo.setBasePackage("com.example");
//        mfGeneratorVo.setEntityPackage("model");
//        mfGeneratorVo.setTableDefPackage("model.table");
//        mfGeneratorVo.setMapperPackage("mapper");
//        mfGeneratorVo.setServicePackage("service");
//        mfGeneratorVo.setServiceImplPackage("service.impl");
//        mfGeneratorVo.setControllerPackage("controller");
//        mfGeneratorVo.setXmlPath("mybatis/mapper");
//        mfGeneratorVo.setModelClassSuffix("Po");
//        mfGeneratorVo.setJdkVersion(21);
//        mfGeneratorVo.setModelTableDefClassSuffix("TableDef");
//        mfGeneratorVo.setControllerClassSuffix("Controller");
//        mfGeneratorVo.setServiceClassSuffix("Service");
//        mfGeneratorVo.setServiceImplClassSuffix("ServiceImpl");
//        mfGeneratorVo.setMapperClassSuffix("Mapper");
//        mfGeneratorVo.setMapperXMLFileSuffix("Mapper");
//        mfGeneratorVo.setTableNames(Lists.newArrayList());
//        mfGeneratorVo.setTablePrefixList(List.of("t_meta_data", "t_"));
//        mfGeneratorVo.setTableSuffixList(Lists.newArrayList());
//        mfGeneratorVo.setExcludeTables(Lists.newArrayList());
//        MybatisFlexGenerator mybatisFlexGenerator = new MybatisFlexGenerator();
//        mybatisFlexGenerator.generator(mfGeneratorVo);
//    }
}