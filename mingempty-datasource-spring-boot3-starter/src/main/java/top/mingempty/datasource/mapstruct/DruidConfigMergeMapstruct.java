package top.mingempty.datasource.mapstruct;

import com.alibaba.druid.pool.DruidDataSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import top.mingempty.datasource.creator.druid.DruidConfig;
import top.mingempty.datasource.model.DataSourceConfig;

/**
 * Druid配置文件转换
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DruidConfigMergeMapstruct extends ConfigMergeMapstruct<DruidConfig> {
    DruidConfigMergeMapstruct INSTANCE = Mappers.getMapper(DruidConfigMergeMapstruct.class);


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
    DruidDataSource toDataSource(DataSourceConfig dataSourceConfig, DruidConfig properties) throws Exception;

}
