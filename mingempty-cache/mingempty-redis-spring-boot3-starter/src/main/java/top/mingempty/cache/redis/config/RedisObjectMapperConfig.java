package top.mingempty.cache.redis.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.jackson2.CoreJackson2Module;
import top.mingempty.commons.util.JacksonUtil;

import java.util.Collections;
import java.util.List;

/**
 * 注入基于Spring-Security配置的ObjectMapper
 *
 * @author zzhao
 */
@ConditionalOnClass(value = {CoreJackson2Module.class})
public class RedisObjectMapperConfig {

    /**
     * ObjectMapper
     *
     * @return
     */
    @Bean(value = "redisObjectMapper")
    @ConditionalOnMissingBean(name = {"redisObjectMapper"})
    @ConditionalOnProperty(prefix = "me.cache", name = "enabled-security", havingValue = "true", matchIfMissing = true)
    public ObjectMapper redisObjectMapperWithSecurity() {
        return JacksonUtil.build(false, JsonInclude.Include.NON_NULL,
                List.of(new CoreJackson2Module()),
                true, Collections.emptyMap());
    }

}
