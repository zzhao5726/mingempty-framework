package top.mingempty.meta.data.repository.service;

import com.mybatisflex.core.service.IService;
import top.mingempty.domain.base.MePage;
import top.mingempty.meta.data.domain.biz.dict.query.ItemQuery;
import top.mingempty.meta.data.repository.model.po.ChangeItemPo;

import java.util.List;

/**
 * 字典项变化流水表 服务层。
 *
 * @author zzhao
 * @since 2025-03-27 15:42:33
 */
public interface ChangeItemService extends IService<ChangeItemPo> {

    /**
     * 条件查询条目字典项
     *
     * @param itemQuery 查询条件
     * @return
     */
    default List<ChangeItemPo> query(ItemQuery itemQuery) {
        return query(itemQuery, null);
    }

    /**
     * 条件查询条目字典项
     *
     * @param itemQuery 查询条件
     * @param mePage    分页参数
     * @return
     */
    List<ChangeItemPo> query(ItemQuery itemQuery, final MePage mePage);

}
