package top.mingempty.mybatis.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import top.mingempty.commons.util.ReflectionUtil;
import top.mingempty.domain.enums.BaseMetaData;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 枚举自定义类型转换
 *
 * @param <E>
 * @param <K>
 */
public class Me2EnumTypeHandler<E extends Enum<E> & BaseMetaData<E, K>, K> extends BaseTypeHandler<E> {
    private final Class<E> enumClassType;
    private final Class<K> keyType;

    public Me2EnumTypeHandler(Class<E> enumClassType) {
        if (enumClassType == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.enumClassType = enumClassType;
        this.keyType = ReflectionUtil.getClassGenricType(enumClassType, 1);

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
