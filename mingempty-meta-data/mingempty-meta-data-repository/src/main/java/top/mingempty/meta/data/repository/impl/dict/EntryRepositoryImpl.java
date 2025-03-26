package top.mingempty.meta.data.repository.impl.dict;

import cn.hutool.core.util.ObjUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.commons.exception.MetaDataException;
import top.mingempty.meta.data.domain.biz.dict.model.EntryModel;
import top.mingempty.meta.data.domain.biz.dict.query.EntryQuery;
import top.mingempty.meta.data.domain.biz.dict.repository.EntryRepository;
import top.mingempty.meta.data.repository.impl.dict.converter.EntryRepositoryConverter;
import top.mingempty.meta.data.repository.model.po.EntryPo;
import top.mingempty.meta.data.repository.service.ChangeEntryService;
import top.mingempty.meta.data.repository.service.EntryService;

import java.util.List;
import java.util.Optional;


/**
 * * 字典条目仓储接口实现
 * *
 * * @author zzhao
 */
@Slf4j
@Component
@AllArgsConstructor
public class EntryRepositoryImpl implements EntryRepository {

    private final EntryService entryService;

    private final ChangeEntryService changeEntryService;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void modifies(EntryModel entryModel) {
        Optional.ofNullable(entryModel)
                .orElseThrow(() -> new MetaDataException("meta-data-repository-0000000001"));
        changeEntryService.save(EntryRepositoryConverter.modelToChangePo(entryModel));
        entryService.transferChange(entryModel.getEntryCode());
    }

    @Override
    public List<EntryModel> query(EntryQuery entryQuery, final MePage mePage) {
        if (ObjUtil.isEmpty(entryQuery)) {
            return List.of();
        }
        return Optional.ofNullable(entryQuery.getEntryVersion())
                .map(query -> changeEntryService.query(entryQuery, mePage))
                .map(EntryRepositoryConverter::changePoToModels)
                .orElseGet(() -> {
                    List<EntryPo> entryPos = entryService.query(entryQuery, mePage);
                    return EntryRepositoryConverter.poToModels(entryPos);
                });
    }
}
