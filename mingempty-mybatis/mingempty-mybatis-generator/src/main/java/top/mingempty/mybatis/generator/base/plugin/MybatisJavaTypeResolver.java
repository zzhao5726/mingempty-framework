package top.mingempty.mybatis.generator.base.plugin;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.sql.Types;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MybatisJavaTypeResolver extends JavaTypeResolverDefaultImpl {

    public MybatisJavaTypeResolver() {
        super();
        typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Integer.class.getName())));
        typeMap.put(Types.BIT, new JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Integer.class.getName())));
        typeMap.put(Types.DATE, new JdbcTypeInformation("DATE", new FullyQualifiedJavaType(LocalDateTime.class.getName())));
        typeMap.put(Types.TIME, new JdbcTypeInformation("TIME",  new FullyQualifiedJavaType(LocalTime.class.getName())));
        typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation("TIMESTAMP",  new FullyQualifiedJavaType(LocalDateTime.class.getName())));
    }
}