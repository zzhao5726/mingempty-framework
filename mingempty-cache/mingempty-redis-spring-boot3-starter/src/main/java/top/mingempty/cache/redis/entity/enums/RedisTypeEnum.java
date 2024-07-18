package top.mingempty.cache.redis.entity.enums;

/**
 * redis配置类型
 */
public enum RedisTypeEnum {
    /**
     * 哨兵
     */
    Sentinel,
    /**
     * 主从复制
     */
    MasterSlave,
    /**
     * 单机
     */
    Single,
    /**
     * 集群
     */
    Cluster,
    /**
     * 云托管
     */
    Replicated,
    ;
}
