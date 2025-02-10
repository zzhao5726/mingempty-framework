package top.mingempty.mybatis.generator.plus.entity;

import lombok.Data;

import java.util.List;

/**
 * Mybatis Plus Generator 入参
 *
 * @author zzhao
 */
@Data
public class MpGeneratorVo {
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
     * 开启链式模型
     */
    private boolean enableChainModel = false;

    /**
     * 父包名
     * <br>
     * 基于{@link  MpGeneratorVo#modulePath}+src/main/resources/
     */
    private String parentPackage = "com.example";

    /**
     * 实体包名
     */
    private String entityPackage = "model";

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
     * 基于{@link  MpGeneratorVo#modulePath}+src/main/resources/
     */
    private String xmlPath = "mybatis/mapper";

    /**
     * 格式化实体文件名称
     */
    private String formatModelFileName = "%sPo";

    /**
     * 格式化controller文件名称
     */
    private String formatControllerFileName = "%sController";

    /**
     * 格式化service接口文件名称
     */
    private String formatServiceFileName = "%sService";

    /**
     * 格式化service实现类文件名称
     */
    private String formatServiceImplFileName = "%sServiceImpl";

    /**
     * 格式化Mapper文件名称
     */
    private String formatMapperFileName = "%sMapper";

    /**
     * 格式化Xml文件名称
     */
    private String formatXmlFileName = "%sMapper";


    /**
     * 增加包含的表名
     */
    private List<String> tableNames;

    /**
     * 增加过滤表前缀
     */
    private List<String> tablePrefixList;

    /**
     * 增加过滤表后缀
     */
    private List<String> tableSuffixList;

    /**
     * 增加排除表
     */
    private List<String> excludeList;


}
