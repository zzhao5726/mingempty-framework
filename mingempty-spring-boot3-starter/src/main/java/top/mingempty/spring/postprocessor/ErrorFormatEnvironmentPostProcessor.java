package top.mingempty.spring.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import top.mingempty.commons.exception.ErrorFormatter;
import top.mingempty.commons.io.UnicodeReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 异常格式化环境配置增强
 *
 * @author zzhao
 */
@Slf4j
public class ErrorFormatEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {


    private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();

    /**
     * Post-process the given {@code environment}.
     *
     * @param environment the environment to post-process
     * @param application the application to which the environment belongs
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Resource[] resources = scanFile();
        Arrays.stream(resources).filter(Objects::nonNull)
                .forEach(resource -> {
                    //获取地理区域位置
                    String errorFormatLocal = resource.getFilename().replace("errorformat", "")
                            .replace(".properties", "");
                    if (errorFormatLocal.startsWith("_")) {
                        errorFormatLocal = errorFormatLocal.substring(1);
                    }
                    Locale locale = Locale.forLanguageTag(errorFormatLocal.replace("_", "-"));
                    try (InputStream inputStream = resource.getInputStream()) {
                        try (UnicodeReader unicodeReader = new UnicodeReader(inputStream, "UTF-8")) {
                            Properties properties = new Properties();
                            properties.load(unicodeReader);
                            ErrorFormatter.getInstance()
                                    .putErrorMap(locale, properties.entrySet()
                                            .parallelStream()
                                            .collect(Collectors.toMap(entry
                                                    -> String.valueOf(entry.getKey()), Map.Entry::getValue)));
                        }
                    } catch (IOException e) {
                        log.error("读取自定义异常配置错误", e);
                    }
                });


    }

    private static Resource[] scanFile() {
        try {
            return RESOURCE_PATTERN_RESOLVER.getResources("classpath*:errorformat/errorformat*.properties");
        } catch (IOException ioException) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ioException);
        }
    }


    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 13;
    }


}
