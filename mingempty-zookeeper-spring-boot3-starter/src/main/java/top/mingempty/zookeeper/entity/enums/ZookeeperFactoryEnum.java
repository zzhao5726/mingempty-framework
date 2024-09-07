package top.mingempty.zookeeper.entity.enums;

import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.admin.ZooKeeperAdmin;

/**
 * Zookeeper工厂枚举
 *
 * @author zzhao
 */
public enum ZookeeperFactoryEnum {
    /**
     * 使用{@link ZooKeeperAdmin}客户端
     */
    ADMIN,

    /**
     * 使用{@link ZooKeeper}客户端
     */
    NO_ADMIN,
    ;
}
