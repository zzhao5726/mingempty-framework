package top.mingempty.meta.data.domain.biz.dict.repository;

import top.mingempty.meta.data.domain.biz.dict.model.LabelModel;
import top.mingempty.meta.data.domain.biz.dict.query.LabelQuery;

import java.util.Collection;

/**
 * 字典项标签仓储接口
 *
 * @author zzhao
 */
public interface LabelRepository extends BaseRepository<LabelModel, LabelQuery> {

    @Override
    default LabelQuery gainQuery(String entryCode) {
        return LabelQuery.builder().entryCode(entryCode).build();
    }

    @Override
    default LabelQuery gainQuery(Collection<String> entryCodes) {
        return LabelQuery.builder().entryCodes(entryCodes).build();
    }
}
