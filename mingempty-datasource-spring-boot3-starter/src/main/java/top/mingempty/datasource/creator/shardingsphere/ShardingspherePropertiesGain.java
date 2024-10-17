package top.mingempty.datasource.creator.shardingsphere;

import org.springframework.core.Ordered;

/**
 * 获取ShardingSphere数据源配置
 */
public interface ShardingspherePropertiesGain extends Ordered {

    /**
     * 获取配置的字节数组
     *
     * @param shardingspherePath
     * @return
     */
    byte[] gain(String shardingspherePath);


    /**
     * 当前加载器是否支持根据此属性创建
     *
     * @param shardingspherePath 数据源属性
     * @return 是否支持
     */
    boolean support(String shardingspherePath);


    @Override
    default int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
