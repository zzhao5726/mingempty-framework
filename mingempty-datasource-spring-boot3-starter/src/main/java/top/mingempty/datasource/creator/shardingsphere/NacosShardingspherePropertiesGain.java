package top.mingempty.datasource.creator.shardingsphere;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import top.mingempty.util.SpringContextUtil;

import java.nio.charset.StandardCharsets;

/**
 * 使用nacos获取ShardingSphere数据源配置
 */
@Slf4j
public class NacosShardingspherePropertiesGain implements ShardingspherePropertiesGain {

    @SneakyThrows
    @Override
    public byte[] gain(String shardingspherePath) {
        NacosConfigManager nacosConfigManager = SpringContextUtil.gainBean(NacosConfigManager.class);
        if (nacosConfigManager != null) {
            ConfigService configService = nacosConfigManager.getConfigService();
            String[] split = shardingspherePath.split(":");
            String dataId;
            String group;
            if (split.length == 2) {
                group = "";
                dataId = split[1];
            } else {
                group = split[1];
                dataId = split[2];
            }
            String config = configService.getConfig(dataId, group, 3000);
            if (StrUtil.isNotEmpty(config)) {
                return config.getBytes(StandardCharsets.UTF_8);
            }
        }
        log.warn("使用nacos获取ShardingSphere数据源配置异常");
        return new byte[0];
    }

    @Override
    public boolean support(String shardingspherePath) {
        return StrUtil.isNotEmpty(shardingspherePath) && shardingspherePath.startsWith("nacos:");
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 1;
    }
}
