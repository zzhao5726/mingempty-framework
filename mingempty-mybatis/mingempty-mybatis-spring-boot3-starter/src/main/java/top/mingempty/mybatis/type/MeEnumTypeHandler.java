package top.mingempty.mybatis.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import top.mingempty.commons.util.EnumWrapper;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 枚举自定义类型转换
 *
 * @param <E>
 */
public class MeEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private final EnumWrapper<E, ?> enumWrapper;

    public MeEnumTypeHandler(Class<E> enumClassType) {
        enumWrapper = EnumWrapper.of(enumClassType);
    }


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        Object value = enumWrapper.getEnumValue(parameter);
        if (jdbcType == null) {
            ps.setObject(i, value);
        } else {
            ps.setObject(i, value, jdbcType.TYPE_CODE);
        }

    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object value = rs.getObject(columnName, enumWrapper.getPropertyType());
        if (null == value && rs.wasNull()) {
            return null;
        }
        return enumWrapper.getEnum(value);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object value = rs.getObject(columnIndex, enumWrapper.getPropertyType());
        if (null == value && rs.wasNull()) {
            return null;
        }
        return enumWrapper.getEnum(value);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object value = cs.getObject(columnIndex, enumWrapper.getPropertyType());
        if (null == value && cs.wasNull()) {
            return null;
        }
        return enumWrapper.getEnum(value);
    }
}
