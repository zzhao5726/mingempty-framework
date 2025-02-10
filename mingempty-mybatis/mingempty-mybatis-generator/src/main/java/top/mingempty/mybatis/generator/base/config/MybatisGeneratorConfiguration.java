package top.mingempty.mybatis.generator.base.config;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.mybatis.generator.base.entity.MybatisGeneratorVo;
import top.mingempty.mybatis.generator.base.tool.GeneratorZipTool;
import top.mingempty.mybatis.generator.base.tool.MybatisGenerator;

/**
 * mybatis generator配置
 */
@Configuration
@ConditionalOnClass(name = {"org.mybatis.generator.api.MyBatisGenerator"})
public class MybatisGeneratorConfiguration {

    @Bean
    public MybatisGenerator mybatisGenerator() {
        return new MybatisGenerator();
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> generatorMybatisServlet() {
        return new ServletRegistrationBean<>(new HttpServlet() {
            @Override
            @SneakyThrows
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
                // 读取请求体
                MybatisGeneratorVo mybatisGeneratorVo = JsonUtil.DEFAULT_OBJECT_MAPPER.readValue(req.getInputStream(), MybatisGeneratorVo.class);
                // 执行生成逻辑
                String generatorPath = mybatisGenerator().generator(mybatisGeneratorVo);
                GeneratorZipTool.zip(generatorPath, MybatisGenerator.SUB_PATH);
            }
        }, "/mybatis" );
    }
}
