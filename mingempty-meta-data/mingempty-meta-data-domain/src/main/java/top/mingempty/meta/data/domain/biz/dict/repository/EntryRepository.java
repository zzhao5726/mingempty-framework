package top.mingempty.meta.data.domain.biz.dict.repository;

import top.mingempty.meta.data.domain.biz.dict.model.EntryModel;
import top.mingempty.meta.data.domain.biz.dict.query.EntryQuery;

import java.util.Collection;

/**
 * 字典条目仓储接口
 *
 * @author zzhao
 */
public interface EntryRepository extends BaseRepository<EntryModel, EntryQuery> {

    /**
     * 创建或修改条目
     *
     * @param entryModel
     */
    void modifies(EntryModel entryModel);

    @Override
    default EntryQuery gainQuery(String entryCode) {
        return EntryQuery.builder().entryCode(entryCode).build();
    }

    @Override
    default EntryQuery gainQuery(Collection<String> entryCodes) {
        return EntryQuery.builder().entryCodes(entryCodes).build();
    }
}
