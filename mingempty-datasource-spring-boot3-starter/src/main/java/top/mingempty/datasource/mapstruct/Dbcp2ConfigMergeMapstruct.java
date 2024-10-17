package top.mingempty.datasource.mapstruct;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import top.mingempty.datasource.creator.dbcp.Dbcp2Config;
import top.mingempty.datasource.model.DataSourceConfig;

/**
 * Dbcp2配置文件转换
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface Dbcp2ConfigMergeMapstruct extends ConfigMergeMapstruct<Dbcp2Config> {
    Dbcp2ConfigMergeMapstruct INSTANCE = Mappers.getMapper(Dbcp2ConfigMergeMapstruct.class);

    /**
     * 将配置文件转换为数据源
     *
     * @param dataSourceConfig 配置文件
     * @param properties         配置文件
     * @return 数据源
     */
    @Mappings(value = {
            @Mapping(target = "username", source = "dataSourceConfig.username"),
            @Mapping(target = "password", source = "dataSourceConfig.password"),
            @Mapping(target = "url", source = "dataSourceConfig.url"),
            @Mapping(target = "driverClassName", source = "dataSourceConfig.driverClassName")
    })
    BasicDataSource toDataSource(DataSourceConfig dataSourceConfig, Dbcp2Config properties) ;

}
