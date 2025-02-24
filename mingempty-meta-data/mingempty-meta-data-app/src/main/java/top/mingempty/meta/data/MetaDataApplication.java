package top.mingempty.meta.data;

import org.springframework.boot.SpringApplication;
import top.mingempty.boot.MeCloudApplication;
import top.mingempty.cache.redis.annotation.EnabledRedisCache;
import top.mingempty.datasource.aspect.annotation.EnabledMoreDatasource;
import top.mingempty.openapi.annotation.EnabledOpenApi;
import top.mingempty.zookeeper.annotation.EnabledZookeeper;

/**
 * MetaData启动类
 *
 * @author zzhao
 * @date 2023/2/27 20:03
 */
@MeCloudApplication
@EnabledMoreDatasource
@EnabledOpenApi
@EnabledZookeeper
@EnabledRedisCache
public class MetaDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(MetaDataApplication.class, args);
    }
}