package top.mingempty.cloud.util;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 切面工具类
 * @author zzhao
 */
@Slf4j
public class AspectUtil {


    /**
     * 获取一个代理实例的真实对象
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    public static Object getTarget(Object proxy) throws Exception {
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            // JDK动态代理
            return getJdkDynamicProxyTargetObject(proxy);
        } else {
            // cglib动态代理
            return getCglibProxyTargetObject(proxy);
        }
    }

    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
    }

    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        AopProxy aopProxy = (AopProxy) h.get(proxy);
        Field advised = aopProxy.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        return ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
    }

    /**
     * 获取代理实例的真实对象的类
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    public static Class<?> findTargetClass(Object proxy) throws Exception {
        if (AopUtils.isAopProxy(proxy)) {
            AdvisedSupport advised = getAdvisedSupport(proxy);
            Object target = advised.getTargetSource().getTarget();
            return findTargetClass(target);
        } else {
            return proxy == null ? null : proxy.getClass();
        }
    }


    /**
     * 获取代理对象的切面包装,里面有
     * List<Advisor> advisors  所有的advisor
     * TargetSource targetSource   目标实例通过getTarget()方法获取
     *
     * @param proxy
     * @return
     * @throws Exception
     */
    public static AdvisedSupport getAdvisedSupport(Object proxy) throws Exception {
        Field h;
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            h = proxy.getClass().getSuperclass().getDeclaredField("h");
        } else {
            h = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        }
        h.setAccessible(true);
        Object dynamicAdvisedInterceptor = h.get(proxy);
        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);
        return (AdvisedSupport) advised.get(dynamicAdvisedInterceptor);
    }


    /**
     * 获取代理对象
     *
     * @param joinPoint
     * @return
     */
    public static Class getClass(ProceedingJoinPoint joinPoint) {
        Object target = joinPoint.getTarget();

        if (!Proxy.isProxyClass(target.getClass())) {
            return target.getClass();
        }

        if (!AopUtils.isAopProxy(target)) {
            Type[] genericInterfaces = target.getClass().getGenericInterfaces();
            try {
                return Class.forName(genericInterfaces[0].getTypeName());
            } catch (ClassNotFoundException e) {
                log.warn("没有找到类[{}]", genericInterfaces[0].getTypeName());
                return null;
            }
        }
        return target.getClass();
    }

    /**
     * 获取对象
     *
     * @param method
     * @return
     */
    public static Class getClass(Method method) {
        return method.getDeclaringClass();
    }

    /**
     * 获取代理方法
     *
     * @param joinPoint
     * @return
     */
    public static Method getMethod(ProceedingJoinPoint joinPoint) {
        Method method = null;
        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature methodSignature)) {
            log.warn("该注解只能用于方法");
            return null;
        }
        try {
            method = joinPoint.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        } catch (NoSuchMethodException e) {
            log.error("切面异常，无法获取切面方法", e);
            return null;
        }
        if (ObjectUtils.isEmpty(methodSignature)) {
            log.warn("切面异常，无法获取切面方法");
            return null;
        }
        return method;
    }

    /**
     * 获取类和方法
     *
     * @param joinPoint
     * @return
     */
    public static Map<String, Object> getClassAndMethod(ProceedingJoinPoint joinPoint) {
        Map<String, Object> map = new ConcurrentHashMap<>();
        Method method = getMethod(joinPoint);
        map.put("method", method);
        Class aClass = getClass(joinPoint);
        map.put("aClass", aClass);
        return map;
    }

}
