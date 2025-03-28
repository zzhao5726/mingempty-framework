package top.mingempty.commons.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.domain.function.SerializableFunction;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 反射工具类
 *
 * @author zzhao
 */
@Slf4j
public class ReflectionUtil {

    /**
     * 缓存
     */
    private static final Map<SerializableFunction<?, ?>, Field> SERIALIZABLE_FUNCTION_CACHE = new ConcurrentHashMap<>();
    /**
     * 类字段缓存
     */
    private static final Map<String, List<Field>> DECLARED_FIELDS_CACHE = new ConcurrentHashMap<>();
    /**
     * 类方法缓存
     */
    private static final Map<String, List<Method>> DECLARED_METHOD_CACHE = new ConcurrentHashMap<>();


    /**
     * 获取某个字段的下划线名称
     *
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> String getUnderlineCase(SerializableFunction<T, R> function) {
        return StrUtil.toUnderlineCase(getFieldName(function));
    }

    /**
     * 获取某个对象的字段名称
     *
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> String getFieldName(SerializableFunction<T, R> function) {
        Field field = ReflectionUtil.getField(function);
        return field.getName();
    }

    /**
     * 获取某个对象字段的field
     *
     * @param function
     * @return
     */
    public static <T, R> Field getField(SerializableFunction<T, R> function) {
        return SERIALIZABLE_FUNCTION_CACHE.computeIfAbsent(function, ReflectionUtil::findField);
    }


    /**
     * 获取该Field
     *
     * @param function
     * @return
     */
    public static Field findField(SerializableFunction<?, ?> function) {
        String fieldName = null;
        try {
            // 第1步 获取SerializedLambda
            Method method = function.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(function);
            // 第2步 implMethodName 即为Field对应的Getter方法名
            String implMethodName = serializedLambda.getImplMethodName();
            if (implMethodName.startsWith("get") && implMethodName.length() > 3) {
                fieldName = Introspector.decapitalize(implMethodName.substring(3));

            } else if (implMethodName.startsWith("is") && implMethodName.length() > 2) {
                fieldName = Introspector.decapitalize(implMethodName.substring(2));
            } else if (implMethodName.startsWith("lambda$")) {
                throw new IllegalArgumentException("SerializableFunction不能传递lambda表达式,只能使用方法引用");

            } else {
                throw new IllegalArgumentException(implMethodName + "不是Getter方法引用");
            }
            // 第3步 获取的Class是字符串，并且包名是“/”分割，需要替换成“.”，才能获取到对应的Class对象
            String declaredClass = serializedLambda.getImplClass().replace("/", ".");
            Class<?> aClass = Class.forName(declaredClass);
            log.debug("找到的类名称为：[{}]，字段名称为：[{}]", aClass.getName(), fieldName);
            // 第4步 反射工具类获取Class中定义的Field
            return findField(aClass, fieldName);
        } catch (Exception e) {
            log.error("通过方法获取Java字段异常，异常原因为:", e);
        }
        // 第5步 如果没有找到对应的字段应该抛出异常
        throw new NoSuchFieldError(fieldName);
    }

    /**
     * 尝试使用提供的{@code name}在提供的{@link Class}上找到一个{@link Field Field}。 搜索所有超类直到{@link Object}。
     *
     * @param clazz 自省类
     * @param name  字段的名称(如果指定type，可以是{@code null})
     * @return 对应的Field对象，或者如果没有找到{@code null}
     */
    public static Field findField(Class<?> clazz, String name) {
        return findField(clazz, name, null);
    }


    /**
     * 尝试使用提供的{@code name}和/或{@link Class type}在提供的{@link Class}上找到一个{@link Field Field}。 搜索所有超类直到{@link Object}。
     *
     * @param clazz 自省类
     * @param name  字段的名称(如果指定type，可以是{@code null})
     * @param type  字段的类型(如果指定了name，可以是{@code null})
     * @return 对应的Field对象，或者如果没有找到{@code null}
     */
    public static Field findField(Class<?> clazz, String name, Class<?> type) {
        Assert.notNull(clazz, "Class must not be null");
        Assert.isTrue(name != null || type != null, "Either name or type of the field must be specified");
        return getDeclaredFieldsAll(clazz).parallelStream()
                .filter(field -> (name == null || name.equals(field.getName())) &&
                        (type == null || type.equals(field.getType())))
                .findFirst()
                .orElse(null);
    }

    /**
     * 这个变体从本地缓存中检索{@link Class#getDeclaredFields()}，以避免JVM的SecurityManager检查和防御数组复制。
     *
     * @param clazz 自省类
     * @return 缓存的字段数组
     */
    public static List<Field> getDeclaredFields(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return DECLARED_FIELDS_CACHE.computeIfAbsent(clazz.getName(), key -> {
            Field[] result = clazz.getDeclaredFields();
            return Arrays.asList(result);
        });
    }

