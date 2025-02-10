package top.mingempty.mybatis.flex.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mybatis flex配置文件
 */
@Data
@ConfigurationProperties(prefix = "me.mybatis-flex")
public class MybatisFlexProperties {

    /**
     * 是否开启mybaits flex功能
     */
    private boolean enabled = true;

    /**
     * 是否开启替换表名功能
     */
    private boolean replacedTableName = true;
}
