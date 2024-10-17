package top.mingempty.datasource.creator.shardingsphere;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import top.mingempty.util.SpringContextUtil;

/**
 * 基于path获取ShardingSphere数据源配置
 */
@Slf4j
public class PathShardingspherePropertiesGain implements ShardingspherePropertiesGain {

    @Override
    @SneakyThrows
    public byte[] gain(String shardingspherePath) {
        Resource resource = SpringContextUtil.gainApplicationContext().getResource(shardingspherePath);
        return resource.getContentAsByteArray();
    }

    @Override
    public boolean support(String shardingspherePath) {
        return StrUtil.isNotEmpty(shardingspherePath);
    }
}