    /**
     * 这个变体从本地缓存中检索{@link Class#getDeclaredFields()}，以避免JVM的SecurityManager检查和防御数组复制。
     *
     * @param clazz 自省类
     * @return 缓存的字段数组
     */
    public static List<Field> getDeclaredFieldsAll(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");

        List<Field> fields = DECLARED_FIELDS_CACHE.get(clazz.getName());
        if (CollUtil.isNotEmpty(fields)) {
            return fields;
        }
        synchronized (clazz) {
            fields = DECLARED_FIELDS_CACHE.get(clazz.getName());
            if (CollUtil.isNotEmpty(fields)) {
                return fields;
            }
            List<Field> fieldList = new ArrayList<>();
            Class<?> searchType = clazz;
            while (Object.class != searchType
                    && Enum.class != searchType
                    && searchType != null) {
                fieldList.addAll(getDeclaredFields(searchType));
                searchType = searchType.getSuperclass();
            }
            fieldList.parallelStream()
                    .forEach(field
                            -> field.setAccessible(true));
            DECLARED_FIELDS_CACHE.put(clazz.getName(), fieldList);
            return fieldList;
        }
    }

    /**
     * 获取类的指定方法
     *
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        return getDeclaredMethodAll(clazz)
                .parallelStream()
                .filter(method -> method.getName().equals(methodName))
                .filter(method -> method.getParameterCount() == parameterTypes.length)
                .filter(method -> {
                    if (parameterTypes.length == 0) {
                        return true;
                    }

                    for (int i = 0; i < parameterTypes.length; i++) {
                        if (method.getParameterTypes()[i].equals(parameterTypes[i])) {
                            return true;
                        }
                    }
                    return false;
                })
                .findFirst()
                .orElse(null);
    }


    /**
     * 这个变体从本地缓存中检索{@link Class#getDeclaredMethods()}，以避免JVM的SecurityManager检查和防御数组复制。
     *
     * @param clazz 自省类
     * @return 缓存的字段数组
     */
    public static List<Method> getDeclaredMethods(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return DECLARED_METHOD_CACHE.computeIfAbsent(clazz.getName(), key -> {
            Method[] result = clazz.getDeclaredMethods();
            return Arrays.asList(result);
        });
    }

    /**
     * 这个变体从本地缓存中检索{@link Class#getDeclaredMethods()}，以避免JVM的SecurityManager检查和防御数组复制。
     *
     * @param clazz 自省类
     * @return 缓存的字段数组
     */
    public static List<Method> getDeclaredMethodAll(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");

        List<Method> methods = DECLARED_METHOD_CACHE.get(clazz.getName());
        if (CollUtil.isNotEmpty(methods)) {
            return methods;
        }
        synchronized (clazz) {
            methods = DECLARED_METHOD_CACHE.get(clazz.getName());
            if (CollUtil.isNotEmpty(methods)) {
                return methods;
            }
            List<Method> methodList = new ArrayList<>();
            Class<?> searchType = clazz;
            while (Object.class != searchType
                    && Enum.class != searchType
                    && searchType != null) {
                methodList.addAll(getDeclaredMethods(searchType));
                searchType = searchType.getSuperclass();
            }
            DECLARED_METHOD_CACHE.put(clazz.getName(), methodList);
            return methodList;
        }
    }


