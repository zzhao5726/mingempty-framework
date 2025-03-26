package top.mingempty.meta.data.repository.service;

import cn.hutool.core.util.ObjUtil;
import com.mybatisflex.core.service.IService;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.ItemQuery;
import top.mingempty.meta.data.repository.model.po.ItemPo;

import java.util.Collection;
import java.util.List;

/**
 * 字典项表 服务层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
public interface ItemService extends IService<ItemPo> {

    /**
     * 转移变更数据
     *
     * @param entryCode
     */
    default void transferChange(String entryCode) {
        if (ObjUtil.isEmpty(entryCode)) {
            return;
        }
        transferChange(List.of(entryCode));
    }

    /**
     * 转移变更数据
     *
     * @param entryCodes
     */
    void transferChange(Collection<String> entryCodes);

    /**
     * 条件查询条目字典项
     *
     * @param itemQuery 查询条件
     * @return
     */
    default List<ItemPo> query(ItemQuery itemQuery) {
        return query(itemQuery, null);
    }

    /**
     * 条件查询条目字典项
     *
     * @param itemQuery 查询条件
     * @param mePage    分页参数
     * @return
     */
    List<ItemPo> query(ItemQuery itemQuery, final MePage mePage);
}
