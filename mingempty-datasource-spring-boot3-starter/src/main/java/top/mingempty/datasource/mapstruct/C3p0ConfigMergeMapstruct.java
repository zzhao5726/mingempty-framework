package top.mingempty.datasource.mapstruct;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import top.mingempty.datasource.creator.c3p0.C3p0Config;
import top.mingempty.datasource.model.DataSourceConfig;

/**
 * C3p0配置文件转换
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface C3p0ConfigMergeMapstruct extends ConfigMergeMapstruct<C3p0Config> {
    C3p0ConfigMergeMapstruct INSTANCE = Mappers.getMapper(C3p0ConfigMergeMapstruct.class);


    /**
     * 将配置文件转换为数据源
     *
     * @param properties 配置文件
     * @return 数据源
     */
    @Mappings(value = {
            @Mapping(target = "user", source = "dataSourceConfig.username"),
            @Mapping(target = "jdbcUrl", source = "dataSourceConfig.url"),
            @Mapping(target = "driverClass", source = "dataSourceConfig.driverClassName"),
            @Mapping(target = "password", source = "dataSourceConfig.password")
    })
    ComboPooledDataSource toDataSource(DataSourceConfig dataSourceConfig, C3p0Config properties) throws Exception;
}
