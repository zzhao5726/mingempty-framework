package top.mingempty.util;

import cn.hutool.core.map.MapUtil;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.lang.Nullable;
import top.mingempty.commons.util.StringUtil;

import java.beans.Introspector;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Spring ApplicationContext 工具类
 *
 * @author zzhao
 */
public class SpringContextUtil implements BeanFactoryPostProcessor, ApplicationContextAware {

    private static DefaultListableBeanFactory DEFAULT_LISTABLE_BEAN_FACTORY;

    private static ConfigurableListableBeanFactory CONFIGURABLE_LISTABLE_BEAN_FACTORY;

    private static ApplicationContext APPLICATION_CONTEXT;

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

    /**
     * 通过一个名称销毁一个bean
     *
     * @param beanClass 类
     */
    public static void destroy(Class<?> beanClass) {
        destroy(beanClass.getName());
    }


    /**
     * 通过一个类名称销毁一个bean
     *
     * @param className 类名称
     */
    public static void destroy(String className) {
        gainDefaultListableBeanFactory().destroySingleton(Introspector.decapitalize(ClassUtils.getShortName(className)));
    }


    /**
     * 通过bean名称获取一个bean
     *
     * @param beanName bean名称
     * @param <T>      bean类型
     * @return 获取的bean
     */
    public static <T> T gainBean(String beanName) {
        if (gainApplicationContext().containsBean(beanName)) {
            return (T) gainApplicationContext().getBean(beanName);
        } else {
            return null;
        }
    }

    /**
     * 通过class获取Bean
     *
     * @param beanClass bean的类
     * @param <T>       bean类型
     * @return 获取的bean
     */
    public static <T> T gainBean(Class<T> beanClass) {
        try {
            return gainApplicationContext().getBean(beanClass);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T gainBean(String name, Class<T> beanClass) {
        return gainApplicationContext().getBean(name, beanClass);
    }


    /**
     * 获取指定类型的所有bean
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> gainBeanMapOfType(Class<T> beanClass) {
        return gainApplicationContext().getBeansOfType(beanClass);
    }

    /**
     * 获取指定类型的所有bean
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> List<T> getBeanListOfType(Class<T> beanClass) {
        Map<String, T> beanMapOfType = gainBeanMapOfType(beanClass);
        if (MapUtil.isEmpty(beanMapOfType)) {
            return List.of();
        }
        return beanMapOfType
                .entrySet()
                .parallelStream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    /**
     * 获取默认的bean名称
     *
     * @param beanClass bean的类
     * @param <T>       bean类型
     * @return 获取的bean
     */
    public static <T> String gainDefaultBeanName(Class<T> beanClass) {
        return Introspector.decapitalize(ClassUtils.getShortName(beanClass));
    }

    /**
     * 获取默认的bean名称并加上前缀
     *
     * @param prefix    前缀
     * @param beanClass bean的类
     * @param <T>       bean类型
     * @return 加上前缀的bean名称
     */
    public static <T> String gainDefaultBeanName(String prefix, Class<T> beanClass) {
        return prefix.concat(gainDefaultBeanName(beanClass));
    }


    /**
     * 通过类注册一个单例bean
     *
     * @param beanClass 类
     * @param <T>       泛型
     */
    public static <T> void registerSingleton(Class<T> beanClass) {
        gainDefaultListableBeanFactory().registerSingleton(StringUtil.beanName(beanClass.getName()),
                gainDefaultListableBeanFactory().createBean(beanClass));
    }


    /**
     * 注册bean
     *
     * @param beanName    bean的名称
     * @param beanClass   bean的类型
     * @param supplier    获取bean的方式
     * @param customizers 自定义
     * @param <T>         bean的泛型
     */
    @SneakyThrows
    public static <T> void registerBean(@Nullable String beanName, Class<T> beanClass,
                                        @Nullable Supplier<T> supplier, BeanDefinitionCustomizer... customizers) {
        gainGenericApplicationContext().registerBean(beanName, beanClass, supplier, customizers);
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext gainApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    public static DefaultListableBeanFactory gainDefaultListableBeanFactory() {
        return DEFAULT_LISTABLE_BEAN_FACTORY;
    }

    public static ConfigurableListableBeanFactory gainConfigurableListableBeanFactory() {
        return CONFIGURABLE_LISTABLE_BEAN_FACTORY;
    }

    public static GenericApplicationContext gainGenericApplicationContext() {
        return GENERIC_APPLICATION_CONTEXT;
    }

    public static boolean containsBean(String name) {
        return gainApplicationContext().containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return gainApplicationContext().isSingleton(name);
    }

    public static Class<?> getType(String name) {
        return gainApplicationContext().getType(name);
    }
}