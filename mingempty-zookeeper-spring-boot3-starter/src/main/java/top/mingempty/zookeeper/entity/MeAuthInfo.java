package top.mingempty.zookeeper.entity;

import org.apache.curator.framework.AuthInfo;
import top.mingempty.zookeeper.entity.enums.AuthSchemaEnum;

/**
 *
 * @author zzhao
 */
public class MeAuthInfo {
    final AuthSchemaEnum scheme;
    final String auth;

    public MeAuthInfo( AuthSchemaEnum scheme, String auth) {
        this.scheme = scheme;
        this.auth = auth;
    }

    public AuthInfo generateAuthInfo() {
        return new AuthInfo(scheme.getScheme(), auth.getBytes());
    }
}