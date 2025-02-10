package top.mingempty.mybatis.generator.flex.config;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.mingempty.commons.util.JsonUtil;
import top.mingempty.mybatis.generator.base.tool.GeneratorZipTool;
import top.mingempty.mybatis.generator.flex.entity.MfGeneratorVo;
import top.mingempty.mybatis.generator.flex.tool.MybatisFlexGenerator;

/**
 * mybatis generator配置
 */
@Configuration
@ConditionalOnClass(name = {"com.mybatisflex.codegen.Generator"})
public class MybatisFlexGeneratorConfiguration {

    @Bean
    public MybatisFlexGenerator mybatisFlexGenerator() {
        return new MybatisFlexGenerator();
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> generatorMybatisFlexServlet() {
        return new ServletRegistrationBean<>(new HttpServlet() {
            @Override
            @SneakyThrows
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
                // 读取请求体
                MfGeneratorVo mfGeneratorVo = JsonUtil.DEFAULT_OBJECT_MAPPER.readValue(req.getInputStream(), MfGeneratorVo.class);
                // 执行生成逻辑
                String generatorPath = mybatisFlexGenerator().generator(mfGeneratorVo);
                GeneratorZipTool.zip(generatorPath, MybatisFlexGenerator.SUB_PATH);
            }
        }, "/mybatisflex");
    }
}
