package top.mingempty.jdbc.mapstruct;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import top.mingempty.jdbc.domain.C3p0Config;

/**
 * c3p0配置转换
 *
 * @author zzhao
 */
@Mapper
public interface C3p0Mapstruct {
    C3p0Mapstruct INSTANCE = Mappers.getMapper(C3p0Mapstruct.class);

    /**
     * 将配置文件转换为数据源
     *
     * @param properties 配置文件
     * @return 数据源
     */
    @Mappings(value = {
            @Mapping(target = "user", source = "username"),
            @Mapping(target = "jdbcUrl", source = "url"),
            @Mapping(target = "driverClass", source = "driverClassName")
    })
    ComboPooledDataSource toDataSource(C3p0Config properties) throws Exception;

}
