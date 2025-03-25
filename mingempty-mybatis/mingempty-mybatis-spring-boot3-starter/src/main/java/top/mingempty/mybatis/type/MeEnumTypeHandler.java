package top.mingempty.mybatis.type;

import cn.hutool.core.map.MapUtil;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import top.mingempty.domain.enums.BaseMetaData;
import top.mingempty.mybatis.enums.EnumValue;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举自定义类型转换
 *
 * @param <E>
 */
public class MeEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private static final Map<String, String> TABLE_METHOD_OF_ENUM_TYPES = new ConcurrentHashMap<>();
    private static final ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();


    private final Class<E> enumClassType;
    private final Class<?> keyType;
    private final Invoker getInvoker;

    public MeEnumTypeHandler(Class<E> enumClassType) {
        if (enumClassType == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.enumClassType = enumClassType;

        MetaClass metaClass = MetaClass.forClass(enumClassType, REFLECTOR_FACTORY);
        String name = "itemCode";
        if (!BaseMetaData.class.isAssignableFrom(enumClassType)) {
            name = findEnumValueFieldName(this.enumClassType).orElseThrow(() -> new IllegalArgumentException(String.format("Could not find @EnumValue in Class: %s.", this.enumClassType.getName())));
        }
        this.keyType = metaClass.getGetterType(name);
        this.getInvoker = metaClass.getGetInvoker(name);
    }

    /**
     * 查找标记标记EnumValue字段
     *
     * @param clazz class
     * @return EnumValue字段
     * @since 3.3.1
     */
    public static Optional<String> findEnumValueFieldName(Class<?> clazz) {
        if (clazz != null && clazz.isEnum()) {
            String className = clazz.getName();
            return Optional.ofNullable(MapUtil.computeIfAbsent(TABLE_METHOD_OF_ENUM_TYPES, className, key -> {
                Optional<Field> fieldOptional = findEnumValueAnnotationField(clazz);
                return fieldOptional.map(Field::getName).orElse(null);
            }));
        }
        return Optional.empty();
    }

    private static Optional<Field> findEnumValueAnnotationField(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.isAnnotationPresent(EnumValue.class)).findFirst();
    }


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter.getItemCode());

    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.wasNull() ? null : BaseMetaData.EnumHelper.INSTANCE.findOne(enumClassType, rs.getObject(columnName, keyType));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.wasNull() ? null : BaseMetaData.EnumHelper.INSTANCE.findOne(enumClassType, rs.getObject(columnIndex, keyType));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.wasNull() ? null : BaseMetaData.EnumHelper.INSTANCE.findOne(enumClassType, cs.getObject(columnIndex, keyType));
    }
}
