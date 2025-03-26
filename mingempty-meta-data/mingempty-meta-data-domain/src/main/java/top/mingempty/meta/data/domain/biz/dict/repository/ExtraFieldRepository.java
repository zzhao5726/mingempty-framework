package top.mingempty.meta.data.domain.biz.dict.repository;

import top.mingempty.meta.data.domain.biz.dict.model.ExtraFieldModel;
import top.mingempty.meta.data.domain.biz.dict.query.ExtraFieldQuery;

import java.util.Collection;

/**
 * 字典扩展字段信息仓储接口
 *
 * @author zzhao
 */
public interface ExtraFieldRepository extends BaseRepository<ExtraFieldModel, ExtraFieldQuery> {

    /**
     * 字典扩展字段信息修改
     *
     * @param extraField
     */
    void modifies(ExtraFieldModel extraField);

    @Override
    default ExtraFieldQuery gainQuery(String entryCode) {
        return ExtraFieldQuery.builder().entryCode(entryCode).build();
    }

    @Override
    default ExtraFieldQuery gainQuery(Collection<String> entryCodes) {
        return ExtraFieldQuery.builder().entryCodes(entryCodes).build();
    }
}
