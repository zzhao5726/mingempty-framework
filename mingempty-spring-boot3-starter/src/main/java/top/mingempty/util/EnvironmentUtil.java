package top.mingempty.util;

import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.Profiles;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.lang.Nullable;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Spring Environment 工具类
 *
 * @author zzhao
 */
public class EnvironmentUtil implements EnvironmentAware {

    private static Environment ENVIRONMENT;

    private static StandardEnvironment STANDARD_ENVIRONMENT;

    /**
     * 所有配置文件的key
     */
    private static final Set<String> keyS = new HashSet<>();

    @Override
    public void setEnvironment(@Nullable Environment environment) {
        ENVIRONMENT = environment;
        if (ENVIRONMENT instanceof StandardEnvironment standardEnvironment) {
            STANDARD_ENVIRONMENT = standardEnvironment;
        }
        initKeyS();
    }


    /**
     * 判断是否有以（@keyPrefix）开头的配置
     *
     * @param keyPrefix
     * @return
     */
    public static Boolean startsWith(String keyPrefix) {
        return keyS.stream().anyMatch(key -> key.startsWith(keyPrefix));
    }

    /**
     * 判断是否有配置（@key）
     *
     * @param key
     * @return
     */
    public static Boolean contains(String key) {
        return keyS.contains(key);
    }

    private static void initKeyS() {
        if (!keyS.isEmpty()) {
            return;
        }
        MutablePropertySources propertySources = gainStandardEnvironment().getPropertySources();
        propertySources.stream().forEach(propertySource -> {
            String name = propertySource.getName();
            if (!name.equals(StandardServletEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME)
                    && !name.equals(StandardServletEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME)) {
                Object o = propertySource.getSource();
                if (o instanceof Map map) {
                    ((Set<Map.Entry>) map.entrySet()).stream().map(Map.Entry::getKey).map(Object::toString).forEach(keyS::add);
                }
            }
        });
    }

    /**
     * 返回给定的属性键是否可用于解析，
     * <p>
     * 例如，如果给定键的值不是{@code null}。
     */
    public static Boolean containsProperty(String key) {
        return gainEnvironment().containsProperty(key);
    }


    /**
     * 返回与给定键相关联的属性值。
     * 无法解析时，则抛出一个IllegalStateException。
     */
    public static String getRequiredProperty(String key) throws IllegalStateException {
        return gainEnvironment().getRequiredProperty(key);
    }

    /**
     * 返回与给定键相关联的属性值，转换为给定的targetType。
     * 无法解析时，则抛出一个IllegalStateException。
     */
    public static <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
        return gainEnvironment().getRequiredProperty(key, targetType);
    }


    /**
     * 返回与给定键关联的属性值，如果键不能解析，则返回{@code null}。
     */
    public static String getProperty(String key) {
        return gainEnvironment().getProperty(key);
    }

    /**
     * 返回与给定键关联的属性值，如果键不能解析，则返回{@code null}。
     */
    public static <T> T getProperty(String key, Class<T> targetType) {
        return gainEnvironment().getProperty(key, targetType);
    }

    /**
     * 返回与给定键关联的属性值，如果键不能解析，则返回{@code defaultValue}。
     */
    public static String getProperty(String key, String defaultValue) {
        return gainEnvironment().getProperty(key, defaultValue);
    }

    /**
     * 返回与给定键关联的属性值，如果键不能解析，则返回{@code defaultValue}。
     */
    public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return gainEnvironment().getProperty(key, targetType, defaultValue);
    }

    /**
     * 解决${… }占位符，用{@link #getProperty}解析的相应属性值替换它们。
     * 没有默认值或者不可解析的，则原样返回。
     */
    public static String resolvePlaceholders(String text) {
        return gainEnvironment().resolvePlaceholders(text);
    }

    /**
     * 解决${… }占位符，用{@link #getProperty}解析的相应属性值替换它们。
     * 没有默认值或者不可解析的，则抛出一个IllegalArgumentException。
     */
    public static String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
        return gainEnvironment().resolveRequiredPlaceholders(text);
    }


    /**
     * 判断配置文件类型
     */
    public static Boolean acceptsProfiles(Profiles profiles) {
        return gainEnvironment().acceptsProfiles(profiles);
    }

    /**
     * 判断配置文件类型
     */
    public static Boolean acceptsProfiles(String... profiles) {
        return gainEnvironment().acceptsProfiles(Profiles.of(profiles));
    }

    /**
     * 获取激活的配置文件
     */
    public static Set<String> getActiveProfiles() {
        return Arrays.stream(gainEnvironment().getActiveProfiles()).collect(Collectors.toSet());
    }

    /**
     * 返回在未显式设置活动配置文件时默认处于活动状态的配置文件集
     */
    public static Set<String> getDefaultProfiles() {
        return Arrays.stream(gainEnvironment().getDefaultProfiles()).collect(Collectors.toSet());
    }


    /**
     * 系统设置名称
     */
    public static String getApplicationName() {
        return getProperty("spring.application.name", getProperty("me.name", ""));
    }


    /**
     * 系统设置分组
     */
    public static String getApplicationGroup() {
        return getProperty("spring.application.group", getProperty("me.group", ""));
    }


    /**
     * 系统设置版本
     *
     * @return
     */
    public static String getApplicationVersion() {
        return getProperty("spring.application.version", getProperty("me.version", ""));
    }

    /**
     * 系统前缀
     */
    public static String getContextPath() {
        return getProperty("spring.webflux.base-path",
                getProperty("server.servlet.context-path",
                        getProperty("me.base-path", "")));
    }

    public static Environment gainEnvironment() {
        return ENVIRONMENT;
    }

    public static StandardEnvironment gainStandardEnvironment() {
        return STANDARD_ENVIRONMENT;
    }
}
