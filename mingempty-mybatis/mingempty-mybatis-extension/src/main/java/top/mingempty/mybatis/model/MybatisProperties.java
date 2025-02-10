package top.mingempty.mybatis.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mybatis配置文件
 */
@Data
@ConfigurationProperties(prefix = "me.mybatis")
public class MybatisProperties {

    /**
     * 是否开启mybaits功能
     */
    private boolean enabled = true;

    /**
     * 是否开启替换表名功能
     */
    private boolean replacedTableName = true;

    /**
     * 是否开启mybatis审计功能
     */
    private boolean auditEnabled = true;

    /**
     * 审计字段默认操作员
     */
    private String auditOperator = "SYS";
}