    /**
     * 获取对应字段的值
     *
     * @param fieldName 字段
     * @param obj       对象
     * @return
     */
    public static <T, V> V getValue(String fieldName, T obj) {
        Assert.notNull(fieldName, "fieldName must not be null");
        Assert.notNull(obj, "obj must not be null");

        try {
            List<Field> declaredFieldsAll = ReflectionUtil.getDeclaredFieldsAll(obj.getClass());
            if (CollUtil.isNotEmpty(declaredFieldsAll)) {
                for (Field field : declaredFieldsAll) {
                    if (fieldName.equals(field.getName())) {
                        return (V) field.get(obj);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            log.error("根据字段名获取属性值失败", e);
        }
        return null;
    }


    /**
     * 设置对应字段的值
     *
     * @param fieldName 字段
     * @param obj       对象
     * @param v         对象的值
     */
    public static <T, V> void setValue(String fieldName, T obj, V v) {
        Assert.notNull(fieldName, "fieldName must not be null");
        Assert.notNull(obj, "obj must not be null");
        try {
            List<Field> declaredFieldsAll = ReflectionUtil.getDeclaredFieldsAll(obj.getClass());
            if (CollUtil.isNotEmpty(declaredFieldsAll)) {
                for (Field field : declaredFieldsAll) {
                    if (fieldName.equals(field.getName())) {
                        field.setAccessible(true);
                        field.set(obj, v);
                        return;
                    }
                }
            }
        } catch (IllegalAccessException e) {
            log.error("根据字段名修改属性值失败", e);
        }
    }

    public static Class<?> classForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("通过类名称[{}]加载类异常", className, e);
            return null;
        }
    }

    /**
     * 获得超类的泛型参数类型，取第一个参数类型
     *
     * @param clazz 超类类型
     */
    @SuppressWarnings("rawtypes")
    public static Class getClassGenericSuperclassType(final Class clazz) {
        return getClassGenericSuperclassType(clazz, 0);
    }

    /**
     * 获得超类的泛型参数类型
     *
     * @param clazz 超类类型
     * @param index 索引
     */
    @SuppressWarnings("rawtypes")
    public static Class getClassGenericSuperclassType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            log.warn(String.format("Warn: %s's superclass not ParameterizedType", clazz.getSimpleName()));
            return null;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index <= params.length && index >= 0) {
            if ((params[index] instanceof Class)) {
                return (Class) params[index];
            } else if (ParameterizedType.class.isAssignableFrom(params[index].getClass())) {
                ParameterizedType parameterizedType = (ParameterizedType) params[index];
                Type rawType = parameterizedType.getRawType();
                if ((rawType instanceof Class)) {
                    return (Class) rawType;
                }
            }
            log.warn(String.format("Warn: %s not set the actual class on superclass generic parameter", clazz.getSimpleName()));
            return Object.class;
        } else {
            log.warn(String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index, clazz.getSimpleName(), params.length));
            return null;
        }
    }


    /**
     * 获得实现接口的泛型参数类型，取第一个参数类型
     *
     * @param clazz 超类类型
     */
    @SuppressWarnings("rawtypes")
    public static Class getClassGenericInterfacesType(final Class clazz, final Class rawType) {
        return getClassGenericInterfacesType(clazz, rawType,0);
    }


    /**
     * 获得实现接口的泛型参数类型
     *
     * @param clazz 超类类型
     */
    @SuppressWarnings("rawtypes")
    public static Class getClassGenericInterfacesType(final Class clazz, final Class rawType, final int index) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType parameterizedType
                    && rawType.equals(parameterizedType.getRawType())) {
                Type[] actualTypes = parameterizedType.getActualTypeArguments();
                if (index >= 0 && index < actualTypes.length) {
                    Type actualType = actualTypes[index];
                    if (actualType instanceof Class) {
                        return (Class<?>) actualType;
                    } else if (actualType instanceof ParameterizedType) {
                        return (Class<?>) ((ParameterizedType) actualType).getRawType();
                    }
                }
            }
        }
        return Object.class;

    }

    /**
     * 创建泛型对应的对象
     *
     * @param initargs
     * @return
     */
    public static Object newInstance(Class<?> clazz, Object... initargs) {
        return newInstance(clazz, 0, initargs);
    }

    /**
     * 创建泛型对应的对象
     *
     * @param initargs
     * @return
     */
    public static Object newInstance(Class<?> clazz, Integer index, Object... initargs) {
        Object o = null;
        try {
            o = getClassGenericSuperclassType(clazz, index).getDeclaredConstructor().newInstance(initargs);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            log.error("创建泛型对应的对象异常", e);
            return o;
        }
        return o;
    }

    /**
     * 获取第一个带有指定注解的属性
     *
     * @param clazz
     * @param annotationCalass
     * @return
     */
    public static Field getFieldFirstWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotationCalass) {
        return getDeclaredFieldsAll(clazz)
                .parallelStream()
                .filter(field -> field.isAnnotationPresent(annotationCalass))
                .findFirst().orElse(null);
    }

    /**
     * 获取第一个带有指定注解的方法
     *
     * @param clazz
     * @param annotationCalass
     * @return
     */
    public static Method getMethodFirstWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotationCalass) {
        return getDeclaredMethodAll(clazz)
                .parallelStream()
                .filter(method -> method.isAnnotationPresent(annotationCalass))
                .findFirst().orElse(null);
    }


    public static Class<?> getWrapType(Class<?> clazz) {
        if (clazz == null || !clazz.isPrimitive()) {
            return clazz;
        }
        if (clazz == Integer.TYPE) {
            return Integer.class;
        } else if (clazz == Long.TYPE) {
            return Long.class;
        } else if (clazz == Boolean.TYPE) {
            return Boolean.class;
        } else if (clazz == Float.TYPE) {
            return Float.class;
        } else if (clazz == Double.TYPE) {
            return Double.class;
        } else if (clazz == Short.TYPE) {
            return Short.class;
        } else if (clazz == Character.TYPE) {
            return Character.class;
        } else if (clazz == Byte.TYPE) {
            return Byte.class;
        } else if (clazz == Void.TYPE) {
            return Void.class;
        }
        return clazz;
    }

    public static Class<?> primitiveToBoxed(Class<?> paraClass) {
        if (paraClass == Integer.TYPE) {
            return Integer.class;
        } else if (paraClass == Long.TYPE) {
            return Long.class;
        } else if (paraClass == Double.TYPE) {
            return Double.class;
        } else if (paraClass == Float.TYPE) {
            return Float.class;
        } else if (paraClass == Boolean.TYPE) {
            return Boolean.class;
        } else if (paraClass == Short.TYPE) {
            return Short.class;
        } else if (paraClass == Byte.TYPE) {
            return Byte.class;
        } else if (paraClass == Character.TYPE) {
            return Character.class;
        } else {
            throw new IllegalArgumentException("Can not convert primitive class for type: " + paraClass);
        }
    }

}
