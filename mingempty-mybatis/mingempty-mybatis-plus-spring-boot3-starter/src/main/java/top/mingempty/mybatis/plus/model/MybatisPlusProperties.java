package top.mingempty.mybatis.plus.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mybatis plus配置文件
 */
@Data
@ConfigurationProperties(prefix = "me.mybatis-plus")
public class MybatisPlusProperties {

    /**
     * 是否开启mybaits plus功能
     */
    private boolean enabled = true;

    /**
     * 是否开启替换表名功能
     */
    private boolean replacedTableName = true;
}
