package top.mingempty.mybatis.flex.handler;

import com.mybatisflex.annotation.EnumValue;
import com.mybatisflex.core.handler.FlexEnumTypeHandler;
import com.mybatisflex.core.util.ClassUtil;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import top.mingempty.mybatis.type.MeEnumTypeHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MeMFCompositeEnumTypeHandler<E extends Enum<E>> implements TypeHandler<E> {

    private final TypeHandler<E> delegate;

    public MeMFCompositeEnumTypeHandler(Class<E> enumClass) {
        boolean isNotFound = false;
        List<Field> enumDbValueFields = ClassUtil.getAllFields(enumClass, f -> f.getAnnotation(EnumValue.class) != null);
        if (enumDbValueFields.isEmpty()) {
            Method enumDbValueMethod = ClassUtil.getFirstMethodByAnnotation(enumClass, EnumValue.class);
            if (enumDbValueMethod == null) {
                isNotFound = true;
            }
        }
        if (isNotFound) {
            delegate = new MeEnumTypeHandler<>(enumClass);
        } else {
            delegate = new FlexEnumTypeHandler<>(enumClass);
        }
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        delegate.setParameter(ps, i, parameter, jdbcType);
    }

    @Override
    public E getResult(ResultSet rs, String columnName) throws SQLException {
        return delegate.getResult(rs, columnName);
    }

    @Override
    public E getResult(ResultSet rs, int columnIndex) throws SQLException {
        return delegate.getResult(rs, columnIndex);
    }

    @Override
    public E getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return delegate.getResult(cs, columnIndex);
    }

}