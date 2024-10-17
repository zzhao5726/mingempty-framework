package top.mingempty.datasource.mapstruct;

import com.zaxxer.hikari.HikariConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import top.mingempty.datasource.creator.hikaricp.HikariCpConfig;
import top.mingempty.datasource.model.DataSourceConfig;

/**
 * HikariCp配置文件转换
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,nullValueCheckStrategy= NullValueCheckStrategy.ALWAYS)
public interface HikariCpConfigMergeMapstruct extends ConfigMergeMapstruct<HikariCpConfig> {
    HikariCpConfigMergeMapstruct INSTANCE = Mappers.getMapper(HikariCpConfigMergeMapstruct.class);


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
            @Mapping(target = "jdbcUrl", source = "dataSourceConfig.url"),
            @Mapping(target = "driverClassName", source = "dataSourceConfig.driverClassName"),
            @Mapping(target = "poolName", source = "dataSourceConfig.name")
    })
    HikariConfig toDataSource(DataSourceConfig dataSourceConfig, HikariCpConfig properties) ;

}
