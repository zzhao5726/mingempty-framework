package top.mingempty.meta.data.domain.biz.dict.repository;

import top.mingempty.meta.data.domain.biz.dict.model.ItemModel;
import top.mingempty.meta.data.domain.biz.dict.query.ItemQuery;

import java.util.Collection;

/**
 * 字典项仓储接口
 *
 * @author zzhao
 */
public interface ItemRepository extends BaseRepository<ItemModel, ItemQuery> {
    /**
     * 条目字典项信息修改
     *
     * @param item
     */
    void modifies(ItemModel item);

    @Override
    default ItemQuery gainQuery(String entryCode) {
        return ItemQuery.builder().entryCode(entryCode).build();
    }

    @Override
    default ItemQuery gainQuery(Collection<String> entryCodes) {
        return ItemQuery.builder().entryCodes(entryCodes).build();
    }
}
