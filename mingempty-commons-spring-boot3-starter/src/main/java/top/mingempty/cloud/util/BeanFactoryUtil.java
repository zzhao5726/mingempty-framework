package top.mingempty.cloud.util;

import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.lang.Nullable;

import java.util.Map;


/**
 * SpringContext工具类
 *
 * @author zzhao
 */
public class BeanFactoryUtil implements BeanFactoryAware {

    @Getter
    private static BeanFactory BEAN_FACTORY;

    @Getter
    private static DefaultListableBeanFactory DEFAULT_LISTABLE_BEAN_FACTORY;

    @Override
    public void setBeanFactory(@Nullable BeanFactory beanFactory) throws BeansException {
        BEAN_FACTORY = beanFactory;
        if (beanFactory instanceof DefaultListableBeanFactory defaultListableBeanFactory) {
            DEFAULT_LISTABLE_BEAN_FACTORY = defaultListableBeanFactory;
        }
    }

    public static <T> void registerSingleton(Class<T> type) {
        T obj = DEFAULT_LISTABLE_BEAN_FACTORY.createBean(type);
        String beanName = beanName(type.getName());
        DEFAULT_LISTABLE_BEAN_FACTORY.registerSingleton(beanName, obj);
    }

    public static void destroy(String className) {
        String beanName = beanName(className);
        DEFAULT_LISTABLE_BEAN_FACTORY.destroySingleton(beanName);
    }

    public static String beanName(String className) {
        String[] path = className.split("\\.");
        String beanName = path[path.length - 1];
        return Character.toLowerCase(beanName.charAt(0)) + beanName.substring(1);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) DEFAULT_LISTABLE_BEAN_FACTORY.getBean(name);
    }

    public static <T> T getBean(Class<T> type) {
        return DEFAULT_LISTABLE_BEAN_FACTORY.getBean(type);
    }

    public static <T> Map<String, T> getBeans(Class<T> type) {
        return DEFAULT_LISTABLE_BEAN_FACTORY.getBeansOfType(type);
    }
}
