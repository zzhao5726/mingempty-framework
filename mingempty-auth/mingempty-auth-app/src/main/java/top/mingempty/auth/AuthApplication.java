package top.mingempty.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.mingempty.cache.redis.annotation.EnabledRedisCache;
import top.mingempty.jdbc.annotation.EnabledMoreDatasource;

/**
 * 身份验证中心启动类
 *
 * @author zzhao
 * @date 2023/2/27 20:03
 */
@EnabledRedisCache
@EnabledMoreDatasource
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}