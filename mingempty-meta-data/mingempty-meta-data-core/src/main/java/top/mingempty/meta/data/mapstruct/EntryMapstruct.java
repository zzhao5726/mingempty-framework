package top.mingempty.meta.data.mapstruct;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import top.mingempty.meta.data.model.bo.EntryBo;
import top.mingempty.meta.data.model.bo.EntryQueryBo;
import top.mingempty.meta.data.model.po.ChangeEntryPo;
import top.mingempty.meta.data.model.po.EntryPo;
import top.mingempty.meta.data.model.vo.in.EntryCreateVo;
import top.mingempty.meta.data.model.vo.in.EntryQueryVo;
import top.mingempty.meta.data.model.vo.in.EntryUpdateVo;
import top.mingempty.meta.data.model.vo.out.EntryBaseResultVo;

import java.util.Collection;
import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntryMapstruct {
    EntryMapstruct INSTANCE = Mappers.getMapper(EntryMapstruct.class);

    @Mappings(value = {
            @Mapping(target = "entryId", expression = "java(top.mingempty.meta.data.util.IDUtil.gainId())")
    })
    @Named("createVoToChangePo")
    ChangeEntryPo createVoToChangePo(EntryCreateVo entryCreateVo);

    @IterableMapping(qualifiedByName = "createVoToChangePo")
    List<ChangeEntryPo> createVoToChangePo(Collection<EntryCreateVo> entryCreateVos);

    @Named("changePoToBo")
    EntryBo changePoToBo(ChangeEntryPo changeEntryPo);

    @IterableMapping(qualifiedByName = "changePoToBo")
    List<EntryBo> changePoToBo(Collection<ChangeEntryPo> changeEntryPos);

    @Named("poToBo")
    EntryBo poToBo(EntryPo entryPo);

    @IterableMapping(qualifiedByName = "poToBo")
    List<EntryBo> poToBo(Collection<EntryPo> entryPo);

    void updateBoFromVo(EntryUpdateVo vo, @MappingTarget EntryBo bo);

    @Mappings(value = {
            @Mapping(target = "entryId", expression = "java(top.mingempty.meta.data.util.IDUtil.gainId())")
    })
    ChangeEntryPo changeBoToPo(EntryBo bo);

    EntryQueryBo queryVoToBo(EntryQueryVo entryQueryVo);

    @Named("queryBoToResultVo")
    EntryBaseResultVo queryBoToResultVo(EntryBo entryBo);

    @IterableMapping(qualifiedByName = "queryBoToResultVo")
    List<EntryBaseResultVo> queryBoToResultVo(List<EntryBo> entryBos);
}

