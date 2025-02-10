package top.mingempty.mybatis.generator.plus.config;

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
import top.mingempty.mybatis.generator.plus.entity.MpGeneratorVo;
import top.mingempty.mybatis.generator.plus.tool.MybatisPlusGenerator;

/**
 * mybatis generator配置
 */
@Configuration
@ConditionalOnClass(name = {"com.baomidou.mybatisplus.generator.FastAutoGenerator", "freemarker.template.Template"})
public class MybatisPlusGeneratorConfiguration {

    @Bean
    public MybatisPlusGenerator mybatisPlusGenerator() {
        return new MybatisPlusGenerator();
    }

    @Bean
    public ServletRegistrationBean<HttpServlet> generatorMybatisPlusServlet() {
        return new ServletRegistrationBean<>(new HttpServlet() {
            @Override
            @SneakyThrows
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
                // 读取请求体
                MpGeneratorVo mpGeneratorVo = JsonUtil.DEFAULT_OBJECT_MAPPER.readValue(req.getInputStream(), MpGeneratorVo.class);
                // 执行生成逻辑
                String generatorPath = mybatisPlusGenerator().generator(mpGeneratorVo);
                GeneratorZipTool.zip(generatorPath, MybatisPlusGenerator.SUB_PATH);
            }
        }, "/mybatisplus" );
    }
}
