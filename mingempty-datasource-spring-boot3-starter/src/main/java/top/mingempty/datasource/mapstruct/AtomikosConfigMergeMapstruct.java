package top.mingempty.datasource.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import top.mingempty.datasource.creator.atomikos.AtomikosConfig;

/**
 * Atomikos配置文件转换
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AtomikosConfigMergeMapstruct extends ConfigMergeMapstruct<AtomikosConfig> {
    AtomikosConfigMergeMapstruct INSTANCE = Mappers.getMapper(AtomikosConfigMergeMapstruct.class);

}
