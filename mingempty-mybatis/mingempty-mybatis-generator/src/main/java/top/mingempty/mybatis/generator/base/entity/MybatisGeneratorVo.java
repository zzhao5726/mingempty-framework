package top.mingempty.mybatis.generator.base.entity;

import lombok.Data;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.config.ModelType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mybatis Generator 入参
 *
 * @author zzhao
 */
@Data
public class MybatisGeneratorVo {

    /**
     * Mybatis Generator 上下文ID
     */
    private String contextId = "mybatisGenerator";

    /**
     * 作者
     */
    private String author = "zzhao";

    /**
     * 上下文模型类型
     */
    private ModelType contextModelType = ModelType.FLAT;

    /**
     * 上下文目标运行时
     */
    private IntrospectedTable.TargetRuntime contextTargetRuntime = IntrospectedTable.TargetRuntime.MYBATIS3;

    /**
     * 数据库连接驱动
     */
    private String driverClass = "com.mysql.cj.jdbc.Driver";
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
     * 实体类的包名
     */
    private String modelTargetPackage = "com.example.model";
    /**
     * 实体类的位置
     */
    private String modelTargetProject = "src/main/java";

    /**
     * set方法是否trim字符串
     * lombook不生效
     */
    private String trimStrings = "false";

    /**
     * 是否添加Swagger注解
     */
    private String addSwaggerComments = "false";

    /**
     * 是否启用Lombok
     */
    private boolean enableLombok = false;

    /**
     * Mapper接口的包名
     */
    private String clientTargetPackage = "com.example.mapper";
    /**
     * Mapper接口的位置
     */
    private String clientTargetProject = "src/main/java";

    /**
     * Mapper Xml的包名
     */
    private String mapperTargetPackage = "mybatis.mappe";
    /**
     * Mapper Xml的位置
     */
    private String mapperTargetProject = "src/main/resources";

    /**
     * 表名与实体类名映射
     * <p>
     * key: 表名称
     * <br>
     * value: 实体类名称
     * </p>
     * <p>
     * 建议实体类名称以{@code Po}结尾
     * </p>
     */
    private Map<String, String> tableNameMap = new ConcurrentHashMap<>();
    /**
     * 表名与Schema的映射
     * <p>
     * key: 表名称
     * <br>
     * value: Schema
     * </p>
     */
    private Map<String, String> tableSchemaMap = new ConcurrentHashMap<>();


    /**
     * 插件列表
     */
    private List<String> pluginList;

    /**
     * 插件的属性
     */
    private Map<String, Map<String, String>> pluginProperty = new ConcurrentHashMap<>();


}
