package top.mingempty.cloud.util;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import top.mingempty.commons.util.StringUtil;

import java.beans.Introspector;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Spring ApplicationContext 工具类
 *
 * @author zzhao
 */
public class SpringContextUtil implements BeanFactoryPostProcessor, ApplicationContextAware {

    @Getter
    private static DefaultListableBeanFactory DEFAULT_LISTABLE_BEAN_FACTORY;

    @Getter
    private static ConfigurableListableBeanFactory CONFIGURABLE_LISTABLE_BEAN_FACTORY;

    @Getter
    private static ApplicationContext APPLICATION_CONTEXT;

    @Getter
    private static GenericApplicationContext GENERIC_APPLICATION_CONTEXT;


    @Override
    public void postProcessBeanFactory(@Nullable ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        CONFIGURABLE_LISTABLE_BEAN_FACTORY = configurableListableBeanFactory;
        if (configurableListableBeanFactory instanceof DefaultListableBeanFactory defaultListableBeanFactory) {
            DEFAULT_LISTABLE_BEAN_FACTORY = defaultListableBeanFactory;

        }
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        APPLICATION_CONTEXT = applicationContext;
        if (applicationContext instanceof GenericApplicationContext genericApplicationContext) {
            GENERIC_APPLICATION_CONTEXT = genericApplicationContext;
        }
    }


    public static <T> void registerSingleton(Class<T> type) {
        T obj = DEFAULT_LISTABLE_BEAN_FACTORY.createBean(type);
        String beanName = StringUtil.beanName(type.getName());
        DEFAULT_LISTABLE_BEAN_FACTORY.registerSingleton(beanName, obj);
    }

    public static void destroy(String className) {
        String beanName = StringUtil.beanName(className);
        DEFAULT_LISTABLE_BEAN_FACTORY.destroySingleton(beanName);
    }

    public static <T> T getBean(String beanName) {
        if (APPLICATION_CONTEXT.containsBean(beanName)) {
            return (T) APPLICATION_CONTEXT.getBean(beanName);
        } else {
            return null;
        }
    }

    /**
     * 通过class获取Bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            return APPLICATION_CONTEXT.getBean(clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取默认的bean名称
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> String getDefaultBeanName(Class<T> clazz) {
        String shortClassName = ClassUtils.getShortName(clazz);
        return Introspector.decapitalize(shortClassName);
    }

    /**
     * 获取默认的bean名称
     *
     * @param prefix
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> String getDefaultBeanName(String prefix, Class<T> clazz) {
        return prefix.concat(getDefaultBeanName(clazz));
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> baseType) {
        return APPLICATION_CONTEXT.getBeansOfType(baseType);
    }

    /**
     * 注册bean
     *
     * @param beanName
     * @param beanClass
     * @param supplier
     * @param customizers
     * @param <T>
     */
    @SneakyThrows
    public static <T> void registerBean(@Nullable String beanName, Class<T> beanClass,
                                        @Nullable Supplier<T> supplier, BeanDefinitionCustomizer... customizers) {
        GENERIC_APPLICATION_CONTEXT.registerBean(beanName, beanClass, supplier, customizers);
        // 统一执行
        if (supplier.get() instanceof InitializingBean) {
            ((InitializingBean) supplier.get()).afterPropertiesSet();
        }

    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    public static boolean containsBean(String name) {
        return APPLICATION_CONTEXT.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return APPLICATION_CONTEXT.isSingleton(name);
    }

    public static Class<? extends Object> getType(String name) {
        return APPLICATION_CONTEXT.getType(name);
    }
}