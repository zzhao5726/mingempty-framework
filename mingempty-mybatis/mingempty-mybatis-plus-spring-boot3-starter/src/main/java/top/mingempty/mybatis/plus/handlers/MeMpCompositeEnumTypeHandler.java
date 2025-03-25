package top.mingempty.mybatis.plus.handlers;

import com.baomidou.mybatisplus.core.handlers.MybatisEnumTypeHandler;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import top.mingempty.mybatis.type.MeEnumTypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author miemie
 * @since 2022-03-07
 */
public class MeMpCompositeEnumTypeHandler<E extends Enum<E>> implements TypeHandler<E> {

    private static final Map<Class<?>, Boolean> MP_ENUM_CACHE = new ConcurrentHashMap<>();
    private final TypeHandler<E> delegate;

    public MeMpCompositeEnumTypeHandler(Class<E> enumClassType) {
        if (enumClassType == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        if (CollectionUtils.computeIfAbsent(MP_ENUM_CACHE, enumClassType, MybatisEnumTypeHandler::isMpEnums)) {
            delegate = new MybatisEnumTypeHandler<>(enumClassType);
        } else {
            delegate = new MeEnumTypeHandler<>(enumClassType);
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