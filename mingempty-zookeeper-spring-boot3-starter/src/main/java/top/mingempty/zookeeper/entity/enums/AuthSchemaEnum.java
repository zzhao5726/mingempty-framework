package top.mingempty.zookeeper.entity.enums;

import lombok.Getter;

/**
 * 权限类型枚举
 *
 * @author zzhao
 */
@Getter
public enum AuthSchemaEnum {
    WORLD("world", "表示任何客户端都不需要认证即可访问"),
    AUTH("auth", "表示已经通过 addauth 命令认证过的客户"),
    DIGEST("digest", "基于用户名和密码的哈希值进行认证"),
    IP("ip", "根据客户端 IP 地址进行认证"),
    SUPER("super", "超级权限，通常指服务器本身"),
    ;
    private final String scheme;

    private final String desc;

    AuthSchemaEnum(String scheme, String desc) {
        this.scheme = scheme;
        this.desc = desc;
    }

}
