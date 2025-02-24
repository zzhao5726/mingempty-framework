package top.mingempty.meta.data.model;

import java.util.Set;

/**
 * 元数据管理常量类
 *
 * @author zzhao
 */
public interface MetaDataConstant {


    /**
     * 默认字典权限校验角色
     */
    Set<String> DEFAUT_AUTHORIZATION_ROLE = Set.of("admin", "dict_admin");
}
