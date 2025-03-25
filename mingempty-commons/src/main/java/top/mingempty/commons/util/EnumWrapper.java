
package top.mingempty.commons.util;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import top.mingempty.commons.exception.BaseCommonException;
import top.mingempty.domain.enums.BaseMetaData;
import top.mingempty.domain.enums.EnumValue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class EnumWrapper<E extends Enum<E>, K> {

    private static final Map<Class<? extends Enum<?>>, EnumWrapper<? extends Enum<?>, ?>> CACHE_ENUM_WRAPPER = new ConcurrentHashMap<>();

    private final static Map<Class<? extends Enum<?>>, Map<?, ? extends Enum<?>>> CACHE_ENUM = new ConcurrentHashMap<>();

    /**
     * 是否有@EnumValue注解
     */
    private boolean hasEnumValueAnnotation = false;

    /**
     * 是否实现了BaseMetaData
     */
    private boolean hasImplementsBaseMetaData = false;

    /**
     * 枚举类
     */
    private final Class<E> enumClass;
    /**
     * 枚举值类型
     */
    @Getter
    private final Class<K> propertyType;


    /**
     * 枚举值属性
     */
    private Field property;

    /**
     * 枚举值get方法
     */
    private Method getterMethod;

    public static <R extends Enum<R>, S> EnumWrapper<R, S> of(Class<R> enumClass) {
        EnumWrapper<? extends Enum<?>, ?> enumWrapper = CACHE_ENUM_WRAPPER.get(enumClass);
        if (enumWrapper != null) {
            return (EnumWrapper<R, S>) enumWrapper;
        }
        synchronized (enumClass) {
            enumWrapper = CACHE_ENUM_WRAPPER.get(enumClass);
            if (enumWrapper != null) {
                return (EnumWrapper<R, S>) enumWrapper;
            }
            enumWrapper = new EnumWrapper<R, S>(enumClass);
            CACHE_ENUM_WRAPPER.put(enumClass, enumWrapper);
            return (EnumWrapper<R, S>) enumWrapper;
        }
    }

    public EnumWrapper(Class<E> enumClass) {
        this.enumClass = enumClass;
        if (BaseMetaData.class.isAssignableFrom(enumClass)) {
            hasImplementsBaseMetaData = true;
            this.propertyType = ReflectionUtil.getClassGenericInterfacesType(enumClass, BaseMetaData.class, 1);
            return;
        }
        Field enumValueField = ReflectionUtil.getFieldFirstWithAnnotation(enumClass, EnumValue.class);
        if (enumValueField != null) {
            hasEnumValueAnnotation = true;
        }
        Class<K> propertyType1 = null;
        if (hasEnumValueAnnotation) {
            propertyType1 = (Class<K>) ReflectionUtil.getWrapType(enumValueField.getType());
            String getterMethodName = "get" + StrUtil.upperFirst(enumValueField.getName());
            Method getter = ReflectionUtil.getMethod(enumClass, getterMethodName);
            if (getter == null) {
                if (Modifier.isPublic(enumValueField.getModifiers())) {
                    property = enumValueField;
                } else {
                    throw new IllegalStateException("Can not find method \"" + getterMethodName + "()\" in enum: " + enumClass.getName());
                }
            } else {
                this.getterMethod = getter;
            }
        }

        if (!hasEnumValueAnnotation) {
            Method enumValueMethod = ReflectionUtil.getMethodFirstWithAnnotation(enumClass, EnumValue.class);
            if (enumValueMethod != null) {
                String methodName = enumValueMethod.getName();
                if (!(methodName.startsWith("get") && methodName.length() > 3)) {
                    throw new IllegalStateException("Can not find get method \"" + methodName + "()\" in enum: " + enumClass.getName());
                }
                this.getterMethod = enumValueMethod;
                this.hasEnumValueAnnotation = true;
                Class<?> returnType = enumValueMethod.getReturnType();
                if (returnType.isPrimitive()) {
                    returnType = ReflectionUtil.primitiveToBoxed(returnType);
                }
                propertyType1 = (Class<K>) returnType;
            }
        }
        propertyType = propertyType1;
        CACHE_ENUM.computeIfAbsent(enumClass, key -> {
            Map<Object, E> map = new ConcurrentHashMap<>();
            Arrays.stream(enumClass.getEnumConstants()).parallel().forEach(e -> {
                map.put(getEnumValue(e), e);
            });
            return map;
        });

    }

    /**
     * 获取枚举值
     * 顺序：
     * 1、@EnumValue标识的get方法
     * 2、@EnumValue标识的属性
     * 3、没有使用@EnumValue，取枚举name
     *
     * @param enums
     * @return
     */
    public Object getEnumValue(E enums) {
        try {
            if (hasImplementsBaseMetaData
                    && enums instanceof BaseMetaData<?, ?> baseMetaData) {
                return baseMetaData.getItemCode();
            }

            if (getterMethod != null) {
                return getterMethod.invoke(enums);
            } else if (property != null) {
                return property.get(enums);
            } else {
                return enums.name();
            }
        } catch (Exception e) {
            throw new BaseCommonException("0000000001", e);
        }
    }


    public <S extends Enum<S> & BaseMetaData<S, K>, K> E getEnum(Object value) {
        if (ObjUtil.isNull(value)) {
            return null;
        }
        if (hasImplementsBaseMetaData) {
            return (E) BaseMetaData.EnumHelper.INSTANCE.findOne((Class<S>) enumClass, (K) value);
        }
        return (E) Optional.ofNullable(CACHE_ENUM.get(enumClass))
                .map(map -> map.get(value))
                .orElse(null);
    }

}
