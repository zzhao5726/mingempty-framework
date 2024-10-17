package top.mingempty.datasource.mapstruct;

import cn.beecp.BeeDataSource;
import cn.beecp.BeeDataSourceConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import top.mingempty.datasource.creator.beecp.BeeCpConfig;
import top.mingempty.datasource.model.DataSourceConfig;

/**
 * BeeCp配置文件转换
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BeeCpConfigMergeMapstruct extends ConfigMergeMapstruct<BeeCpConfig> {
    BeeCpConfigMergeMapstruct INSTANCE = Mappers.getMapper(BeeCpConfigMergeMapstruct.class);


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
    BeeDataSourceConfig toDataSourceConfig(DataSourceConfig dataSourceConfig, BeeCpConfig properties) ;

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
    BeeDataSource toDataSource(DataSourceConfig dataSourceConfig, BeeCpConfig properties) ;

}
