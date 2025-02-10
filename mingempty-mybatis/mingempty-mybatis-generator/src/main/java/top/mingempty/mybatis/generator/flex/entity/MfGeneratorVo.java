package top.mingempty.mybatis.generator.flex.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.util.List;

/**
 * Mybatis Flex Generator 入参
 *
 * @author zzhao
 */
@Data
public class MfGeneratorVo {
    /**
     * 作者
     */
    private String author = "zzhao";

    /**
     * 多模块路径
     */
    private String modulePath = "";


    /**
     * 数据库连接Url
     */
    private String url = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
    /**
     * 数据库连接用户
     */
    private String username = "root";
    /**
     * 数据库连接密码
     */
    private String password = "root";

    /**
     * 是否添加Springdoc注解
     */
    private boolean enableSpringdoc = false;

    /**
     * 是否启用Lombok
     */
    private boolean enableLombok = false;

    /**
     * 父包名
     * <br>
     * 基于{@link  MfGeneratorVo#modulePath}+src/main/resources/
     */
    private String basePackage = "com.example";

    /**
     * 实体包名
     */
    private String entityPackage = "model";

    /**
     * 实体包名
     */
    private String tableDefPackage = "model.table";

    /**
     * mapper接口包名
     */
    private String mapperPackage = "mapper";

    /**
     * service接口包名
     */
    private String servicePackage = "service";

    /**
     * service实现类包名
     */
    private String serviceImplPackage = "service.impl";

    /**
     * 控制器包名
     */
    private String controllerPackage = "controller";

    /**
     * 指定xml路径
     * <br>
     * 基于{@link  MfGeneratorVo#modulePath}+src/main/resources/
     */
    private String xmlPath = "mybatis/mapper";

    /**
     * 格式化实体文件名称
     */
    private String modelClassSuffix = "Po";

    /**
     * 设置JDK版本
     */
    private Integer jdkVersion = 21;

    /**
     * 格式化实体 TableDef文件名称
     */
    private String modelTableDefClassSuffix = "TableDef";

    /**
     * 格式化controller文件名称
     */
    private String controllerClassSuffix = "Controller";

    /**
     * 格式化service接口文件名称
     */
    private String serviceClassSuffix = "Service";

    /**
     * 格式化service实现类文件名称
     */
    private String serviceImplClassSuffix = "ServiceImpl";

    /**
     * 格式化Mapper文件名称
     */
    private String mapperClassSuffix = "Mapper";

    /**
     * 格式化Xml文件名称
     */
    private String mapperXMLFileSuffix = "Mapper";


    /**
     * 增加包含的表名
     */
    private List<String> tableNames;

    /**
     * 表前缀
     */
    private List<String> tablePrefixList;

    /**
     * 表后缀
     */
    private List<String> tableSuffixList;

    /**
     * 增加排除表
     */
    private List<String> excludeTables;


    public String getEntityPackage() {
        if (StrUtil.isNotEmpty(this.getBasePackage())) {
            return this.getBasePackage() + "." + this.entityPackage;
        }
        return entityPackage;
    }

    public String getTableDefPackage() {
        if (StrUtil.isNotEmpty(this.getBasePackage())) {
            return this.getBasePackage() + "." + this.tableDefPackage;
        }
        return tableDefPackage;
    }

    public String getMapperPackage() {
        if (StrUtil.isNotEmpty(this.getBasePackage())) {
            return this.getBasePackage() + "." + this.mapperPackage;
        }
        return mapperPackage;
    }

    public String getServicePackage() {
        if (StrUtil.isNotEmpty(this.getBasePackage())) {
            return this.getBasePackage() + "." + this.servicePackage;
        }
        return servicePackage;
    }

    public String getServiceImplPackage() {
        if (StrUtil.isNotEmpty(this.getBasePackage())) {
            return this.getBasePackage() + "." + this.serviceImplPackage;
        }
        return serviceImplPackage;
    }

    public String getControllerPackage() {
        if (StrUtil.isNotEmpty(this.getBasePackage())) {
            return this.getBasePackage() + "." + this.controllerPackage;
        }
        return controllerPackage;
    }
}
